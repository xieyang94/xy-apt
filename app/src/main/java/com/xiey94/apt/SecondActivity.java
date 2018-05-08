package com.xiey94.apt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.xiey94.bindview_annotation.BindView;
import com.xiey94.bindview_annotation.ContentView;
import com.xiey94.bindview_annotation.OnClick;
import com.xiey94.bindview_api.ButterKnife;

@ContentView(R.layout.activity_second)
public class SecondActivity extends AppCompatActivity {

    @BindView(R.id.btn1)
    public Button btn1;


    @BindView(R.id.btn2)
    public Button btn2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        btn1.setText("success!!");
        btn2.setText("again!!");
    }

    @OnClick({R.id.btn1, R.id.btn2})
    public void onClick(View view) {

    }
}
