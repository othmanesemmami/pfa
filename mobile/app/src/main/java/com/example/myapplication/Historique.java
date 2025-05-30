package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Historique extends AppCompatActivity {

    private RecyclerView recyclerViewOrders;
    private OrderAdapter orderAdapter;
    private List<Order> orderList;
    private TextView tvEmptyHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        // Configurer le bouton de retour
        ImageButton btnRetour = findViewById(R.id.btnRetour);
        btnRetour.setOnClickListener(v -> finish());

        // Configurer le bouton du panier
        ImageButton btnPanier = findViewById(R.id.btnPanier);
        btnPanier.setOnClickListener(v -> {
            Intent intent = new Intent(Historique.this, CartActivity.class);
            startActivity(intent);
        });

        // Trouver la vue pour l'historique vide
        tvEmptyHistory = findViewById(R.id.tvEmptyHistory);
        if (tvEmptyHistory == null) {
            // Si la vue n'existe pas dans le layout, créez-la programmatiquement
            tvEmptyHistory = new TextView(this);
            tvEmptyHistory.setText("Vous n'avez pas encore de commandes");
            tvEmptyHistory.setTextSize(16);
            tvEmptyHistory.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            // Ajoutez-la au layout parent
            View parent = findViewById(android.R.id.content);
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).addView(tvEmptyHistory);
            }
        }

        // Configurer le RecyclerView
        recyclerViewOrders = findViewById(R.id.recyclerViewOrders);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));

        // Initialiser la liste des commandes
        orderList = getOrders();

        // Configurer l'adaptateur
        orderAdapter = new OrderAdapter(this, orderList);
        recyclerViewOrders.setAdapter(orderAdapter);

        // Mettre à jour l'UI
        updateUI();

        // Configurer la navigation du bas
        setupBottomNavigation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recharger les commandes à chaque fois que l'activité devient visible
        orderList = getOrders();
        orderAdapter.setOrderList(orderList);
        updateUI();
    }

    private void updateUI() {
        if (orderList.isEmpty()) {
            recyclerViewOrders.setVisibility(View.GONE);
            tvEmptyHistory.setVisibility(View.VISIBLE);
        } else {
            recyclerViewOrders.setVisibility(View.VISIBLE);
            tvEmptyHistory.setVisibility(View.GONE);
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
            Intent intent = new Intent(Historique.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        offresNav.setOnClickListener(v -> {
            Intent intent = new Intent(Historique.this, Offre.class);
            startActivity(intent);
        });

        historiqueNav.setOnClickListener(v -> {
            // Déjà sur la page d'historique
            Toast.makeText(Historique.this, "Vous êtes déjà sur la page d'historique", Toast.LENGTH_SHORT).show();
        });

        profilNav.setOnClickListener(v -> {
            // Implémenter la navigation vers le profil
            Toast.makeText(Historique.this, "Profil utilisateur", Toast.LENGTH_SHORT).show();
        });
    }

    private List<Order> getOrders() {
        // Récupérer les commandes depuis OrderManager
        List<Order> orders = OrderManager.getInstance().getOrders();

        // Si aucune commande n'existe encore, ajouter quelques commandes fictives pour démonstration
        if (orders.isEmpty()) {
            // Commande 1
            Order order1 = new Order("12345", "12/05/2023", "15/05/2023", 350.00, "Livré");
            List<OrderItem> items1 = new ArrayList<>();
            items1.add(new OrderItem("1", "Pizza Margherita", "Pâte fine, sauce tomate maison, mozzarella, basilic.", 65.00, 2, R.drawable.a1));
            items1.add(new OrderItem("2", "Tacos Mexicain", "Tortilla de maïs, poulet mariné, salsa piquante, guacamole maison.", 55.00, 4, R.drawable.a1));
            order1.setItems(items1);
            orders.add(order1);

            // Commande 2
            Order order2 = new Order("12346", "10/05/2023", "13/05/2023", 420.50, "En cours");
            List<OrderItem> items2 = new ArrayList<>();
            items2.add(new OrderItem("3", "Poulet Frit", "6 pièces marinées, panées, sauces au choix.", 60.00, 3, R.drawable.a1));
            items2.add(new OrderItem("4", "Burger Spécial", "Steak 150g, cheddar, salade, tomate, frites maison.", 75.00, 2, R.drawable.a1));
            items2.add(new OrderItem("5", "Boisson Gazeuse", "Canette 33cl, au choix.", 15.00, 5, R.drawable.a1));
            order2.setItems(items2);
            orders.add(order2);

            // Commande 3
            Order order3 = new Order("12347", "08/05/2023", "11/05/2023", 150.75, "Annulé");
            List<OrderItem> items3 = new ArrayList<>();
            items3.add(new OrderItem("6", "Salade César", "Laitue romaine, poulet grillé, croûtons, parmesan.", 45.00, 2, R.drawable.a1));
            items3.add(new OrderItem("7", "Dessert du jour", "Pâtisserie fraîche du jour.", 30.00, 2, R.drawable.a1));
            order3.setItems(items3);
            orders.add(order3);

            // Commande 4
            Order order4 = new Order("12348", "05/05/2023", "08/05/2023", 275.25, "Livré");
            List<OrderItem> items4 = new ArrayList<>();
            items4.add(new OrderItem("8", "Sushi Combo", "Assortiment de 24 pièces variées.", 120.00, 1, R.drawable.a1));
            items4.add(new OrderItem("9", "Soupe Miso", "Bouillon traditionnel japonais.", 25.00, 3, R.drawable.a1));
            order4.setItems(items4);
            order4.setReviewed(true);
            orders.add(order4);
        }

        return orders;
    }
}