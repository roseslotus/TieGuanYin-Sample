package com.lotus.tieguanyin;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lotus.annotations.Builder;
import com.lotus.annotations.Optional;
import com.lotus.annotations.Required;

@Builder
public class UserActivity extends AppCompatActivity {

    @Required
    public String name;

    @Required
    public int age;

    @Optional
    public String title;

    @Optional
    public String company;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user);

        ((TextView)findViewById(R.id.nameView)).setText(name);
        ((TextView)findViewById(R.id.ageView)).setText(String.valueOf(age));
        ((TextView)findViewById(R.id.titleView)).setText(title);
        ((TextView)findViewById(R.id.companyView)).setText(company);

    }
}
