package com.example.jonny.fragment.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jonny.fragment.Bean.Course;
import com.example.jonny.fragment.R;

import java.io.Serializable;

/**
 * Created by jonny on 2016/7/11.
 */
public class DetailActivity extends Activity{
    private TextView tv_name;
    private TextView tv_tname;
    private TextView tv_date;
    private TextView tv_time;
    private TextView tv_cnumber;
    private TextView tv_pnumber;
    private TextView tv_info;
    private TextView tv_id;
    private TextView tv_place;
    private Button btn_attend;
    private TextView classloc;
    private TextView couinfo;

    Course course;
    String phone;
    int status=0;
    protected void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        setContentView(R.layout.class_d);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
        init();
        final Bundle bundle=this.getIntent().getExtras();
        course=(Course)bundle.getSerializable("course");
        phone=bundle.getString("phone");
        status=bundle.getInt("status");
        String cnumber=Integer.toString(course.getCnumber());
        String cpnumber=Integer.toString(course.getCpnumber());
        tv_id.setText(course.getCid());
        tv_name.setText(course.getCname());
        tv_tname.setText(course.getCteacher());
        tv_place.setText(course.getCplace());
        tv_date.setText(course.getCstartdata()+"-"+course.getCenddata());
        tv_time.setText(course.getCtime()+"-"+course.getCschtime());
        tv_cnumber.setText(cnumber);
        tv_pnumber.setText(cpnumber);
        tv_info.setText("    "+course.getCinfo());
        classloc.setClickable(true);
        couinfo.setClickable(true);
        couinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a=1;//进行班级标志
                Intent intent =new Intent(DetailActivity.this,CouresInfoActivity.class);
                Bundle bundle1=new Bundle();
                bundle1.putSerializable("course",(Serializable)course);
                bundle1.putString("cp_name",course.getCname());
                bundle1.putString("phone",phone);
               bundle1.putInt("a",a);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
        classloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(DetailActivity.this,MapLocationActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("course",(Serializable)course);
                bundle.putString("phone",phone);
                bundle.putInt("status",status);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void init() {
        tv_name=(TextView)findViewById(R.id.tv_name);
        tv_tname=(TextView)findViewById(R.id.tv_tname);
        tv_date=(TextView)findViewById(R.id.tv_date);
        tv_time=(TextView)findViewById(R.id.tv_time);
        tv_cnumber=(TextView)findViewById(R.id.tv_cnumber);
        tv_pnumber=(TextView)findViewById(R.id.tv_pnumber);
        tv_info=(TextView)findViewById(R.id.tv_detail);
        tv_id=(TextView)findViewById(R.id.tv_id);
        tv_place=(TextView)findViewById(R.id.tv_place);
        btn_attend=(Button)findViewById(R.id.btn_attend);
        classloc=(TextView) findViewById(R.id.classloc);
        couinfo=(TextView)findViewById(R.id.tv_couinfo);
        ImageButton ib_back=(ImageButton)findViewById(R.id.ib_dback);
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DetailActivity.this,MainActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("phone",phone);
                bundle.putInt("status",status);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        btn_attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status==1){
                Intent intent =new Intent(DetailActivity.this,AttendCourseActivity.class);

                Bundle bundle=new Bundle();
                bundle.putSerializable("course",(Serializable)course);

                  bundle.putString("phone",phone);
                  bundle.putInt("status",status);
                  intent.putExtras(bundle);
                  startActivity(intent);
                }else{
                    Toast.makeText(getApplication(),"尚未登陆，请先登录后再选课",Toast.LENGTH_SHORT).show();
                    Intent intent1=new Intent(DetailActivity.this,LoginAcitvity.class);
                    startActivity(intent1);
                }

            }
        });

    }

}
