package duy.tdgroup.appfoody.Controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import duy.tdgroup.appfoody.Adapter.AdapterRecyclerViewOdau;
import duy.tdgroup.appfoody.Controller.Interface.ODauInterface;
import duy.tdgroup.appfoody.Model.StoreModel;
import duy.tdgroup.appfoody.R;

/**
 * Created by phand on 5/29/2017.
 */

public class ODauController {
    Context context;
    StoreModel storeModel;
    AdapterRecyclerViewOdau adapterRecyclerViewOdau;
    int itemdaco = 5;

    public ODauController(Context context){
        this.context = context;
        storeModel = new StoreModel();
    }
    public void getListStoreController(Context context, NestedScrollView nestedScrollViewOdau, RecyclerView recyclerODau, final ProgressBar progressBar, final Location vitrihientai){
        final List<StoreModel> storeModels = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerODau.setLayoutManager(layoutManager);
        adapterRecyclerViewOdau = new AdapterRecyclerViewOdau(context, storeModels, R.layout.custom_recyclerview_odau);
        recyclerODau.setAdapter(adapterRecyclerViewOdau);

        progressBar.setVisibility(View.VISIBLE);
        final ODauInterface oDauInterface = new ODauInterface() {
            @Override
            public void getListStoreModel(final StoreModel storeModel) {
                final List<Bitmap> bitmaps = new ArrayList<>();
                for (String linkhinh: storeModel.getHinhanhquanan()){
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("hinhanh").child(linkhinh);
                    long ONE_MEGABYTE = 1024 * 1024;
                    storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            bitmaps.add(bitmap);
                            storeModel.setBitmaps(bitmaps);

                            if(storeModel.getBitmaps().size() == storeModel.getHinhanhquanan().size()){
                                storeModels.add(storeModel);
                                adapterRecyclerViewOdau.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        };

        nestedScrollViewOdau.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.getChildAt(v.getChildCount() - 1) != null){
                    if (scrollY >= (v.getChildAt(v.getChildCount() - 1)).getMeasuredHeight() - v.getMeasuredHeight()){
                        itemdaco += 5;
                        storeModel.getListStore(oDauInterface, vitrihientai, itemdaco, itemdaco - 5);
                    }
                }
            }
        });

        storeModel.getListStore(oDauInterface, vitrihientai, itemdaco, 0);
    }
}
