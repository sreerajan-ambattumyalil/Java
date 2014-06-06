package com.bandido;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class OptimizedRemoteControllerTest {

    /*TestCase #1
        1 20
        2 18 19
        5 15 14 17 1 17
      */
    @Test
    public void TestCaseOne(){

        ArrayList<Channel> blockedChannels = new ArrayList<Channel>();
        blockedChannels.add(new Channel(18));
        blockedChannels.add(new Channel(19));
        OptimizedRemoteController remoteController = new OptimizedRemoteController(1,20,blockedChannels);

        ArrayList<Channel> channels = new ArrayList<Channel>();
        channels.add(new Channel(15));
        channels.add(new Channel(14));
        channels.add(new Channel(17));
        channels.add(new Channel(1));
        channels.add(new Channel(17));
        int numberOfClicks = remoteController.FindMinimumClicksToGoThroughChannels(channels);
        assertEquals(7,numberOfClicks);
    }

    /*TestCase #2
        103 108
        1 104
        5 105 106 107 103 105
      */
    @Test
    public void TestCaseTwo(){

        ArrayList<Channel> blockedChannels = new ArrayList<Channel>();
        blockedChannels.add(new Channel(104));
        OptimizedRemoteController remoteController = new OptimizedRemoteController(103,108,blockedChannels);

        ArrayList<Channel> channels = new ArrayList<Channel>();
        channels.add(new Channel(105));
        channels.add(new Channel(106));
        channels.add(new Channel(107));
        channels.add(new Channel(103));
        channels.add(new Channel(105));
        int numberOfClicks = remoteController.FindMinimumClicksToGoThroughChannels(channels);
        assertEquals(8,numberOfClicks);
    }

    /*TestCase #3
        1 100
        4 78 79 80 3
        8 10 13 13 100 99 98 77 81
      */
    @Test
    public void TestCaseThree(){

        ArrayList<Channel> blockedChannels = new ArrayList<Channel>();
        blockedChannels.add(new Channel(78));
        blockedChannels.add(new Channel(79));
        blockedChannels.add(new Channel(80));
        blockedChannels.add(new Channel(3));

        OptimizedRemoteController remoteController = new OptimizedRemoteController(1,100,blockedChannels);

        ArrayList<Channel> channels = new ArrayList<Channel>();
        channels.add(new Channel(10));
        channels.add(new Channel(13));
        channels.add(new Channel(13));
        channels.add(new Channel(100));
        channels.add(new Channel(99));
        channels.add(new Channel(98));
        channels.add(new Channel(77));
        channels.add(new Channel(81));
        int numberOfClicks = remoteController.FindMinimumClicksToGoThroughChannels(channels);
        assertEquals(12,numberOfClicks);
    }

    /*TestCase #4
        1 200
        0
        4 1 100 1 101
      */
    @Test
    public void TestCaseFour(){

        ArrayList<Channel> blockedChannels = new ArrayList<Channel>();

        OptimizedRemoteController remoteController = new OptimizedRemoteController(1,200,blockedChannels);

        ArrayList<Channel> channels = new ArrayList<Channel>();
        channels.add(new Channel(1));
        channels.add(new Channel(100));
        channels.add(new Channel(1));
        channels.add(new Channel(101));
        int numberOfClicks = remoteController.FindMinimumClicksToGoThroughChannels(channels);
        assertEquals(7,numberOfClicks);
    }
}