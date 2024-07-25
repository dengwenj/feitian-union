package vip.dengwj.feitian_union.base;

public interface BasePresenter<T> {
    // 注册 UI 通知接口
    void registerCallback(T homeCallback);

    // 取消 UI 通知接口
    void unregisterCallback(T homeCallback);
}
