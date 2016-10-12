package com.example.kashishgrover.mttn.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
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

public class WebsisResultActivity extends ProgressActivity {

    private static final String JAVASCRIPT_BODY_FETCH = "javascript:window.HTMLOUT.processContent(document.getElementById('ListAttendanceSummary_table').innerText);";
    private static final String ATTENDANCE_URL = "http://websismit.manipal.edu/websis/control/StudentAcademicProfile";

    WebView web;
    TextView tvResult;
    //TableLayout tb;
    TableRow row;
    RelativeLayout rl;
    String html;
    private int count;
    private Thread threadInc;
    private int presentProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.websis_result);

        getProgressBar().setVisibility(View.VISIBLE);
        try {
            count = 0;
            rl = (RelativeLayout) findViewById(R.id.REL);
            tvResult = (TextView) findViewById(R.id.tvResult);
            //tb = (TableLayout) findViewById(R.id.tbLayout);
            //tb.setVisibility(View.INVISIBLE);
            getProgressBar().setMax(100);
            setProgressss(5);

            String userA, passA;
            userA = getIntent().getExtras().getString("user");
            passA = getIntent().getExtras().getString("pass");
            submit(userA, passA);

        } catch (Exception e) {
            e.printStackTrace();
        }

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Attendance Report");
        actionBar.setDisplayHomeAsUpEnabled(true);
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

    @SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
    public void submit(String userA, String passA) {
        final String userNam = userA;
        final String passWor = passA;

        web = (WebView) findViewById(R.id.webView1);
        //Some settings changed so that webview loads up quickly
        web.getSettings().setDomStorageEnabled(true);
        web.getSettings().setJavaScriptEnabled(true);
        //Add a JS interface on the view which will help extract the text required
        web.addJavascriptInterface(new MyJavaScriptInterface(tvResult), "HTMLOUT");

        web.loadUrl(ATTENDANCE_URL);
        web.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView v, String url)
            {
                if (url.equals(ATTENDANCE_URL)) {
                    Log.i("TAG", "1");
                    v.loadUrl(JAVASCRIPT_BODY_FETCH);
                }
                Log.i("HELLO FROM","1111111111");
                tvResult.setText("Loading...");
                v.loadUrl("javascript: {" +
                    "document.getElementById('idValue').value = '" + userNam +"';" +
                    "document.getElementById('birthDate').value = '" + passWor + "';" +
                    "document.getElementsByName('loginform')[0].submit(); };");
                v.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView w, String url) {
                        Log.i("HELLO FROM","22222222222");
                        w.loadUrl(ATTENDANCE_URL);
                        w.setWebViewClient(new WebViewClient() {
                            @Override
                            public void onPageFinished(WebView x, String url) {
                                Log.i("HELLO FROM","3333333333");
                                x.loadUrl(JAVASCRIPT_BODY_FETCH);
                                tvResult.setText("Login complete!");
                            }
                        });
                    }
                });
            }
        });
    }

    class MyJavaScriptInterface {
        private TextView contentView;

        public MyJavaScriptInterface(TextView aContentView) {
            contentView = aContentView;
        }

        @JavascriptInterface
        public void processContent(final String aContent) {
            final String content = aContent;
            contentView.post(new Runnable() {
                public void run() {
                    Log.i("TAG:HTMLOUT", content);
                    contentView.setText(content);
                    web.stopLoading();
                    ((ViewManager) web.getParent()).removeView(web);
                    setProgressss(98);
                    web.destroy();
                    //tb.setVisibility(View.VISIBLE);
                    //parseTheShit();
                    //Toast.makeText(getApplicationContext(),content,Toast.LENGTH_LONG).show();
                    tvResult.setText(content);
                    getProgressBar().setVisibility(View.INVISIBLE);
                }

            });
        }
    }
//
//    String check1, check2, check3;
//
//    private TableRow getTableRow(String A[]) {
//
//        TableRow r = new TableRow(getApplicationContext());
//        r.setBackgroundColor(Color.parseColor("#fff9d8"));
//
//        int p = 2;
//        String second = "";
//
//        float wieght[] = { 1f, 1f, 0.5f, 0.5f, 0.5f, 0.5f, 1f };
//
//        for (int i = 0; i < 7; i++) {
//
//            String Hcode = "-", Hname = "-", Htotal = "-", Hpr = "-", Hab = "-", Hp = "-", Hupdated = "-";
//
//            TextView t = new TextView(getApplicationContext());
//            t.setGravity(Gravity.CENTER);
//            t.setLayoutParams(new TableRow.LayoutParams(
//                    TableRow.LayoutParams.WRAP_CONTENT,
//                    TableRow.LayoutParams.WRAP_CONTENT, wieght[i]));
//            t.setTextColor(Color.parseColor("#567880"));
//
//            try {
//                switch (i) {
//                    case 0:
//                        t.setGravity(Gravity.LEFT);
//                        Hcode = A[0] + A[1];
//                        t.setText(Hcode);
//                        break;
//                    case 1:
//                        t.setGravity(Gravity.LEFT);
//                        for (p = 2; p < A.length; p++)
//                            if (checkNumber(A[p]) == 0)
//                                break;
//                            else
//                                second = second + " " + A[p].trim();
//                        Hname = shorten(second);
//                        t.setText(Hname);
//                        break;
//                    case 2:
//                        try {
//                            Htotal = A[p];
//                            t.setText(Htotal);
//
//                        } catch (Exception e) {
//                            t.setText("-");
//                        }
//                        break;
//                    case 3:
//
//                        try {
//                            Hpr = A[p + 1];
//                            t.setText(Hpr);
//                        } catch (Exception e) {
//                            t.setText("-");
//                        }
//                        break;
//                    case 4:
//
//                        try {
//                            Hab = A[p + 2];
//                            t.setText(Hab);
//
//                        } catch (Exception e) {
//
//                            t.setText("-");
//                        }
//                        break;
//                    case 5:
//
//                        try {
//                            Hp = A[p + 3];
//                            t.setText(Hp);
//                            if (!Hp.equals("-")) {
//
//                                try {
//                                    int pA = Integer.parseInt(Hp.trim());
//                                    if (pA <= 75) {
//                                        t.setBackgroundResource(R.drawable.textviewshapered);
//                                    }
//
//                                } catch (NumberFormatException nfe) {
//                                    Toast.makeText(getApplicationContext(),nfe.getMessage(),Toast.LENGTH_LONG).show();
//                                }
//
//                            }
//                        } catch (Exception e) {
//                            t.setText("-");
//                        }
//                        break;
//                    case 6:
//                        try {
//                            Hupdated = A[p + 4];
//                            t.setText(Hupdated);
//                        } catch (Exception e) {
//                            t.setText("-");
//                        }
//                        break;
//
//                }
//            } catch (Exception e) {
//                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
//            }
//
//            r.addView(t);
//        }
//        return r;
//    }
//
//    private void goDoEveryShitThere(String line, int i) {
//
//        line = line.replaceAll("  ", " ").replaceAll("  ", " ").trim();
//        String A[] = line.split(" ");
//
//        tb.addView(getTableRow(A));
//
//    }
//
//    private void checkForParseError(String a) {
//
//        boolean error = false;
//        String aa = "", bb = "";
//        try {
//
//            if (tb != null) {
//                TableRow tb1 = (TableRow) tb.getChildAt(1);
//                TableRow tb2 = (TableRow) tb.getChildAt(2);
//                if (tb1 != null && tb2 != null) {
//
//                    for (int i = 1; i < tb.getChildCount(); i++) {
//                        aa += ((TextView) tb2
//                                .getChildAt(tb2.getChildCount() - 1)).getText()
//                                .toString();
//                        bb += "-";
//                    }
//                    if (aa.contains(bb))
//                        error = true;
//                    else
//                        error = false;
//
//                }
//            }
//
//        } catch (Exception e) {
//            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
//        }
//
//        if (error) {
//            Toast.makeText(getApplicationContext(), aa + "Parse error" + bb,
//                    Toast.LENGTH_LONG).show();
//            ((ViewManager) tb.getParent()).removeView(tb);
//            TextView t = new TextView(getApplicationContext());
//            t.setText(Html.fromHtml(a));
//            t.setTextColor(Color.BLACK);
//            rl.addView(t);
//        }
//    }
//
//    private void parseTheShit() {
//
//        Log.i("TAG", "inside ParseTheShit");
//
//        String a = tvResult.getText().toString();;
//        web.loadDataWithBaseURL(null, a, "text/html", "UTF-8", null);
//
//        //tvResult.setText(Html.fromHtml(a));
//        ((ViewManager) tvResult.getParent()).removeView(tvResult);
//
//        if (a != null) {
//
//            String A[] = a.split("Updated");
//            String B = A[1].trim();
//
//            String AB[] = B.trim().split("\n");
//
//            for (int i = 1; i < AB.length; i++) {
//                goDoEveryShitThere(AB[i], i);
//            }
//            getProgressBar().setVisibility(View.INVISIBLE);
//            checkForParseError(a);
//        } else {
//            Toast.makeText(this, "Attendance Sheet Null", Toast.LENGTH_SHORT)
//                    .show();
//            getProgressBar().setVisibility(View.INVISIBLE);
//            finish();
//        }
//
//    }
//
//    private String shorten(String a) {
//        if (!a.trim().contains(" ")) {
//            if (a.length() >= 8)
//                return a.trim().substring(0, 5);
//            else
//                return a.trim();
//        }
//
//        String answer = "", A[];
//        A = a.trim().split(" ");
//
//        for (int i = 0; i < A.length; i++) {
//
//            if (A[i].trim().length() == 1 || isInIgnoreList(A[i].trim()))
//                continue;
//            else if (A[i].length() < 5) {
//                answer += A[i] + " ";
//            } else if (A[i].length() >= 5) {
//                String ex = String.valueOf(A[i].trim().charAt(0)) + ". ";
//                answer += ex;
//            }
//        }
//
//        return answer;
//    }

    public void incrementProgressSlowly(final int i) {
        threadInc = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    presentProgress = i;
                    mech();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        threadInc.start();
    }

    private void mech() throws InterruptedException {
        synchronized (this) {
            while (true) {

                if (presentProgress == 99) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            web.stopLoading();
                            ((ViewManager) web.getParent()).removeView(web);
                            web.destroy();
                            getProgressBar().setVisibility(View.INVISIBLE);

                        }
                    });
                    break;
                }

                if (mProgressBar.getProgress() == presentProgress) {
                    Log.i("MECH", "Waiting" + mProgressBar.getProgress());
                    wait();
                    Log.i("MECH", "Resuming" + mProgressBar.getProgress());
                } else {
                    int x = mProgressBar.getProgress();
                    x++;
                    mProgressBar.setProgress(x);
                }
                Thread.sleep(30);
            }
        }
    }

    private void stopChangeandNotifyMech(int a) {
        synchronized (this) {
            if (mProgressBar.getProgress() != 100) {
                presentProgress = a;
                Log.i("stop&change", "notify" + mProgressBar.getProgress());
                notify();
            }
        }
    }

    private boolean isInIgnoreList(String list) {
        String L[] = { "and", "&", "of", "using" };
        for (String A : L) {
            if (list.equalsIgnoreCase(A))
                return true;
        }
        return false;
    }

    private void setProgressss(final int i) {
        if (i == 5) {
            incrementProgressSlowly(i);
        } else
            stopChangeandNotifyMech(i);

    }

    public int checkNumber(String a) {
        try {
            Integer.parseInt(a.trim());
            return 0;
        } catch (NumberFormatException e) {
            if (a.contains("/"))
                return 1;
            return 2;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

}

//public class WebsisResultActivity extends ProgressActivity {
//
//    private static final String JAVASCRIPT_BODY_FETCH = "javascript:window.HTMLOUT.processHTML(document.getElementById('ListAttendanceSummary_table').innerText);";
//    private static final String ATTENDANCE_URL = "http://websismit.manipal.edu/websis/control/StudentAcademicProfile";
//
//    private static final String CHECK_MAIN_PAGE = "Student Information Portal";
//    private static final String CHECK_LOGIN = "This portal is a read only portal";
//
//    String username;
//    String dateOfBirth;
//    String htmlData = "Null";
//
//    TextView tvResult;
//    TableLayout tb;
//    TableRow row;
//    RelativeLayout rl;
//    private Thread threadInc;
//    private int presentProgress;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.websis_result);
//
//        username = getIntent().getExtras().getString("user");
//        dateOfBirth = getIntent().getExtras().getString("pass");
//
//        WebView view = new WebView(this);
//
//        //Some settings changed so that webview loads up quickly
//
//        view.getSettings().setDomStorageEnabled(true);
//        view.getSettings().setJavaScriptEnabled(true);
//        view.getSettings().setBlockNetworkImage(true);
//        view.getSettings().setSavePassword(false);
//        view.getSettings().setSaveFormData(false);
//
//        //Add a JS interface on the view which will help extract the text required
//        view.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");
//
//        view.loadUrl(ATTENDANCE_URL);
//        view.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView v, String url)
//            {
//                Log.i("HELLO FROM","1111111111");
//                v.loadUrl("javascript: {" +
//                    "document.getElementById('idValue').value = '" + username +"';" +
//                    "document.getElementById('birthDate').value = '" + dateOfBirth + "';" +
//                    "document.getElementsByName('loginform')[0].submit(); };");
//                v.loadUrl(JAVASCRIPT_BODY_FETCH);
//                v.setWebViewClient(new WebViewClient() {
//                    @Override
//                    public void onPageFinished(WebView w, String url) {
//                        Log.i("HELLO FROM","22222222222");
//                        w.loadUrl(ATTENDANCE_URL);
//                        w.setWebViewClient(new WebViewClient() {
//                            @Override
//                            public void onPageFinished(WebView x, String url) {
//                                Log.i("HELLO FROM","3333333333");
//                                x.loadUrl(JAVASCRIPT_BODY_FETCH);
//                            }
//                        });
//                    }
//                });
//            }
//        });
//        setContentView(view);
//    }
//
//    /* An instance of this class will be registered as a JavaScript interface */
//    class MyJavaScriptInterface
//    {
//        @JavascriptInterface
//        @SuppressWarnings("unused")
//        public void processHTML(String html)
//        {
//            htmlData = html;
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Toast.makeText(getApplicationContext(),htmlData, Toast.LENGTH_SHORT).show();
//    }
//
//    public static boolean check(String u, String p) {
//        if (u == null || p == null)
//            return false;
//        else if (u.trim().equals("") || p.trim().equals(""))
//            return false;
//        else {
//            try {
//                String[] A = p.trim().split("-");
//                if (u.length() == 9 && A[0].trim().length() == 4
//                        && A[1].trim().length() == 2
//                        && A[2].trim().length() == 2)
//                    return true;
//                return false;
//            } catch (Exception e) {
//                e.printStackTrace();
//                return false;
//            }
//        }
//    }
//}