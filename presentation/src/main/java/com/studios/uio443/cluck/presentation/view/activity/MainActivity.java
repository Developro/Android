package com.studios.uio443.cluck.presentation.view.activity;

import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.studios.uio443.cluck.presentation.R;
import com.studios.uio443.cluck.presentation.mvp.MainActivityVP;
import com.studios.uio443.cluck.presentation.presenter.MainActivityPresenter;
import com.studios.uio443.cluck.presentation.presenter.PresenterManager;
import com.studios.uio443.cluck.presentation.util.Consts;
import com.studios.uio443.cluck.presentation.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.studios.uio443.cluck.presentation.util.Consts.PERMISSION_REQUEST_CODE;

/**
 * Created by zundarik on 29.07.2018
 */

public class MainActivity extends BaseActivity implements
        MainActivityVP.View,
        NavigationView.OnNavigationItemSelectedListener {

    public static final String FRAGMENT_ID = "fragment_id";
    public static final int RESULT_BACK_PRESSED = RESULT_FIRST_USER;
    private static long back_pressed;
    MainActivityPresenter presenter;
    int fragmentId;
    boolean persmissionsGranted = false;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(Consts.TAG, "MainActivity.onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            fragmentId = R.id.nav_main;
            presenter = new MainActivityPresenter();
        } else {
            fragmentId = savedInstanceState.getInt(FRAGMENT_ID, R.id.nav_main);
            presenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }

        presenter.bindView(this);

        //вызов активити блокировки приложения/экрана
        //presenter.getLoginActivity();

        initDrawer();
        initNavigationView();

/*
    persmissionsGranted = checkMultiplePermissions(new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
    });
*/

        /*Select startup fragment*/
        //int fragmentId = pref.getInt(FRAGMENT_ID, R.id.nav_notes);
        navigationView.getMenu().performIdentifierAction(fragmentId, 0);
    }

    private void initDrawer() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerStateChanged(int newState) {
                if (newState == DrawerLayout.STATE_SETTLING && !drawer.isDrawerOpen(navigationView)) {
                    Log.d(Consts.TAG, "Drawer is being opened!!! Supress keyboard!");
                    View view = getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        try {
                            Thread.currentThread();
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        assert imm != null;
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initNavigationView() {
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //pref.edit().putInt(FRAGMENT_ID, id).apply();

        selectDrawerNavItem(id);

        ((NavigationView) findViewById(R.id.nav_view)).setCheckedItem(id);
        item.setChecked(true);
        setTitle(item.getTitle());

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void selectDrawerNavItem(int id) {
        String nameItem = "";
        switch (id) {
            case R.id.nav_main: {
                nameItem = "nav_main";
                break;
            }
            case R.id.nav_settings: {
                nameItem = "nav_settings";
                break;
            }
            case R.id.nav_about: {
                nameItem = "nav_about";
                break;
            }
            case R.id.nav_feedback: {
                nameItem = "nav_notes";
                break;
            }
        }
        invalidateOptionsMenu();
        presenter.selectDrawerNavItem(nameItem);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.bindView(this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(Consts.TAG, "MainActivity.onSaveInstanceState");
        super.onSaveInstanceState(outState);

        outState.putInt(FRAGMENT_ID, fragmentId);
        PresenterManager.getInstance().savePresenter(presenter, outState);
    }

    @Override
    protected void onPause() {
        Log.d(Consts.TAG, "MainActivity.onPause");
        super.onPause();

        presenter.unbindView();

        //TODO таймер включения блокировки экрана
    }

    @Override
    protected void onStop() {
        Log.d(Consts.TAG, "MainActivity.onStop");
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        Log.d(Consts.TAG, "MainActivity.onBackPressed");
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (back_pressed + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
                setResult(RESULT_BACK_PRESSED);
            } else
                Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }

    @Override
    public void setFragment(BaseFragment fragment) {
        try {
            //ataching to fragment the navigation presenter
            fragment.atachPresenter(presenter);
            //showing the presenter on screen
            replaceFragment(R.id.main_container, fragment);
        } catch (NullPointerException e) {
            Log.e(Consts.TAG, "MainActivity.setFragment\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void setFragment(Fragment fragment) {
        try {
            replaceFragment(R.id.main_container, fragment);
        } catch (NullPointerException e) {
            Log.e(Consts.TAG, "MainActivity.setFragment\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean checkMultiplePermissions(String[] permissions) {
        if (permissions.length == 0) return false;
        if (persmissionsGranted) return true;
        List<String> permissionsDenied = new ArrayList<>();
        //проверка - какие разрешения отсутствуют те в список
        for (String p : permissions) {
            if (ContextCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED) {
                permissionsDenied.add(p);
            }
        }
        //оправка запроса на разрешения по списку
        if (permissionsDenied.size() != 0) {
            permissions = new String[permissionsDenied.size()];
            permissionsDenied.toArray(permissions);
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
            return false;
        } else
            return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(Consts.TAG, "MainActivity.onRequestPermissionsResult");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            //TODO заменить на нужное при необходимости
            case 100: {
                persmissionsGranted =
                        grantResults.length > 0
                                && grantResults[0] == PackageManager.PERMISSION_GRANTED
                                && grantResults.length > 1
                                && grantResults[1] == PackageManager.PERMISSION_GRANTED;
                break;
            }
            case PERMISSION_REQUEST_CODE: {
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        persmissionsGranted = false;
                        return;
                    }
                }
                persmissionsGranted = true;
                break;
            }
        }
        if (persmissionsGranted) {
            recreate();
        }
    }
}
