package model;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Torneo {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La ciudad es obligatoria")
    private String ciudad;

    @NotNull(message = "El premio es obligatorio")
    @DecimalMin(value = "0.01", message = "El premio debe ser mayor que 0")
    private BigDecimal premio;

    @NotNull(message = "Debes seleccionar un equipo campeón")
    private Long equipoCampeonId;
}

