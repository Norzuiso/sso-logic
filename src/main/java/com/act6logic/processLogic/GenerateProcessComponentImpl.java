package com.act6logic.processLogic;

import com.act6logic.obj.Proceso;
import com.act6logic.utils.TimeUtils;
import com.act6logic.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GenerateProcessComponentImpl implements GenerateProcessComponent {

    @Autowired
    private Utils utils;

    @Autowired
    private TimeUtils timeUtils;

    @Override
    public List<Proceso> generateProcesos(int procesosQuantity) {
        List<Proceso> procesos = new ArrayList<>();
        for (int i = 0; i <= procesosQuantity; i++) {
            procesos.add(generateProcess(i));
        }
        return procesos;
    }

    @Override
    public Proceso generateProceso(String lastId) {
        String[] split = lastId.split("-");
        int lastValue = Integer.parseInt(split[1])+1;
        return generateProcess(lastValue);
    }

    private Proceso generateProcess(int lastValue) {
        Proceso pro = new Proceso();
        pro.setId("pro-" + lastValue);
        pro.setOperation(utils.generateOperation());
        pro.setResult(utils.getResult(pro.getOperation()));
        pro.setTiempoMaxEstimado(utils.generateEstimatedTime());
        pro.setTiempoRestantePorEjecutar(timeUtils.resTime(pro.getTiempoMaxEstimado(), pro.getTiempoServicio()));
        return pro;
    }

}
