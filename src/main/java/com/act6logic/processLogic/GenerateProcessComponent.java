package com.act6logic.processLogic;

import com.act6logic.obj.Proceso;

import java.util.List;

public interface GenerateProcessComponent {

    public List<Proceso> generateProcesos(int procesosQuantity);


    Proceso generateProceso(String lastId);

    Proceso generateProceso(int lastValue);
}
