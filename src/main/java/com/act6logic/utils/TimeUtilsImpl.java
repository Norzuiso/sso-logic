package com.act6logic.utils;

import com.act6logic.obj.Proceso;
import com.act6logic.obj.ProcessTime;
import org.springframework.stereotype.Component;

@Component
public class TimeUtilsImpl implements TimeUtils {
    private static final ProcessTime processTimeOneSecond = new ProcessTime(0, 1);

    @Override
    public ProcessTime increment(ProcessTime pt) {
        return sumTimer(pt, processTimeOneSecond);

    }


    @Override
    public ProcessTime decrement(ProcessTime pt) {
        return resTime(pt, processTimeOneSecond);
    }

    @Override
    public ProcessTime sumTimer(ProcessTime pt1, ProcessTime pt2) {
        ProcessTime result = new ProcessTime(0, 0);
        result.setMinutes(pt1.getMinutes() + pt2.getMinutes());
        result.setSeconds(pt1.getSeconds() + pt2.getSeconds());
        return processSeconds(result);
    }

    @Override
    public ProcessTime resTime(ProcessTime pt1, ProcessTime pt2) {
        ProcessTime result = new ProcessTime(0, 0);
        int secondsFromMinutes = (pt1.getMinutes() * 60) - pt2.getMinutes() * 60;
        secondsFromMinutes += (pt1.getSeconds()) - (pt2.getSeconds());
        result.setSeconds(secondsFromMinutes);
        return processSeconds(result);
    }

    @Override
    public Boolean compareTimeWithZero(ProcessTime pt1) {
        return compareTimesEquals(pt1, new ProcessTime(0, 0));
    }


    @Override
    public Boolean compareTimesEquals(ProcessTime pt1, ProcessTime pt2) {
        return (pt1.getMinutes() == pt2.getMinutes()) && (pt1.getSeconds() == pt2.getSeconds());
    }

    @Override
    public ProcessTime processSeconds(ProcessTime pt) {
        if (pt.getSeconds() >= 60) {
            int x = pt.getSeconds();
            int resto = x % 60;
            int cociente = (x / 60);
            pt.setSeconds(resto);
            pt.setMinutes(cociente);
        }
        return pt;
    }

    @Override
    public ProcessTime processSeconds(Integer seconds) {
        return processSeconds(new ProcessTime(0, seconds));
    }
}
