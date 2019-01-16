package com.github.rrukavina.bowling_simulator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.Arrays;

/**
 * Tests for Game simulator.
 */
public class GameTests
{
    @Test(dataProvider = "gameShotsSequence")
    public void testGameShotsSequence(String testDescription, int expectedScore, int[] shotsSequence) {
        System.out.println("\n" + testDescription);
        System.out.println(Arrays.toString(shotsSequence));
        Game game = new Game(shotsSequence);
        System.out.println("Total score for the game: " + game.getTotalScore());
        Assert.assertEquals(game.getTotalScore(), expectedScore);
    }

    @DataProvider(name = "gameShotsSequence")
    public Object[][] createShotsSequenceDataRows() {
        // Each data row consists of a test description, expected total score, and shots sequence array
        return new Object[][] {
                { "Empty input shot sequence results in incomplete game", Game.INCOMPLETE_GAME_SCORE, new int[] { } },
                { "One shot input shot sequence results in incomplete game", Game.INCOMPLETE_GAME_SCORE,
                        new int[] { 1 } },
                { "All strikes is a perfect game score of 300", 300,
                        new int[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 } },
                { "All strikes with extraneous shot input still results in game of 300 and extra shots ignored", 300,
                        new int[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 1, 2, 3 } },
                { "All gutter balls results in game score of 0", 0,
                        new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
                { "All open frames no gutter balls gives score of total pins knocked down", 46,
                        new int[] { 1, 1, 2, 2, 3, 3, 4, 4, 1, 1, 2, 2, 3, 3, 4, 4, 1, 1, 2, 2 } },
                { "All open frames with some gutter balls gives score of total pins knocked down", 47,
                        new int[] { 1, 0, 0, 2, 3, 3, 9, 0, 1, 1, 2, 2, 3, 3, 8, 0, 0, 9, 0, 0} },
                { "All spares with zero bonus for each gives score of total pins knocked down", 100,
                        new int[] { 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0} },
                { "Mix of open frames, spares, and stickers results in correct game score", 178,
                        new int[] { 8, 1, 9, 1, 0, 10, 10, 10, 10, 7, 3, 6, 4, 7, 2, 8, 2, 10} }
        };
    }
}
