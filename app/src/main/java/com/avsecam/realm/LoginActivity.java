package com.avsecam.realm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {

    @ViewById(R.id.editTextUsername) EditText usernameField;
    @ViewById(R.id.editTextPassword) EditText passwordField;
    @ViewById(R.id.checkBoxRememberMe) CheckBox rememberMeCheckBox;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @AfterViews
    protected void init() {
        sharedPreferences = getSharedPreferences(getString(R.string.SHAREDPREFERENCES_NAME), MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Remember the remember me checkbox state
        if (sharedPreferences.contains(getString(R.string.REMEMBERME_KEY))) {
            rememberMeCheckBox.setChecked(sharedPreferences.getBoolean(getString(R.string.REMEMBERME_KEY), false));
        }
        // Remember the saved credentials
        if (checkForCredentials()) {
            String savedUsername = sharedPreferences.getString(getString(R.string.USERNAME_KEY), "");
            String savedPassword = sharedPreferences.getString(getString(R.string.PASSWORD_KEY), "");
            usernameField.setText(savedUsername);
            passwordField.setText(savedPassword);
        }
    }

    @Click(R.id.buttonSignIn)
    public void onLoginButtonPressed() {
        // Check if there are already saved credentials
        if (checkForCredentials()) {
            String savedUsername = sharedPreferences.getString(getString(R.string.USERNAME_KEY), "");
            String savedPassword = sharedPreferences.getString(getString(R.string.PASSWORD_KEY), "");
            String username = usernameField.getText().toString();
            String password = passwordField.getText().toString();
            // Compare credentials
            boolean correctCredentials = username.equals(savedUsername) && password.equals(savedPassword);
            if (correctCredentials) {
                // Go to landing page
                Intent intent = new Intent(this, LandingActivity_.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Invalid credentials.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Nothing saved.", Toast.LENGTH_SHORT).show();
        }
    }

    @Click(R.id.buttonRegister)
    public void onRegisterButtonPressed() {
        Intent goToRegister = new Intent(this, RegisterActivity_.class);
        startActivity(goToRegister);
    }

    @Click(R.id.buttonClear)
    public void onClearButtonPressed() {
        if (checkForCredentials()) {
            editor.clear();
            editor.commit();
            Toast.makeText(this, "Credentials cleared.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No credentials to clear.", Toast.LENGTH_SHORT).show();
        }
    }

    // Saves the checkbox state on every press
    @Click(R.id.checkBoxRememberMe)
    public void onRememberMeCheckboxToggled() {
        editor.putBoolean(getString(R.string.REMEMBERME_KEY), rememberMeCheckBox.isChecked());
        editor.commit();
    }

    private boolean checkForCredentials() {
        return sharedPreferences.contains(getString(R.string.USERNAME_KEY)) && sharedPreferences.contains(getString(R.string.PASSWORD_KEY));
    }
}