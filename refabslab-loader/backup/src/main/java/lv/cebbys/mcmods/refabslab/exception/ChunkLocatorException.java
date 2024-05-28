package lv.cebbys.mcmods.refabslab.exception;

public class ChunkLocatorException extends RuntimeException {

    public ChunkLocatorException(Class<?> identifier, String cause) {
        this(identifier.getName(), new Exception(cause));
    }

    public ChunkLocatorException(Class<?> identifier, Exception e) {
        this(identifier.getName(), e);
    }

    public ChunkLocatorException(String identifier, Exception e) {
        super("Failed to create '" + identifier + "' ChunkLocator", e);
    }
}
