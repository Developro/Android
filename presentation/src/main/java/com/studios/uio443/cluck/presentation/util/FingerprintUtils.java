package com.studios.uio443.cluck.presentation.util;

import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;

/**
 * Created by azret.magometov on 19-Dec-16.
 * https://habr.com/company/e-Legion/blog/317706/
 */

public final class FingerprintUtils {
    private FingerprintUtils() {
    }

    //Совместимость с устройствами Android 6+ с сенсором
    public static boolean checkFingerprintCompatibility(@NonNull Context context) {
        return FingerprintManagerCompat.from(context).isHardwareDetected();
    }

    //нам нужно понять, готов ли сенсор к использованию
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static mSensorState checkSensorState(@NonNull Context context) {
        if (checkFingerprintCompatibility(context)) {

            KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
            if (!keyguardManager.isKeyguardSecure()) {
                // если устройство не защищено пином, рисунком или паролем
                return mSensorState.NOT_BLOCKED;
            }

            if (!FingerprintManagerCompat.from(context).hasEnrolledFingerprints()) {
                // если на устройстве нет отпечатков
                return mSensorState.NO_FINGERPRINTS;
            }

            //сенсор готов к использованию
            return mSensorState.READY;

        } else {
            return mSensorState.NOT_SUPPORTED;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean isSensorStateAt(@NonNull mSensorState state, @NonNull Context context) {
        return checkSensorState(context) == state;
    }

    public enum mSensorState {
        NOT_SUPPORTED,
        NOT_BLOCKED,        // если устройство не защищено пином, рисунком или паролем
        NO_FINGERPRINTS,    // если на устройстве нет отпечатков
        READY
    }
}
