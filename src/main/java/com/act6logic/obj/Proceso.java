package com.act6logic.obj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Proceso {
    private String id = "";
    private String Operation = "";
    private String Result = "";
    private Boolean Llegada = false;
    private Boolean Respuesta = false;
    private ProcessTime TiempoMaxEstimado = new ProcessTime();
    private ProcessTime TiempoTranscurrido = new ProcessTime();
    private ProcessTime TiempoLlegada = new ProcessTime();
    private ProcessTime TiempoFinalizacion = new ProcessTime();
    private ProcessTime TiempoRetorno = new ProcessTime();
    private ProcessTime TiempoEspera = new ProcessTime();
    private ProcessTime TiempoRespuesta = new ProcessTime();
    private ProcessTime TiempoBloqueado = new ProcessTime();
    private ProcessTime TiempoBloqueadoParaSalir = new ProcessTime();
    private ProcessTime TiempoServicio = new ProcessTime();
    private ProcessTime TiempoRestantePorEjecutar = new ProcessTime();
    private Integer memorySize = 0;

}
