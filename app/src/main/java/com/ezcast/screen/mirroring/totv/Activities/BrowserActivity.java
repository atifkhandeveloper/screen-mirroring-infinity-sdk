package com.ezcast.screen.mirroring.totv.Activities;

import static com.ezcast.screen.mirroring.totv.ExtentionKt.edgeToEdge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;

import com.ezcast.screen.mirroring.totv.R;
import com.ezcast.screen.mirroring.totv.Models.SearchHistoryItem;
import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class BrowserActivity extends ThemeActivity {
    public static ImageView webViewBack;
    private Activity activity;
    
    private TextView browserBingBtn;
    private String browserCheck;
    private TextView browserFoxBtn;
    private TextView browserGoogleBtn;
    private LinearLayout browserHomeLayout;
    private TextView browserIMDbBtn;
    private TextView browserSoundcloudBtn;
    private TextView browserTwitchBtn;
    private TextView browserYahooBtn;
    private TextView browserYoutubeBtn;
    private Context content;
    private ImageView crossBtn;
    private ImageView mirrorCast;
    private ProgressBar progressBar;
    private EditText searchEditText;
    private String userText;
    private WebView webView;
    private ImageView webViewForward;
    private ImageView webViewHistory;
    private ImageView webViewHome;
    private ImageView webViewReload;
    boolean webView_back_forward = false;
    private ArrayList<SearchHistoryItem> historyList = new ArrayList<>();

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_browser);
        edgeToEdge(this, findViewById(R.id.mainLayout));




        initializeFields();
        clickListener();

        String stringExtra = getIntent().getStringExtra("browserCheck");
        this.browserCheck = stringExtra;
        if (stringExtra != null) {
            if (getIntent().getBooleanExtra("isFromHistory", false)) {
                linkReloadOnWebView();
                this.webView.loadUrl(this.browserCheck);
            }
            if (this.browserCheck.equals("google")) {
                linkReloadOnWebView();
                this.webView.loadUrl("https://google.com/");
            } else if (this.browserCheck.equals("youtube")) {
                linkReloadOnWebView();
                this.webView.loadUrl("https://www.youtube.com/");
            } else if (this.browserCheck.equals("facebook")) {
                linkReloadOnWebView();
                this.webView.loadUrl("https://www.facebook.com/");
            } else if (this.browserCheck.equals("twitch")) {
                linkReloadOnWebView();
                this.webView.loadUrl("https://www.twitch.com/");
            } else if (this.browserCheck.equals("dailymotion")) {
                linkReloadOnWebView();
                this.webView.loadUrl("https://www.dailymotion.com/");
            } else if (this.browserCheck.equals("vimeo")) {
                linkReloadOnWebView();
                this.webView.loadUrl("https://www.vimeo.com/");
            } else if (this.browserCheck.equals("amazon")) {
                linkReloadOnWebView();
                this.webView.loadUrl("https://www.amazon.com/");
            } else if (this.browserCheck.equals("netflix")) {
                linkReloadOnWebView();
                this.webView.loadUrl("https://www.netflix.com/");
            }
        }
    }

    private void clickListener() {
        this.searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() { 
            @Override 
            public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return BrowserActivity.this.m175x9db243c9(textView, i, keyEvent);
            }
        });
        this.crossBtn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                BrowserActivity.this.m176xd77ce5a8(view);
            }
        });
        webViewBack.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                BrowserActivity.this.m183x11478787(view);
            }
        });
        this.webViewForward.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                BrowserActivity.this.m184x4b122966(view);
            }
        });
        this.webViewReload.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                BrowserActivity.this.m185x84dccb45(view);
            }
        });
        this.webViewHome.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                BrowserActivity.this.m186xbea76d24(view);
            }
        });
        this.webViewHistory.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                BrowserActivity.this.m187xf8720f03(view);
            }
        });
        this.mirrorCast.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                BrowserActivity.this.m188x323cb0e2(view);
            }
        });
        this.browserGoogleBtn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                BrowserActivity.this.m189x6c0752c1(view);
            }
        });
        this.browserYoutubeBtn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                BrowserActivity.this.m190xa5d1f4a0(view);
            }
        });
        this.browserYahooBtn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                BrowserActivity.this.m177x48243b10(view);
            }
        });
        this.browserSoundcloudBtn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                BrowserActivity.this.m178x81eedcef(view);
            }
        });
        this.browserBingBtn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                BrowserActivity.this.m179xbbb97ece(view);
            }
        });
        this.browserTwitchBtn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                BrowserActivity.this.m180xf58420ad(view);
            }
        });
        this.browserIMDbBtn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                BrowserActivity.this.m181x2f4ec28c(view);
            }
        });
        this.browserFoxBtn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                BrowserActivity.this.m182x6919646b(view);
            }
        });
    }

    
    
    public  boolean m175x9db243c9(TextView textView, int i, KeyEvent keyEvent) {
        String str;
        if (i == 4) {
            String obj = this.searchEditText.getText().toString();
            this.userText = obj;
            if (URLUtil.isValidUrl(obj)) {
                str = this.userText;
            } else {
                str = "https://www.google.com/search?q=" + this.userText.replace(" ", "%20");
            }
            this.browserHomeLayout.setVisibility(View.GONE);
            this.webView.setVisibility(View.VISIBLE);
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.searchEditText.getWindowToken(), 0);
            this.webView.loadUrl(str);
            this.searchEditText.setText(str);
            this.webViewReload.setClickable(true);
            return true;
        }
        return false;
    }

    
    
    public  void m176xd77ce5a8(View view) {
        super.onBackPressed();
    }

    
    
    public  void m183x11478787(View view) {
        if (this.webView.canGoBack()) {
            this.webView.goBack();
        }
    }

    
    
    public  void m184x4b122966(View view) {
        if (this.webView.canGoForward()) {
            this.webView.goForward();
        }
    }

    
    
    public  void m185x84dccb45(View view) {
        this.webView.reload();
    }

    
    
    public  void m186xbea76d24(View view) {
        this.searchEditText.setText((CharSequence) null);
        this.browserHomeLayout.setVisibility(View.VISIBLE);
        this.webView.setVisibility(View.GONE);
        this.webViewReload.setClickable(false);
        this.progressBar.setVisibility(View.GONE);
    }

    
    
    public  void m187xf8720f03(View view) {
        startActivity(new Intent(this.content, BrowserHistoryActivity.class));
    }

    
    
    public  void m188x323cb0e2(View view) {
        startActivity(new Intent("android.settings.CAST_SETTINGS"));
    }

    
    
    public  void m189x6c0752c1(View view) {
        linkReloadOnWebView();
        this.webView.loadUrl("https://www.google.com/");
    }

    
    
    public  void m190xa5d1f4a0(View view) {
        linkReloadOnWebView();
        this.webView.loadUrl("https://www.youtube.com/");
    }

    
    
    public  void m177x48243b10(View view) {
        linkReloadOnWebView();
        this.webView.loadUrl("https://www.yahoo.com/");
    }

    
    
    public  void m178x81eedcef(View view) {
        linkReloadOnWebView();
        this.webView.loadUrl("https://soundcloud.com/");
    }

    
    
    public  void m179xbbb97ece(View view) {
        linkReloadOnWebView();
        this.webView.loadUrl("https://www.bing.com/");
    }

    
    
    public  void m180xf58420ad(View view) {
        linkReloadOnWebView();
        this.webView.loadUrl("https://www.twitch.tv/");
    }

    
    
    public  void m181x2f4ec28c(View view) {
        linkReloadOnWebView();
        this.webView.loadUrl("https://www.imdb.com/");
    }

    
    
    public  void m182x6919646b(View view) {
        linkReloadOnWebView();
        this.webView.loadUrl("https://www.fox.com/");
    }

    private void initializeFields() {
        this.content = this;
        this.activity = this;
        this.webView = (WebView) findViewById(R.id.webView);
        webViewBack = (ImageView) findViewById(R.id.webViewBack);
        this.webViewForward = (ImageView) findViewById(R.id.webViewForward);
        this.webViewHome = (ImageView) findViewById(R.id.webViewHome);
        this.webViewHistory = (ImageView) findViewById(R.id.webViewHistory);
        this.webViewReload = (ImageView) findViewById(R.id.webViewReload);
        this.searchEditText = (EditText) findViewById(R.id.searchEditText);
        this.browserHomeLayout = (LinearLayout) findViewById(R.id.browserHomeLayout);
        this.crossBtn = (ImageView) findViewById(R.id.crossBtn);
        this.mirrorCast = (ImageView) findViewById(R.id.mirrorCast);
        this.browserGoogleBtn = (TextView) findViewById(R.id.browserGoogleBtn);
        this.browserYoutubeBtn = (TextView) findViewById(R.id.browserYoutubeBtn);
        this.browserYahooBtn = (TextView) findViewById(R.id.browserYahooBtn);
        this.browserSoundcloudBtn = (TextView) findViewById(R.id.browserSoundcloudBtn);
        this.browserBingBtn = (TextView) findViewById(R.id.browserBingBtn);
        this.browserTwitchBtn = (TextView) findViewById(R.id.browserTwitchBtn);
        this.browserIMDbBtn = (TextView) findViewById(R.id.browserIMDbBtn);
        this.browserFoxBtn = (TextView) findViewById(R.id.browserFoxBtn);
        this.progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.setWebViewClient(new MyWebViewClient(this.content, this.activity, this.searchEditText));
        this.browserHomeLayout.setVisibility(View.VISIBLE);
        this.webView.setVisibility(View.GONE);
        this.progressBar.setVisibility(View.VISIBLE);
        ArrayList<SearchHistoryItem> readHistoryFromPref = BrowserHistoryActivity.readHistoryFromPref(this.content);
        this.historyList = readHistoryFromPref;
        if (readHistoryFromPref == null) {
            this.historyList = new ArrayList<>();
        }
        this.webView.setWebChromeClient(new WebChromeClient() { 
            @Override 
            public void onProgressChanged(WebView webView, int i) {
                if (i == 100) {
                    BrowserActivity.this.progressBar.setVisibility(View.GONE);
                    return;
                }
                BrowserActivity.this.progressBar.setVisibility(View.VISIBLE);
                BrowserActivity.this.progressBar.setProgress(i);
            }
        });
    }

    
    public class MyWebViewClient extends WebViewClient {
        private final Activity mactivity;
        private final Context mcontent;
        private final EditText searchEditText;
        private ArrayList<SearchHistoryItem> historyList = new ArrayList<>();
        private int count = 0;

        public MyWebViewClient(Context context, Activity activity, EditText editText) {
            this.mcontent = context;
            this.mactivity = activity;
            this.searchEditText = editText;
        }

        @Override 
        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            int i = this.count + 1;
            this.count = i;
            if (i == 3) {
                this.count = 0;
            }
            this.searchEditText.setText(str);
            String format = new SimpleDateFormat("hh : mm a", Locale.getDefault()).format(Calendar.getInstance().getTime());
            String format2 = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
            ArrayList<SearchHistoryItem> readHistoryFromPref = BrowserHistoryActivity.readHistoryFromPref(this.mcontent);
            this.historyList = readHistoryFromPref;
            if (readHistoryFromPref == null) {
                this.historyList = new ArrayList<>();
            }
            this.historyList.add(0, new SearchHistoryItem(str, format, format2));
            writeHistoryInPref(this.mcontent, this.historyList);
            super.onPageStarted(webView, str, bitmap);
        }

        @Override 
        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
        }

        @Override 
        public void onLoadResource(WebView webView, String str) {
            super.onLoadResource(webView, str);
        }

        @Override 
        public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
            return super.shouldInterceptRequest(webView, webResourceRequest);
        }

        @Override 
        public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
            return super.shouldOverrideUrlLoading(webView, webResourceRequest);
        }

        private void writeHistoryInPref(Context context, ArrayList<SearchHistoryItem> arrayList) {
            SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
            edit.putString(BrowserHistoryActivity.MY_HISTORY_PREF, new Gson().toJson(arrayList));
            edit.apply();
        }
    }

    @Override 
    public void onBackPressed() {
        if (this.webView.canGoBack()) {
            this.webView.goBack();
            return;
        }
        super.onBackPressed();
    }

    private void linkReloadOnWebView() {
        this.webView.setVisibility(View.VISIBLE);
        this.browserHomeLayout.setVisibility(View.GONE);
    }
}
