package com.eyinfo.android_pure_utils.utils;

import android.text.TextUtils;

import com.eyinfo.android_pure_utils.GsonParameterizedType;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonUtils {

    /**
     * 将对象转为json
     *
     * @param object 要转换的对象
     * @return json
     */
    public static String toJson(Object object) {
        if (object == null) {
            return "";
        }
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    /**
     * 解析json数据至对象
     *
     * @param json                   json数据
     * @param clazz                  类对象
     * @param isAssociatedAssignment true-解析后对带有{@link com.cloud.libsdk.objects.annotations.OriginalField}注解的属性进行关联赋值,false-不处理此类逻辑;
     * @param <T>                    泛型
     * @return 对象
     */
    public static <T> T parseT(String json, Class<T> clazz) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, clazz);
        } catch (JsonSyntaxException e) {
            return newNull(clazz);
        }
    }

    /**
     * 解析json数据至对象
     *
     * @param json  json数据
     * @param clazz 类对象
     * @param <T>   泛型
     * @return 对象
     */
    @Deprecated
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, clazz);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    /**
     * 将json数据解析成列表
     *
     * @param json  json数据
     * @param clazz 类class对象
     * @param <T>   泛型
     * @return 集合
     */
    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        try {
            Gson gson = new Gson();
            Type type = new GsonParameterizedType(clazz);
            return gson.fromJson(json, type);
        } catch (JsonSyntaxException e) {
            return new ArrayList<T>(0);
        }
    }

    /**
     * 根据class实例化对象
     *
     * @param clazz 类class对象
     * @param <T>   泛型
     * @return 对象
     */
    public static <T> T newNull(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            // json parase error
        }
        return null;
    }

    /**
     * json中是否包含指定的键
     *
     * @param keyName 键名称
     * @return true-包含;false-不包含;
     */
    public static boolean containerKey(String keyName, String jsonString) {
        if (ObjectJudge.isEmptyString(keyName)) {
            return false;
        }
        if (ObjectJudge.isEmptyJson(jsonString)) {
            return false;
        }
        String regex = "(((\"" + keyName + "\")|('" + keyName + "')):(.*?)((\\,\\|\\})(\\s\\S)*))";
        Matcher matcher = Pattern.compile(regex).matcher(jsonString);
        return matcher.find();
    }

    /**
     * 获取json指定key对应的值
     *
     * @param keyName           json键
     * @param jsonString        json
     * @param isFilltWhitespace true-过滤空格;false-不做过滤;
     * @return 返回对应的值
     */
    public static String getValue(String keyName, String jsonString, boolean isFilltWhitespace) {
        if (ObjectJudge.isEmptyString(keyName) || ObjectJudge.isEmptyString(jsonString)) {
            return "";
        }
        if (isFilltWhitespace) {
            jsonString = jsonString.replaceAll("\\r|\\n|\\s|\\t", "");
        }
        String regex = "((\"" + keyName + "\")|('" + keyName + "')):(((\\[(.+)\\](\\,|\\}))|(\\{(.+)\\}(\\,|\\})))|((.*?)((\\,|\\})(\\s\\S)*)))";
        Matcher matcher = Pattern.compile(regex).matcher(jsonString);
        String value = "";
        while (matcher.find()) {
            //避免null出错
            value = (matcher.group() + "").trim();
            //根据:分隔
            int index = value.indexOf(":");
            if (index < 0 || (index + 1) == value.length()) {
                continue;
            }
            value = value.substring(index + 1).trim();
            int start = 0;
            if (value.startsWith("\"") || value.startsWith("'")) {
                start = 1;
            }
            //去掉前面引号
            //去掉最后一个字符包含的,或}
            value = value.substring(start, value.length() - 1);
            value = value.trim();
            //去掉后面引号
            int end = value.length();
            if (value.endsWith("\"") || value.endsWith("'")) {
                end -= 1;
            }
            value = value.substring(0, end);
            break;
        }
        return value;
    }

    /**
     * 获取json指定key对应的值
     *
     * @param keyName    json键
     * @param jsonString json
     * @return 返回对应的值
     */
    public static String getValue(String keyName, String jsonString) {
        return getValue(keyName, jsonString, true);
    }

    /**
     * 获取json指定key对应的值
     *
     * @param relationKeys json键关系(如key或key->key1->key2),根据json关系指定
     * @param jsonString   json
     * @return 返回对应的值
     */
    public static String getAccurateValue(String relationKeys, String jsonString) {
        if (ObjectJudge.isEmptyString(relationKeys)) {
            return "";
        }
        if (ObjectJudge.isEmptyJson(jsonString)) {
            return "";
        }
        String[] split = relationKeys.split("->");
        if (ObjectJudge.isNullOrEmpty(split)) {
            return "";
        }
        String value = "";
        String subJson = jsonString.replaceAll("\\r|\\n|\\s|\\t", "");
        for (int i = 0; i < split.length; i++) {
            if ((i + 1) == split.length) {
                value = getValue(split[i], subJson);
            } else {
                subJson = getValue(split[i], subJson);
            }
        }
        return value;
    }

    /**
     * 解析key-value对象
     *
     * @param kvJson
     * @return
     */
    public static Map<String, Object> parseKV(String kvJson) {
        Map<String, Object> map = new HashMap<>();
        if (TextUtils.isEmpty(kvJson)) {
            return map;
        }
        String pattern = "(\\w+)=([^&,{}]*)";
        List<String> matches = ValidUtils.matches(pattern, kvJson);
        for (String match : matches) {
            if (TextUtils.isEmpty(match)) {
                continue;
            }
            String[] split = match.split("=");
            if (split.length != 2) {
                map.put(split[0], "");
            } else {
                map.put(split[0], ParameterUtils.getParams(match, split[0]));
            }
        }
        return map;
    }
}
