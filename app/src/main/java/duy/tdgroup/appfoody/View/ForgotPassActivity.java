package duy.tdgroup.appfoody.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import duy.tdgroup.appfoody.R;

/**
 * Created by phand on 5/26/2017.
 */

public class ForgotPassActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.edtForgotPass)
    EditText edtEmail;
    @BindView(R.id.tvRegister)
    TextView tvRegister;
    @BindView(R.id.btnSendEmail)
    Button btnSendMail;

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();

        tvRegister.setOnClickListener(this);
        btnSendMail.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.tvRegister:
                Intent signinIntent = new Intent(ForgotPassActivity.this, SignUpActivity.class);
                startActivity(signinIntent);
                break;
            case R.id.btnSendEmail:
                String email = edtEmail.getText().toString();
                boolean checkmail = CheckMail(email);
                if (checkmail == true){
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ForgotPassActivity.this, "Gửi email thành công", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(this, "Email không hợp lệ, xin vui lòng thử lại", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public boolean CheckMail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
