package com.theofficialdarac.task_manager.serviceapi;

import com.theofficialdarac.task_manager.models.Task;
import com.theofficialdarac.task_manager.models.TaskGroup;
import com.theofficialdarac.task_manager.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Services {

    @GET("other/GETISUniqueEmail.php")
    Call<Integer> getIsUniqueEmail(@Query("email") String email);

    //region UserServices
    @GET("UA/GETUsers.php")
    //  gets all users from db
    Call<List<User>> getAppUsers();

    @GET("UA/GETUser.php")
        //  gets a specific user from db
    Call<User> getAppUser(@Query("username") String username, @Query("password") String password);

    @POST("UA/POSTUser.php")
        //  make post request to server
    Call<Integer> postAppUser(@Query("username") String username, @Query("password") String password, @Query("email") String email, @Query("firstName") String firstName, @Query("lastName") String lastName);
    //endregion

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region TaskServices
    @GET("TA/GETTasks.php")
    //  gets all tasks from db
    Call<List<Task>> getTasks();

    @GET("TA/GETTask.php")
    Call<Task> getTask();

    @DELETE("TA/DELETETask.php")
    Call<Void> deleteTask(@Query("ID") int ID);

    @GET("TA/GETTGTasks.php")
    Call<List<Task>> getTGTasks(@Query("taskGroupID") int TGID);

    @POST("TA/POSTTask.php")
    Call<Void> postTask(@Query("adminID") int userID, @Query("taskGroupID") int taskGroupID, @Query("title") String title, @Query("description") String description);

    //endregion

    ////////////////////////////////////////////////////////////////////////////////////////////////

    //region TaskGroupServices
    @POST("TGA/POSTTG.php")
    //  make post request to server for TaskGroup creation
    Call<Void> postTaskGroup(@Query("adminID") int adminID, @Query("title") String title, @Query("description") String description);

    @GET("TGA/GETUTG.php")
    Call<List<TaskGroup>> getUserTGs(@Query("userID") int userID);

    @DELETE("TGA/DELTETG.php")
    Call<Void> deleteTG(@Query("tgID") int taskGroupID);

    //endregion
}

