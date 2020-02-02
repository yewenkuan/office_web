package com.example.office_web.entity;

import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ywk
 * @since 2020-02-01
 */
public class Sys_reply_message extends Model<Sys_reply_message> {

    private static final long serialVersionUID = 1L;

    private String id;

    private String shop_id;

    private String question;

    private String content;

    private Date create_date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Sys_reply_message{" +
        "id=" + id +
        ", shop_id=" + shop_id +
        ", question=" + question +
        ", create_date=" + create_date +
        "}";
    }
}
