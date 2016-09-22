package com.example.user.istpandroidproject;

import android.graphics.drawable.Drawable;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.user.istpandroidproject.model.Utils;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public class DrawerActivity extends AppCompatActivity implements Drawer.OnDrawerItemClickListener{

    AccountHeader headerResult = null;
    IProfile profile;
    Drawer naviDrawer;

    Fragment[] fragments;
    FragmentManager fragmentManager;

    final static int defaultSelectedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        fragments = new Fragment[3];
        fragments[0] = PokemonListFragment.newInstance();
        fragments[1] = TestFragment.newInstance("fake 1");
        fragments[2] = TestFragment.newInstance("fake 2");

        fragmentManager = getSupportFragmentManager();

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

        naviDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .inflateMenu(R.menu.drawer_view)
                .withOnDrawerItemClickListener(this)
                .withSavedInstance(savedInstanceState)
                .build();

        //select first item and fire OnItemClick
        naviDrawer.setSelectionAtPosition(defaultSelectedIndex + 1, true);
    }

    void displayFragment(Fragment fragment) {
        FragmentTransaction transaction =
                fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);

        transaction.addToBackStack(null);
        transaction.commit();
    }

    void buidDrawerHeader(boolean compact, Bundle savedInstanceState) {

        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(Utils.getDrawable(this, R.drawable.header))
                .withCompactStyle(compact)
                .addProfiles(profile)
                .withSavedInstance(savedInstanceState) //restore UI state from saveInstanceState
                .build();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        headerResult.saveInstanceState(outState); //save headerResult's UI state into outState
        naviDrawer.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        displayFragment(fragments[position - 1]);
        return false; //return false to bound back the drawer after clicking one of items
    }
}
