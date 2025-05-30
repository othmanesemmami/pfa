package com.example.myapplication;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderDetaille extends AppCompatActivity {

    private Order order;
    private RecyclerView recyclerViewItems;
    private OrderItemAdapter orderItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detaille);

        // Récupérer l'objet Order de l'intent
        order = (Order) getIntent().getSerializableExtra("ORDER");
        if (order == null) {
            Toast.makeText(this, "Erreur: Commande introuvable", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Configurer le bouton de retour
        ImageButton btnRetour = findViewById(R.id.btnRetour);
        btnRetour.setOnClickListener(v -> finish());

        // Configurer les vues avec les données de la commande
        TextView tvOrderNumber = findViewById(R.id.tvOrderNumber);
        TextView tvOrderDate = findViewById(R.id.tvOrderDate);
        TextView tvOrderStatus = findViewById(R.id.tvOrderStatus);
        TextView tvOrderTotal = findViewById(R.id.tvOrderTotal);

        tvOrderNumber.setText(order.getOrderId());
        tvOrderDate.setText(order.getOrderDate());
        tvOrderStatus.setText(order.getStatus());
        tvOrderTotal.setText(String.format("%.2f Dhs", order.getTotalAmount()));

        // Définir la couleur du statut
        GradientDrawable statusBackground = (GradientDrawable) tvOrderStatus.getBackground();
        switch (order.getStatus().toLowerCase()) {
            case "livré":
                statusBackground.setColor(Color.parseColor("#4CAF50")); // Vert
                break;
            case "en cours":
                statusBackground.setColor(Color.parseColor("#2196F3")); // Bleu
                break;
            case "annulé":
                statusBackground.setColor(Color.parseColor("#F44336")); // Rouge
                break;
            case "en attente":
                statusBackground.setColor(Color.parseColor("#FF9800")); // Orange
                break;
            default:
                statusBackground.setColor(Color.parseColor("#9E9E9E")); // Gris
                break;
        }

        // Configurer le RecyclerView pour les éléments de la commande
        recyclerViewItems = findViewById(R.id.recyclerViewOrderItems);
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));
        orderItemAdapter = new OrderItemAdapter(this, order.getItems());
        recyclerViewItems.setAdapter(orderItemAdapter);

        // Configurer le bouton de suivi de commande
        Button btnTrackOrder = findViewById(R.id.btnTrackOrder);
        if ("Livré".equalsIgnoreCase(order.getStatus()) || "Annulé".equalsIgnoreCase(order.getStatus())) {
            btnTrackOrder.setVisibility(View.GONE);
        } else {
            btnTrackOrder.setOnClickListener(v -> {
                Toast.makeText(this, "Suivi de la commande " + order.getOrderId(), Toast.LENGTH_SHORT).show();
                // Implémenter la fonctionnalité de suivi ici
            });
        }
    }
}