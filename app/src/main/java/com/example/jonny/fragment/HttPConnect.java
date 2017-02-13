package com.example.jonny.fragment;



import com.example.jonny.fragment.Bean.Course;
import com.example.jonny.fragment.Bean.CourseInfo;
import com.example.jonny.fragment.Bean.UserInfo;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonny on 2016/7/8.
 */
public class HttPConnect {
    //String url ="http://192.168.200.101:9999/TrainClass//student/cp/getCpInfo.do";

/* public  List<Course> getCourseList(){
       List<Course> courselist=null;
       String url ="http://192.168.200.101:9999/TrainClass//student/cp/getCpInfo.do";

           HttpPost request= new HttpPost();
     try {
         HttpResponse response=new DefaultHttpClient().execute(request);
         if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
             String str=EntityUtils.toString(response.getEntity());
             courselist=getCourseList(str);
         }
     } catch (IOException e) {
         e.printStackTrace();
     }


     return courselist;


   }

    private List<Course> getCourseList(String str) {
        List<Course> courselist= new ArrayList<Course>();
        //byte[] data =readParse(str);
        try {
            JSONArray jay= new JSONArray(str);
            for(int i=0;i<jay.length();i+=1){
                JSONObject temp=(JSONObject)jay.get(i);
                Course course=new Course();
                course.setCname(temp.getString("cp_coursename"));
                course.setCteacher(temp.getString("cp_teachername"));
                course.setCplace(temp.getString("cp_locaname"));
                course.setCstartdata(temp.getString("cp_starttime"));
                course.setCenddata(temp.getString("cp_endtime"));
                course.setCtime(temp.getString("cp_timename"));
                course.setCschtime(temp.getString("cp_schtime"));
                courselist.add(course);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return courselist;
    }*/

    public static List<Course> getCourseList(String url) throws Exception
    { byte[] data=readParse(url);
        //
        List<Course> courselist = new ArrayList<Course>();
        JSONObject job=new JSONObject(new String(data));
        String result = job.getString("list");
        JSONArray array = new JSONArray(new String(result));

        //从JSON数组对象读取数据
        for(int i=0;i<array.length();i++){
            JSONObject temp=array.getJSONObject(i);
            Course course=new Course();
            course.setCname(temp.getString("cp_coursename"));
            course.setCteacher(temp.getString("cp_teachername"));
            course.setCplace(temp.getString("cp_locaname"));
            course.setCstartdata(temp.getString("cp_starttime"));
            course.setCenddata(temp.getString("cp_endtime"));
            course.setCtime(temp.getString("cp_timename"));
            course.setCschtime(temp.getString("cp_schtime"));
            course.setCid(temp.getString("cp_id"));
            course.setCinfo(temp.getString("cp_info"));
            course.setCnumber(temp.getInt("cp_studamount"));
            course.setCpnumber(temp.getInt("cp_calculate"));
            courselist.add(course);
        }
        return courselist;
       /* //
        String path = "http://192.168.200.101:9999/TrainClass//student/cp/getCpInfo.do";
        //建立一个URL对象
        URL url = new URL(path);
        //得到打开的链接对象
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置请求超时与请求方式
        conn.setReadTimeout(5*1000);
        conn.setRequestMethod("GET");*/
        //从链接中获取一个输入流对象
       // InputStream inStream = conn.getInputStream();
        //调用数据流处理方法
       // byte[] data = StreamTool.readInputStream(inStream);

        //String json = new String(data);
        //构建JSON数组对象
       // JSONArray array = new JSONArray(new String(data));
       // JSONArray array1=array.getJSONArray(1);

    }
    public static UserInfo getUserInfo(String url) throws Exception{
        byte[] data=readParse(url);
      // UserInfo userInfos=new UserInfo();
        // List<UserInfo> userInfos=new ArrayList<UserInfo>();

        //String result=job.getString("userinfo");
       // JSONArray array=new JSONArray(new String(result));
        //JSONArray array=job.getJSONArray("userinfo");
        JSONObject job=new JSONObject(new String(data));
        String message=job.getString("message");
        JSONObject temp=job.getJSONObject("userinfo");
        int status=job.getInt("status");

     // for (int i=0;i<array.length();i++){
      // JSONObject temp=job.getJSONObject("user");
            UserInfo userInfo= new UserInfo();
            userInfo.setMessage(message);
            userInfo.setStatus(status);
            userInfo.setSc_id(temp.getString("scId"));
            userInfo.setSc_name(temp.getString("scName"));
            userInfo.setSc_idcardno(temp.getString("scIdcardno"));
            userInfo.setSc_phone(temp.getString("scZhitel"));
            userInfo.setSc_qq(temp.getString("scQq"));
            userInfo.setSc_email(temp.getString("scEmail"));
            userInfo.setSc_history(temp.getString("scHiststudy"));
            userInfo.setSc_gender(temp.getString("scGender"));
           // userInfos.add(userInfo);
      //  }
        return userInfo;

    }
    public static CourseInfo getCourseInfo(String url) throws Exception{
        CourseInfo courseInfo=new CourseInfo();
        byte[] data=readParse(url);

        JSONArray array=new JSONArray(new String(data));
        JSONObject job=array.getJSONObject(0);
        courseInfo.setCp_id(job.getString("cp_id"));
        courseInfo.setCp_name(job.getString("cp_name"));
        courseInfo.setCp_info(job.getString("cp_info"));
        //courseInfo.setCp_icon(job.getString("cp_icon"));
        courseInfo.setCp_hour(job.getString("cp_hour"));
        courseInfo.setCp_stdprice(job.getString("cp_stdprice"));
        courseInfo.setCp_stuprice(job.getString("cp_stuprice"));
        courseInfo.setCp_specprice(job.getString("cp_speprice"));
        courseInfo.setCp_maintm(job.getString("cp_maintm"));
        courseInfo.setCp_refertm(job.getString("cp_refertm"));
        return courseInfo;



    }

    public static byte[] readParse(String url)throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {

            URL path=new URL(url);
            HttpURLConnection conn=(HttpURLConnection)path.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            InputStream inStream=conn.getInputStream();

            while((len=inStream.read(data))!=-1){
                outStream.write(data,0,len);
            }
            inStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outStream.toByteArray();
    }


}



