package service;

import exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Torneo;
import model.TorneoConEquipo;
import org.springframework.stereotype.Service;
import repository.EquipoRepository;
import repository.TorneoRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TorneoService {

    private final TorneoRepository repository;
    private final EquipoRepository equipoRepository;

    public List<TorneoConEquipo> obtenerTorneosConEquipoCampeon() {
        log.info("Recuperando torneos con equipo campeón");
        return repository.obtenerTorneosConEquipoCampeon();
    }

    public Torneo buscarPorId(Long id) {
        log.info("Buscando torneo id={}", id);
        return repository.buscarPorId(id);
    }

    public void guardar(Torneo torneo) {
        log.info("Intentando guardar torneo: {}", torneo.getNombre());
        validarReglasDeNegocio(torneo, false);
        repository.guardar(torneo);
        log.info("Torneo guardado correctamente");
    }

    public void actualizar(Torneo torneo) {
        log.info("Intentando actualizar torneo id={}", torneo.getId());
        validarReglasDeNegocio(torneo, true);
        repository.actualizar(torneo);
        log.info("Torneo actualizado correctamente");
    }

    public void borrar(Long id) {
        log.info("Borrando torneo id={}", id);
        repository.borrar(id);
        log.info("Torneo borrado correctamente");
    }

    private void validarReglasDeNegocio(Torneo torneo, boolean actualizacion) {
        if (torneo.getPremio().compareTo(new BigDecimal("1000")) < 0) {
            log.warn("Premio insuficiente para torneo {}", torneo.getNombre());
            throw new BusinessException("El premio mínimo de un torneo debe ser 1000");
        }

        try {
            equipoRepository.buscarPorId(torneo.getEquipoCampeonId());
        } catch (Exception e) {
            log.warn("Equipo campeón no encontrado: {}", torneo.getEquipoCampeonId());
            throw new BusinessException("El equipo campeón seleccionado no existe");
        }

        boolean duplicado = actualizacion
                ? repository.existePorNombreYCiudadDistintoId(torneo.getNombre(), torneo.getCiudad(), torneo.getId())
                : repository.existePorNombreYCiudad(torneo.getNombre(), torneo.getCiudad());

        if (duplicado) {
            log.warn("Torneo duplicado: {} - {}", torneo.getNombre(), torneo.getCiudad());
            throw new BusinessException("Ya existe un torneo con ese nombre en esa ciudad");
        }
    }
}

