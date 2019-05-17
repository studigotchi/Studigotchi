package studigochi.test.student;

import studigochi.test.models.UserModel;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Student {

    private static final Random random = new Random();

    private UserModel user;
    private Timer timer;
    private Status status;
    private long globalTimer;

    public Student(UserModel user) {

        this.user = user;
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

    public void setHealth(double health) {

        user.setHealth(health);
    }


    public void setSuccess(double success) {

        user.setSuccess(success);
    }


    public double getHealth() {

        return user.getHealth();
    }


    public double getSuccess() {

        return user.getSuccess();
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

        addHealth(-0.1);
        addSuccess(random.nextDouble() * 0.9 + 0.1);
    }


    public void sleep() {

        addHealth(0.2);
    }


    public void just_be() {

        addHealth(-0.2);
    }
}