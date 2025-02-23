package com.eyinfo.android_pure_utils.utils;

/**
 * Author lijinghuan
 * Email:ljh0576123@163.com
 * CreateTime:2020/8/25
 * Description:
 * Modifier:
 * ModifyContent:
 */
public class ParameterUtils {

    /**
     * 获取url参数
     *
     * @param uri       uri地址
     * @param paramName 参数名
     * @return 参数值
     */
    public static String getParams(String uri, String paramName) {
        if (ObjectJudge.isEmpty(paramName, uri)) {
            return "";
        }
        String pattern = String.format("(^|)%s=([^&]*)(|$)", paramName);
        String matche = ValidUtils.matche(pattern, uri);
        int index = matche.indexOf("=");
        if (index < 0) {
            return "";
        }
        String value = matche.substring(index + 1);
        return ConvertUtils.toString(value);
    }
}
