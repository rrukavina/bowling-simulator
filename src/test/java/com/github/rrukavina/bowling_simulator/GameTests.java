package com.github.rrukavina.bowling_simulator;

import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.Arrays;

/**
 * Tests for Game simulator.
 */
public class GameTests
{
    @Test
    public void emptyInputArray_gameIsIncomplete() {
        int[] noShots = {};
        Assert.assertEquals(new Game(noShots).getTotalScore(), Game.INCOMPLETE_GAME_SCORE);
    }

    @Test
    public void oneShotInputArray_gameIsIncomplete() {
        int[] noShots = { 1 };
        Assert.assertEquals(new Game(noShots).getTotalScore(), Game.INCOMPLETE_GAME_SCORE);
    }

    @Test
    public void allStrikes_scoreIs300() {
        int[] shots = new int[12];
        Arrays.fill(shots, 10);
        Assert.assertEquals(new Game(shots).getTotalScore(), 300);
    }

    @Test
    public void allStrikesWithExtraneousShotsInInput_scoreIs300() {
        int[] shots = { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 1, 2, 3};
        Assert.assertEquals(new Game(shots).getTotalScore(), 300);
    }

    @Test
    public void allGutterBalls_scoreIsZero() {
        int[] shots = new int[20];
        Arrays.fill(shots, 0);
        Assert.assertEquals(new Game(shots).getTotalScore(), 0);
    }

    @Test
    public void allOpenFramesNoGutterBalls_scoreIsTotalPinsKnockedDown() {
        int[] shots = { 1, 1, 2, 2, 3, 3, 4, 4, 1, 1, 2, 2, 3, 3, 4, 4, 1, 1, 2, 2 };
        Assert.assertEquals(new Game(shots).getTotalScore(), 46);
    }

    @Test
    public void allOpenFramesWithGutterBalls_scoreIsTotalPinsKnockedDown() {
        int[] shots = { 1, 0, 0, 2, 3, 3, 9, 0, 1, 1, 2, 2, 3, 3, 8, 0, 0, 9, 0, 0 };
        Assert.assertEquals(new Game(shots).getTotalScore(), 47);
    }

    @Test
    public void allSparesWithZeroBonus_scoreIs100() {
        int[] shots = { 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0};
        Assert.assertEquals(new Game(shots).getTotalScore(), 100);
    }
}
