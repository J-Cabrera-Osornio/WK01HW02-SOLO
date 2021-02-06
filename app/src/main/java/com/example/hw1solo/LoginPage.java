package com.example.hw1solo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginPage extends AppCompatActivity {

    private TextView textViewResult;

    private String content = "";
    private EditText userNameInput;
    private EditText passWordEnter;
    private Button verifyCredentials;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);


        textViewResult = findViewById(R.id.text_view_result);
        userNameInput = (EditText) findViewById(R.id.userNameInput);
        String username = userNameInput.getText().toString();



        passWordEnter = (EditText) findViewById(R.id.passWordEnter);
        String verify = passWordEnter.getText().toString();

        userNameInput.addTextChangedListener(checkBlank);
        passWordEnter.addTextChangedListener(checkBlank);

        verifyCredentials = (Button) findViewById(R.id.verifyCredentials);




        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());
                    return;
                }
                List<Post> posts = response.body();
                for (Post post : posts) {
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n\n";
                   textViewResult.append(content);
                }
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                //textViewResult.setText(t.getMessage());
            }
        });


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