package vip.dengwj.feitian_union.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import vip.dengwj.feitian_union.R;
import vip.dengwj.feitian_union.base.BaseFragment;
import vip.dengwj.feitian_union.databinding.ActivityMainBinding;
import vip.dengwj.feitian_union.ui.fragment.HomeFragment;
import vip.dengwj.feitian_union.ui.fragment.RedPackedFragment;
import vip.dengwj.feitian_union.ui.fragment.SearchFragment;
import vip.dengwj.feitian_union.ui.fragment.SelectedFragment;

public class MainActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitymainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activitymainBinding.getRoot());

        // 初始化 fragment
        initFragment();
        // 监听
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
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 切换 fragment
        transaction.replace(R.id.main_page_container_frame_layout, fragment);
        transaction.commit();
    }

    private void initListener() {
        activitymainBinding.mainNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                switchFragment(homeFragment);
            } else if (itemId == R.id.selected) {
                switchFragment(selectedFragment);
            } else if (itemId == R.id.red_packet) {
                switchFragment(redPackedFragment);
            } else if (itemId == R.id.search) {
                switchFragment(searchFragment);
            }

            return true;
        });
    }
}