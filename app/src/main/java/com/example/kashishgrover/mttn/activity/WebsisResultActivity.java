package com.example.kashishgrover.mttn.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kashishgrover.mttn.R;
import com.example.kashishgrover.mttn.other.InternetCheck;

public class WebsisResultActivity extends Activity {

    private static final String JAVASCRIPT_BODY_FETCH = "javascript:console.log(document.getElementById('ListAttendanceSummary_table').innerText);";
    private static final String ATTENDANCE_URL = "http://websismit.manipal.edu/websis/control/StudentAcademicProfile";

    String username;
    String dateOfBirth;
    String htmlData = "Null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.websis_result);

        username = getIntent().getExtras().getString("user");
        dateOfBirth = getIntent().getExtras().getString("pass");

        WebView view = new WebView(this);
        view.getSettings().setDomStorageEnabled(true);
        view.getSettings().setJavaScriptEnabled(true);
        view.getSettings().setBlockNetworkImage(true);
        view.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        view.getSettings().setSupportMultipleWindows(false);
        view.getSettings().setSupportZoom(false);
        view.getSettings().setSavePassword(false);
        view.setVerticalScrollBarEnabled(false);
        view.setHorizontalScrollBarEnabled(false);
        view.getSettings().setAppCacheEnabled(false);
        view.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        view.loadUrl("http://websismit.manipal.edu/websis/control/clearSession");
        view.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView v, String url)
            {
                v.loadUrl("javascript: {" +
                            "document.getElementById('idValue').value = '" + username +"';" +
                            "document.getElementById('birthDate').value = '" + dateOfBirth + "';" +
                            "document.getElementsByName('loginform')[0].submit(); };");
            }
        });
        view.loadUrl(ATTENDANCE_URL);
        view.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView v, String url)//, Bitmap favicon)
            {
                v.loadUrl(JAVASCRIPT_BODY_FETCH);
            }
        });
        setContentView(view);
    }

    @Override
    public void onDestroy() {
        finish();
        super.onDestroy();
        Toast.makeText(getApplicationContext(),htmlData, Toast.LENGTH_SHORT).show();
    }

    public static boolean check(String u, String p) {
        if (u == null || p == null)
            return false;
        else if (u.trim().equals("") || p.trim().equals(""))
            return false;
        else {
            try {
                String[] A = p.trim().split("-");
                if (u.length() == 9 && A[0].trim().length() == 4
                        && A[1].trim().length() == 2
                        && A[2].trim().length() == 2)
                    return true;
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}