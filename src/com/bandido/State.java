package com.bandido;

public class State{
    private Channel previousChannel = new Channel(0);
    private Channel currentChannel = new Channel(0);


    public Channel getPreviousChannel() {
        return previousChannel;
    }

    public void setPreviousChannel(Channel previousChannel) {
        this.previousChannel = previousChannel;
    }

    public Channel getCurrentChannel() {
        return currentChannel;
    }

    public void setCurrentChannel(Channel currentChannel) {
        this.currentChannel = currentChannel;
    }

    public void Swap() {
        Channel temporaryChannel = previousChannel;
        previousChannel = currentChannel;
        currentChannel = temporaryChannel;
    }

    public State Copy() {
        State state = new State();
        state.setPreviousChannel(new Channel(previousChannel.getNumber()));
        state.setCurrentChannel(new Channel(currentChannel.getNumber()));
        return state;
    }
}
