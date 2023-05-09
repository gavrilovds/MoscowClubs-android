package com.example.samsungproject2.view.registration;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
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
import com.example.samsungproject2.databinding.FragmentRegistrationBinding;
import com.example.samsungproject2.model.user.UserForRegistration;
import com.example.samsungproject2.viewmodel.profile.RegistrationViewModel;
import com.google.android.material.textfield.TextInputLayout;


public class RegistrationFragment extends Fragment {

    private FragmentRegistrationBinding binding;
    private Button alrdHaveAnAcc;
    private TextView text1;
    private TextView text2;
    private TextInputLayout emailInput;
    private TextInputLayout passwordInput;
    private Button registerButton;
    private ImageView cocktailImg;
    private RegistrationViewModel registrationViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Transition transition = TransitionInflater.from(requireContext())
                .inflateTransition(R.transition.shared_views);
        setSharedElementEnterTransition(transition);
        setSharedElementReturnTransition(transition);
        SharedPreferences preferences = getActivity().getSharedPreferences("MoscowClubs", Context.MODE_PRIVATE);
        //if (preferences.getString("USER_TOKEN", null) != null)
        //  Navigation.findNavController(getActivity().findViewById(R.id.nav_host_fragment))
        //        .navigate(R.id.);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        alrdHaveAnAcc = binding.haveAnAccountButton;
        text1 = binding.welcomeText;
        text2 = binding.welcomeText2;
        emailInput = binding.emailInput;
        passwordInput = binding.passwordInput;
        registerButton = binding.registerButton;
        cocktailImg = binding.cocktailImg;
        registrationViewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);
        registrationViewModel.init();

        registrationViewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!s.equals("")) {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MoscowClubs", Context.MODE_PRIVATE);
                    sharedPreferences.edit().putString("USER_TOKEN", s).apply();
                    Navigation.findNavController(getActivity().findViewById(R.id.nav_host_fragment))
                            .navigate(R.id.action_registrationFragment_to_bottom_nav_user_profile);
                    registrationViewModel.getLiveData().postValue("");
                }
            }
        });

        registrationViewModel.getErrorText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
            }
        });

        FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
                .addSharedElement(alrdHaveAnAcc, "have_an_account_transition")
                .addSharedElement(text1, "text_transition")
                .addSharedElement(text2, "text2_transition")
                .addSharedElement(emailInput, "email_transition")
                .addSharedElement(passwordInput, "password_transition")
                .addSharedElement(registerButton, "reg_log_transition")
                .addSharedElement(cocktailImg, "cocktail_transition")
                .build();

        alrdHaveAnAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(
                        R.id.action_registrationFragment_to_profileFragment,
                        null,
                        null,
                        extras);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int code = checkUpForm(binding.nameInput.getEditText().getText().toString(),
                        binding.emailInput.getEditText().getText().toString(),
                        binding.passwordInput.getEditText().getText().toString(),
                        binding.confirmPasswordInput.getEditText().getText().toString());
                switch (code) {
                    case 1:
                        Toast.makeText(getContext(), "Имя слишком короткое", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(getContext(), "Длина почты слишком короткая", Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        Toast.makeText(getContext(), "Неверный формат почты", Toast.LENGTH_LONG).show();
                        break;
                    case 4:
                        Toast.makeText(getContext(), "Длина пароля должна быть минимум 8 символов", Toast.LENGTH_LONG).show();
                        break;
                    case 5:
                        Toast.makeText(getContext(), "Пароли не совпадают", Toast.LENGTH_LONG).show();
                        break;
                    case 0:
                        UserForRegistration user = new UserForRegistration(
                                binding.emailInput.getEditText().getText().toString(),
                                binding.passwordInput.getEditText().getText().toString(),
                                binding.nameInput.getEditText().getText().toString()
                        );
                        registrationViewModel.registration(user);
                        break;
                }

            }
        });


        return binding.getRoot();
    }

    private int checkUpForm(String name, String email, String password, String confirmPassword) {
        if (name.length() < 3)
            return 1;
        if (email.length() < 5)
            return 2;
        if (!email.contains("@") || !email.contains("."))
            return 3;
        if (password.length() < 8)
            return 4;
        if (!password.equals(confirmPassword))
            return 5;
        return 0;
    }

}