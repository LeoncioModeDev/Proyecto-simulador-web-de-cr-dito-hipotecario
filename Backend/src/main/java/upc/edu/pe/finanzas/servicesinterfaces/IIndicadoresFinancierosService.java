package upc.edu.pe.finanzas.servicesinterfaces;

import upc.edu.pe.finanzas.entities.IndicadoresFinancieros;

import java.util.List;

public interface IIndicadoresFinancierosService {
    public List<IndicadoresFinancieros> list();
    public void insert(IndicadoresFinancieros f);
    public IndicadoresFinancieros listId(int id);
    public void update(IndicadoresFinancieros f);
    public void delete(int id);
}
