package com.act6logic.logic.Bloqueados;

import com.act6logic.obj.Proceso;

import java.util.List;

public interface ProcesosBloqueadosService {

    void updateProcesosBloqueados(List<Proceso> procesosBloqueadosList, List<Proceso> procesosEspera);

    void extraerProcesosBloqueados(List<Proceso> procesosBloqueadosList, List<Proceso> procesosEspera, List<Proceso> bloqueqadosPorSalir);
}
