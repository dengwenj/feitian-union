package vip.dengwj.feitian_union.ui.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import vip.dengwj.feitian_union.R;
import vip.dengwj.feitian_union.ui.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化视图
        initView();
        // 监听
        initListener();
    }

    private void initView() {
        bottomNavigationView = findViewById(R.id.main_navigation);

        // 把 fragment 放在 FrameLayout 里
        HomeFragment homeFragment = new HomeFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.main_page_container_frame_layout, homeFragment);
        transaction.commit();
    }

    private void initListener() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
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