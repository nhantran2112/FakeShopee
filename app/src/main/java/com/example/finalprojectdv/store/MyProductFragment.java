package com.example.finalprojectdv.store;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalprojectdv.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyProductFragment extends Fragment {
    private ArrayList<Product> productArrayList;
    private ProductRVAdapter productRVAdapter;
    private RecyclerView idRVMyProduct;
    private TextView idTVNotifi;

    private String account;

    private DatabaseReference mDatabase;

    public MyProductFragment(String account) {
        this.account = account;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_my_product, container, false);

        anhxa(view);
        getProduct();
        productRVAdapter.notifyDataSetChanged();


        return view;
    }
    private void anhxa(View view){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("product");
        idRVMyProduct = view.findViewById(R.id.idRVMyProduct);
        productArrayList = new ArrayList<>();
        productRVAdapter = new ProductRVAdapter(productArrayList, this.getActivity(),R.layout.item_sneaker);
        idRVMyProduct.setAdapter(productRVAdapter);

        idTVNotifi=view.findViewById(R.id.idTVNotifi);
    }

    private void getProduct(){
        productArrayList.clear();
        mDatabase.orderByChild("account").equalTo(account).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                productArrayList.add(snapshot.getValue(Product.class));
                if (productArrayList.size()>0){
                    idTVNotifi.setVisibility(View.GONE);
                }
                productRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                productArrayList.add(snapshot.getValue(Product.class));
                if (productArrayList.size()>0)
                    idTVNotifi.setVisibility(View.GONE);
                productRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
}