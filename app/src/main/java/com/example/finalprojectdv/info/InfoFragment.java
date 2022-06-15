package com.example.finalprojectdv.info;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.finalprojectdv.LoginRegister.RegisterActivity;
import com.example.finalprojectdv.MainActivity;
import com.example.finalprojectdv.R;
import com.example.finalprojectdv.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import vn.thanguit.toastperfect.ToastPerfect;


public class InfoFragment extends Fragment {
    private ImageView idIVEditPerson;
    private EditText ETaccount, ETpassword,ETname,ETaddress,ETphonenumber;
    private TextView idTVChangeImage,idTVSave;
    private ProgressBar idPB;

    private String account;
    private DatabaseReference mDatabase;
    private StorageReference storageRef;

    private User user;
    private int checkchangeava = 0;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri uri;


    public InfoFragment(String account) {
        this.account = account;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        anhxa(view);

        mDatabase.child("user").child(account).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                user =  task.getResult().getValue(User.class);
                ETaccount.setText(user.account);
                ETpassword.setText(user.password);
                ETname.setText(user.name);
                ETaddress.setText(user.address);
                ETphonenumber.setText(user.phonenumber);
                Picasso.get().load(user.avatar).into(idIVEditPerson);

            }
        });

        idTVChangeImage.setOnClickListener(v -> {
            chooseImage();
        });
        idIVEditPerson.setOnClickListener(v ->{
            chooseImage();
        });

        idTVSave.setOnClickListener(v ->{
            if (ETpassword.getText().toString().length()>0 && ETname.getText().toString().length()>0){
                if(checkchangeava ==1){
                    UploadTask uploadTask = storageRef.child("image/avatar/" + account).putFile(uri);
                    uploadTask.addOnFailureListener(e -> {
                        Log.d("CCCCC", e.getLocalizedMessage());
                    }).addOnSuccessListener(taskSnapshot -> {
                        Log.d("CCCCC","Upload thanh cong");
                        storageRef.child("image/avatar/"+account).getDownloadUrl().addOnSuccessListener(uri -> {
                            user.avatar = uri.toString();
                        }).addOnFailureListener(e -> Log.d("CCCCFP2", "onFailure: "+e.getLocalizedMessage()));

                    });
                }
                User user1 = new User( ETaccount.getText().toString() ,ETpassword.getText().toString(),ETname.getText().toString(),
                        user.avatar, ETaddress.getText().toString(),ETphonenumber.getText().toString());
                mDatabase.child("user").child(account).setValue(user1);
                ToastPerfect.makeText(getActivity(), ToastPerfect.SUCCESS, "Lưu thông tin thành công ", ToastPerfect.CENTER, ToastPerfect.LENGTH_SHORT).show();

            }
        });

        return view;
    }

    private void anhxa(View view){
        idIVEditPerson = view.findViewById(R.id.idIVEditPerson);
        ETaccount = view.findViewById(R.id.ETaccount);
        ETpassword = view.findViewById(R.id.ETpassword);
        ETname = view.findViewById(R.id.ETname);
        ETaddress = view.findViewById(R.id.ETaddress);
        ETphonenumber = view.findViewById(R.id.ETphonenumber);
        idTVChangeImage = view.findViewById(R.id.idTVChangeImage);
        idTVSave = view.findViewById(R.id.idTVSave);
        idPB = view.findViewById(R.id.idPB);

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                idIVEditPerson.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            checkchangeava = 1;
        }
        else
            ToastPerfect.makeText(getActivity(), ToastPerfect.INFORMATION, "Bạn chưa chọn ảnh", ToastPerfect.CENTER, ToastPerfect.LENGTH_SHORT).show();
        super.onActivityResult(requestCode, resultCode, data);
    }
}