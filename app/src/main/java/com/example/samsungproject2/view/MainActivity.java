package com.example.samsungproject2.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samsungproject2.R;
import com.example.samsungproject2.databinding.ActivityMainBinding;
import com.yandex.mapkit.MapKitFactory;

public class MainActivity extends AppCompatActivity {

    private Animation topAnim, bottomAnim;
    private ImageView image;
    private TextView text;
    private static int SPLASH_SCREEN = 3000;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MapKitFactory.setApiKey("e88559aa-a570-483e-84b4-72b7f74183ff");
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);
        image = binding.logoIconLoadScreen;
        text = binding.mskClubsLoadScreen;
        image.setAnimation(topAnim);
        text.setAnimation(bottomAnim);
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, MainScreenActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_SCREEN);
    }
}