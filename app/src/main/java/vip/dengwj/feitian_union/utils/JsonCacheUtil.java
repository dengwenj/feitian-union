package vip.dengwj.feitian_union.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import vip.dengwj.feitian_union.base.BaseApplication;
import vip.dengwj.feitian_union.model.domain.CacheWithDuration;

public class JsonCacheUtil {
    public static final String JSON_CACHE_SP_NAME = "JSON_CACHE_SP_NAME";

    private static JsonCacheUtil jsonCacheUtil = null;
    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    private JsonCacheUtil() {
        sharedPreferences = BaseApplication.getAppContent().getSharedPreferences(JSON_CACHE_SP_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveVal(String key, Object val) {
        saveVal(key, val, -1L);
    }

    public void saveVal(String key, Object val, long duration) {
        SharedPreferences.Editor edit = sharedPreferences.edit();

        String valStr = gson.toJson(val);

        // 方便后续判断时间是否过期
        if (duration != -1) {
            duration += System.currentTimeMillis();
        }
        CacheWithDuration cacheWithDuration = new CacheWithDuration(duration, valStr);
        String json = gson.toJson(cacheWithDuration);
        edit.putString(key, json);

        edit.apply();
    }

    public void delVal(String key) {
        sharedPreferences.edit().remove(key).apply();
    }

    public <T> T getVal(String key, Class<T> tClass) {
        String json = sharedPreferences.getString(key, null);
        if (json == null) {
            return null;
        }

        CacheWithDuration cacheWithDuration = gson.fromJson(json, CacheWithDuration.class);
        Long duration = cacheWithDuration.getDuration();
        if (duration != -1 && duration <= System.currentTimeMillis()) {
            // 时间过期
            delVal(key);
            return null;
        }
        // 没有过期
        String cache = cacheWithDuration.getCache();
        return gson.fromJson(cache, tClass);
    }

    public static JsonCacheUtil getInstance() {
        if (jsonCacheUtil == null) {
            jsonCacheUtil = new JsonCacheUtil();
        }

        return jsonCacheUtil;
    }
}
