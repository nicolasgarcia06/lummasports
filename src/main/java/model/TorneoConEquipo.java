package model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TorneoConEquipo {
    private Long id;
    private String nombreTorneo;
    private String ciudad;
    private BigDecimal premio;
    private String nombreEquipo;
}
