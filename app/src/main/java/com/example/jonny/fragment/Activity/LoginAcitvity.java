package com.example.jonny.fragment.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jonny.fragment.HttPConnect;
import com.example.jonny.fragment.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by jonny on 2016/7/9.
 */
public class LoginAcitvity extends Activity{
    private EditText et_number;
    private EditText et_password;
    private Button btn_login;
    private CheckBox ck_re;
    private CheckBox ck_auto;
    private TextView tv_jumpmain;
    private TextView tv_signup;
    private SharedPreferences sp;
    private String userNameValue,passwordValue;
    int u_type,status;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginview);

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

        et_number=(EditText)findViewById(R.id.et_phone);
        et_password=(EditText)findViewById(R.id.et_pwd);
        btn_login=(Button)findViewById(R.id.login_commit_btn);
        ck_re=(CheckBox)findViewById(R.id.ck_re);
        ck_auto=(CheckBox)findViewById(R.id.ck_autologin);
        tv_jumpmain=(TextView)findViewById(R.id.tv_jumpmain);
        tv_signup=(TextView)findViewById(R.id.tv_signup);
        textviewClick();


        sp=getSharedPreferences("userInfo",MODE_PRIVATE);
        String name=sp.getString("phone","");
        final String password=sp.getString("password","");
        boolean choseRemenber=sp.getBoolean("remember",false);
        boolean choseAutoLogin=sp.getBoolean("autologin",false);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNameValue=et_number.getText().toString();
                passwordValue=et_password.getText().toString();
                SharedPreferences.Editor editor=sp.edit();
                String path="http://192.168.200.101:9999/TrainClass/student/user/login.do";
                String url= null;
                try {
                    url = path+"?phone="+ URLEncoder.encode(userNameValue,"utf-8")+"&password="
                            +URLEncoder.encode(passwordValue,"utf-8");
                    byte[] data= HttPConnect.readParse(url);
                    JSONObject job=new JSONObject(new String(data));
                    String result = job.getString("message");
                    status=job.getInt("status");
                    if (status==1){
                        Toast.makeText(LoginAcitvity.this,result,Toast.LENGTH_SHORT).show();
                        editor.putString("phone",userNameValue);
                        editor.putString("password",passwordValue);
                        if (ck_re.isChecked()){
                            editor.putBoolean("remember",true);
                        }else{
                            editor.putBoolean("remember",false);
                        }
                        if (ck_auto.isChecked()){
                            editor.putBoolean("autologin",true);
                        }else {
                            editor.putBoolean("autologin",false);
                        }
                        editor.commit();
                        Intent intent=new Intent(LoginAcitvity.this,MainActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("phone",userNameValue);
                        bundle.putInt("status",status);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else {
                        Toast.makeText(LoginAcitvity.this,"用户名或密码错误，请重新登录!",Toast.LENGTH_SHORT).show();

                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

             /*   new Thread(){
                    public void run(){

                        try {

                            URL url=new URL(urlpath);
                            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                            conn.setRequestMethod("GET");
                            conn.setConnectTimeout(5000);

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
               /* if (userNameValue.equals("123456")&&passwordValue.equals("123456")){
                    Toast.makeText(LoginAcitvity.this,"success",Toast.LENGTH_SHORT).show();
                    editor.putString("username",userNameValue);
                    editor.putString("password",passwordValue);
                    if (ck_re.isChecked()){
                        editor.putBoolean("remember",true);
                    }else{
                        editor.putBoolean("remember",false);
                    }
                    if (ck_auto.isChecked()){
                        editor.putBoolean("autologin",true);
                    }else {
                        editor.putBoolean("autologin",false);
                    }
                    editor.commit();
                    Intent intent=new Intent(LoginAcitvity.this,MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(LoginAcitvity.this,"用户名或密码错误，请重新登录!",Toast.LENGTH_SHORT).show();
                }*/

            }
        });
        if (choseRemenber){
            et_number.setText(name);
            et_password.setText(password);
            ck_re.setChecked(true);
        }
        if (choseAutoLogin){
            status=1;
            Intent intent=new Intent(LoginAcitvity.this,MainActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("phone",userNameValue);
          bundle.putInt("status",status);
            intent.putExtras(bundle);
            startActivity(intent);
        }


    }

    private void textviewClick() {
        tv_jumpmain.setClickable(true);
        tv_signup.setClickable(true);
        tv_jumpmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginAcitvity.this,MainActivity.class);
                Bundle bundle=new Bundle();
                userNameValue=null;
                status=0;
                bundle.putString("phone",userNameValue);
                bundle.putInt("status",status);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginAcitvity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
    }


}
