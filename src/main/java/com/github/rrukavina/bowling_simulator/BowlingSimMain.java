package com.github.rrukavina.bowling_simulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Bowling program that processes a sequence of shots specified in the given file input and
 * produces the total score for the game.
 */
public class BowlingSimMain
{
    public static void main(String[] args)
    {
        if (args.length != 1) {
            throw new IllegalArgumentException("Input file path name argument not specified");
        }

        Path filePath = Paths.get(args[0]);
        Charset charset = Charset.forName("US-ASCII");

        try (BufferedReader reader = Files.newBufferedReader(filePath, charset)) {
            String line;

            while ((line = reader.readLine()) != null) {
                // remove all spaces
                line = line.replace(" ", "");

                System.out.println(line);
                int[] shotsSequence = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
                Game game = new Game(shotsSequence);
                System.out.println("Total score for the game: " + game.getTotalScore());
            }
        } catch (IOException ioe) {
            System.err.format(ioe.getMessage());
        }
    }
}
