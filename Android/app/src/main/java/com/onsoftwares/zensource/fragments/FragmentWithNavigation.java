package com.onsoftwares.zensource.fragments;

import android.support.v4.app.Fragment;

import com.onsoftwares.zensource.interfaces.NavigationActivityHandler;

/**
 * Created by Menighin on 05/01/2018.
 */

public class FragmentWithNavigation extends Fragment {

    public void activateNavigation() {
        if (getActivity() != null)
            ((NavigationActivityHandler) getActivity()).activateNavigation();
    }

    public void deactivateNavigation() {
        if (getActivity() != null)
            ((NavigationActivityHandler) getActivity()).deactivateNavigation();
    }

}
