package com.example.jonny.fragment.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jonny.fragment.Bean.Course;
import com.example.jonny.fragment.Bean.CourseInfo;
import com.example.jonny.fragment.HttPConnect;
import com.example.jonny.fragment.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by jonny on 2016/7/27.
 */
public class CouresInfoActivity extends Activity{
    private TextView cpid;
    private TextView cpname;
    private TextView cpinfo;
    private TextView cpstdprice;
    private TextView cpstuprice;
    private TextView cpspecprice;
    private  TextView cpmaintm;
    private TextView cprefertm;
    private TextView cphour;
    String cp_name,phone;
    CourseInfo courseInfo;
    Course course;
    int a;
    protected void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        setContentView(R.layout.courseinfoview);

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
        Bundle bundle=getIntent().getExtras();
        course= (Course) bundle.getSerializable("course");
        cp_name=bundle.getString("cp_name");
        phone=bundle.getString("phone");
       a=bundle.getInt("a");
        try {
            String sql="SELECT * FROM course_product WHERE cp_name="+"'"+cp_name+"'";
            String url="http://192.168.200.101:9999/TrainClass/sql/select.do?sql="+ URLEncoder.encode(sql,"utf-8");
            byte[] data=HttPConnect.readParse(url);
            JSONArray array=new JSONArray(new String(data));
            JSONObject jsonObject=array.getJSONObject(0);
            cpid.setText(jsonObject.getString("cp_id"));
            cpinfo.setText(jsonObject.getString("cp_info"));
            cpname.setText(jsonObject.getString("cp_name"));
            cpmaintm.setText(jsonObject.getString("cp_maintm"));
            cpstdprice.setText(jsonObject.getString("cp_stdprice"));
            cpstuprice.setText(jsonObject.getString("cp_stuprice"));
            cpspecprice.setText(jsonObject.getString("cp_specprice"));
            cprefertm.setText(jsonObject.getString("cp_refertm"));
            cphour.setText(jsonObject.getString("cp_hour"));

           /* courseInfo= HttPConnect.getCourseInfo(url);
            cpid.setText(courseInfo.getCp_id());
            cpname.setText(courseInfo.getCp_name());
            cpinfo.setText(courseInfo.getCp_info());
            cpstdprice.setText(courseInfo.getCp_stdprice());
            cpstuprice.setText(courseInfo.getCp_stuprice());
            cpspecprice.setText(courseInfo.getCp_specprice());
            cphour.setText(courseInfo.getCp_hour());
            cpmaintm.setText(courseInfo.getCp_maintm());
            cprefertm.setText(courseInfo.getCp_refertm());*/

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        cpid=(TextView)findViewById(R.id.tv_cpid);
        cpname=(TextView)findViewById(R.id.tv_cpname);
        cpinfo=(TextView)findViewById(R.id.tv_cpinfo);
        cpstdprice=(TextView)findViewById(R.id.tv_stdprice);
        cpstuprice=(TextView)findViewById(R.id.tv_stuprice);
        cpspecprice=(TextView)findViewById(R.id.tv_specprice);
        cpmaintm=(TextView)findViewById(R.id.tv_maintm);
        cprefertm=(TextView)findViewById(R.id.tv_refertm);
        cphour=(TextView)findViewById(R.id.tv_cphour);
        ImageButton ib_back=(ImageButton)findViewById(R.id.ib_cback);
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int status=1;
                Bundle bundle=new Bundle();
                bundle.putSerializable("course",(Serializable)course);
                bundle.putString("phone",phone);
                bundle.putInt("status",status);
            if (a==1){
                Intent intent=new Intent(CouresInfoActivity.this,DetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

               }else if (a==0){
                    Intent intent=new Intent(CouresInfoActivity.this,PlancourseDetailActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }
}
