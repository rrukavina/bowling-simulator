package com.github.rrukavina.bowling_simulator;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to represent a frame instance.
 */
public class BowlingFrame {

    private static final int ALL_PINS_COUNT = 10;
    private static final int TENTH_FRAME = 10;
    private static final int TENTH_FRAME_MAX_ROLLS = 3;

    public enum FrameType {
        INCOMPLETE,
        OPEN_FRAME,
        SPARE_BONUS_NOT_ADDED,
        SPARE_BONUS_ADDED,
        STRIKE_BONUS_NOT_ADDED,
        STRIKE_BONUS_ADDED,
        TENTH_FRAME_THREE_BALLS_ROLLED
    }

    private int frame;
    private FrameType status = FrameType.INCOMPLETE;
    private List<Integer> shots = new ArrayList<>();
    private List<Integer> bonuses;

    /**
     * Constructor.
     * @param frameNumber an integer value from 1 to 10 specifying the frame number
     */
    public BowlingFrame(int frameNumber) {
        frame = frameNumber;
    }

    /**
     * Add roll score to frame.
     * @param numberOfPinsKnockedDown total number of pins knocked down by roll
     */
    public void addShot(int numberOfPinsKnockedDown) {
        if (isCompleted()) {
            throw new InvalidBowlingFrameOperation("Cannot add a shot to a completed frame");
        }

        shots.add(numberOfPinsKnockedDown);

        if (frame == TENTH_FRAME && shots.size() == TENTH_FRAME_MAX_ROLLS) {
            status = FrameType.TENTH_FRAME_THREE_BALLS_ROLLED;
            return;
        }

        if (numberOfPinsKnockedDown == ALL_PINS_COUNT && frame != TENTH_FRAME) {
            bonuses = new ArrayList<>();

            if (shots.size() == 1) {
                status = FrameType.STRIKE_BONUS_NOT_ADDED;
            } else {
                status = FrameType.SPARE_BONUS_NOT_ADDED;
            }
        } else if (shots.size() == 2) {
            if (getShotsScore() == ALL_PINS_COUNT && frame != TENTH_FRAME) {
                bonuses = new ArrayList<>();
                status = FrameType.SPARE_BONUS_NOT_ADDED;
            }
            else if (getShotsScore() < ALL_PINS_COUNT) {
                status = FrameType.OPEN_FRAME;
            }
        }
    }

    /**
     * Add bonus to strike or spare frame.
     * @param numberOfPinsKknockedDown total number of pins knocked down by subsequent frame roll
     */
    public void addBonus(int numberOfPinsKknockedDown) {
        if (!isBonusRequired()) {
            throw new InvalidBowlingFrameOperation("Cannot add bonus to frame that does not require bonus shots");
        }

        bonuses.add(new Integer(numberOfPinsKknockedDown));

        if (status == FrameType.SPARE_BONUS_NOT_ADDED) {
            status = FrameType.SPARE_BONUS_ADDED;
        } else if (bonuses.size() == 2) {
            status = FrameType.STRIKE_BONUS_ADDED;
        }
    }

    /**
     * @return true if a strike or spare was scored for the frame and required bonus roll or rolls have not
     * been added yet; false otherwise
     */
    public boolean isBonusRequired() {
        return (status == FrameType.SPARE_BONUS_NOT_ADDED || status == FrameType.STRIKE_BONUS_NOT_ADDED);
    }

    /**
     * @return true if shots completed for frame, i.e. a strike was made or both shots were taken regardless of
     * whether or not a spare was made; false otherwise
     */
    public boolean isCompleted() {
        return status != FrameType.INCOMPLETE;
    }

    /**
     * @return the total number of pins knocked down by rolls for the frame
     */
    public int getScore() {
        int total = getShotsScore();

        if (bonuses != null) {
            for (int bonus : bonuses) {
                total += bonus;
            }
        }

        return total;
    }

    // Get total pins knocked down in frame
    private int getShotsScore() {
        int total = 0;

        for (int shot : shots) {
            total += shot;
        }

        return total;
    }
}
