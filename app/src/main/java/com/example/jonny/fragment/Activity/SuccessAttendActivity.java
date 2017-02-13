package com.example.jonny.fragment.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jonny.fragment.Bean.Course;
import com.example.jonny.fragment.HttPConnect;
import com.example.jonny.fragment.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * Created by jonny on 2016/7/20.
 */
public class SuccessAttendActivity extends Activity{
    private TextView saphone;
    private TextView saname;
    private TextView saidcard;
    private TextView sacourseid;
    private TextView sastuid;
    private Button btn_saback;
    String phone,sc_name,sc_idcard;
    Course course;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.successattendview);
        init();

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


        settext();



    }

    private void settext() {
        final Bundle bundle=this.getIntent().getExtras();
        phone=bundle.getString("phone");
        sc_name=bundle.getString("sc_name");
        course=(Course)bundle.getSerializable("course");
        sc_idcard=bundle.getString("sc_idcard");

        saphone.setText(phone);
        saname.setText(sc_name);
        saidcard.setText(sc_idcard);
        sacourseid.setText(course.getCid());
        try {
            String sql = "select u_stuid from user where u_phone=" + phone;
            String url1 = "http://192.168.200.101:9999/TrainClass/sql/select.do?sql=" + URLEncoder.encode(sql, "utf-8");
            byte[] data2= new byte[0];
            data2 = HttPConnect.readParse(url1);
            JSONArray array = new JSONArray(new String(data2));
            JSONObject job1=array.getJSONObject(0);
            String u_stuid=job1.getString("u_stuid");
            sastuid.setText(u_stuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        btn_saback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SuccessAttendActivity.this,MainActivity.class);
                Bundle bundle1=new Bundle();
                bundle1.putString("phone",phone);
                bundle1.putInt("status",1);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
    }

    private void init() {
        saphone=(TextView)findViewById(R.id.tv_saphone);
        saname=(TextView)findViewById(R.id.tv_saname);
        saidcard=(TextView)findViewById(R.id.tv_saidcard);
        sacourseid=(TextView)findViewById(R.id.tv_sacourseid);
        sastuid=(TextView)findViewById(R.id.tv_sastuid);
        btn_saback=(Button)findViewById(R.id.btn_saback);

    }

}
