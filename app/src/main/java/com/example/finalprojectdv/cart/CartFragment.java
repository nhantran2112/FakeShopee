package com.example.finalprojectdv.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalprojectdv.R;
import com.example.finalprojectdv.store.Product;

import java.util.ArrayList;


public class CartFragment extends Fragment {
    public static ArrayList<Product> ListItemCart = new ArrayList<>();

    private ItemCartRVAdapter itemCartRVAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        RecyclerView idRVMyCart = view.findViewById(R.id.idRVMyCart);
        //ListItemCart = new ArrayList<>();
        itemCartRVAdapter = new ItemCartRVAdapter(ListItemCart,R.layout.item_product_cart,getActivity());
        idRVMyCart.setAdapter(itemCartRVAdapter);

        itemCartRVAdapter.notifyDataSetChanged();

        return view;
    }
}