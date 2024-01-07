package com.theofficialdarac.task_manager.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class TaskGroup extends BaseObservable {
    private Integer ID;
    private int groupAdmin;
    private String title;
    private String description;

    public TaskGroup(Integer ID, int groupAdmin, String title, String description) {
        this.ID = ID;
        this.groupAdmin = groupAdmin;
        this.title = title;
        this.description = description;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public int getGroupAdmin() {
        return groupAdmin;
    }

    public void setGroupAdmin(int groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }
}
