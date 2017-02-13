package com.example.jonny.fragment.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jonny.fragment.Bean.Course;
import com.example.jonny.fragment.HttPConnect;
import com.example.jonny.fragment.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jonny on 2016/7/13.
 */
public class SearchViewActivity extends Activity{
    private ListView searchlist;
    List<Course> searchcourse;
    //private TextView test;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchlist);

        searchlist=(ListView)findViewById(R.id.sc_listview);
        Intent intent=getIntent();
        //String url=intent.getStringExtra("url");
        String cp_coursename=intent.getStringExtra("cp_coursename");
        String cp_teachername=intent.getStringExtra("cp_teachername");
        String cp_locaname=intent.getStringExtra("cp_locaname");
        String cp_timename=intent.getStringExtra("cp_timename");
        String path="http://192.168.200.101:9999/TrainClass/student/cp/getCpInfoBySelection.do";
        String  url=null;

        //test=(TextView)findViewById(R.id.test);
       // test.setText(cp_coursename+cp_teachername+cp_locaname+cp_timename);
        String[] from={"scname","stname","splace","scdata","sctime"};
        int[] to ={R.id.list_name,R.id.list_tname,R.id.list_locaname,R.id.list_data,R.id.list_time};
        try {
            url=path+"?cp_coursename="+ URLEncoder.encode(cp_coursename,"utf-8")+"&cp_teachername="+
                    URLEncoder.encode(cp_teachername,"utf-8")+"&cp_locaname="+URLEncoder.encode(cp_locaname,"utf-8")
                    +"&cp_timename="+URLEncoder.encode(cp_timename,"utf-8");
            searchcourse=HttPConnect.getCourseList(url);
            List<HashMap<String ,Object>> list= new ArrayList<HashMap<String,Object>>();
            if (searchcourse.size()==0){
                Toast.makeText(this,"查询失败，没有符合您所输入条件的班级，请返回重新查询",Toast.LENGTH_LONG).show();
            }else {
                for (int i = 0; i < searchcourse.size(); i++) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    Course course = searchcourse.get(i);
                    map.put("scname", course.getCname());
                    map.put("stname", course.getCteacher());
                    map.put("splace", course.getCplace());
                    map.put("scdata", course.getCstartdata() + "-" + course.getCenddata());
                    map.put("sctime", course.getCtime() + "-" + course.getCschtime());
                    map.put("scid", course.getCid());
                    map.put("scnumber", course.getCnumber());
                    map.put("spnumber", course.getCpnumber());
                    map.put("scinfo", course.getCinfo());
                    list.add(map);
                }
                SimpleAdapter adapter=new SimpleAdapter(this,list,R.layout.class_item,from,to);
                searchlist.setAdapter(adapter);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
       searchlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(SearchViewActivity.this,DetailActivity.class);
                Course course=searchcourse.get(i);
                Bundle bundle=new Bundle();
                bundle.putSerializable("course",(Serializable)course);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}
