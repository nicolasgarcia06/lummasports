package service;



import model.Torneo;
import model.TorneoConEquipo;

import java.util.List;

public interface TorneoService {

    List<TorneoConEquipo> obtenerTorneosConEquipoCampeon();

    Torneo buscarPorId(Long id);

    void guardar(Torneo torneo);

    void actualizar(Torneo torneo);

    void borrar(Long id);
}
