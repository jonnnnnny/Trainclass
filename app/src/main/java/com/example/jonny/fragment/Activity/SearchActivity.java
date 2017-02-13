package com.example.jonny.fragment.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.Spinner;
import android.widget.TextView;

import com.example.jonny.fragment.Bean.Course;
import com.example.jonny.fragment.HttPConnect;
import com.example.jonny.fragment.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jonny on 2016/7/11.
 */
public class SearchActivity extends Activity{
    private Spinner sp_cname;
    private Spinner sp_tname;
    private Spinner sp_cplace;
    private Spinner sp_ctime;
    private Button btn_search;
    private Button btn_back;
    private TextView test1;
    String cp_coursename,cp_timename,cp_teachername,cp_locaname,url;
    String[] coursename;
    String[] coursetname;
    String[] courseplace;
    String[] coursetime;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchview);
        init();
        coursename=getResources().getStringArray(R.array.coursename);
        coursetname=getResources().getStringArray(R.array.coursetname);
        courseplace=getResources().getStringArray(R.array.courseplace);
        coursetime=getResources().getStringArray(R.array.coursetime);


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(SearchActivity.this,MainActivity.class);

                startActivity(intent1);
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(SearchActivity.this,SearchViewActivity.class);
                cp_coursename=sp_cname.getSelectedItem().toString();
                cp_teachername=sp_tname.getSelectedItem().toString();
                cp_locaname=sp_cplace.getSelectedItem().toString();
                cp_timename=sp_ctime.getSelectedItem().toString();
                intent2.putExtra("cp_coursename",cp_coursename);
                intent2.putExtra("cp_teachername",cp_teachername);
                intent2.putExtra("cp_locaname",cp_locaname);
                intent2.putExtra("cp_timename",cp_timename);

                //intent2.putExtra("url",url);
                startActivity(intent2);

            }
        });




    }

    private void init() {
        sp_cname=(Spinner)findViewById(R.id.sp_cname);
        sp_tname=(Spinner)findViewById(R.id.sp_tname);
        sp_cplace=(Spinner)findViewById(R.id.sp_cplace);
        sp_ctime=(Spinner)findViewById(R.id.sp_ctime);
        btn_search=(Button)findViewById(R.id.btn_search);
        btn_back=(Button)findViewById(R.id.btn_back);
        test1=(TextView)findViewById(R.id.stv_test);
        /*sp_cname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cp_coursename=coursename[i];
               // test1.setText((String)sp_cname.getSelectedItem());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp_tname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cp_teachername=coursetname[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp_cplace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cp_locaname=courseplace[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp_ctime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cp_timename=coursetime[i];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/


    }


}
