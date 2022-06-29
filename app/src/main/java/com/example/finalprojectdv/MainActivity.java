package com.example.finalprojectdv;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.finalprojectdv.LoginRegister.LoginActivity;
import com.example.finalprojectdv.cart.CartFragment;
import com.example.finalprojectdv.home.HomeFragment;
import com.example.finalprojectdv.info.DataUser;
import com.example.finalprojectdv.info.InfoFragment;
import com.example.finalprojectdv.message.ListChatRoomActivity;
import com.example.finalprojectdv.store.StoreFragment;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import vn.thanguit.toastperfect.ToastPerfect;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottom_navigation;
    private BottomNavigationView TopNavigation;

    private EditText idETSearch;


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = this.getIntent();
        String account = intent.getStringExtra("account");
        DataUser.account = account;
        if(intent.getStringExtra("addproduct") != null)
            openFragment(new StoreFragment(DataUser.account));
        else
            openFragment(new HomeFragment(null));
        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.page_1:
                    openFragment(new HomeFragment(null));
                    return true;
                case R.id.page_2:
                    openFragment(new CartFragment());
                    return true;
                case R.id.page_3:
                    openFragment(new StoreFragment(DataUser.account));
                    return true;
                case R.id.page_4:
                    openFragment(new InfoFragment(DataUser.account));
                    return true;
            }
            return false;
        });

        TopNavigation = findViewById(R.id.TopNavigation);
        TopNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.page_mess:
                    Intent i = new Intent(this ,ListChatRoomActivity.class );
                    startActivity(i);
                    return true;
                case R.id.page_notifi:
                    ToastPerfect.makeText(MainActivity.this, ToastPerfect.INFORMATION, "We will update this function", ToastPerfect.CENTER, ToastPerfect.LENGTH_SHORT).show();

            }
            return  false;
        });

        idETSearch = findViewById(R.id.idETSearch);
        idETSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                openFragment(new HomeFragment(v.getText().toString()));
                v.setText("");
                return true;
            }
            return false;
        });

    }

    private void openFragment(Fragment fragment) {
        Log.d("AAAA", "openFragment: ");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //this is a helper class that replaces the container with the fragment. You can replace or add fragments.
        transaction.replace(R.id.container, fragment);
        transaction.commit(); // commit() performs the action
    }

    //First commit
    //Second commit
}