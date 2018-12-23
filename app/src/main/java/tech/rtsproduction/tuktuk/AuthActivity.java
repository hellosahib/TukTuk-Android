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
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1, true);
        }
    }

    public void swapSelected(TextView t1,TextView t2){
        t1.setTextColor(getResources().getColor(R.color.colorAccent));
        t2.setTextColor(getResources().getColor(R.color.white));
    }
}
