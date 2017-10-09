package de.meetme;

/**
 * Created by skiera on 09.10.2017.
 */

public class UserLocation {

        float lat;
        float lng;
        String userID;

        public UserLocation(){};

        public UserLocation(float lat, float lng, String userID) {

            this.lat = lat;
            this.lng = lng;
            this.userID = userID;
        }

        public float getLat() {
            return lat;
        }

        public float getLng() {
            return lng;
        }

        public String getUserID() {
            return userID;
        }
    }


