package studigochi.test.student;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The student.<br/>
 */
public class Student {

    /**
     * Handles random additions to health/life between the ticks
     */
    private static final Random random = new Random();
    /**
     * Time (in seconds) between exams<br/>
     * In this time, the User needs to fill {@link #success} up to {@link #MAX_SUCCESS}
     */
    public static final int TIME_PER_SEMESTER = 3600;
    /**
     * The total amount of semesters the student can study at most.<br/>
     * <br/>
     * If this semester is reached, then the student will be thrown out of uni and having no place to go fall into depression and eventually hang himself.<br/>
     * You don't want that now, do you?<br/>
     * So take good care of your student!
     */
    public static final int MAX_TOTAL_SEMESTER = 10;
    /**
     * The maximum Value that {@link #success can be}
     */
    private static final double MAX_SUCCESS = 5.0D;
    /**
     *The maximum Value that {@link #health can be}
     */
    private static final double MAX_LIFE = 5.0D;
    /**
     * The semesters the student needs to pass in order to win the game.<br/>
     * <br>
     * If this matches {@link #getSemester()} then the game is considered won.
     */
    public static final int SEMESTERS_TO_WIN = 6;

    /**
     * Updates the student's {@link #status}, {@link #health} and {@link #success} each second.
     */
    private Timer timer;
    /**
     * The status the student is currently in:<br/>
     * By default {@link Status#JUST_BE}<br/>
     * See {@link Status} for more information on their meanings and the effects they have on the student.
     */
    private Status status;
    /**
     * The time until the next semester is reached.<br/>
     * <br/>
     * If this reacehs {@code 0L} the the semester is considered over and the exam is written.
     */
    private long semesterTimer;
    /**
     * The total amount of semester the student has participated in.<br/>
     * Updated even if the user fails an exam.<br/>
     * <br/>
     * If this reaches {@link #MAX_TOTAL_SEMESTER} then the student will be thrown out of uni and having no place to go fall into depression and eventually hang himself.<br/>
     * You don't want that now, do you?<br/>
     * So take good care of your student!
     */
    private int totalSemesters;

    /**
     * The user's display name
     */
    private String userName;

    /**
     * The user's internal Database ID (nonnull, primary auto-increment key in the DB)
     */
    private int userId;
    /**
     * The semester the student is currently in.<br/>
     * <br/>
     * If this reaches {@link #SEMESTERS_TO_WIN} then the student has won the game.
     */
    private int semester;
    /**
     * The student's current health<br/>
     * <br/>
     * If this reaches {@code 0.0d} then the student has died on his desk.<br/>
     * You don't want that now, do you?<br/>
     * So take good care of your student!
     */
    private double health;

    /**
     * The student's current exam success<br/>
     * <br/>
     * Needs to equal {@link #MAX_SUCCESS} by the time the semester ends to advance to the next semester.<br/>
     * Else, the semester needs to be repeated
     */
    private double success;

    /**
     * No-Args constructor to allow JASPER to use this class as a Java Bean.
     * Should never be called.
     * @throws IllegalStateException Should never be called, so it will always fail!
     */
    public Student() {
        //this("studi", 10.0D, 0.0D, 1, 0, TIME_PER_SEMESTER);
        throw new IllegalStateException("I should never be called!");
    }

    /**
     * Usually called by {@link studigochi.test.database.DataBaseTools}<br/>
     *
     * Create a new student with the given parameters:
     *
     * @param userName The user's {@link #userName}
     * @param health The user's {@link #health}
     * @param success The user's {@link #success}
     * @param semester The user's {@link #semester}
     * @param userId The user's {@link #userId}
     * @param semesterTimer The user's {@link #semesterTimer}
     * @param totalSemesters The user's {@link #totalSemesters}
     */
    public Student(String userName, double health, double success, int semester, int userId, long semesterTimer, int totalSemesters) {
        this.userName = userName;
        this.health = health;
        this.success = success;
        this.semester = semester;
        this.totalSemesters = totalSemesters;
        this.userId = userId;

        this.timer = new Timer();
        this.status = Status.JUST_BE;
        this.semesterTimer = semesterTimer;
    }

    /**
     * Starts the student's timer<br/>
     * Before calling this method, the user's status will not update by itself.<br/>
     * See {@link #timer}
     */
    public void startTimer() {
        timer.purge();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                doSomething();
            }
        }, 1000, 1000);
    }

    /**
     * Stops the student's timer<br/>
     * After calling this method, the user's status will no longer update by itself.<br/>
     * See {@link #timer}
     */
    public void stopTimer() {
        timer.cancel();
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = Math.min(health, MAX_LIFE);
    }

    public double getSuccess() {
        return this.success;
    }

    public void setSuccess(double success) {
        this.success = Math.min(success, MAX_SUCCESS);
    }

    /**
     * Check the student's {@link #status} and change {@link #status}, {@link #health} and {@link #success} accordingly.
     */
    private void doSomething() {

        //Check the game-over criteria first:
        if (health == 0.0D) {
            //Dead on desk
            setStatus(Status.DEAD);
            return;
        }

        if(totalSemesters > MAX_TOTAL_SEMESTER) {
            //Students falls into despair and hangs himself
            setStatus(Status.DEAD_HANGING);
            return;
        }

        if (semesterTimer == 0L) {
            //Next semester or repeat current one
            endOfSemester();
        } else {
            switch (status) {
                case WON:
                case DEAD:
                case DEAD_HANGING:
                    //If the game is already over we don't need to do anything
                    return;
                case LEARN:
                    learn();
                    break;

                case SLEEP:
                    sleep();
                    break;

                case JUST_BE:
                    just_be();
                    break;
            }
        }

        //Time will always decrease if the game is not over yet
        semesterTimer--;
    }

    /**
     * Check if the student has passed the exam.
     */
    private void endOfSemester() {
        totalSemesters++;

        //Did the student pass his exams?
        if (success >= MAX_SUCCESS) {
            semester++;

            //Did the student win?
            if(semester > SEMESTERS_TO_WIN) {
                setStatus(Status.WON);
                return;
            }

        } else {
            //Is the student thrown out of uni?
            if(totalSemesters >= MAX_TOTAL_SEMESTER) {
                //Game is over at this point so we can return early.
                setStatus(Status.DEAD_HANGING);
                return;
            }

            //To cope with his failed exams, the student goes through a phase of excessive drinking and
            // his health worsens.
            health -= random.nextDouble() * 3.0D;
        }

        //Start the next/same semester with 0 success and with full time until the next exam.
        success = 0.0D;
        semesterTimer = TIME_PER_SEMESTER;
    }


    /**
     * Helper function to add to the students {@link #health}<br/>
     * Will never allow the health to go below {@code 0.0d}
     * @param amount Amount to add, can be negative to remove {@link #success}
     */
    private void addHealth(double amount) {
        amount += getHealth();
        amount = Math.max(0.0D, amount);
        setHealth(amount);
    }


    /**
     * Helper function to add to the students {@link #success}<br/>
     * Will never allow the health to go below {@code 0.0d}
     * @param amount Amount to add, can be negative to remove {@link #success}
     */
    private void addSuccess(double amount) {
        amount += getSuccess();
        setSuccess(amount);
    }


    /**
     * Go through a learning cycle.<br/>
     * Sets the {@link #status} to {@link Status#LEARN}<br/>
     * Decreases {@link #health}<br/>
     * Increases {@link #success}
     */
    public void learn() {
        if (this.health == 0.0D) {
            this.status = Status.JUST_BE;
            return;
        }

        this.status = Status.LEARN;
        addHealth(-0.1);
        addSuccess(random.nextDouble() * 0.10);
    }


    /**
     * Go through a sleeping cycle.<br/>
     * Sets the {@link #status} to {@link Status#SLEEP}<br/>
     * Increases {@link #health}
     */
    public void sleep() {
        this.status = Status.SLEEP;
        addHealth(0.02);
    }


    /**
     * Go through a sleeping cycle.<br/>
     * Sets the {@link #status} to {@link Status#JUST_BE}<br/>
     * Decreses {@link #health}
     */
    public void just_be() {
        this.status = Status.JUST_BE;
        addHealth(-0.02D);
    }

    /**
     * Creates a JSON representation of this student for serialization.<br/>
     * @return A String representation of this student's
     * {@link #userName},
     * {@link #status},
     * {@link #semesterTimer},
     * {@link #health},
     * {@link #semester},
     * {@link #totalSemesters},
     * {@link #success} to be sent to the client.<br/>
     * Respects the client's differing name mapping.
     */
    public String toJSONString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"name\": \"").append(this.userName).append("\",");
        sb.append("\"status\": \"").append(this.status).append("\",");
        sb.append("\"time\": ").append(getRemainingSemesterTime()).append(",");
        sb.append("\"hearts\": ").append(this.getHealth()).append(",");
        sb.append("\"semester\": ").append(this.semester).append(",");
        sb.append("\"totalSemesters\": ").append(this.totalSemesters).append(",");
        sb.append("\"stars\": ").append(this.getSuccess());
        return sb.append("}").toString();
    }

    public long getRemainingSemesterTime() {
        return semesterTimer;
    }

    /**
     * Go through a sleeping cycle.<br/>
     * Sets the {@link #status} to {@link Status#JUST_BE}<br/>
     * Increases {@link #health}
     */
    public void eat() {
        this.status = Status.JUST_BE;
        this.addHealth(1.0D);
    }

    public Status getStatus() {
        return status;
    }

    public int getSemester() {
        return semester;
    }

    public int getUserId() {
        return userId;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getTotalSemesters() {
        return totalSemesters;
    }

    public String getUserName() {
        return userName;
    }
}
