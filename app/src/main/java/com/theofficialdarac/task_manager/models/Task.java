package com.theofficialdarac.task_manager.models;

public class Task {
    public enum State {
        todo,
        wip,
        done
    }

    private int ID;
    private String title;
    private int adminID;
    private String description;
    private int taskGroupID;
    private State state;

    public Task(int ID, String title, int adminID, String description, int taskGroupID, State state) {
        this.ID = ID;
        this.title = title;
        this.adminID = adminID;
        this.description = description;
        this.taskGroupID = taskGroupID;
        this.state = state;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTaskGroupID() {
        return taskGroupID;
    }

    public void setTaskGroupID(int taskGroupID) {
        this.taskGroupID = taskGroupID;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}

