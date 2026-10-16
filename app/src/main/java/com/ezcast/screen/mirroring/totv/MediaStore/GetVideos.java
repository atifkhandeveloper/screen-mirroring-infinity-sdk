package com.ezcast.screen.mirroring.totv.MediaStore;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.ezcast.screen.mirroring.totv.Models.CastingModel;
import java.io.File;
import java.util.ArrayList;


public class GetVideos {
    public static ArrayList<CastingModel> ascendingDialog(Context context) {
        Uri withAppendedPath;
        ArrayList<CastingModel> arrayList = new ArrayList<>();
        Cursor query = context.getContentResolver().query(MediaStore.Files.getContentUri("external"), new String[]{"_data", "_display_name", "_size", "date_added", "date_modified", "mime_type", "media_type", "_id"}, null, null, "date_modified DESC");
        if (query != null && query.getCount() > 0) {
            try {
                query.moveToFirst();
                do {
                    int i = query.getInt(query.getColumnIndexOrThrow("media_type"));
                    if (i == 3) {
                        CastingModel castingModel = new CastingModel();
                        new File(query.getString(query.getColumnIndexOrThrow("_data")));
                        if (i == 3) {
                            castingModel.setMimeType("video");
                            castingModel.setVideo(query.getString(query.getColumnIndexOrThrow("_data")));
                        }
                        int i2 = query.getInt(query.getColumnIndex("_id"));
                        if (castingModel.getMimeType().contains("image")) {
                            withAppendedPath = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, String.valueOf(i2));
                        } else {
                            withAppendedPath = Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, String.valueOf(i2));
                        }
                        castingModel.setVideoUri(withAppendedPath);
                        if (new File(query.getString(query.getColumnIndexOrThrow("_data"))).exists()) {
                            arrayList.add(castingModel);
                        }
                    }
                } while (query.moveToNext());
                query.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }
}
