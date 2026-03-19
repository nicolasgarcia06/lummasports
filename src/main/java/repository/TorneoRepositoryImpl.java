package repository;

import lombok.AllArgsConstructor;
import model.Torneo;
import model.TorneoConEquipo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class TorneoRepositoryImpl implements TorneoRepository {

    private final JdbcTemplate jdbcTemplate;


    public List<Torneo> obtenerTodos() {
        String sql = "SELECT * FROM torneos ORDER BY id";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Torneo(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("ciudad"),
                        rs.getBigDecimal("premio"),
                        rs.getLong("equipo_campeon_id")
                )
        );
    }

    public Torneo buscarPorId(Long id) {
        String sql = "SELECT * FROM torneos WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new Torneo(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("ciudad"),
                        rs.getBigDecimal("premio"),
                        rs.getLong("equipo_campeon_id")
                ), id
        );
    }

    @Override
    public void guardar(Torneo torneo) {
        String sql = "INSERT INTO torneos (nombre, ciudad, premio, equipo_campeon_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                torneo.getNombre(),
                torneo.getCiudad(),
                torneo.getPremio(),
                torneo.getEquipoCampeonId());
    }



    public void actualizar(Torneo torneo) {
        String sql = "UPDATE torneos SET nombre = ?, ciudad = ?, premio = ?, equipo_campeon_id = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                torneo.getNombre(),
                torneo.getCiudad(),
                torneo.getPremio(),
                torneo.getEquipoCampeonId(),
                torneo.getId());
    }

    public void borrar(Long id) {
        String sql = "DELETE FROM torneos WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public boolean existeEquipoCampeon(Long equipoId) {
        String sql = "SELECT COUNT(*) FROM torneos WHERE equipo_campeon_id = ?";
        Integer total = jdbcTemplate.queryForObject(sql, Integer.class, equipoId);
        return total != null && total > 0;
    }

    public boolean existePorNombreYCiudad(String nombre, String ciudad) {
        String sql = "SELECT COUNT(*) FROM torneos WHERE LOWER(nombre) = LOWER(?) AND LOWER(ciudad) = LOWER(?)";
        Integer total = jdbcTemplate.queryForObject(sql, Integer.class, nombre, ciudad);
        return total != null && total > 0;
    }

    public boolean existePorNombreYCiudadDistintoId(String nombre, String ciudad, Long id) {
        String sql = "SELECT COUNT(*) FROM torneos WHERE LOWER(nombre) = LOWER(?) AND LOWER(ciudad) = LOWER(?) AND id <> ?";
        Integer total = jdbcTemplate.queryForObject(sql, Integer.class, nombre, ciudad, id);
        return total != null && total > 0;
    }

    public List<TorneoConEquipo> obtenerTorneosConEquipoCampeon() {
        String sql = """
                SELECT t.id,
                       t.nombre AS nombre_torneo,
                       t.ciudad,
                       t.premio,
                       e.nombre AS nombre_equipo
                FROM torneos t
                JOIN equipos e ON t.equipo_campeon_id = e.id
                ORDER BY t.id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new TorneoConEquipo(
                        rs.getLong("id"),
                        rs.getString("nombre_torneo"),
                        rs.getString("ciudad"),
                        rs.getBigDecimal("premio"),
                        rs.getString("nombre_equipo")
                )
        );
    }
}
