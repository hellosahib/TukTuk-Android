package tech.rtsproduction.tuktuk;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class AuthActivity extends AppCompatActivity {

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private TextView mLogin,mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        mLogin = findViewById(R.id.tv_login_auth);
        mRegister = findViewById(R.id.tv_register_auth);

        mPager = findViewById(R.id.pager_auth);
        mPagerAdapter = new AuthPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapSelected(mLogin,mRegister);
                mPager.setCurrentItem(0,true);
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapSelected(mRegister,mLogin);
                mPager.setCurrentItem(1,true);
            }
        });

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int i, float v, int i1) {}
            public void onPageScrollStateChanged(int i) {}
            @Override
            public void onPageSelected(int i) {
                if(i==0) swapSelected(mLogin,mRegister);
                if(i==1) swapSelected(mRegister,mLogin);
            }
        });

    }

    private void swapSelected(TextView t1,TextView t2){
        t1.setTextColor(getResources().getColor(R.color.colorAccent));
        t2.setTextColor(getResources().getColor(R.color.white));
    }
}
