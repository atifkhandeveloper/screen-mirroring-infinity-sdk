package com.ezcast.screen.mirroring.totv.Activities;

import static com.ezcast.screen.mirroring.totv.ExtentionKt.edgeToEdge;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ezcast.screen.mirroring.totv.R;
import com.ezcast.screen.mirroring.totv.Adapter.HistoryAdapter;
import com.ezcast.screen.mirroring.totv.Interfaces.HistoryClickListener;
import com.ezcast.screen.mirroring.totv.Models.SearchHistoryItem;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import java.util.ArrayList;


public class BrowserHistoryActivity extends ThemeActivity implements HistoryClickListener {
    public static final String MY_HISTORY_PREF = "MY_HISTORY_PREF";
    
    private ImageView back_icon;
    private Context content;
    private HistoryAdapter historyAdapter;
    private ArrayList<SearchHistoryItem> historyList = new ArrayList<>();
    private RecyclerView historyRecycler;
    ImageView historyViewHome;

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_browser_history);
        edgeToEdge(this, findViewById(R.id.mainLayout));





        initializeFields();

    }

    private void initializeFields() {
        this.content = this;
        this.historyRecycler = (RecyclerView) findViewById(R.id.browserHistoryRecycler);
        this.back_icon = (ImageView) findViewById(R.id.history_back_icon);
        ArrayList<SearchHistoryItem> readHistoryFromPref = readHistoryFromPref(this.content);
        this.historyList = readHistoryFromPref;
        if (readHistoryFromPref == null) {
            this.historyList = new ArrayList<>();
        }
        this.historyAdapter = new HistoryAdapter(this.content, this, this.historyList, this);
        this.historyRecycler.setLayoutManager(new LinearLayoutManager(this.content));
        this.historyRecycler.setAdapter(this.historyAdapter);
        this.back_icon.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                BrowserHistoryActivity.this.m191x1fbaba30(view);
            }
        });
    }

    
    
    public  void m191x1fbaba30(View view) {
        onBackPressed();
    }

    
    public static ArrayList<SearchHistoryItem> readHistoryFromPref(Context context) {
        String string = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).getString(MY_HISTORY_PREF, "");
        if (string == null || string.isEmpty()) {
            return new ArrayList<>();
        }
        return (ArrayList) new Gson().fromJson(string, new TypeToken<ArrayList<SearchHistoryItem>>() { 
        }.getType());
    }

    @Override 
    public void onHistoryItemClick(int i) {
        String url = this.historyList.get(i).getURL();
        Intent intent = new Intent(this, BrowserActivity.class);
        intent.putExtra("isFromHistory", true);
        intent.putExtra("browserCheck", url);
        startActivity(intent);
    }

    @Override 
    public void optionClick(final int i) {
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this, R.style.adsBackgroundNativeCommon);
        materialAlertDialogBuilder.setTitle((CharSequence) getResources().getString(R.string.delete_history));
        materialAlertDialogBuilder.setMessage((CharSequence) getResources().getString(R.string.conform_delete));
        materialAlertDialogBuilder.setPositiveButton(17039379, new DialogInterface.OnClickListener() { 
            @Override 
            public void onClick(DialogInterface dialogInterface, int i2) {
                BrowserHistoryActivity.this.historyList.remove(i);
                BrowserHistoryActivity.this.historyAdapter.notifyItemRemoved(i);
                SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(BrowserHistoryActivity.this).edit();
                edit.putString(BrowserHistoryActivity.MY_HISTORY_PREF, new Gson().toJson(BrowserHistoryActivity.this.historyList));
                edit.apply();
            }
        });
        materialAlertDialogBuilder.setNegativeButton(17039369, new DialogInterface.OnClickListener() { 
            @Override 
            public void onClick(DialogInterface dialogInterface, int i2) {
                dialogInterface.cancel();
            }
        });
        materialAlertDialogBuilder.show();
    }
}
