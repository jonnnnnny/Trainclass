package com.example.jonny.fragment.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.jonny.fragment.HttPConnect;
import com.example.jonny.fragment.R;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by jonny on 2016/7/15.
 */
public class SignupActivity extends Activity{
    String APPKEY = "141312a75c78e";
    String APPSECRET = "04a1f9fbd86517bf7e37f71fa42b955e";
    private EditText et_phone;
    private EditText et_code;
    //private EditText et_pwd;
    private Button btn_requestCode;
    private Button btn_summit;
    private TextView tv_backlogin;
    private TextView tv_jumpmain2;
    String phone;
    String pwd;
    int i=60;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupview);
        init();
        tvclick();




        btn_requestCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String phoneNums=et_phone.getText().toString().trim();
                phone=et_phone.getText().toString().trim();
                //pwd=et_pwd.getText().toString().trim();
                //TextView test11=(TextView)findViewById(R.id.test11);
                //test11.setText(phone);
                if (!judgePhoneNums(phone)){
                    return;
                }
                SMSSDK.getVerificationCode("86",phone);
                btn_requestCode.setClickable(false);
                btn_requestCode.setText("重新发送(" + i + ")");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (;i>0;i--){
                            handler.sendEmptyMessage(-9);
                            if (i<=0){
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.sendEmptyMessage(-8);
                    }
                }).start();

            }
        });
        btn_summit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SMSSDK.submitVerificationCode("86",phone,et_code.getText().toString());

            }
        });

    }

    private void tvclick() {
        tv_backlogin.setClickable(true);
        tv_jumpmain2.setClickable(true);
        tv_jumpmain2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(SignupActivity.this,MainActivity.class);
                startActivity(intent1);
            }
        });
        tv_backlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(SignupActivity.this,LoginAcitvity.class);
                startActivity(intent2);
            }
        });
    }

    private void init() {
        et_phone=(EditText)findViewById(R.id.et_phone);
        et_code=(EditText)findViewById(R.id.et_code);
        //et_pwd=(EditText)findViewById(R.id.et_pwd);
        btn_requestCode=(Button)findViewById(R.id.btn_requestcode);
        btn_summit=(Button)findViewById(R.id.btn_summit);
        tv_backlogin=(TextView)findViewById(R.id.tv_backlogin);
        tv_jumpmain2=(TextView)findViewById(R.id.tv_jumpmain2);

        SMSSDK.initSDK(this,APPKEY,APPSECRET);
        EventHandler eventHandler=new EventHandler(){
            public void afterEvent(int event,int result,Object data){
                Message msg=new Message();
                msg.arg1=event;
                msg.arg2=result;
                msg.obj=data;
                handler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
    }
    Handler handler=new Handler(){
        public void handleMessage(Message msg){
            if (msg.what==-9){
                btn_requestCode.setText("重新发送(" + i + ")");
            }else if (msg.what==-8){
                btn_requestCode.setText("获取验证码");
                btn_requestCode.setClickable(true);
                i=60;
            }else {
                int event=msg.arg1;
                int result=msg.arg2;
                Object data=msg.obj;
                Log.e("event", "event=" + event);
                if (result==SMSSDK.RESULT_COMPLETE){
                    if (event==SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                        Toast.makeText(getApplicationContext(),"提交验证码成功",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(SignupActivity.this,SetPwdActivity.class);
                        intent.putExtra("phone",phone);
                        startActivity(intent);
                       /* try {
                            String path="http://192.168.200.101:9999/TrainClass/student/user/register.do" +
                                    "+?phone="+ URLEncoder.encode(phone,"utf-8")+"&password="+URLEncoder.encode(pwd,"utf-8");
                            byte[] jsondata= HttPConnect.readParse(path);
                            JSONObject job=new JSONObject(new String(jsondata));
                            String message=job.getString("message");
                            int status=job.getInt("status");
                            if (status==1){
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

                            }else if (status==0){
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                            }

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }*/



                    }else if (event== SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        Toast.makeText(getApplicationContext(),"正在获取验证码",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        ((Throwable )data).printStackTrace();
                    }
                }
            }
        }
    };
    private boolean judgePhoneNums(String phoneNums){
        if (isMatchLength(phoneNums,11)&&isMobileNO(phoneNums)){
            return true;
        }
        Toast.makeText(this, "手机号码输入有误！",Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean isMobileNO(String phoneNums) {
        String telRegex="[1][358]\\d{9}";
        if(TextUtils.isEmpty(phoneNums)){
            return false;
        }else
        {return phoneNums.matches(telRegex);}
    }

    private boolean isMatchLength(String phoneNums, int i) {
        if (phoneNums.isEmpty()){
            return false;
        }else {
            return phoneNums.length()==i?true:false;}
    }
    protected void onDestroy() {
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }

}
