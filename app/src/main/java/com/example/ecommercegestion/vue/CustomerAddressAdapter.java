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
import com.example.ecommercegestion.modele.Address;
import java.util.ArrayList;


public class CustomerAddressAdapter extends BaseAdapter {
    private ArrayList<Address> lesAddress;
    private LayoutInflater inflater;
    private Control control;
    private Context context;

    public CustomerAddressAdapter(Context context, ArrayList<Address> lesAddress){
        this.context = context;
        this.control = Control.getInstance(null);
        this.lesAddress = lesAddress;
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * retourne le nombre de ligne de la list
     * @return
     */
    @Override
    public int getCount() {
        return lesAddress.size();
    }

    /**
     * retourne l'item de la ligne actuel
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return lesAddress.get(position);
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
        CustomerAddressAdapter.ViewHolder holder;
        // si la ligne n''existe pas encore
        if (view == null){
            Log.d("CustomerAddressAdapter", "holder est nul");
            holder = new CustomerAddressAdapter.ViewHolder();
            // la ligne est construite avec un formatage relier a layout_list_histo
            view =  inflater.inflate(R.layout.layout_liste_address, null);

            // chaque propriete du holder est relier a une propriete graphique
            holder.txtListAddressFirstName = view.findViewById(R.id.txtListAddressFirstName);
            holder.txtListAddressLastName = view.findViewById(R.id.txtListAddressLastName);
            holder.txtListAddressAddr = view.findViewById(R.id.txtListAddressAddr);
            holder.txtListAddressCity = view.findViewById(R.id.txtListAddressCity);
            holder.txtListAddressCp = view.findViewById(R.id.txtListAddressCp);
            holder.txtListAddressCountry = view.findViewById(R.id.txtListAddressCountry);
            holder.txtListAddressMobile = view.findViewById(R.id.txtListAddressMobile);

            // affecter le holder (comme un tag)
            view.setTag(holder);
        }else {
            // recuperation du holder dans la ligne existante
            holder = (CustomerAddressAdapter.ViewHolder) view.getTag();
        }
        // valorisation du contenu du holder (donc de la lignee

        holder.txtListAddressFirstName.setText(String.format("Prénom : %s", lesAddress.get(position).getFirstName()));
        holder.txtListAddressLastName.setText(String.format("Nom: %s", lesAddress.get(position).getLastName()));
        holder.txtListAddressAddr.setText(String.format("Addresse de Livraison : %s", lesAddress.get(position).getAddress()));
        holder.txtListAddressCity.setText(String.format("Ville : %s", lesAddress.get(position).getCity()));
        holder.txtListAddressCp.setText(String.format("Code Postal : %s", lesAddress.get(position).getCp()));
        holder.txtListAddressCountry.setText(String.format("Pays : %s", lesAddress.get(position).getCountry()));
        holder.txtListAddressMobile.setText(String.format("Téléphone : %s", lesAddress.get(position).getMobile()));

        holder.txtListAddressFirstName.setTag(position);
        holder.txtListAddressLastName.setTag(position);
        holder.txtListAddressAddr.setTag(position);
        holder.txtListAddressCity.setTag(position);
        holder.txtListAddressCp.setTag(position);
        holder.txtListAddressCountry.setTag(position);
        holder.txtListAddressMobile.setTag(position);

        // measure ListView item (to solve 'ListView inside ScrollView' problem)
        view.measure(View.MeasureSpec.makeMeasureSpec(
                View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        return view;
    }

    private class ViewHolder{
        TextView txtListAddressFirstName;
        TextView txtListAddressLastName;
        TextView txtListAddressAddr;
        TextView txtListAddressCity;
        TextView txtListAddressCp;
        TextView txtListAddressCountry;
        TextView txtListAddressMobile;
    }
}
