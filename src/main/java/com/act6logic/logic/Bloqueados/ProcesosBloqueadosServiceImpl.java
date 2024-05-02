package com.act6logic.logic.Bloqueados;

import com.act6logic.obj.Proceso;
import com.act6logic.obj.ProcessTime;
import com.act6logic.utils.ProcessTimeUtils;
import com.act6logic.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProcesosBloqueadosServiceImpl implements ProcesosBloqueadosService {

    @Autowired
    private TimeUtils timeUtils;

    @Autowired
    private ProcessTimeUtils processTimeUtils;

    @Override
    public void updateProcesosBloqueados(List<Proceso> procesosBloqueadosList, List<Proceso> procesosEspera) {
        if (!procesosBloqueadosList.isEmpty()) {
            updateTiemposBloqueado(procesosBloqueadosList);
            List<Proceso> bloqueqadosPorSalir = getProcesosPorSalir(procesosBloqueadosList);
            extraerProcesosBloqueados(procesosBloqueadosList, procesosEspera, bloqueqadosPorSalir);
        }
    }

    @Override
    public void extraerProcesosBloqueados(List<Proceso> procesosBloqueadosList,
                                          List<Proceso> procesosEspera,
                                          List<Proceso> bloqueqadosPorSalir) {
        for (Proceso procesoB : bloqueqadosPorSalir) {
            procesosBloqueadosList.remove(procesoB);
            procesoB.setTiempoBloqueadoParaSalir(timeUtils.processSeconds(0));
            procesosEspera.addLast(procesoB);
        }
    }


    private static List<Proceso> getProcesosPorSalir(List<Proceso> procesosBloqueadosList) {
        List<Proceso> bloqueqadosPorSalir = procesosBloqueadosList.stream()
                .filter(x -> x.getTiempoBloqueadoParaSalir().getSeconds() == 8).toList();
        return bloqueqadosPorSalir;
    }

    private void updateTiemposBloqueado(List<Proceso> procesosBloqueadosList) {
        for (Proceso proceso : procesosBloqueadosList) {
            processTimeUtils.updateTiempoBloqueado(proceso);
            processTimeUtils.updateTiempoBloqueadoParaSalir(proceso);
        }
    }

}
