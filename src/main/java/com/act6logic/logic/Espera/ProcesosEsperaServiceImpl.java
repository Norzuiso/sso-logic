package com.act6logic.logic.Espera;

import com.act6logic.obj.Proceso;
import com.act6logic.obj.ProcessTime;
import com.act6logic.utils.ProcessTimeUtils;
import com.act6logic.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProcesosEsperaServiceImpl implements ProcesosEsperaService {

    @Autowired
    private TimeUtils timeUtils;

    @Autowired
    private ProcessTimeUtils processTimeUtils;

    @Override
    public void updateProcesosEspera(List<Proceso> procesosEspera) {
        if (procesosEspera.isEmpty()){
            return;
        }
        for (Proceso proceso : procesosEspera) {
            processTimeUtils.updateTiempoEspera(proceso);
            processTimeUtils.updateTiempoRetorno(proceso);

        }

    }
}
