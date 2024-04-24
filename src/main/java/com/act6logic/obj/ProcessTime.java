package com.act6logic.obj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessTime {
    private int minutes = 0;
    private int seconds = 0;
}
