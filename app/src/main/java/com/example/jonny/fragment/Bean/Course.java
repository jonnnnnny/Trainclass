package com.example.jonny.fragment.Bean;

import java.io.Serializable;

/**
 * Created by jonny on 2016/7/8.
 */
public class Course implements Serializable{
    private String cid;//课程id
    private String cname;//课程名称
    private String cteacher;//任课教师
    private String cplace;//上课地点
    private String cstartdata;//开课时间
    private String cenddata;//结课时间
    private String ctime;//上课日期
    private String cschtime;//上课时间
    private String  cinfo;
    private int cnumber;
    private int cpnumber;


    public void setCname (String cname){
       this.cname=cname;
    }
    public String getCname(){
        return cname;
    }

    public String getCteacher() {
        return cteacher;
    }

    public void setCteacher(String cteacher) {
        this.cteacher = cteacher;
    }

    public String getCplace() {
        return cplace;
    }

    public void setCplace(String cplace) {
        this.cplace = cplace;
    }

    public String getCstartdata() {
        return cstartdata;
    }

    public void setCstartdata(String cstartdata) {
        this.cstartdata = cstartdata;
    }

    public String getCenddata() {
        return cenddata;
    }

    public void setCenddata(String cenddata) {
        this.cenddata = cenddata;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getCschtime() {
        return cschtime;
    }

    public void setCschtime(String cschtime) {
        this.cschtime = cschtime;
    }


    public String getCinfo() {
        return cinfo;
    }

    public void setCinfo(String cinfo) {
        this.cinfo = cinfo;
    }

    public int getCnumber() {
        return cnumber;
    }

    public void setCnumber(int cnumber) {
        this.cnumber = cnumber;
    }

    public int getCpnumber() {
        return cpnumber;
    }

    public void setCpnumber(int cpnumber) {
        this.cpnumber = cpnumber;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
