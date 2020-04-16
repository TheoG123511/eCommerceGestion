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
import com.example.ecommercegestion.modele.Order;
import java.util.ArrayList;


public class OrderListAdapter extends BaseAdapter {


    private ArrayList<Order> lesOrders;
    private LayoutInflater inflater;
    private Control control;
    private Context context;

    public OrderListAdapter(Context context, ArrayList<Order> lesOrders){
        this.context = context;
        this.control = Control.getInstance(null);
        this.lesOrders = lesOrders;
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * retourne le nombre de ligne de la list
     * @return
     */
    @Override
    public int getCount() {
        return lesOrders.size();
    }

    /**
     * retourne l'item de la ligne actuel
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return lesOrders.get(position);
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
        OrderListAdapter.ViewHolder holder;
        // si la ligne n''existe pas encore
        if (view == null){
            Log.d("OrderListAdapter", "holder est nul");
            holder = new OrderListAdapter.ViewHolder();
            // la ligne est construite avec un formatage relier a layout_list_histo
            view = inflater.inflate(R.layout.layout_liste_order, null);
            // chaque propriete du holder est relier a une propriete graphique
            holder.txtListOrderId = view.findViewById(R.id.txtListOrderId);
            holder.txtListOrderState = view.findViewById(R.id.txtListOrderState);
            holder.txtListOrderStartTime = view.findViewById(R.id.txtListOrderStartTime);
            // affecter le holder (comme un tag)
            view.setTag(holder);
        }else {
            // recuperation du holder dans la ligne existante
            holder = (OrderListAdapter.ViewHolder) view.getTag();
        }
        // valorisation du contenu du holder (donc de la ligne)
        Log.d("OrderListAdapter", "Valorisation du contenu");
        // on verifie si cette adapter est destiner a la classe ViewCustomerActivity ou a OrderActivity
        if (context instanceof ViewCustomerActivity) {
            holder.txtListOrderId.setText(String.format("Methode de Payement : %s", lesOrders.get(position).getMethodPayment()));
        }else {
            holder.txtListOrderId.setText(String.format("Identifiant Utilisateur : %s", Integer.toString(lesOrders.get(position).getCustomerId())));
        }
        holder.txtListOrderState.setText(String.format("Commande : %s", lesOrders.get(position).getStatus()));
        holder.txtListOrderStartTime.setText(String.format("Debut de la Commande : %s", lesOrders.get(position).getStartDate()));

        holder.txtListOrderId.setTag(position);
        holder.txtListOrderState.setTag(position);
        holder.txtListOrderStartTime.setTag(position);

        if (context instanceof ViewCustomerActivity){
            // click sur le reste de la ligne pour afficher le commande dans ViewOrderActivity
            holder.txtListOrderId.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v){
                    int position = (int)v.getTag();
                    ((ViewCustomerActivity)context).afficheOrder(lesOrders.get(position));
                }
            });

            // click sur le reste de la ligne pour afficher le commande dans ViewOrderActivity
            holder.txtListOrderState.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v){
                    int position = (int)v.getTag();
                    ((ViewCustomerActivity)context).afficheOrder(lesOrders.get(position));
                }
            });

            // click sur le reste de la ligne pour afficher le commande dans ViewOrderActivity
            holder.txtListOrderStartTime.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v){
                    int position = (int)v.getTag();
                    ((ViewCustomerActivity)context).afficheOrder(lesOrders.get(position));
                }
            });
        }else {
            // click sur le reste de la ligne pour afficher le commande dans ViewOrderActivity
            holder.txtListOrderId.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v){
                    int position = (int)v.getTag();
                    ((OrderActivity)context).afficheOrder(lesOrders.get(position));
                }
            });

            // click sur le reste de la ligne pour afficher le commande dans ViewOrderActivity
            holder.txtListOrderState.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v){
                    int position = (int)v.getTag();
                    ((OrderActivity)context).afficheOrder(lesOrders.get(position));
                }
            });

            // click sur le reste de la ligne pour afficher le commande dans ViewOrderActivity
            holder.txtListOrderStartTime.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v){
                    int position = (int)v.getTag();
                    ((OrderActivity)context).afficheOrder(lesOrders.get(position));
                }
            });
        }


        return view;
    }

    private class ViewHolder{
        TextView txtListOrderId;
        TextView txtListOrderState;
        TextView txtListOrderStartTime;
    }
}
