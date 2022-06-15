package com.example.finalprojectdv.LoginRegister;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalprojectdv.R;
import com.example.finalprojectdv.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import vn.thanguit.toastperfect.ToastPerfect;

public class RegisterActivity extends AppCompatActivity {
    private ImageView idIVRegister ;
    private TextView idTVChangeImageReg, idTVlogin;
    private EditText ETaccountReg, ETpasswordReg, ETnameReg;
    private Button idbtnregister;
    private String account , password , name ;
    private ProgressBar idPB;
    private DatabaseReference mDatabase;
    private StorageReference storageRef;

    private String urlavatar;

    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        anhxa();
        idTVlogin.setOnClickListener(view -> {
            Intent i = new Intent(RegisterActivity.this , LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });
        idTVChangeImageReg.setOnClickListener(v ->{
            chooseImage();
        });

        idIVRegister.setOnClickListener(v ->{
            chooseImage();
        });
        idbtnregister.setOnClickListener(view -> {
            account = ETaccountReg.getText().toString();
            password = ETpasswordReg.getText().toString();
            name = ETnameReg.getText().toString();

            if(account.length()>0 && password.length() >0 && name.length()>0) {
                idbtnregister.setVisibility(View.GONE);
                idPB.setVisibility(View.VISIBLE);

                //check account
                mDatabase.child("user").child(account).get().addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                    else {
                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
                        if(task.getResult().getValue() == null){
                            //Insert avatar storage
                            if(filePath != null){
                                UploadTask uploadTask = storageRef.child("image/avatar/" + account).putFile(filePath);
                                uploadTask.addOnFailureListener(e -> {
                                    Log.d("CCCCC", e.getLocalizedMessage());
                                }).addOnSuccessListener(taskSnapshot -> {
                                    Log.d("CCCCC","Upload thanh cong");
                                    storageRef.child("image/avatar/"+account).getDownloadUrl().addOnSuccessListener(uri -> {
                                        urlavatar = uri.toString();
                                        //Insert info firebase
                                        User user = new User(account,password,name,urlavatar);
                                        mDatabase.child("user").child(account).setValue(user);
                                        ToastPerfect.makeText(RegisterActivity.this, ToastPerfect.SUCCESS, "Đăng ký thành công vui lòng đăng nhập lại ", ToastPerfect.CENTER, ToastPerfect.LENGTH_SHORT).show();
                                        finish();
                                    }).addOnFailureListener(e -> Log.d("CCCCFP2", "onFailure: "+e.getLocalizedMessage()));

                                });
                            }
                        }
                        else
                            ToastPerfect.makeText(RegisterActivity.this, ToastPerfect.ERROR, "Đã có người sử dụng Account này ", ToastPerfect.CENTER, ToastPerfect.LENGTH_SHORT).show();
                    }
                });
                idbtnregister.setVisibility(View.VISIBLE);
                idPB.setVisibility(View.GONE);
            }
            else
                ToastPerfect.makeText(RegisterActivity.this, ToastPerfect.WARNING, "Vui lòng nhập đủ thông tin ", ToastPerfect.CENTER, ToastPerfect.LENGTH_SHORT).show();
        });
    }

    private void anhxa(){
        idIVRegister = findViewById(R.id.idIVRegister);
        idTVChangeImageReg = findViewById(R.id.idTVChangeImageReg);
        idTVlogin = findViewById(R.id.idTVlogin);
        ETaccountReg = findViewById(R.id.ETaccountReg);
        ETpasswordReg = findViewById(R.id.ETpasswordReg);
        ETnameReg = findViewById(R.id.ETnameReg);
        idbtnregister = findViewById(R.id.idbtnregister);
        idPB = findViewById(R.id.idPB);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        storageRef = FirebaseStorage.getInstance().getReference();

    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                idIVRegister.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}