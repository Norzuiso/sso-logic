package com.act6logic.utils;

import com.act6logic.obj.Proceso;
import com.act6logic.obj.ProcessTime;

public interface ProcessTimeUtils {


    void updateTiempoRetorno(Proceso proceso);

    void updateTiempoBloqueado(Proceso proceso);
    void updateTiempoBloqueadoParaSalir(Proceso proceso);
    void updateTiempoEspera(Proceso proceso);


    void updateTiempoTranscurrido(Proceso procesoEnEjecucion);

    void updateTiempoPorEjecutar(Proceso procesoEnEjecucion);

    ProcessTime calculateTiempoRetorno(Proceso proceso);

    ProcessTime calculateTiempoRespuesta(ProcessTime tiempoActual, ProcessTime tiempoLlegada);

    void calculateTiemposLlegada(Proceso proceso, ProcessTime tiempoActual);

    void calculateTiempoRespuesta(Proceso proceso, ProcessTime tiempoActual);

    void updateTiempoServicio(Proceso procesoEnEjecucion);
}
