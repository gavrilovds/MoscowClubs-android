package com.example.samsungproject2.view.registration;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.transition.Transition;
import androidx.transition.TransitionInflater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samsungproject2.R;
import com.example.samsungproject2.databinding.FragmentRegistrationBinding;
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
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Transition transition = TransitionInflater.from(requireContext())
                .inflateTransition(R.transition.shared_views);
        setSharedElementEnterTransition(transition);
        setSharedElementReturnTransition(transition);
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

        FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
                .addSharedElement(alrdHaveAnAcc, "have_an_account_transition")
                .addSharedElement(text1, "text_transition")
                .addSharedElement(text2, "text2_transition")
                .addSharedElement(emailInput, "email_transition")
                .addSharedElement(passwordInput, "password_transition")
                .addSharedElement(registerButton,"reg_log_transition")
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

        return binding.getRoot();
    }
}