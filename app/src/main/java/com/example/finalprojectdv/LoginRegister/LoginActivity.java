package com.example.finalprojectdv.LoginRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.finalprojectdv.MainActivity;
import com.example.finalprojectdv.R;
import com.example.finalprojectdv.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import vn.thanguit.toastperfect.ToastPerfect;

public class LoginActivity extends AppCompatActivity {
    private String account , password;
    private EditText ETaccount,ETpassword;
    private Button idbtnlogin;
    private TextView idTVRegister;
    private ProgressBar idPB;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        anhxa();
        idbtnlogin.setOnClickListener(view ->{
            account = ETaccount.getText().toString();
            password = ETpassword.getText().toString();
            if (account.length()>0 && password.length()>0) {
                idbtnlogin.setVisibility(View.GONE);
                idPB.setVisibility(View.VISIBLE);

                //check account in firebase
                mDatabase.child("user").child(account).get().addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                    else {
                        if(task.getResult().getValue() == null){
                            ToastPerfect.makeText(LoginActivity.this, ToastPerfect.ERROR, "TK sai ", ToastPerfect.CENTER, ToastPerfect.LENGTH_SHORT).show();
                        }
                        else{
                            User user =  task.getResult().getValue(User.class);
                            if(user.password.equals(password)){
                                ToastPerfect.makeText(LoginActivity.this, ToastPerfect.SUCCESS, "Đăng đăng nhập thành công ", ToastPerfect.CENTER, ToastPerfect.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginActivity.this , MainActivity.class);
                                i.putExtra("account" , account);
                                startActivity(i);
                            }
                            else
                                ToastPerfect.makeText(LoginActivity.this, ToastPerfect.ERROR, "MK sai ", ToastPerfect.CENTER, ToastPerfect.LENGTH_SHORT).show();
                        }
                    }
                });
                idbtnlogin.setVisibility(View.VISIBLE);
                idPB.setVisibility(View.GONE);
            }
            else
                ToastPerfect.makeText(LoginActivity.this, ToastPerfect.WARNING, "Vui lòng nhập đầy đủ thông tin", ToastPerfect.CENTER, ToastPerfect.LENGTH_SHORT).show();
        });

        idTVRegister.setOnClickListener(view -> {
            Intent i = new Intent(LoginActivity.this , RegisterActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });
    }

    private void anhxa(){
        ETaccount = findViewById(R.id.ETaccount);
        ETpassword = findViewById(R.id.ETpassword);
        idbtnlogin = findViewById(R.id.idbtnlogin);
        idTVRegister = findViewById(R.id.idTVRegister);
        idPB = findViewById(R.id.idPB);
        mDatabase = FirebaseDatabase.getInstance().getReference();


    }

}