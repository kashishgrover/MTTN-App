package com.example.kashishgrover.mttn.activity;

import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by Kashish Grover on 10/10/2016.
 */
public class ResultActivity {
    private static final String main_PAGE_check = "UniCamp Student Information Portal provides you quick and personalized access to your institute record and academic status";
    private static final String JAVASCRIPT_BODY_FETCH = "javascript:window.MITINTERFACE.processContent(document.getElementById('ListAttendanceSummary_table').innerText);";
    private static final String ATTENDANCE_URL = "http://websismit.manipal.edu/websis/control/StudentAcademicProfile";
    private static final String CHECK_LOGIN = "This portal is a read only portal. If you are a student and have the login credentials follow the link below to access";

    WebView web;
    TextView tvResult;
    TableLayout tb;
    TableRow row;
    RelativeLayout rl;
    String html;
    private int count;
    private Thread threadInc;
    private int presentProgress;


}
