package com.example.tyudy.ticket2rideclient.common;
import java.io.Serializable;

import java.io.Serializable;

<<<<<<< HEAD
public class Destination implements Serializable
{
    String source;
    String dest;
=======
public class Destination implements Serializable {
    private String source;
    private String dest;
>>>>>>> oogy-boogy-2

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

<<<<<<< HEAD
    public void setSource(String source) {
        this.source = source;
    }

    public void setDest(String dest) {
        this.dest = dest;
=======
    public void setSource(String src) { source = src; }

    public void setDest(String dst) { dest = dst; }

    @Override
    public String toString() {
        return "Source: " + source + ", Dest: " + dest;
>>>>>>> oogy-boogy-2
    }
//
//    @Override
//    public String toString() {
//        return "Source: " + source + ", Dest: " + dest;
//    }
}
