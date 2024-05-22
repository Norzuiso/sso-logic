package com.act6logic.controller;

import com.act6logic.logic.GeneralFunctionalityComponent;
import com.act6logic.obj.ManejoMemoria;
import com.act6logic.obj.Proceso;
import com.act6logic.obj.ProcessResult;
import com.act6logic.obj.ProcessTime;
import com.act6logic.processLogic.GenerateProcessComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("")
public class GeneralController {

    @Autowired
    private GenerateProcessComponent generateProcess;

    @Autowired
    private GeneralFunctionalityComponent generalFuncionality;

    @GetMapping("/generateProcess/{processQuantity}")
    public ProcessResult generateProcess(@PathVariable Integer processQuantity) {
        processQuantity--;
        ProcessResult processResult = new ProcessResult();
        List<Proceso> procesoList = generateProcess.generateProcesos(processQuantity);
        int processInMemory = 1;
        Proceso procesoEnEjecucion = procesoList.removeFirst();
        procesoEnEjecucion.setRespuesta(true);
        processResult.setProcesoEnEjecucion(procesoEnEjecucion);
        List<Proceso> procesosEspera = new ArrayList<>();

        for (; processInMemory <= 3; processInMemory++) {
            if (procesoList.isEmpty()) {
                break;
            }
            Proceso removeFirst = procesoList.removeFirst();
            removeFirst.setTiempoLlegada(new ProcessTime(0, 0));
            removeFirst.setTiempoLlegada(new ProcessTime(0, 0));
            removeFirst.setLlegada(true);

            procesosEspera.add(removeFirst);
        }

        processResult.setProcesosEspera(procesosEspera);

        processResult.setProcessInMemory(4);

        processResult.setProcesosNuevos(procesoList);
        return processResult;
    }


    @GetMapping("/generateProcess/{processQuantity}/{quantum}")
    public ProcessResult generateProcess(@PathVariable Integer processQuantity, @PathVariable Integer quantum) {
        processQuantity--;
        ProcessResult processResult = new ProcessResult();
        List<Proceso> procesoList = generateProcess.generateProcesos(processQuantity);
        int processInMemory = 1;
        Proceso procesoEnEjecucion = procesoList.removeFirst();

        procesoEnEjecucion.setTiempoLlegada(new ProcessTime());
        procesoEnEjecucion.setLlegada(true);
        procesoEnEjecucion.setTiempoRespuesta(new ProcessTime());
        procesoEnEjecucion.setRespuesta(true);

        processResult.setProcesoEnEjecucion(procesoEnEjecucion);
        List<Proceso> procesosEspera = new ArrayList<>();

        for (; processInMemory <= 3; processInMemory++) {
            if (procesoList.isEmpty()) {
                break;
            }
            Proceso removeFirst = procesoList.removeFirst();
            removeFirst.setTiempoLlegada(new ProcessTime(0, 0));
            removeFirst.setLlegada(true);

            procesosEspera.add(removeFirst);
        }

        processResult.setProcesosEspera(procesosEspera);

        if (processQuantity < 4) {
            processResult.setProcessInMemory(processQuantity);
        } else {
            processResult.setProcessInMemory(4);
        }

        processResult.setProcesosNuevos(procesoList);
        processResult.setQuantum(quantum);
        return processResult;
    }

    @PostMapping("/result")
    public ProcessResult resolveProcess(@RequestBody ProcessResult processResult) throws InterruptedException {
        return generalFuncionality.resolveProcess(processResult);
    }

    // A partir de la act-8 se usa este, act-10 tambien manda el estado
    @PostMapping("/result/{state}")
    public ProcessResult resolveProcessWithState(@PathVariable String state, @RequestBody ProcessResult processResult) throws InterruptedException {
        processResult.setState(state);
        return generalFuncionality.resolveProcess(processResult);
    }

    @GetMapping("/create-memory")
    public ManejoMemoria createMemory() {
        ManejoMemoria manejoMemoria = new ManejoMemoria();
        Proceso proceso = new Proceso();
        proceso.setId("1-Holaaa");
        proceso.setMemorySize(26);
        manejoMemoria.addNewProcessToMemory(proceso);

        proceso = new Proceso();
        proceso.setId("2-Holaaa");
        proceso.setMemorySize(25);
        manejoMemoria.addNewProcessToMemory(proceso);

        proceso = new Proceso();
        proceso.setId("3-Holaaa");
        proceso.setMemorySize(26);
        manejoMemoria.addNewProcessToMemory(proceso);

        proceso = new Proceso();
        proceso.setId("4-Holaaa");
        proceso.setMemorySize(25);
        manejoMemoria.addNewProcessToMemory(proceso);

        Proceso proceso5 = new Proceso();
        proceso5.setId("5-Holaaa");
        proceso5.setMemorySize(26);
        manejoMemoria.addNewProcessToMemory(proceso5);

        proceso = new Proceso();
        proceso.setId("6-Holaaa");
        proceso.setMemorySize(25);
        manejoMemoria.addNewProcessToMemory(proceso);

        proceso = new Proceso();
        proceso.setId("7-Holaaa");
        proceso.setMemorySize(26);
        manejoMemoria.addNewProcessToMemory(proceso);

        manejoMemoria.removeProcessToMemory(proceso5);

        proceso = new Proceso();
        proceso.setId("8-Holaaa");
        proceso.setMemorySize(25);
        manejoMemoria.addNewProcessToMemory(proceso);

        proceso = new Proceso();
        proceso.setId("9-Holaaa");
        proceso.setMemorySize(10);
        manejoMemoria.addNewProcessToMemory(proceso);


        return manejoMemoria;
    }

}
