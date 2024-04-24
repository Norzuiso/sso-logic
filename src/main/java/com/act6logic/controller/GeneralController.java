package com.act6logic.controller;

import com.act6logic.logic.GeneralFunctionalityComponent;
import com.act6logic.obj.Proceso;
import com.act6logic.obj.ProcessResult;
import com.act6logic.obj.ProcessTime;
import com.act6logic.processLogic.GenerateProcessComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

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
        processResult.setProcesoEnEjecucion(procesoList.removeFirst());
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

        processResult.setProcessInMemory(4);

        processResult.setProcesosNuevos(procesoList);
        return processResult;
    }

    @PostMapping("/result")
    public ProcessResult resolveProcess(@RequestBody ProcessResult processResult) throws InterruptedException {
        return generalFuncionality.resolveProcess(processResult);
    }

    @PostMapping("/result/{state}")
    public ProcessResult resolveProcessWithState(@PathVariable String state,@RequestBody ProcessResult processResult) throws InterruptedException {
        processResult.setState(state);
        return generalFuncionality.resolveProcess(processResult);
    }

}
