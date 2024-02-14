package com.example.workshop6;

public class TripType {
    private String triptypeId;
    private String triptypeName;

    public String getTriptypeId() {
        return triptypeId;
    }

    public void setTriptypeId(String triptypeId) {
        this.triptypeId = triptypeId;
    }

    public String getTriptypeName() {
        return triptypeName;
    }

    public void setTriptypeName(String triptypeName) {
        this.triptypeName = triptypeName;
    }

    public TripType(String triptypeId, String triptypeName) {
        this.triptypeId = triptypeId;
        this.triptypeName = triptypeName;
    }

}
