package duy.tdgroup.appfoody.View;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import duy.tdgroup.appfoody.Adapter.AdapterRecyclerHinhAnhBinhLuan;
import duy.tdgroup.appfoody.Model.CommentModel;
import duy.tdgroup.appfoody.R;

/**
 * Created by phand on 6/16/2017.
 */

public class LoadDetailCommentActivity extends AppCompatActivity {

    @BindView(R.id.img_hinhanh_comment_chitiet)
    CircleImageView circleImageView;
    @BindView(R.id.tv_name_comment_chitiet)
    TextView tvTieuDeBinhLuan;
    @BindView(R.id.tv_noidung_comment_chitiet)
    TextView tvNoiDungBinhLuan;
    @BindView(R.id.tv_point_comment_chitiet)
    TextView tvDiemBinhLuan;
    @BindView(R.id.recycler_image_binhluan)
    RecyclerView recyclerViewHinhBinhLuan;

    List<Bitmap> bitmapList;
    CommentModel commentModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_layout_binhluan);

        ButterKnife.bind(this);

        bitmapList = new ArrayList<>();
        commentModel = getIntent().getParcelableExtra("binhluanmodel");

        tvTieuDeBinhLuan.setText(commentModel.getTieude());
        tvNoiDungBinhLuan.setText(commentModel.getNoidung());
        tvDiemBinhLuan.setText(commentModel.getChamdiem() + "");
        setImageBinhLuan(circleImageView, commentModel.getMemberModel().getHinhanh());

        for (String linkhinh: commentModel.getHinhanhbinhluanlist()){


            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("hinhanh").child(linkhinh);
            long ONE_MEGABYTE = 1024 * 1024;
            storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    bitmapList.add(bitmap);
                    if (bitmapList.size() == commentModel.getHinhanhbinhluanlist().size()){
                        AdapterRecyclerHinhAnhBinhLuan adapterRecyclerHinhAnhBinhLuan = new AdapterRecyclerHinhAnhBinhLuan(LoadDetailCommentActivity.this, R.layout.custom_layout_hinhanhbinhluan, bitmapList, commentModel, true);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(LoadDetailCommentActivity.this, 2);
                        recyclerViewHinhBinhLuan.setLayoutManager(layoutManager);
                        recyclerViewHinhBinhLuan.setAdapter(adapterRecyclerHinhAnhBinhLuan);
                        adapterRecyclerHinhAnhBinhLuan.notifyDataSetChanged();
                    }
                }
            });
        }
    }
    private void setImageBinhLuan(final CircleImageView circleImageView, String linkhinh){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("thanhvien").child(linkhinh);
        long ONE_MEGABYTE = 1024 * 1024;
        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                circleImageView.setImageBitmap(bitmap);
            }
        });
    }
}
