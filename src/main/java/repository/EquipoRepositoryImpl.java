package repository;



import lombok.AllArgsConstructor;
import model.Equipo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class EquipoRepositoryImpl implements EquipoRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<Equipo> obtenerTodos() {
        String sql = "SELECT * FROM equipos ORDER BY id";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Equipo(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("juego"),
                        rs.getString("pais")
                )
        );
    }

    public Equipo buscarPorId(Long id) {
        String sql = "SELECT * FROM equipos WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new Equipo(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("juego"),
                        rs.getString("pais")
                ), id
        );
    }

    @Override
    public void guardar(Equipo equipo) {
        String sql = "INSERT INTO equipos (nombre, juego, pais) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, equipo.getNombre(), equipo.getJuego(), equipo.getPais());
    }



    public void actualizar(Equipo equipo) {
        String sql = "UPDATE equipos SET nombre = ?, juego = ?, pais = ? WHERE id = ?";
        jdbcTemplate.update(sql, equipo.getNombre(), equipo.getJuego(), equipo.getPais(), equipo.getId());
    }

    public void borrar(Long id) {
        String sql = "DELETE FROM equipos WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public boolean existePorNombre(String nombre) {
        String sql = "SELECT COUNT(*) FROM equipos WHERE LOWER(nombre) = LOWER(?)";
        Integer total = jdbcTemplate.queryForObject(sql, Integer.class, nombre);
        return total != null && total > 0;
    }

    public boolean existePorNombreDistintoId(String nombre, Long id) {
        String sql = "SELECT COUNT(*) FROM equipos WHERE LOWER(nombre) = LOWER(?) AND id <> ?";
        Integer total = jdbcTemplate.queryForObject(sql, Integer.class, nombre, id);
        return total != null && total > 0;
    }
}

