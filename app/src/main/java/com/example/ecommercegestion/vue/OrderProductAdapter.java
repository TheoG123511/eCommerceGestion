package com.example.ecommercegestion.vue;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.ecommercegestion.R;
import com.example.ecommercegestion.controleur.Control;
import com.example.ecommercegestion.modele.OrderProduct;
import java.util.ArrayList;



public class OrderProductAdapter extends BaseAdapter {

    private ArrayList<OrderProduct> lesOrdersProducts;
    private LayoutInflater inflater;
    private Control control;
    private Context context;

    public OrderProductAdapter(Context context, ArrayList<OrderProduct> lesOrdersProducts){
        this.context = context;
        this.control = Control.getInstance(null);
        this.lesOrdersProducts = lesOrdersProducts;
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * retourne le nombre de ligne de la list
     * @return
     */
    @Override
    public int getCount() {
        return lesOrdersProducts.size();
    }

    /**
     * retourne l'item de la ligne actuel
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return lesOrdersProducts.get(position);
    }

    /**
     * retourne un indice par rapport a la ligne actuel
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * retourne la ligne (view) formater avec gestion des evenements
     * @param position
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        // declaration d'un holder
        OrderProductAdapter.ViewHolder holder;
        // si la ligne n''existe pas encore
        if (view == null){
            Log.d("OrderProductAdapter", "holder est nul");
            holder = new OrderProductAdapter.ViewHolder();
            // la ligne est construite avec un formatage relier a layout_list_histo
            view = inflater.inflate(R.layout.layout_liste_order_product, null);
            // chaque propriete du holder est relier a une propriete graphique
            holder.txtListOrderProductName = view.findViewById(R.id.txtListOrderProductName);
            holder.txtListOrderProductQuantity = view.findViewById(R.id.txtListOrderProductQuantity);
            holder.txtListOrderProductPrice = view.findViewById(R.id.txtListOrderProductPrice);
            // affecter le holder (comme un tag)
            view.setTag(holder);
        }else {
            // recuperation du holder dans la ligne existante
            holder = (OrderProductAdapter.ViewHolder) view.getTag();
        }
        // valorisation du contenu du holder (donc de la ligne)
        Log.d("OrderProductAdapter", "Valorisation du contenu");

        // on verifie si cette adapter est destiner a la classe ViewCustomerActivity ou a OrderActivity
        holder.txtListOrderProductName.setText(String.format("Nom du Produit : %s", lesOrdersProducts.get(position).getProductName()));
        holder.txtListOrderProductQuantity.setText(String.format("Quantité Commander : %s", lesOrdersProducts.get(position).getQuantity()));
        holder.txtListOrderProductPrice.setText(String.format("Prix Unitaire : %s euros", lesOrdersProducts.get(position).getProductPrice()));

        holder.txtListOrderProductName.setTag(position);
        holder.txtListOrderProductQuantity.setTag(position);
        holder.txtListOrderProductPrice.setTag(position);

        return view;
    }

    private class ViewHolder{
        TextView txtListOrderProductName;
        TextView txtListOrderProductQuantity;
        TextView txtListOrderProductPrice;
    }
}
