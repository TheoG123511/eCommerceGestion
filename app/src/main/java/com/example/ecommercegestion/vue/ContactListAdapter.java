package com.example.ecommercegestion.vue;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.ecommercegestion.R;
import com.example.ecommercegestion.controleur.Control;
import com.example.ecommercegestion.modele.Contact;
import java.util.ArrayList;


class ContactListAdapter extends BaseAdapter {

    private ArrayList<Contact> lesContacts;
    private LayoutInflater inflater;
    private Control control;
    private Context context;

    public ContactListAdapter(Context context, ArrayList<Contact> lesContacts){
        this.context = context;
        this.control = Control.getInstance(null);
        this.lesContacts = lesContacts;
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * retourne le nombre de ligne de la list
     * @return
     */
    @Override
    public int getCount() {
        return lesContacts.size();
    }

    /**
     * retourne l'item de la ligne actuel
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return lesContacts.get(position);
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
        ViewHolder holder;
        // si la ligne n''existe pas encore
        if (view == null){
            Log.d("CONTACTLISTADAPTER", "holder est nul");
            holder = new ViewHolder();
            // la ligne est construite avec un formatage relier a layout_list_histo
            view = inflater.inflate(R.layout.layout_liste_contact, null);
            // chaque propriete du holder est relier a une propriete graphique
            holder.txtListContactDate = view.findViewById(R.id.txtListContactDate);
            holder.txtListContactSubject = view.findViewById(R.id.txtListContactSubject);
            holder.btnListContactDelImg =  view.findViewById(R.id.btnListContactSuppr);
            // affecter le holder (comme un tag)
            view.setTag(holder);
        }else {
            // recuperation du holder dans la ligne existante
            holder = (ViewHolder) view.getTag();
        }
        // valorisation du contenu du holder (donc de la ligne)
        Log.d("CONTACTLISTADAPTER", "Valorisation du contenu");
        holder.txtListContactDate.setText(lesContacts.get(position).getDate());
        holder.txtListContactSubject.setText(lesContacts.get(position).getSubject());
        holder.txtListContactDate.setTag(position);
        holder.txtListContactSubject.setTag(position);
        holder.btnListContactDelImg.setTag(position);

        // click sur le reste de la ligne pour afficher le contact dans ViewContactActivity
        holder.txtListContactSubject.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                int position = (int)v.getTag();
                ((ContactActivity)context).afficheContact(lesContacts.get(position));
            }
        });

        // click sur le reste de la ligne pour afficher le contact dans ViewContactActivity
        holder.txtListContactDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                int position = (int)v.getTag();
                ((ContactActivity)context).afficheContact(lesContacts.get(position));
            }
        });

        // click sur la croix pour supprimer le contact enregistrer
        holder.btnListContactDelImg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                int position = (int)v.getTag();
                // demande la suppresion au controleur
                control.delContact(lesContacts.get(position));
                // rafraichir la liste
                notifyDataSetChanged();
            }
        });

        return view;
    }

    private class ViewHolder{
        ImageButton btnListContactDelImg;
        TextView txtListContactDate;
        TextView txtListContactSubject;
    }

}
