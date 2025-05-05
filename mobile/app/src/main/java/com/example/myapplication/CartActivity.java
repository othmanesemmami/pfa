package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
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
                // Implémenter la logique de paiement ici
                Toast.makeText(this, "Paiement en cours...", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Votre panier est vide", Toast.LENGTH_SHORT).show();
            }
        });
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