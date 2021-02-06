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


    /*textViewResult: data from API is sent and displayed on login_page.xml
     * I couldn't figure out how to remove the textview without causing an app crash.
     * */
    private TextView textViewResult;
    private String fromInternet = "";


    private EditText userNameInput;
    private EditText passWordEnter;
    private Button verifyCredentials;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);


        textViewResult = findViewById(R.id.text_view_result);
        userNameInput = (EditText) findViewById(R.id.userNameInput);
        passWordEnter = (EditText) findViewById(R.id.passWordEnter);
        verifyCredentials = (Button) findViewById(R.id.verifyCredentials);


        userNameInput.addTextChangedListener(checkBlank);
        passWordEnter.addTextChangedListener(checkBlank);



        /* Retrofit code was used from tutorial on how to install retrofit.*/
        Retrofit jsonApi = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = jsonApi.create(JsonPlaceHolderApi.class);
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();

        call.enqueue(new Callback<List<Post>>() {

            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                List<Post> posts = response.body();
                for (Post post : posts) {
                    fromInternet += "ID: " + post.getId() + "\n";
                    fromInternet += "User ID: " + post.getUserId() + "\n";
                    fromInternet += "Title: " + post.getTitle() + "\n";
                    fromInternet += "Text: " + post.getText() + "\n\n";
                    textViewResult.append(fromInternet);
                }
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
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
        Intent sendMessage = new Intent(this, LandingPage.class);
        startActivity(sendMessage);
    }

    /*checkBlank: It prevents the user from pressing button to landing page by greying it
     *out. Only if the fields are open will the button be available to be clicked.
     * */
    private TextWatcher checkBlank = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String usernameCheck = userNameInput.getText().toString().trim();
            String passwordCheck = passWordEnter.getText().toString().trim();

            verifyCredentials.setEnabled(!usernameCheck.isEmpty() && !passwordCheck.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


}