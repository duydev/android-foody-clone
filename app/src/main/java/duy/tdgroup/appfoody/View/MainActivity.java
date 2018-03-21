package duy.tdgroup.appfoody.View;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import duy.tdgroup.appfoody.Adapter.AdapterViewPagerMain;
import duy.tdgroup.appfoody.R;

/**
 * Created by phand on 5/25/2017.
 */

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener{
    @BindView(R.id.rdg_place_food)
    RadioGroup rdgPlaceFood;
    @BindView(R.id.rd_place)
    RadioButton rdOdau;
    @BindView(R.id.rd_food)
    RadioButton rdAnGi;
    ViewPager viewPagerMain;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        viewPagerMain = (ViewPager) findViewById(R.id.viewpager_main);

        AdapterViewPagerMain adapterViewPagerMain = new AdapterViewPagerMain(getSupportFragmentManager());
        viewPagerMain.setAdapter(adapterViewPagerMain);
        viewPagerMain.addOnPageChangeListener(this);

        rdgPlaceFood.setOnCheckedChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                rdOdau.setChecked(true);
                break;
            case 1:
                rdAnGi.setChecked(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId){
            case R.id.rd_place:
                viewPagerMain.setCurrentItem(0);
                break;
            case R.id.rd_food:
                viewPagerMain.setCurrentItem(1);
                break;
        }
    }
}
