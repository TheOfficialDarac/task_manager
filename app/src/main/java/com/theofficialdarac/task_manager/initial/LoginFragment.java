package com.theofficialdarac.task_manager.initial;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.theofficialdarac.task_manager.MainActivity;
import com.theofficialdarac.task_manager.R;
import com.theofficialdarac.task_manager.TaskGroupsFragment;
import com.theofficialdarac.task_manager.models.User;
import com.theofficialdarac.task_manager.viewmodel.MyViewModel;

public class LoginFragment extends Fragment {
    MaterialButton btnLogin;
    TextInputEditText username, password;
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        MyViewModel myViewModel = new ViewModelProvider(getActivity()).get(MyViewModel.class);

        username = rootView.findViewById(R.id.etUsername);
        password = rootView.findViewById(R.id.etPassword);

        username.setText("");
        password.setText("");

        btnLogin = rootView.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (myViewModel.loginUser(username.getText().toString(), password.getText().toString()) != null) {
//                User currentUser = getActivity().
                if (myViewModel.loginUser("RealRocky", "lozinka") != null) {
                    Toast.makeText(getContext(), "Welcome user", Toast.LENGTH_SHORT).show();

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.flMain, new TaskGroupsFragment())
                            .commit();

                } else {
                    username.setError("Username or password incorrect.");
                    password.setError("Username or password incorrect.");
                }
            }
        });

        TextView btnRegister = rootView.findViewById(R.id.tvRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flMain, new RegisterFragment())
                        .commit();
            }
        });

        return rootView;    }
}