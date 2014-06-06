package com.bandido;

/**
 * Created by sreeraja on 06/06/14.
 */
public class Channel {
    private int number;

    public Channel(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public Channel GetChannelAfter() {
        return new Channel(number+1);
    }

    public Channel GetChannelBefore() {
        return new Channel(number-1);
    }

    public boolean Equals(Channel other) {
        return number == other.getNumber();
    }
}
