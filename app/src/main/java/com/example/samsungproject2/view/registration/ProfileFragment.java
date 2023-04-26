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
import com.example.samsungproject2.databinding.FragmentProfileBinding;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Transition transition = TransitionInflater.from(requireContext())
                .inflateTransition(R.transition.shared_views);
        setSharedElementEnterTransition(transition);
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
        return binding.getRoot();
    }
}