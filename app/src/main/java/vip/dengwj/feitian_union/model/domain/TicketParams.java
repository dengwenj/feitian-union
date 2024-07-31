package vip.dengwj.feitian_union.model.domain;

public class TicketParams {
    private String url;
    private String title;

    public TicketParams() {
    }

    public TicketParams(String url, String title) {
        this.url = url;
        this.title = title;
    }

    /**
     * 获取
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public String toString() {
        return "TicketParams{url = " + url + ", title = " + title + "}";
    }
}
