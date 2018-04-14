package com.example.jose.androidkamis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.jose.androidkamis.Interface.ItemClickListener;
import com.example.jose.androidkamis.ViewHolder.ProductViewHolder;
import com.example.jose.androidkamis.model.Product;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Product, ProductViewHolder> adapterProduct;
    FirebaseDatabase database;
    DatabaseReference productList;
    String categoryId = "";

    //Search Funcionality
    FirebaseRecyclerAdapter<Product, ProductViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        //Init fire base
        database = FirebaseDatabase.getInstance();
        productList = database.getReference("Product");
        recyclerView = findViewById(R.id.recycler_products);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Get intent here
        if(getIntent() != null)
            categoryId = getIntent().getStringExtra("CategoryId");

        if(!categoryId.isEmpty()  && categoryId != null){
            loadListProducts(categoryId);
        }

        //Search
        materialSearchBar = findViewById(R.id.searchBar);
        loadSuggest();
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest = new ArrayList<>();
                for(String search:suggestList){
                    if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean b) {
                if(!b)
                    recyclerView.setAdapter(adapterProduct);
            }

            @Override
            public void onSearchConfirmed(CharSequence charSequence) {
                startSearch(charSequence);
            }

            @Override
            public void onButtonClicked(int i) {

            }
        });
    }

    private void startSearch(CharSequence charSequence){
        searchAdapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(Product.class, R.layout.product_item, ProductViewHolder.class,
                         productList.orderByChild("Name").equalTo(charSequence.toString())) {
            @Override
            protected void populateViewHolder(ProductViewHolder productViewHolder, Product product, int i) {
                productViewHolder.product_name.setText(product.getName());

                Picasso.with(getBaseContext()).load(product.getImage()).into(productViewHolder.product_image);

                final Product local = product;

                productViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent productDetail = new Intent(ProductList.this, ProductDetail.class);
                        productDetail.putExtra("ProductId",adapterProduct.getRef(position).getKey());
                        startActivity(productDetail);
                    }
                });
            }
        };
        recyclerView.setAdapter(searchAdapter);
    }

    private void loadSuggest() {

        productList.orderByChild("MenuId").equalTo(categoryId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Product item = postSnapshot.getValue(Product.class);
                    suggestList.add(item.getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void loadListProducts(String categoryId) {

            adapterProduct = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(Product.class, R.layout.product_item, ProductViewHolder.class,
                             productList.orderByChild("MenuId").equalTo(categoryId)) {
                @Override
                protected void populateViewHolder(ProductViewHolder productViewHolder, Product product, int i) {
                    productViewHolder.product_name.setText(product.getName());

                    Picasso.with(getBaseContext()).load(product.getImage()).into(productViewHolder.product_image);

                    final Product local = product;

                    productViewHolder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            Intent productDetail = new Intent(ProductList.this, ProductDetail.class);
                            productDetail.putExtra("ProductId",adapterProduct.getRef(position).getKey());
                            startActivity(productDetail);
                         }
                    });
                }
            };
            recyclerView.setAdapter(adapterProduct);
    }
}
