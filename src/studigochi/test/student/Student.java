package studigochi.test.student;

import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Student {

    private static final Random random = new Random();
    private static final int TIME_PER_SEMESTER = 3600;
    private static final double MAX_SUCCESS = 5.0D;
    private static final double MAX_LIFE = 5.0D;

    private Timer timer;
    private Status status;
    private long semesterTimer;
    private int totalSemesters;

    @NotNull
    private String userName;
    @NotNull
    private String PW_Hash;
    private int userId;
    private int semester;
    private double health;
    private double success;

    public Student() {
        this("studi", "", 10.0D, 0.0D);
    }

    public Student(@NotNull String userName, @NotNull String PW_Hash, double health, double success) {
        this.userName = userName;
        this.PW_Hash = PW_Hash;
        this.health = health;
        this.success = success;
        this.semester = 1;
        this.totalSemesters = 0;

        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                doSomething();
            }
        }, 10, 1000);
        status = Status.JUST_BE;
        semesterTimer = 0L;
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

        if(semesterTimer == 0L) {
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

        if(success >= 5.0D) {
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
        if(this.health == 0.0D) {
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
}
