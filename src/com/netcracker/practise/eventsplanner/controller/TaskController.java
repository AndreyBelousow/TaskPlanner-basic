package com.netcracker.practise.eventsplanner.controller;

import com.netcracker.practise.eventsplanner.io.OutputProcessor;
import com.netcracker.practise.eventsplanner.model.Task;
import com.netcracker.practise.eventsplanner.model.TaskJournal;
import com.netcracker.practise.eventsplanner.io.Notifier;
import com.netcracker.practise.eventsplanner.timer.TaskTimer;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Артем Привалов, дополнил Андрей Белоусов
 */

public class TaskController {

    private String FILEPATH = "journal.txt";

    private TaskJournal taskJournal;
    private List<TaskTimer> timers;
    private Notifier notifier;
    private OutputProcessor outputProcessor;
    
    public TaskController(Notifier notifier, OutputProcessor outputProcessor){
        this.taskJournal = new TaskJournal();
        this.timers = new ArrayList<>();
        this.notifier = notifier;
        this.outputProcessor = outputProcessor;
    }

    public void saveJournal(){
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(FILEPATH);
            this.writeJournal(fileWriter);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        outputProcessor.message("SAVED");
    }

    public void loadJournal(){
        FileReader fileReader;
        TaskJournal tj;
        try {
            fileReader = new FileReader(FILEPATH);
            tj = readJournal(fileReader);
            taskJournal = tj;

            for(Task task : taskJournal.getTasks()){
                if(task.getDate().getTime()<System.currentTimeMillis()){
                    task.setMissing(true);
                    notifier.show(task);
                    taskJournal.deleteTask(task);
                }
                else{
                    TaskTimer t = new TaskTimer(task, notifier);
                    t.schedule();
                    timers.add(t);
                }
            }
            fileReader.close();
        } catch (Exception e) {
            outputProcessor.message("THERE'S EMPTY JOURNAL OR READING ERROR, CREATING NEW....");
        }
    }

    public void checkTasks(){
        for(int i=0; i<taskJournal.getTasks().size(); i++){
            if (taskJournal.getTask(i).getDate().getTime()<System.currentTimeMillis()){
                taskJournal.deleteTask(i);
            }
        }
    }

    //________________________________________________

    public TaskJournal getJournal(){
        return taskJournal;
    }

    public void setJournal(TaskJournal taskJournal){
        this.taskJournal = taskJournal;
    }
    
    public void addTask(String name, String description, List<String> contactsList,Date date){
        Task task  = new Task(name, description, contactsList, date);
        taskJournal.addTask(task);
        TaskTimer tT = new TaskTimer(task, notifier);
        this.timers.add(tT);
        tT.schedule();
    }
    
    public void addTask(Task task){
        taskJournal.addTask(task);
        TaskTimer tt = new TaskTimer(task, notifier);
        tt.schedule();
    }
    
    public void deleteTask(Task task){
        if (taskJournal.getTasks().contains(task)) {
            timers.remove(taskJournal.getTasks().indexOf(task));
            taskJournal.deleteTask(task);
        }
    }

    public void deleteTask(int number){
            timers.remove(number);
            taskJournal.deleteTask(number);
    }
    
    public Task getTask(int number){
        return taskJournal.getTask(number);
    }

    public void setTask(int number,Task task){
        if (number<taskJournal.getTasks().size()) {
            timers.get(number).cancel();
            taskJournal.setTask(number, task);
            timers.get(number).schedule();
        }
        System.out.print("there is no such task");
        
    }

    public void printJournal(){
        checkTasks();
        outputProcessor.printJournal(taskJournal);
    }
    
    public void writeJournal(Writer out){
        PrintWriter printWriter = new PrintWriter(out);
        printWriter.write(taskJournal.getTasks().size()+"\n");
        for (int i = 0; i < taskJournal.getTasks().size(); i++) {
            writeTask(taskJournal.getTask(i), out);
        }
        printWriter.flush();
        printWriter.close();
    }

    public TaskJournal readJournal(Reader in) throws IOException, ParseException{
        List<Task> taskList;
        try (BufferedReader bufferedReader = new  BufferedReader(in)) {
            int listSize = Integer.parseInt(bufferedReader.readLine());
            taskList = new ArrayList<>(listSize);
            for (int i = 0; i < listSize; i++) {
                taskList.add(readTask(bufferedReader));
            }
            bufferedReader.close();
        }
        return new TaskJournal(taskList);
    }

    private void writeTask(Task task, Writer out) {
        PrintWriter pw = new PrintWriter(out);

        pw.write(task.getName()+"\n");
        pw.write(task.getDate().getTime()+"\n");
        pw.write(task.getDescription()+"\n");
        pw.write(task.getContacts().size()+"\n");
        for (int i = 0; i < task.getContacts().size(); i++) {
            pw.write(task.getContact(i)+"\n");
        }
        pw.flush();
    }

    private Task readTask(BufferedReader in) throws IOException, ParseException{
        String name  = in.readLine();
        String dateString = in.readLine();
        Date date = new Date(Long.parseLong(dateString));
        String description = in.readLine();
        int sizeOfList = Integer.parseInt(in.readLine());
        List<String> contacts = new ArrayList<>();
        for (int i = 0; i < sizeOfList; i++) {
            contacts.add(in.readLine());
        }
        return new Task(name, description, contacts, date);
    }
}
