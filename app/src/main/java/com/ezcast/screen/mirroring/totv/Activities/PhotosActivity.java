package com.ezcast.screen.mirroring.totv.Activities;

import static com.ezcast.screen.mirroring.totv.ExtentionKt.edgeToEdge;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ezcast.screen.mirroring.totv.R;
import com.ezcast.screen.mirroring.totv.Adapter.PhotosAdapter;
import com.ezcast.screen.mirroring.totv.MediaStore.GetPhotos;
import com.ezcast.screen.mirroring.totv.Models.CastingModel;
import java.util.ArrayList;


public class PhotosActivity extends ThemeActivity {
    public static boolean forPermission;
    public static ArrayList<CastingModel> photosList;
    final int MY_READ_PERMISSION_CODE = 101;
    PhotosAdapter adapter;
    
    ImageView back_icon;
    RecyclerView recyclerView;

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_photos_second);
        edgeToEdge(this, findViewById(R.id.mainLayout));





        this.recyclerView = (RecyclerView) findViewById(R.id.photos_recyclerView);
        this.back_icon = (ImageView) findViewById(R.id.photos_back_icon);

        this.recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        photosList = GetPhotos.getPhotos(this);
        PhotosAdapter photosAdapter = new PhotosAdapter(photosList, this);
        this.adapter = photosAdapter;
        this.recyclerView.setAdapter(photosAdapter);
        this.back_icon.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                PhotosActivity.this.m210lambda$onCreate$0$comexampleunimoteActivitiesPhotosActivity(view);
            }
        });
    }

    
    
    public  void m210lambda$onCreate$0$comexampleunimoteActivitiesPhotosActivity(View view) {
        onBackPressed();
    }
}
