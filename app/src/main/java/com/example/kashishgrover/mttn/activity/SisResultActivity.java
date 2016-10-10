package com.example.kashishgrover.mttn.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.kashishgrover.mttn.R;

/**
 * Created by Kashish Grover on 10/10/2016.
 */
public class SisResultActivity extends Activity {

    String username;
    String dateOfBirth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.websis_result);

        username = getIntent().getExtras().getString("user");
        dateOfBirth = getIntent().getExtras().getString("pass");

        MyWebView view = new MyWebView(this);
        view.getSettings().setJavaScriptEnabled(true);
        view.getSettings().setDomStorageEnabled(true);
        view.loadUrl("https://sis.manipal.edu/studlogin.aspx");
        view.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView v, String url) {
                v.loadUrl("javascript: {" +
                        "document.getElementById('txtroll').value = '" + username +"';" +
                        "document.getElementById('txtdob').value = '" + dateOfBirth + "'};");
            }
        });
        //view.loadUrl("https://sis.manipal.edu/geninfo.aspx");
        //view.loadUrl("https://sis.manipal.edu/tempattd.aspx");
        setContentView(view);
    }

    class MyWebView extends WebView {
        Context context;
        public MyWebView(Context context) {
            super(context);
            this.context = context;
        }
    }

    public static boolean check(String u, String p) {
        if (u == null || p == null)
            return false;
        else if (u.trim().equals("") || p.trim().equals(""))
            return false;
        else {
            try {
                String[] A = p.trim().split("/");
                if (u.length() == 9 && A[0].trim().length() == 2
                        && A[1].trim().length() == 2
                        && A[2].trim().length() == 4)
                    return true;
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}