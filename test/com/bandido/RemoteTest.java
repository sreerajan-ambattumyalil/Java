package com.bandido;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class RemoteTest {

    @Test
    public void ShouldSetTheInitialRemoteStateWhenSwitchedOn(){

        Remote remote = new Remote(1, 20, new ArrayList<Channel>());

        assertEquals(0,remote.GetCurrentChannel().getNumber());
        assertEquals(0,remote.GetPreviousChannel().getNumber());
    }

    @Test
    public void ShouldChangeRemoteStateWhenMovingFrom15to14ByEnteringTheChannelNumber(){
        Remote remote = new Remote(1, 20, new ArrayList<Channel>());

        remote.EnterChannelNumber(15);
        assertEquals(0, remote.GetPreviousChannel().getNumber());
        assertEquals(15,remote.GetCurrentChannel().getNumber());

        remote.EnterChannelNumber(14);
        assertEquals(15,remote.GetPreviousChannel().getNumber());
        assertEquals(14,remote.GetCurrentChannel().getNumber());

    }

    @Test
    public void ShouldReturnTwoClicksWhenMovingFrom15to14ByEnteringTheChannelNumber(){
        Remote remote = new Remote(1, 20, new ArrayList<Channel>());

        remote.EnterChannelNumber(15);
        int numberOfClicks = remote.EnterChannelNumber(14);

        assertEquals(2,numberOfClicks);
    }

    @Test
    public void ShouldMoveToNextChannelFrom105WhenPRessingUp(){
        Remote remote = new Remote(103, 109, new ArrayList<Channel>());

        remote.EnterChannelNumber(105);
        remote.MoveUp();
        assertEquals(106,remote.GetCurrentChannel().getNumber());
        assertEquals(105,remote.GetPreviousChannel().getNumber());
    }

    @Test
    public void ShouldMoveDownFrom106ByOneChannelWhenPressingDown(){
        Remote remote = new Remote(103, 109, new ArrayList<Channel>());

        remote.EnterChannelNumber(106);
        remote.MoveDown();
        assertEquals(105,remote.GetCurrentChannel().getNumber());
        assertEquals(106,remote.GetPreviousChannel().getNumber());
    }

    @Test
    public void ShouldMoveFrom77to81If78And79And80AreBlockedChannelsWhenPressingUp(){
        ArrayList<Channel> blockedChannels = new ArrayList<Channel>();
        blockedChannels.add(new Channel(78));
        blockedChannels.add(new Channel(79));
        blockedChannels.add(new Channel(80));
        Remote remote = new Remote(1, 100, blockedChannels);

        remote.EnterChannelNumber(77);
        remote.MoveUp();
        assertEquals(81,remote.GetCurrentChannel().getNumber());
        assertEquals(77,remote.GetPreviousChannel().getNumber());
    }

    @Test
    public void ShouldMoveFrom81to77If78And79And80AreBlockedChannelsWhenPressingDown(){
        ArrayList<Channel> blockedChannels = new ArrayList<Channel>();
        blockedChannels.add(new Channel(78));
        blockedChannels.add(new Channel(79));
        blockedChannels.add(new Channel(80));
        Remote remote = new Remote(1, 100, blockedChannels);

        remote.EnterChannelNumber(81);
        remote.MoveDown();
        assertEquals(77,remote.GetCurrentChannel().getNumber());
        assertEquals(81,remote.GetPreviousChannel().getNumber());
    }

    @Test
    public void ShouldMove1to100WhenPressingBackButtonIf100IsThePreviousChannelAnd1IsTheCurrentChannel(){
        ArrayList<Channel> blockedChannels = new ArrayList<Channel>();
        blockedChannels.add(new Channel(78));
        blockedChannels.add(new Channel(79));
        blockedChannels.add(new Channel(80));
        Remote remote = new Remote(1, 100, blockedChannels);

        remote.EnterChannelNumber(100);
        remote.EnterChannelNumber(1);
        remote.GoBack();
        assertEquals(100,remote.GetCurrentChannel().getNumber());
        assertEquals(1,remote.GetPreviousChannel().getNumber());
    }

    @Test
    public void ShouldMoveFrom100To1WhenMovingUpWhenTheHighestChannelIs100(){
        ArrayList<Channel> blockedChannels = new ArrayList<Channel>();
        blockedChannels.add(new Channel(78));
        blockedChannels.add(new Channel(79));
        blockedChannels.add(new Channel(80));
        Remote remote = new Remote(1, 100, blockedChannels);

        remote.EnterChannelNumber(100);
        remote.MoveUp();
        assertEquals(1,remote.GetCurrentChannel().getNumber());
        assertEquals(100,remote.GetPreviousChannel().getNumber());
    }

    @Test
    public void ShouldMoveFrom1To100WhenMovingDownWhenTheLowestChannelIs1(){
        ArrayList<Channel> blockedChannels = new ArrayList<Channel>();
        blockedChannels.add(new Channel(78));
        blockedChannels.add(new Channel(79));
        blockedChannels.add(new Channel(80));
        Remote remote = new Remote(1, 100, blockedChannels);

        remote.EnterChannelNumber(1);
        remote.MoveDown();
        assertEquals(100,remote.GetCurrentChannel().getNumber());
        assertEquals(1,remote.GetPreviousChannel().getNumber());
    }

}

