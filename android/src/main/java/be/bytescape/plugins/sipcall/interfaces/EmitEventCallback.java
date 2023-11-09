package be.bytescape.plugins.sipcall.interfaces;

import com.getcapacitor.JSObject;

@FunctionalInterface
public interface EmitEventCallback {
    void emitEvent(String eventType, String value);
}