package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvRegister, tvForgotPassword;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialiser les vues
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);


        // Configurer le bouton de retour
        btnBack.setOnClickListener(v -> finish());

        // Configurer le bouton de connexion
        btnLogin.setOnClickListener(v -> {
            if (validateForm()) {
                // Simuler une connexion réussie
                loginUser();
            }
        });

        // Configurer le lien vers l'inscription
        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });

        // Configurer le lien "mot de passe oublié"
        tvForgotPassword.setOnClickListener(v -> {
            Toast.makeText(Login.this, "Fonctionnalité de récupération de mot de passe à implémenter", Toast.LENGTH_SHORT).show();
        });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

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
            etPassword.setError("Veuillez entrer votre mot de passe");
            valid = false;
        } else if (password.length() < 6) {
            etPassword.setError("Le mot de passe doit contenir au moins 6 caractères");
            valid = false;
        } else {
            etPassword.setError(null);
        }

        return valid;
    }

    private void loginUser() {
        // Ici, vous implémenteriez la logique réelle de connexion
        // Pour cet exemple, nous simulons simplement une connexion réussie

        // Afficher un message de succès
        Toast.makeText(Login.this, "Connexion réussie", Toast.LENGTH_SHORT).show();

        // Rediriger vers l'écran principal
        Intent intent = new Intent(Login.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Fermer l'activité de connexion
    }
}