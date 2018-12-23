package tech.rtsproduction.tuktuk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginFragment extends Fragment {

    TextInputLayout mEmail, mPassword;
    //ProgressBar mProgressLogin;
    TextView mForgetPassword;
    Button mLogin;

    public LoginFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frame_login, container, false);
        mEmail = root.findViewById(R.id.tip_email_login);
        mPassword = root.findViewById(R.id.tip_password_login);
        mLogin = root.findViewById(R.id.btn_login_login);
        mForgetPassword = root.findViewById(R.id.text_forgot_password_login);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mEmail.getEditText().getText()) || TextUtils.isEmpty(mPassword.getEditText().getText())) {
                    Toast.makeText(getContext(), "Please Fill the Details", Toast.LENGTH_SHORT).show();
                } else {
                    /*AuthHelper helper = new AuthHelper(getContext());
                    if(helper.checkUser(mEmail.getEditText().getText().toString(), mPassword.getEditText().getText().toString()) == -1){
                        Toast.makeText(getContext(), "User Not Exists!", Toast.LENGTH_SHORT).show();
                    }else{
                    */
                    startActivity(new Intent(getContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    //}
                }
            }
        });

        mForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "To be Implemented", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}
