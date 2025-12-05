package upc.edu.pe.finanzas.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "CatTipoGracia")
public class CatTipoGracia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tipo_gracia_id;

    @Column(name = "codigo", length = 35, nullable = false)
    private String codigo;

    @Column(name = "descripcion", length = 100, nullable = false)
    private String descripcion;

    public CatTipoGracia() {}

    public CatTipoGracia(int tipo_gracia_id, String codigo, String descripcion) {
        this.tipo_gracia_id = tipo_gracia_id;
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public int getTipo_gracia_id() {
        return tipo_gracia_id;
    }

    public void setTipo_gracia_id(int tipo_gracia_id) {
        this.tipo_gracia_id = tipo_gracia_id;
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
