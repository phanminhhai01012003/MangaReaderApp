package com.manga.mangareaderapp.viewmodel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.manga.mangareaderapp.R;
import com.manga.mangareaderapp.adapter.CategoryAdapter;
import com.manga.mangareaderapp.model.Tag;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryAct extends AppCompatActivity implements CategoryAdapter.OnItemCateClick {

    /**
     * View
     */
    RecyclerView rcvCate;

    /**
     * Adapter
     */
    CategoryAdapter adapter;
    List<Tag> Cates;

    /**
     * Database
     */
    DatabaseReference reference;

    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        initUI();
    }

    private void initUI() {
        rcvCate = findViewById(R.id.rcv_category);
        Cates = new ArrayList<>();
        ivBack = findViewById(R.id.imgBack);
        ivBack.setOnClickListener(v -> finish());
        UpdateItem();
    }

    private void UpdateItem() {
        reference = FirebaseDatabase.getInstance().getReference("Category");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!Cates.isEmpty())
                    Cates.clear();
                for (DataSnapshot item : snapshot.getChildren()) {
                    Tag tag = item.getValue(Tag.class);
                    Cates.add(tag);
                }
                setCateAdapter(Cates);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setCateAdapter(List<Tag> cates) {
        adapter = new CategoryAdapter(this, Cates, this);
        adapter.notifyDataSetChanged();
        rcvCate.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rcvCate.setAdapter(adapter);
    }

    @Override
    public void onCateItemClick(int clickedItemIndex) {
        //Toast.makeText(this, Cates.get(clickedItemIndex).getTag(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(CategoryAct.this, categoryMangaDetailsAct.class);
        intent.putExtra("category", Cates.get(clickedItemIndex));
        startActivity(intent);
    }
}