package com.netcracker.practise.eventsplanner.io;

import com.netcracker.practise.eventsplanner.model.Task;

/**
 * @author Андрей Белоусов
 */
public interface Notifier {
    public void show(Task task);
}
