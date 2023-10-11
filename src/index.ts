import { registerPlugin } from '@capacitor/core';

import type { SipCallPlugin } from './definitions';

const SipCall = registerPlugin<SipCallPlugin>('SipCall', {
  web: () => import('./web').then(m => new m.SipCallWeb()),
});

export * from './definitions';
export { SipCall };
