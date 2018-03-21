package duy.tdgroup.appfoody.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import duy.tdgroup.appfoody.R;

/**
 * Created by phand on 5/25/2017.
 */

public class SignInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, FirebaseAuth.AuthStateListener, View.OnClickListener {
    GoogleApiClient googleApiClient;
    public static int REQUEST_CODE_SIGN_IN_GOOGLE = 3;
    public static int CHECK_AUTH_PROVIDER = 0;

    FirebaseAuth firebaseAuth;
    CallbackManager mCallbackFacebook;
    LoginManager loginManager;
    List<String> permissionFacebook = Arrays.asList("email", "public_profile");
    ProgressDialog progressDialog;

    @BindView(R.id.btnSignInGoogle)
    Button btnGoogleSignIn;
    @BindView(R.id.btnSignInFacebook)
    Button btnFacebookSignIn;
    @BindView(R.id.tvSignUp)
    TextView tvSignUp;
    @BindView(R.id.btnSignIn)
    Button btnSignIn;
    @BindView(R.id.edtSignInEmail)
    EditText edtEmail;
    @BindView(R.id.edtSignInPassword)
    EditText edtPass;
    @BindView(R.id.tvForgotPassword)
    TextView tvForgotPass;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        mCallbackFacebook = CallbackManager.Factory.create();
        loginManager = LoginManager.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();

        btnFacebookSignIn.setOnClickListener(this);
        btnGoogleSignIn.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        tvForgotPass.setOnClickListener(this);
        ClientSignInGoogle();
    }

//    Khởi tạo Client Đăng nhập google
    private void ClientSignInGoogle(){
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                .build();
    }
//    end

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(this);
    }
//    Mở form đăng nhập bằng google
    private void SignInGoogle(GoogleApiClient googleApiClient){
        CHECK_AUTH_PROVIDER = 1;
        Intent googlesigninIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(googlesigninIntent, REQUEST_CODE_SIGN_IN_GOOGLE);
    }
//    end
    private void SignInFacebook(){
        loginManager.logInWithReadPermissions(this, permissionFacebook);
        loginManager.registerCallback(mCallbackFacebook, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                CHECK_AUTH_PROVIDER = 2;
                String token_ID = loginResult.getAccessToken().getToken();
                AuthFireBase(token_ID);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

//    Lấy token đã đăng nhập bằng google để đăng nhập firebase
    private void AuthFireBase(String token){
        if(CHECK_AUTH_PROVIDER == 1){
            AuthCredential authCredential = GoogleAuthProvider.getCredential(token, null);
            firebaseAuth.signInWithCredential(authCredential);
        }else if(CHECK_AUTH_PROVIDER == 2){
            AuthCredential authCredential = FacebookAuthProvider.getCredential(token);
            firebaseAuth.signInWithCredential(authCredential);
        }
    }
//    end
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SIGN_IN_GOOGLE){
            if(resultCode == RESULT_OK){
                GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                GoogleSignInAccount signInAccount = signInResult.getSignInAccount();
                String token = signInAccount.getIdToken();
                AuthFireBase(token);
            }
        }else {
            mCallbackFacebook.onActivityResult(requestCode,resultCode,data);
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

//    Kiểm tra người dùng đăng nhập hay đăng xuất
    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
        }else {

        }
    }
    private void SignIn(){
        String email = edtEmail.getText().toString();
        String pass = edtPass.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(SignInActivity.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnSignInGoogle:

                SignInGoogle(googleApiClient);
                break;
            case R.id.btnSignInFacebook:
                SignInFacebook();
                break;
            case R.id.tvSignUp:
                Intent signupIntent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(signupIntent);
                break;
            case R.id.btnSignIn:
                SignIn();
                break;
            case R.id.tvForgotPassword:
                Intent forgotIntent = new Intent(SignInActivity.this, ForgotPassActivity.class);
                startActivity(forgotIntent);
                break;
        }
    }
//    end

}
