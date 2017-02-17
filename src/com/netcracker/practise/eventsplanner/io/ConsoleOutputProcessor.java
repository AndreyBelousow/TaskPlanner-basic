package com.netcracker.practise.eventsplanner.io;

import com.netcracker.practise.eventsplanner.model.Task;
import com.netcracker.practise.eventsplanner.model.TaskJournal;

/**
 * Created by 1 on 09.12.2016.
 */
public class ConsoleOutputProcessor implements OutputProcessor{

    @Override
    public void printJournal(TaskJournal taskJournal) {
        if(taskJournal.getTasks().isEmpty()){
            System.out.println("Journal is empty");
        }
        else {
            for (Task task : taskJournal.getTasks()) {
                System.out.println(task.getName().toUpperCase());
                System.out.println(task.getDate());
                System.out.println("Description:");
                System.out.println(task.getDescription());
                System.out.println("Contacts:");
                for (String contact : task.getContacts()) {
                    System.out.println("____"+contact);
                }
                System.out.println("\n");
            }
        }
    }

    @Override
    public void message(String s) {
        System.out.println(s+"\n");
    }

    @Override
    public void greeting() {
        System.out.println("WELCOME TO PLANNER\n");
    }
}
