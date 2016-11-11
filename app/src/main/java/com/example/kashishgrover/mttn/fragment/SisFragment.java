package com.example.kashishgrover.mttn.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kashishgrover.mttn.R;
import com.example.kashishgrover.mttn.activity.SisResultActivity;
import com.example.kashishgrover.mttn.other.InternetCheck;
import com.example.kashishgrover.mttn.other.LogData;

/**
 * Created by Kashish Grover on 10/10/2016.
 */
public class SisFragment extends Fragment {

    private SisFragment.OnFragmentInteractionListener mListener;

    public SisFragment() {
        // Required empty public constructor
    }

    private String currentURL = "https://sis.manipal.edu/studlogin.aspx";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("SwA", "WVF onCreateView");
        View v = inflater.inflate(R.layout.sis, container, false);
        if (currentURL != null) {
            Log.d("SwA", "Current URL 1["+currentURL+"]");
            WebView wv = (WebView) v.findViewById(R.id.webViewSis);
            wv.setWebViewClient(new SisFragment.SwAWebClient());
            wv.loadUrl(currentURL);
            wv.setWebViewClient(new WebViewClient() {
                @SuppressLint("NewApi")
                @Override
                public void onPageFinished(WebView view, String url) {
                    Log.i("HELLO","onPageFinished()");
                    super.onPageFinished(view, url);
                }
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    Log.i("HELLO","onPageStarted()");
                    super.onPageStarted(view, url, favicon);
                }
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    handler.proceed(); // Ignore SSL certificate errors
                }
            });
        }
        return v;
    }

    private class SwAWebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
