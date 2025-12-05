package upc.edu.pe.finanzas.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.finanzas.dtos.*;
import upc.edu.pe.finanzas.entities.*;
import upc.edu.pe.finanzas.repositories.*;
import upc.edu.pe.finanzas.servicesinterfaces.ICatTipoTasaService;
import upc.edu.pe.finanzas.servicesinterfaces.ISimulacionesCreditoService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SimulacionesCreditoServiceImplement implements ISimulacionesCreditoService {

    @Autowired
    private IClientesRepository clientesRepo;
    @Autowired
    private IInmueblesRepository inmueblesRepo;
    @Autowired
    private IEntidadesFinancierasRepository ifiRepo;
    @Autowired
    private ICatMonedaRepository catMonedaRepo;
    @Autowired
    private ICatTipoTasaRepository catTipoTasaRepo;
    @Autowired
    private ICatTipoGraciaRepository catTipoGraciaRepo;
    @Autowired
    private IParamBonoRepository paramBonoRepo;
    @Autowired
    private IParamMiViviendaRepository paramMiViRepo;

    @Autowired
    private ISimulacionesCreditoRepository simulacionesRepo;
    @Autowired
    private IPlanesPagoRepository planesRepo;
    @Autowired
    private IIndicadoresFinancierosRepository indicadoresRepo;

    /** Convierte TEA (en fracción, no en %) a TEM */
    private double teaToTem(double teaAnual) {
        return Math.pow(1 + teaAnual, 1.0 / 12.0) - 1.0;
    }

    /** Convierte TNA (fracción) con capitalización m a TEM */
    private double tnaToTem(double tna, int m) {
        double tea = Math.pow(1 + (tna / m), m) - 1.0;
        return teaToTem(tea);
    }

    /** NPV/VAN de una serie de flujos dados una tasa mensual */
    private double npv(double tasaMensual, double[] flujos) {
        double van = 0.0;
        for (int t = 0; t < flujos.length; t++) {
            van += flujos[t] / Math.pow(1 + tasaMensual, t);
        }
        return van;
    }

    /** IRR/TIR aproximada por bisección sobre flujos mensuales */
    private double irrBiseccion(double[] flujos, double low, double high, double tol, int maxIter) {
        double fLow = npv(low, flujos);
        double fHigh = npv(high, flujos);

        // Si no hay cambio de signo, devolvemos 0 como fallback
        if (fLow * fHigh > 0) {
            return 0.0;
        }

        for (int i = 0; i < maxIter; i++) {
            double mid = (low + high) / 2.0;
            double fMid = npv(mid, flujos);

            if (Math.abs(fMid) < tol) {
                return mid;
            }

            if (fLow * fMid < 0) {
                high = mid;
                fHigh = fMid;
            } else {
                low = mid;
                fLow = fMid;
            }
        }
        return (low + high) / 2.0;
    }

    @Override
    public List<SimulacionesCredito> list() {
        return simulacionesRepo.findAll();
    }

    @Override
    public void insert(SimulacionesCredito s) {
        if (s.getEstado() == null) {
            s.setEstado("REGISTRADO");
        }
        if (s.getFecha_simulacion() == null) {
            s.setFecha_simulacion(LocalDateTime.now());
        }
        simulacionesRepo.save(s);
    }

    @Override
    public SimulacionesCredito listId(int id) {
        return simulacionesRepo.findById(id).orElse(null);
    }

    @Override
    public void update(SimulacionesCredito s) {
        simulacionesRepo.save(s);
    }

    @Override
    public void delete(int id) {
        simulacionesRepo.deleteById(id);
    }

    // =========================================================
    // Verificación de crédito (MiVivienda + BFH)
    // =========================================================

    @Override
    public VerificarCreditoResponse verificarCredito(SimulacionCreditoRequest req) {
        Clientes cliente = clientesRepo.findById(req.getCliente_id()).orElse(null);
        Inmuebles inmueble = inmueblesRepo.findById(req.getInmueble_id()).orElse(null);
        EntidadesFinancieras ifi = ifiRepo.findById(req.getIfi_id()).orElse(null);

        if (cliente == null || inmueble == null || ifi == null) {
            return new VerificarCreditoResponse(false,
                    "Cliente, inmueble o IFI no existen en el sistema.", false);
        }

        LocalDate hoy = LocalDate.now();
        ParamMiVivienda paramMV = paramMiViRepo.buscarParamVigente(hoy)
                .stream().findFirst().orElse(null);

        if (paramMV == null) {
            return new VerificarCreditoResponse(false,
                    "No hay parámetros vigentes de Nuevo Crédito MiVivienda.", false);
        }

        double ingresoFamiliar = cliente.getIngreso_mensual();
        double precioVivienda = inmueble.getPrecio();

        // Ingreso y precio tope
        if (ingresoFamiliar > paramMV.getIngreso_max_familiar()) {
            return new VerificarCreditoResponse(false,
                    "El ingreso familiar excede el máximo permitido por MiVivienda.", false);
        }

        if (precioVivienda > paramMV.getPrecio_max_no_vis()) {
            return new VerificarCreditoResponse(false,
                    "El precio del inmueble excede el máximo permitido para el producto.", false);
        }

        // Plazo
        if (req.getPlazo_meses() < paramMV.getPlazo_min_meses()
                || req.getPlazo_meses() > paramMV.getPlazo_max_meses()) {
            return new VerificarCreditoResponse(false,
                    "El plazo solicitado no cumple con los rangos de MiVivienda.", false);
        }

        if (req.getPlazo_meses() < ifi.getPlazo_min_meses()
                || req.getPlazo_meses() > ifi.getPlazo_max_meses()) {
            return new VerificarCreditoResponse(false,
                    "El plazo solicitado no cumple con los rangos de la entidad financiera.", false);
        }

        // Cuota inicial mínima (regulador vs IFI)
        double cuotaMinRegulatoria = paramMV.getCuota_ini_min_pct();
        double cuotaMinIFI = ifi.getPorcentaje_cuota_ini_min();
        double cuotaMinRequerida = Math.max(cuotaMinRegulatoria, cuotaMinIFI);

        if (req.getCuota_inicial_pct() < cuotaMinRequerida) {
            return new VerificarCreditoResponse(false,
                    "La cuota inicial es menor al mínimo requerido (" + cuotaMinRequerida + "%).", false);
        }

        // ========= COSTOS INICIALES =========
        double costoNotarial = (req.getCosto_notarial() != null)
                ? req.getCosto_notarial()
                : ifi.getCosto_notarial();

        double costoRegistral = (req.getCosto_registral() != null)
                ? req.getCosto_registral()
                : ifi.getCosto_registral();

        double costoTasacion = (req.getCosto_tasacion() != null)
                ? req.getCosto_tasacion()
                : ifi.getCosto_tasacion();

        double costoEstTit = (req.getCosto_estudio_titulos() != null)
                ? req.getCosto_estudio_titulos()
                : ifi.getCosto_estudio_titulos();

        double comisionActiv = (req.getComision_activacion() != null)
                ? req.getComision_activacion()
                : ifi.getComision_activacion();

        double gastosInicialesIFI =
                costoNotarial
                        + costoRegistral
                        + costoTasacion
                        + costoEstTit
                        + comisionActiv
                        + req.getGastos_adicionales();

        // Monto de crédito estimado
        double cuotaInicialMonto = precioVivienda * req.getCuota_inicial_pct() / 100.0;

        double montoBono = (req.isAplica_bfh() && req.getBfh_monto() != null)
                ? req.getBfh_monto()
                : 0.0;

        double montoCredito = precioVivienda - cuotaInicialMonto + gastosInicialesIFI - montoBono;

        if (montoCredito < ifi.getMonto_min_credito()
                || montoCredito > ifi.getMonto_max_credito()) {
            return new VerificarCreditoResponse(false,
                    "El monto de crédito calculado no se encuentra dentro de los rangos de la IFI.", false);
        }

        // ========= 🔹 NUEVA VALIDACIÓN: IFI permite BFH =========
        if (req.isAplica_bfh() && !ifi.isPermite_bfh()) {
            return new VerificarCreditoResponse(false,
                    "La entidad financiera seleccionada no permite el uso del Bono Familiar Habitacional.", false);
        }

        // Validar bono si se marcó "aplica BFH" Y la IFI lo permite
        if (req.isAplica_bfh()) {
            List<ParamBono> bonos = paramBonoRepo.buscarBonoVigente(
                    ingresoFamiliar, precioVivienda, hoy);

            if (bonos.isEmpty()) {
                return new VerificarCreditoResponse(false,
                        "El cliente no califica a ningún tramo de Bono Familiar Habitacional vigente.", false);
            }
        }

        // ========= 🔹 EVALUAR ELEGIBILIDAD BFH (NUEVO) =========
        boolean elegibleBfh = false;
        String mensajeBfh = "";

        // Solo si la IFI permite BFH
        if (ifi.isPermite_bfh()) {
            // Buscar el tramo de bono que aplica
            List<ParamBono> bonos = paramBonoRepo.buscarBonoVigente(
                    ingresoFamiliar,    // Ingreso del cliente
                    precioVivienda,     // Precio del inmueble
                    hoy                 // Fecha actual
            );

            // Si encontró un tramo, es elegible
            if (!bonos.isEmpty()) {
                elegibleBfh = true;
                ParamBono bono = bonos.get(0);
                mensajeBfh = String.format(
                        " Cliente califica para BFH %s: S/. %.2f",
                        bono.getTramo(),
                        bono.getMonto_bono()
                );

                System.out.println("✅ Cliente elegible para BFH: " + bono.getTramo());
            } else {
                // No califica para ningún tramo
                elegibleBfh = false;
                mensajeBfh = " (No califica para BFH en ningún tramo)";
                System.out.println("❌ Cliente NO elegible para BFH");
            }
        } else {
            // IFI no permite BFH
            elegibleBfh = false;
            mensajeBfh = " (IFI no permite BFH)";
            System.out.println("❌ IFI no permite BFH");
        }

        // ========= RETORNAR CON ELEGIBLEBFH =========
        String mensajeCompleto =
                "El crédito cumple condiciones de MiVivienda e IFI." + mensajeBfh;

        System.out.println("Mensaje de verificación: " + mensajeCompleto);
        System.out.println("Elegible BFH: " + elegibleBfh);

        return new VerificarCreditoResponse(
                true,              // elegibleCredito
                mensajeCompleto,   // mensaje
                elegibleBfh        // ✅ NUEVO CAMPO
        );
    }

    // =========================================================
    // Simulación de crédito – método francés + costos
    // =========================================================

    @Override
    public SimulacionCreditoResponse simularCredito(SimulacionCreditoRequest req) {

        // 1) Revalidar
        VerificarCreditoResponse verif = verificarCredito(req);
        if (!verif.isElegibleCredito()) {
            throw new RuntimeException("El crédito no es elegible: " + verif.getMensaje());
        }

        Clientes cliente = clientesRepo.findById(req.getCliente_id()).orElseThrow();
        Inmuebles inmueble = inmueblesRepo.findById(req.getInmueble_id()).orElseThrow();
        EntidadesFinancieras ifi = ifiRepo.findById(req.getIfi_id()).orElseThrow();
        CatMoneda moneda = catMonedaRepo.findById(req.getMoneda_id()).orElseThrow();
        CatTipoTasa tipoTasa = catTipoTasaRepo.findById(req.getTipo_tasa_id()).orElseThrow();
        CatTipoGracia tipoGracia = catTipoGraciaRepo.findById(req.getTipo_gracia_id()).orElseThrow();

        double precioVivienda = inmueble.getPrecio();
        double cuotaInicialPct = req.getCuota_inicial_pct();
        double cuotaInicialMonto = precioVivienda * cuotaInicialPct / 100.0;

        // ========= COSTOS INICIALES =========
        double costoNotarial = (req.getCosto_notarial() != null)
                ? req.getCosto_notarial()
                : ifi.getCosto_notarial();

        double costoRegistral = (req.getCosto_registral() != null)
                ? req.getCosto_registral()
                : ifi.getCosto_registral();

        double costoTasacion = (req.getCosto_tasacion() != null)
                ? req.getCosto_tasacion()
                : ifi.getCosto_tasacion();

        double costoEstTit = (req.getCosto_estudio_titulos() != null)
                ? req.getCosto_estudio_titulos()
                : ifi.getCosto_estudio_titulos();

        double comisionActiv = (req.getComision_activacion() != null)
                ? req.getComision_activacion()
                : ifi.getComision_activacion();

        double gastosInicialesIFI =
                costoNotarial
                        + costoRegistral
                        + costoTasacion
                        + costoEstTit
                        + comisionActiv
                        + req.getGastos_adicionales();

        double montoBono = (req.isAplica_bfh() && req.getBfh_monto() != null)
                ? req.getBfh_monto()
                : 0.0;

        // Fórmula: Precio - Cuota Inicial + Costos Iniciales - Bono
        double montoCredito = precioVivienda - cuotaInicialMonto + gastosInicialesIFI - montoBono;

        // 2) Calcular TEM a partir de TEA o TNA
        double tea;
        double tem; // tasa efectiva mensual
        if ("EFECTIVA".equalsIgnoreCase(tipoTasa.getCodigo())) {
            tea = req.getTasa_anual_ingresada() / 100.0;
            tem = teaToTem(tea);
        } else {
            double tna = req.getTasa_anual_ingresada() / 100.0;
            int m = req.getCapitalizacion_m();
            tem = tnaToTem(tna, m);
            tea = Math.pow(1 + tem, 12.0) - 1.0;
        }

        int n = req.getPlazo_meses();

        // ======== 🔹 CORRECCIÓN: Cálculo correcto de cuota base considerando gracia ========
        int mesesGracia = req.getMeses_gracia();
        String codigoGracia = tipoGracia.getCodigo();

        // PASO 1: Calcular el saldo DESPUÉS de los períodos de gracia
        double saldoPostGracia = montoCredito;

        if (mesesGracia > 0 &&
                ("TOTAL".equalsIgnoreCase(codigoGracia) ||
                        "PARCIAL".equalsIgnoreCase(codigoGracia))) {

            // Simular los períodos de gracia para calcular el saldo actualizado
            for (int periodoGracia = 1; periodoGracia <= mesesGracia; periodoGracia++) {
                double interesGracia = saldoPostGracia * tem;

                // En gracia (TOTAL o PARCIAL), solo se pagan intereses, NO se amortiza
                // El saldo CRECE por los intereses capitalizados
                saldoPostGracia += interesGracia;

                System.out.println("🔹 Período Gracia " + periodoGracia +
                        ": Interés = " + String.format("%.2f", interesGracia) +
                        ", Saldo = " + String.format("%.2f", saldoPostGracia));
            }
        }

        // PASO 2: Calcular número de períodos de amortización
        int nAmort = n;
        if (mesesGracia > 0 &&
                ("TOTAL".equalsIgnoreCase(codigoGracia) ||
                        "PARCIAL".equalsIgnoreCase(codigoGracia))) {
            nAmort = n - mesesGracia;
        }

        // PASO 3: Calcular cuotaBase usando el saldo DESPUÉS de gracia (CORRECCIÓN CRUCIAL)
        double cuotaBase = saldoPostGracia *
                ((tem * Math.pow(1 + tem, nAmort)) / (Math.pow(1 + tem, nAmort) - 1));

        System.out.println("\n=== CÁLCULO DE CUOTA BASE ===");
        System.out.println("Monto Inicial: " + String.format("%.2f", montoCredito));
        System.out.println("Meses de Gracia: " + mesesGracia);
        System.out.println("Saldo Post-Gracia: " + String.format("%.2f", saldoPostGracia));
        System.out.println("Períodos a Amortizar: " + nAmort);
        System.out.println("Cuota Base Calculada: " + String.format("%.2f", cuotaBase));
        System.out.println("===========================\n");

        // ========= COSTOS PERIÓDICOS =========
        double comisionPeriodica = (req.getComision_periodica_mensual() != null)
                ? req.getComision_periodica_mensual()
                : ifi.getComision_periodica_mensual();

        double portesMensuales = (req.getPortes_mensuales() != null)
                ? req.getPortes_mensuales()
                : (ifi.getPortes_mensuales() == null ? 0.0 : ifi.getPortes_mensuales());

        double gastosAdmMensuales = (req.getGastos_adm_mensuales() != null)
                ? req.getGastos_adm_mensuales()
                : (ifi.getGastos_adm_mensuales() == null ? 0.0 : ifi.getGastos_adm_mensuales());

        // ✅ CORRECCIÓN 2: Seguro de desgravamen (sobre saldo)
        double tasaSdAnual = (req.getTasa_seguro_desgravamen() != null)
                ? req.getTasa_seguro_desgravamen() / 100.0
                : ifi.getTasa_seguro_desgravamen() / 100.0;
        double tasaSdMensual = teaToTem(tasaSdAnual);

        // ✅ CORRECCIÓN 2: Seguro contra todo riesgo (sobre precio del bien)
        double tasaSrAnual = (req.getTasa_seguro_riesgo() != null)
                ? req.getTasa_seguro_riesgo() / 100.0
                : (ifi.getTasa_seguro_riesgo() != null ? ifi.getTasa_seguro_riesgo() / 100.0 : 0.0);
        double tasaSrMensual = teaToTem(tasaSrAnual);

        List<PlanPagoDTO> planDTOs = new ArrayList<>();
        List<Double> flujosCuotasTotales = new ArrayList<>();

        double saldo = montoCredito;
        double totalIntereses = 0.0;
        double totalCostos = 0.0;
        double totalPagado = 0.0;

        double totalAmortizacion = 0.0;
        double totalSd = 0.0;
        double totalSr = 0.0;
        double totalComisiones = 0.0;
        double totalPortes = 0.0;
        double totalGastosAdm = 0.0;

        for (int periodo = 1; periodo <= n; periodo++) {

            double interes = saldo * tem;

            // ✅ Seguro de desgravamen: sobre el saldo del préstamo
            double sd = saldo * tasaSdMensual;

            // ✅ Seguro contra todo riesgo: sobre el precio del bien
            double sr = precioVivienda * tasaSrMensual;

            double comision = comisionPeriodica;
            double costosPeriodo = comision + portesMensuales + gastosAdmMensuales + sd + sr;

            double cuotaCapital;
            double cuotaBasePeriodo;
            double cuotaTotal;
            double saldoFinal;
            double prepago = 0.0;
            String estadoPeriodo;

            // ==========================
            // Lógica de gracia
            // ==========================
            if (periodo <= mesesGracia &&
                    "TOTAL".equalsIgnoreCase(codigoGracia)) {

                // Gracia total: solo intereses + costos, NO amortiza capital
                estadoPeriodo = "GRACIA_TOTAL";
                cuotaCapital = 0.0;
                cuotaBasePeriodo = interes;
                cuotaTotal = cuotaBasePeriodo + costosPeriodo;
                saldoFinal = saldo;

            } else if (periodo <= mesesGracia &&
                    "PARCIAL".equalsIgnoreCase(codigoGracia)) {

                // Gracia parcial: intereses + costos, NO amortiza capital
                estadoPeriodo = "GRACIA_PARCIAL";
                cuotaCapital = 0.0;
                cuotaBasePeriodo = interes;
                cuotaTotal = cuotaBasePeriodo + costosPeriodo;
                saldoFinal = saldo;

            } else {
                // Periodos normales de amortización
                estadoPeriodo = "AMORTIZACION";
                cuotaCapital = cuotaBase - interes;
                cuotaBasePeriodo = cuotaBase;
                cuotaTotal = cuotaBasePeriodo + costosPeriodo;
                saldoFinal = saldo - cuotaCapital;
            }

            // Construimos DTO
            PlanPagoDTO p = new PlanPagoDTO();
            p.setPeriodo(periodo);
            p.setEstado(estadoPeriodo);
            p.setSaldo_inicial(saldo);
            p.setInteres_periodo(interes);
            p.setAmortizacion_periodo(cuotaCapital);
            p.setCostos_periodo(costosPeriodo);
            p.setCuota_periodo(cuotaTotal);
            p.setSaldo_final(Math.max(saldoFinal, 0));

            p.setSeguro_desgravamen(sd);
            p.setSeguro_riesgo(sr);
            p.setComision_periodica(comision);
            p.setPortes_periodo(portesMensuales);
            p.setGastos_adm_periodo(gastosAdmMensuales);
            p.setPrepago(prepago);

            planDTOs.add(p);
            flujosCuotasTotales.add(-cuotaTotal);

            // Acumuladores
            saldo = saldoFinal;

            totalIntereses += interes;
            totalCostos += costosPeriodo;
            totalPagado += cuotaTotal;

            totalAmortizacion += cuotaCapital;
            totalSd += sd;
            totalSr += sr;
            totalComisiones += comision;
            totalPortes += portesMensuales;
            totalGastosAdm += gastosAdmMensuales;
        }

        // 5) Persistir SimulacionesCredito
        SimulacionesCredito simulacion = new SimulacionesCredito();
        simulacion.setCuota_inicial_pct(cuotaInicialPct);
        simulacion.setCuota_inicial_monto(cuotaInicialMonto);
        simulacion.setPlazo_meses(n);
        simulacion.setGastos_adicionales(req.getGastos_adicionales());
        simulacion.setTasa_anual(tea * 100); // en %
        simulacion.setCapitalizacion_m(req.getCapitalizacion_m());
        simulacion.setMeses_gracia(req.getMeses_gracia());
        simulacion.setBfh_aplica(req.isAplica_bfh());
        simulacion.setBfh_monto(montoBono);
        simulacion.setMonto_credito(montoCredito);
        simulacion.setTasa_efectiva_mensual(tem);
        simulacion.setEstado("SIMULADO");
        simulacion.setFecha_simulacion(LocalDateTime.now());

        simulacion.setClientes(cliente);
        simulacion.setInmuebles(inmueble);
        simulacion.setEntidadesFinancieras(ifi);
        simulacion.setCatMoneda(moneda);
        simulacion.setCatTipoTasa(tipoTasa);
        simulacion.setCatTipoGracia(tipoGracia);

        simulacion = simulacionesRepo.save(simulacion);

        // 6) Persistir plan de pagos
        List<PlanesPago> entidadesPlan = new ArrayList<>();
        for (PlanPagoDTO dto : planDTOs) {
            PlanesPago e = new PlanesPago();
            e.setPeriodo(dto.getPeriodo());
            e.setEstado(dto.getEstado());
            e.setSaldo_inicial(dto.getSaldo_inicial());
            e.setInteres_periodo(dto.getInteres_periodo());
            e.setAmortizacion_periodo(dto.getAmortizacion_periodo());
            e.setCostos_periodo(dto.getCostos_periodo());
            e.setCuota_periodo(dto.getCuota_periodo());
            e.setSaldo_final(dto.getSaldo_final());

            e.setSeguro_desgravamen(dto.getSeguro_desgravamen());
            e.setSeguro_riesgo(dto.getSeguro_riesgo());
            e.setComision_periodica(dto.getComision_periodica());
            e.setPortes_periodo(dto.getPortes_periodo());
            e.setGastos_adm_periodo(dto.getGastos_adm_periodo());
            e.setPrepago(dto.getPrepago());

            e.setSimulacionesCredito(simulacion);
            entidadesPlan.add(e);
        }
        planesRepo.saveAll(entidadesPlan);

        // 7) VAN, TIR, TCEA
        // Flujos: t0 = +montoCredito - cuotaInicialMonto
        double flujoInicial = montoCredito - cuotaInicialMonto;
        double[] flujos = new double[flujosCuotasTotales.size() + 1];
        flujos[0] = flujoInicial;
        for (int i = 0; i < flujosCuotasTotales.size(); i++) {
            flujos[i + 1] = flujosCuotasTotales.get(i);
        }

        // Tasa de descuento anual (si no la configuras en IFI, uso la TEA del crédito)
        double tasaDescAnual = (ifi.getTasa_descuento_anual() != null)
                ? ifi.getTasa_descuento_anual() / 100.0
                : tea;
        double tasaDescMensual = teaToTem(tasaDescAnual);

        double van = npv(tasaDescMensual, flujos);
        double tirMensual = irrBiseccion(flujos, -0.9, 1.0, 1e-7, 200);
        double tirAnual = Math.pow(1 + tirMensual, 12.0) - 1.0;
        double tceaAnual = tirAnual;

        IndicadoresFinancieros indicadores = new IndicadoresFinancieros();
        indicadores.setVan(van);
        indicadores.setTir_mensual(tirMensual);
        indicadores.setTir_anual(tirAnual);
        indicadores.setTcea_anual(tceaAnual);
        indicadores.setTotal_intereses(totalIntereses);
        indicadores.setTotal_costos(totalCostos);
        indicadores.setTotal_pagado(totalPagado);

        indicadores.setTotal_amortizacion(totalAmortizacion);
        indicadores.setTotal_seguro_desgravamen(totalSd);
        indicadores.setTotal_seguro_riesgo(totalSr);
        indicadores.setTotal_comisiones(totalComisiones);
        indicadores.setTotal_portes(totalPortes);
        indicadores.setTotal_gastos_adm(totalGastosAdm);
        indicadores.setTasa_descuento_anual(tasaDescAnual * 100.0);

        indicadores.setSimulacionesCredito(simulacion);

        indicadores = indicadoresRepo.save(indicadores);
        simulacion.setIndicadoresFinancieros(indicadores);
        simulacionesRepo.save(simulacion);

        // 8) DTO de respuesta
        SimulacionCreditoDTO simDTO = new SimulacionCreditoDTO();
        simDTO.setSimulacion_id(simulacion.getSimulacion_id());
        simDTO.setCuota_inicial_pct(simulacion.getCuota_inicial_pct());
        simDTO.setCuota_inicial_monto(simulacion.getCuota_inicial_monto());
        simDTO.setPlazo_meses(simulacion.getPlazo_meses());
        simDTO.setGastos_adicionales(simulacion.getGastos_adicionales());
        simDTO.setTasa_anual(simulacion.getTasa_anual());
        simDTO.setCapitalizacion_m(simulacion.getCapitalizacion_m());
        simDTO.setMeses_gracia(simulacion.getMeses_gracia());
        simDTO.setBfh_aplica(simulacion.isBfh_aplica());
        simDTO.setBfh_monto(simulacion.getBfh_monto());
        simDTO.setMonto_credito(simulacion.getMonto_credito());
        simDTO.setTasa_efectiva_mensual(simulacion.getTasa_efectiva_mensual());
        simDTO.setEstado(simulacion.getEstado());
        simDTO.setFecha_simulacion(simulacion.getFecha_simulacion());

        IndicadoresFinancierosDTO indDTO = new IndicadoresFinancierosDTO();
        indDTO.setVan(indicadores.getVan());
        indDTO.setTir_mensual(indicadores.getTir_mensual());
        indDTO.setTir_anual(indicadores.getTir_anual());
        indDTO.setTcea_anual(indicadores.getTcea_anual());
        indDTO.setTotal_intereses(indicadores.getTotal_intereses());
        indDTO.setTotal_costos(indicadores.getTotal_costos());
        indDTO.setTotal_pagado(indicadores.getTotal_pagado());

        indDTO.setTotal_amortizacion(indicadores.getTotal_amortizacion());
        indDTO.setTotal_seguro_desgravamen(indicadores.getTotal_seguro_desgravamen());
        indDTO.setTotal_seguro_riesgo(indicadores.getTotal_seguro_riesgo());
        indDTO.setTotal_comisiones(indicadores.getTotal_comisiones());
        indDTO.setTotal_portes(indicadores.getTotal_portes());
        indDTO.setTotal_gastos_adm(indicadores.getTotal_gastos_adm());
        indDTO.setTasa_descuento_anual(indicadores.getTasa_descuento_anual());

        SimulacionCreditoResponse resp = new SimulacionCreditoResponse();
        resp.setSimulacion(simDTO);
        resp.setIndicadores(indDTO);
        resp.setPlanPago(planDTOs);

        return resp;
    }

    // =========================================================
    // 🔹 NUEVO: Obtener simulación completa por ID
    // =========================================================
    @Override
    public SimulacionCreditoResponse obtenerSimulacionCompleta(int simulacionId) {

        // 1. Obtener la simulación
        SimulacionesCredito simulacion = simulacionesRepo.findById(simulacionId)
                .orElseThrow(() -> new RuntimeException("Simulación no encontrada con ID: " + simulacionId));

        // 2. Obtener los indicadores
        IndicadoresFinancieros indicadores = simulacion.getIndicadoresFinancieros();
        if (indicadores == null) {
            throw new RuntimeException("No se encontraron indicadores para la simulación ID: " + simulacionId);
        }

        // 3. Obtener el plan de pagos
        List<PlanesPago> planesPago = planesRepo.findBySimulacionId(simulacionId);
        if (planesPago.isEmpty()) {
            throw new RuntimeException("No se encontró plan de pagos para la simulación ID: " + simulacionId);
        }

        // 4. Construir DTO de simulación
        SimulacionCreditoDTO simDTO = new SimulacionCreditoDTO();
        simDTO.setSimulacion_id(simulacion.getSimulacion_id());
        simDTO.setCuota_inicial_pct(simulacion.getCuota_inicial_pct());
        simDTO.setCuota_inicial_monto(simulacion.getCuota_inicial_monto());
        simDTO.setPlazo_meses(simulacion.getPlazo_meses());
        simDTO.setGastos_adicionales(simulacion.getGastos_adicionales());
        simDTO.setTasa_anual(simulacion.getTasa_anual());
        simDTO.setCapitalizacion_m(simulacion.getCapitalizacion_m());
        simDTO.setMeses_gracia(simulacion.getMeses_gracia());
        simDTO.setBfh_aplica(simulacion.isBfh_aplica());
        simDTO.setBfh_monto(simulacion.getBfh_monto());
        simDTO.setMonto_credito(simulacion.getMonto_credito());
        simDTO.setTasa_efectiva_mensual(simulacion.getTasa_efectiva_mensual());
        simDTO.setEstado(simulacion.getEstado());
        simDTO.setFecha_simulacion(simulacion.getFecha_simulacion());

        // 5. Construir DTO de indicadores
        IndicadoresFinancierosDTO indDTO = new IndicadoresFinancierosDTO();
        indDTO.setVan(indicadores.getVan());
        indDTO.setTir_mensual(indicadores.getTir_mensual());
        indDTO.setTir_anual(indicadores.getTir_anual());
        indDTO.setTcea_anual(indicadores.getTcea_anual());
        indDTO.setTotal_intereses(indicadores.getTotal_intereses());
        indDTO.setTotal_costos(indicadores.getTotal_costos());
        indDTO.setTotal_pagado(indicadores.getTotal_pagado());
        indDTO.setTotal_amortizacion(indicadores.getTotal_amortizacion());
        indDTO.setTotal_seguro_desgravamen(indicadores.getTotal_seguro_desgravamen());
        indDTO.setTotal_seguro_riesgo(indicadores.getTotal_seguro_riesgo());
        indDTO.setTotal_comisiones(indicadores.getTotal_comisiones());
        indDTO.setTotal_portes(indicadores.getTotal_portes());
        indDTO.setTotal_gastos_adm(indicadores.getTotal_gastos_adm());
        indDTO.setTasa_descuento_anual(indicadores.getTasa_descuento_anual());

        // 6. Construir DTOs de plan de pagos
        List<PlanPagoDTO> planDTOs = new ArrayList<>();
        for (PlanesPago plan : planesPago) {
            PlanPagoDTO dto = new PlanPagoDTO();
            dto.setPeriodo(plan.getPeriodo());
            dto.setEstado(plan.getEstado());
            dto.setSaldo_inicial(plan.getSaldo_inicial());
            dto.setInteres_periodo(plan.getInteres_periodo());
            dto.setAmortizacion_periodo(plan.getAmortizacion_periodo());
            dto.setSeguro_desgravamen(plan.getSeguro_desgravamen());
            dto.setSeguro_riesgo(plan.getSeguro_riesgo());
            dto.setComision_periodica(plan.getComision_periodica());
            dto.setPortes_periodo(plan.getPortes_periodo());
            dto.setGastos_adm_periodo(plan.getGastos_adm_periodo());
            dto.setCostos_periodo(plan.getCostos_periodo());
            dto.setPrepago(plan.getPrepago());
            dto.setCuota_periodo(plan.getCuota_periodo());
            dto.setSaldo_final(plan.getSaldo_final());

            planDTOs.add(dto);
        }

        // 7. Construir respuesta completa
        SimulacionCreditoResponse response = new SimulacionCreditoResponse();
        response.setSimulacion(simDTO);
        response.setIndicadores(indDTO);
        response.setPlanPago(planDTOs);

        return response;
    }
}