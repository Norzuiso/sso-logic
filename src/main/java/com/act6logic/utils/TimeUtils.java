package com.act6logic.utils;

import com.act6logic.logic.GeneralFunctionalityComponentImpl;
import com.act6logic.obj.Proceso;
import com.act6logic.obj.ProcessTime;

public interface TimeUtils {
    public ProcessTime increment(ProcessTime pt);


    public ProcessTime decrement(ProcessTime pt);

    public ProcessTime sumTimer(ProcessTime pt1, ProcessTime pt2);

    public ProcessTime resTime(ProcessTime pt1, ProcessTime pt2);

    Boolean compareTimeWithZero(ProcessTime pt1);

    Boolean compareTimesEquals(ProcessTime pt1, ProcessTime pt2);

    public ProcessTime processSeconds(ProcessTime pt);

    public ProcessTime processSeconds(Integer seconds);


}
