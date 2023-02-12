package com.example.sweetshopadmin;

public class TestimonialClass {

    private int testimonialId;
    private String Name;
    private String Text;
    private String Image;

    public int publish;
    public TestimonialClass(int testimonialId, String Name, String Text,String Image, int publish){

        this.testimonialId = testimonialId;
        this.Name = Name;
        this.Text = Text;
        this.Image = Image;
        this.publish = publish;

    }

    public String getImageurl() {
        return Image;
    }

    public String toString(){
        return "\nName: "+ Name + "\nSubject: "+ "Testimonial's Feedback\n" ;
    }
}
