

import controller.EquipoController;

import model.Equipo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import service.EquipoService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EquipoController.class)
class EquipoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EquipoService service;

    @Test
    void debeMostrarLaVistaEquipos() throws Exception {
        given(service.obtenerTodos()).willReturn(List.of(
                new Equipo(1L, "KOI", "LoL", "España")
        ));

        mockMvc.perform(get("/equipos"))
                .andExpect(status().isOk())
                .andExpect(view().name("equipos"))
                .andExpect(model().attributeExists("equipos"));
    }
}
