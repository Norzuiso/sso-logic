package com.act6logic.obj;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ManejoMemoria {
    private static final int MARCOS_TOTALES = 46;
    private static final int MARCOS_SO = 5;
    private static final int MARCO_SIZE = 5;
    private static final int MEMORY_SIZE = 230;

    private int marcosDisponibles = 0;
    private int marcosUtilizados = 0;
    private Marco lastFreeMarco;

    private Integer freeMemory = 0;
    private Integer usedMemory = 0;

    private Map<Integer, Marco> memoryMap = new HashMap<>();

    public ManejoMemoria() {
        for (int i = 1; i <= MARCOS_TOTALES; i++) {
            marcosDisponibles++;
            fillMemoryMap(i);
        }
        freeMemory = MEMORY_SIZE - usedMemory;
        calculateAllVariables();
    }

    private void calculateAllVariables() {
        calculateMarcosUtilizados();
        calculateMarcosDisponibles();
        calculateUsedMemory();
        calculateLastFreeMarco();
    }

    private void calculateMarcosDisponibles() {
        marcosDisponibles = MARCOS_TOTALES - marcosUtilizados;
    }

    private void calculateMarcosUtilizados() {
        marcosUtilizados = memoryMap.values().stream()
                .filter(memoria -> !memoria.getProcesoID().isBlank()).toList().size();
    }

    private void calculateUsedMemory() {
        usedMemory = memoryMap.values().stream().mapToInt(Marco::getSizeUsed).sum();
    }

    private void fillMemoryMap(int i) {

        if (i >= (MARCOS_TOTALES - MARCOS_SO)) {
            memoryMap.put(i, new Marco(i, MARCO_SIZE, "SO", MARCO_SIZE));
        } else {
            memoryMap.put(i, new Marco(i));
        }
    }

    public boolean isThereFreeMemory() {
        return marcosDisponibles > 0 && marcosUtilizados != MARCOS_TOTALES;
    }

    public boolean isMemoryFull() {
        return marcosDisponibles <= 0 && marcosUtilizados == MARCOS_TOTALES;
    }

    public boolean isMemoryEmpty() {
        return marcosDisponibles == MARCOS_TOTALES && marcosUtilizados == 0;
    }


    private void calculateLastFreeMarco() {

        Optional<Marco> lastFreeMarcoOptional = memoryMap.values().stream()
                .filter(marco -> marco.getSizeUsed() == 0)
                .findFirst();

        lastFreeMarco = lastFreeMarcoOptional.orElseGet(Marco::new);
    }

    public void removeProcessToMemory(Proceso proceso) {
        if (isMemoryEmpty()) {
            return;
        }
        String id = proceso.getId();
        memoryMap.values().stream()
                .filter(marco -> marco.getProcesoID().equals(id))
                .forEach(marco -> memoryMap.put(marco.getId(), new Marco(marco.getId())));
        calculateAllVariables();
    }

    public void addNewProcessToMemory(Proceso proceso) {
        if (isMemoryFull()) {
            return;
        }
        Integer procesoMemorySize = proceso.getMemorySize();

        int marcosCompletos = procesoMemorySize / MARCO_SIZE;
        int marcosTotales = (procesoMemorySize % MARCO_SIZE == 0) ? marcosCompletos : marcosCompletos + 1;

        if (marcosTotales > marcosDisponibles){
            return;
        }

        for (int i = 0; i < marcosTotales; i++) {
            calculateLastFreeMarco();

            Integer memoryUsed = getMemoryUsedByProceso(procesoMemorySize);
            lastFreeMarco.setSizeUsed(memoryUsed);
            lastFreeMarco.setProcesoID(proceso.getId());
            if (!memoryMap.containsValue(lastFreeMarco)) {
                break;
            }
            memoryMap.put(lastFreeMarco.getId(), lastFreeMarco);
            procesoMemorySize -= MARCO_SIZE;
        }
        calculateAllVariables();
    }

    private static Integer getMemoryUsedByProceso(Integer procesoMemorySize) {
        if (procesoMemorySize < MARCO_SIZE) {
            return procesoMemorySize;
        }
        return MARCO_SIZE;
    }

}
