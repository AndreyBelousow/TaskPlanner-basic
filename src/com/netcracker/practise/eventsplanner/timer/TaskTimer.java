package com.netcracker.practise.eventsplanner.timer;

import com.netcracker.practise.eventsplanner.model.Task;
import com.netcracker.practise.eventsplanner.io.Notifier;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Андрей Белоусов
 */

public class TaskTimer extends Timer{

    private Task task;
    private Notifier notifier;

    public TaskTimer(Task task, Notifier notifier){
        super();
        this.task = task;
        this.notifier = notifier;
    }

    /**The overloaded .schedule() from Timer without parameters. It starts timer*/
    public void schedule(){
        super.schedule(new TimerTask() {
            @Override
            public void run() {
                notifier.show(task);
            }
        }, task.getDate());
    }

    //_______________________________________


    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Notifier getNotifier() {
        return notifier;
    }

    public void setNotifier(Notifier notifier) {
        this.notifier = notifier;
    }
}
