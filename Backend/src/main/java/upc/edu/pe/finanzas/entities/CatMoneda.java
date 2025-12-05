package upc.edu.pe.finanzas.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "CatMoneda")
public class CatMoneda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int moneda_id;

    @Column(name = "codigo", length = 35, nullable = false)
    private String codigo;

    @Column(name = "descripcion", length = 100, nullable = false)
    private String descripcion;

    public CatMoneda() {}

    public CatMoneda(int moneda_id, String codigo, String descripcion) {
        this.moneda_id = moneda_id;
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public int getMoneda_id() {
        return moneda_id;
    }

    public void setMoneda_id(int moneda_id) {
        this.moneda_id = moneda_id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
