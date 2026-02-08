package com.example.berlinhotspots.dto;

public class HotspotDto {
    public int cluster;
    public int total;
    public int fatal;
    public int serious;
    public int slight;
    public int riskScore;

    public HotspotDto(int cluster, int total, int fatal, int serious, int slight) {
        this.cluster = cluster;
        this.total = total;
        this.fatal = fatal;
        this.serious = serious;
        this.slight = slight;
        this.riskScore = (fatal * 5) + (serious * 3) + (slight * 1);
    }
}
