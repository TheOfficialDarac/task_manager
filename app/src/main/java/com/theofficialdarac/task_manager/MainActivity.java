package com.theofficialdarac.task_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.theofficialdarac.task_manager.initial.LoginFragment;
import com.theofficialdarac.task_manager.models.User;
import com.theofficialdarac.task_manager.viewmodel.MyViewModel;

public class MainActivity extends AppCompatActivity {

    MyViewModel myViewModel;
    public User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flMain, new LoginFragment())
                .commit();
    }
    public void setCurrentUser(User user){
        currentUser = user;
    }
    public User getCurrentUser() {
        return currentUser;
    }
}