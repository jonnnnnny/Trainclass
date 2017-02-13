package com.example.jonny.fragment.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jonny.fragment.Fragment.CourseListFragment;
import com.example.jonny.fragment.Fragment.PlanCourseFragment;
import com.example.jonny.fragment.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout coursetv;
    private LinearLayout historycoursetv;
    private CourseListFragment courseListFragment;
    private PlanCourseFragment planCourseFragment;

    private ImageButton img_search;
    private ImageButton img_login;
    private static boolean isExit=false;
    int status=0;
    String userphone;

     Handler mHandler=new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            isExit=false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        img_search=(ImageButton)findViewById(R.id.img_search);
        img_search.setOnClickListener(this);
        img_login=(ImageButton)findViewById(R.id.login);
        img_login.setOnClickListener(this);
        coursetv=(LinearLayout)findViewById(R.id.courselisttv);
        historycoursetv=(LinearLayout)findViewById(R.id.historycoursetv);
        coursetv.setOnClickListener(this);
        historycoursetv.setOnClickListener(this);

        //接收登陆用户的手机号和状态
        Bundle bundle1=getIntent().getExtras();
        status=bundle1.getInt("status");
        userphone=bundle1.getString("phone");


    }

    @Override
    public void onClick(View view) {
        FragmentManager fm=getFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        switch (view.getId()){
            case R.id.courselisttv:
                setTabSelection(0);
                if (courseListFragment==null){
                    courseListFragment=new CourseListFragment();
                    Bundle bundle =new Bundle();
                    if (status==1){
                    bundle.putInt("status",status);
                    bundle.putString("phone",userphone);

                   }else{
                        status=0;
                       userphone="";
                    bundle.putInt("status",status);
                      bundle.putString("phone",userphone);
                    }
                    courseListFragment.setArguments(bundle);
                }
                transaction.replace(R.id.fl_content,courseListFragment);
               //coursetv.setBackgroundColor(0x990000FF);

                break;
            case R.id.historycoursetv:
                setTabSelection(1);
                if (planCourseFragment ==null){
                    planCourseFragment = new PlanCourseFragment();
                    Bundle bundle1=new Bundle();
                    if (status==1){
                        bundle1.putInt("status",status);
                        bundle1.putString("phone",userphone);
                    }else {
                        status=0;
                        userphone="";
                        bundle1.putInt("status",status);
                        bundle1.putString("phone",userphone);
                    }
                    planCourseFragment.setArguments(bundle1);
                }
                transaction.replace(R.id.fl_content, planCourseFragment);
                break;
            case R.id.img_search:

                Intent intent1=new Intent(MainActivity.this,SearchActivity.class);
                if (status==1){
                    Bundle bundle=new Bundle();
                    bundle.putString("phone",userphone);
                    bundle.putInt("status",status);
                    intent1.putExtras(bundle);
                }

                startActivity(intent1);
                break;
            case R.id.login:
                if (status==1){
                    Intent intent=new Intent(MainActivity.this,UserInfoActivity.class);
                    intent.putExtra("phone",userphone);
                    startActivity(intent);
                }
                else {
                Intent intent2=new Intent(MainActivity.this,LoginAcitvity.class);
                startActivity(intent2);}
                break;


        }
        transaction.commit();

    }

    private void setTabSelection(int i) {
        clearSelectiom();
        switch (i){
            case 0:
                coursetv.setBackgroundColor(0x990000FF);
                break;
            case 1:
                historycoursetv.setBackgroundColor(0x990000FF);
        }
    }

    private void clearSelectiom() {
        coursetv.setBackgroundColor(0xffffffff);
        historycoursetv.setBackgroundColor(0xffffffff);
    }
  /*  public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode==KeyEvent.KEYCODE_BACK){
            ToQuitTheApp();
            return false;
        }else {
            return super.onKeyDown(keyCode,event);
        }
    }

    private void ToQuitTheApp() {
        if (!isExit){
            isExit=true;
            Toast.makeText(getApplicationContext(),"再按一次退出程序",Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0,3000);
        }else {
            finish();
            System.exit(0);
        }
    }*/
}
