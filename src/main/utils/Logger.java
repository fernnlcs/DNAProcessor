package main.utils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Callable;

public class Logger {

    private final Queue<String> messages = new LinkedList<>();
    private int waitingTasks = 0;
    private boolean isWaitingToReport = false;

    public Logger() {
    }

    public void log(String information) {
        if (information != null) {
            this.messages.add(information);
        }
    }

    public String getReport() {
        StringBuilder result = new StringBuilder();

        while (!this.messages.isEmpty()) {
            result.append("> ").append(this.messages.remove().trim()).append("\n");
        }

        return result.toString();
    }

    private void printReport() {
        System.out.print("\n" + this.getReport());
    }

    public void report() {
        if (this.isLocked()) {
            this.isWaitingToReport = true;
        } else {
            printReport();
        }
    }

    public int size() {
        return this.messages.size();
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public boolean isLocked() {
        return this.waitingTasks > 0;
    }

    public void lock() {
        this.waitingTasks++;
    }

    public void unlock() {
        this.waitingTasks--;
        if (!this.isLocked() && this.isWaitingToReport) {
            this.report();
        }
    }

    public void waitFor(Runnable runnable) {
        this.lock();

        try {
            runnable.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.unlock();
    }
    public Object waitFor(Callable<Object> callable) {
        this.lock();

        try {
            Object result =  callable.call();
            this.unlock();
            return result;
        } catch (Exception e) {
            this.unlock();
            throw new RuntimeException(e);
        }
    }
}
