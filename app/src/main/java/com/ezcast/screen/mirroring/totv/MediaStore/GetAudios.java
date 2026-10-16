package com.ezcast.screen.mirroring.totv.MediaStore;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;
import com.ezcast.screen.mirroring.totv.Models.AudioModel;
import java.util.ArrayList;
import java.util.List;


public class GetAudios {
    
    
    
    public static List<AudioModel> getAllAudios(Context context) {
        ArrayList arrayList = new ArrayList();
        Cursor cursor = null;
        try {
            try {
                cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "_display_name", "_data", "duration"}, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex("duration");
                    int columnIndexOrThrow = cursor.getColumnIndexOrThrow("_id");
                    int columnIndexOrThrow2 = cursor.getColumnIndexOrThrow("_display_name");
                    int columnIndexOrThrow3 = cursor.getColumnIndexOrThrow("_data");
                    do {
                        long j = cursor.getLong(columnIndexOrThrow);
                        String string = cursor.getString(columnIndexOrThrow2);
                        String string2 = cursor.getString(columnIndexOrThrow3);
                        long j2 = cursor.getLong(columnIndex);
                        AudioModel audioModel = new AudioModel();
                        audioModel.setName(string);
                        audioModel.setId(j);
                        audioModel.setPath(string2);
                        audioModel.setDuration(j2);
                        arrayList.add(audioModel);
                    } while (cursor.moveToNext());
                }
            } catch (Exception e) {
                Log.e("AudioUtils", "Error retrieving audio files: " + e.getMessage());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return arrayList;
    }
}
