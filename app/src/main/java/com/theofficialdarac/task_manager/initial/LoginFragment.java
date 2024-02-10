package com.theofficialdarac.task_manager.initial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.theofficialdarac.task_manager.MainActivity;
import com.theofficialdarac.task_manager.R;
import com.theofficialdarac.task_manager.TaskGroupsFragment;
import com.theofficialdarac.task_manager.models.User;
import com.theofficialdarac.task_manager.serviceapi.ApiManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    MaterialButton btnLogin;
    TextInputEditText username, password;
    String sUsername, sPassword;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        username = rootView.findViewById(R.id.etUsername);
        password = rootView.findViewById(R.id.etPassword);

        username.setText("RealRocky");
        password.setText("lozinka");

        btnLogin = rootView.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> {

            sUsername = username.getText().toString();
            sPassword = password.getText().toString();
            if (!User.isValidUsername(sUsername) || sPassword.equals("")) {
                username.setError("Username or password invalid.");
                password.setError("Username or password invalid.");
            } else {
                ApiManager.getInstance().services().getAppUser("RealRocky", "lozinka").enqueue(new Callback<User>() {
//                ApiManager.getInstance().services().getAppUser(username.getText().toString(), password.getText().toString()).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful())
                            if (response.body() != null) {
                                if (!response.body().isVerified()) {
                                    Toast.makeText(getActivity(), "Account does not exist or has not been verified.", Toast.LENGTH_SHORT).show();
                                } else {
                                    ((MainActivity) getActivity()).setCurrentUser(response.body());
                                    getActivity().getSupportFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.flMain, new TaskGroupsFragment(response.body().getUserID()))
                                            .commit();
                                }
                            } else {
                                username.setError("Username or password incorrect.");
                                password.setError("Username or password incorrect.");
                            }
                    }

                    @Override
                    public void onFailure(Call<User> call, @NonNull Throwable t) {
                        Toast.makeText(getActivity(), "Server Error. ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        TextView btnRegister = rootView.findViewById(R.id.tvRegister);
        btnRegister.setOnClickListener(v -> getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flMain, new RegisterFragment())
                .commit());

        return rootView;
    }
}