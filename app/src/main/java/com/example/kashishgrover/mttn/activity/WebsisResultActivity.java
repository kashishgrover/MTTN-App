package com.example.kashishgrover.mttn.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kashishgrover.mttn.ListItem;
import com.example.kashishgrover.mttn.MyListAdapter;
import com.example.kashishgrover.mttn.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

public class WebsisResultActivity extends ProgressActivity {

    private static final String JAVASCRIPT_BODY_FETCH = "javascript:window.HTMLOUT.processContent(document.getElementById('ListAttendanceSummary_table').innerText);";
    private static final String ATTENDANCE_URL = "http://websismit.manipal.edu/websis/control/StudentAcademicProfile";

    WebView web;
    TextView tvResult;
    LinearLayout linearLayout;
    private int presentProgress;

    ArrayList<ListItem> subjectListArray = new ArrayList<>();

    MyListAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.websis_result);

        adapter = new MyListAdapter(this, subjectListArray);
        listView = (ListView) findViewById(R.id.listViewWebsis);
        listView.setAdapter(adapter);

        getProgressBar().setVisibility(View.VISIBLE);
        try {
            linearLayout = (LinearLayout) findViewById(R.id.REL);
            tvResult = (TextView) findViewById(R.id.tvResult);
            getProgressBar().setMax(100);
            setProgressPlease(5);

            String userA, passA;
            userA = getIntent().getExtras().getString("user");
            passA = getIntent().getExtras().getString("pass");
            submit(userA, passA);

        } catch (Exception e) {
            e.printStackTrace();
        }

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
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
                return u.length() == 9 && A[0].trim().length() == 4
                        && A[1].trim().length() == 2
                        && A[2].trim().length() == 2;
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
            contentView.post(new Runnable() {
                public void run() {
                    Log.i("TAG:HTMLOUT", aContent);
                    contentView.setText(aContent);
                    web.stopLoading();
                    ((ViewManager) web.getParent()).removeView(web);
                    setProgressPlease(98);
                    web.destroyDrawingCache();
                    web.destroy();
                    parseTheContentAndDisplay(aContent);

                    getProgressBar().setVisibility(View.INVISIBLE);
                }

            });
        }

        private void parseTheContentAndDisplay(String aContent) {
            try {
                String[] subjectArray = aContent.split("(\\s\\s)+");
                for(int i=1; i<subjectArray.length; i++)
                {
                    subjectArray[i] = subjectArray[i].trim();
                    String[] subjectParams = subjectArray[i].split("[\\t]");
                    String name = "";
                    String total = "";
                    String present = "";
                    String absent = "";
                    String percentage = "";
                    String updated = "";
                    for(int j=1;j<subjectParams.length;j++)
                    {
                        if(j==1)
                        {
                            name = subjectParams[j];
                        }
                        if(j==2)
                        {
                            total = "\nTotal: "+subjectParams[j];
                        }
                        else if(j==3)
                        {
                            present = "\nPresent: "+subjectParams[j];
                        }
                        else if(j==4)
                        {
                            absent = "\nAbsent: "+subjectParams[j];
                        }
                        else if(j==5)
                        {
                            int num = Integer.parseInt(subjectParams[j]);
                            if(num <= 75)
                                subjectParams[j] = subjectParams[j] + "*";
                            percentage = "\nPercentage: "+subjectParams[j];
                        }
                        else if(j==6)
                        {
                            updated = "\nUpdated: "+subjectParams[j];
                        }
                    }
                    String params = total+present+absent+percentage+updated;

                    ListItem x = new ListItem(name,params);

                    subjectListArray.add(x);
                    Log.i("Subject:",subjectArray[i]);
                }
            }catch(PatternSyntaxException e)
            {
                e.printStackTrace();
            }
            adapter.notifyDataSetChanged();
        }
    }

    public void incrementProgressSlowly(final int i) {
        Thread threadInc = new Thread(new Runnable() {
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

    private void stopChangeAndNotifyMech(int a) {
        synchronized (this) {
            if (mProgressBar.getProgress() != 100) {
                presentProgress = a;
                Log.i("stop&change", "notify" + mProgressBar.getProgress());
                notify();
            }
        }
    }

    private void setProgressPlease(final int i) {
        if (i == 5) {
            incrementProgressSlowly(i);
        } else
            stopChangeAndNotifyMech(i);

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