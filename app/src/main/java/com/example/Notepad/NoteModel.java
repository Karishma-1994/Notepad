package com.example.Notepad;

public class NoteModel {

    private int id;
    private String title;
    private String content;
    private boolean isChecked = false;

    public NoteModel(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "NoteModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }
}