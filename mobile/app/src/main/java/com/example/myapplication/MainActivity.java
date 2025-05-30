package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Starting onCreate");
        setContentView(R.layout.activity_main);

        try {
            // Configuration des boutons de navigation pour les catégories
            setupCategoryNavigation();

            // Configuration du bouton du panier
            setupCartButton();

            // Configuration des boutons d'ajout au panier (si applicable)
            setupAddToCartButtons();

            // Configuration de la barre de navigation du bas
            setupBottomNavigation();

            Log.d(TAG, "onCreate completed successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate: " + e.getMessage(), e);
            Toast.makeText(this, "Une erreur s'est produite lors du démarrage de l'application", Toast.LENGTH_LONG).show();
        }
    }

    private void setupCategoryNavigation() {
        // Trouver les vues des catégories par leur ID
        View foodCategory = findViewById(R.id.foodCategory);
        View hygieneCategory = findViewById(R.id.hygieneCategory);
        View electronicsCategory = findViewById(R.id.electronicsCategory);
        View giftsCategory = findViewById(R.id.giftsCategory);
        View clothingCategory = findViewById(R.id.clothingCategory);
        View offreCategory = findViewById(R.id.offreCategory);

        // Configurer les listeners pour chaque catégorie
        if (foodCategory != null) {
            foodCategory.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Food.class)));
        } else {
            Log.w(TAG, "foodCategory view not found");
        }

        if (hygieneCategory != null) {
            hygieneCategory.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Hygiene.class)));
        } else {
            Log.w(TAG, "hygieneCategory view not found");
        }

        if (electronicsCategory != null) {
            electronicsCategory.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Electronique.class)));
        } else {
            Log.w(TAG, "electronicsCategory view not found");
        }

        if (giftsCategory != null) {
            giftsCategory.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Cadeau.class)));
        } else {
            Log.w(TAG, "giftsCategory view not found");
        }

        if (clothingCategory != null) {
            clothingCategory.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Vetement.class)));
        } else {
            Log.w(TAG, "clothingCategory view not found");
        }

        if (offreCategory != null) {
            offreCategory.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Offre.class)));
        } else {
            Log.w(TAG, "offreCategory view not found");
        }
    }

    private void setupCartButton() {
        ImageButton btnPanier = findViewById(R.id.btnPanier);
        if (btnPanier != null) {
            btnPanier.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            });
        } else {
            Log.w(TAG, "btnPanier view not found");
        }
    }

    private void setupAddToCartButtons() {
        try {
            // Exemple pour un bouton d'ajout au panier
            // Note: Ce bouton n'existe probablement pas dans activity_main.xml
            // Il est préférable de le supprimer si ce n'est pas nécessaire
            Button btnAddToCart = findViewById(R.id.btnAddToCart);
            if (btnAddToCart != null) {
                btnAddToCart.setOnClickListener(v -> {
                    // Récupérer les informations du produit (à adapter selon votre structure)
                    String productName = "Nom du produit"; // Remplacez par le nom réel du produit
                    double price = 99.99; // Remplacez par le prix réel du produit
                    int quantity = 1; // Ou récupérez la quantité depuis un TextView

                    // Créer un nouvel élément de panier
                    CartItem item = new CartItem(1, productName, price, quantity);

                    // Ajouter au gestionnaire de panier
                    CartManager.getInstance().addToCart(item);

                    // Afficher un message de confirmation
                    Toast.makeText(this, productName + " ajouté au panier", Toast.LENGTH_SHORT).show();
                });
            } else {
                Log.d(TAG, "btnAddToCart view not found in MainActivity layout");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in setupAddToCartButtons: " + e.getMessage(), e);
        }
    }

    private void setupBottomNavigation() {
        try {
            // Trouver les éléments de la barre de navigation par leur ID
            LinearLayout homeNav = findViewById(R.id.nav_home);
            LinearLayout offresNav = findViewById(R.id.nav_offers);
            LinearLayout historiqueNav = findViewById(R.id.nav_history);
            LinearLayout profilNav = findViewById(R.id.nav_profile);

            // Configurer les listeners
            if (homeNav != null) {
                homeNav.setOnClickListener(v -> {
                    // Déjà sur MainActivity, pas besoin de naviguer
                    Log.d(TAG, "Already on MainActivity");
                });
            } else {
                Log.w(TAG, "homeNav view not found");
            }

            if (offresNav != null) {
                offresNav.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Offre.class)));
            } else {
                Log.w(TAG, "offresNav view not found");
            }

            if (historiqueNav != null) {
                // CORRECTION: Utiliser historiqueNav au lieu de offresNav
                historiqueNav.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Historique.class)));
            } else {
                Log.w(TAG, "historiqueNav view not found");
            }

            if (profilNav != null) {
                profilNav.setOnClickListener(v -> Toast.makeText(this, "Profil utilisateur", Toast.LENGTH_SHORT).show());
            } else {
                Log.w(TAG, "profilNav view not found");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in setupBottomNavigation: " + e.getMessage(), e);
        }
    }
}