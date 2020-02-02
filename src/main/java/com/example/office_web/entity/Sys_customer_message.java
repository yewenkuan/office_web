package com.example.office_web.entity;

import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author ywk
 * @since 2020-02-01
 */
public class Sys_customer_message extends Model<Sys_customer_message> {

    private static final long serialVersionUID = 1L;

    private User user;

    private String id;

    private String user_id;

    private String message;

    private Integer flag;

    private Date create_time;

    private String shop_id;

    private String phone;

    private String userName;

    private String picUrl;

    private List<String> picUrlList;//图片的list形式，用来给前段显示

    private Sys_reply_message sys_reply_message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getMessage() {
        return message;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public List<String> getPicUrlList() {
        return picUrlList;
    }

    public void setPicUrlList(List<String> picUrlList) {
        this.picUrlList = picUrlList;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }


    public Sys_reply_message getSys_reply_message() {
        return sys_reply_message;
    }

    public void setSys_reply_message(Sys_reply_message sys_reply_message) {
        this.sys_reply_message = sys_reply_message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Sys_customer_message{" +
        "id=" + id +
        ", user_id=" + user_id +
        ", message=" + message +
        ", flag=" + flag +
        ", create_time=" + create_time +
        ", shop_id=" + shop_id +
        "}";
    }
}
