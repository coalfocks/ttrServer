package com.example.tyudy.ticket2rideclient.common;

import java.io.Serializable;

public class Destination implements Serializable {
    private String source;
    private String dest;

    public Destination(String source, String dest) {
        this.source = source;
        this.dest = dest;
    }

    public String getSource() {
        return source;
    }

    public String getDest() {
        return dest;
    }

    public void setSource(String src) { source = src; }

    public void setDest(String dst) { dest = dst; }

    @Override
    public String toString() {
        return "Source: " + source + ", Dest: " + dest;
    }
}
