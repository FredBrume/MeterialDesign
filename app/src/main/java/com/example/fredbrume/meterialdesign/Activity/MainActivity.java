package com.example.fredbrume.meterialdesign.Activity;


import android.opengl.Visibility;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fredbrume.meterialdesign.Adapters.NavigationDrawerAdapter;
import com.example.fredbrume.meterialdesign.Dialog.BaseDialogFragment;
import com.example.fredbrume.meterialdesign.Dialog.ClassDialog;
import com.example.fredbrume.meterialdesign.Dialog.ClassSelectorDialog;
import com.example.fredbrume.meterialdesign.Dialog.TeacherSelectorDialog;
import com.example.fredbrume.meterialdesign.Fragments.AddOrEditAssignmentFragment;
import com.example.fredbrume.meterialdesign.Fragments.AddOrEditClassFragment;
import com.example.fredbrume.meterialdesign.Fragments.AssignmentFragment;
import com.example.fredbrume.meterialdesign.Fragments.CaldroidCalendarFragment;
import com.example.fredbrume.meterialdesign.Fragments.ClassFragment;
import com.example.fredbrume.meterialdesign.Fragments.DocumentFragment;
import com.example.fredbrume.meterialdesign.Fragments.HomeFragment;
import com.example.fredbrume.meterialdesign.Fragments.MapFragment;
import com.example.fredbrume.meterialdesign.Fragments.MapTypeListFragment;
import com.example.fredbrume.meterialdesign.Fragments.NewsFragment;
import com.example.fredbrume.meterialdesign.Fragments.PicturesFragment;
import com.example.fredbrume.meterialdesign.Model.Assignment;
import com.example.fredbrume.meterialdesign.Model.Location;
import com.example.fredbrume.meterialdesign.Model.NavDrawerItem;
import com.example.fredbrume.meterialdesign.Model.News;
import com.example.fredbrume.meterialdesign.Model.Subject;
import com.example.fredbrume.meterialdesign.R;
import com.example.fredbrume.meterialdesign.util.AppUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        HomeFragment.GetNewsItemOnClick, MapTypeListFragment.SendLocationHandler, ViewPropertyHandler,
        BaseDialogFragment.DialogDataHandler, CaldroidCalendarFragment.CalendarEventOnClickHandler,
        AssignmentFragment.GetAssignmentOnClickHandler {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.list_slidermenu)
    ListView mDrawerList;

    @BindView(R.id.left_drawer)
    DrawerLayout drawer;

    @BindView(R.id.main_bg_logo)
    ImageView mainBackgroundLogo;

    private Fragment fragment;
    private ActionBarDrawerToggle toggle;
    private NavigationDrawerAdapter adapter;

    final private FragmentManager fragmentManager = getSupportFragmentManager();


    private boolean doubleBackToExitPressedOnce = false;
    private FragmentTransaction transaction;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);


        adapter = new NavigationDrawerAdapter(getApplicationContext());
        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(this);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        supportInvalidateOptionsMenu();
        showStarterFragment();

    }

    private void showStarterFragment() {
        openFragment(new HomeFragment());
    }

    @Override
    public void onBackPressed() {

        if (fragmentManager.getBackStackEntryCount() != 0) {

            if (this.doubleBackToExitPressedOnce) {
                super.onBackPressed();
            }
            try {

                fragment = fragmentManager.findFragmentByTag(HomeFragment.class.getSimpleName());

                if (!(fragment.isVisible() && fragment == null)) {
                    openFragment(HomeFragment.getInstance(), false, null, Integer.valueOf(R.anim.enter_from_left), Integer.valueOf(R.anim.exit_to_right));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (this.doubleBackToExitPressedOnce) {
            super.onBackPressed();
        } else {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getString(R.string.Press_back_again_to_exit), Toast.LENGTH_LONG).show();

            new Handler().postDelayed(new Runnable() {
                public void run() {
                    MainActivity.this.doubleBackToExitPressedOnce = false;
                }
            }, 500);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        NavDrawerItem navItem = (NavDrawerItem) mDrawerList.getItemAtPosition(position);
        String title = navItem.getTitle();
        displayView(title);
    }

    @Override
    public void openFragment(Fragment fragment) {
        openFragment(fragment, false, null);
    }

    @Override
    public void openFragment(Fragment fragment, boolean withBackStack, String backStackId) {
        openFragment(fragment, withBackStack, backStackId, null, null);
    }

    @Override
    public void openDialog(String[] data, String dialogTag) {

        switch (dialogTag) {
            case "AddOrEditClassFragment":
                TeacherSelectorDialog teacherSelectorDialog =
                        TeacherSelectorDialog.newInstance(dialogTag, data);
                teacherSelectorDialog.show(getSupportFragmentManager(), TeacherSelectorDialog.class.getSimpleName());
                break;

            case "AddOrEditAssignmentFragment":
                ClassSelectorDialog classSelectorDialog =
                        ClassSelectorDialog.newInstance(dialogTag, data);
                classSelectorDialog.show(getSupportFragmentManager(), ClassSelectorDialog.class.getSimpleName());
        }

    }

    @Override
    public void openDialog(Subject subject) {

        ClassDialog classDialog = ClassDialog.newInstance(subject);
        classDialog.show(getSupportFragmentManager(), ClassDialog.class.getSimpleName());
    }

    @Override
    public void setBackgroundMainLogo(final float alpha) {

        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mainBackgroundLogo.setImageDrawable(AppUtils.mainBackgroundLogo(getApplicationContext()));
                mainBackgroundLogo.setAlpha(alpha);
                Animation backgroundLogoFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                mainBackgroundLogo.startAnimation(backgroundLogoFadeIn);
            }
        }, 500L);

    }

    @Override
    public void setToolBar(int menu) {

        toolbar.getMenu().clear();
        toolbar.inflateMenu(menu);

    }

    @Override
    public void setToolbarItemVisibility(int menuItemId, boolean visibility) {

        MenuItem meunItem = toolbar.getMenu().findItem(menuItemId);
        meunItem.setVisible(true);

    }

    private void openFragment(Fragment fragment, boolean withBackStack, String backStackId, Integer inAnimId, Integer outAnimId) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (!withBackStack) {
                for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
                    fragmentManager.popBackStackImmediate();
                }
            }
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (inAnimId == null || outAnimId == null) {
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            } else if (!(inAnimId.intValue() == Integer.MIN_VALUE || outAnimId.intValue() == Integer.MIN_VALUE)) {
                transaction.setCustomAnimations(inAnimId.intValue(), outAnimId.intValue());
            }
            if (withBackStack) {
                transaction.addToBackStack(backStackId);

            } else {
                transaction.disallowAddToBackStack();
            }
            transaction.replace(R.id.main_container, fragment, fragment.getClass().getSimpleName());
            transaction.commitAllowingStateLoss();
        }
    }


    private void displayView(String title) {

        switch (title) {
            case "Gallery":
                openFragment(new PicturesFragment(), true, null);

                closeDrawer();
                break;

            case "Map":
                openFragment(new MapFragment(), true, null);

                closeDrawer();
                break;

            case "Documents":
                openFragment(DocumentFragment.getInstance(), true, null);

                closeDrawer();
                break;

            case "Assignment":
                openFragment(AssignmentFragment.getInstance(), true, null);

                closeDrawer(); //dont add child fragment to stack
                break;
        }

        Toast.makeText(this, title.toString(), Toast.LENGTH_SHORT).show();

    }

    private void closeDrawer() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                drawer.closeDrawers();
            }
        };
        handler.postDelayed(runnable, 500);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (id) {
            case R.id.addAssignment:
                openFragment(AddOrEditAssignmentFragment.getInstance(), true, null);

                break;

            case R.id.myclasses:
                openFragment(ClassFragment.getInstance(), true, null);
                break;

            case R.id.addClass:
                openFragment(AddOrEditClassFragment.getInstance(), true, null);
                break;

            case R.id.update:

                AddOrEditAssignmentFragment addOrEditAssignmentFragment = (AddOrEditAssignmentFragment) getSupportFragmentManager()
                        .findFragmentByTag(AddOrEditAssignmentFragment.class.getSimpleName());
                addOrEditAssignmentFragment.shareFile();

                break;
            case R.id.done:

                getCurrentFragmentTag();
                switch (getCurrentFragmentTag()) {
                    case "AddOrEditClassFragment":

                        AddOrEditClassFragment addOrEditClassFragment = (AddOrEditClassFragment) getSupportFragmentManager()
                                .findFragmentByTag(AddOrEditClassFragment.class.getSimpleName());
                        addOrEditClassFragment.doneMenuItemSelected();
                        break;

                    case "AddOrEditAssignmentFragment":

                        AddOrEditAssignmentFragment addOrEditAssignFragment = (AddOrEditAssignmentFragment) getSupportFragmentManager()
                                .findFragmentByTag(AddOrEditAssignmentFragment.class.getSimpleName());
                        addOrEditAssignFragment.doneMenuItemSelected();
                        break;
                }
        }

        return super.onOptionsItemSelected(item);
    }

    private String getCurrentFragmentTag() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragment = fragmentManager.getFragments();

        return fragment.get(fragment.size() - 1).getClass().getSimpleName();
    }


    @Override
    public void getNewsDetails(News news) {

        openFragment(NewsFragment.getInstance(news), false, null);

    }

    @Override
    public void sendLocation(Location location) {

        MapFragment mapFragment = (MapFragment) getSupportFragmentManager()
                .findFragmentByTag(MapFragment.class.getSimpleName());
        mapFragment.getLocation(location, this);
    }

    @Override
    public void sendDialogData(String dialogTag, String data) {

        switch (dialogTag) {

            case "TeacherSelectorDialog":

                AddOrEditClassFragment addOrEditClassFragment = (AddOrEditClassFragment) getSupportFragmentManager()
                        .findFragmentByTag(AddOrEditClassFragment.class.getSimpleName());

                addOrEditClassFragment.teacherViewSelected(data);
                break;

            case "ClassSelectorDialog":
                AddOrEditAssignmentFragment addOrEditAssignmentFragment = (AddOrEditAssignmentFragment) getSupportFragmentManager()
                        .findFragmentByTag(AddOrEditAssignmentFragment.class.getSimpleName());

                addOrEditAssignmentFragment.classViewSelected(data);
                break;
        }
    }

    @Override
    public void sendDialogOption(Subject subject) {

        ClassFragment addOrEditClassFragment = (ClassFragment) getSupportFragmentManager()
                .findFragmentByTag(ClassFragment.class.getSimpleName());
        addOrEditClassFragment.removeClassFromList(subject);
    }

    @Override
    public void getEventOnClick(String date) {

        AssignmentFragment assignmentFragment = (AssignmentFragment) getSupportFragmentManager()
                .findFragmentByTag(AssignmentFragment.class.getSimpleName());

        assignmentFragment.displayAssignments(date);
    }

    @Override
    public void getAssignmentOnClick(Assignment assignment) {

        openFragment(AddOrEditAssignmentFragment.getInstance(assignment), true, null);

    }
}
