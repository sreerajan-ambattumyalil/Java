package com.bandido;

import java.util.ArrayList;
import java.util.Stack;

public class Remote {

    private State currentState;
    private State stateReachedWithMinimumClicks;
    private int lowestChannelNumber;
    private int highestChannelNumber;
    private ArrayList<Channel> blockedChannels;
    private Stack<State> stateStack = new Stack<State>();

    public Remote(int lowestChannelNumber, int highestChannelNumber, ArrayList<Channel> blockedChannels){
        this.lowestChannelNumber = lowestChannelNumber;
        this.highestChannelNumber = highestChannelNumber;
        this.blockedChannels = blockedChannels;
        currentState = new State();
        stateReachedWithMinimumClicks = new State();
    }

    public int EnterChannelNumber(int channelNumber){

        stateStack.push(currentState.Copy());
        currentState.setPreviousChannel(currentState.getCurrentChannel());
        currentState.setCurrentChannel(new Channel(channelNumber));
        return (int)Math.log10(channelNumber) + 1;

    }

    public int MoveUp(){

        Channel currentChannel = currentState.getCurrentChannel();
        Channel nextChannel = GetChannelAfter(currentChannel);
        while(IsABlockedChannel(nextChannel))
            nextChannel = GetChannelAfter(nextChannel);
        stateStack.push(currentState.Copy());
        currentState.setPreviousChannel(currentChannel);
        currentState.setCurrentChannel(nextChannel);

        return 1;
    }

    private Channel GetChannelAfter(Channel currentChannel) {
        return currentChannel.getNumber() >= highestChannelNumber ? new Channel(lowestChannelNumber) :  currentChannel.GetChannelAfter();
    }

    public int MoveDown() {
        Channel currentChannel = currentState.getCurrentChannel();
        Channel nextChannel = GetChannelBefore(currentChannel);
        while(IsABlockedChannel(nextChannel))
            nextChannel = GetChannelBefore(nextChannel);
        stateStack.push(currentState.Copy());
        currentState.setPreviousChannel(currentChannel);
        currentState.setCurrentChannel(nextChannel);

        return 1;
    }

    private Channel GetChannelBefore(Channel currentChannel) {
        return currentChannel.getNumber() <= lowestChannelNumber ? new Channel(highestChannelNumber) : currentChannel.GetChannelBefore();
    }

    public Remote GoBack() {

        stateStack.push(currentState.Copy());
        currentState.Swap();
        return this;
    }

    public Channel GetCurrentChannel(){
        return currentState.getCurrentChannel();
    }

    public Channel GetPreviousChannel(){
        return currentState.getPreviousChannel();
    }

    public void Reset(){
        while (!stateStack.isEmpty())
            currentState = stateStack.pop();
    }

    private boolean IsABlockedChannel(Channel currentChannel) {
        if(blockedChannels.isEmpty()) return false;
        for (Channel blockedChannel : blockedChannels){
            if(blockedChannel.Equals(currentChannel)) return true;
        }
        return false;
    }


    public void SaveCurrentState() {
        stateReachedWithMinimumClicks = currentState.Copy();
    }

    public void SetCurrentState() {
        currentState = stateReachedWithMinimumClicks;
    }
}

