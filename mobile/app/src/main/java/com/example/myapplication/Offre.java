package com.example.myapplication;

import android.content.Intent;
import android.graphics.Paint;
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

public class Offre extends AppCompatActivity {

    // Map pour stocker les informations des produits
    private Map<Integer, ProductInfo> productInfoMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offre);

        // Configurer le bouton de retour
        ImageButton btnRetour = findViewById(R.id.btnRetour);
        btnRetour.setOnClickListener(v -> finish());

        // Configurer le bouton du panier
        ImageButton btnPanier = findViewById(R.id.btnPanier);
        btnPanier.setOnClickListener(v -> {
            Intent intent = new Intent(Offre.this, CartActivity.class);
            startActivity(intent);
        });

        // Initialiser les produits
        initializeProducts();

        // Configurer les contrôles pour tous les produits
        setupProductControls();

        // Configurer la navigation du bas
        setupBottomNavigation();

        // Appliquer le style barré aux prix originaux
        applyStrikethroughToOriginalPrices();
    }

    private void applyStrikethroughToOriginalPrices() {
        // Appliquer le style barré aux prix originaux
        TextView tvOriginalPrice1 = findViewById(R.id.tvOriginalPrice1);
        TextView tvOriginalPrice2 = findViewById(R.id.tvOriginalPrice2);
        TextView tvOriginalPrice3 = findViewById(R.id.tvOriginalPrice3);
        TextView tvOriginalPrice4 = findViewById(R.id.tvOriginalPrice4);

        if (tvOriginalPrice1 != null) {
            tvOriginalPrice1.setPaintFlags(tvOriginalPrice1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (tvOriginalPrice2 != null) {
            tvOriginalPrice2.setPaintFlags(tvOriginalPrice2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (tvOriginalPrice3 != null) {
            tvOriginalPrice3.setPaintFlags(tvOriginalPrice3.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (tvOriginalPrice4 != null) {
            tvOriginalPrice4.setPaintFlags(tvOriginalPrice4.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    private void initializeProducts() {
        // Définir les informations des produits cadeaux
        productInfoMap.put(1, new ProductInfo(1, "Samsung Smart TV", 899.00, 1));
        productInfoMap.put(2, new ProductInfo(2, "Cartable Élève", 249.00, 1));
        productInfoMap.put(3, new ProductInfo(3, "Savon Hydratant", 49.00, 1));
        productInfoMap.put(4, new ProductInfo(4, "Pack de Lait", 39.00, 1));
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
                LinearLayout layout = (LinearLayout) child;

                // Vérifier si c'est le LinearLayout de quantité
                if (layout.getChildCount() >= 4 && layout.getChildAt(0) instanceof TextView &&
                        ((TextView) layout.getChildAt(0)).getText().toString().startsWith("Quantité")) {
                    for (int j = 0; j < layout.getChildCount(); j++) {
                        View quantityChild = layout.getChildAt(j);

                        if (quantityChild instanceof Button) {
                            Button button = (Button) quantityChild;
                            String buttonText = button.getText().toString();

                            if ("-".equals(buttonText)) {
                                btnSubtract = button;
                            } else if ("+".equals(buttonText)) {
                                btnAdd = button;
                            }
                        } else if (quantityChild instanceof TextView) {
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
            Intent intent = new Intent(Offre.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        offresNav.setOnClickListener(v -> {
            // Déjà sur la page des offres
            Toast.makeText(Offre.this, "Vous êtes déjà sur la page des offres", Toast.LENGTH_SHORT).show();
        });

        historiqueNav.setOnClickListener(v -> {
            // Implémenter la navigation vers l'historique
            Toast.makeText(Offre.this, "Historique des commandes", Toast.LENGTH_SHORT).show();
        });

        profilNav.setOnClickListener(v -> {
            // Implémenter la navigation vers le profil
            Toast.makeText(Offre.this, "Profil utilisateur", Toast.LENGTH_SHORT).show();
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

        public int getProductId() { return productId; }
        public String getProductName() { return productName; }
        public double getPrice() { return price; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }
}
