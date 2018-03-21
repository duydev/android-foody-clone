package duy.tdgroup.appfoody.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Parcelable;
import android.os.storage.StorageManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import duy.tdgroup.appfoody.Model.BranchStoreModel;
import duy.tdgroup.appfoody.Model.CommentModel;
import duy.tdgroup.appfoody.Model.StoreModel;
import duy.tdgroup.appfoody.R;
import duy.tdgroup.appfoody.View.DetailStoreActivity;

/**
 * Created by phand on 5/29/2017.
 */

public class AdapterRecyclerViewOdau extends RecyclerView.Adapter<AdapterRecyclerViewOdau.ViewHolder> {

    List<StoreModel> storeModels;
    int resource;
    Context context;
    public AdapterRecyclerViewOdau( Context context, List<StoreModel> storeModels, int resource){
        this.storeModels = storeModels;
        this.resource = resource;
        this.context = context;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_store_name_place)
        TextView tvStoreName;
        @BindView(R.id.btnDatMonODau)
        Button btnDatMon;
        @BindView(R.id.img_hinhanh_odau)
        ImageView imgHinhAnhODau;
        @BindView(R.id.tv_name_comment_1)
        TextView tvNameComment1;
        @BindView(R.id.tv_name_comment_2)
        TextView tvNameComment2;
        @BindView(R.id.img_hinhanh_comment_1)
        CircleImageView cimImageComent1;
        @BindView(R.id.img_hinhanh_comment_2)
        CircleImageView cimImageComent2;
        @BindView(R.id.tv_noidung_comment_1)
        TextView tvNoiDungComment1;
        @BindView(R.id.tv_noidung_comment_2)
        TextView tvNoiDungComment2;
        @BindView(R.id.tv_point_comment_1)
        TextView tvPointComment1;
        @BindView(R.id.tv_point_comment_2)
        TextView tvPointComment2;
        @BindView(R.id.ln_comment_1)
        LinearLayout lnComment1;
        @BindView(R.id.ln_comment_2)
        LinearLayout lnComment2;
        @BindView(R.id.tv_tong_binh_luan)
        TextView tvTongBinhLuan;
        @BindView(R.id.tv_tong_hinh_anh)
        TextView tvTongHinhAnh;
        @BindView(R.id.tv_point_store)
        TextView tvTongDiem;
        @BindView(R.id.tv_address_store)
        TextView tvAddress;
        @BindView(R.id.tv_khoangcach_store)
        TextView tvKhoangCach;
        @BindView(R.id.cardViewImageStore)
        CardView cvImageStore;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public AdapterRecyclerViewOdau.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterRecyclerViewOdau.ViewHolder holder, int position) {
        final StoreModel storeModel = storeModels.get(position);
        holder.tvStoreName.setText(storeModel.getTenquanan());
        if(storeModel.isGiaohang() == true){
            holder.btnDatMon.setVisibility(View.VISIBLE);
        }
        if(storeModel.getBitmaps().size() > 0){
            holder.imgHinhAnhODau.setImageBitmap(storeModel.getBitmaps().get(0));
        }

        if (storeModel.getCommentModels().size() > 0){
            holder.lnComment1.setVisibility(View.VISIBLE);
            CommentModel commentModel = storeModel.getCommentModels().get(0);
            holder.tvNameComment1.setText(commentModel.getTieude());
            holder.tvNoiDungComment1.setText(commentModel.getNoidung());
            if(commentModel.getChamdiem() >= 6){
                holder.tvPointComment1.setTextColor(Color.parseColor("#1DA60E"));
            }else {
                holder.tvPointComment1.setTextColor(Color.parseColor("#CD2027"));
            }
            String point1 = String.valueOf(commentModel.getChamdiem());
            holder.tvPointComment1.setText(point1);

            setImageBinhLuan(holder.cimImageComent1, commentModel.getMemberModel().getHinhanh());
            if (storeModel.getCommentModels().size() > 2){
                holder.lnComment2.setVisibility(View.VISIBLE);
                CommentModel commentModel2 = storeModel.getCommentModels().get(1);
                holder.tvNameComment2.setText(commentModel2.getTieude());
                holder.tvNoiDungComment2.setText(commentModel2.getNoidung());
                if(commentModel2.getChamdiem() >= 6){
                    holder.tvPointComment2.setTextColor(Color.parseColor("#1DA60E"));
                }else {
                    holder.tvPointComment2.setTextColor(Color.parseColor("#CD2027"));
                }
                String point2 = String.valueOf(commentModel2.getChamdiem());
                holder.tvPointComment2.setText(point2);

                setImageBinhLuan(holder.cimImageComent2, commentModel.getMemberModel().getHinhanh());
            }
            holder.tvTongBinhLuan.setText(storeModel.getCommentModels().size() + "");
            int tonghinhanhbinhluan = 0;
            double tongdiem = 0;

            for (CommentModel comentModel: storeModel.getCommentModels()){
                tonghinhanhbinhluan += comentModel.getHinhanhbinhluanlist().size();
                tongdiem+= comentModel.getChamdiem();
            }
            double diemtb = tongdiem/storeModel.getCommentModels().size();
            if(diemtb >= 6){
                holder.tvTongDiem.setBackgroundResource(R.drawable.background_recyclerview_odau);
            }
            holder.tvTongDiem.setText(String.format("%.1f",diemtb));
            if (tonghinhanhbinhluan > 0){
                holder.tvTongHinhAnh.setText(tonghinhanhbinhluan + "");
            }

        }else {
            holder.lnComment1.setVisibility(View.GONE);
            holder.lnComment2.setVisibility(View.GONE);
        }

        if(storeModel.getBranchStoreModels().size() > 0){
            BranchStoreModel branchStoreModelTam = storeModel.getBranchStoreModels().get(0);
            for (BranchStoreModel branchStoreModel: storeModel.getBranchStoreModels()){
                if (branchStoreModelTam.getKhoangcach() > branchStoreModel.getKhoangcach()){
                    branchStoreModelTam = branchStoreModel;
                }
            }
            holder.tvAddress.setText(branchStoreModelTam.getDiachi());
            holder.tvKhoangCach.setText(String.format("%.1f",branchStoreModelTam.getKhoangcach()) + " km");
        }
        holder.tvStoreName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailStoreIntent = new Intent(context, DetailStoreActivity.class);
                detailStoreIntent.putExtra("quanan", storeModel);
                context.startActivity(detailStoreIntent);
            }
        });

        holder.cvImageStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailStoreIntent = new Intent(context, DetailStoreActivity.class);
                detailStoreIntent.putExtra("quanan", storeModel);
                context.startActivity(detailStoreIntent);
            }
        });
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
    @Override
    public int getItemCount() {
        return storeModels.size();
    }


}
