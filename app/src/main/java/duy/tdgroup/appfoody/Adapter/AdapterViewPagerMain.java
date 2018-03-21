package duy.tdgroup.appfoody.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.Switch;

import duy.tdgroup.appfoody.View.fragment.AnGiFragment;
import duy.tdgroup.appfoody.View.fragment.ODauFragment;

/**
 * Created by phand on 5/28/2017.
 */

public class AdapterViewPagerMain extends FragmentStatePagerAdapter {
    AnGiFragment anGiFragment;
    ODauFragment oDauFragment;
    public AdapterViewPagerMain(FragmentManager fm) {
        super(fm);
        anGiFragment = new AnGiFragment();
        oDauFragment = new ODauFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return oDauFragment;
            case 1:
                return anGiFragment;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
