package com.example.samsungproject2.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.transition.Transition;
import androidx.transition.TransitionInflater;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samsungproject2.R;
import com.example.samsungproject2.databinding.FragmentMapBinding;
import com.example.samsungproject2.databinding.FragmentProfileBinding;
import com.google.android.material.textfield.TextInputEditText;
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

        //TODO ПОРАБОТАТЬ НАД АНИМАЦИЕЙ "УЖЕ ЕСТЬ АККАУНТ"
        //Transition transition = TransitionInflater.from(requireContext())
        //        .inflateTransition(R.transition.shared_views);
        //int cnt = getActivity().getSupportFragmentManager().getBackStackEntryCount();
        // if (cnt!=0){
        //  if (String.valueOf(getActivity().getSupportFragmentManager().getBackStackEntryAt(cnt-1).getName())=="from_reg_fragment")
        //      setSharedElementEnterTransition(transition);
        //}
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

        signUp.setOnClickListener(v -> getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addSharedElement(signUp, "have_an_account_transition")
                .addSharedElement(logIn, "reg_log_transition")
                .addSharedElement(cocktailImg, "cocktail_transition")
                .addSharedElement(welcomeText, "text_transition")
                .addSharedElement(welcomeText2, "text2_transition")
                .addSharedElement(emailInput, "email_transition")
                .addSharedElement(passwordInput, "password_transition")
                .replace(R.id.body_container, new RegistrationFragment())
                .addToBackStack(null)
                .commit());


        return binding.getRoot();
    }
}