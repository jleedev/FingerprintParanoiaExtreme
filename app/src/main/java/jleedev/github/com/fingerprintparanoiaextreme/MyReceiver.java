package jleedev.github.com.fingerprintparanoiaextreme;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends DeviceAdminReceiver {
    private final String TAG = "MyReceiver";

    @Override
    public void onEnabled(Context context, Intent intent) {
    }

    @Override
    public void onPasswordFailed(Context context, Intent intent) {
        ComponentName name = new ComponentName(context, MyReceiver.class);
        DevicePolicyManager dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);

        int attempts = dpm.getCurrentFailedPasswordAttempts();
        dpm.setKeyguardDisabledFeatures(name, DevicePolicyManager.KEYGUARD_DISABLE_FINGERPRINT);
        Log.d(TAG, String.format("Disabling fingerprint after %d failed unlock attempts", attempts));
    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
        ComponentName name = new ComponentName(context, MyReceiver.class);
        DevicePolicyManager dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);

        dpm.setKeyguardDisabledFeatures(name, DevicePolicyManager.KEYGUARD_DISABLE_FEATURES_NONE);
        Log.d(TAG, "Unlock successful, enabling fingerprint");
    }
}
