package com.github.rrukavina.bowling_simulator;

import java.util.ArrayList;
import java.util.List;

public class Game {

    public static final int INCOMPLETE_GAME_SCORE = -1;
    public static final int TOTAL_FRAMES_IN_GAME = 10;

    // List of frames built up as the shot sequence is processed. Current frame
    // is the last frame on the list.
    private List<BowlingFrame> frames = new ArrayList<>();

    // Total score is either set to -1 for an incomplete game or a valid total
    // score for a complete game for the given input shots sequence.
    private int totalScore = INCOMPLETE_GAME_SCORE;

    /**
     * Constructor.
     * @param shotsSequence sequence of rolls for complete game
     */
    public Game(int[] shotsSequence) {
        // add first frame which is the current frame for the first shot added
        addNewFrame();

        processShotsSequence(shotsSequence);
    }

    /**
     * @return total score for the game
     */
    public int getTotalScore() {
        return totalScore;
    }

    private void addNewFrame() {
        frames.add(new BowlingFrame(getNextFrameNumber()));
    }

    // Returns number of the next frame to become the active frame and ranges from 1 to 10.
    private int getNextFrameNumber() {
        return frames.size() + 1;
    }

    private BowlingFrame getCurrentFrame() {
        return frames.get(frames.size() - 1);
    }

    /**
     * @return if only one frame played return null otherwise return the previous {@link BowlingFrame} instance
     */
    private BowlingFrame getPreviousFrame() {

        if (frames.size() < 2) {
            return null;
        }

        return frames.get(frames.size() - 2);
    }

    /**
     * @return if only two or less frames played return null otherwise return the two frames
     * prior {@link BowlingFrame} instance
     */
    private BowlingFrame getFrameTwoPrior() {
        // If two or less frames played then return null
        if (frames.size() < 3) {
            return null;
        }
        return frames.get(frames.size() - 3);
    }

    private boolean isGameCompleted() {
        return frames.size() == TOTAL_FRAMES_IN_GAME && getCurrentFrame().isCompleted();
    }

    /**
     * Processes the given shots sequence until a complete 10 frame game total score
     * is determined and ignores any extraneous shots provided in the input array.
     * @param shotsSequence array of shots specifying the number of pins knocked for each shot entry
     */
    private void processShotsSequence(int[] shotsSequence) {
        // Process shots from given shots array until game is completed or no
        // remaining shots to process
        for (int i = 0; i < shotsSequence.length; i++) {
            int shot = shotsSequence[i];
            getCurrentFrame().addShot(shot);

            addRollAsBonusToPreviousStrikeOrSpare(shot);

            if (getCurrentFrame().isCompleted()) {
                if (isGameCompleted()) {
                    setTotalScore();
                    break;
                }

                // add next frame which becomes the current frame for the next shot added
                addNewFrame();
            }
        }
    }

    private void addRollAsBonusToPreviousStrikeOrSpare(int shot) {
        // If current frame is not first frame and previous frame was
        // strike or spare then add bonus
        if (getPreviousFrame() != null && getPreviousFrame().isBonusRequired()) {
            getPreviousFrame().addBonus(shot);
        }

        // If current frame is not first of second frame and two frames prior
        // was a strike then add bonus
        if (getFrameTwoPrior() != null && getFrameTwoPrior().isBonusRequired()) {
            getFrameTwoPrior().addBonus(shot);
        }
    }

    private void setTotalScore() {
        int total = 0;

        for (BowlingFrame item : frames) {
            total += item.getScore();
        }

        totalScore = total;
    }
}
