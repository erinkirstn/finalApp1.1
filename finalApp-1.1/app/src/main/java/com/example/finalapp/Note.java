package com.example.finalapp;

public class Note {
    private String title;
    private String description;
    private String date;
    private String user;
    private String documentId;


    public Note() {
        //Firebase always need a public no-arg constructor
    }

    public Note(String title, String description, String date, String user) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.user = user;

    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public void setTitle(java.lang.String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
