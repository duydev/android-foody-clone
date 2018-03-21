package duy.tdgroup.appfoody.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by phand on 6/2/2017.
 */

public class BranchStoreModel implements Parcelable{
    String diachi;
    double latitude;
    double longitude;
    double khoangcach;

    public BranchStoreModel(){

    }

    protected BranchStoreModel(Parcel in) {
        diachi = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        khoangcach = in.readDouble();
    }

    public static final Creator<BranchStoreModel> CREATOR = new Creator<BranchStoreModel>() {
        @Override
        public BranchStoreModel createFromParcel(Parcel in) {
            return new BranchStoreModel(in);
        }

        @Override
        public BranchStoreModel[] newArray(int size) {
            return new BranchStoreModel[size];
        }
    };

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getKhoangcach() {
        return khoangcach;
    }

    public void setKhoangcach(double khoangcach) {
        this.khoangcach = khoangcach;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(diachi);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeDouble(khoangcach);
    }
}
