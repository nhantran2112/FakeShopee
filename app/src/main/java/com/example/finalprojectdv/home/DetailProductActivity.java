package com.example.finalprojectdv.home;

import static com.example.finalprojectdv.R.drawable.*;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.finalprojectdv.R;
import com.example.finalprojectdv.User;
import com.example.finalprojectdv.cart.CartFragment;
import com.example.finalprojectdv.message.ChatRoomActivity;
import com.example.finalprojectdv.other.MyImageSwitcher;
import com.example.finalprojectdv.store.Product;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

import vn.thanguit.toastperfect.ToastPerfect;

public class DetailProductActivity extends AppCompatActivity {
    private MyImageSwitcher idIVProduct;
    private TextView idTVPreImage,idTVNextImage,idTVPosition,idTVNameSneaker,
                    idTVPriceSneaker, idTVNameSeller,idTVAddress,idTVProducts,idTVClose,
                    idTVQuantity,idTVBrand,idTVCategory,idTVVersion,idTVAddressDetail,idTVDetailProduct;
    private ImageView idIVAvaSeller;
    private Button idBtnViewShop,idBtnChatShop,idBtnAddCart,idBtnBuyNow;
    private int position = 0;

    private Product product;
    private User user_seller;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        anhxa();
        setImageSwitch();
        setProduct();
        getSeller();

        idTVPreImage.setOnClickListener(v -> previousImage());
        idTVNextImage.setOnClickListener(v -> nextImage());
        idTVClose.setOnClickListener(v -> finish());

        idBtnChatShop.setOnClickListener(v ->{
            Intent i = new Intent(this , ChatRoomActivity.class);
            i.putExtra("account" , user_seller.account);
            i.putExtra("avatar", user_seller.avatar);
            i.putExtra("name", user_seller.name);

            startActivity(i);
        });

        idBtnAddCart.setOnClickListener(v -> {
            openDialogSelection("add");
        });
        idBtnBuyNow.setOnClickListener(v ->{
            openDialogSelection("buy");
        });

    }

    @SuppressLint("NonConstantResourceId")
    private void openDialogSelection(String option) {
        View viewDialog = getLayoutInflater().inflate(R.layout.layout_bottom_sheet_addcart,null);
        MaterialButton idBtnConfirm = viewDialog.findViewById(R.id.idBtnConfirm);

        RadioGroup idRGrSize = viewDialog.findViewById(R.id.idRGrSize);
        RadioGroup idRGrColor = viewDialog.findViewById(R.id.idRGrColor);

        EditText idETQuantity = viewDialog.findViewById(R.id.idETQuantity);

        final String[] colorSelect = new String[1];

        for (int i =0 ; i < product.color.size();i++){
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(30,30,30,30);

            RadioButton radioButton = new RadioButton(getApplicationContext());
            radioButton.setText(product.color.get(i));
            radioButton.setId(i+1000);
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setPadding(50,50,50,50);
            radioButton.setLayoutParams(params);
            radioButton.setButtonDrawable(null);
            int[][] states = new int[][] {
                    new int[] {-android.R.attr.state_checked}, // unchecked
                    new int[] { android.R.attr.state_checked}  // checked
            };

            int[] colors = new int[] {
                    Color.BLACK,
                    Color.WHITE,
            };

            ColorStateList myList = new ColorStateList(states, colors);
            radioButton.setTextColor(myList);
            radioButton.setBackgroundResource(custom_rdbutton_bg);
            idRGrColor.addView(radioButton);

        }

        if (option.equals("buy")){
            idBtnConfirm.setText("Buy it now");
            idBtnConfirm.setTextColor(Color.WHITE);
            idBtnConfirm.setBackgroundColor(Color.parseColor("#FF018786"));
            idBtnConfirm.setIcon(ContextCompat.getDrawable(this, ic_outline_monetization_on_24));
            idBtnConfirm.setIconTint(ColorStateList.valueOf(Color.WHITE));

        }
        idRGrSize.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case R.id.RBsize36:
                    product.size = 36;
                    break;
                case R.id.RBsize37:
                    product.size = 37;
                    break;
                case R.id.RBsize38:
                    product.size = 38;
                    break;
                case R.id.RBsize39:
                    product.size = 39;
                    break;
                case R.id.RBsize40:
                    product.size = 40;
                    break;
                case R.id.RBsize41:
                    product.size = 41;
                    break;
                case R.id.RBsize42:
                    product.size = 42;
                    break;
                case R.id.RBsize43:
                    product.size = 43;
                    break;
                case R.id.RBsize44:
                    product.size = 44;
                    break;
                default:
                    product.size = 0;
                    break;
            }
        });
        idRGrColor.setOnCheckedChangeListener((group, checkedId) -> {
            for (int i = 0; i < product.color.size(); i++) {
                if (i+1000 == checkedId){
                    colorSelect[0] = product.color.get(i);
                }
            }

        });


        idBtnConfirm.setOnClickListener(v -> {
            if (option.equals("buy")) {
                ToastPerfect.makeText(DetailProductActivity.this, ToastPerfect.SUCCESS, "Buy Successful", ToastPerfect.CENTER, ToastPerfect.LENGTH_SHORT).show();
                finish();
            }
            else{
                product.color = Arrays.asList(colorSelect);
                product.quantity= Integer.parseInt(idETQuantity.getText().toString());
                CartFragment.ListItemCart.add(product);
                ToastPerfect.makeText(DetailProductActivity.this, ToastPerfect.SUCCESS, "Add Successful", ToastPerfect.CENTER, ToastPerfect.LENGTH_SHORT).show();
                finish();
            }
        });

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(viewDialog);
        bottomSheetDialog.show();
    }

    private void anhxa(){
        idTVPreImage = findViewById(R.id.idTVPreImage);
        idTVNextImage = findViewById(R.id.idTVNextImage);
        idTVPosition = findViewById(R.id.idTVPosition);

        idIVProduct = findViewById(R.id.idIVProduct);
        idTVNameSneaker = findViewById(R.id.idTVNameSneaker);
        idTVPriceSneaker = findViewById(R.id.idTVPriceSneaker);
        idTVQuantity = findViewById(R.id.idTVQuantity);
        idTVBrand = findViewById(R.id.idTVBrand);
        idTVCategory = findViewById(R.id.idTVCategory);
        idTVVersion = findViewById(R.id.idTVVersion);
        idTVAddressDetail = findViewById(R.id.idTVAddressDetail);
        idTVDetailProduct = findViewById(R.id.idTVDetailProduct);

        idTVNameSeller = findViewById(R.id.idTVNameSeller);
        idTVAddress = findViewById(R.id.idTVAddress);
        idTVProducts = findViewById(R.id.idTVProducts);
        idIVAvaSeller = findViewById(R.id.idIVAvaSeller);
        idBtnViewShop = findViewById(R.id.idBtnViewShop);
        idBtnChatShop = findViewById(R.id.idBtnChatShop);

        idTVClose = findViewById(R.id.idTVClose);
        idBtnAddCart = findViewById(R.id.idBtnAddCart);
        idBtnBuyNow = findViewById(R.id.idBtnBuyNow);

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @SuppressLint("SetTextI18n")
    private void setProduct(){
        //nhan du lieu product truyen tu homefragment
        product = getIntent().getParcelableExtra("product");

        idTVNameSneaker.setText(product.name);
        idTVPriceSneaker.setText("$ "+ product.price);
        idTVQuantity.setText(String.valueOf(product.quantity));
        idTVBrand.setText(product.brand);
        idTVCategory.setText(product.category);
        idTVVersion.setText(product.version);
        idTVDetailProduct.setText(product.detail);

        idTVPosition.setText("1/"+product.listImages.size());

        showImage(product.listImages.get(0));

    }
    private void getSeller(){
        String account = product.account;
        mDatabase.child("user/"+account).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                user_seller =  task.getResult().getValue(User.class);
                idTVNameSeller.setText(user_seller.name);
                idTVAddress.setText(user_seller.address);
                Picasso.get().load(user_seller.avatar).into(idIVAvaSeller);
            }
        });

    }

    private void setImageSwitch(){
        Animation out= AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        Animation in= AnimationUtils.loadAnimation(this, android.R.anim.fade_in);

        idIVProduct.setInAnimation(in);
        idIVProduct.setOutAnimation(out);

        idIVProduct.setFactory(() -> {
            ImageView imageView = new ImageView(getApplicationContext());

            imageView.setBackgroundColor(Color.LTGRAY);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            ImageSwitcher.LayoutParams params= new ImageSwitcher.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
            return imageView;
        });
    }

    private void previousImage()  {
        if(position > 0) {
            position--;
            idTVPosition.setText(position+1+"/"+product.listImages.size());;
        }else  {
            Toast.makeText(getApplicationContext(), "No Previous Image", Toast.LENGTH_SHORT).show();
            return;
        }
        this.showImage(product.listImages.get(position));
    }

    private void nextImage()  {
        if(position < this.product.listImages.size()-1) {
            position++;
            idTVPosition.setText(position+1+"/"+product.listImages.size());;
        }else  {
            Toast.makeText(getApplicationContext(), "No Next Image", Toast.LENGTH_SHORT).show();
            return;
        }
        this.showImage(product.listImages.get(position));
    }


    private void showImage(String image)  {
        idIVProduct.setImageUrl(image);
    }

}

