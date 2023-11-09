package be.bytescape.plugins.sipcall;

import android.Manifest;
import android.util.Log;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;

@CapacitorPlugin(
        name = "SipCall",
        permissions = {
                       @Permission(
                            alias = "Calling",
                            strings = { Manifest.permission.RECORD_AUDIO }
                        ),
                    }
)
public class SipCallPlugin extends Plugin {

    private SipCall implementation;

    @Override
    public void load() {
        implementation = new SipCall(this.getActivity().getApplicationContext(), (eventType, value) -> {
            JSObject ret = new JSObject();
            ret.put("value", value);
            notifyListeners(eventType, ret);
        });
    }

    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void login(PluginCall call) {
        String username = call.getString("username");
        String password = call.getString("password");
        String realm = call.getString("realm");

        implementation.login(username, password, realm);
    }

    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void logout(PluginCall call) {
        implementation.logout();
    }
    
    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void hangup(PluginCall call) {
        implementation.hangup();
    }

    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void call(PluginCall call) {
        String address = call.getString("address");
        String displayName = call.getString("displayName");

        implementation.call(address, displayName);
    }

    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void setMicrophoneMuted(PluginCall call) {
        Boolean muted = call.getBoolean("muted");

        implementation.setMicrophoneMuted(muted);
    }

    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void setSpeakerEnabled(PluginCall call) {
        Boolean enabled = call.getBoolean("enabled");

        implementation.setSpeakerEnabled(enabled);
    }
}
