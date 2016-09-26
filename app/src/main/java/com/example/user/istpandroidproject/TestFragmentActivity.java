package com.example.user.istpandroidproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TestFragmentActivity extends AppCompatActivity implements View.OnClickListener{

    Fragment[] fragments;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fragment);

        Button f1Btn = (Button)findViewById(R.id.fragmentBtn1);
        Button f2Btn = (Button)findViewById(R.id.fragmentBtn2);

        f1Btn.setOnClickListener(this);
        f2Btn.setOnClickListener(this);

        fragments = new Fragment[2];
        fragments[0] = TestFragment.newInstance("fragment 1");
        fragments[1] = TestFragment.newInstance("fragment 2");

        fragmentManager = getSupportFragmentManager();

    }

    void displayFragment(Fragment fragment) {
        FragmentTransaction transaction =
                fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);

        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if(viewId == R.id.fragmentBtn1) {
            displayFragment(fragments[0]);
        }
        else if(viewId == R.id.fragmentBtn2) {
            displayFragment(fragments[1]);
        }
    }

    @Override
    public void onBackPressed() {
        if(fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }
}
