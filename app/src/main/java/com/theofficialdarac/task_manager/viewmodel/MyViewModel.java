package com.theofficialdarac.task_manager.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.theofficialdarac.task_manager.models.Repository;
import com.theofficialdarac.task_manager.models.Task;
import com.theofficialdarac.task_manager.models.TaskGroup;
import com.theofficialdarac.task_manager.models.User;

import java.time.OffsetTime;
import java.util.List;

public class MyViewModel extends AndroidViewModel {
    private Repository repository;
    private MyViewModel instance;
    private User currentUser;

    public User getCurrentUser() {
        return repository.retrieveCurrentUser().getValue();
    }
    public void setCurrentUser(User user) {
        repository.setUser(user);
    }

//    private MyViewModel(@NonNull Application application) {
//        super(application);
//        this.repository = Repository.getInstance(application);
//    }
    public MyViewModel(@NonNull Application application) {
        super(application);
//        if(instance == null) {
            this.repository = Repository.getInstance(application);
//        }
    }


//    public MyViewModel getInstance(Application application) {
//        if(instance == null) {
//            instance = new ViewModelProvider((ViewModelStoreOwner) application).get(MyViewModel.class);;
//        }
//        return instance;
//    }

    public LiveData<List<TaskGroup>> getAllUserTGs(int userID) {
        return repository.getTaskGroups(userID);
    }

    public LiveData<List<Task>> getTGTasks(int taskGroupID) {
        return repository.getTasks(taskGroupID);
    }

    public LiveData<User> loginUser(String username, String password) {
        LiveData<User> user = repository.getUser(username, password);
        currentUser = user.getValue();
        return user;
    }

    public LiveData<User> registerUser(String username, String password, String email, String firstName, String lastName) {
        LiveData<User> user = repository.postUser(username, password, email, firstName, lastName);
        currentUser = user.getValue();
        return user;
    }
}
