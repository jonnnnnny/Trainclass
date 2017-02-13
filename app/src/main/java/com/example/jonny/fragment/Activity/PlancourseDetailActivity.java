package com.example.jonny.fragment.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jonny.fragment.Bean.Course;
import com.example.jonny.fragment.R;

import java.io.Serializable;

/**
 * Created by jonny on 2016/7/13.
 */
public class PlancourseDetailActivity extends Activity {
    private TextView ptv_name;
    private TextView ptv_tname;
    private TextView ptv_date;
    private TextView ptv_time;
    private TextView ptv_cnumber;
    private TextView ptv_pnumber;
    private TextView ptv_info;
    private TextView ptv_id;
    private TextView ptv_place;
    private TextView pcourseinfo;
    private TextView pclassloc;
    Course course;
    String phone;
    int status;
    protected void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        setContentView(R.layout.planclass_d);
        init();

        Bundle bundle=this.getIntent().getExtras();
        course=(Course)bundle.getSerializable("plancourse");
        phone=bundle.getString("phone");
        status=bundle.getInt("status");

       String pcnumber=Integer.toString(course.getCnumber());
        String pcpnumber=Integer.toString(course.getCpnumber());

        ptv_id.setText(course.getCid());
        ptv_name.setText(course.getCname());
        ptv_tname.setText(course.getCteacher());
        ptv_place.setText(course.getCplace());
        ptv_date.setText(course.getCstartdata()+"-"+course.getCenddata());
        ptv_time.setText(course.getCtime()+"-"+course.getCschtime());
        ptv_cnumber.setText(pcnumber);
        ptv_pnumber.setText(pcpnumber);
        ptv_info.setText("    "+course.getCinfo());
    }

    private void init() {
        ptv_name=(TextView)findViewById(R.id.ptv_name);
        ptv_tname=(TextView)findViewById(R.id.ptv_tname);
        ptv_id=(TextView)findViewById(R.id.ptv_id);
        ptv_date=(TextView)findViewById(R.id.ptv_date);
        ptv_time=(TextView)findViewById(R.id.ptv_time);
        ptv_cnumber=(TextView)findViewById(R.id.ptv_cnumber);
        ptv_pnumber=(TextView)findViewById(R.id.ptv_pnumber);
        ptv_place=(TextView)findViewById(R.id.ptv_place);
        ptv_info=(TextView)findViewById(R.id.ptv_detail);
        pcourseinfo=(TextView)findViewById(R.id.tv_pcouinfo);
        pclassloc=(TextView)findViewById(R.id.pclassloc);
        pclassloc.setClickable(true);
        pcourseinfo.setClickable(true);
        pcourseinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a=0;
                Intent intent =new Intent(PlancourseDetailActivity.this,CouresInfoActivity.class);
                Bundle bundle1=new Bundle();
                bundle1.putSerializable("course",(Serializable)course);
                bundle1.putString("cp_name",course.getCname());
                bundle1.putString("phone",phone);
                bundle1.putInt("a",a);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
        pclassloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PlancourseDetailActivity.this,MapLocationActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("phone",phone);
                bundle.putInt("status",status);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        ImageButton ib_back=(ImageButton)findViewById(R.id.ib_pback);
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PlancourseDetailActivity.this,MainActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("phone",phone);
                bundle.putInt("status",status);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
