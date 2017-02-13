package com.example.jonny.fragment.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jonny.fragment.Bean.UserInfo;
import com.example.jonny.fragment.HttPConnect;
import com.example.jonny.fragment.R;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by jonny on 2016/7/19.
 */
public class SetUserInfoActivity extends Activity {
    private EditText et_scidcard;
    private EditText et_scname;
    private EditText et_scqq;
    private EditText et_scemail;
    private TextView tv_getphone;
    private RadioButton rb_man;
    private RadioButton rb_woman;
    private Button btn_setuserinfo;
    String phone;
    int status=0;
    UserInfo userInfo;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setuserinfoview);
        init();
        Bundle bundle=this.getIntent().getExtras();
        phone=bundle.getString("phone");
        status=bundle.getInt("status");
        userInfo= (UserInfo) bundle.getSerializable("userinfo");
        tv_getphone.setText(phone);
        et_scname.setText(userInfo.getSc_name());
        et_scqq.setText(userInfo.getSc_qq());
        et_scidcard.setText(userInfo.getSc_idcardno());
        et_scemail.setText(userInfo.getSc_email());
        if (userInfo.getSc_gender().equals("男")){
            rb_man.setChecked(true);
        }else if (userInfo.getSc_gender().equals("女")){
             rb_woman.setChecked(true);

        }
        btn_setuserinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        if (status==1){

            String sc_qq=et_scqq.getText().toString().trim();
            String sc_email=et_scemail.getText().toString().trim();
            String sc_gender = "";
            if (rb_man.isChecked()){
                 sc_gender="男";
            }else if (rb_woman.isChecked()){
                 sc_gender="女";
            }

            try {
                String path="http://192.168.200.101:9999/TrainClass/student/user/updateUserInfo.do?phone="
                        + URLEncoder.encode(phone,"utf-8")+"&sc_qq="+URLEncoder.encode(sc_qq,"utf-8")+"&sc_email="
                        +URLEncoder.encode(sc_email,"utf-8")+"&sc_gender="+URLEncoder.encode(sc_gender,"utf-8");
                byte[] data= HttPConnect.readParse(path);
                JSONObject job=new JSONObject(new String(data));
                String message=job.getString("message");
                int status=job.getInt("status");
                if (status==1){
                    Toast.makeText(getApplication(),message,Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(SetUserInfoActivity.this,UserInfoActivity.class);
                    intent.putExtra("phone",phone);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplication(),message,Toast.LENGTH_SHORT).show();
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
            }
        });

    }

    private void init() {
        et_scemail=(EditText)findViewById(R.id.et_scemail);
        et_scidcard=(EditText)findViewById(R.id.et_scidcard);
        et_scname=(EditText)findViewById(R.id.et_scname);
        et_scqq=(EditText)findViewById(R.id.et_scqq);
        rb_man=(RadioButton)findViewById(R.id.rb_man);
        rb_woman=(RadioButton)findViewById(R.id.rb_woman);
        tv_getphone=(TextView)findViewById(R.id.tv_getphone);
        btn_setuserinfo=(Button)findViewById(R.id.btn_setuserinfo);



    }
}
