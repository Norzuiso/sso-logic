package com.act6logic.obj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Marco {
    private Integer id;
    private Integer size;
    private String procesoID;
    private Integer sizeUsed;

    public Marco(Integer id) {
        this.id = id;
        this.size = 6;
        this.sizeUsed = 0;
        this.procesoID = "";
    }
}
