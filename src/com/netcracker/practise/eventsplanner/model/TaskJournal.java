
package com.netcracker.practise.eventsplanner.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Артем Привалов
 */

public class TaskJournal implements Serializable{
    
    
    private List<Task> taskList;
  

    public TaskJournal()
    {
        taskList = new LinkedList();
    }

    //------------
    
    public TaskJournal(List<Task> taskList){
        this.taskList = new LinkedList<>(taskList);
    }
    
    public Task getTask(int number){
        return taskList.get(number);
    }

    public List<Task> getTasks(){
        return taskList;
    }
    
    public void setTask(int number, Task task){
        taskList.set(number, task);
    }

    public void setTask(Task oldTask, Task newTask){
        if (taskList.contains(oldTask)) {
            taskList.set(taskList.indexOf(oldTask), newTask);
        }
        
    }
    
    public void addTask(Task task){
        taskList.add(task);
    }
    
    public void deleteTask(int number){
        taskList.remove(number);
    }

    public void deleteTask(Task task){
        taskList.remove(task);
    }
}
