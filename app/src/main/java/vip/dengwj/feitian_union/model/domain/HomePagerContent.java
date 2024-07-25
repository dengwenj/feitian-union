package vip.dengwj.feitian_union.model.domain;

import java.util.List;

public class HomePagerContent {
    private boolean success;
    private int code;
    private String msg;
    private DataBean data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int total;
        private int pageSize;
        private int currentPage;
        private boolean hasNext;
        private boolean hasPre;
        private int totalPage;
        private List<ListBean> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public boolean isHasNext() {
            return hasNext;
        }

        public void setHasNext(boolean hasNext) {
            this.hasNext = hasNext;
        }

        public boolean isHasPre() {
            return hasPre;
        }

        public void setHasPre(boolean hasPre) {
            this.hasPre = hasPre;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private String cover;
            private String source;
            private String title;
            private double couponAmount;
            private String zkFinalPrice;
            private String couponShareUrl;
            private int couponTotalCount;
            private int couponRemainCount;
            private int sellCount;
            private String justPrice;

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public double getCouponAmount() {
                return couponAmount;
            }

            public void setCouponAmount(int couponAmount) {
                this.couponAmount = couponAmount;
            }

            public String getZkFinalPrice() {
                return zkFinalPrice;
            }

            public void setZkFinalPrice(String zkFinalPrice) {
                this.zkFinalPrice = zkFinalPrice;
            }

            public String getCouponShareUrl() {
                return couponShareUrl;
            }

            public void setCouponShareUrl(String couponShareUrl) {
                this.couponShareUrl = couponShareUrl;
            }

            public int getCouponTotalCount() {
                return couponTotalCount;
            }

            public void setCouponTotalCount(int couponTotalCount) {
                this.couponTotalCount = couponTotalCount;
            }

            public int getCouponRemainCount() {
                return couponRemainCount;
            }

            public void setCouponRemainCount(int couponRemainCount) {
                this.couponRemainCount = couponRemainCount;
            }

            public int getSellCount() {
                return sellCount;
            }

            public void setSellCount(int sellCount) {
                this.sellCount = sellCount;
            }

            public String getJustPrice() {
                return justPrice;
            }

            public void setJustPrice(String justPrice) {
                this.justPrice = justPrice;
            }

            @Override
            public String toString() {
                return "ListBean{" +
                        "cover='" + cover + '\'' +
                        ", source='" + source + '\'' +
                        ", title='" + title + '\'' +
                        ", couponAmount=" + couponAmount +
                        ", zkFinalPrice='" + zkFinalPrice + '\'' +
                        ", couponShareUrl='" + couponShareUrl + '\'' +
                        ", couponTotalCount=" + couponTotalCount +
                        ", couponRemainCount=" + couponRemainCount +
                        ", sellCount=" + sellCount +
                        ", justPrice='" + justPrice + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "total=" + total +
                    ", pageSize=" + pageSize +
                    ", currentPage=" + currentPage +
                    ", hasNext=" + hasNext +
                    ", hasPre=" + hasPre +
                    ", totalPage=" + totalPage +
                    ", list=" + list +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "HomePagerContent{" +
                "success=" + success +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
