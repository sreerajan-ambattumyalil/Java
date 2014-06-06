package com.bandido;

import java.util.ArrayList;

public class OptimizedRemoteController {

    private Channel previousChannel = new Channel(0);
    private Channel currentChannel = new Channel(0);
    private int lowestChannelNumber;
    private int highestChannelNumber;
    private ArrayList<Channel> blockedChannels;

    public OptimizedRemoteController(int lowestChannelNumber, int highestChannelNumber, ArrayList<Channel> blockedChannels) {
        this.lowestChannelNumber = lowestChannelNumber;
        this.highestChannelNumber = highestChannelNumber;
        this.blockedChannels = blockedChannels;
    }

    public int FindMinimumClicksToGoThroughChannels(ArrayList<Channel> channels){

        int result = 0;
        Remote remote = new Remote(lowestChannelNumber, highestChannelNumber, blockedChannels);
        for (Channel channel: channels) {

            int minimumClicksFound;
            int numberOfKeyPressesToEnterTheChannelNumber = remote.EnterChannelNumber(channel.getNumber());
            minimumClicksFound = numberOfKeyPressesToEnterTheChannelNumber;
            remote.SaveCurrentState();
            remote.Reset();

            int numberOfClicksNeededWhenMovingUp = GetNumberOfClicksNeededWhenMovingUp(remote, channel);
            if(numberOfClicksNeededWhenMovingUp < minimumClicksFound) {
                minimumClicksFound = numberOfClicksNeededWhenMovingUp;
                remote.SaveCurrentState();
            }
            remote.Reset();

            int numberOfClicksNeededWhenMovingDown = GetNumberOfClicksNeededWhenMovingDown(remote, channel);
            if(numberOfClicksNeededWhenMovingDown < minimumClicksFound){
                minimumClicksFound = numberOfClicksNeededWhenMovingDown;
                remote.SaveCurrentState();
            }
            remote.Reset();
            int numberOfClicksNeededWhenMovingUpAfterGoingToPreviousChannel = GetNumberOfClicksNeededWhenMovingUp(remote.GoBack(), channel) + 1;
            if(numberOfClicksNeededWhenMovingUpAfterGoingToPreviousChannel < minimumClicksFound){
                minimumClicksFound = numberOfClicksNeededWhenMovingUpAfterGoingToPreviousChannel;
                remote.SaveCurrentState();
            }
            remote.Reset();

            int numberOfClicksNeededWhenMovingDownAfterGoingToPreviousChannel = GetNumberOfClicksNeededWhenMovingDown(remote.GoBack(), channel) + 1;
            if(numberOfClicksNeededWhenMovingDownAfterGoingToPreviousChannel < minimumClicksFound){
                minimumClicksFound = numberOfClicksNeededWhenMovingDownAfterGoingToPreviousChannel;
                remote.SaveCurrentState();
            }
            remote.Reset();
            remote.SetCurrentState();
            result += minimumClicksFound;
        }

        return result;
    }

    private int GetNumberOfClicksNeededWhenMovingUp(Remote remote, Channel channel) {
        int numberOfClicksNeededMovingUp = 0;
        Channel currentChannel = remote.GetCurrentChannel();
        while (!currentChannel.Equals(channel)){
            remote.MoveUp();
            currentChannel = remote.GetCurrentChannel();
            numberOfClicksNeededMovingUp++;
        }
        return numberOfClicksNeededMovingUp;
    }

    private int GetNumberOfClicksNeededWhenMovingDown(Remote remote, Channel channel) {
        int numberOfClicksNeededMovingDown = 0;
        Channel currentChannel = remote.GetCurrentChannel();
        while (!currentChannel.Equals(channel)){
            remote.MoveDown();
            currentChannel = remote.GetCurrentChannel();
            numberOfClicksNeededMovingDown++;
        }
        return numberOfClicksNeededMovingDown;
    }
}

