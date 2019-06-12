package com.example.asus.xiaoliu.entity;

import cn.bmob.v3.BmobObject;

public class Comment extends BmobObject {

    private User commentUser;//评论人
    private  Dynamic commentDynamic;//评论的动态
    private  String  commentContent;//评论的内容
    private  String commentPhoneType;

    public String getCommentPhoneType() {
        return commentPhoneType;
    }

    public Comment setCommentPhoneType(String commentPhoneType) {
        this.commentPhoneType = commentPhoneType;
        return this;
    }

    public User getCommentUser() {
        return commentUser;
    }

    public Comment setCommentUser(User commentUser) {
        this.commentUser = commentUser;
        return this;
    }

    public Dynamic getCommentDynamic() {
        return commentDynamic;
    }

    public Comment setCommentDynamic(Dynamic commentDynamic) {
        this.commentDynamic = commentDynamic;
        return this;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public Comment setCommentContent(String commentContent) {
        this.commentContent = commentContent;
        return this;
    }

}
