# 🏠 Simulador de Créditos Hipotecarios MiVivienda

Sistema web integral para la simulación y cálculo de créditos hipotecarios del programa **Nuevo Crédito MiVivienda** con opción de **Bono Familiar Habitacional (Techo Propio)**, respetando todas las disposiciones del Sistema Financiero Peruano.

---

## 🎯 Objetivo

Desarrollar una aplicación web que simplifique el cálculo y la proyección de planes de financiamiento hipotecario, permitiendo a empresas inmobiliarias y clientes finales obtener simulaciones precisas, transparentes y conformes con la normativa de la Superintendencia de Banca, Seguros y AFP (SBS).

---

## ✨ Características Principales

- ✅ **Cálculo preciso del método francés** vencido ordinario (30/360)
- ✅ **Múltiples entidades financieras** con tasas y condiciones parametrizadas
- ✅ **Soporte para Bono Familiar Habitacional (BFH)** con validación automática
- ✅ **Conversión de tasas** (nominal ↔ efectiva) automática y precisa
- ✅ **Períodos de gracia** (total y parcial) según normativa
- ✅ **Indicadores financieros**: VAN, TIR, TCEA
- ✅ **Planes de pago detallados** mes a mes con desglose de interés y amortización
- ✅ **Soporte multimoneda** (PEN / USD) con tipo de cambio configurable
- ✅ **Autenticación segura** con JWT tokens
- ✅ **Base de datos normalizada** con 12 tablas relacionales

---

## 📊 Técnicas Utilizadas

### Backend
- **Framework**: Spring Boot
- **Base de Datos**: PostgreSQL
- **Autenticación**: JWT (JSON Web Tokens)
- **Validación**: Spring Validation
- **Matemáticas Financieras**: Implementación de fórmulas ISO 8601

### Frontend
- **Framework**: Angular 15+
- **Estilos**: CSS3 + Tailwind CSS
- **Gestión de Estado**: RxJS
- **Diseño**: Figma (mockups)
- **Consumo API**: HttpClientModule

### Infraestructura
- **Control de Versiones**: Git / GitHub
- **Comunicación**: API REST con JSON
- **CORS**: Habilitado para múltiples orígenes

---

## 📦 Instalación

### Requisitos Previos
- **Node.js** 16+ (para Angular)
- **Java** 11+
- **PostgreSQL** 12+
- **Git**

### Pasos de Instalación

#### 1. Clonar el repositorio
```bash
git clone https://github.com/tu-usuario/simulador-mivivienda.git
cd simulador-mivivienda
```

#### 2. Configurar Backend (Spring Boot)
```bash
cd backend

# Crear archivo application.properties
cp src/main/resources/application.properties.example src/main/resources/application.properties

# Editar configuración de base de datos
# spring.datasource.url=jdbc:postgresql://localhost:5432/finanzas
# spring.datasource.username=tu_usuario
# spring.datasource.password=tu_contraseña
# server.port=8081 -> esto puede cambiar

# Compilar y ejecutar
mvn clean install
mvn spring-boot:run
```

#### 3. Configurar Frontend (Angular)
```bash
cd frontend

# Instalar dependencias
npm install

# Configurar variables de entorno
cp src/environments/environment.example.ts src/environments/environment.ts

# Ejecutar servidor de desarrollo
ng serve

# Acceder a http://localhost:4200
```

---

## 📖 Uso

### Flujo de Usuario

1. **Registro/Inicio de Sesión**: Acceder con credenciales de la inmobiliaria
2. **Registrar Cliente**: Ingresar datos socioeconómicos del solicitante
3. **Registrar Inmueble**: Agregar características y precio de la propiedad
4. **Simular Crédito**: Seleccionar cliente, inmueble y entidad financiera
5. **Configurar Parámetros**: 
   - Cuota inicial (%)
   - Plazo (meses)
   - Tipo de tasa (nominal/efectiva)
   - Período de gracia (opcional)
   - Bono Techo Propio (opcional)
6. **Generar Plan de Pagos**: Ver cronograma completo y indicadores financieros

### Ejemplo de Simulación

```
Cliente: Juan Pérez García
Ingreso Mensual: S/. 3,500.00
Inmueble: Departamento Villa Nueva
Precio: S/. 157,950.00
Cuota Inicial: 10% = S/. 15,795.00
Bono BFH: S/. 25,000.00
Plazo: 180 meses
Tasa (TEA): 11.02%
Resultado:
├─ Monto a Financiar: S/. 117,155.00
├─ Cuota Mensual: S/. 1,630.00
├─ Total Intereses: S/. 176,245.00
├─ Total Pagado: S/. 293,400.00
├─ TCEA: 12.15%
└─ VAN (8%): S/. -15,340.50
```

---

## 🔐 Seguridad

- **Contraseñas**: Hasheadas con bcrypt
- **Autenticación**: JWT con expiración configurable
- **HTTPS**: Recomendado en producción
- **CORS**: Restringido a dominios autorizados
- **SQL Injection**: Prevenida con prepared statements
- **Validación**: Frontend y Backend

---

## 📐 Fórmulas Financieras Implementadas

### Método Francés
```
C = P × [r(1+r)^n] / [(1+r)^n - 1]
```
Donde:
- C = Cuota mensual
- P = Capital a financiar
- r = Tasa efectiva mensual
- n = Número de períodos

### Conversión de Tasas
```
TEA = (1 + i_m)^12 - 1
TEM = (1 + TEA)^(1/12) - 1
```

### Valor Actual Neto (VAN)
```
VAN = -P + Σ [CF_t / (1 + d)^t]
```

### Tasa Interna de Retorno (TIR)
```
0 = -P + Σ [CF_t / (1 + TIR)^t]
```

### TCEA (Tasa de Costo Efectivo Anual)
```
TCEA = (1 + TIR_mensual)^12 - 1
```

---

## 📋 Especificaciones del Sistema

### Datos de Entrada

| Parámetro | Tipo | Rango | Ejemplo |
|-----------|------|-------|---------|
| Precio Vivienda | Decimal | 50,000 - 500,000 | 157,950.00 |
| Cuota Inicial (%) | Decimal | 5% - 50% | 10.00% |
| Plazo (meses) | Integer | 60 - 360 | 180 |
| Tasa Anual (%) | Decimal | 7% - 20% | 11.02% |
| Monto BFH | Decimal | 0 - 128,400 | 25,000.00 |

### Datos de Salida

- **Plan de Pagos**: Tabla con 180 filas (una por mes)
- **Indicadores**: VAN, TIR, TCEA, Total Intereses
- **Resumen**: Monto financiado, cuota fija, total pagado

---

## 🏦 Entidades Financieras Soportadas

| Banco | TEA (S/) | TCEA (S/) | BFH | Plazo Máx |
|-------|----------|-----------|-----|-----------|
| BBVA | 7.0% - 8.8% | 7.7% - 9.5% | ✓ | 360 |
| BCP | 8.2% - 10.1% | 8.9% - 10.8% | ✓ | 300 |
| Interbank | 7.2% - 9.1% | 7.9% - 9.8% | ✓ | 360 |
| Scotiabank | 7.8% - 9.5% | 8.5% - 10.2% | ✗ | 300 |
| Caja Arequipa | 9.1% - 11.2% | 9.8% - 11.9% | ✓ | 240 |

---

## 📂 Estructura del Proyecto

```
simulador-mivivienda/
├── backend/
│   ├── src/
│   │   ├── main/java/upc/edu/pe/finanzas/
│   │   │   ├── controllers/
│   │   │   ├── dtos/
│   │   │   ├── entities/
│   │   │   ├── repositories/
│   │   │   ├── security/
│   │   │   ├── servicesimplements/
│   │   │   └── servicesinterfaces/
│   │   └── resources/
│   │       └── application.properties
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── app/
│   │   │   ├── components/
│   │   │   ├── guards/
│   │   │   ├── interceptors/
│   │   │   ├── models/
│   │   │   ├── modules/
│   │   │   └── services/
│   │   └── assets/
│   ├── angular.json
│   └── package.json
└── README.md
```

---

### Casos de Prueba

1. **Gracia Total**: Capitalización de intereses durante 2 meses
2. **Gracia Parcial**: Pago de intereses sin amortización
3. **Conversión de Tasas**: Nominal ↔ Efectiva
4. **BFH Automático**: Validación de elegibilidad por ingreso
5. **Multimoneda**: Conversión PEN ↔ USD

---

## 🛠️ Configuración Base de Datos

```sql
-- Crear base de datos
CREATE DATABASE finanzas;

```

---

### Contribuir
1. Fork el repositorio
2. Crear rama feature: `git checkout -b feature/nueva-funcionalidad`
3. Commit: `git commit -m "Agregar nueva funcionalidad"`
4. Push: `git push origin feature/nueva-funcionalidad`
5. Pull Request

---

## 📜 Normativa

Este sistema cumple con:

- ✓ **Ley N.° 27829**: Bono Familiar Habitacional
- ✓ **Ley N.° 28579**: Financiamiento e Inclusión para la Vivienda
- ✓ **Ley N.° 29571**: Código de Protección del Consumidor
- ✓ **Disposiciones SBS**: Transparencia y cálculo de tasas
- ✓ **Método Francés**: Base 30/360 (ISO 8601)

---

## 🔗 Enlaces Útiles

- [Documentación MiVivienda](https://www.mivivienda.com.pe)
- [SBS Perú](https://www.sbs.gob.pe)

---

**Última actualización**: Diciembre 2025  
**Estado**: En Producción ✅
