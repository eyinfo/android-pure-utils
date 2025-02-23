package com.eyinfo.android_pure_utils.encryption;

import android.text.TextUtils;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Author lijinghuan
 * Email:ljh0576123@163.com
 * CreateTime:2017/7/16
 * Description:
 * Modifier:
 * ModifyContent:
 */
public class AESEncrypt {
    private static AESEncrypt instance = null;

    public static AESEncrypt getInstance() {
        if (instance == null) {
            instance = new AESEncrypt();
        }
        return instance;
    }

    public String encrypt(byte[] content, String password) {
        try {
            if (content == null || TextUtils.isEmpty(password)) {
                return "";
            }
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] raw = password.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(content);
            return new String(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String encrypt(String content, String password) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        return encrypt(bytes, password);
    }

    public String decrypt(byte[] content, String password) {
        try {
            if (content == null || TextUtils.isEmpty(password)) {
                return "";
            }
            byte[] raw = password.getBytes();
            SecretKeySpec sKeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
            byte[] original = cipher.doFinal(content);
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String decrypt(String content, String password) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        return decrypt(bytes, password);
    }
}
