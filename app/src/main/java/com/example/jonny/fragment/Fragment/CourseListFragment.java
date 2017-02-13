package com.example.jonny.fragment.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.jonny.fragment.Bean.Course;
import com.example.jonny.fragment.Activity.DetailActivity;
import com.example.jonny.fragment.HttPConnect;
import com.example.jonny.fragment.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jonny on 2016/7/12.
 */
public class CourseListFragment extends Fragment {
    private ListView listView;
    private View view;
    List<Course> courseList;

    String phone;
    int status=0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        view=inflater.inflate(R.layout.courselist,container,false);

        if (view!=null){
            initView();
        }
        return view;
    }

    private void initView() {
        listView=(ListView)view.findViewById(R.id.cl_listview);

        String[] from={"cname","tname","place","cdata","ctime"};
        int[] to ={R.id.list_name,R.id.list_tname,R.id.list_locaname,R.id.list_data,R.id.list_time};
        final String url="http://192.168.200.101:9999/TrainClass/student/cp/getCpInfoWithCourse.do";
        try {
            courseList= HttPConnect.getCourseList(url);
            List<HashMap<String ,Object>> list= new ArrayList<HashMap<String,Object>>();
            for(int i=0;i<courseList.size();i++){
                HashMap<String,Object> map=new HashMap<String,Object>();
                Course course=courseList.get(i);

                map.put("cname",course.getCname());
                map.put("tname",course.getCteacher());
                map.put("place",course.getCplace());
                map.put("cdata",course.getCstartdata()+"-"+course.getCenddata());
                map.put("ctime",course.getCtime()+"-"+course.getCschtime());
                map.put("cid",course.getCid());
                map.put("cnumber",course.getCnumber());
                map.put("pnumber",course.getCpnumber());
                map.put("cinfo",course.getCinfo());
                list.add(map);
            }
            SimpleAdapter adapter =new SimpleAdapter(getActivity(),list,R.layout.class_item,from,to);
            listView.setAdapter(adapter);

        } catch (Exception e) {
            Toast.makeText(getActivity(), "获取课程失败",Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", e.toString());
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle=getArguments();
                phone=bundle.getString("phone");
                status=bundle.getInt("status");
                Intent intent=new Intent(getActivity(),DetailActivity.class);
                Course course=courseList.get(i);
                Bundle bundle1=new Bundle();
                bundle1.putSerializable("course",(Serializable)course);
                bundle1.putString("phone",phone);
                bundle1.putInt("status",status);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
    }





}
