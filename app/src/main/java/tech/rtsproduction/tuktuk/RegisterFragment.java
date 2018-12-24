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
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

public class RegisterFragment extends Fragment {

    private TextInputLayout mName, mEmail, mPassword, mConfirmPassword;
    //private ProgressBar mProgressbar;
    private CheckBox mCheckTerms;
    private Button mRegister;

    public RegisterFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frame_register, container, false);
        mName = root.findViewById(R.id.til_name_register);
        mEmail = root.findViewById(R.id.til_email_register);
        mPassword = root.findViewById(R.id.til_pass_register);
        mConfirmPassword = root.findViewById(R.id.til_confirmpass_register);
        //mProgressbar = root.findViewById(R.id.progressbar_register);
        mRegister = root.findViewById(R.id.btn_signup_register);
        mCheckTerms = root.findViewById(R.id.termsCheckbox_register);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mName.getEditText().getText()) || TextUtils.isEmpty(mEmail.getEditText().getText()) || TextUtils.isEmpty(mPassword.getEditText().getText()) || TextUtils.isEmpty(mConfirmPassword.getEditText().getText())) {
                    Toast.makeText(getContext(), "Please fill all crendentials", Toast.LENGTH_SHORT).show();
                } else if (!TextUtils.equals(mPassword.getEditText().getText(), mConfirmPassword.getEditText().getText())) {
                    Toast.makeText(getContext(), "Password don't match", Toast.LENGTH_SHORT).show();
                } else if (!mCheckTerms.isChecked()) {
                    Toast.makeText(getContext(), "Please accept terms and conditions", Toast.LENGTH_SHORT).show();
                } else {
                    //insertData(mName.getEditText().getText().toString(), mEmail.getEditText().getText().toString(), mPass.getEditText().getText().toString());
                    startActivity(new Intent(getContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));

                }
            }
        });
        return root;
    }
}
