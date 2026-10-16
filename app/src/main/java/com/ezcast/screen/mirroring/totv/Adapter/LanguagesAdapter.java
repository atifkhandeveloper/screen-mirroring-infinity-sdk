package com.ezcast.screen.mirroring.totv.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.ezcast.screen.mirroring.totv.R;
import com.ezcast.screen.mirroring.totv.Activities.LanguageActivity;
import com.ezcast.screen.mirroring.totv.Models.LanguageModelClass;
import java.util.ArrayList;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;



public final class LanguagesAdapter extends RecyclerView.Adapter<LanguagesAdapter.viewholderclass> {
    public static final Companion Companion = new Companion(null);
    private static ArrayList<LanguageModelClass> arrayList$1 = new ArrayList<>();
    public Activity activity;
    public ArrayList<LanguageModelClass> arrayList;
    public Context context;
    private SharedPreferences languagePreferences;

    @Override 
    public int getItemViewType(int i) {
        return i;
    }

    public LanguagesAdapter(Context context, ArrayList<LanguageModelClass> arrayList, Activity activity) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(arrayList, "arrayList");
        Intrinsics.checkNotNullParameter(activity, "activity");
        setActivity(activity);
        setContext(context);
        setArrayList(arrayList);
        this.languagePreferences = context.getSharedPreferences("music_player", 0);
    }

    public final Context getContext() {
        Context context = this.context;
        if (context != null) {
            return context;
        }
        Intrinsics.throwUninitializedPropertyAccessException("context");
        return null;
    }

    public final void setContext(Context context) {
        Intrinsics.checkNotNullParameter(context, "<set-?>");
        this.context = context;
    }

    public final ArrayList<LanguageModelClass> getArrayList() {
        ArrayList<LanguageModelClass> arrayList = this.arrayList;
        if (arrayList != null) {
            return arrayList;
        }
        Intrinsics.throwUninitializedPropertyAccessException("arrayList");
        return null;
    }

    public final void setArrayList(ArrayList<LanguageModelClass> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "<set-?>");
        this.arrayList = arrayList;
    }

    public final Activity getActivity() {
        Activity activity = this.activity;
        if (activity != null) {
            return activity;
        }
        Intrinsics.throwUninitializedPropertyAccessException("activity");
        return null;
    }

    public final void setActivity(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "<set-?>");
        this.activity = activity;
    }

    @Override 
    public viewholderclass onCreateViewHolder(ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.languages_design, parent, false);
        Intrinsics.checkNotNullExpressionValue(inflate, "from(parent.context).inf…es_design, parent, false)");
        return new viewholderclass(this, inflate);
    }

    @Override 
    public void onBindViewHolder(viewholderclass holder, final int i) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        if (getArrayList().get(i).isIs_primium()) {
            holder.getPrimum().setVisibility(View.VISIBLE);
            holder.bind(i);
        } else {
            holder.bind(i);
        }
        holder.getTick().setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                LanguageActivity.Companion.setPosition(i);
                LanguageActivity.Companion companion = LanguageActivity.Companion;
                String languageCode = getArrayList().get(i).getLanguageCode();
                Intrinsics.checkNotNullExpressionValue(languageCode, "arrayList[position].languageCode");
                companion.setClickLanguage(languageCode);
                Context context = getContext();
                Intrinsics.checkNotNull(context, "null cannot be cast to non-null type com.demo.example.Activities.LanguageActivity");
                ((LanguageActivity) context).notifyAdapter();
            }
        });
        holder.getMainLayout().setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                LanguageActivity.Companion.setPosition(i);
                LanguageActivity.Companion companion = LanguageActivity.Companion;
                String languageCode = getArrayList().get(i).getLanguageCode();
                Intrinsics.checkNotNullExpressionValue(languageCode, "arrayList[position].languageCode");
                companion.setClickLanguage(languageCode);
                Context context = getContext();
                Intrinsics.checkNotNull(context, "null cannot be cast to non-null type com.demo.example.Activities.LanguageActivity");
                ((LanguageActivity) context).notifyAdapter();
            }
        });
    }



    @Override 
    public int getItemCount() {
        return getArrayList().size();
    }

    
    @Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\u00020\u0006X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\b\"\u0004\b\u0019\u0010\nR\u001a\u0010\u001a\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\b\"\u0004\b\u001c\u0010\nR\u001a\u0010\u001d\u001a\u00020\u001eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"¨\u0006'"}, d2 = {"Lcom/example/unimote/Adapter/LanguagesAdapter$viewholderclass;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/example/unimote/Adapter/LanguagesAdapter;Landroid/view/View;)V", "flag", "Landroid/widget/ImageView;", "getFlag", "()Landroid/widget/ImageView;", "setFlag", "(Landroid/widget/ImageView;)V", "languageName", "Landroid/widget/TextView;", "getLanguageName", "()Landroid/widget/TextView;", "setLanguageName", "(Landroid/widget/TextView;)V", "mainLayout", "Landroid/widget/RelativeLayout;", "getMainLayout", "()Landroid/widget/RelativeLayout;", "setMainLayout", "(Landroid/widget/RelativeLayout;)V", "primum", "getPrimum", "setPrimum", "stroke", "getStroke", "setStroke", "tick", "Landroid/widget/RadioButton;", "getTick", "()Landroid/widget/RadioButton;", "setTick", "(Landroid/widget/RadioButton;)V", "bind", "", "pos", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    
    public final class viewholderclass extends RecyclerView.ViewHolder {
        private ImageView flag;
        private TextView languageName;
        private RelativeLayout mainLayout;
        public ImageView primum;
        private ImageView stroke;
        final  LanguagesAdapter this$0;
        private RadioButton tick;

        
        public viewholderclass(LanguagesAdapter languagesAdapter, View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            this.this$0 = languagesAdapter;
            View findViewById = itemView.findViewById(R.id.mainLayout);
            Intrinsics.checkNotNullExpressionValue(findViewById, "itemView.findViewById(R.id.mainLayout)");
            this.mainLayout = (RelativeLayout) findViewById;
            View findViewById2 = itemView.findViewById(R.id.flags);
            Intrinsics.checkNotNullExpressionValue(findViewById2, "itemView.findViewById(R.id.flags)");
            this.flag = (ImageView) findViewById2;
            View findViewById3 = itemView.findViewById(R.id.language_primium_icon);
            Intrinsics.checkNotNullExpressionValue(findViewById3, "itemView.findViewById(R.id.language_primium_icon)");
            setPrimum((ImageView) findViewById3);
            View findViewById4 = itemView.findViewById(R.id.stroke);
            Intrinsics.checkNotNullExpressionValue(findViewById4, "itemView.findViewById(R.id.stroke)");
            this.stroke = (ImageView) findViewById4;
            View findViewById5 = itemView.findViewById(R.id.tick);
            Intrinsics.checkNotNullExpressionValue(findViewById5, "itemView.findViewById(R.id.tick)");
            this.tick = (RadioButton) findViewById5;
            View findViewById6 = itemView.findViewById(R.id.languageName);
            Intrinsics.checkNotNullExpressionValue(findViewById6, "itemView.findViewById(R.id.languageName)");
            this.languageName = (TextView) findViewById6;
        }

        public final RelativeLayout getMainLayout() {
            return this.mainLayout;
        }

        public final void setMainLayout(RelativeLayout relativeLayout) {
            Intrinsics.checkNotNullParameter(relativeLayout, "<set-?>");
            this.mainLayout = relativeLayout;
        }

        public final ImageView getFlag() {
            return this.flag;
        }

        public final void setFlag(ImageView imageView) {
            Intrinsics.checkNotNullParameter(imageView, "<set-?>");
            this.flag = imageView;
        }

        public final ImageView getPrimum() {
            ImageView imageView = this.primum;
            if (imageView != null) {
                return imageView;
            }
            Intrinsics.throwUninitializedPropertyAccessException("primum");
            return null;
        }

        public final void setPrimum(ImageView imageView) {
            Intrinsics.checkNotNullParameter(imageView, "<set-?>");
            this.primum = imageView;
        }

        public final ImageView getStroke() {
            return this.stroke;
        }

        public final void setStroke(ImageView imageView) {
            Intrinsics.checkNotNullParameter(imageView, "<set-?>");
            this.stroke = imageView;
        }

        public final RadioButton getTick() {
            return this.tick;
        }

        public final void setTick(RadioButton radioButton) {
            Intrinsics.checkNotNullParameter(radioButton, "<set-?>");
            this.tick = radioButton;
        }

        public final TextView getLanguageName() {
            return this.languageName;
        }

        public final void setLanguageName(TextView textView) {
            Intrinsics.checkNotNullParameter(textView, "<set-?>");
            this.languageName = textView;
        }

        public final void bind(int i) {
            this.flag.setBackgroundResource(this.this$0.getArrayList().get(i).getFlags());
            TextView textView = this.languageName;
            String languageName = this.this$0.getArrayList().get(i).getLanguageName();
            if (languageName == null) {
                languageName = "";
            }
            textView.setText(languageName);
            if (i == LanguageActivity.Companion.getPosition()) {
                this.languageName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                this.tick.setChecked(true);
                this.stroke.setVisibility(View.VISIBLE);
                return;
            }
            this.languageName.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            this.tick.setChecked(false);
            this.stroke.setVisibility(View.GONE);
        }
    }

    public final void setLanguage(String str) {
        Locale locale = new Locale(str);
        Locale.setDefault(locale);
        Resources resources = getContext().getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\"\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0086\u000e¢\u0006\u0010\n\u0002\b\n\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\u000b"}, d2 = {"Lcom/example/unimote/Adapter/LanguagesAdapter$Companion;", "", "()V", "arrayList", "Ljava/util/ArrayList;", "Lcom/example/unimote/Models/LanguageModelClass;", "getArrayList", "()Ljava/util/ArrayList;", "setArrayList", "(Ljava/util/ArrayList;)V", "arrayList$1", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    
    public static final class Companion {
        public  Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ArrayList<LanguageModelClass> getArrayList() {
            return LanguagesAdapter.arrayList$1;
        }

        public final void setArrayList(ArrayList<LanguageModelClass> arrayList) {
            Intrinsics.checkNotNullParameter(arrayList, "<set-?>");
            LanguagesAdapter.arrayList$1 = arrayList;
        }
    }
}
