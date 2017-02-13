package com.example.jonny.fragment.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jonny.fragment.Bean.UserInfo;
import com.example.jonny.fragment.HttPConnect;
import com.example.jonny.fragment.R;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jonny on 2016/7/19.
 */
public class UserInfoActivity extends Activity {
    private TextView sc_name;
    private TextView sc_id;
    private TextView sc_phone;
    private TextView sc_idcardno;
    private TextView sc_qq;
    private TextView sc_email;
    private TextView sc_gender;
    private TextView sc_history;
    private ImageButton ib_back;
    private ImageButton ib_edit;
    private Button btn_exit;
    private TextView tv_location;
    String userphone;
    UserInfo userinfo;
    int status,u_type;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfoview);
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

        Intent intent1 = getIntent();
        userphone = intent1.getStringExtra("phone");
        // sc_id.setText(userphone);
        try {
            String sql = "select u_type from user where u_phone=" + userphone;
            String url= "http://192.168.200.101:9999/TrainClass/sql/select.do?sql=" + URLEncoder.encode(sql, "utf-8");
            byte[] data2=HttPConnect.readParse(url);
            JSONArray array = new JSONArray(new String(data2));
            JSONObject job1=array.getJSONObject(0);
            u_type=job1.getInt("u_type");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (u_type==1){

        try {
            String url="http://192.168.200.101:9999/TrainClass/student/user/getUserInfo.do?phone="
                    +URLEncoder.encode(userphone,"utf-8");

           /* byte[] data=HttPConnect.readParse(url);
            JSONObject jsonObject=new JSONObject(new String(data));
            JSONObject job=jsonObject.getJSONObject("userinfo");
            sc_id.setText(job.getString("scId"));
            sc_gender.setText(job.getString("scGender"));
            sc_idcardno.setText(job.getString("scIdcardno"));
            sc_email.setText(job.getString("scEmail"));*/
               userinfo=HttPConnect.getUserInfo(url);
                sc_id.setText(userinfo.getSc_id());
                sc_name.setText(userinfo.getSc_name());
                sc_phone.setText(userphone);
                sc_idcardno.setText(userinfo.getSc_idcardno());
                sc_qq.setText(userinfo.getSc_qq());
                sc_email.setText(userinfo.getSc_email());
                sc_gender.setText(userinfo.getSc_gender());
                sc_history.setText(userinfo.getSc_history());



        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        }else if (u_type==0){
            //sc_name.setText();
            sc_phone.setText(userphone);
            Toast.makeText(getApplication(), "游客身份", Toast.LENGTH_SHORT).show();
        }
        buttonClick();
    }
    private void buttonClick() {
        ib_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (u_type== 1) {
                        Intent intent = new Intent(UserInfoActivity.this, SetUserInfoActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userinfo", (Serializable) userinfo);
                        bundle.putString("phone", userphone);
                        bundle.putInt("status",u_type);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else if (u_type==0){
                        Toast.makeText(getApplication(), "请先选择课程报名后才能修改个人信息", Toast.LENGTH_SHORT).show();
                    }
            }
        });
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
                // int status=1;
                Bundle bundle = new Bundle();
                bundle.putString("phone", userphone);
                bundle.putInt("status", status);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInfoActivity.this, LoginAcitvity.class);
                SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("autologin", false);
                editor.commit();
                status = 0;
                userphone = null;
                startActivity(intent);
            }
        });
    }
    private void init() {
        sc_id = (TextView) findViewById(R.id.tv_scid);
        sc_phone = (TextView) findViewById(R.id.tv_scphone);
        sc_name = (TextView) findViewById(R.id.tv_scname);
        sc_idcardno = (TextView) findViewById(R.id.tv_scidcard);
        sc_qq = (TextView) findViewById(R.id.tv_scqq);
        sc_email = (TextView) findViewById(R.id.tv_scemail);
        sc_gender = (TextView) findViewById(R.id.tv_scgender);
        sc_history=(TextView)findViewById(R.id.tv_schistory) ;
        ib_back = (ImageButton) findViewById(R.id.ib_back);
        ib_edit = (ImageButton) findViewById(R.id.ib_edit);
        btn_exit = (Button) findViewById(R.id.btn_exit);
        tv_location=(TextView)findViewById(R.id.tv_location);
        tv_location.setClickable(true);
        final int loc=1;//是否定位 标志
        tv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(UserInfoActivity.this,MapLocationActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("status",status);
                bundle.putInt("loc",loc);
                bundle.putString("phone",userphone);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
