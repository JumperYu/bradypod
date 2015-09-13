package com.bradypod.shop.item.center.po;

import java.util.Date;

public class ItemInfo {
    private Long id;

    private Long user_id;

    private Byte item_type;

    private Long ctg_id;

    private String title;

    private String pic_url_list;

    private String description;

    private String attri_json;

    private Long price;

    private Byte status;

    private Date approval_time;

    private Date create_time;

    private Date update_time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Byte getItem_type() {
        return item_type;
    }

    public void setItem_type(Byte item_type) {
        this.item_type = item_type;
    }

    public Long getCtg_id() {
        return ctg_id;
    }

    public void setCtg_id(Long ctg_id) {
        this.ctg_id = ctg_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getPic_url_list() {
        return pic_url_list;
    }

    public void setPic_url_list(String pic_url_list) {
        this.pic_url_list = pic_url_list == null ? null : pic_url_list.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getAttri_json() {
        return attri_json;
    }

    public void setAttri_json(String attri_json) {
        this.attri_json = attri_json == null ? null : attri_json.trim();
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getApproval_time() {
        return approval_time;
    }

    public void setApproval_time(Date approval_time) {
        this.approval_time = approval_time;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}