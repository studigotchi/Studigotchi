package studigochi.test.student;

/**
 * An enumeration of possible student statuses.<br/>
 * They are used in {@link Student} objects and serialized in {@link Student#toJSONString()} using {@link #toString()}<br/>
 * <br/>
 * <strong>Careful:</strong><br/>Serialized to the database using {@link #ordinal()}, so changes in the order will break compatibility!
 */
public enum Status {
    /**
     * No special Status, intermittent status between the other ones.<br/>
     * Drains health (slower than {@link #LEARN})
     */
    JUST_BE,
    /**
     * Student is learning, drains health and adds success
     */
    LEARN,
    /**
     * Student is sleeping, adds health
     */
    SLEEP,
    /**
     * Student is dead, the game is over.<br/>
     * Earned when {@link Student#getHealth()} reaches {@code 0.0d}<br/>
     * This status will not permute the student since the game is considered over at this point
     */
    DEAD,
    /**
     * Student is dead, the game is over.<br/>
     * Earned when {@link Student#getTotalSemesters()} reaches {@link Student#MAX_TOTAL_SEMESTER}<br/>
     * <br/>
     * After being thrown out of uni the student has no place left to life, and gradually falls
     * into the ever-deepening abyss of depression. No longer able to deal with his life on his own any longer
     * and with his depression worsening, he starts fighting an already lost battle.<br/>
     * After a long but ultimately lost fight, he abandons all hope and starts seeing no way out of his
     * situation but to end his life.
     * This status will not permute the student since the game is considered over at this point
     */
    DEAD_HANGING,
    /**
     * Student has finished the game<br/>
     * Earned when {@link Student#getSemester()} reaches {@link Student#SEMESTERS_TO_WIN}<br/>
     * This status will not permute the student since the game is considered over at this point
     */
    WON;
}
