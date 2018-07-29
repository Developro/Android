package com.studios.uio443.cluck.presentation.view.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.studios.uio443.cluck.presentation.R;
import com.studios.uio443.cluck.presentation.util.Animate;
import com.studios.uio443.cluck.presentation.util.Consts;
import com.studios.uio443.cluck.presentation.util.CryptoUtils;
import com.studios.uio443.cluck.presentation.util.FingerprintUtils;
import com.yakivmospan.scytale.Crypto;
import com.yakivmospan.scytale.Options;
import com.yakivmospan.scytale.Store;

import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zundarik
 */

public class LoginPinActivity extends AppCompatActivity {

    public static final int RESULT_BACK_PRESSED = RESULT_FIRST_USER;
    private static final String PIN = "pin_LoginPin";
    private static final String PIN_FP = "pin_Fingerprint";
    private static final int PIN_LENGTH = 4;
    @BindView(R.id.title)
    TextView mTextTitle;
    @BindView(R.id.pin_lock_view)
    PinLockView mPinLockView;
    @BindView(R.id.attempts)
    TextView mTextAttempts;
    @BindView(R.id.indicator_dots)
    IndicatorDots mIndicatorDots;
    @BindView(R.id.fingerView)
    AppCompatImageView mImageViewFingerView;
    @BindView(R.id.fingerText)
    TextView mTextFingerText;

    private boolean mSetPin = false;
    private String mFirstPin = "";
    private SharedPreferences mPreferences;
    private LoginPinActivity.FingerprintHelper mFingerprintHelper;

    private AnimatedVectorDrawable showFingerprint;
    private AnimatedVectorDrawable fingerprintToTick;
    private AnimatedVectorDrawable fingerprintToCross;

    private PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            if (mSetPin) {
                setPin(pin);
            } else {
                checkPin(pin);
            }
            Log.d(Consts.TAG, "Pin complete: " + pin);
        }

        @Override
        public void onEmpty() {
            Log.d(Consts.TAG, "Pin empty");
        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {
            Log.d(Consts.TAG, "Pin changed, new length " + pinLength + " with intermediate pin " + intermediatePin);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_pin);
        ButterKnife.bind(this);
        setResult(RESULT_BACK_PRESSED);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            showFingerprint = (AnimatedVectorDrawable) getDrawable(R.drawable.show_fingerprint);
            fingerprintToTick = (AnimatedVectorDrawable) getDrawable(R.drawable.fingerprint_to_tick);
            fingerprintToCross = (AnimatedVectorDrawable) getDrawable(R.drawable.fingerprint_to_cross);
        }

        if (mSetPin) {
            changeLayoutForSetPin();
        } else {
            String pin = getPinFromSharedPreferences();
            if (pin.equals("")) {
                changeLayoutForSetPin();
                mSetPin = true;
            } else {
                //prepareSensor();
            }
        }

        mPinLockView.attachIndicatorDots(mIndicatorDots);
        mPinLockView.setPinLockListener(mPinLockListener);
        //mPinLockView.setCustomKeySet(new int[]{2, 3, 1, 5, 9, 6, 7, 0, 8, 4});
        //mPinLockView.enableLayoutShuffling();

        mPinLockView.setPinLength(PIN_LENGTH);
        mPinLockView.setTextColor(ContextCompat.getColor(this, R.color.white));

        mIndicatorDots.setIndicatorType(IndicatorDots.IndicatorType.FILL_WITH_ANIMATION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPreferences.contains(PIN_FP)) {
            prepareSensor();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mFingerprintHelper != null) {
            mFingerprintHelper.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_BACK_PRESSED);
        super.onBackPressed();
    }

    private void setPin(String pin) {
        if (mFirstPin.equals("")) {
            mFirstPin = pin;
            mTextTitle.setText(getString(R.string.pinlock_secondPin));
            mPinLockView.resetPinLockView();
        } else {
            if (pin.equals(mFirstPin)) {
                writePinToSharedPreferences(pin);
                prepareLogin(pin);
                changeLayoutForEnterAndCheckPin();
                //finish();
            } else {
                shake();
                mTextTitle.setText(getString(R.string.pinlock_tryagain));
                mPinLockView.resetPinLockView();
                mFirstPin = "";
            }
        }
    }

    //проверка введеного пин-кода
    private void checkPin(String pin) {
        // Create and save key
        Store store = new Store(getApplicationContext());
        // Get key
        SecretKey key = store.getSymmetricKey("test", null);

        // Decrypt data
        Crypto crypto = new Crypto(Options.TRANSFORMATION_SYMMETRIC);
        String text = "Sample text";

        String encoded = getPinFromSharedPreferences();
        String decryptedData = crypto.decrypt(encoded, key);
        Log.i("Scytale", "Decrypted data: " + decryptedData);

//        if (encoded.equals(getPinFromSharedPreferences())) {
        if (pin.equals(decryptedData)) {
            //пин-код верный
            prepareLogin(pin);
            mPinLockView.resetPinLockView();
            //finish();
        } else {
            //пин-код не верный
            shake();

//            mTryCount++;

            mTextAttempts.setText(getString(R.string.pinlock_wrongpin));
            mPinLockView.resetPinLockView();

//            if (mTryCount == 1) {
//                mTextAttempts.setText(getString(R.string.pinlock_firsttry));
//                mPinLockView.resetPinLockView();
//            } else if (mTryCount == 2) {
//                mTextAttempts.setText(getString(R.string.pinlock_secondtry));
//                mPinLockView.resetPinLockView();
//            } else if (mTryCount > 2) {
//                setResult(RESULT_TOO_MANY_TRIES);
//                finish();
//            }
        }
    }

    //встряхивание экрана
    private void shake() {
        new ObjectAnimator();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mPinLockView, "translationX",
                0, 25, -25, 25, -25, 15, -15, 6, -6, 0).setDuration(1000);
        objectAnimator.start();
    }

    private void changeLayoutForSetPin() {
        mImageViewFingerView.setVisibility(View.GONE);
        mTextFingerText.setVisibility(View.GONE);
        mTextAttempts.setVisibility(View.GONE);
        mTextTitle.setText(getString(R.string.pinlock_settitle));
    }

    private void changeLayoutForEnterAndCheckPin() {
        mPinLockView.resetPinLockView();
        mImageViewFingerView.setVisibility(View.VISIBLE);
        mTextFingerText.setVisibility(View.VISIBLE);
        mTextAttempts.setVisibility(View.VISIBLE);
    }

    private void writePinToSharedPreferences(String pin) {
        if (FingerprintUtils.isSensorStateAt(FingerprintUtils.mSensorState.READY, this)) {
            String encoded = CryptoUtils.encode(pin);
            //encoded=Utils.sha256(pin);
            mPreferences.edit().putString(PIN_FP, encoded).apply();
        }

        // Create and save key
        Store store = new Store(getApplicationContext());
        if (!store.hasKey("test")) {
            SecretKey key = store.generateSymmetricKey("test", null);
        }

        // Get key
        SecretKey key = store.getSymmetricKey("test", null);

        // Encrypt data
        Crypto crypto = new Crypto(Options.TRANSFORMATION_SYMMETRIC);

        String encryptedData = crypto.encrypt(pin, key);
        Log.i("Scytale", "Encrypted data: " + encryptedData);
        Log.i("Scytale", "Encrypted data: " + crypto.encrypt(pin, key));
        mPreferences.edit().putString(PIN, encryptedData).apply();
    }

    private String getPinFromSharedPreferences() {
        return mPreferences.getString(PIN, "");
    }

    private void prepareLogin(String pin) {
        if (pin.length() > 0) {
            writePinToSharedPreferences(pin);
            setResult(RESULT_OK);
            //startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "pin is empty", Toast.LENGTH_SHORT).show();
            Log.e(Consts.TAG, "Error: pin is empty");
        }
    }

    private void prepareSensor() {
        if (FingerprintUtils.isSensorStateAt(FingerprintUtils.mSensorState.READY, this)) {
            FingerprintManagerCompat.CryptoObject cryptoObject = CryptoUtils.getCryptoObject();
            if (cryptoObject != null) {
                Toast.makeText(this, "use fingerprint to login", Toast.LENGTH_LONG).show();
                mFingerprintHelper = new LoginPinActivity.FingerprintHelper(this);
                mFingerprintHelper.startAuth(cryptoObject);
            } else {
                mPreferences.edit().remove(PIN_FP).apply();
                Toast.makeText(this, "new fingerprint enrolled. enter pin again", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class FingerprintHelper extends FingerprintManagerCompat.AuthenticationCallback {
        private Context mContext;
        private CancellationSignal mCancellationSignal;

        FingerprintHelper(Context context) {
            mContext = context;
        }

        void startAuth(FingerprintManagerCompat.CryptoObject cryptoObject) {
            //сигнал используется, чтобы отменить режим считывания отпечатков (при сворачивании приложения, например)
            mCancellationSignal = new CancellationSignal();
            FingerprintManagerCompat manager = FingerprintManagerCompat.from(mContext);

            //Для того чтобы использовать сенсор, нужно воспользоваться методом
            manager.authenticate(cryptoObject, 0, mCancellationSignal, this, null);
            //мы создаем криптообъект и отправляем его на вход в authenticate()
            // для получения подтверждения использования ключа
        }

        void cancel() {
            if (mCancellationSignal != null) {
                mCancellationSignal.cancel();
            }
        }

        //результаты считывания сенсора мы получаем в методах коллбеках
        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            //несколько неудачных попыток считывания (5)
            //после этого сенсор станет недоступным на некоторое время (30 сек)
            Toast.makeText(mContext, errString, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            //грязные пальчики, недостаточно сильный зажим
            Toast.makeText(mContext, helpString, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
            //все прошло успешно
            //В случае успешного распознавания мы получаем AuthenticationResult,
            // из которого можем достать объект Cipher c уже подтвержденным ключом
            Cipher cipher = result.getCryptoObject().getCipher();
            String encoded = mPreferences.getString(PIN_FP, null);
            String decoded = CryptoUtils.decode(encoded, Objects.requireNonNull(cipher));
            //Toast.makeText(mContext, "success", Toast.LENGTH_SHORT).show();
            Animate.animate(mImageViewFingerView, fingerprintToTick);
            final Handler handler = new Handler();
//            handler.postDelayed(LoginPinActivity.this::finish, 750);
            handler.postDelayed(() -> prepareLogin(Objects.requireNonNull(decoded)), 750);
        }

        @Override
        public void onAuthenticationFailed() {
            //отпечаток считался, но не распознался
            //Toast.makeText(mContext, "try again", Toast.LENGTH_SHORT).show();
            Animate.animate(mImageViewFingerView, fingerprintToCross);
            final Handler handler = new Handler();
            handler.postDelayed(() -> Animate.animate(mImageViewFingerView, showFingerprint), 750);
        }
    }
}
