package duy.tdgroup.appfoody.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import duy.tdgroup.appfoody.Model.CommentModel;
import duy.tdgroup.appfoody.R;

/**
 * Created by phand on 6/16/2017.
 */

public class AdapterBinhLuan extends RecyclerView.Adapter<AdapterBinhLuan.ViewHolder> {

    Context context;
    int layout;
    List<CommentModel> commentModels;
    List<Bitmap> bitmapList;

    public AdapterBinhLuan(Context context, int layout, List<CommentModel> commentModels){
        this.context = context;
        this.layout = layout;
        this.commentModels = commentModels;
        bitmapList = new ArrayList<>();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public AdapterBinhLuan.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterBinhLuan.ViewHolder holder, int position) {
        final CommentModel commentModel = commentModels.get(position);
        holder.tvTieuDeBinhLuan.setText(commentModel.getTieude());
        holder.tvNoiDungBinhLuan.setText(commentModel.getNoidung());
        holder.tvDiemBinhLuan.setText(commentModel.getChamdiem() + "");
        setImageBinhLuan(holder.circleImageView, commentModel.getMemberModel().getHinhanh());

        for (String linkhinh: commentModel.getHinhanhbinhluanlist()){


            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("hinhanh").child(linkhinh);
            long ONE_MEGABYTE = 1024 * 1024;
            storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    bitmapList.add(bitmap);
                    if (bitmapList.size() == commentModel.getHinhanhbinhluanlist().size()){
                        AdapterRecyclerHinhAnhBinhLuan adapterRecyclerHinhAnhBinhLuan = new AdapterRecyclerHinhAnhBinhLuan(context, R.layout.custom_layout_hinhanhbinhluan, bitmapList, commentModel, false);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
                        holder.recyclerViewHinhBinhLuan.setLayoutManager(layoutManager);
                        holder.recyclerViewHinhBinhLuan.setAdapter(adapterRecyclerHinhAnhBinhLuan);
                        adapterRecyclerHinhAnhBinhLuan.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int sobinhluan = commentModels.size();
        if (sobinhluan > 5){
            return 5;
        }else {
            return commentModels.size();
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
