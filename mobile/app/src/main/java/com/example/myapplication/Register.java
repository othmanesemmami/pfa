package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {

    private EditText etFullName, etPhone, etEmail, etPassword, etConfirmPassword, etAddress;
    private CheckBox cbTerms;
    private Button btnRegister;
    private TextView tvLogin;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre);

        // Initialiser les vues
        etFullName = findViewById(R.id.etFullName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etAddress = findViewById(R.id.etAddress);
        cbTerms = findViewById(R.id.cbTerms);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);

        // Configurer le bouton de retour
        btnBack.setOnClickListener(v -> finish());

        // Configurer le bouton d'inscription
        btnRegister.setOnClickListener(v -> {
            if (validateForm()) {
                // Simuler une inscription réussie
                registerUser();
            }
        });

        // Configurer le lien vers la connexion
        tvLogin.setOnClickListener(v -> {
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
            finish(); // Fermer l'activité d'inscription
        });
    }

    private boolean validateForm() {
        boolean valid = true;

        String fullName = etFullName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        // Valider le nom complet
        if (TextUtils.isEmpty(fullName)) {
            etFullName.setError("Veuillez entrer votre nom complet");
            valid = false;
        } else {
            etFullName.setError(null);
        }

        // Valider le téléphone
        if (TextUtils.isEmpty(phone)) {
            etPhone.setError("Veuillez entrer votre numéro de téléphone");
            valid = false;
        } else if (phone.length() < 10) {
            etPhone.setError("Veuillez entrer un numéro de téléphone valide");
            valid = false;
        } else {
            etPhone.setError(null);
        }

        // Valider l'email
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Veuillez entrer votre email");
            valid = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Veuillez entrer un email valide");
            valid = false;
        } else {
            etEmail.setError(null);
        }

        // Valider le mot de passe
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Veuillez entrer un mot de passe");
            valid = false;
        } else if (password.length() < 6) {
            etPassword.setError("Le mot de passe doit contenir au moins 6 caractères");
            valid = false;
        } else {
            etPassword.setError(null);
        }

        // Valider la confirmation du mot de passe
        if (TextUtils.isEmpty(confirmPassword)) {
            etConfirmPassword.setError("Veuillez confirmer votre mot de passe");
            valid = false;
        } else if (!confirmPassword.equals(password)) {
            etConfirmPassword.setError("Les mots de passe ne correspondent pas");
            valid = false;
        } else {
            etConfirmPassword.setError(null);
        }

        // Valider l'adresse
        if (TextUtils.isEmpty(address)) {
            etAddress.setError("Veuillez entrer votre adresse");
            valid = false;
        } else {
            etAddress.setError(null);
        }

        // Valider les conditions d'utilisation
        if (!cbTerms.isChecked()) {
            Toast.makeText(this, "Veuillez accepter les conditions d'utilisation", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        return valid;
    }

    private void registerUser() {
        // Ici, vous implémenteriez la logique réelle d'inscription
        // Pour cet exemple, nous simulons simplement une inscription réussie

        // Afficher un message de succès
        Toast.makeText(Register.this, "Inscription réussie", Toast.LENGTH_SHORT).show();

        // Rediriger vers l'écran de connexion
        Intent intent = new Intent(Register.this, Login.class);
        startActivity(intent);
        finish(); // Fermer l'activité d'inscription
    }
}