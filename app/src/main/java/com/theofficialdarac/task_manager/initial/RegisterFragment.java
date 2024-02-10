package com.theofficialdarac.task_manager.initial;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.theofficialdarac.task_manager.MainActivity;
import com.theofficialdarac.task_manager.R;
import com.theofficialdarac.task_manager.TaskGroupsFragment;
import com.theofficialdarac.task_manager.models.User;
import com.theofficialdarac.task_manager.serviceapi.ApiManager;
import com.theofficialdarac.task_manager.viewmodel.MyViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {

    private MaterialButton btnRegister;
    private TextInputEditText etrUsername, etrEmail, etrFirstName, etrLastName, etrPassword, etrRepeatPassword;
    private String username, email, password, firstName, lastName;

    MyViewModel myViewModel;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        myViewModel = new ViewModelProvider(getActivity()).get(MyViewModel.class);

        etrUsername = rootView.findViewById(R.id.etrUsername);
        etrEmail = rootView.findViewById(R.id.etrEmail);
        etrFirstName = rootView.findViewById(R.id.etrFirstName);
        etrLastName = rootView.findViewById(R.id.etrLastName);
        etrPassword = rootView.findViewById(R.id.etrPassword);
        etrRepeatPassword = rootView.findViewById(R.id.etrRepeatPassword);

        btnRegister = rootView.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateUserInput();
            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flMain, new LoginFragment())
                        .commit();
            }
        });

        return rootView;
    }

    private void validateUserInput() {

        boolean isValidInput = true;
        username = etrUsername.getText().toString();
        email = etrEmail.getText().toString();
        password = etrPassword.getText().toString().trim();
        String repeatPassword = etrRepeatPassword.getText().toString().trim();
        firstName = etrFirstName.getText().toString();
        lastName = etrLastName.getText().toString();

        if (!User.isValidEmailAddress(email)) {
            etrEmail.setText("");
            etrEmail.setError("Invalid email address");
            isValidInput = false;
        }
        if (!User.isValidUsername(username)) {
            etrUsername.setText("");
            etrUsername.setError("Username is invalid.");
            isValidInput = false;
        }
        if (!User.isValidLastName(lastName)) {
            etrLastName.setText("");
            etrLastName.setError("Last name is invalid.");
            isValidInput = false;
        }
        if (!User.isValidFirstName(firstName)) {
            etrFirstName.setText("");
            etrFirstName.setError("First name is invalid.");
            isValidInput = false;
        }
        if (!password.equals(repeatPassword)) {
            Toast.makeText(getContext(), password + " " + repeatPassword, Toast.LENGTH_SHORT).show();
            etrPassword.setText("");
            etrRepeatPassword.setText("");
            etrPassword.setError("Passwords do not match.");
            etrRepeatPassword.setError("Passwords do not match.");
            isValidInput = false;
        }
        if (isValidInput) {
            ApiManager.getInstance().services().getIsUniqueEmail(email).enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.body().equals(0)) {
                        ApiManager.getInstance().services().postAppUser(username, password, email, firstName, lastName).enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                if(response.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Registration Successful", Toast.LENGTH_SHORT).show();
                                    getActivity().getSupportFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.flMain, new LoginFragment())
                                            .commit();
                                }
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                Toast.makeText(getActivity(), "Registration failed.", Toast.LENGTH_SHORT).show();
                            }
                        });
//                        ((MainActivity)getActivity()).setCurrentUser(myViewModel.registerUser(username, password, email, firstName, lastName).getValue());
//                            myViewModel.registerUser(username, password, email, firstName, lastName);
//                            Log.d("USER_ID", tmp.getID().toString());
//                        Toast.makeText(getActivity(), "Registration Successful", Toast.LENGTH_SHORT).show();
//                        getActivity().getSupportFragmentManager()
//                                .beginTransaction()
//                                .replace(R.id.flMain, new LoginFragment())
//                                .commit();
                    } else {
                        etrEmail.setText("");
                        etrEmail.setError("There is a user already registered to this email address.");
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Toast.makeText(getContext(), "Registration failed. Try again later.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}