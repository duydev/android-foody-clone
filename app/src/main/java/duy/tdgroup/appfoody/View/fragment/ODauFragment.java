package duy.tdgroup.appfoody.View.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import duy.tdgroup.appfoody.Controller.ODauController;
import duy.tdgroup.appfoody.Model.StoreModel;
import duy.tdgroup.appfoody.R;

/**
 * Created by phand on 5/28/2017.
 */

public class ODauFragment extends Fragment {
    @BindView(R.id.recyclerODau)
    RecyclerView recyclerODau;
    @BindView(R.id.nestScroll_Odau)
    NestedScrollView nestedScrollViewOdau;

    ProgressBar progressBar;
    ODauController oDauController;
    SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_odau, container, false);
        ButterKnife.bind(this, view);
        recyclerODau.setNestedScrollingEnabled(false);
        progressBar = (ProgressBar) view.findViewById(R.id.prg_odau);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        sharedPreferences = getContext().getSharedPreferences("toado", Context.MODE_PRIVATE);
        Location vitrihientai = new Location("");
        vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude", "0")));
        vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude", "0")));
        oDauController = new ODauController(getContext());

        oDauController.getListStoreController(getContext(), nestedScrollViewOdau, recyclerODau, progressBar, vitrihientai);

    }
}
