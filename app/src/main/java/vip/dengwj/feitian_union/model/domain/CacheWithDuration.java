package vip.dengwj.feitian_union.model.domain;

public class CacheWithDuration {
    private Long duration;
    private String cache;

    public CacheWithDuration() {
    }

    public CacheWithDuration(Long duration, String cache) {
        this.duration = duration;
        this.cache = cache;
    }

    /**
     * 获取
     * @return duration
     */
    public Long getDuration() {
        return duration;
    }

    /**
     * 设置
     * @param duration
     */
    public void setDuration(Long duration) {
        this.duration = duration;
    }

    /**
     * 获取
     * @return cache
     */
    public String getCache() {
        return cache;
    }

    /**
     * 设置
     * @param cache
     */
    public void setCache(String cache) {
        this.cache = cache;
    }

    public String toString() {
        return "CacheWithDuration{duration = " + duration + ", cache = " + cache + "}";
    }
}
