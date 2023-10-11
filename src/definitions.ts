export interface SipCallPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
