package com.example.user.istpandroidproject;

import android.graphics.drawable.Drawable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.user.istpandroidproject.model.Utils;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public class DrawerActivity extends AppCompatActivity {

    AccountHeader headerResult = null;
    IProfile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String profileName = "batman";
        String profileEmail = "batman@gmail.com";
        Drawable profileIcon = Utils.getDrawable(this,
                R.drawable.profile3);

        profile = new ProfileDrawerItem()
                .withName(profileName)
                .withEmail(profileEmail)
                .withIcon(profileIcon);

        buidDrawerHeader(true, savedInstanceState);


    }

    void buidDrawerHeader(boolean compact, Bundle savedInstanceState) {

        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(Utils.getDrawable(this, R.drawable.header))
                .withCompactStyle(compact)
                .addProfiles(profile)
                .withSavedInstance(savedInstanceState)
                .build();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

}
