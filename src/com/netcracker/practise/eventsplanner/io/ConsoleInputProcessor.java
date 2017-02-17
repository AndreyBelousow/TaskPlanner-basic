package com.netcracker.practise.eventsplanner.io;

import com.netcracker.practise.eventsplanner.model.Task;
import com.netcracker.practise.eventsplanner.controller.TaskController;

import java.util.*;

/**
 * @author Андрей Белоусов
 */
public class ConsoleInputProcessor implements InputProcessor{

    private TaskController taskController;

    private OutputProcessor outputProcessor;

    Scanner input = new Scanner(System.in);

    /**
     * The right way to create new test tasks using the Calendar:
     * Create Calendar, set it's fields (like SECOND, MONTH, etc)
     * as you need, and then in Task's constructor call .getTime()
     * to get a Date object to initialize new Task
     */
    Calendar calendar = new GregorianCalendar();

    public ConsoleInputProcessor(TaskController controller, OutputProcessor outputProcessor) {
        this.taskController = controller;
        this.outputProcessor = outputProcessor;
    }

    public void go() {

        outputProcessor.greeting();

        man();

        while (true) {
            switch (input.nextLine().trim().toLowerCase()) {
                case "q": {
                    return;
                }
                case "p": {
                    print();
                    break;
                }
                case "a": {
                    add();
                    break;
                }
                case "d": {
                    delete();
                    break;
                }
                case "c": {
                    change();
                    break;
                }
                case "man": {
                    man();
                    break;
                }
                default: {
                    outputProcessor.message("unknown command");
                }
            }
        }
    }

    //________________________________________________

    /**
     * Prints user manual
     */
    private void man() {
        System.out.println("___________________________");
        System.out.println("man: print list of commands");
        System.out.println("p: print list of events");
        System.out.println("a: add new enent");
        System.out.println("d: delete event");
        System.out.println("c: change event");
        System.out.println("q: save and quit");
        System.out.println("___________________________\n");
    }

    /**
     * Create new event dialog
     */
    private void add() {
        Task task = new Task();

        System.out.println("Write name of event (q to cancel)");
        String s = input.nextLine().trim().toLowerCase();
        if (s.equals("q")) {
            System.out.println("canlelled\n");
            return;
        }
        task.setName(s);

        System.out.println("Add a description? (y/n)");
        while (true) {
            switch (input.nextLine().trim().toLowerCase()) {
                case "y": {
                    System.out.println("Write a description:");
                    task.setDescription(input.nextLine());
                    break;
                }
                case "n": {
                    task.setDescription("Empty");
                    break;
                }
                default: {
                    System.out.println("input error");
                    continue;
                }
            }
            break;
        }

        System.out.println("Add contacts list? (y/n)");
        while (true) {
            switch (input.nextLine().trim().toLowerCase()) {
                case "y": {
                    System.out.println("Write contacts, q to finish:");
                    while (true) {
                        String st = input.nextLine().trim();
                        switch (st) {
                            case "q": {
                                break;
                            }
                            default: {
                                task.addContact(st);
                                continue;
                            }
                        }
                        break;
                    }
                    break;
                }
                case "n": {
                    task.addContact("Empty");
                    break;
                }
                default: {
                    System.out.println("input error");
                    continue;
                }
            }
            break;
        }

        System.out.println("Enter date YYYY:MM:DD:HH:MM");
        System.out.println("Date must be greater than the current");
        while (true) {
            try {
                String[] strings = input.nextLine().split(":");

                
                calendar.set(Calendar.YEAR, Integer.parseInt(strings[0]));
                calendar.set(Calendar.MONTH, Integer.parseInt(strings[1])-1);
                calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(strings[2]));
                calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(strings[3]));
                calendar.set(Calendar.MINUTE, Integer.parseInt(strings[4]));
                calendar.set(Calendar.SECOND, 0);
                if (calendar.getTime().getTime() <= System.currentTimeMillis()) throw new IllegalArgumentException();
                break;
            } catch (Exception e) {
                System.out.println("Date must be in correct format and greater than the current");
                continue;
            }
        }
        
        task.setDate(calendar.getTime());
        System.out.println(task.getDate());

        outputProcessor.message("Event " + task.getName() + " on " + task.getDate()+" CREATED\n");
        taskController.addTask(task);
    }

    /**
     * Delete event dialog
     */
    private void delete() {
        System.out.println("Which event do you want to delete? (number, from zero, q to cancel)");
        int number;
        while (true) {
            try {
                String s = input.nextLine().trim();
                if (s.equals("q")) {
                    System.out.println("canlelled\n");
                    return;
                }
                number = Integer.parseInt(s);
                if (taskController.getJournal().getTasks().size() < number) {
                    throw new IndexOutOfBoundsException();
                }


                taskController.deleteTask(number);

                
                outputProcessor.message("Event#" + number + " DELETED\n");
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("wrong index");
                continue;
            } catch (Exception e) {
                System.out.println("input error");
                continue;
            }
        }
    }

    /**
     * Print list of event
     */
    private void print() {
        taskController.printJournal();
    }

    /**
     * Change event dialog
     */
    private void change() {

        if(true) {
            System.out.println("not fully supported in this version");
            System.out.println("blocked due to it, sorry\n");
            return;
        }

        System.out.println("Which event do you want to change? (q to cancel)");
        String s = input.nextLine().trim().toLowerCase();
        if (s.equals("q")) {
            System.out.println("canlelled\n");
            return;
        }
        int taskNumber;
        while (true) {
            try {
                taskNumber = Integer.parseInt(s);
                if (taskController.getJournal().getTasks().size() < taskNumber) {
                    throw new IndexOutOfBoundsException();
                }

                changeName(taskNumber);

                changeDescription(taskNumber);

                changeContacts(taskNumber);

                System.out.println("Date change is not supported now");
                //changeDate(taskNumber);

                break;

            } catch (IndexOutOfBoundsException e) {
                System.out.println("wrong index");
                continue;
            } catch (Exception e) {
                System.out.println("input error");
                continue;
            }
        }
        System.out.println("DONE\n");
    }

    private void changeName(int taskNumber){
        System.out.println("Change name? (y/n)");
        while (true) {
            switch (input.nextLine().trim().toLowerCase()) {
                case "y": {
                    System.out.println("Write a description:");
                    taskController.getJournal().getTask(taskNumber).setName(input.nextLine());
                    break;
                }
                case "n": {
                    break;
                }
                default: {
                    System.out.println("input error");
                    continue;
                }
            }
            break;
        }
    }

    private void changeDescription(int taskNumber){
        System.out.println("Change description? (y/n)");
        while (true) {
            switch (input.nextLine().trim().toLowerCase()) {
                case "y": {
                    System.out.println("Write a description:");
                    taskController.getJournal().getTask(taskNumber).setDescription(input.nextLine());
                    break;
                }

                case "n": {
                    break;
                }
                default: {
                    System.out.println("input error");
                    continue;
                }
            }
            break;
        }
    }

    private void changeContacts(int taskNumber){
        System.out.println("Change contacts list? (y/n)");
        while (true) {
            switch (input.nextLine().trim().toLowerCase()) {
                case "y": {
                    System.out.println("a: add new contacts, c: change existing");
                    while (true) {
                        switch (input.nextLine().trim()) {
                            case "a": {
                                System.out.println("Write contacts, q to finish:");
                                while (true) {
                                    switch (input.nextLine()) {
                                        case "q": {
                                            break;
                                        }
                                        default: {
                                            taskController.getJournal().getTask(taskNumber).getContacts().add(input.nextLine());
                                            continue;
                                        }
                                    }
                                    break;
                                }
                                break;
                            }
                            case "c": {
                                System.out.println("Which contact do you want to change?");
                                while (true) {
                                    String st = input.nextLine().trim();
                                    int n;
                                    try {
                                        n = Integer.parseInt(st);
                                        if (taskController.getJournal().getTask(taskNumber).getContacts().size() < n) {
                                            throw new IndexOutOfBoundsException();
                                        }
                                        System.out.println("Write new contact information");
                                        taskController.getJournal().getTask(taskNumber).setContact(n, input.nextLine().trim());
                                    } catch (IndexOutOfBoundsException e) {
                                        System.out.println("wrong index");
                                        continue;
                                    } catch (Exception e) {
                                        System.out.println("input error");
                                        continue;
                                    }
                                }
                            }
                            default: {
                                System.out.println("input error");
                                continue;
                            }
                        }
                    }
                }
                case "n": {
                    break;
                }
                default: {
                    System.out.println("input error");
                    continue;
                }
            }
            break;
        }
    }

    private void changeDate(int taskNumber){
        System.out.println("Change date? (y/n)");
        while (true) {
            switch (input.nextLine().toLowerCase()) {
                case "y": {
                    System.out.println("Enter new date YYYY:MM:DD:HH:MM");
                    System.out.println("Date must be greater than the current");
                    while (true) {
                        try {
                            String[] strings = input.nextLine().split(":");
                            calendar.set(Calendar.YEAR, Integer.parseInt(strings[0]));
                            calendar.set(Calendar.MONTH, Integer.parseInt(strings[1]));
                            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(strings[2]));
                            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(strings[3]));
                            calendar.set(Calendar.MINUTE, Integer.parseInt(strings[4]));
                            calendar.set(Calendar.SECOND, 0);
                            if (calendar.getTime().getTime() <= System.currentTimeMillis())
                                throw new IllegalArgumentException();
                            taskController.getJournal().getTask(taskNumber).setDate(calendar.getTime());
                            break;
                        } catch (Exception e) {
                            System.out.println("Date must be in correct format and greater than the current");
                            continue;
                        }
                    }
                    break;
                }
                case "n": {
                    break;
                }
                default: {
                    System.out.println("input error");
                    continue;
                }
            }
            break;
        }
    }
}
