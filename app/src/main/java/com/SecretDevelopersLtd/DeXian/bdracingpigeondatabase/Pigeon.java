package com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase;

public class Pigeon {

    private String PigeonRingNumber;
    private int Position;
    private String RaceName;
    private String OwnerName;
    private String ClubName;
    private float PigeonVelocity;
    private int TotalPigeons;
    private String RaceDate;

    public Pigeon(){}

    public String getPigeonRingNumber() {
        return PigeonRingNumber;
    }

    public void setPigeonRingNumber(String pigeonRingNumber) {
        PigeonRingNumber = pigeonRingNumber;
    }

    public int getPosition() {
        return Position;
    }

    public void setPosition(int position) {
        Position = position;
    }

    public String getRaceName() {
        return RaceName;
    }

    public void setRaceName(String raceName) {
        RaceName = raceName;
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public void setOwnerName(String ownerName) {
        OwnerName = ownerName;
    }

    public String getClubName() {
        return ClubName;
    }

    public void setClubName(String clubName) {
        ClubName = clubName;
    }

    public float getPigeonVelocity() {
        return PigeonVelocity;
    }

    public void setPigeonVelocity(float pigeonVelocity) {
        PigeonVelocity = pigeonVelocity;
    }

    public int getTotalPigeons() {
        return TotalPigeons;
    }

    public void setTotalPigeons(int totalPigeons) {
        TotalPigeons = totalPigeons;
    }

    public String getRaceDate() {
        return RaceDate;
    }

    public void setRaceDate(String raceDate) {
        RaceDate = raceDate;
    }
}
