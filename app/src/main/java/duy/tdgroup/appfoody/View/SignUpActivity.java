package duy.tdgroup.appfoody.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import duy.tdgroup.appfoody.Controller.DangKyController;
import duy.tdgroup.appfoody.Model.MemberModel;
import duy.tdgroup.appfoody.R;

/**
 * Created by phand on 5/26/2017.
 */

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    @BindView(R.id.edtSignUpEmail)
    EditText edtEmail;
    @BindView(R.id.edtSignUpPassword)
    EditText edtPassword;
    @BindView(R.id.edtSignUpEnterPass)
    EditText edtEnterPass;

    DangKyController dangKyController;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
    }
    @OnClick(R.id.btnSignUp)
    public void SignUp(){
        progressDialog.setMessage("Đang xử lý...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        final String email = edtEmail.getText().toString();
        String pass = edtPassword.getText().toString();
        String enterpass = edtEnterPass.getText().toString();

        if (email.equals("")){
            edtEmail.setError("Mời bạn nhập email");
        }else if (pass.equals("")){
            edtPassword.setError("Mời bạn nhập password");
        }else if (enterpass.equals("")){
            edtEnterPass.setError("Mời bạn nhập lại password");
        }else if (!enterpass.equals(pass)){
            edtEnterPass.setError("Nhập lại pass không đúng");
        }else {
            firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        MemberModel memberModel = new MemberModel();
                        memberModel.setHoten(email);
                        memberModel.setHinhanh("user2.png");
                        String uid = task.getResult().getUser().getUid();
                        dangKyController = new DangKyController();
                        dangKyController.ThemThanhVienController(memberModel, uid);
                        Toast.makeText(SignUpActivity.this, "Đăng ký tài khoản thành công", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        Intent signinIntent = new Intent(SignUpActivity.this, SignInActivity.class);
                        startActivity(signinIntent);
                        finish();
                    }
                }
            });
        }
    }
}
