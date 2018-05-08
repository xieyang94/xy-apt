package com.xiey94.apt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xiey94.bindview_annotation.BindView;
import com.xiey94.bindview_annotation.ContentView;
import com.xiey94.bindview_annotation.LongClick;
import com.xiey94.bindview_annotation.OnClick;
import com.xiey94.bindview_api.ButterKnife;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {


    @BindView(R.id.tv_1)
    public TextView tv_1;

    @BindView(R.id.tv_2)
    public TextView tv_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        tv_1.setText("success!!!");

    }


    @OnClick({R.id.tv_1, R.id.tv_2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_1:
                Toast.makeText(this, "1  success", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_2:
                Toast.makeText(this, "2  success", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @LongClick({R.id.tv_1, R.id.tv_2})
    public void onLongClick(View view) {
        switch (view.getId()) {
            case R.id.tv_1:
                Toast.makeText(this, "1  success", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_2:
                Toast.makeText(this, "2  success", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
