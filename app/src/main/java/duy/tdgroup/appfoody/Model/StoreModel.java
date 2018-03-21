package duy.tdgroup.appfoody.Model;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import duy.tdgroup.appfoody.Controller.Interface.ODauInterface;

/**
 * Created by phand on 5/29/2017.
 */

public class StoreModel implements Parcelable{
    boolean giaohang;
    String giomocua;
    String giodongcua;
    String tenquanan;
    String videogioithieu;
    String maquanan;
    long luotthich;
    List<String> tienich;
    List<String> hinhanhquanan;
    List<CommentModel> commentModels;
    List<BranchStoreModel> branchStoreModels;
    List<Bitmap> bitmaps;
    DatabaseReference nodeRoot;

    protected StoreModel(Parcel in) {
        giaohang = in.readByte() != 0;
        giomocua = in.readString();
        giodongcua = in.readString();
        tenquanan = in.readString();
        videogioithieu = in.readString();
        maquanan = in.readString();
        luotthich = in.readLong();
        tienich = in.createStringArrayList();
        hinhanhquanan = in.createStringArrayList();
        branchStoreModels = new ArrayList<BranchStoreModel>();
        in.readTypedList(branchStoreModels, BranchStoreModel.CREATOR);
        commentModels = new ArrayList<CommentModel>();
        in.readTypedList(commentModels, CommentModel.CREATOR);
    }

    public static final Creator<StoreModel> CREATOR = new Creator<StoreModel>() {
        @Override
        public StoreModel createFromParcel(Parcel in) {
            return new StoreModel(in);
        }

        @Override
        public StoreModel[] newArray(int size) {
            return new StoreModel[size];
        }
    };

    public List<Bitmap> getBitmaps() {
        return bitmaps;
    }

    public void setBitmaps(List<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
    }

    public List<BranchStoreModel> getBranchStoreModels() {
        return branchStoreModels;
    }

    public void setBranchStoreModels(List<BranchStoreModel> branchStoreModels) {
        this.branchStoreModels = branchStoreModels;
    }

    public List<CommentModel> getCommentModels() {
        return commentModels;
    }

    public void setCommentModels(List<CommentModel> commentModels) {
        this.commentModels = commentModels;
    }

    public List<String> getHinhanhquanan() {
        return hinhanhquanan;
    }

    public void setHinhanhquanan(List<String> hinhanhquanan) {
        this.hinhanhquanan = hinhanhquanan;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public StoreModel(){
        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }

    public List<String> getTienich() {
        return tienich;
    }

    public void setTienich(List<String> tienich) {
        this.tienich = tienich;
    }

    public boolean isGiaohang() {
        return giaohang;
    }

    public void setGiaohang(boolean giaohang) {
        this.giaohang = giaohang;
    }

    public String getGiomocua() {
        return giomocua;
    }

    public void setGiomocua(String giomocua) {
        this.giomocua = giomocua;
    }

    public String getGiodongcua() {
        return giodongcua;
    }

    public void setGiodongcua(String giodongcua) {
        this.giodongcua = giodongcua;
    }

    public String getTenquanan() {
        return tenquanan;
    }

    public void setTenquanan(String tenquanan) {
        this.tenquanan = tenquanan;
    }

    public String getVideogioithieu() {
        return videogioithieu;
    }

    public void setVideogioithieu(String videogioithieu) {
        this.videogioithieu = videogioithieu;
    }

    public String getMaquanan() {
        return maquanan;
    }

    public void setMaquanan(String maquanan) {
        this.maquanan = maquanan;
    }

    private DataSnapshot dataRoot;

    public void getListStore(final ODauInterface odauInterface, final Location vitrihientai, final int itemtieptheo, final int itemdaco){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataRoot = dataSnapshot;
                LayDanhSachQuanAn(dataSnapshot, odauInterface, vitrihientai, itemtieptheo, itemdaco);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        if (dataRoot != null){
            LayDanhSachQuanAn(dataRoot, odauInterface, vitrihientai, itemtieptheo, itemdaco);
        }else {
            nodeRoot.addListenerForSingleValueEvent(valueEventListener);
        }
    }

    private void LayDanhSachQuanAn(DataSnapshot dataSnapshot, ODauInterface oDauInterface, Location vitrihientai, int itemtieptheo, int itemdaco){
        DataSnapshot dataSnapshotStore = dataSnapshot.child("quanans");
        int i = 0;
        // Lấy danh sách quán ăn
        for (DataSnapshot valueStore: dataSnapshotStore.getChildren()){
            if (i == itemtieptheo){
                break;
            }
            if (i < itemdaco){
                i++;
                continue;
            }
            i++;
            StoreModel storeModel = valueStore.getValue(StoreModel.class);
            storeModel.setMaquanan(valueStore.getKey());
            DataSnapshot dataSnapshotImageStore = dataSnapshot.child("hinhanhquanans").child(valueStore.getKey());
            // Lấy hình ảnh quán ăn của quán ăn theo mã
            List<String> hinhanhList = new ArrayList<>();
            for (DataSnapshot valueImageStore: dataSnapshotImageStore.getChildren()){
                hinhanhList.add(valueImageStore.getValue(String.class));
            }
            // Lấy danh sách bình luận của quán ăn
            DataSnapshot dataSnapshotComment = dataSnapshot.child("binhluans").child(storeModel.getMaquanan());
            List<CommentModel> commentModels = new ArrayList<>();
            for (DataSnapshot valueCommentStore: dataSnapshotComment.getChildren()){
                CommentModel commentModel = valueCommentStore.getValue(CommentModel.class);
                commentModel.setMabinhluan(valueCommentStore.getKey());
                MemberModel memberModel = dataSnapshot.child("thanhviens").child(commentModel.getMauser()).getValue(MemberModel.class);
                commentModel.setMemberModel(memberModel);

                //Lấy hình ảnh comment quán ăn
                List<String> hinhanhcommentList = new ArrayList<>();
                DataSnapshot dataSnapshotMaHinhAnhBinhLuan = dataSnapshot.child("hinhanhbinhluans").child(commentModel.getMabinhluan());
                for (DataSnapshot valueImageComment: dataSnapshotMaHinhAnhBinhLuan.getChildren()){
                    hinhanhcommentList.add(valueImageComment.getValue(String.class));
                }
                commentModel.setHinhanhbinhluanlist(hinhanhcommentList);
                commentModels.add(commentModel);
            }

            // Lấy chi nhánh quán ăn
            DataSnapshot dataSnapshotBranchStore = dataSnapshot.child("chinhanhquanans").child(storeModel.getMaquanan());
            List<BranchStoreModel> branchStoreModels = new ArrayList<>();
            for (DataSnapshot valueBranchStore: dataSnapshotBranchStore.getChildren()){
                BranchStoreModel branchStoreModel = valueBranchStore.getValue(BranchStoreModel.class);
                Location vitriquanan = new Location("");
                vitriquanan.setLatitude(branchStoreModel.getLatitude());
                vitriquanan.setLongitude(branchStoreModel.getLongitude());

                double khoangcach = vitrihientai.distanceTo(vitriquanan)/1000;
                branchStoreModel.setKhoangcach(khoangcach);
                branchStoreModels.add(branchStoreModel);
            }
            storeModel.setBranchStoreModels(branchStoreModels);
            storeModel.setCommentModels(commentModels);
            storeModel.setHinhanhquanan(hinhanhList);
            oDauInterface.getListStoreModel(storeModel);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (giaohang ? 1 : 0));
        dest.writeString(giomocua);
        dest.writeString(giodongcua);
        dest.writeString(tenquanan);
        dest.writeString(videogioithieu);
        dest.writeString(maquanan);
        dest.writeLong(luotthich);
        dest.writeStringList(tienich);
        dest.writeStringList(hinhanhquanan);
        dest.writeTypedList(branchStoreModels);
        dest.writeTypedList(commentModels);
    }
}
