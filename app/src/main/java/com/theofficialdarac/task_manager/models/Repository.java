package com.theofficialdarac.task_manager.models;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.theofficialdarac.task_manager.serviceapi.ApiManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private Application application;
    private static Repository instance;
    private Repository(Application application) {
        this.application = application;
    }
    public static synchronized Repository getInstance(Application application) {
        if(instance == null) {
            instance = new Repository(application);
        }
        return instance;
    }

    private User AppUser = null;
    private MutableLiveData<User> mutableAppUser = new MutableLiveData<>();
    private List<TaskGroup> TGs = null;
    private MutableLiveData<List<TaskGroup>> mutableTGs = new MutableLiveData<>();
    private List<Task> Tasks = null;
    private MutableLiveData<List<Task>> mutableTasks = new MutableLiveData<>();

    public MutableLiveData<List<Task>> getTasks(int taskGroupID) {
        ApiManager.getInstance().services().getTGTasks(taskGroupID)
                .enqueue(new Callback<List<Task>>() {
                    @Override
                    public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                        List<Task> result = response.body();
                        if (result != null) {
                            Tasks = result;
                            mutableTasks.setValue(Tasks);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Task>> call, Throwable t) {

                    }
                });
        return mutableTasks;
    }

    public MutableLiveData<List<TaskGroup>> getTaskGroups() {
//        Log.d("MY_CUSTOM", AppUser.getUsername());
        Call<List<TaskGroup>> call = ApiManager.getInstance().services().getUserTGs(1);
        call.enqueue(new Callback<List<TaskGroup>>() {
            @Override
            public void onResponse(Call<List<TaskGroup>> call, Response<List<TaskGroup>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        TGs = response.body();
                        mutableTGs.setValue(TGs);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<TaskGroup>> call, Throwable t) {
            }
        });
//
        return mutableTGs;
    }

    public MutableLiveData<User> getUser(String username, String password) {
        ApiManager.getInstance().services().getAppUser(username, password).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User result = response.body();
                if (result != null) {
                    AppUser = result;
                    mutableAppUser.setValue(AppUser);
//                    Log.d("CUSTOM_TAG", "I am called");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
        return mutableAppUser;
    }

    public MutableLiveData<User> postUser(String username, String password, String email, String firstName, String lastName) {
        ApiManager.getInstance().services().postAppUser(username, password, email, firstName, lastName).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Integer result = response.body();
                if (result != null) {
                    AppUser = new User(result, username, email, password, firstName, lastName);
                    mutableAppUser.setValue(AppUser);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
        return mutableAppUser;
    }

}
