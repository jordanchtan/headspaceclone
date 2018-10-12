package com.example.jordan.smiletribematerial;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

public class IntroActivity extends AppIntro {
    String title1 = "First slide!";
    String description1 = "Description!";
//    Drawable image1 = getResources().getDrawable(R.drawable.tstlogo);
    int backgroundColor1 = Color.parseColor("#ffffff");

    String title2 = "Second slide!";
    String description2 = "Description!";
//    Drawable image2 = getResources().getDrawable(R.drawable.tstlogo);
    int backgroundColor2 = Color.parseColor("#ffff00");

    String title3 = "Third slide!";
    String description3 = "Description!";
//    Drawable image3 = getResources().getDrawable(R.drawable.tstlogo);
    int backgroundColor3 = Color.parseColor("#000000");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Slide 1
        SliderPage sliderPage1 = new SliderPage();
        sliderPage1.setTitle(title1);
        sliderPage1.setDescription(description1);
        sliderPage1.setImageDrawable(R.drawable.tstlogo);
        sliderPage1.setBgColor(backgroundColor1);
        addSlide(AppIntroFragment.newInstance(sliderPage1));

        // Slide 2
        SliderPage sliderPage2 = new SliderPage();
        sliderPage2.setTitle(title2);
        sliderPage2.setDescription(description2);
        sliderPage2.setImageDrawable(R.drawable.tstlogo);
        sliderPage2.setBgColor(backgroundColor2);
        addSlide(AppIntroFragment.newInstance(sliderPage2));

        // Slide 3
        SliderPage sliderPage3 = new SliderPage();
        sliderPage3.setTitle(title3);
        sliderPage3.setDescription(description3);
        sliderPage3.setImageDrawable(R.drawable.tstlogo);
        sliderPage3.setBgColor(backgroundColor3);
        addSlide(AppIntroFragment.newInstance(sliderPage3));
        showSkipButton(false);


    }



    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}