package studigochi.test.student;

import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Student {

    private static final Random random = new Random();
    public static final int TIME_PER_SEMESTER = 3600;
    private static final double MAX_SUCCESS = 5.0D;
    private static final double MAX_LIFE = 5.0D;

    private Timer timer;
    private Status status;
    private long semesterTimer;
    private int totalSemesters;

    @NotNull
    private String userName;
    private int userId;
    private int semester;
    private double health;
    private volatile double success;

    public Student() {
        this("studi", 10.0D, 0.0D, 1, 0, TIME_PER_SEMESTER);
    }

    public Student(@NotNull String userName, double health, double success, int semester, int userId, long semesterTimer) {
        this.userName = userName;
        this.health = health;
        this.success = success;
        this.semester = semester;
        this.totalSemesters = 1;
        this.userId = userId;

        timer = new Timer();
        status = Status.JUST_BE;
        this.semesterTimer = semesterTimer;
    }

    public void startTimer() {
        timer.purge();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                System.out.printf("Timer: s: %1.4f, h: %1.4f%n", success, health);
                doSomething();
            }
        }, 1000, 1000);
    }

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

    private void doSomething() {

        if (health == 0.0D) {
            //Set dead
            System.out.println("I am dead");
        }

        if (semesterTimer == 0L) {
            endOfSemester();
        } else {
            switch (status) {
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

        semesterTimer--;
    }

    private void endOfSemester() {
        totalSemesters++;

        if (success >= 5.0D) {
            semester++;
        } else {
            health -= random.nextDouble() * 3.0D;
        }

        success = 0.0D;
        semesterTimer = TIME_PER_SEMESTER;
    }


    private void addHealth(double amount) {

        amount += getHealth();
        amount = Math.max(0.0D, amount);
        setHealth(amount);
    }


    private void addSuccess(double amount) {
        amount += getSuccess();
        setSuccess(amount);
    }


    public void learn() {
        if (this.health == 0.0D) {
            this.status = Status.JUST_BE;
            return;
        }

        this.status = Status.LEARN;
        addHealth(-0.1);
        addSuccess(random.nextDouble() * 0.10);
    }


    public void sleep() {
        this.status = Status.SLEEP;
        addHealth(0.02);
    }


    public void just_be() {
        this.status = Status.JUST_BE;
        addHealth(-0.02D);
    }

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
}
