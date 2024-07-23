package vip.dengwj.feitian_union.ui.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import vip.dengwj.feitian_union.R;
import vip.dengwj.feitian_union.databinding.ActivityMainBinding;
import vip.dengwj.feitian_union.ui.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {
    /**
     * 首先需要知道一个关键点，在启用ViewBinding后。每一个layout文件都会自动生成一份Java类。它会自动根据下划线进行驼峰命名。
     * 比如一个叫 activity_main_demo.xml 的布局文件，它对应自动生成的类叫ActivityMainDemoBinding。
     * 这就意味着我们可以在任何需要导入布局的地方都使用ViewBinding。
     */
    // 用的 ViewBinding，就可以不用 findById 了。mainBinding 就是对应的布局文件
    private ActivityMainBinding activitymainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitymainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activitymainBinding.getRoot());

        // 初始化视图
        initView();
        // 监听
        initListener();
    }

    private void initView() {
        // 把 fragment 放在 FrameLayout 里
        HomeFragment homeFragment = new HomeFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.main_page_container_frame_layout, homeFragment);
        transaction.commit();
    }

    private void initListener() {
        activitymainBinding.mainNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                Log.d("pumu", "1" + item.getTitle());
            } else if (itemId == R.id.selected) {
                Log.d("pumu", "2" + item.getTitle());
            } else if (itemId == R.id.red_packet) {
                Log.d("pumu", "3" + item.getTitle());
            } else if (itemId == R.id.search) {
                Log.d("pumu", "4" + item.getTitle());
            }

            return true;
        });
    }
}