package com.eyinfo.android_pure_utils.utils;

import android.text.TextUtils;

/**
 * Author lijinghuan
 * Email:ljh0576123@163.com
 * CreateTime:2015-11-23 下午3:55:20
 * Description: 目录、路径处理类(http://stackoverflow.com/questions/412380/combine-paths
 * -in-java)
 * Modifier:
 * ModifyContent:
 */
public class PathsUtils {

    /**
     * 组合路径
     * <p>
     * param paths 要组合的地址列表
     * return
     */
    public static String combine(String... paths) {
        if (paths == null || paths.length == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < paths.length; i++) {
            String path = paths[i];
            if (path == null) {
                path = "";
            }
            if (builder.length() > 0) {
                String uri = builder.toString();
                if (uri.endsWith("/")) {
                    if (path.startsWith("/")) {
                        builder.append(path.substring(1));
                    } else {
                        builder.append(path);
                    }
                } else {
                    if (path.startsWith("/")) {
                        builder.append(path);
                    } else {
                        builder.append("/").append(path);
                    }
                }
            } else {
                builder.append(path);
            }
        }
        String uri = builder.toString();
        if (!uri.contains("://") && uri.contains(":/")) {
            uri = uri.replace(":/", "://");
        }
        return uri;
    }

    /**
     * 包含前后斜线(输入示例:/path0/path1)
     *
     * @param path path
     * @return eg. /path0/path1/
     */
    public static String slash(String path) {
        if (TextUtils.isEmpty(path)) {
            return "";
        }
        path = path.replaceAll("\\r|\\n|\\s|\\t", path);
        boolean start = false, end = false;
        if (!path.startsWith("/")) {
            start = true;
        }
        if (!path.endsWith("/")) {
            end = true;
        }
        if (start || end) {
            path = String.format("%s%s%s", (start ? "/" : ""), path, (end ? "/" : ""));
        }
        return path;
    }

    /**
     * 替换最后路径名
     * (示例:http://xxxx/xx/[name?...|name2 -> newName[?...]])
     *
     * @param path            原路径
     * @param replacePathName 替换路径名
     * @return 已替换路径
     */
    public static String replaceLastPathName(String path, String replacePathName) {
        if (TextUtils.isEmpty(path) || TextUtils.isEmpty(replacePathName)) {
            return "";
        }
        String mpath;
        String params = "";
        int quepos = path.indexOf("?");
        if (quepos > 0) {
            mpath = path.substring(0, quepos);
            params = path.substring(quepos);
        } else {
            mpath = path;
        }
        StringBuilder builder = new StringBuilder();
        int pathpos = path.lastIndexOf("/");
        if (pathpos < 0) {
            return builder.append(mpath).append(params).toString();
        }
        String presub = path.substring(0, pathpos);
        builder.append(presub).append("/").append(replacePathName).append(params);
        return builder.toString();
    }
}
