package studigochi.test.student;

import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Student {

    private static final Random random = new Random();

    private Timer timer;
    private Status status;
    private long globalTimer;

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

        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                doSomething();
            }
        }, 10, 1000);
        status = Status.JUST_BE;
        globalTimer = 0L;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getSuccess() {
        return this.success;
    }

    public void setSuccess(double success) {
        this.success = success;
    }

    private void doSomething() {

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

        globalTimer++;
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
        this.status = Status.LEARN;
        addHealth(-0.1);
        addSuccess(random.nextDouble() * 0.9 + 0.1);
    }


    public void sleep() {
        this.status = Status.SLEEP;
        addHealth(0.2);
    }


    public void just_be() {
        this.status = Status.JUST_BE;
        addHealth(-0.2);
    }

    public String toJSONString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"name\": \"").append(this.userName).append("\",");
        sb.append("\"status\": \"").append(this.status).append("\",");
        sb.append("\"time\": ").append(this.globalTimer).append(",");
        sb.append("\"hearts\": ").append(this.getHealth()).append(",");
        sb.append("\"semester\": ").append(this.semester).append(",");
        sb.append("\"stars\": ").append(this.getSuccess());
        return sb.append("}").toString();
    }

    public void eat() {
        this.status = Status.JUST_BE;
        this.addHealth(1.0D);
    }

    public Status getStatus() {
        return status;
    }
}
