package com.eyinfo.android_pure_utils.encryption;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Author lijinghuan
 * Email:ljh0576123@163.com
 * CreateTime:2019-06-28
 * Description:md5
 * Modifier:
 * ModifyContent:
 */
public class Md5Utils {

    /**
     * md5加密
     *
     * @param content 加密内容
     * @return md5
     */
    public static String encrypt(String content) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return byte2hex(md.digest(content.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 16位md5加密(从第9位到25位)
     *
     * @param content 加密内容
     * @return md5
     */
    public static String encryptForHex(String content) {
        String encrypt = encrypt(content);
        if (TextUtils.isEmpty(encrypt)) {
            return "";
        }
        if (encrypt.length() >= 25) {
            return encrypt.substring(8, 24);
        }
        return encrypt;
    }

    private static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp = "";
        for (byte value : b) {
            stmp = (Integer.toHexString(value & 0XFF));
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }
        }
        return hs.toString();
    }

    /**
     * 文件md5
     *
     * @param file file
     * @return md5 value
     */
    public static String md5ForFile(File file) {
        FileInputStream fis;
        byte[] buffer = new byte[1024];
        int numRead = 0;
        MessageDigest md5;
        try {
            fis = new FileInputStream(file);
            md5 = MessageDigest.getInstance("MD5");
            while ((numRead = fis.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }
            fis.close();
            return byte2hex(md5.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * sha1加密
     *
     * @param content 加密内容
     * @return sha1加密后的字符
     */
    public static String sha1(String content) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(content.getBytes());
            return byte2hex(digest.digest());
        } catch (Exception e) {
            return "";
        }
    }
}
