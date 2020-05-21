package com.durumluemrullah.mobilappproje.Classes;

import com.google.firebase.firestore.FieldValue;

public class Comment {

    private String postId;
    private String commenter;
    private String comment;
    private FieldValue fieldValue;


    public Comment(String postId, String commenter, String comment, FieldValue fieldValue) {
        this.postId = postId;
        this.commenter = commenter;
        this.comment = comment;
        this.fieldValue = fieldValue;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public FieldValue getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(FieldValue fieldValue) {
        this.fieldValue = fieldValue;
    }
}
