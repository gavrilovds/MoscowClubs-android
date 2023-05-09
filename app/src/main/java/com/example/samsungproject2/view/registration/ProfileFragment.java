package com.example.samsungproject2.view.registration;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.transition.Transition;
import androidx.transition.TransitionInflater;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samsungproject2.R;
import com.example.samsungproject2.databinding.FragmentProfileBinding;
import com.example.samsungproject2.viewmodel.profile.LogInViewModel;
import com.google.android.material.textfield.TextInputLayout;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private Button signUp;
    private Button logIn;
    private ImageView cocktailImg;
    private TextView welcomeText;
    private TextView welcomeText2;
    private TextInputLayout emailInput;
    private TextInputLayout passwordInput;
    private LogInViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Transition transition = TransitionInflater.from(requireContext())
                .inflateTransition(R.transition.shared_views);
        setSharedElementEnterTransition(transition);
        SharedPreferences preferences = getActivity().getSharedPreferences("MoscowClubs", Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        signUp = binding.signInButton;
        logIn = binding.logInButton;
        cocktailImg = binding.cocktailImg;
        welcomeText = binding.welcomeText;
        welcomeText2 = binding.welcomeText2;
        emailInput = binding.emailInput;
        passwordInput = binding.passwordInput;
        viewModel = new ViewModelProvider(requireActivity()).get(LogInViewModel.class);
        viewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!s.equals("")) {
                    SharedPreferences preferences = getActivity().getSharedPreferences("MoscowClubs", Context.MODE_PRIVATE);
                    preferences.edit().putString("USER_TOKEN", s).apply();
                    Navigation.findNavController(getActivity().findViewById(R.id.nav_host_fragment))
                            .navigate(R.id.action_bottom_nav_profile_to_bottom_nav_user_profile);
                    viewModel.getLiveData().postValue("");
                }
            }
        });
        viewModel.getErrorText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
            }
        });
        FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
                .addSharedElement(signUp, "have_an_account_transition")
                .addSharedElement(logIn, "reg_log_transition")
                .addSharedElement(cocktailImg, "cocktail_transition")
                .addSharedElement(welcomeText, "text_transition")
                .addSharedElement(welcomeText2, "text2_transition")
                .addSharedElement(emailInput, "email_transition")
                .addSharedElement(passwordInput, "password_transition")
                .build();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity().findViewById(R.id.nav_host_fragment)).navigate(
                        R.id.registrationFragment,
                        null,
                        null,
                        extras);
            }
        });
        binding.logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int code = checkUpForm(binding.emailInput.getEditText().getText().toString(),
                        binding.passwordInput.getEditText().getText().toString());
                switch (code) {
                    case 1:
                        Toast.makeText(getContext(), "Длина почты слишком короткая", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(getContext(), "Неверный формат почты", Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        Toast.makeText(getContext(), "Длина пароля должна быть минимум 8 символов", Toast.LENGTH_LONG).show();
                        break;
                    case 0:
                        viewModel.signIn(binding.emailInput.getEditText().getText().toString(), binding.passwordInput.getEditText().getText().toString());
                        break;
                }


            }


        });
        return binding.getRoot();
    }

    private int checkUpForm(String email, String password) {
        if (email.length() < 5)
            return 1;
        if (!email.contains("@") || !email.contains("."))
            return 2;
        if (password.length() < 8)
            return 3;
        return 0;
    }

}