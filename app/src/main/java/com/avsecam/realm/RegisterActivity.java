package com.avsecam.realm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.activity_register)
public class RegisterActivity extends AppCompatActivity {
    @ViewById(R.id.editTextNewUsername) EditText usernameField;
    @ViewById(R.id.editTextNewPassword) EditText passwordField;
    @ViewById(R.id.editTextConfirmPassword) EditText confirmPasswordField;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @AfterViews
    protected void init() {
        sharedPreferences = getSharedPreferences(getString(R.string.SHAREDPREFERENCES_NAME), MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Click(R.id.buttonSave)
    public void onSaveButtonPressed() {
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        String confirmPassword = confirmPasswordField.getText().toString();
        // Check if all fields have values
        if (username.length() > 0 && password.length() > 0 && confirmPassword.length() > 0) {
            // Check if both passwords typed are equal
            if (password.equals(confirmPassword)) {
                editor.putString(getString(R.string.USERNAME_KEY), username);
                editor.putString(getString(R.string.PASSWORD_KEY), password);
                editor.commit();
                Toast.makeText(this, "Credentials saved.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
            }
        } else {
            String toastText = "";
            if (username.length() <= 0) toastText += "Username must not be blank!\n";
            if (password.length() <= 0) toastText += "Password must not be blank!\n";
            toastText = toastText.substring(0, toastText.length() - 1);
            Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
        }
    }

    @Click(R.id.buttonCancel)
    public void onCancelButtonPressed() {
        finish();
    }
}