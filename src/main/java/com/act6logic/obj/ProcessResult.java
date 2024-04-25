package com.act6logic.obj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessResult {
    private String state = "C";
    private Integer contadorGlobal = 0;
    private Integer processInMemory = 0;
    private List<Proceso> procesosNuevos = new ArrayList<>();
    private List<Proceso> procesosEspera = new ArrayList<>();
    private ProcesosBloqueados procesosBloqueados = new ProcesosBloqueados();
    private Proceso procesoEnEjecucion = new Proceso();
    private List<Proceso> procesoTerminado = new ArrayList<>();
    private ProcessTime quantumInProgress = new ProcessTime();
    private Integer quantum;
}
