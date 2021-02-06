package com.example.hw1solo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginPage extends AppCompatActivity {

    private EditText userNameInput;
    private EditText passWordEnter;
    private Button verifyCredentials;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);



        userNameInput = (EditText) findViewById(R.id.userNameInput);
        String username = userNameInput.getText().toString();

        passWordEnter = (EditText) findViewById(R.id.passWordEnter);
        String verify = passWordEnter.getText().toString();

        userNameInput.addTextChangedListener(checkBlank);
        passWordEnter.addTextChangedListener(checkBlank);

        verifyCredentials = (Button) findViewById(R.id.verifyCredentials);

        verifyCredentials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNameInput.setText("");
                passWordEnter.setText("");

                openActivityDisplayMessage();

            }
        });

    }

    public void openActivityDisplayMessage(){
        EditText enterUsername = (EditText) findViewById(R.id.userNameInput);
        String user = enterUsername.getText().toString();

        Intent sendMessage = new Intent(this, LandingPage.class);
        sendMessage.putExtra("Hello", 9);
        startActivity(sendMessage);
    }

    private TextWatcher checkBlank = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }//This can be left empty but if taken out, android will complain.

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String usernameCheck = userNameInput.getText().toString().trim();
            String passwordCheck = passWordEnter.getText().toString().trim();

            verifyCredentials.setEnabled(!usernameCheck.isEmpty() && !passwordCheck.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable s) {

        }//This can be left empty but if taken out, android will complain.
    };

}