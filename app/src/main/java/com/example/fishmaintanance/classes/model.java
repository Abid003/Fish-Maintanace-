package com.example.fishmaintanance.classes;

public class model
{
  String title,desc,purl;
    model()
    {

    }
    public model(String title, String desc, String purl) {
        this.title = title;
        this.desc = desc;
        this.purl = purl;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
}
