package com.cad.flashcards;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


@SpringBootApplication
public class Application {
    private static final String FILE_DIR = "src/resources/service-account.json";

    public static void main(String[] args) throws IOException {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(new FileInputStream(FILE_DIR)))
                .build();

        FirebaseApp.initializeApp(options);

        SpringApplication.run(Application.class, args);
    }
}
