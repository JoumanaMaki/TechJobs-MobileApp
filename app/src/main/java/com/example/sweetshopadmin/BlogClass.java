package com.example.sweetshopadmin;

public class BlogClass {

    private int blogID;
    private String Title;
    private String Text;
    private String Image;
    public String Author;
    public int publish;
    public BlogClass(int blogId, String BlogTitle, String BlogText,String BlogImg, int publish,String author){

        this.blogID = blogId;
        this.Title = BlogTitle;
        this.Text = BlogText;
        this.Image = BlogImg;
        this.Author = author; this.publish = publish;

    }

    public String getImageurl() {
        return Image;
    }

    public String toString(){
        return "Title: "+ Title + "\nAuthor: "+ Author  ;
    }
}
