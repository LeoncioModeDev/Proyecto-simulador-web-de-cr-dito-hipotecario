package upc.edu.pe.finanzas.dtos;

import jakarta.persistence.Column;

public class CatTipoTasaDTO {

    private int tipo_tasa_id;

    private String codigo;

    private String descripcion;

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
