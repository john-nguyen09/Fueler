package com.snowk49.android.fueler.singleton;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.snowk49.android.fueler.MainActivity;
import com.snowk49.android.fueler.view.CarListViewFragment;

public final class FragmentChanger {

    static FragmentChanger sInstance;

    public static void init(Context context, int id) {
        if (sInstance == null) {
            sInstance = new FragmentChanger(context, id);
            sInstance.initFirstFragment();
        }
    }

    public static FragmentChanger getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("FragmentChanger has not been inited");
        }

        return sInstance;
    }

    public static void close() {
        sInstance = null;
    }

    private final int id;
    private final FragmentManager fragmentManager;
    private final CarListViewFragment mainFragment;

    public FragmentChanger(Context context, int id) {
        this.fragmentManager = ((MainActivity) context).getSupportFragmentManager();
        this.id = id;

        mainFragment = new CarListViewFragment();
    }

    public void initFirstFragment() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        ft.replace(id, mainFragment);
        ft.commit();
    }

    public CarListViewFragment getMainFragment() {
        return mainFragment;
    }

    public void changeFragment(Fragment fragment) {
        FragmentTransaction ft = fragmentManager.beginTransaction();

        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(id, fragment);
        ft.addToBackStack(fragment.getClass().getName());
        ft.commitAllowingStateLoss();
    }

    public void goBackFragment() {
        fragmentManager.popBackStack();
    }
}
