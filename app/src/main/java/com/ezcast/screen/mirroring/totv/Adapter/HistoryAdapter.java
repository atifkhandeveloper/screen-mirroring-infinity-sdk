package com.ezcast.screen.mirroring.totv.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.ezcast.screen.mirroring.totv.R;
import com.ezcast.screen.mirroring.totv.Interfaces.HistoryClickListener;
import com.ezcast.screen.mirroring.totv.Models.SearchHistoryItem;
import java.util.ArrayList;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    Activity activity;
    Context context;
    ArrayList<SearchHistoryItem> historyList;
    HistoryClickListener listener;

    
    public static  boolean lambda$onBindViewHolder$0(View view) {
        return true;
    }

    public HistoryAdapter(Context context, Activity activity, ArrayList<SearchHistoryItem> arrayList, HistoryClickListener historyClickListener) {
        this.context = context;
        this.activity = activity;
        this.historyList = arrayList;
        this.listener = historyClickListener;
    }

    @Override 
    public HistoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new HistoryViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_item, viewGroup, false));
    }

    @Override 
    public void onBindViewHolder(final HistoryViewHolder historyViewHolder, int i) {
        historyViewHolder.searchUrl.setText(this.historyList.get(i).getURL());
        historyViewHolder.searchTime.setText(this.historyList.get(i).getTime());
        historyViewHolder.searchDate.setText(this.historyList.get(i).getTimestamp());
        historyViewHolder.history_layout.setOnLongClickListener(new View.OnLongClickListener() { 
            @Override 
            public final boolean onLongClick(View view) {
                return HistoryAdapter.lambda$onBindViewHolder$0(view);
            }
        });
        historyViewHolder.searchUrl.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                HistoryAdapter.this.m222xa9af5a9f(historyViewHolder, view);
            }
        });
        historyViewHolder.optionBtn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                HistoryAdapter.this.m223x6c9bc3fe(historyViewHolder, view);
            }
        });
    }

    
    
    public  void m222xa9af5a9f(HistoryViewHolder historyViewHolder, View view) {
        this.listener.onHistoryItemClick(historyViewHolder.getAdapterPosition());
    }

    
    
    public  void m223x6c9bc3fe(final HistoryViewHolder historyViewHolder, View view) {
        PopupMenu popupMenu = new PopupMenu(this.context, historyViewHolder.optionBtn);
        popupMenu.inflate(R.menu.histroy_option_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { 
            @Override 
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() != R.id.delete) {
                    return false;
                }
                HistoryAdapter.this.listener.optionClick(historyViewHolder.getAdapterPosition());
                return true;
            }
        });
        popupMenu.show();
    }

    @Override 
    public int getItemCount() {
        return this.historyList.size();
    }

    
    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        RelativeLayout history_layout;
        View line_view;
        ImageView optionBtn;
        TextView searchDate;
        TextView searchTime;
        TextView searchUrl;

        public HistoryViewHolder(View view) {
            super(view);
            this.line_view = view.findViewById(R.id.line_view);
            this.searchTime = (TextView) view.findViewById(R.id.searching_Time);
            this.searchUrl = (TextView) view.findViewById(R.id.searching_Url);
            this.searchDate = (TextView) view.findViewById(R.id.searching_date);
            this.optionBtn = (ImageView) view.findViewById(R.id.history_option_Btn);
            this.history_layout = (RelativeLayout) view.findViewById(R.id.long_click_history_layout);
        }
    }
}
