package upc.edu.pe.finanzas.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Clientes")
public class Clientes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cliente_id;

    @Column(name = "nombres", length = 100, nullable = false)
    private String nombres;

    @Column(name = "apellidos", length = 100, nullable = false)
    private String apellidos;

    @Column(name = "dni", length = 8, nullable = false)
    private String dni;

    @Column(name = "correo", length = 100, nullable = false)
    private String correo;

    @Column(name = "telefono", length = 100, nullable = false)
    private String telefono;

    @Column(name = "direccion", length = 100, nullable = false)
    private String direccion;

    @Column(name = "ingresoMensual", nullable = false)
    private double ingreso_mensual;

    @Column(name = "estado_civil", length = 100, nullable = false)
    private String estado_civil;

    @Column(name = "dependientes", length = 100, nullable = false)
    private String dependientes;

    @Column(name = "residencia_actual", length = 100, nullable = false)
    private String residencia_actual;


    public Clientes() {}

    public Clientes( int cliente_id, String nombres, String apellidos, String dni, String correo, String telefono, String direccion, double ingreso_mensual, String estado_civil, String dependientes, String residencia_actual) {
        this.cliente_id = cliente_id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.ingreso_mensual = ingreso_mensual;
        this.estado_civil = estado_civil;
        this.dependientes = dependientes;
        this.residencia_actual = residencia_actual;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getIngreso_mensual() {
        return ingreso_mensual;
    }

    public void setIngreso_mensual(double ingreso_mensual) {
        this.ingreso_mensual = ingreso_mensual;
    }

    public String getEstado_civil() {
        return estado_civil;
    }

    public void setEstado_civil(String estado_civil) {
        this.estado_civil = estado_civil;
    }

    public String getDependientes() {
        return dependientes;
    }

    public void setDependientes(String dependientes) {
        this.dependientes = dependientes;
    }

    public String getResidencia_actual() {
        return residencia_actual;
    }

    public void setResidencia_actual(String residencia_actual) {
        this.residencia_actual = residencia_actual;
    }
}
