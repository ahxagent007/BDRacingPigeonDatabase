package com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Developer extends AppCompatActivity {

    private ImageView IV_email, IV_linkedin, IV_twitter, IV_facebook, IV_ista, IV_youtube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);


        IV_email = findViewById(R.id.IV_email);
        IV_twitter = findViewById(R.id.IV_twitter);
        IV_linkedin = findViewById(R.id.IV_linkedin);
        IV_facebook = findViewById(R.id.IV_facebook);
        IV_ista = findViewById(R.id.IV_ista);
        IV_youtube = findViewById(R.id.IV_youtube);

        IV_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEmail();
            }
        });

        IV_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/ahxAgent007"));
                startActivity(browserIntent);
            }
        });

        IV_linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/ahxagent007/"));
                startActivity(browserIntent);
            }
        });

        IV_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/ahxXian"));
                startActivity(browserIntent);
            }
        });

        IV_ista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/secret_developers/"));
                startActivity(browserIntent);
            }
        });

        IV_youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCo4qWAa9lcXbaK0Ut6apZ4w"));
                startActivity(browserIntent);
            }
        });


    }

    private void showEmail() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Developer.this);
        builder.setMessage("ahx.agent007@gmail.com");

        /*.setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });*/

        builder.show();


    }
}
