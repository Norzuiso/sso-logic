package com.act6logic.logic.Ejecucion;

import com.act6logic.obj.Proceso;
import com.act6logic.obj.ProcessTime;

public interface ProcesoEjecucionService {
    void updateProcesoEnEjecucion(Proceso procesoEnEjecucion);

    boolean isInvalid(Proceso procesoEnEjecucion);

    boolean isProcessDone(Proceso procesoEnEjecucion);

    void finisProcess(Proceso procesoEnEjecucion, ProcessTime tiempoActual);

    void finisProcessError(Proceso procesoEnEjecucion, ProcessTime tiempoActual, String message);
}
