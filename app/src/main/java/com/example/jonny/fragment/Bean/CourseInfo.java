package com.example.jonny.fragment.Bean;

import java.io.Serializable;

/**
 * Created by jonny on 2016/7/27.
 */
public class CourseInfo implements Serializable{
    private String cp_id;
    private String cp_name;
    private String cp_icon;
    private String cp_info;
    private String cp_hour;
    private String cp_stdprice;
    private String cp_stuprice;
    private String cp_specprice;
    private String cp_maintm;
    private String cp_refertm;

    public String getCp_refertm() {
        return cp_refertm;
    }

    public void setCp_refertm(String cp_refertm) {
        this.cp_refertm = cp_refertm;
    }

    public String getCp_maintm() {
        return cp_maintm;
    }

    public void setCp_maintm(String cp_maintm) {
        this.cp_maintm = cp_maintm;
    }

    public String getCp_specprice() {
        return cp_specprice;
    }

    public void setCp_specprice(String cp_specprice) {
        this.cp_specprice = cp_specprice;
    }

    public String getCp_stuprice() {
        return cp_stuprice;
    }

    public void setCp_stuprice(String cp_stuprice) {
        this.cp_stuprice = cp_stuprice;
    }

    public String getCp_stdprice() {
        return cp_stdprice;
    }

    public void setCp_stdprice(String cp_stdprice) {
        this.cp_stdprice = cp_stdprice;
    }

    public String getCp_hour() {
        return cp_hour;
    }

    public void setCp_hour(String cp_hour) {
        this.cp_hour = cp_hour;
    }

    public String getCp_info() {
        return cp_info;
    }

    public void setCp_info(String cp_info) {
        this.cp_info = cp_info;
    }

    public String getCp_icon() {
        return cp_icon;
    }

    public void setCp_icon(String cp_icon) {
        this.cp_icon = cp_icon;
    }

    public String getCp_name() {
        return cp_name;
    }

    public void setCp_name(String cp_name) {
        this.cp_name = cp_name;
    }

    public String getCp_id() {
        return cp_id;
    }

    public void setCp_id(String cp_id) {
        this.cp_id = cp_id;
    }
}
