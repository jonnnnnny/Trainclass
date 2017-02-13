package com.example.jonny.fragment.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jonny.fragment.Bean.Course;
import com.example.jonny.fragment.Bean.UserInfo;
import com.example.jonny.fragment.HttPConnect;
import com.example.jonny.fragment.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by jonny on 2016/7/20.
 */
public class AttendCourseActivity extends Activity{
    private TextView tv_accname;
    private TextView tv_accid;
    private TextView tv_actname;
    private TextView tv_acctime;
    private TextView tv_accdate;
    private TextView tv_accplace;
    private TextView tv_acgetphone;
    private EditText et_acuname;
    private EditText et_acuidcatd;
    private Button btn_attendcourse;
    String phone;
    int status=0;
    Course course;
    UserInfo userInfo;
    int u_type;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendcourseview);

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

        final Bundle bundle=this.getIntent().getExtras();
        course=(Course)bundle.getSerializable("course");
        phone=bundle.getString("phone");
        status=bundle.getInt("status");
        init();
        String url= null;
        try {
            url = "http://192.168.200.101:9999/TrainClass/student/user/getUserInfo.do?phone=" + URLEncoder.encode(phone,"utf-8");
            userInfo=HttPConnect.getUserInfo(url);

            et_acuidcatd.setText(userInfo.getSc_idcardno());
            et_acuname.setText(userInfo.getSc_name());

            String sql = "select u_type from user where u_phone=" + phone;
            String url1 = "http://192.168.200.101:9999/TrainClass/sql/select.do?sql=" + URLEncoder.encode(sql, "utf-8");
            byte[] data2=HttPConnect.readParse(url1);
            JSONArray array = new JSONArray(new String(data2));
            JSONObject job1=array.getJSONObject(0);
             u_type=job1.getInt("u_type");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        btn_attendcourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cp_id=course.getCid();
                String sc_name=et_acuname.getText().toString().trim();
                String sc_idcardno=et_acuidcatd.getText().toString().trim();

                if (u_type==1){
                    if (sc_name.equals(userInfo.getSc_name())&&
                    sc_idcardno.equals(userInfo.getSc_idcardno())){



                    try {
                        String path= null;
                        path = "http://192.168.200.101:9999/TrainClass/student/user/simplyApply.do?phone="
                                + URLEncoder.encode(phone,"utf-8")+"&cp_id="+URLEncoder.encode(cp_id,"utf-8")
                                +"&name="+URLEncoder.encode(sc_name,"utf-8")+"&idcardno="+URLEncoder.encode(sc_idcardno,"utf-8");
                        byte[] data= HttPConnect.readParse(path);
                        JSONObject job=new JSONObject(new String(data));
                        String message=job.getString("message");
                        status=job.getInt("status");
                        if (status==1){
                            Toast.makeText(getApplication(),message,Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(AttendCourseActivity.this,SuccessAttendActivity.class);
                            Bundle bundle1=new Bundle();
                            bundle1.putString("phone",phone);
                            bundle1.putString("sc_name",sc_name);
                            bundle1.putString("sc_idcard",sc_idcardno);
                            bundle1.putSerializable("course",course);
                            intent.putExtras(bundle1);
                            startActivity(intent);
                        }else if (status==-1){
                            Toast.makeText(getApplication(),message,Toast.LENGTH_SHORT).show();
                        }

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    }else{
                        Toast.makeText(AttendCourseActivity.this,"姓名或身份证号不一致",Toast.LENGTH_SHORT).show();
                    }
                }else if (u_type==0){
                    if (sc_name.equals("")||sc_idcardno.equals("")){
                        Toast.makeText(AttendCourseActivity.this,"姓名和身份证号不能为空",Toast.LENGTH_SHORT).show();
                    }else {
                        String path= null;
                        try {
                            path = "http://192.168.200.101:9999/TrainClass/student/user/simplyApply.do?phone="
                                    + URLEncoder.encode(phone,"utf-8")+"&cp_id="+URLEncoder.encode(cp_id,"utf-8")
                                    +"&name="+URLEncoder.encode(sc_name,"utf-8")+"&idcardno="+URLEncoder.encode(sc_idcardno,"utf-8");
                        byte[] data= HttPConnect.readParse(path);
                        JSONObject job=new JSONObject(new String(data));
                        String message=job.getString("message");
                        status=job.getInt("status");
                            if (status==1){
                                Toast.makeText(getApplication(),message,Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(AttendCourseActivity.this,SuccessAttendActivity.class);
                                Bundle bundle1=new Bundle();
                                bundle1.putString("phone",phone);
                                bundle1.putString("sc_name",sc_name);
                                bundle1.putString("sc_idcard",sc_idcardno);
                                bundle1.putSerializable("course",course);
                                intent.putExtras(bundle1);
                                startActivity(intent);
                            }

                        } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }





            }
        });
    }

    private void init() {
        tv_accname=(TextView)findViewById(R.id.tv_acname);
        tv_accid=(TextView)findViewById(R.id.tv_acid);
        tv_actname=(TextView)findViewById(R.id.tv_actname);
        tv_acctime=(TextView)findViewById(R.id.tv_acctime);
        tv_accdate=(TextView)findViewById(R.id.tv_acdate);
        tv_accplace=(TextView)findViewById(R.id.tv_acplace);
        tv_acgetphone=(TextView)findViewById(R.id.tv_acgetphone);
        et_acuname=(EditText)findViewById(R.id.et_acscname);
        et_acuidcatd=(EditText)findViewById(R.id.et_acsceidcard);
        btn_attendcourse=(Button)findViewById(R.id.btn_attendcourse);
        tv_accid.setText(course.getCid());
        tv_accname.setText(course.getCname());
        tv_actname.setText(course.getCteacher());
        tv_accplace.setText(course.getCplace());
        tv_accdate.setText(course.getCstartdata()+"-"+course.getCenddata());
        tv_acctime.setText(course.getCtime()+"-"+course.getCschtime());
        tv_acgetphone.setText(phone);






    }
}
