package com.act6logic.obj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcesosBloqueados {
    private List<Proceso> procesosBloqueados = new ArrayList<>();
    private Boolean tenemosBloqueados = !procesosBloqueados.isEmpty();
}
