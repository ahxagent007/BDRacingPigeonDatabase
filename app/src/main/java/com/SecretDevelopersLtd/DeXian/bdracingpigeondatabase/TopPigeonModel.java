package com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase;

public class TopPigeonModel {

    String PigeonRingNumber;
    String OwnerName;
    int PositionLessThenFifty;
    String ClubName;

    public TopPigeonModel() {
    }

    public String getPigeonRingNumber() {
        return PigeonRingNumber;
    }

    public void setPigeonRingNumber(String pigeonRIngNumber) {
        PigeonRingNumber = pigeonRIngNumber;
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public void setOwnerName(String ownerName) {
        OwnerName = ownerName;
    }

    public int getPositionLessThenFifty() {
        return PositionLessThenFifty;
    }

    public void setPositionLessThenFifty(int positionLessThenFifty) {
        PositionLessThenFifty = positionLessThenFifty;
    }

    public String getClubName() {
        return ClubName;
    }

    public void setClubName(String clubName) {
        ClubName = clubName;
    }
}
