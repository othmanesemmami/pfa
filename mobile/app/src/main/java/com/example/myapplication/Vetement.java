package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.HashMap;
import java.util.Map;

public class Vetement extends AppCompatActivity {

    // Map pour stocker les informations des produits
    private Map<Integer, ProductInfo> productInfoMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vetement);

        // Configurer le bouton de retour
        ImageButton btnRetour = findViewById(R.id.btnRetour);
        btnRetour.setOnClickListener(v -> finish());

        // Configurer le bouton du panier
        ImageButton btnPanier = findViewById(R.id.btnPanier);
        btnPanier.setOnClickListener(v -> {
            Intent intent = new Intent(Vetement.this, CartActivity.class);
            startActivity(intent);
        });

        // Initialiser les produits
        initializeProducts();

        // Configurer les contrôles pour tous les produits
        setupProductControls();

        // Configurer la navigation du bas
        setupBottomNavigation();
    }

    private void initializeProducts() {
        // Définir les informations des produits de vêtements
        productInfoMap.put(1, new ProductInfo(1, "T-shirt Coton", 120.00, 1));
        productInfoMap.put(2, new ProductInfo(2, "Jean Slim", 250.00, 1));
        productInfoMap.put(3, new ProductInfo(3, "Veste Légère", 350.00, 1));
        productInfoMap.put(4, new ProductInfo(4, "Chaussures Sport", 450.00, 1));
    }

    private void setupProductControls() {
        // Trouver toutes les CardView dans le GridLayout
        ViewGroup gridProducts = findViewById(R.id.gridProducts);
        int childCount = gridProducts.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = gridProducts.getChildAt(i);
            if (child instanceof CardView) {
                CardView cardView = (CardView) child;
                setupProductCard(cardView, i + 1); // i+1 comme ID de produit
            }
        }
    }

    private void setupProductCard(CardView cardView, int productId) {
        // Trouver les contrôles dans la CardView
        ViewGroup cardContent = (ViewGroup) cardView.getChildAt(0);
        if (!(cardContent instanceof LinearLayout)) return;

        // Trouver les boutons et le TextView de quantité
        Button btnSubtract = null;
        TextView tvQuantity = null;
        Button btnAdd = null;
        Button btnAddToCart = null;

        // Parcourir les enfants pour trouver les contrôles
        for (int i = 0; i < cardContent.getChildCount(); i++) {
            View child = cardContent.getChildAt(i);

            // Trouver le LinearLayout qui contient les contrôles de quantité
            if (child instanceof LinearLayout) {
                LinearLayout quantityLayout = (LinearLayout) child;
                for (int j = 0; j < quantityLayout.getChildCount(); j++) {
                    View quantityChild = quantityLayout.getChildAt(j);

                    if (quantityChild instanceof Button) {
                        Button button = (Button) quantityChild;
                        String buttonText = button.getText().toString();

                        if ("-".equals(buttonText)) {
                            btnSubtract = button;
                        } else if ("+".equals(buttonText)) {
                            btnAdd = button;
                        }
                    } else if (quantityChild instanceof TextView) {
                        // Vérifier si c'est le TextView de quantité
                        TextView textView = (TextView) quantityChild;
                        try {
                            Integer.parseInt(textView.getText().toString());
                            tvQuantity = textView;
                        } catch (NumberFormatException e) {
                            // Ce n'est pas le TextView de quantité
                        }
                    }
                }
            }

            // Le dernier enfant devrait être le bouton "Ajouter"
            if (i == cardContent.getChildCount() - 1 && child instanceof Button) {
                btnAddToCart = (Button) child;
            }
        }

        // Configurer les listeners si tous les contrôles sont trouvés
        if (btnSubtract != null && tvQuantity != null && btnAdd != null && btnAddToCart != null) {
            final TextView finalTvQuantity = tvQuantity;

            // Bouton pour diminuer la quantité
            btnSubtract.setOnClickListener(v -> {
                ProductInfo info = productInfoMap.get(productId);
                if (info != null && info.getQuantity() > 1) {
                    info.setQuantity(info.getQuantity() - 1);
                    finalTvQuantity.setText(String.valueOf(info.getQuantity()));
                }
            });

            // Bouton pour augmenter la quantité
            btnAdd.setOnClickListener(v -> {
                ProductInfo info = productInfoMap.get(productId);
                if (info != null) {
                    info.setQuantity(info.getQuantity() + 1);
                    finalTvQuantity.setText(String.valueOf(info.getQuantity()));
                }
            });

            // Bouton pour ajouter au panier
            btnAddToCart.setOnClickListener(v -> {
                ProductInfo info = productInfoMap.get(productId);
                if (info != null) {
                    CartItem item = new CartItem(
                            info.getProductId(),
                            info.getProductName(),
                            info.getPrice(),
                            info.getQuantity()
                    );
                    CartManager.getInstance().addToCart(item);
                    Toast.makeText(this, info.getProductName() + " ajouté au panier", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setupBottomNavigation() {
        // Trouver les éléments de la barre de navigation
        LinearLayout bottomNav = findViewById(R.id.bottomNav);
        LinearLayout homeNav = (LinearLayout) bottomNav.getChildAt(0);
        LinearLayout offresNav = (LinearLayout) bottomNav.getChildAt(1);
        LinearLayout historiqueNav = (LinearLayout) bottomNav.getChildAt(2);
        LinearLayout profilNav = (LinearLayout) bottomNav.getChildAt(3);

        // Configurer les listeners
        homeNav.setOnClickListener(v -> {
            Intent intent = new Intent(Vetement.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        offresNav.setOnClickListener(v -> {
            Intent intent = new Intent(Vetement.this, Offre.class);
            startActivity(intent);
        });

        historiqueNav.setOnClickListener(v -> {
            // Implémenter la navigation vers l'historique
            Intent intent = new Intent(Vetement.this, Historique.class);
            startActivity(intent);        });

        profilNav.setOnClickListener(v -> {
            // Implémenter la navigation vers le profil
            Toast.makeText(Vetement.this, "Profil utilisateur", Toast.LENGTH_SHORT).show();
        });
    }

    // Classe interne pour stocker les informations des produits
    private static class ProductInfo {
        private int productId;
        private String productName;
        private double price;
        private int quantity;

        public ProductInfo(int productId, String productName, double price, int quantity) {
            this.productId = productId;
            this.productName = productName;
            this.price = price;
            this.quantity = quantity;
        }

        public int getProductId() {
            return productId;
        }

        public String getProductName() {
            return productName;
        }

        public double getPrice() {
            return price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}