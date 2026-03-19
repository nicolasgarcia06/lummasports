package service;





import model.Equipo;

import java.util.List;

public interface EquipoService {

    List<Equipo> obtenerTodos();

    Equipo buscarPorId(Long id);

    void guardar(Equipo equipo);

    void actualizar(Equipo equipo);

    void borrar(Long id);
}

