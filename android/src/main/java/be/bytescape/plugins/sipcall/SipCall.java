package be.bytescape.plugins.sipcall;

import java.util.Arrays;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import org.linphone.core.Account;
import org.linphone.core.AccountParams;
import org.linphone.core.Address;
import org.linphone.core.AudioDevice;
import org.linphone.core.AuthInfo;
import org.linphone.core.Call;
import org.linphone.core.CallParams;
import org.linphone.core.Core;
import org.linphone.core.CoreListenerStub;
import org.linphone.core.Factory;
import org.linphone.core.MediaEncryption;
import org.linphone.core.RegistrationState;
import org.linphone.core.TransportType;
import org.linphone.core.tools.Log;

import be.bytescape.plugins.sipcall.interfaces.EmitEventCallback;


public class SipCall {
    private static final String Tag = "SipCall";
    private static final String ACCOUNT_REGISTRATION_EVENT = "AccountRegistrationEvent";
    private static final String CALL_STATE_EVENT = "CallStateEvent";
    private Core core;
    private AuthInfo authInfo;
    private EmitEventCallback emitEventCallback;

    public SipCall(Context context, EmitEventCallback _emitEventCallback) {
        Factory factory = Factory.instance();
        core = factory.createCore(null, null, context);
        emitEventCallback = _emitEventCallback;
    }

    private CoreListenerStub coreListener = new CoreListenerStub() {
        @Override
        public void onAccountRegistrationStateChanged(@NonNull Core core, @NonNull Account account, RegistrationState state,
                                                      @NonNull String message) {
            super.onAccountRegistrationStateChanged(core, account, state, message);
            switch (state) {
                case None:
                    emitEvent(ACCOUNT_REGISTRATION_EVENT, RegistrationState.None.name());
                    break;
                case Progress:
                    emitEvent(ACCOUNT_REGISTRATION_EVENT, RegistrationState.Progress.name());
                    break;
                case Failed:
                    emitEvent(ACCOUNT_REGISTRATION_EVENT, RegistrationState.Failed.name());
                    break;
                case Ok:
                    emitEvent(ACCOUNT_REGISTRATION_EVENT, RegistrationState.Ok.name());
                    break;
                case Cleared:
                    emitEvent(ACCOUNT_REGISTRATION_EVENT, RegistrationState.Cleared.name());
                    break;
                default:
                    Log.e(Tag, "Unknown Account Registration Event");
                    break;
            }
        }

        @Override
        public void onCallStateChanged(@NonNull Core core, @NonNull Call call, Call.State state, @NonNull String message) {
            super.onCallStateChanged(core, call, state, message);
            switch (state) {
                case OutgoingInit:
                    emitEvent(CALL_STATE_EVENT, Call.State.OutgoingInit.name());
                    break;
                case OutgoingRinging:
                    emitEvent(CALL_STATE_EVENT, Call.State.OutgoingRinging.name());
                    break;
                case OutgoingProgress:
                    emitEvent(CALL_STATE_EVENT, Call.State.OutgoingProgress.name());
                    break;
                case Connected:
                    emitEvent(CALL_STATE_EVENT, Call.State.Connected.name());
                    break;
                case Resuming:
                    emitEvent(CALL_STATE_EVENT, Call.State.Resuming.name());
                    break;
                case End:
                    emitEvent(CALL_STATE_EVENT, Call.State.End.name());
                    break;
                case Error:
                    emitEvent(CALL_STATE_EVENT, Call.State.Error.name());
                    break;
                default:
                    Log.e(Tag, "Unknown Call State Event");
                    break;
            }
        }
    };

    public void login(String username, String password, String realm) {
        TransportType transportType = TransportType.Udp;

        // To configure a SIP account, we need an Account object and an AuthInfo object
        // The first one is how to connect to the proxy server, the second one stores
        // the credentials

        // The auth info can be created from the Factory as it's only a data class
        // userID is set to null as it's the same as the username in our case
        // ha1 is set to null as we are using the clear text password. Upon first
        // register, the hash will be computed automatically.
        // The realm will be determined automatically from the first register, as well
        // as the algorithm
        authInfo = Factory.instance().createAuthInfo(username, null, password, null, realm, null, null);

        // Account object replaces deprecated ProxyConfig object
        // Account object is configured through an AccountParams object that we can
        // obtain from the Core
        AccountParams accountParams = core.createAccountParams();

        // A SIP account is identified by an identity address that we can construct from
        // the username and domain
        Address identity = Factory.instance().createAddress("sip:" + username + "@" + realm);
        accountParams.setIdentityAddress(identity);

        // We also need to configure where the proxy server is located
        Address address = Factory.instance().createAddress("sip:" + realm);
        // We use the Address object to easily set the transport protocol
        address.setTransport(transportType);
        accountParams.setServerAddress(address);
        // And we ensure the account will start the registration process
        accountParams.setRegisterEnabled(true);

        // Now that our AccountParams is configured, we can create the Account object
        Account account = core.createAccount(accountParams);

        // Now let's add our objects to the Core
        core.addAuthInfo(authInfo);
        core.addAccount(account);

        // Also set the newly added account as default
        core.setDefaultAccount(account);

        // To be notified of the connection status of our account, we need to add the
        // listener to the Core
        core.addListener(coreListener);
        // Finally we need the Core to be started for the registration to happen (it
        // could have been started before)
        core.start();
    }

    public void logout() {
        core.clearAccounts();
        core.clearAllAuthInfo();
    }

    public void call(String address, String displayName) {
        // As for everything we need to get the SIP URI of the remote and convert it to
        // an Address
        String realm = authInfo.getRealm();
        String remoteSipUri = "sip:" + address + "@" + realm;
        Address remoteAddress = Factory.instance().createAddress(remoteSipUri);
        if (remoteAddress == null) {
            // If address parsing fails, we can't continue with outgoing call process
            Log.e(" invalid remote address");
        }

        // We also need a CallParams object
        // Create call params expects a Call object for incoming calls, but for outgoing
        // we must use null safely
        CallParams params = core.createCallParams(null);
        // We can now configure it
        // Here we ask for no encryption but we could ask for ZRTP/SRTP/DTLS
        params.setMediaEncryption(MediaEncryption.None);

        // Finally we start the call
        core.inviteAddressWithParams(remoteAddress, params);
        // Call process can be followed in onCallStateChanged callback from core
        // listener
    }

    public void hangup() {
        if (core.getCallsNb() == 0) {
            return;
        }

        Call call = core.getCurrentCall();
        call = core.getCurrentCall() != null ? call : core.getCalls()[0];

        call.terminate();
    }

    public void setMicrophoneMuted(Boolean muted) {
        if (core.getCallsNb() == 0) {
            return;
        }

        Call call = core.getCurrentCall();
        call = core.getCurrentCall() != null ? call : core.getCalls()[0];

        call.setMicrophoneMuted(muted);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setSpeakerEnabled(Boolean enabled) {
        if (core.getCallsNb() == 0) {
            return;
        }

        Call call = core.getCurrentCall();
        call = core.getCurrentCall() != null ? call : core.getCalls()[0];
        AudioDevice[] audioDevices = core.getAudioDevices();

        // TODO: send error plugin result -> no audio devices found

        if(enabled) {
            // get audio device with type == speaker
            AudioDevice speakerAudioDevice = Arrays
                    .stream(audioDevices)
                    .filter(d -> d.getType().equals(AudioDevice.Type.Speaker))
                    .findFirst()
                    .orElse(null);

            // TODO: send error plugin result -> no speaker audioDevice found
            // set call output audio device
            if(speakerAudioDevice != null) {
                call.setOutputAudioDevice(speakerAudioDevice);
            }
        } else {
            // get audio device with type == earpiece
            AudioDevice earpieceAudioDevice = Arrays
                    .stream(audioDevices)
                    .filter(d -> d.getType().equals(AudioDevice.Type.Earpiece))
                    .findFirst()
                    .orElse(null);

            // TODO: send error plugin result -> no earpiece audioDevice found
            // set call output audio device
            if(earpieceAudioDevice != null) {
                call.setOutputAudioDevice(earpieceAudioDevice);
            }
        }
    }

    private void emitEvent(String eventType, String eventName) {
        Log.d(eventName);
        emitEventCallback.emitEvent(eventType, eventName);
    }
}
