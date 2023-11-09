# sip-call

Making voip calls using a SIP provider

## Install

```bash
npm install sip-call
npx cap sync
```

## API

<docgen-index>

* [`login(...)`](#login)
* [`logout()`](#logout)
* [`call(...)`](#call)
* [`hangup()`](#hangup)
* [`setMicrophoneMuted(...)`](#setmicrophonemuted)
* [`setSpeakerEnabled(...)`](#setspeakerenabled)
* [`requestPermissions()`](#requestpermissions)
* [`checkPermissions()`](#checkpermissions)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### login(...)

```typescript
login(options: LoginOptions) => Promise<void>
```

Login with your VoIP provider

| Param         | Type                                                  | Description                              |
| ------------- | ----------------------------------------------------- | ---------------------------------------- |
| **`options`** | <code><a href="#loginoptions">LoginOptions</a></code> | <a href="#loginoptions">LoginOptions</a> |

--------------------


### logout()

```typescript
logout() => Promise<void>
```

Logout from your VoIP provider

--------------------


### call(...)

```typescript
call(options: CallOptions) => Promise<void>
```

Call with your VoIP provider

| Param         | Type                                                | Description                            |
| ------------- | --------------------------------------------------- | -------------------------------------- |
| **`options`** | <code><a href="#calloptions">CallOptions</a></code> | <a href="#calloptions">CallOptions</a> |

--------------------


### hangup()

```typescript
hangup() => Promise<void>
```

Hangup the call

--------------------


### setMicrophoneMuted(...)

```typescript
setMicrophoneMuted(options: { muted: boolean; }) => Promise<void>
```

Mute the microphone

| Param         | Type                             | Description      |
| ------------- | -------------------------------- | ---------------- |
| **`options`** | <code>{ muted: boolean; }</code> | : boolean} Muted |

--------------------


### setSpeakerEnabled(...)

```typescript
setSpeakerEnabled(options: { enabled: boolean; }) => Promise<void>
```

Enables the speaker

| Param         | Type                               | Description              |
| ------------- | ---------------------------------- | ------------------------ |
| **`options`** | <code>{ enabled: boolean; }</code> | : boolean} Speaker state |

--------------------


### requestPermissions()

```typescript
requestPermissions() => Promise<PermissionStatus>
```

RequestPermissions for the plugin (RECORD_AUDIO)

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

--------------------


### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

--------------------


### Interfaces


#### LoginOptions

| Prop           | Type                |
| -------------- | ------------------- |
| **`username`** | <code>string</code> |
| **`password`** | <code>string</code> |
| **`realm`**    | <code>string</code> |


#### CallOptions

| Prop              | Type                |
| ----------------- | ------------------- |
| **`address`**     | <code>string</code> |
| **`displayName`** | <code>string</code> |


#### PermissionStatus

| Prop          | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`calling`** | <code><a href="#permissionstate">PermissionState</a></code> |


### Type Aliases


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>

</docgen-api>
