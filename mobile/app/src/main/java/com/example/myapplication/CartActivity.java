package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity implements CartAdapter.CartItemListener {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private TextView tvEmptyCart, tvTotal;
    private Button btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Initialiser les vues
        recyclerView = findViewById(R.id.recyclerViewCart);
        tvEmptyCart = findViewById(R.id.emptyCartView);
        tvTotal = findViewById(R.id.tvTotalPrice);
        btnCheckout = findViewById(R.id.btnCheckout);

        // Configurer le RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CartAdapter(this, this);
        recyclerView.setAdapter(adapter);

        // Charger les éléments du panier
        loadCartItems();

        // Configurer le bouton de paiement
        btnCheckout.setOnClickListener(v -> {
            if (adapter.getItemCount() > 0) {
                // Afficher une boîte de dialogue de confirmation
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirmer la commande");
                builder.setMessage("Voulez-vous confirmer cette commande?");
                builder.setPositiveButton("Confirmer", (dialog, which) -> {
                    confirmOrder();
                });
                builder.setNegativeButton("Annuler", null);
                builder.show();
            } else {
                Toast.makeText(this, "Votre panier est vide", Toast.LENGTH_SHORT).show();
            }
        });

        // Configurer le bouton de retour
        findViewById(R.id.btnRetour).setOnClickListener(v -> finish());
    }

    private void confirmOrder() {
        // Récupérer les éléments du panier
        List<CartItem> cartItems = CartManager.getInstance().getCartItems();

        if (cartItems.isEmpty()) {
            Toast.makeText(this, "Votre panier est vide", Toast.LENGTH_SHORT).show();
            return;
        }

        // Générer un ID de commande unique (implémentation simple)
        String orderId = String.valueOf(System.currentTimeMillis()).substring(5);

        // Obtenir la date actuelle
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());

        // Calculer la date de livraison estimée (3 jours à partir de maintenant)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        String estimatedDelivery = dateFormat.format(calendar.getTime());

        // Calculer le prix total
        double totalPrice = adapter.getCartTotal();

        // Créer une nouvelle commande
        Order newOrder = new Order(orderId, currentDate, estimatedDelivery, totalPrice, "En cours");

        // Convertir les éléments du panier en éléments de commande
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem(
                    String.valueOf(cartItem.getProductId()),
                    cartItem.getProductName(),
                    "Produit de qualité supérieure", // Description par défaut
                    cartItem.getPrice(),
                    cartItem.getQuantity(),
                    R.drawable.a1 // Image par défaut
            );
            orderItems.add(orderItem);
        }

        // Ajouter les éléments à la commande
        newOrder.setItems(orderItems);

        // Enregistrer la commande dans OrderManager
        OrderManager.getInstance().addOrder(newOrder);

        // Vider le panier
        CartManager.getInstance().clearCart();

        // Afficher un message de succès
        Toast.makeText(this, "Commande confirmée avec succès!", Toast.LENGTH_SHORT).show();

        // Rediriger vers l'historique des commandes
        Intent intent = new Intent(CartActivity.this, Historique.class);
        startActivity(intent);
        finish();
    }

    private void loadCartItems() {
        // Récupérer les éléments du panier depuis le CartManager
        List<CartItem> cartItems = CartManager.getInstance().getCartItems();

        // Mettre à jour l'adaptateur
        adapter.setCartItems(cartItems);

        // Mettre à jour l'interface utilisateur
        updateUI();
    }

    private void updateUI() {
        if (adapter.getItemCount() == 0) {
            tvEmptyCart.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            btnCheckout.setEnabled(false);
        } else {
            tvEmptyCart.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            btnCheckout.setEnabled(true);
        }

        // Mettre à jour le total
        double total = adapter.getCartTotal();
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.getDefault());
        tvTotal.setText("Total: " + formatter.format(total));
    }

    @Override
    public void onItemRemoved(CartItem item) {
        // Cette méthode est appelée lorsqu'un article est supprimé
        updateUI();
    }

    @Override
    public void onCartUpdated() {
        // Cette méthode est appelée lorsque le panier est mis à jour
        updateUI();
    }
}