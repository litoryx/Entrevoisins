package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private TextView mName;
    private TextView mPhone;
    private TextView mAdr;
    private TextView mAp;
    private ImageView mImg;
    private ImageButton mButtonBack;
    private FloatingActionButton mFavo;
    private Neighbour mUser;
    Toolbar mToolbar;
    boolean mModifButtonFav;
    int i;
    private NeighbourApiService mApiService;
    List<Neighbour> mRecupFav;
    int mCountFav;
    private static final String SHARED_PREF_USER_INFO = "SHARED_PREF_USER_INFO";
    private static final List<Neighbour> SHARED_PREF_USER_INFO_LISTFAV = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mName = findViewById(R.id.name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mPhone = findViewById(R.id.phone);
        mAdr = findViewById(R.id.adresse);
        mAp = findViewById(R.id.about);
        mImg = findViewById(R.id.img);
        mFavo = findViewById(R.id.favoris);
        mApiService = DI.getNeighbourApiService();
        mRecupFav = mApiService.getFav();
        mCountFav = mRecupFav.size();
        mModifButtonFav = true;

        Intent intent = getIntent();
        mUser = intent.getParcelableExtra("mName");
        mName.setText(mUser.getName());
        mPhone.setText(mUser.getPhoneNumber());
        mAdr.setText(mUser.getAddress());
        mAp.setText(mUser.getAboutMe());
        Glide.with(mImg.getContext()).load(mUser.getAvatarUrl()).centerCrop().into(mImg);

        if (mCountFav != 0) {
            for (i = 0; i < mCountFav; i++) {
                if (mRecupFav.get(i).equals(mUser)) {
                    mModifButtonFav = false;
                    mFavo.setImageResource(R.drawable.ic_star_white_24dp);
                }
            }
        }

        mFavo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mModifButtonFav == false) {
                    mApiService.SupFav(mUser);
                    mFavo.setImageResource(R.drawable.ic_star_border_white_24dp);
                } else {
                    mApiService.addFav(mUser);
                    mFavo.setImageResource(R.drawable.ic_star_white_24dp);
                    mModifButtonFav = false;
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}