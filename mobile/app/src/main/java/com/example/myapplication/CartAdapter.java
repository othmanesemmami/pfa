package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItems;
    private Context context;
    private CartItemListener listener;

    // Interface pour gérer les événements sur les éléments du panier
    public interface CartItemListener {
        void onItemRemoved(CartItem item);
        void onCartUpdated();
    }

    public CartAdapter(Context context, CartItemListener listener) {
        this.context = context;
        this.cartItems = new ArrayList<>();
        this.listener = listener;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    // Méthode pour calculer le total du panier
    public double getCartTotal() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    // Méthode pour formater le prix
    private String formatPrice(double price) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.getDefault());
        return formatter.format(price);
    }

    // ViewHolder pour les éléments du panier
    class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView tvProductName, tvProductPrice, tvQuantity, tvItemTotal;
        private ImageButton btnRemove;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvItemTotal = itemView.findViewById(R.id.tvItemTotal);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }

        public void bind(final CartItem item) {
            tvProductName.setText(item.getProductName());
            tvProductPrice.setText(formatPrice(item.getPrice()));
            tvQuantity.setText(String.valueOf(item.getQuantity()));
            tvItemTotal.setText(formatPrice(item.getPrice() * item.getQuantity()));

            // Gérer la suppression d'un article
            btnRemove.setOnClickListener(v -> {
                removeItem(getAdapterPosition());
            });
        }

        private void removeItem(int position) {
            if (position != RecyclerView.NO_POSITION && position < cartItems.size()) {
                CartItem itemToRemove = cartItems.get(position);

                // Supprimer du CartManager
                CartManager.getInstance().removeFromCart(itemToRemove.getProductId());

                // Supprimer de la liste locale
                cartItems.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartItems.size());

                // Notifier le listener
                if (listener != null) {
                    listener.onItemRemoved(itemToRemove);
                    listener.onCartUpdated();
                }

                Toast.makeText(context, "Article supprimé", Toast.LENGTH_SHORT).show();
            }
        }
    }
}