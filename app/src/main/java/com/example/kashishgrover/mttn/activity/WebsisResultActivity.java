package com.example.kashishgrover.mttn.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewManager;
import android.webkit.JavascriptInterface;
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

    private static final String main_PAGE_check = "AMS Student Information Portal";
    //private static final String JAVASCRIPT_BODY_FETCH = "javascript:window.MITINTERFACE.processContent(document.getElementById('ListAttendanceSummary_table').innerText);";
    private static final String JAVASCRIPT_BODY_FETCH = "javascript:console.log(document.getElementById('ListAttendanceSummary_table').innerText);";
    private static final String ATTENDANCE_URL = "http://websismit.manipal.edu/websis/control/StudentAcademicProfile";
    private static final String CHECK_LOGIN = "Student Profile";

    TextView tvResult;
    TableLayout tb;
    TableRow row;
    RelativeLayout rl;
    String html;
    private int count;
    private Thread threadInc;
    private int presentProgress;
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
        view.loadUrl("http://websismit.manipal.edu/websis/control/clearSession");
        view.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView v, String url) {
                v.loadUrl("javascript: {" +
                            "document.getElementById('idValue').value = '" + username +"';" +
                            "document.getElementById('birthDate').value = '" + dateOfBirth + "';" +
                            "document.getElementsByName('loginform')[0].submit(); };");
            }
        });
        view.loadUrl("http://websismit.manipal.edu/websis/control/StudentAcademicProfile");
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