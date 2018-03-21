package duy.tdgroup.appfoody.Controller;

import duy.tdgroup.appfoody.Model.MemberModel;

/**
 * Created by phand on 5/30/2017.
 */

public class DangKyController {
    MemberModel memberModel;
    public DangKyController(){
        memberModel = new MemberModel();
    }

    public void ThemThanhVienController(MemberModel memberModel, String uid){
        memberModel.ThemThongTinThanhVien(memberModel, uid);
    }
}
