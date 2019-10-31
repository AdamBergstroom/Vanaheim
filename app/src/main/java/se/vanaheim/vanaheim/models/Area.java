package se.vanaheim.vanaheim.models;

public class Area {

    private String areaName;
    private int areaObjectTypeNumber;
    private String areObjectTypeName;

    private int numberOfObject;

    private double latitude;
    private double longitude;


    public Area() {
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getAreaObjectTypeNumber() {
        return areaObjectTypeNumber;
    }

    public void setAreaObjectTypeNumber(int areaObjectType) {
        this.areaObjectTypeNumber = areaObjectType;
    }

    public String getAreObjectTypeName() {
        return areObjectTypeName;
    }

    public void setAreObjectTypeName(String areObjectTypeName) {
        this.areObjectTypeName = areObjectTypeName;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getLatitudeAsString() {
        String lang = Double.toString(latitude);
        return lang;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getLongitudeAsString() {
        String lon = Double.toString(longitude);
        return lon;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getNumberOfObject() {
        return numberOfObject;
    }

    public void setNumberOfObject(int numberOfObjects) {
        this.numberOfObject = numberOfObjects;
    }
}
