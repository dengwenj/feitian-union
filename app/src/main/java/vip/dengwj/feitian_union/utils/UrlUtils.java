package vip.dengwj.feitian_union.utils;

public class UrlUtils {
    public static String createHomePagerUrl(int categoryId, int page) {
        return "shop/m/"+ page + "/" + categoryId;
    }
}
