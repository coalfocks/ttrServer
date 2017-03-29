package com.example.tyudy.ticket2rideclient.common;
import java.io.Serializable;

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

    public void setSource(String source) {
        this.source = source;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }
    @Override
    public String toString() {
        return "Source: " + source + ", Dest: " + dest;
    }
}
