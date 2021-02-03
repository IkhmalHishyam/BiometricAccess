package com.project.biometricaccess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class BiometricAuthentication extends AppCompatActivity
{
    private ImageView imgOpenAuth;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometric_authentication);

        imgOpenAuth = (ImageView) findViewById(R.id.imgOpenAuth);

        BioAuth();

        //Pop out
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setSubtitle("Login using fingerprint authentication")
                .setNegativeButtonText("User App Password")
                .build();

        imgOpenAuth.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                biometricPrompt.authenticate(promptInfo);
            }
        });
    }

    private void BioAuth()
    {
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(BiometricAuthentication.this,executor,new BiometricPrompt.AuthenticationCallback()
        {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString)
            {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(BiometricAuthentication.this, "Error Auth", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result)
            {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(BiometricAuthentication.this, "Auth Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed()
            {
                super.onAuthenticationFailed();
                Toast.makeText(BiometricAuthentication.this, "Auth Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}