package com.opp.fangla.terznica.data.entities;


public class Report {
    //createReport(idKorisnika, tipPrijave, idPrijavljeneStavke,
    // idVrstePrijave, komentar) -> (idPrijave)


    private int userId, reportedItemId, reportId;
    private String comment;
    private int ReportType;// tipPrijave = { 0 = korisnik, 1 = proizvod, 2 = ocjena/komentar}
    private int ReportTypeId;//idVrstePrijave = { 0 = govorMržnje,
                            // 1 = neprimjerenSadržaj, 2 = lažanProfil,
                            // 3 = nevaljanOglas}

    public Report(int userId, int reportedItemId, int reportId, String comment, int reportType, int reportTypeId) {
        this.userId = userId;
        this.reportedItemId = reportedItemId;
        this.reportId = reportId;
        this.comment = comment;
        ReportType = reportType;
        ReportTypeId = reportTypeId;
    }

    public Report (){}

    public int getReportTypeId() {
        return ReportTypeId;
    }

    public void setReportTypeId(int reportTypeId) {
        ReportTypeId = reportTypeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getReportedItemId() {
        return reportedItemId;
    }

    public void setReportedItemId(int reportedItemId) {
        this.reportedItemId = reportedItemId;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getReportType() {
        return ReportType;
    }

    public void setReportType(int reportType) {
        ReportType = reportType;
    }

}
