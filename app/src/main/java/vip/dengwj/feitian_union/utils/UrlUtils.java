package vip.dengwj.feitian_union.utils;

public class UrlUtils {
    public static String createHomePagerUrl(int categoryId, int page) {
        return "shop/m/"+ page + "/" + categoryId;
    }

    public static String getCoverPath(String path, int size) {
        if (path.startsWith("http")) {
            return path + "_" + size + "x" + size + ".jpg";
        }
        return "https:" + path + "_" + size + "x" + size + ".jpg";
    }

    public static String youLikeUrl(int page) {
        return "/shop/r/" + page;
    }
}
