package service;

import exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Equipo;
import org.springframework.stereotype.Service;
import repository.EquipoRepository;
import repository.TorneoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EquipoServiceImpl implements EquipoService {

    private final EquipoRepository repository;
    private final TorneoRepository torneoRepository;

    @Override
    public List<Equipo> obtenerTodos() {
        log.info("Recuperando listado de equipos");
        return repository.obtenerTodos();
    }

    @Override
    public Equipo buscarPorId(Long id) {
        log.info("Buscando equipo con id={}", id);
        return repository.buscarPorId(id);
    }

    @Override
    public void guardar(Equipo equipo) {
        log.info("Intentando guardar equipo: {}", equipo.getNombre());

        if (repository.existePorNombre(equipo.getNombre())) {
            log.warn("Nombre de equipo duplicado: {}", equipo.getNombre());
            throw new BusinessException("Ya existe un equipo con ese nombre");
        }

        repository.guardar(equipo);
        log.info("Equipo guardado correctamente");
    }



    @Override
    public void actualizar(Equipo equipo) {
        log.info("Intentando actualizar equipo id={}", equipo.getId());

        if (repository.existePorNombreDistintoId(equipo.getNombre(), equipo.getId())) {
            log.warn("Nombre duplicado al actualizar equipo id={}", equipo.getId());
            throw new BusinessException("Ya existe otro equipo con ese nombre");
        }

        repository.actualizar(equipo);
        log.info("Equipo actualizado correctamente");
    }

    @Override
    public void borrar(Long id) {
        log.info("Intentando borrar equipo id={}", id);

        if (torneoRepository.existeEquipoCampeon(id)) {
            log.warn("No se puede borrar equipo id={} porque está asociado a un torneo", id);
            throw new BusinessException("No se puede borrar el equipo porque aparece como campeón en un torneo");
        }

        repository.borrar(id);
        log.info("Equipo borrado correctamente");
    }
}

