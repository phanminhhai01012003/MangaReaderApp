package com.manga.mangareaderapp.viewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.manga.mangareaderapp.R;
import com.manga.mangareaderapp.adapter.MyViewPagerAdapter;
import com.manga.mangareaderapp.model.Chapter;
import com.manga.mangareaderapp.util.BookFlipPageTransformer;

public class ViewMangaAct extends AppCompatActivity implements View.OnClickListener {

    ViewPager viewPager;
    TextView txtChapterName;
    View btnBack;
    Chapter chapter;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_manga);
        chapter = (Chapter) getIntent().getSerializableExtra("chapter");
        initUI();
    }

    private void initUI() {
        viewPager = findViewById(R.id.viewPager);
        txtChapterName = findViewById(R.id.txtCurrentChapter);
        btnBack = findViewById(R.id.chapter_back);
        txtChapterName.setText(chapter.getName());
        seekBar = findViewById(R.id.SeekbarManga);

        fetchhLinks(chapter);


        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.chapter_back) {
            Toast.makeText(this, "BACK", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void fetchhLinks(Chapter chap) {
        if (chap.getLinks() != null) {
            if (!chap.getLinks().isEmpty()) {
                MyViewPagerAdapter adapter = new MyViewPagerAdapter(getBaseContext(), chap.getLinks());
                viewPager.setAdapter(adapter);
                BookFlipPageTransformer bookFlipPageTransformer = new BookFlipPageTransformer();
                bookFlipPageTransformer.setScaleAmountPercent(10f);
                viewPager.setPageTransformer(true, bookFlipPageTransformer);

            }
        }
    }
}