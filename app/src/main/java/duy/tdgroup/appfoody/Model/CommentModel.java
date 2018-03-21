package duy.tdgroup.appfoody.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by phand on 5/31/2017.
 */

public class CommentModel implements Parcelable {
    double chamdiem;
    long luotthich;
    MemberModel memberModel;
    String noidung;
    String tieude;
    String mauser;
    String mabinhluan;
    List<String> hinhanhbinhluanlist;

    protected CommentModel(Parcel in) {
        chamdiem = in.readDouble();
        luotthich = in.readLong();
        noidung = in.readString();
        tieude = in.readString();
        mauser = in.readString();
        mabinhluan = in.readString();
        hinhanhbinhluanlist = in.createStringArrayList();
        memberModel = in.readParcelable(MemberModel.class.getClassLoader());
    }

    public static final Creator<CommentModel> CREATOR = new Creator<CommentModel>() {
        @Override
        public CommentModel createFromParcel(Parcel in) {
            return new CommentModel(in);
        }

        @Override
        public CommentModel[] newArray(int size) {
            return new CommentModel[size];
        }
    };

    public String getMabinhluan() {
        return mabinhluan;
    }

    public void setMabinhluan(String mabinhluan) {
        this.mabinhluan = mabinhluan;
    }

    public List<String> getHinhanhbinhluanlist() {
        return hinhanhbinhluanlist;
    }

    public void setHinhanhbinhluanlist(List<String> hinhanhlist) {
        this.hinhanhbinhluanlist = hinhanhlist;
    }

    public CommentModel(){

    }
    public double getChamdiem() {
        return chamdiem;
    }

    public String getMauser() {
        return mauser;
    }

    public void setMauser(String mauser) {
        this.mauser = mauser;
    }

    public void setChamdiem(double chamdiem) {
        this.chamdiem = chamdiem;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public MemberModel getMemberModel() {
        return memberModel;
    }

    public void setMemberModel(MemberModel memberModel) {
        this.memberModel = memberModel;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(chamdiem);
        dest.writeLong(luotthich);
        dest.writeString(noidung);
        dest.writeString(tieude);
        dest.writeString(mauser);
        dest.writeString(mabinhluan);
        dest.writeStringList(hinhanhbinhluanlist);
        dest.writeParcelable(memberModel, flags);
    }
}
