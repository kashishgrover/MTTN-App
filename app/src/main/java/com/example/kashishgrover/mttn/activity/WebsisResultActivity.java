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

    private static final String JAVASCRIPT_BODY_FETCH = "javascript:window.HTMLOUT.processHTML(document.getElementById('ListAttendanceSummary_table').innerText);";
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

        //Some settings changed so that webview loads up quickly

        view.getSettings().setDomStorageEnabled(true);
        view.getSettings().setJavaScriptEnabled(true);
        view.getSettings().setBlockNetworkImage(true);

        //Add a JS interface on the view which will help extract the text required
        view.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");

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
            public void onPageFinished(WebView v, String url)
            {
                v.loadUrl(JAVASCRIPT_BODY_FETCH);
            }
        });
        setContentView(view);
    }

    /* An instance of this class will be registered as a JavaScript interface */
    class MyJavaScriptInterface
    {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html)
        {
            htmlData = html;
        }
    }

    @Override
    public void onDestroy() {
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