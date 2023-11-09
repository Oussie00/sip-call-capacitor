import { Plugin } from "@capacitor/core";
import type { PermissionState } from '@capacitor/core';

export interface SipCallPlugin extends Plugin {
  /**
   * Login with your VoIP provider
   * @param options {LoginOptions} LoginOptions
   * @return {Promise<void>} Returns a promise that resolves when logged in
   */
  login(options: LoginOptions): Promise<void>;

  /**
   * Logout from your VoIP provider
   * @return {Promise<void>} Returns a promise that resolves when logged out
   */
  logout(): Promise<void>;

  /**
   * Call with your VoIP provider
   * @param options {CallOptions} CallOptions
   * @return {Promise<void>} Returns a promise that resolves when logged in
   */
  call(options: CallOptions): Promise<void>;

  /** 
    * Hangup the call
    * @return {Promise<void>} Returns a promise that resolves when call is terminated
    */
  hangup(): Promise<void>;

  /**
   * Mute the microphone
   * @param options {muted: boolean} Muted
   * @return {Promise<void>} Returns a promise that resolves when mic is muted/enabled
   */
  setMicrophoneMuted(options: { muted: boolean }): Promise<void>;

  /**
   * Enables the speaker
   * @param options {enabled: boolean} Speaker state
   * @return {Promise<void>} Returns a promise that resolves when the speaker is enabled/disabled
   */
  setSpeakerEnabled(options: { enabled: boolean }): Promise<void>;

  /**
   * RequestPermissions for the plugin (RECORD_AUDIO)
   * @return {Promise<void>} Returns a promise that resolves Permissions have been requested
   */
  requestPermissions(): Promise<PermissionStatus>;

  checkPermissions(): Promise<PermissionStatus>;
}

export interface PermissionStatus {
  calling: PermissionState;
}

export interface LoginOptions {
  /**
  * @param username {string} Username
  */
  username: string;
  /**
  * @param realm {string} Realm to connect to
  */
  password: string;
  /**
  * @param password {string} Password
  */
  realm: string;
}

export interface CallOptions {
  address: string;
  displayName: string;
}

export enum CallState {
  OutgoingInit = "OutgoingInit",
  OutgoingProgress = "OutgoingProgress",
  OutgoingRinging = "OutgoingRinging",
  Connected = "Connected",
  Resuming = "Resuming",
  Ended = "End",
  Error = "Error"
}

export enum RegistrationState {
  None = "None",
  Cleared = "Cleared",
  Failed = "Failed",
  Ok = "Ok",
  Progress = "Progress"
}

export enum SipCallEvent {
  AccountRegistrationEvent = 'AccountRegistrationEvent',
  CallStateEvent = 'CallStateEvent'
}