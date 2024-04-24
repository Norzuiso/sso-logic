package com.act6logic.logic.Ejecucion;

import com.act6logic.obj.Proceso;
import com.act6logic.obj.ProcessTime;
import com.act6logic.utils.ProcessTimeUtils;
import com.act6logic.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcesoEjecucionServiceImpl implements ProcesoEjecucionService {

    @Autowired
    private ProcessTimeUtils processTimeUtils;

    @Autowired
    private TimeUtils timeUtils;

    @Override
    public void updateProcesoEnEjecucion(Proceso procesoEnEjecucion) {
        updateTiempos(procesoEnEjecucion);
    }

    private void updateTiempos(Proceso procesoEnEjecucion) {
        processTimeUtils.updateTiempoPorEjecutar(procesoEnEjecucion);
        processTimeUtils.updateTiempoTranscurrido(procesoEnEjecucion);

    }

    @Override
    public boolean isInvalid(Proceso procesoEnEjecucion) {
        return (procesoEnEjecucion.getId().isEmpty());
    }

    @Override
    public boolean isProcessDone(Proceso proceso) {
        return timeUtils.compareTimeWithZero(proceso.getTiempoRestantePorEjecutar());
    }

    @Override
    public void finisProcess(Proceso proceso, ProcessTime tiempoActual) {
        proceso.setTiempoFinalizacion(tiempoActual);
        proceso.setTiempoRetorno(processTimeUtils.calculateTiempoRetorno(proceso));
        proceso.setTiempoServicio(proceso.getTiempoMaxEstimado());
    }

    @Override
    public void finisProcessError(Proceso proceso, ProcessTime tiempoActual, String message) {
        proceso.setResult(message);
        proceso.setTiempoServicio(proceso.getTiempoTranscurrido());
        proceso.setTiempoFinalizacion(tiempoActual);
        proceso.setTiempoRetorno(processTimeUtils.calculateTiempoRetorno(proceso));

    }
}
