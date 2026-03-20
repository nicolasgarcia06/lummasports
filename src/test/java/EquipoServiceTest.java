


import model.Equipo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.EquipoRepository;
import repository.TorneoRepository;
import service.EquipoService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EquipoServiceTest {

    @Mock
    private EquipoRepository repository;

    @Mock
    private TorneoRepository torneoRepository;

    @InjectMocks
    private EquipoService service;

    @Test
    void debeDevolverListaDeEquipos() {
        when(repository.obtenerTodos()).thenReturn(List.of(
                new Equipo(1L, "KOI", "LoL", "España")
        ));

        List<Equipo> resultado = service.obtenerTodos();

        assertEquals(1, resultado.size());
        assertEquals("KOI", resultado.get(0).getNombre());
    }
}
