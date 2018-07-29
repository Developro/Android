package com.studios.uio443.cluck.presentation.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.Nullable;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.util.Base64;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

/**
 * Created by azret.magometov on 08-Nov-16.
 * https://habr.com/company/e-Legion/blog/317706/
 */
@TargetApi(Build.VERSION_CODES.M)
public final class CryptoUtils {
    private static final String TAG = CryptoUtils.class.getSimpleName();

    private static final String KEY_ALIAS = "key_for_pin";
    private static final String KEY_STORE = "AndroidKeyStore";
    private static final String TRANSFORMATION = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

    private static KeyStore sKeyStore;
    private static KeyPairGenerator sKeyPairGenerator;
    private static Cipher sCipher;

    private CryptoUtils() {
    }

    //метод, который зашифровывает строку аргумент
    //В результате мы получаем Base64-строку, которую можно спокойно хранить в преференсах приложения.
    public static String encode(String inputString) {
        try {
            if (prepare() && initCipher(Cipher.ENCRYPT_MODE)) {
                //Преобразование входных данных в массивы байт
                byte[] bytes = sCipher.doFinal(inputString.getBytes());
                return Base64.encodeToString(bytes, Base64.NO_WRAP);
            }
        } catch (IllegalBlockSizeException | BadPaddingException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    //Для расшифровки же используем следующий метод
    public static String decode(String encodedString, Cipher cipher) {
        try {
            //Преобразование входных данных в массивы байт
            byte[] bytes = Base64.decode(encodedString, Base64.NO_WRAP);
            return new String(cipher.doFinal(bytes));
        } catch (IllegalBlockSizeException | BadPaddingException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private static boolean prepare() {
        return getKeyStore() && getCipher() && getKey();
    }

    //кейстор хранит только криптографические ключи.
    //Пароли, пин и другие приватные данные там хранить нельзя.
    private static boolean getKeyStore() {
        try {
            sKeyStore = KeyStore.getInstance(KEY_STORE);
            sKeyStore.load(null);
            return true;
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            e.printStackTrace();
        }
        return false;
    }

    //На выбор у нас два варианта ключей: симметричный ключ и пара из публичного и приватного ключа.
    // Из соображений UX мы воспользуемся парой. Это позволит нам отделить ввод отпечатка от шифрования пин-кода.
    //Ключи мы будем доставать из кейстора, но сначала нужно их туда положить.
    // Для создания ключа воспользуемся генератором.
    @TargetApi(Build.VERSION_CODES.M)
    private static boolean getKeyPairGenerator() {
        try {
            //При инициализации мы указываем, в какой кейстор пойдут сгенерированные ключи
            // и для какого алгоритма предназначен этот ключ.
            sKeyPairGenerator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, KEY_STORE);
            return true;
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Инициализируем объект Cipher
    @SuppressLint("GetInstance")
    private static boolean getCipher() {
        try {
            sCipher = Cipher.getInstance(TRANSFORMATION);
            return true;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Проверять наличие ключа будем следующим образом
    private static boolean getKey() {
        try {
            return sKeyStore.containsAlias(KEY_ALIAS) || generateNewKey();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private static boolean generateNewKey() {
        if (getKeyPairGenerator()) {
            try {
                sKeyPairGenerator.initialize(
                        new KeyGenParameterSpec.Builder(KEY_ALIAS, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                                .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_OAEP)
                                .setUserAuthenticationRequired(true)
                                .build());
                //KEY_ALIAS — это псевдоним ключа, по которому мы будем выдергивать его из кейстора, обычный psfs
                //.setUserAuthenticationRequired(true) — этот флаг указывает, что каждый раз,
                // когда нам нужно будет воспользоваться ключом, нужно будет подтвердить себя, в нашем случае — с помощью отпечатка
                sKeyPairGenerator.generateKeyPair();
                return true;
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private static boolean initCipher(int mode) {
        try {
            sKeyStore.load(null);

            switch (mode) {
                case Cipher.ENCRYPT_MODE:
                    initEncodeCipher(mode);
                    break;

                case Cipher.DECRYPT_MODE:
                    initDecodeCipher(mode);
                    break;
                default:
                    return false; //this cipher is only for encode\decode
            }
            return true;

        } catch (KeyPermanentlyInvalidatedException exception) {
            //если по какой-то причине ключ нельзя использовать, выстреливает это исключение
            // Возможные причины — добавление нового отпечатка к существующему, смена или полное удаление блокировки.
            // Тогда ключ более не имеет смысла хранить, и мы его удаляем.
            deleteInvalidKey();

        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException |
                NoSuchAlgorithmException | InvalidKeyException | InvalidKeySpecException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void initDecodeCipher(int mode) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException, InvalidKeyException {
        PrivateKey key = (PrivateKey) sKeyStore.getKey(KEY_ALIAS, null);
        sCipher.init(mode, key);
    }

    //Нетрудно заметить, что зашифровывающий Cipher несколько сложнее инициализировать.
    // Это косяк самого Гугла, суть которого в том, что публичный ключ требует подтверждения пользователя.
    // Мы обходим это требование с помощью слепка ключа (костыль, ага).
    private static void initEncodeCipher(int mode) throws KeyStoreException, InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        PublicKey key = sKeyStore.getCertificate(KEY_ALIAS).getPublicKey();

        // workaround for using public key
        // from https://developer.android.com/reference/android/security/keystore/KeyGenParameterSpec.html
        PublicKey unrestricted = KeyFactory.getInstance(key.getAlgorithm()).generatePublic(new X509EncodedKeySpec(key.getEncoded()));
        // from https://code.google.com/p/android/issues/detail?id=197719
        OAEPParameterSpec spec = new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA1, PSource.PSpecified.DEFAULT);

        sCipher.init(mode, unrestricted, spec);
    }

    public static void deleteInvalidKey() {
        if (getKeyStore()) {
            try {
                sKeyStore.deleteEntry(KEY_ALIAS);
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }
        }
    }

    //CryptoObject в данном случае используется как обертка для Cipher'a. Чтобы его получить, используем метод
    //криптообъект создается из расшифровывающего Cipher
    //Если этот Cipher прямо сейчас отправить в метод decode(), то вылетит исключение,
    // оповещающее о том, что мы пытаемся использовать ключ без подтверждения.
    // Строго говоря, мы создаем криптообъект и отправляем его на вход в authenticate()
    // как раз для получения этого самого подтверждения.
    //Если getCryptoObject() вернул null, то это значит,
    // что при инициализации Chiper'а произошел KeyPermanentlyInvalidatedException.
    // Тут уже ничего не поделаешь, кроме как дать пользователю знать,
    // что вход по отпечатку недоступен и ему придется заново ввести пин-код.
    @Nullable
    public static FingerprintManagerCompat.CryptoObject getCryptoObject() {
        if (prepare() && initCipher(Cipher.DECRYPT_MODE)) {
            return new FingerprintManagerCompat.CryptoObject(sCipher);
        }
        return null;
    }

    @Nullable
    public static Cipher getCipherForDecrypt() {
        if (prepare() && initCipher(Cipher.DECRYPT_MODE)) {
            return sCipher;
        }
        return null;
    }
}

