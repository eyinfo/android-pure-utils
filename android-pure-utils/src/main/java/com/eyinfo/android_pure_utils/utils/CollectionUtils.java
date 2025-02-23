package com.eyinfo.android_pure_utils.utils;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Author lijinghuan
 * Email:ljh0576123@163.com
 * CreateTime:2019-07-26
 * Description:集合工具类
 * Modifier:
 * ModifyContent:
 */
public class CollectionUtils {

    /**
     * 添加key-value到集合
     *
     * @param map   map
     * @param key   key
     * @param value value
     */
    public static void put(HashMap<String, Set<String>> map, String key, String value) {
        if (map == null || TextUtils.isEmpty(key)) {
            return;
        }
        if (map.containsKey(key)) {
            Set<String> lst = map.get(key);
            if (lst == null) {
                lst = new HashSet<>();
                lst.add(value);
                map.put(key, lst);
                return;
            }
            lst.add(value);
            map.put(key, lst);
            return;
        }
        Set<String> lst = new HashSet<>();
        lst.add(value);
        map.put(key, lst);
    }
}
