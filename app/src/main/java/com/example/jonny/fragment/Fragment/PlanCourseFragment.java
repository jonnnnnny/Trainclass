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

import com.example.jonny.fragment.Activity.DetailActivity;
import com.example.jonny.fragment.Activity.PlancourseDetailActivity;
import com.example.jonny.fragment.Bean.Course;
import com.example.jonny.fragment.HttPConnect;
import com.example.jonny.fragment.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jonny on 2016/7/12.
 */
public class PlanCourseFragment extends Fragment{
    private ListView plistview;
    private View view;
    private List<Course> plancourse;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        view=inflater.inflate(R.layout.plancourse,container,false);
        if (view!=null){
            ininview();
        }

        return view;
    }

    private void ininview() {
        final String url="http://192.168.200.101:9999/TrainClass/student/cplan/getCplanInfo.do";
        String[] from={"pcname","ptname","pplace","pdate","ptime"};
        int[] to ={R.id.list_name,R.id.list_tname,R.id.list_locaname,R.id.list_data,R.id.list_time};
        plistview=(ListView)view.findViewById(R.id.pc_listview);

        try {
           plancourse= HttPConnect.getCourseList(url);
            List<HashMap<String ,Object>> list= new ArrayList<HashMap<String,Object>>();
            for(int i=0;i<plancourse.size();i++){
                HashMap<String,Object> map=new HashMap<String,Object>();
                Course course=plancourse.get(i);

                map.put("pcname",course.getCname());
                map.put("ptname",course.getCteacher());
                map.put("pplace",course.getCplace());
                map.put("pdate",course.getCstartdata()+"-"+course.getCenddata());
                map.put("ptime",course.getCtime()+"-"+course.getCschtime());
                map.put("pcid",course.getCid());
                map.put("pcnumber",course.getCnumber());
                map.put("ppnumber",course.getCpnumber());
                map.put("pcinfo",course.getCinfo());
                list.add(map);
            }
            SimpleAdapter adapter =new SimpleAdapter(getActivity(),list,R.layout.class_item,from,to);
            plistview.setAdapter(adapter);

        } catch (Exception e) {
            Toast.makeText(getActivity(), "获取课程失败",Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", e.toString());
        }
        plistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle=getArguments();
               String phone=bundle.getString("phone");
               int status=bundle.getInt("status");
                Intent intent=new Intent(getActivity(),PlancourseDetailActivity.class);
                Course course=plancourse.get(i);
                Bundle bundle1=new Bundle();
                bundle1.putSerializable("plancourse",(Serializable)course);
                bundle1.putString("phone",phone);
                bundle1.putInt("status",status);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });


    }
}
