package upc.edu.pe.finanzas.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "CatTipoTasa")
public class CatTipoTasa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tipo_tasa_id;

    @Column(name = "codigo", length = 35, nullable = false)
    private String codigo;

    @Column(name = "descripcion", length = 100, nullable = false)
    private String descripcion;

    public CatTipoTasa() {}

    public CatTipoTasa(int tipo_tasa_id, String codigo, String descripcion) {
        this.tipo_tasa_id = tipo_tasa_id;
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public int getTipo_tasa_id() {
        return tipo_tasa_id;
    }

    public void setTipo_tasa_id(int tipo_tasa_id) {
        this.tipo_tasa_id = tipo_tasa_id;
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
