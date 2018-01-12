package com.opp.fangla.terznica.data.entities;

import java.util.Date;



public class Comment {

    private String comment, idReviewMark;
    private Integer idCommentGiver, idCommentReceiver, reviewMark;
    private Date time;

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setIdCommentGiver(Integer idCommentGiver) {
        this.idCommentGiver = idCommentGiver;
    }

    public void setIdCommentReceiver(Integer idCommentReceiver) {
        this.idCommentReceiver = idCommentReceiver;
    }

    public void setReviewMark(Integer reviewMark) {
        this.reviewMark = reviewMark;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getComment() {

        return comment;
    }

    public Integer getIdCommentGiver() {
        return idCommentGiver;
    }

    public Integer getIdCommentReceiver() {
        return idCommentReceiver;
    }

    public Integer getReviewMark() {
        return reviewMark;
    }

    public Date getTime() {
        return time;
    }

    public String getIdReviewMark() {
        return idReviewMark;
    }

    public void setIdReviewMark(String idReviewMark) {
        this.idReviewMark = idReviewMark;
    }
}
