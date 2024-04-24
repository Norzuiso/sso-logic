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
        Proceso pro = new Proceso();
        for (int i = 0; i <= procesosQuantity; i++) {
            pro.setId("pro-" + i);
            pro.setOperation(utils.generateOperation());
            pro.setResult(utils.getResult(pro.getOperation()));
            pro.setTiempoMaxEstimado(utils.generateEstimatedTime());
            pro.setTiempoRestantePorEjecutar(timeUtils.resTime(pro.getTiempoMaxEstimado(), pro.getTiempoServicio()));

            procesos.add(pro);
            pro = new Proceso();
        }
        return procesos;
    }

}
