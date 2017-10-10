package de.meetme;

/**
 * Created by skiera on 09.10.2017.
 */

public class UserLocation {

    double lat;
    double lng;
    String userID;

    public UserLocation(){};

    public UserLocation(double lat, double lng, String userID) {

        this.lat = lat;
        this.lng = lng;
        this.userID = userID;

    }

    public double getLat() {
            return lat;
        }

    public double getLng() {
            return lng;
        }

    public String getUserID() {
            return userID;
        }

}


