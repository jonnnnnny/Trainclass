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

import com.example.jonny.fragment.HttPConnect;
import com.example.jonny.fragment.R;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;

/**
 * Created by jonny on 2016/7/18.
 */
public class SetPwdActivity extends Activity{
    private EditText et_pwd;
    private String phone,pwd;
    private Button btn_setpwd;
    int status=0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setpwdview);

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

        final Intent intent=getIntent();
        phone=intent.getStringExtra("phone");
        btn_setpwd=(Button)findViewById(R.id.btn_setpwd);
        et_pwd=(EditText)findViewById(R.id.et_pwd);
       // TextView test12=(TextView)findViewById(R.id.test12);
        //test12.setText(phone);
        btn_setpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwd=et_pwd.getText().toString().trim();
                try {
                    String path="http://192.168.200.101:9999/TrainClass/student/user/register.do" +
                            "?phone="+ URLEncoder.encode(phone,"utf-8")+"&password="+URLEncoder.encode(pwd,"utf-8");
                    byte[] jsondata= HttPConnect.readParse(path);
                    JSONObject job=new JSONObject(new String(jsondata));
                    String message=job.getString("message");
                    int status=job.getInt("status");
                    if (status==1){
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                        Intent intent1=new Intent(SetPwdActivity.this,MainActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("status",status);
                        bundle.putString("phone",phone);
                        intent1.putExtras(bundle);


                        startActivity(intent1);

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
                }
            }
        });

    }
}
