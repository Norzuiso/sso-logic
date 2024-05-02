package com.act6logic.utils;

import com.act6logic.obj.Proceso;
import com.act6logic.obj.ProcessTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessTimeUtilsImpl implements ProcessTimeUtils {

    @Autowired
    private TimeUtils timeUtils;

    @Override
    public void updateTiempoRetorno(Proceso proceso) {
        ProcessTime increment = timeUtils.increment(proceso.getTiempoRetorno());
        proceso.setTiempoRetorno(increment);
    }

    @Override
    public void updateTiempoBloqueado(Proceso proceso) {
        ProcessTime increment = timeUtils.increment(proceso.getTiempoBloqueado());
        proceso.setTiempoBloqueado(increment);
    }

    @Override
    public void updateTiempoBloqueadoParaSalir(Proceso proceso) {
        ProcessTime increment = timeUtils.increment(proceso.getTiempoBloqueadoParaSalir());
        proceso.setTiempoBloqueadoParaSalir(increment);
    }

    @Override
    public void updateTiempoEspera(Proceso proceso) {
        ProcessTime increment = timeUtils.increment(proceso.getTiempoEspera());
        proceso.setTiempoEspera(increment);
    }

    @Override
    public void updateTiempoTranscurrido(Proceso proceso) {
        ProcessTime increment = timeUtils.increment(proceso.getTiempoTranscurrido());
        proceso.setTiempoTranscurrido(increment);
    }

    @Override
    public void updateTiempoPorEjecutar(Proceso proceso) {
        ProcessTime decrement = timeUtils.decrement(proceso.getTiempoRestantePorEjecutar());
        proceso.setTiempoRestantePorEjecutar(decrement);
    }


    /**
     * @param proceso Tomamos el tiempoFinalización-TiempoLlegada = tiempoRetorno
     * @return Tiempo de retorno
     */
    @Override
    public ProcessTime calculateTiempoRetorno(Proceso proceso) {
        ProcessTime tiempoFinalizacion = proceso.getTiempoFinalizacion();
        ProcessTime tiempoLlegada = proceso.getTiempoLlegada();
        return timeUtils.resTime(tiempoFinalizacion, tiempoLlegada);
    }


    /**
     * @param tiempoActual
     * @param tiempoLlegada tiempoActual - tiempollegada = tiempoRespuesta
     * @return tiempoRespuesta
     */
    @Override
    public ProcessTime calculateTiempoRespuesta(ProcessTime tiempoActual, ProcessTime tiempoLlegada) {
        return timeUtils.resTime(tiempoActual, tiempoLlegada);
    }

    @Override
    public void calculateTiemposLlegada(Proceso proceso, ProcessTime tiempoActual) {
        proceso.setTiempoLlegada(tiempoActual);
        proceso.setLlegada(true);
    }

    @Override
    public void calculateTiempoRespuesta(Proceso proceso, ProcessTime tiempoActual) {
        ProcessTime tiempoLlegada = proceso.getTiempoFinalizacion();
        proceso.setTiempoRespuesta(timeUtils.resTime(tiempoActual, tiempoLlegada));
    }

    @Override
    public void updateTiempoServicio(Proceso procesoEnEjecucion) {
        ProcessTime increment = timeUtils.increment(procesoEnEjecucion.getTiempoServicio());
        procesoEnEjecucion.setTiempoServicio(increment);
    }

}
