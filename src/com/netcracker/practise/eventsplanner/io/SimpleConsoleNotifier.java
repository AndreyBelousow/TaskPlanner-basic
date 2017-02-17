package com.netcracker.practise.eventsplanner.io;

import com.netcracker.practise.eventsplanner.model.Task;

import java.awt.*;

/**
 *
 * @author Андрей Белоусов
 */

public class SimpleConsoleNotifier implements Notifier {
    @Override
    public void show(Task task){

        if (task.isMissing()){
            System.out.println("__________MISSING EVENT__________");
        }
        else {
            System.out.println("__________NEW EVENT__________");
            Toolkit.getDefaultToolkit().beep();
        }
        System.out.println(task.getName().toUpperCase()+":"+" "+task.getDate());

        System.out.println("Description:");
        System.out.println(task.getDescription());

        System.out.println("Contacts:");
        for(String c : task.getContacts()){
            System.out.println("____"+c);
        }
        System.out.println("\n");
    }
}
