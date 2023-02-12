package com.example.sweetshopadmin;

public class Applicant {

    private int ApplicantID;
    private String Name;
    private String Phone;
    private String Email;
    public String image;
    public String jobid;

    public Applicant(int ApplicantID, String Name, String Phone,String Email, String image,String jobid){

        this.ApplicantID = ApplicantID;
        this.Name = Name;
        this.Phone = Phone;
        this.Email = Email;
        this.image = image;
        this.jobid=jobid;
    }

    public String getImageurl() {
        return image;
    }

    public String toString(){
        return "Name: "+ Name + "\nEmail: "+ Email  ;
    }
}
