package upc.edu.pe.finanzas.dtos;

import jakarta.persistence.*;

public class CatTipoGraciaDTO {

    private int tipo_gracia_id;

    private String codigo;

    private String descripcion;

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
