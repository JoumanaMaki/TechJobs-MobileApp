package com.example.sweetshopadmin;

public class JobClass {

    private int ID;
    private String Title;
    private String CompanyName;
    private String CompanyNum;
    private String CompanyEmail;
    private String Requirements;
    private String Objectives;
    private String Type;
    private String Category;
    private String Image;
    public int publish;
    public JobClass(int ID, String Title, String CompanyName,String CompanyNum,String CompanyEmail,String Requirements,String Objectives,String Type, String Category,int publish,String Image){

        this.ID = ID;
        this.Title = Title;
        this.CompanyName = CompanyName;
        this.CompanyNum = CompanyNum;
        this.CompanyEmail = CompanyEmail;
        this.Objectives = Objectives;
        this.Type = Type;
        this.Category = Category;
        this.Requirements = Requirements; this.publish = publish;
        this.Image = Image;
    }

    public String getImageurl() {
        return Image;
    }

    public String toString(){
        return "Title: "+ Title + "\nCompany Name: "+ CompanyName+ "\nJob Type: "+ Type  ;
    }
}
