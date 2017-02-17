package com.netcracker.practise.eventsplanner.io;

import com.netcracker.practise.eventsplanner.model.TaskJournal;

/**
 * Created by 1 on 09.12.2016.
 */
public interface OutputProcessor {
    public void printJournal(TaskJournal journal);

    public void message(String s);

    public void greeting();
}
