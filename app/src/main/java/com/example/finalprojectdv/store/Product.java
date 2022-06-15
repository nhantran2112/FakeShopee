package com.example.finalprojectdv.store;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class Product implements Parcelable {
    public String account;
    public int idproduct;
    public String name ;
    public String brand;
    public String category;
    public List<String> color;
    public String version;
    public float price;
    public int quantity;
    public String detail;
    public List<String> listImages;
    public int size;

    public Product(){

    }

    public Product(String account, int idproduct, String name, String brand, String category,
                   List<String> color, String version, float price, int quantity, String detail,
                   List<String> listImages) {
        this.account = account;
        this.idproduct = idproduct;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.color = color;
        this.version = version;
        this.price = price;
        this.quantity = quantity;
        this.detail = detail;
        this.listImages = listImages;
    }

    protected Product(Parcel in) {
        account = in.readString();
        idproduct = in.readInt();
        name = in.readString();
        brand = in.readString();
        category = in.readString();
        color = in.createStringArrayList();
        version = in.readString();
        price = in.readFloat();
        quantity = in.readInt();
        detail = in.readString();
        listImages = in.createStringArrayList();
        size = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(account);
        dest.writeInt(idproduct);
        dest.writeString(name);
        dest.writeString(brand);
        dest.writeString(category);
        dest.writeStringList(color);
        dest.writeString(version);
        dest.writeFloat(price);
        dest.writeInt(quantity);
        dest.writeString(detail);
        dest.writeStringList(listImages);
        dest.writeInt(size);
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


}
