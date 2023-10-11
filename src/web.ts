import { WebPlugin } from '@capacitor/core';

import type { SipCallPlugin } from './definitions';

export class SipCallWeb extends WebPlugin implements SipCallPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
