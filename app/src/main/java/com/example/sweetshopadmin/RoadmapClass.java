package com.example.sweetshopadmin;

public class RoadmapClass {


    private int roadmapID;
    private String Title;
    private String Steps;
    private String Youtube;
    public String Image;
    public int publish;
    public RoadmapClass(int roadmapID, String Title, String Steps,String Youtube,String Image, int publish){

        this.roadmapID = roadmapID;
        this.Title = Title;
        this.Steps = Steps;
        this.Youtube = Youtube;
        this.Image = Image; this.publish = publish;

    }

    public String getImageurl() {
        return Image;
    }

    public String toString(){
        return "\nTitle: "+ Title + "\nSubject: "+ "Your way to success\n"  ;
    }
}
