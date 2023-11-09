import { WebPlugin } from '@capacitor/core';
import type { CallOptions, LoginOptions, PermissionStatus, SipCallPlugin } from './definitions';

export class SipCallWeb extends WebPlugin implements SipCallPlugin {
  login(_options: LoginOptions): Promise<void> {
    console.error("This plugin is only available for android");
    return Promise.resolve();
  }
  call(_options: CallOptions): Promise<void> {
    console.error("This plugin is only available for android");
    return Promise.resolve();
  }
  setMicrophoneMuted(_options: { muted: boolean; }): Promise<void> {
    console.error("This plugin is only available for android");
    return Promise.resolve();
  }
  setSpeakerEnabled(_options: { enabled: boolean; }): Promise<void> {
    console.error("This plugin is only available for android");
    return Promise.resolve();
  }
  logout(): Promise<void> {
    console.error("This plugin is only available for android");
    return Promise.resolve();
  }

  hangup(): Promise<void> {
    console.error("This plugin is only available for android");
    return Promise.resolve();
  }

  requestPermissions(): Promise<PermissionStatus> {
    throw Error("This plugin is only available for android");
  }

  checkPermissions(): Promise<PermissionStatus> {
    throw Error("This plugin is only available for android");
  }
}
