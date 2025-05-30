package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context context;
    private List<Order> orderList;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);

        holder.tvOrderNumber.setText(order.getOrderId());
        holder.tvOrderDate.setText(order.getOrderDate());
        holder.tvEstimatedDelivery.setText(order.getEstimatedDelivery());
        holder.tvOrderTotal.setText(String.format("%.2f Dhs", order.getTotalAmount()));
        holder.tvOrderStatus.setText(order.getStatus());

        // Définir la couleur du statut
        GradientDrawable statusBackground = (GradientDrawable) holder.tvOrderStatus.getBackground();
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

        holder.btnViewDetails.setOnClickListener(v -> {
            Intent intent = new Intent(context, OrderDetaille.class);
            intent.putExtra("ORDER", order);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderNumber;
        TextView tvOrderStatus;
        TextView tvOrderDate;
        TextView tvEstimatedDelivery;
        TextView tvOrderTotal;
        Button btnViewDetails;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderNumber = itemView.findViewById(R.id.tvOrderNumber);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvEstimatedDelivery = itemView.findViewById(R.id.tvEstimatedDelivery);
            tvOrderTotal = itemView.findViewById(R.id.tvOrderTotal);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails);
        }
    }
}
