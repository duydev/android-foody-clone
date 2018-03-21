package duy.tdgroup.appfoody.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import duy.tdgroup.appfoody.Model.CommentModel;
import duy.tdgroup.appfoody.R;
import duy.tdgroup.appfoody.View.LoadDetailCommentActivity;

/**
 * Created by phand on 6/16/2017.
 */

public class AdapterRecyclerHinhAnhBinhLuan extends RecyclerView.Adapter<AdapterRecyclerHinhAnhBinhLuan.ViewHolder> {

    Context context;
    int layout;
    List<Bitmap> listhinh;
    CommentModel commentModel;
    boolean isChiTietBinhLuan;
    public AdapterRecyclerHinhAnhBinhLuan(Context context, int layout, List<Bitmap> listhinh, CommentModel commentModel, boolean isChiTietBinhLuan){
        this.context = context;
        this.layout = layout;
        this.listhinh = listhinh;
        this.commentModel = commentModel;
        this.isChiTietBinhLuan = isChiTietBinhLuan;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_hinhanh_binhluan)
        ImageView imgHinhAnhBinhLuan;
        @BindView(R.id.tv_soanh_binhluan)
        TextView tvSoHinhBinhLuan;
        @BindView(R.id.khungsohinhbinhluan)
        FrameLayout flKhungHinh;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.imgHinhAnhBinhLuan.setImageBitmap(listhinh.get(position));
        if (isChiTietBinhLuan != true){
            if(position == 3){
                int sohinhconlai = listhinh.size() - 4;
                if (sohinhconlai > 0){
                    holder.flKhungHinh.setVisibility(View.VISIBLE);
                    holder.tvSoHinhBinhLuan.setText("+" + sohinhconlai);
                    holder.imgHinhAnhBinhLuan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent detailCommentIntent = new Intent(context, LoadDetailCommentActivity.class);
                            detailCommentIntent.putExtra("binhluanmodel", commentModel);
                            context.startActivity(detailCommentIntent);
                        }
                    });
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        if (!isChiTietBinhLuan){
            return 4;
        }else {
            return listhinh.size();
        }

    }


}
