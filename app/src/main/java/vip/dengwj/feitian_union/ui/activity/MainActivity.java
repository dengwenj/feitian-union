package vip.dengwj.feitian_union.ui.activity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import vip.dengwj.feitian_union.R;
import vip.dengwj.feitian_union.base.BaseActivity;
import vip.dengwj.feitian_union.base.BaseFragment;
import vip.dengwj.feitian_union.databinding.ActivityMainBinding;
import vip.dengwj.feitian_union.ui.fragment.HomeFragment;
import vip.dengwj.feitian_union.ui.fragment.RedPackedFragment;
import vip.dengwj.feitian_union.ui.fragment.SearchFragment;
import vip.dengwj.feitian_union.ui.fragment.SelectedFragment;
import vip.dengwj.feitian_union.utils.LogUtils;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements IMainActivity {
    /**
     * 首先需要知道一个关键点，在启用ViewBinding后。每一个layout文件都会自动生成一份Java类。它会自动根据下划线进行驼峰命名。
     * 比如一个叫 activity_main_demo.xml 的布局文件，它对应自动生成的类叫ActivityMainDemoBinding。
     * 这就意味着我们可以在任何需要导入布局的地方都使用ViewBinding。
     */
    // 用的 ViewBinding，就可以不用 findById 了。mainBinding 就是对应的布局文件
    private ActivityMainBinding activitymainBinding;
    private HomeFragment homeFragment;
    private SelectedFragment selectedFragment;
    private RedPackedFragment redPackedFragment;
    private SearchFragment searchFragment;
    private FragmentManager fragmentManager;
    private BaseFragment lastFragment;

    private boolean isSwitch2Search = false;

    @Override
    public void initView() {
        // 初始化 fragment
        initFragment();
    }

    @Override
    public ActivityMainBinding getBinding() {
        activitymainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        return activitymainBinding;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initEvent() {
        // 事件
        initListener();
    }

    private void initFragment() {
        homeFragment = new HomeFragment();
        selectedFragment = new SelectedFragment();
        redPackedFragment = new RedPackedFragment();
        searchFragment = new SearchFragment();
        fragmentManager = getSupportFragmentManager();
        // 初始化的 fragment
        switchFragment(homeFragment);
    }

    private void switchFragment(BaseFragment fragment) {
        // 点相同的
        if (lastFragment == fragment) {
            return;
        }

        // 修改成显示和隐藏方式，缓存起来
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!fragment.isAdded()) {
            transaction.add(R.id.main_page_container_frame_layout, fragment);
        } else {
            transaction.show(fragment);
        }

        if (lastFragment != null) {
            transaction.hide(lastFragment);
        }
        lastFragment = fragment;
        // 切换 fragment
        // transaction.replace(R.id.main_page_container_frame_layout, fragment);
        transaction.commit();
    }

    private void initListener() {
        activitymainBinding.tabbar.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.nav_home) {
                switchFragment(homeFragment);
            } else if (checkedId == R.id.nav_selected) {
                switchFragment(selectedFragment);
            } else if (checkedId == R.id.nav_red_packet) {
                switchFragment(redPackedFragment);
            } else if (checkedId == R.id.nav_search) {
                switchFragment(searchFragment);
            }
        });
    }

    /**
     * 首页搜索跳转到搜索 bar
     */
    @Override
    public void switch2Search() {
        isSwitch2Search = true;
        activitymainBinding.tabbar.getTabBarView().check(R.id.nav_search);
    }

    @Override
    public boolean getIsSwitch2Search() {
        return isSwitch2Search;
    }

    @Override
    public void setIsSwitch2Search(boolean isSwitch2Search) {
        this.isSwitch2Search = isSwitch2Search;
    }
}