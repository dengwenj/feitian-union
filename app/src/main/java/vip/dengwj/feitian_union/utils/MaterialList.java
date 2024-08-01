package vip.dengwj.feitian_union.utils;

import java.util.ArrayList;
import java.util.List;

import vip.dengwj.feitian_union.model.domain.Categories;

public class MaterialList {
    public static List<Categories.DataBean> getMaterialList() {
        List<Categories.DataBean> dataBeans = new ArrayList<>();
        dataBeans.add(new Categories.DataBean(27446, "大面额折扣"));
        dataBeans.add(new Categories.DataBean(31362, "天天特卖"));
        dataBeans.add(new Categories.DataBean(84229, "天猫超市"));
        dataBeans.add(new Categories.DataBean(13367, "女装"));
        dataBeans.add(new Categories.DataBean(13370, "鞋包配饰"));
        dataBeans.add(new Categories.DataBean(13371, "美妆护理"));
        dataBeans.add(new Categories.DataBean(13369, "数码家电"));
        dataBeans.add(new Categories.DataBean(78393, "宠物"));
        dataBeans.add(new Categories.DataBean(13372, "男装"));
        dataBeans.add(new Categories.DataBean(13373, "内衣"));
        dataBeans.add(new Categories.DataBean(13376, "运动户外"));
        dataBeans.add(new Categories.DataBean(13374, "母婴"));

        return dataBeans;
    }
}
