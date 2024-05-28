package lv.cebbys.mcmods.refabslab.api.component.math;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Vector3<T> {
    private final T x;
    private final T y;
    private final T z;
}
