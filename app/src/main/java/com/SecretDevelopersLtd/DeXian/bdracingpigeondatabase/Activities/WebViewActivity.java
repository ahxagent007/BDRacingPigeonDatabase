package com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.R;

public class WebViewActivity extends AppCompatActivity {

    WebView WV_webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WV_webview = findViewById(R.id.WV_webview);
        String website = getIntent().getStringExtra("WEBSITE");

        WV_webview.getSettings().setJavaScriptEnabled(true);

        String userAgent = "Mozilla/5.0 (Linux; Android 4.4; Nexus 5 Build/_BuildID_) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/30.0.0.0 Mobile Safari/537.36";
        WV_webview.getSettings().setUserAgentString(userAgent);

        WV_webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                }
                return false;
            }
        });

        WV_webview.loadUrl(website);

    }
}
