package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Food extends AppCompatActivity {

    // Map pour stocker les informations des produits
    private Map<Integer, ProductInfo> productInfoMap = new HashMap<>();
    // Liste des CardViews pour chaque produit
    private List<CardView> productCards = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        // Configurer le bouton de retour
        ImageButton btnRetour = findViewById(R.id.btnRetour);
        btnRetour.setOnClickListener(v -> finish());

        // Configurer le bouton du panier
        ImageButton btnPanier = findViewById(R.id.btnPanier);
        btnPanier.setOnClickListener(v -> {
            Intent intent = new Intent(Food.this, CartActivity.class);
            startActivity(intent);
        });

        // Initialiser les produits
        initializeProducts();

        // Stocker les références aux CardViews des produits
        storeProductCards();

        // Configurer les contrôles pour tous les produits
        setupProductControls();

        // Configurer la navigation du bas
        setupBottomNavigation();

        // Configurer la barre de recherche
        setupSearchBar();
    }

    private void initializeProducts() {
        // Définir les informations des produits
        productInfoMap.put(1, new ProductInfo(1, "Pizza Margherita", 65.00, 1, "Pâte fine, sauce tomate maison, mozzarella, basilic."));
        productInfoMap.put(2, new ProductInfo(2, "Tacos Mexicain", 55.00, 1, "Tortilla de maïs, poulet mariné, salsa piquante, guacamole maison, crème fraîche et fromage râpé."));
        productInfoMap.put(3, new ProductInfo(3, "Poulet Frit", 60.00, 1, "6 pièces marinées, panées, sauces au choix."));
        productInfoMap.put(4, new ProductInfo(4, "Burger Spécial", 75.00, 1, "Steak 150g, cheddar, salade, tomate, frites maison."));
    }

    private void storeProductCards() {
        // Trouver toutes les CardView dans le GridLayout
        ViewGroup gridProducts = findViewById(R.id.gridProducts);
        int childCount = gridProducts.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = gridProducts.getChildAt(i);
            if (child instanceof CardView) {
                productCards.add((CardView) child);
            }
        }
    }

    private void setupSearchBar() {
        EditText searchBar = findViewById(R.id.searchBar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Non utilisé
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Non utilisé
            }

            @Override
            public void afterTextChanged(Editable s) {
                filterProducts(s.toString());
            }
        });
    }

    private void filterProducts(String query) {
        query = query.toLowerCase().trim();

        // Si la requête est vide, afficher tous les produits
        if (query.isEmpty()) {
            for (CardView card : productCards) {
                card.setVisibility(View.VISIBLE);
            }
            return;
        }

        // Parcourir chaque carte de produit
        for (int i = 0; i < productCards.size(); i++) {
            CardView card = productCards.get(i);
            ProductInfo info = productInfoMap.get(i + 1); // i+1 car les IDs commencent à 1

            if (info != null) {
                // Vérifier si le nom ou la description du produit contient la requête
                boolean matches = info.getProductName().toLowerCase().contains(query) ||
                        info.getDescription().toLowerCase().contains(query);

                // Afficher ou masquer la carte en fonction du résultat
                card.setVisibility(matches ? View.VISIBLE : View.GONE);
            }
        }
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
                        // Vérifier si c'est le TextView de quantité (pas le label "Quantité : ")
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
            Intent intent = new Intent(Food.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        offresNav.setOnClickListener(v -> {
            Intent intent = new Intent(Food.this, Offre.class);
            startActivity(intent);
        });

        historiqueNav.setOnClickListener(v -> {
            // Implémenter la navigation vers l'historique
            Intent intent = new Intent(Food.this, Historique.class);
            startActivity(intent);
        });

        profilNav.setOnClickListener(v -> {
            // Implémenter la navigation vers le profil
            Toast.makeText(Food.this, "Profil utilisateur", Toast.LENGTH_SHORT).show();
        });
    }

    // Classe interne pour stocker les informations des produits
    private static class ProductInfo {
        private int productId;
        private String productName;
        private double price;
        private int quantity;
        private String description;

        public ProductInfo(int productId, String productName, double price, int quantity, String description) {
            this.productId = productId;
            this.productName = productName;
            this.price = price;
            this.quantity = quantity;
            this.description = description;
        }

        public int getProductId() { return productId; }
        public String getProductName() { return productName; }
        public double getPrice() { return price; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public String getDescription() { return description; }
    }
}