package com.example.jonny.fragment.Bean;

import java.io.Serializable;

/**
 * Created by jonny on 2016/7/19.
 */
public class UserInfo implements Serializable{
    private String sc_id;
    private String sc_name;
    private String sc_gender;
    private String sc_idcardno;
    private String sc_phone;
    private String sc_email;
    private String sc_qq;
    private String sc_bg;
    private String sc_history;
    private String message;
    private int status;

    public String getSc_id() {
        return sc_id;
    }

    public void setSc_id(String sc_id) {
        this.sc_id = sc_id;
    }

    public String getSc_name() {
        return sc_name;
    }

    public void setSc_name(String sc_name) {
        this.sc_name = sc_name;
    }

    public String getSc_gender() {
        return sc_gender;
    }

    public void setSc_gender(String sc_gender) {
        this.sc_gender = sc_gender;
    }

    public String getSc_idcardno() {
        return sc_idcardno;
    }

    public void setSc_idcardno(String sc_idcardno) {
        this.sc_idcardno = sc_idcardno;
    }

    public String getSc_phone() {
        return sc_phone;
    }

    public void setSc_phone(String sc_phone) {
        this.sc_phone = sc_phone;
    }

    public String getSc_email() {
        return sc_email;
    }

    public void setSc_email(String sc_email) {
        this.sc_email = sc_email;
    }

    public String getSc_qq() {
        return sc_qq;
    }

    public void setSc_qq(String sc_qq) {
        this.sc_qq = sc_qq;
    }

    public String getSc_bg() {
        return sc_bg;
    }

    public void setSc_bg(String sc_bg) {
        this.sc_bg = sc_bg;
    }

    public String getSc_history() {
        return sc_history;
    }

    public void setSc_history(String sc_history) {
        this.sc_history = sc_history;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
