package com.example.finalprojectdv.store;

import static android.app.Activity.RESULT_OK;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.finalprojectdv.MainActivity;
import com.example.finalprojectdv.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vn.thanguit.toastperfect.ToastPerfect;


public class AddProductFragment extends Fragment {
    private EditText ETnameproduct, ETversionproduct, ETpriceproduct,ETquantityproduct,ETdetailproduct;
    private RadioButton idbrandVans , idbrandNike , idbrandBalenciaga, idbrandPuma ,idbrandConverse, idbrandAdidas ,idbrandNewBlance ,idbrandOther,
            idcategoryBasketball, idcategoryBoots, idcategoryCleats ,idcategoryLifestyle, idcategoryRunning, idcategorySandals,idcategorySkateboarding,idcategoryOther;
    private CheckBox idCBBlack,idCBWhite,idCBGrey,idCBGreen,idCBBlue,idCBTeal,idCBYellow,idCBRed,idCBOrange,idCBBrown,idCBPurple,idCBMulti;
    private Button idbtnaddproduct,idbtnaddimage;
    private ProgressBar idPB;
    private TextView idclose;

    private String account;
    private int idproduct;
    private String name ;
    private String brand;
    private String category;
    private List<String> color;
    private String version;
    private float price;
    private int quantity;
    private String detail;
    private List<String> listImages;
    private List<Uri> uriList;
    private ImageProductRVAdapter imageProductRVAdapter;

    private DatabaseReference mDatabase;
    private StorageReference storageRef;


    int PICK_IMAGE_MULTIPLE = 1;

    public AddProductFragment(String account) {
        this.account = account;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);
        anhxa(view);

        idbtnaddimage.setOnClickListener(v -> {
            uriList.clear();
            Intent intent = new Intent();

            // setting type to select to be image
            intent.setType("image/*");

            // allowing multiple image to be selected
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);



        });
        idclose.setOnClickListener(v -> {
            uriList.clear();
            listImages.clear();
            idclose.setVisibility(View.GONE);
            imageProductRVAdapter.notifyDataSetChanged();
        });

        idbtnaddproduct.setOnClickListener(v ->{
            getText();
            if (name.length()>0 && version.length()>0 && detail.length()>0 && price !=0
                    && brand.length()>0 && category.length()>0 && color.size()>0){
                idPB.setVisibility(View.VISIBLE);
                idbtnaddproduct.setVisibility(View.GONE);

                uploadImage();

                ToastPerfect.makeText(getActivity(), ToastPerfect.SUCCESS, "Đăng sản phẩm thành công ", ToastPerfect.CENTER, ToastPerfect.LENGTH_SHORT).show();
                idPB.setVisibility(View.GONE);
                idbtnaddproduct.setVisibility(View.VISIBLE);
            }
            else {
                ToastPerfect.makeText(getActivity(), ToastPerfect.WARNING, "Vui lòng nhập đầy đủ thông tin ", ToastPerfect.CENTER, ToastPerfect.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void anhxa(View view){
        ETnameproduct = view.findViewById(R.id.ETnameproduct);
        ETversionproduct = view.findViewById(R.id.ETversionproduct);
        ETpriceproduct = view.findViewById(R.id.ETpriceproduct);
        ETdetailproduct = view.findViewById(R.id.ETdetailproduct);
        ETquantityproduct = view.findViewById(R.id.ETquantityproduct);

        idbrandVans = view.findViewById(R.id.idbrandVans);
        idbrandNike = view.findViewById(R.id.idbrandNike);
        idbrandBalenciaga = view.findViewById(R.id.idbrandBalenciaga);
        idbrandPuma = view.findViewById(R.id.idbrandPuma);
        idbrandConverse = view.findViewById(R.id.idbrandConverse);
        idbrandAdidas = view.findViewById(R.id.idbrandAdidas);
        idbrandNewBlance = view.findViewById(R.id.idbrandNewBlance);
        idbrandOther = view.findViewById(R.id.idbrandOther);

        idcategoryBasketball = view.findViewById(R.id.idcategoryBasketball);
        idcategoryBoots = view.findViewById(R.id.idcategoryBoots);
        idcategoryCleats = view.findViewById(R.id.idcategoryCleats);
        idcategoryLifestyle = view.findViewById(R.id.idcategoryLifestyle);
        idcategoryRunning = view.findViewById(R.id.idcategoryRunning);
        idcategorySandals = view.findViewById(R.id.idcategorySandals);
        idcategorySkateboarding = view.findViewById(R.id.idcategorySkateboarding);
        idcategoryOther = view.findViewById(R.id.idcategoryOther);

        idCBBlack = view.findViewById(R.id.idCBBlack);
        idCBWhite = view.findViewById(R.id.idCBWhite);
        idCBGrey = view.findViewById(R.id.idCBGrey);
        idCBGreen = view.findViewById(R.id.idCBGreen);
        idCBBlue = view.findViewById(R.id.idCBBlue);
        idCBTeal = view.findViewById(R.id.idCBTeal);
        idCBYellow = view.findViewById(R.id.idCBYellow);
        idCBRed = view.findViewById(R.id.idCBRed);
        idCBOrange = view.findViewById(R.id.idCBOrange);
        idCBBrown = view.findViewById(R.id.idCBBrown);
        idCBPurple = view.findViewById(R.id.idCBPurple);
        idCBMulti = view.findViewById(R.id.idCBMulti);

        idbtnaddimage = view.findViewById(R.id.idbtnaddimage);
        idclose = view.findViewById(R.id.idclose);
        idbtnaddproduct = view.findViewById(R.id.idbtnaddproduct);
        idPB = view.findViewById(R.id.idPB);

        listImages = new ArrayList<>();
        uriList = new ArrayList<>();

        RecyclerView idRVImagePreview = view.findViewById(R.id.idRVImagePreview);
        imageProductRVAdapter = new ImageProductRVAdapter(null, R.layout.image_item_createproduct, uriList);
        idRVImagePreview.setAdapter(imageProductRVAdapter);
        imageProductRVAdapter.notifyDataSetChanged();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        storageRef = FirebaseStorage.getInstance().getReference();

    }

    private String getBrand(){
        String brand;
        if(idbrandVans.isChecked())
            brand = "Vans";
        else if(idbrandAdidas.isChecked())
            brand = "Adidas";
        else if(idbrandBalenciaga.isChecked())
            brand= "Balenciaga";
        else if(idbrandConverse.isChecked())
            brand = "Converse";
        else if(idbrandNike.isChecked())
            brand = "Nike";
        else if(idbrandNewBlance.isChecked())
            brand="New Blance";
        else if(idbrandPuma.isChecked())
            brand="Puma";
        else
            brand="Other";
        return brand;
    }

    private String getCategory(){
        String category;
        if(idcategoryBasketball.isChecked())
            category = "Basketball";
        else if(idcategoryBoots.isChecked())
            category = "Boots";
        else if(idcategoryCleats.isChecked())
            category="Cleats";
        else if(idcategoryLifestyle.isChecked())
            category="Lifestyle";
        else if(idcategoryRunning.isChecked())
            category="Running";
        else if (idcategorySandals.isChecked())
            category="Sandals";
        else if(idcategorySkateboarding.isChecked())
            category="Skateboarding";
        else
            category="Other";
        return category;
    }

    private List<String> getColor(){
        List<String> listcolor;
        listcolor = new ArrayList<>();

        if (idCBBlack.isChecked())
            listcolor.add("Black");
        if (idCBBlue.isChecked())
            listcolor.add("Blue");
        if (idCBBrown.isChecked())
            listcolor.add("Brown");
        if (idCBGreen.isChecked())
            listcolor.add("Green");
        if (idCBGrey.isChecked())
            listcolor.add("Grey");
        if (idCBOrange.isChecked())
            listcolor.add("Orange");
        if (idCBPurple.isChecked())
            listcolor.add("Purple");
        if (idCBWhite.isChecked())
            listcolor.add("White");
        if (idCBTeal.isChecked())
            listcolor.add("Teal");
        if (idCBYellow.isChecked())
            listcolor.add("Yellow");
        if (idCBRed.isChecked())
            listcolor.add("Red");
        if (idCBMulti.isChecked())
            listcolor.add("Multi");

        return listcolor;
    }

    private void getText(){
        name = ETnameproduct.getText().toString();
        version = ETversionproduct.getText().toString();
        detail = ETdetailproduct.getText().toString();
        price = parseFloat(ETpriceproduct.getText().toString());
        quantity = parseInt(ETquantityproduct.getText().toString());
        brand = getBrand();
        category=getCategory();
        color = getColor();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // When an Image is picked
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
            // Get the Image from data
            if (data.getClipData() != null) {
                int cout = data.getClipData().getItemCount();
                for (int i = 0; i < cout; i++) {
                    // adding imageuri in array
                    Uri imageurl = data.getClipData().getItemAt(i).getUri();
                    uriList.add(imageurl);
                }

            } else {
                Uri imageurl = data.getData();
                uriList.add(imageurl);
            }
            idclose.setVisibility(View.VISIBLE);
            imageProductRVAdapter.notifyDataSetChanged();
        } else {
            // show this if no image is selected
            ToastPerfect.makeText(getActivity(), ToastPerfect.INFORMATION, "Bạn chưa chọn ảnh", ToastPerfect.CENTER, ToastPerfect.LENGTH_SHORT).show();
        }

    }
    private void uploadImage(){
        Random generator = new Random();
        idproduct = generator.nextInt(9999);
        for(int i=0 ; i<uriList.size();i++){
            int finalI = i;
            storageRef.child("image/product/" + idproduct+"/images"+i).putFile(uriList.get(i))
            .addOnFailureListener(e -> Log.d("CCCC", "onFailure: up anh kh thanh cong"))
            .addOnSuccessListener(taskSnapshot -> {
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(uri -> {
                    String url = uri.toString();
                    listImages.add(url);
                    Log.d("CCCC", "onSuccess: them link anh thanh cong");
                    if(finalI == uriList.size()-1)
                        uploadInfoProduct();
                });
                Log.d("CCCC", "onSuccess: upload anh thanh cong");
            });
        }
    }
    private void uploadInfoProduct(){
        Product product = new Product(account,idproduct,name,brand,category,color,version,price,quantity,detail,listImages);
        mDatabase.child("product").child(String.valueOf(idproduct)).setValue(product)
        .addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Log.d("CCCC", "onComplete: Them sp thanh cong");
            }
        });
    }
}
