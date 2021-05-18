package com.example.e_auctionfinal;

public class Items {
    String name,desc,baseprice,availablity,currentbid,url,longitude,latitude,placename,owner;
    public Items()
    {

    }

    public Items(String name, String desc, String baseprice, String availablity,String owner
                 ,String currentbid, String url, String longitude,String latitude,String placename) {
        this.name = name;
        this.desc = desc;
        this.baseprice = baseprice;
        this.availablity = availablity;
        this.currentbid = currentbid;
        this.owner = owner;
        this.url = url;
        this.longitude = longitude;
        this.latitude = latitude;
        this.placename = placename;
    }

    public String getPlacename() {
        return placename;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setPlacename(String placename) {
        this.placename = placename;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBaseprice() {
        return baseprice;
    }

    public void setBaseprice(String baseprice) {
        this.baseprice = baseprice;
    }

    public String getAvailablity() {
        return availablity;
    }

    public void setAvailablity(String availablity) {
        this.availablity = availablity;
    }

    public String getCurrentbid() {
        return currentbid;
    }

    public void setCurrentbid(String currentbid) {
        this.currentbid = currentbid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
