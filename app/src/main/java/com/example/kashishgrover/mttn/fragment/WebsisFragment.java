package com.example.kashishgrover.mttn.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kashishgrover.mttn.R;
import com.example.kashishgrover.mttn.activity.ResultActivity;
import com.example.kashishgrover.mttn.other.InternetCheck;
import com.example.kashishgrover.mttn.other.LogData;

/**
 * Created by Kashish Grover on 10/10/2016.
 */
public class WebsisFragment extends Fragment implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {

    EditText user, pass;
    Button submit;
    ImageView xU, xP;
    CheckBox cb;

    public WebsisFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.websis, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        LogData l = new LogData(getActivity());

        try {

            user = (EditText) getView().findViewById(R.id.user);
            pass = (EditText) getView().findViewById(R.id.pass);
            submit = (Button) getView().findViewById(R.id.submit);

            cb = (CheckBox) getView().findViewById(R.id.checkBox1);
            xU = (ImageView) getView().findViewById(R.id.xU);
            xP = (ImageView) getView().findViewById(R.id.xP);

            submit.setOnClickListener((View.OnClickListener) this);
            xU.setOnClickListener((View.OnClickListener) this);
            xP.setOnClickListener((View.OnClickListener) this);

            cb.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
            String u = l.getUser();
            String p = l.getPass();

            if (l.getcheckBox()) {
                cb.setChecked(true);
                if (!user.equals("null")) {
                    user.setText(u, TextView.BufferType.EDITABLE);
                    pass.setText(p, TextView.BufferType.EDITABLE);
                }

            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.xP:
                pass.setText("");
                break;
            case R.id.xU:
                user.setText("");
                break;
            case R.id.submit:
                if (user.getText().toString() != null
                        && !user.getText().toString().equals("")) {

                    try {
                        String userA = user.getText().toString();
                        String passA = pass.getText().toString();

                        if (ResultActivity.check(userA, passA)) {

                            if (cb.isChecked())
                                new LogData(getActivity()).setLoginData(
                                        userA, passA);

                            submit.setTextColor(Color.WHITE);

                            if (InternetCheck.haveInternet(getActivity())) {
                                result(userA, passA);
                            } else
                                InternetCheck.showNoConnectionDialog(
                                        getActivity(), 0);
                        } else {
                            Toast.makeText(getActivity(),
                                    "Invalid username and password",
                                    Toast.LENGTH_SHORT).show();
                            submit.setTextColor(Color.WHITE);

                        }

                    } catch (NullPointerException npe) {
                        npe.printStackTrace();
                    }

                } else {
                    submit.setTextColor(Color.WHITE);
                    Toast.makeText(getActivity(), "Text Fields Empty",
                            Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton arg0, boolean arg1) {

        new LogData(getActivity()).setcheckBox(arg1);
        if (arg1) {

            try {
                String u = user.getText().toString();
                String p = pass.getText().toString();
                if (ResultActivity.check(u, p)) {
                    if (cb.isChecked())
                        new LogData(getActivity())
                                .setLoginData(u, p);
                }
            } catch (NullPointerException npe) {
                npe.printStackTrace();
            }

        }

    }

    public void result(String userA, String passA) {
        Intent A = new Intent(getActivity(), ResultActivity.class);
        A.putExtra("user", userA);
        A.putExtra("pass", passA);
        startActivity(A);
    }
}
