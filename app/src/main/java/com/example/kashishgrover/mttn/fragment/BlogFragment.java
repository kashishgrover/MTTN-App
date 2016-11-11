package com.example.kashishgrover.mttn.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewFragment;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kashishgrover.mttn.R;
import com.example.kashishgrover.mttn.activity.SimpleRSSReaderActivity;
import com.example.kashishgrover.mttn.activity.SisResultActivity;

import java.util.List;

public class BlogFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public BlogFragment() {
        // Required empty public constructor
    }

    private String currentURL = "http://manipalthetalk.org/";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("SwA", "WVF onCreateView");
        View v = inflater.inflate(R.layout.fragment_blog, container, false);
        if (currentURL != null) {
            Log.d("SwA", "Current URL 1["+currentURL+"]");
            WebView wv = (WebView) v.findViewById(R.id.webViewBlog);
            wv.setWebViewClient(new SwAWebClient());
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
