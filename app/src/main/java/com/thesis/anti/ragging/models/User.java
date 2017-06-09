package com.thesis.anti.ragging.models;


public class User {

    private String name;
    private String email;
    private String unique_id;
    private String password;
    private String old_password;
    private String new_password;
    private String discipline;
    private String institute;
    private String student_id;
    private String author1;
    private String author2;
    private String author3;


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAuthor1() {
        return author1;
    }

    public String getAuthor2() {
        return author2;
    }

    public String getAuthor3() {
        return author3;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public void setNew_author1(String author1) {
        this.author1 = author1;
    }

    public void setDiscipline(String discipline) { this.discipline = discipline; }

    public void setInstitute(String institute) { this.institute = institute; }

    public void setStudent_id(String student_id) { this.student_id = student_id; }

    public void setAuthor1(String author1) { this.author1 = author1; }

    public void setAuthor2(String author2) { this.author2 = author2; }

    public void setAuthor3(String author3) { this.author3 = author3; }
}
