package com.example.finalprojectdv.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalprojectdv.MainActivity;
import com.example.finalprojectdv.R;
import com.example.finalprojectdv.store.Product;
import com.example.finalprojectdv.store.ProductRVAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements CategoryRVAdapter.CategoryClickInterface{
    private ArrayList<Category> categoryArrayList;
    private CategoryRVAdapter categoryRVAdapter;

    private ArrayList<Product> productArrayList;
    private ProductRVAdapter productRVAdapter;

    private DatabaseReference mDatabase;
    private String dataSearch;

    public HomeFragment(String dataSearch) {
        this.dataSearch = dataSearch;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        anhxa(view);
        getCategory();
        if (dataSearch == null)
            getProduct("All");
        else
            getProductSearch(dataSearch);


        return view;

    }
    private void anhxa(View view){
        //Set category
        RecyclerView idRVCategory = view.findViewById(R.id.idRVCategory);
        categoryArrayList = new ArrayList<>();
        categoryRVAdapter = new CategoryRVAdapter(categoryArrayList , this.getActivity() , this );
        idRVCategory.setAdapter(categoryRVAdapter);
        categoryRVAdapter.notifyDataSetChanged();

        //set list product
        RecyclerView idRVProductHome = view.findViewById(R.id.idRVProductHome);
        productArrayList = new ArrayList<>();
        productRVAdapter = new ProductRVAdapter(productArrayList, this.getActivity(),R.layout.item_sneaker);
        idRVProductHome.setAdapter(productRVAdapter);
        productRVAdapter.notifyDataSetChanged();


        mDatabase = FirebaseDatabase.getInstance().getReference().child("product");


    }


    private void getCategory(){
        categoryArrayList.clear();
        categoryArrayList.add(new Category("All",R.drawable.img_category1));
        categoryArrayList.add(new Category("Adidas",R.drawable.img_category2));
        categoryArrayList.add(new Category("Puma",R.drawable.img_category3));
        categoryArrayList.add(new Category("Balenciaga",R.drawable.img_category4));
        categoryArrayList.add(new Category("Converse",R.drawable.img_category5));
        categoryArrayList.add(new Category("Nike",R.drawable.img_category6));
        categoryArrayList.add(new Category("Vans",R.drawable.img_category7));
        categoryArrayList.add(new Category("New Blance",R.drawable.img_category8));
        categoryRVAdapter.notifyDataSetChanged();
    }

    private void getProduct(String category){
        productArrayList.clear();
        if (category.equals("All")){
            mDatabase.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    for (DataSnapshot product: dataSnapshot.getChildren()){
                        productArrayList.add(product.getValue(Product.class));
                    }
                    productRVAdapter.notifyDataSetChanged();
                    Log.d("CCCC", "onSuccess: Lay sp thanh cong");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("CCCC", "onFailure: Lay sp kh thanh cong");
                }
            });
        }
        else {
            mDatabase.orderByChild("brand").equalTo(category).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot product: snapshot.getChildren()){
                        productArrayList.add(product.getValue(Product.class));
                    }
                    productRVAdapter.notifyDataSetChanged();
                    Log.d("CCCC", "onSuccess: Lay sp thanh cong");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("CCCC", "onFailure: Lay sp kh thanh cong");
                }
            });
        }
    }
    private void getProductSearch(String value){
        productArrayList.clear();
        mDatabase.orderByChild("name").startAt(value.toUpperCase()).endAt(value.toLowerCase()+"\uf8ff").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot product: snapshot.getChildren()){
                    productArrayList.add(product.getValue(Product.class));
                }
                productRVAdapter.notifyDataSetChanged();
                Log.d("CCCC", "onSuccess: Lay sp thanh cong");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("CCCC", "onFailure: Lay sp kh thanh cong");
            }
        });
    }

    @Override
    public void onCategoryClick(int position) {
        Category category = categoryArrayList.get(position);
        getProduct(category.getCategory());
    }

}