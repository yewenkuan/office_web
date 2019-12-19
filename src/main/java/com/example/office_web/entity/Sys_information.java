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
 * @since 2019-12-19
 */
public class Sys_information extends Model<Sys_information> {

    private static final long serialVersionUID = 1L;

    private String id;

    private String title;

    private String content;

    private String cover_url;

    private Integer Information_type;

    private Integer collect_number;

    private Integer like_number;

    private Integer browse_number;

    private Integer Information_sort;

    private Date create_date;

    private Date update_date;

    private String create_by;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getCover_url() {
        return cover_url;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }
    public Integer getInformation_type() {
        return Information_type;
    }

    public void setInformation_type(Integer Information_type) {
        this.Information_type = Information_type;
    }
    public Integer getCollect_number() {
        return collect_number;
    }

    public void setCollect_number(Integer collect_number) {
        this.collect_number = collect_number;
    }
    public Integer getLike_number() {
        return like_number;
    }

    public void setLike_number(Integer like_number) {
        this.like_number = like_number;
    }
    public Integer getBrowse_number() {
        return browse_number;
    }

    public void setBrowse_number(Integer browse_number) {
        this.browse_number = browse_number;
    }
    public Integer getInformation_sort() {
        return Information_sort;
    }

    public void setInformation_sort(Integer Information_sort) {
        this.Information_sort = Information_sort;
    }
    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }
    public Date getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Date update_date) {
        this.update_date = update_date;
    }
    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Sys_information{" +
        "id=" + id +
        ", title=" + title +
        ", content=" + content +
        ", cover_url=" + cover_url +
        ", Information_type=" + Information_type +
        ", collect_number=" + collect_number +
        ", like_number=" + like_number +
        ", browse_number=" + browse_number +
        ", Information_sort=" + Information_sort +
        ", create_date=" + create_date +
        ", update_date=" + update_date +
        ", create_by=" + create_by +
        "}";
    }
}
