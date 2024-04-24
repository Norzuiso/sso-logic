package com.act6logic.logic;

import com.act6logic.logic.Bloqueados.ProcesosBloqueadosService;
import com.act6logic.logic.Ejecucion.ProcesoEjecucionService;
import com.act6logic.logic.Espera.ProcesosEsperaService;
import com.act6logic.obj.Proceso;
import com.act6logic.obj.ProcesosBloqueados;
import com.act6logic.obj.ProcessResult;
import com.act6logic.obj.ProcessTime;
import com.act6logic.processLogic.GenerateProcessComponent;
import com.act6logic.utils.ProcessTimeUtils;
import com.act6logic.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class GeneralFunctionalityComponentImpl implements GeneralFunctionalityComponent {

    @Autowired
    private GenerateProcessComponent generateProcessComponent;

    @Autowired
    private TimeUtils timeUtils;

    @Autowired
    private ProcesosBloqueadosService proBloqueadoService;

    @Autowired
    private ProcesosEsperaService procesosEsperaService;

    @Autowired
    private ProcesoEjecucionService procesoEjecucionService;

    @Autowired
    private ProcessTimeUtils procesoTimeUtils;

    private String previousState = "C";


    @Override
    public ProcessResult resolveProcess(ProcessResult pr) {
        if (pr.getState().isEmpty()|| pr.getState().equals("undefined")){
            pr.setState("C");
        }
        if (pr.getProcesosEspera().isEmpty()
                && pr.getProcesosBloqueados().getProcesosBloqueados().isEmpty()
                && pr.getProcesoEnEjecucion().getId().isEmpty()) {
            return pr;
        }
        if (procesoEjecucionService.isInvalid(
                pr.getProcesoEnEjecucion()
        ) && pr.getProcesosBloqueados().getProcesosBloqueados().isEmpty()) {
            return pr;
        }
        boolean isStatePorB = Objects.equals(pr.getState(), "P") || Objects.equals(pr.getState(), "B");
        if (isStatePorB) {
            previousState = pr.getState();
            return pr;
        }
        boolean isPreviousStatePorB = previousState.equals("P") || previousState.equals("B");
        boolean isPrState = pr.getState().equals("C");
        if (isPreviousStatePorB && isPrState) {
            previousState = "C";
            pr.setState("C");
        } else if (!isPrState) {
            pr.setState(previousState);
            return pr;
        }


        String state = pr.getState();

        Integer contadorGlobal = pr.getContadorGlobal();
        //Actualizamos tiempo
        contadorGlobal++;

        ProcessTime tiempoActual = timeUtils.processSeconds(contadorGlobal);

        Integer processInMemory = pr.getProcessInMemory();

        List<Proceso> procesosNuevos = pr.getProcesosNuevos();
        List<Proceso> procesosEspera = pr.getProcesosEspera();

        //Procesos bloqueados
        ProcesosBloqueados procesosBloqueados = pr.getProcesosBloqueados();
        List<Proceso> procesosBloqueadosList = procesosBloqueados.getProcesosBloqueados();

        //Proceso en ejecución
        Proceso procesoEnEjecucion = pr.getProcesoEnEjecucion();

        //Proceso terminado
        List<Proceso> procesoTerminado = pr.getProcesoTerminado();

        if (pr.getState().equals("N")){
            state = "C";
            String lastId = procesosNuevos.getLast().getId();
            Proceso newProceso = generateProcessComponent.generateProceso(lastId);
            procesosNuevos.addLast(newProceso);
        }
        if (Objects.equals(pr.getState(), "E")) {
            state = "C";
            procesosBloqueadosList.addLast(procesoEnEjecucion);
            procesoEnEjecucion = getProcesoNewProcesoEjecucion(procesosEspera, tiempoActual);
        }
        if (Objects.equals(pr.getState(), "W")) {
            state = "C";
            procesoEjecucionService.finisProcessError(procesoEnEjecucion, tiempoActual, "Error");
            procesoTerminado.addFirst(procesoEnEjecucion);
            processInMemory--;
            procesoEnEjecucion = getProcesoNewProcesoEjecucion(procesosEspera, tiempoActual);
        }

        // Procesado de bloqueados
        proBloqueadoService.updateProcesosBloqueados(procesosBloqueadosList, procesosEspera);
        if (procesoEjecucionService.isInvalid(procesoEnEjecucion)
                && !procesosEspera.isEmpty()) {
            procesoEnEjecucion = getProcesoNewProcesoEjecucion(procesosEspera, tiempoActual);
        }
        procesosBloqueados = new ProcesosBloqueados(procesosBloqueadosList, procesosBloqueados.getTenemosBloqueados());

        // Procesado de espera
        procesosEsperaService.updateProcesosEspera(procesosEspera);


        // Proceso en ejecucion
        if (procesoEjecucionService.isInvalid(procesoEnEjecucion)) {
            return generateResult(state, contadorGlobal, processInMemory, procesosNuevos, procesosEspera, procesosBloqueados, procesoEnEjecucion, procesoTerminado);
        }

        procesoEjecucionService.updateProcesoEnEjecucion(procesoEnEjecucion);


        boolean isProcessReady = procesoEjecucionService.isProcessDone(procesoEnEjecucion);
        if (isProcessReady) {
            procesoEjecucionService.finisProcess(procesoEnEjecucion, tiempoActual);
            procesoTerminado.addFirst(procesoEnEjecucion);
            processInMemory--;

            procesoEnEjecucion = getProcesoNewProcesoEjecucion(procesosEspera, tiempoActual);
        }

        procesosBloqueados = new ProcesosBloqueados(procesosBloqueadosList, procesosBloqueados.getTenemosBloqueados());
        if (procesosNuevos.isEmpty()) {
            return generateResult(state, contadorGlobal, processInMemory, procesosNuevos, procesosEspera, procesosBloqueados, procesoEnEjecucion, procesoTerminado);
        }
        if ((processInMemory < 4) && !procesosNuevos.isEmpty()) {
            Proceso proceso = procesosNuevos.removeFirst();
            procesoTimeUtils.calculateTiemposLlegada(proceso, tiempoActual);
            procesosEspera.add(proceso);
            processInMemory++;
        }


        return generateResult(state, contadorGlobal, processInMemory, procesosNuevos, procesosEspera, procesosBloqueados, procesoEnEjecucion, procesoTerminado);
    }

    private Proceso getProcesoNewProcesoEjecucion(List<Proceso> procesosEspera, ProcessTime tiempoActual) {
        Proceso procesoEnEjecucion;
        if (!procesosEspera.isEmpty()) {
            procesoEnEjecucion = procesosEspera.removeFirst();
            procesoTimeUtils.calculateTiempoRespuesta(procesoEnEjecucion, tiempoActual);
        } else {
            procesoEnEjecucion = new Proceso();
        }
        return procesoEnEjecucion;
    }

    private static ProcessResult generateResult(String state, Integer contadorGlobal, Integer processInMemory, List<Proceso> procesosNuevos, List<Proceso> procesosEspera, ProcesosBloqueados procesosBloqueados, Proceso procesoEnEjecucion, List<Proceso> procesoTerminado) {
        return new ProcessResult(
                state,
                contadorGlobal,
                processInMemory,
                procesosNuevos,
                procesosEspera,
                procesosBloqueados,
                procesoEnEjecucion,
                procesoTerminado
        );
    }

    private void procesosFill(List<Proceso> procesosNuevos,
                              ProcesosBloqueados procesosBloqueados,
                              List<Proceso> procesosEspera, Integer processInMemory, Integer contadorGlobal) {
        if (processInMemory < 4) {
            if (!procesosNuevos.isEmpty()) {
                Proceso removeFirst = procesosNuevos.removeFirst();
                removeFirst.setTiempoLlegada(timeUtils.processSeconds(contadorGlobal));
                removeFirst.setLlegada(true);
                procesosEspera.add(removeFirst);
            }
        }

    }


}
