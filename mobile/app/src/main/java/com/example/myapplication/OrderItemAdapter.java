package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder> {

    private Context context;
    private List<OrderItem> orderItems;

    public OrderItemAdapter(Context context, List<OrderItem> orderItems) {
        this.context = context;
        this.orderItems = orderItems;
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Utiliser le bon layout pour les éléments de commande
        View view = LayoutInflater.from(context).inflate(R.layout.order_item_detail, parent, false);
        return new OrderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        OrderItem orderItem = orderItems.get(position);

        holder.tvProductName.setText(orderItem.getItemName());
        holder.tvProductDescription.setText(orderItem.getDescription());
        holder.tvProductPrice.setText(String.format("%.2f Dhs", orderItem.getPrice()));
        holder.tvProductQuantity.setText(String.valueOf(orderItem.getQuantity()));
        holder.tvProductTotal.setText(String.format("%.2f Dhs", orderItem.getTotal()));

        // Charger l'image du produit
        holder.ivProductImage.setImageResource(orderItem.getImageResourceId());
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public static class OrderItemViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProductImage;
        TextView tvProductName;
        TextView tvProductDescription;
        TextView tvProductPrice;
        TextView tvProductQuantity;
        TextView tvProductTotal;

        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductDescription = itemView.findViewById(R.id.tvProductDescription);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvProductQuantity = itemView.findViewById(R.id.tvProductQuantity);
            tvProductTotal = itemView.findViewById(R.id.tvProductTotal);
        }
    }
}