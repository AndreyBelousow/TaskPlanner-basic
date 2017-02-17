package com.netcracker.practise.eventsplanner;

import com.netcracker.practise.eventsplanner.controller.TaskController;
import com.netcracker.practise.eventsplanner.io.*;

public class Main {

    public static void main(String[] args) {

        Notifier notifier = new SimpleConsoleNotifier();

        OutputProcessor outputProcessor = new ConsoleOutputProcessor();

        TaskController taskController = new TaskController(notifier, outputProcessor);

        InputProcessor ip = new ConsoleInputProcessor(taskController, outputProcessor);

        taskController.loadJournal();

        ip.go();

        taskController.saveJournal();

        return;
    }
}
