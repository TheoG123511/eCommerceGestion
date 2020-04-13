package com.example.ecommercegestion.vue;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.ecommercegestion.R;
import com.example.ecommercegestion.controleur.Control;
import com.example.ecommercegestion.modele.Product;
import java.util.ArrayList;


public class ProductListAdapter extends BaseAdapter {
    private ArrayList<Product> lesProducts;
    private LayoutInflater inflater;
    private Control control;
    private Context context;

    public ProductListAdapter(Context context, ArrayList<Product> lesProducts){
        this.context = context;
        this.control = Control.getInstance(null);
        this.lesProducts = lesProducts;
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * Retourne le nombre de ligne de la list
     * @return le nombre d'object contenu dans l'array
     */
    @Override
    public int getCount() {
        return lesProducts.size();
    }

    /**
     * Retourne l'item de la ligne actuel
     * @param position: la position de l'object dans le tableaux
     * @return l'object contenu a l'index passer en parametre
     */
    @Override
    public Object getItem(int position) {
        return lesProducts.get(position);
    }

    /**
     * retourne un indice par rapport a la ligne actuel
     * @param position : la position actuel
     * @return : la position
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
        ProductListAdapter.ViewHolder holder;
        // si la ligne n''existe pas encore
        if (view == null){
            holder = new ProductListAdapter.ViewHolder();
            // la ligne est construite avec un formatage relier a layout_list_histo
            view = inflater.inflate(R.layout.layout_liste_product, null);
            // chaque propriete du holder est relier a une propriete graphique
            holder.txtListProductName = view.findViewById(R.id.txtListProductName);
            holder.txtListProductQuantity = view.findViewById(R.id.txtListProductQuantity);
            holder.txtlistProductAddTime = view.findViewById(R.id.txtlistProductAddTime);
            holder.btnListProductDel = view.findViewById(R.id.btnListProductDel);
            // affecter le holder (comme un tag)
            view.setTag(holder);
        }else {
            // recuperation du holder dans la ligne existante
            holder = (ProductListAdapter.ViewHolder) view.getTag();
        }
        // valorisation du contenu du holder (donc de la ligne)
        holder.txtListProductName.setText(String.format("Nom du Produit : %s", lesProducts.get(position).getName()));
        holder.txtListProductQuantity.setText(String.format("Quantité Disponible : %s", Integer.toString(lesProducts.get(position).getQuantity())));
        holder.txtlistProductAddTime.setText(String.format("Date Dajout : %s", lesProducts.get(position).getDate()));

        holder.txtListProductName.setTag(position);
        holder.txtListProductQuantity.setTag(position);
        holder.txtlistProductAddTime.setTag(position);
        holder.btnListProductDel.setTag(position);
        control.setComeToListProduct(true);
        // click sur le reste de la ligne pour afficher le produit dans AddProductActivity
        holder.txtListProductName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                int position = (int)v.getTag();
                ((ProductActivity)context).afficheProduct(lesProducts.get(position));
            }
        });

        // click sur le reste de la ligne pour afficher le produit dans AddProductActivity
        holder.txtListProductQuantity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                int position = (int)v.getTag();
                ((ProductActivity)context).afficheProduct(lesProducts.get(position));
            }
        });

        // click sur le reste de la ligne pour afficher le produit dans AddProductActivity
        holder.txtlistProductAddTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                int position = (int)v.getTag();
                ((ProductActivity)context).afficheProduct(lesProducts.get(position));
            }
        });

        // click sur la croix pour supprimer le produit enregistrer
        holder.btnListProductDel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                int position = (int)v.getTag();
                // demande la suppresion au controleur
                control.delProduct(lesProducts.get(position));
                // rafraichir la liste
                notifyDataSetChanged();
            }
        });

        return view;
    }

    private class ViewHolder{
        ImageButton btnListProductDel;
        TextView txtListProductName;
        TextView txtListProductQuantity;
        TextView txtlistProductAddTime;
    }
}
