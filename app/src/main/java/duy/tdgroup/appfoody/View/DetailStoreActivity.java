package duy.tdgroup.appfoody.View;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import duy.tdgroup.appfoody.Adapter.AdapterBinhLuan;
import duy.tdgroup.appfoody.Model.StoreModel;
import duy.tdgroup.appfoody.R;

/**
 * Created by phand on 6/3/2017.
 */

public class DetailStoreActivity extends AppCompatActivity implements OnMapReadyCallback{
    @BindView(R.id.tv_name_store_detail)
    TextView tvNameStore;
    @BindView(R.id.tv_address_store_detail)
    TextView tvAddressStore;
    @BindView(R.id.tv_time_store)
    TextView tvTimeStore;
    @BindView(R.id.tv_mocua_dongcua_store)
    TextView tvTrangThai;
    @BindView(R.id.img_hinhanh_store_detail)
    ImageView imgHinhAnhStore;
    @BindView(R.id.tv_tong_comment_detail)
    TextView tvTongComment;
    @BindView(R.id.tv_tong_checkin_detail)
    TextView tvTongCheckin;
    @BindView(R.id.tv_tong_hinh_anh_detail)
    TextView tvTongHinhAnh;
    @BindView(R.id.tv_tong_luu_lai_detail)
    TextView tvTongLuuLai;
    @BindView(R.id.tv_toolbar_tieude)
    TextView tvTieuDeToolbar;
    @BindView(R.id.recyclerBinhLuanChiTietQuanAn)
    RecyclerView recyclerViewBinhLuan;
    @BindView(R.id.nestScroll_Chitiet)
    NestedScrollView nestedScrollViewChiTiet;

    MapFragment mapFragment;
    StoreModel storeModel;
    Toolbar toolbar;
    AdapterBinhLuan adapterBinhLuan;
    GoogleMap googleMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_store);
        ButterKnife.bind(this);
        storeModel = getIntent().getParcelableExtra("quanan");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String giohientai = simpleDateFormat.format(calendar.getTime());
        String giomocua = storeModel.getGiomocua();
        String giodongcua = storeModel.getGiodongcua();

        try {
            Date dateHientai = simpleDateFormat.parse(giohientai);
            Date dateDongcua = simpleDateFormat.parse(giodongcua);
            Date dateMocua = simpleDateFormat.parse(giomocua);

            if (dateHientai.after(dateMocua) && dateHientai.before(dateDongcua)){
                tvTrangThai.setText(getString(R.string.dangmocua));
            }else {
                tvTrangThai.setText(getString(R.string.chuamocua));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvTieuDeToolbar.setText(storeModel.getTenquanan());
        tvNameStore.setText(storeModel.getTenquanan());
        tvAddressStore.setText(storeModel.getBranchStoreModels().get(0).getDiachi());
        tvTimeStore.setText(storeModel.getGiomocua() + " - " + storeModel.getGiodongcua());
        tvTongComment.setText(storeModel.getCommentModels().size() + "");
        tvTongHinhAnh.setText(storeModel.getHinhanhquanan().size() + "");

        StorageReference storageHinhquanan = FirebaseStorage.getInstance().getReference().child("hinhanh").child(storeModel.getHinhanhquanan().get(0));
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhquanan.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imgHinhAnhStore.setImageBitmap(bitmap);
            }
        });

        //load danh sach binh luan
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewBinhLuan.setLayoutManager(layoutManager);
        adapterBinhLuan = new AdapterBinhLuan(this, R.layout.custom_layout_binhluan, storeModel.getCommentModels());
        recyclerViewBinhLuan.setAdapter(adapterBinhLuan);
        adapterBinhLuan.notifyDataSetChanged();

        nestedScrollViewChiTiet.smoothScrollTo(0,0);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        double latitude = storeModel.getBranchStoreModels().get(0).getLatitude();
        double longitude = storeModel.getBranchStoreModels().get(0).getLongitude();

        LatLng latLng = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(storeModel.getTenquanan());

        googleMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,14);
        googleMap.moveCamera(cameraUpdate);
    }
}
