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
import com.example.ecommercegestion.modele.Customer;
import java.util.ArrayList;


public class CustomerListAdapter extends BaseAdapter {
    private ArrayList<Customer> lesCustomer;
    private LayoutInflater inflater;
    private Control control;
    private Context context;

    public CustomerListAdapter(Context context, ArrayList<Customer> lesCustomer){
        this.context = context;
        this.control = Control.getInstance(null);
        this.lesCustomer = lesCustomer;
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * retourne le nombre de ligne de la list
     * @return
     */
    @Override
    public int getCount() {
        return lesCustomer.size();
    }

    /**
     * retourne l'item de la ligne actuel
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return lesCustomer.get(position);
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
        CustomerListAdapter.ViewHolder holder;
        // si la ligne n''existe pas encore
        if (view == null){
            Log.d("CONTACTLISTADAPTER", "holder est nul");
            holder = new CustomerListAdapter.ViewHolder();
            // la ligne est construite avec un formatage relier a layout_list_histo
            view = inflater.inflate(R.layout.layout_liste_user, null);
            // chaque propriete du holder est relier a une propriete graphique
            holder.txtListCustomerId = view.findViewById(R.id.txtListCustomerId);
            holder.txtListCustomerLastLogin = view.findViewById(R.id.txtListCustomerLastLogin);
            holder.txtListCustomerLastName = view.findViewById(R.id.txtListCustomerLastName);
            // affecter le holder (comme un tag)
            view.setTag(holder);
        }else {
            // recuperation du holder dans la ligne existante
            holder = (CustomerListAdapter.ViewHolder) view.getTag();
        }
        // valorisation du contenu du holder (donc de la ligne)
        Log.d("CustomerAdapter", "Valorisation du contenu");

        holder.txtListCustomerId.setText(String.format("ID %s", Integer.toString(lesCustomer.get(position).getId())));
        holder.txtListCustomerLastLogin.setText(String.format("Derniere Connection : %s", lesCustomer.get(position).getLast_login()));
        holder.txtListCustomerLastName.setText(String.format("%s %s",lesCustomer.get(position).getFirstName(), lesCustomer.get(position).getLastName()));

        holder.txtListCustomerId.setTag(position);
        holder.txtListCustomerLastLogin.setTag(position);
        holder.txtListCustomerLastName.setTag(position);

        // click sur le reste de la ligne pour afficher le customer dans ViewCustomerActivity
        holder.txtListCustomerId.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                int position = (int)v.getTag();
                // on demande de recuperer les addresse relatif a ce client
                //control.getInfoAboutCustomer(lesCustomer.get(position).getId());
                ((CustomerActivity)context).afficheCustomer(lesCustomer.get(position));
            }
        });

        // click sur le reste de la ligne pour afficher le customer dans ViewCustomerActivity
        holder.txtListCustomerLastLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                int position = (int)v.getTag();
                // on demande de recuperer les addresse relatif a ce client
                //control.getInfoAboutCustomer(lesCustomer.get(position).getId());
                ((CustomerActivity)context).afficheCustomer(lesCustomer.get(position));
            }
        });

        // click sur le reste de la ligne pour afficher le customer dans ViewCustomerActivity
        holder.txtListCustomerLastName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                int position = (int)v.getTag();
                // on demande de recuperer les addresse relatif a ce client
                //control.getInfoAboutCustomer(lesCustomer.get(position).getId());
                ((CustomerActivity)context).afficheCustomer(lesCustomer.get(position));
            }
        });

        return view;
    }

    private class ViewHolder{
        TextView txtListCustomerId;
        TextView txtListCustomerLastLogin;
        TextView txtListCustomerLastName;
    }

}

