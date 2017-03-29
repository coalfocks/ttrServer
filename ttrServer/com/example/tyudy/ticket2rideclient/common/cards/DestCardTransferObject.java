package com.example.tyudy.ticket2rideclient.common.cards;

import java.io.Serializable;

/**
 * Created by colefox on 3/28/17.
 */

public class DestCardTransferObject implements Serializable {
    private String src;
    private String dest;
    private int pts;

    public String getSrc()
    {
        return src;
    }

    public void setSrc(String src)
    {
        this.src = src;
    }

    public String getDest()
    {
        return dest;
    }

    public void setDest(String dest)
    {
        this.dest = dest;
    }

    public int getPts()
    {
        return pts;
    }

    public void setPts(int pts)
    {
        this.pts = pts;
    }
}
