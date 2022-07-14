package lv.cebbys.mcmods.refabslab.exception;

public class CompatibilityException extends RuntimeException {
    public CompatibilityException(String msg) {
        super(msg);
    }
    public CompatibilityException(String msg, Throwable t) {
        super(msg, t);
    }
}
