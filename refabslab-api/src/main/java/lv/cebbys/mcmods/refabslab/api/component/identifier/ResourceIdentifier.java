package lv.cebbys.mcmods.refabslab.api.component.identifier;

import lombok.Data;

import java.util.function.BiFunction;

@Data
public class ResourceIdentifier {
    private final String namespace;
    private final String path;

    public <T> T as(BiFunction<String, String, T> mapper) {
        return mapper.apply(namespace, path);
    }
}
