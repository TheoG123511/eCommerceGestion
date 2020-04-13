package com.example.ecommercegestion.vue;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ecommercegestion.R;
import com.example.ecommercegestion.controleur.Control;
import com.example.ecommercegestion.modele.Address;
import com.example.ecommercegestion.modele.Order;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


public class ViewCustomerActivity extends AppCompatActivity {

    private Control controle;
    private TextView txtViewCustomerLastlogin;
    private TextView txtViewCustomerDateJointed;
    private TextView txtViewCustomerUsername;
    private TextView txtViewCustomerFirstName;
    private TextView txtViewCustomerLastName;
    private TextView txtViewCustomerEmail;
    private TextView txtViewCustomerMobile;
    private TextView txtViewCustomerGender;
    private TextView txtViewCustomerIsNewsLetter;
    private ImageButton btnViewCustomerRefreshImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer);
        init();
    }

    /**
     * Permet d'initialiser l'activity
     */
    private void init(){
        txtViewCustomerLastlogin = findViewById(R.id.txtViewCustomerLastlogin);
        txtViewCustomerDateJointed = findViewById(R.id.txtViewCustomerDateJointed);
        txtViewCustomerUsername = findViewById(R.id.txtViewCustomerUsername);
        txtViewCustomerFirstName = findViewById(R.id.txtViewCustomerFirstName);
        txtViewCustomerLastName = findViewById(R.id.txtViewCustomerLastName);
        txtViewCustomerEmail = findViewById(R.id.txtViewCustomerEmail);
        txtViewCustomerMobile = findViewById(R.id.txtViewCustomerMobile);
        txtViewCustomerGender = findViewById(R.id.txtViewCustomerGender);
        txtViewCustomerIsNewsLetter = findViewById(R.id.txtViewCustomerIsNewsLetter);
        btnViewCustomerRefreshImg = findViewById(R.id.btnViewCustomerRefreshImg);
        controle = Control.getInstance(this);
        controle.setViewCustomerActivity(this);
        controle.getInfoAboutCustomer(controle.getCustomer().getId());
        ecouteMenu((ImageButton)findViewById(R.id.btnViewCustomerBackToHomeImg), DashBoardActivity.class);
        ecouteMenu((ImageButton)findViewById(R.id.btnViewCustomerBaskToListImg), CustomerActivity.class);
        recupCustomer();
        refresh();
    }

    /**
     * Permet d'ecouter le click sur un object de type ImageButton
     * @param imageButton l'object sur lequel il faut ecouté le click
     * @param classe l"activity a lancé si le button est clické
     */
    private void ecouteMenu(ImageButton imageButton, final Class classe){
        imageButton.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(ViewCustomerActivity.this, classe);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    /**
     * creer la liste adapteur pour afficher les Addresse d'un client
     */
    public void creerListAddress(){
        ArrayList<Address> lesAddress = controle.getAllAddress();
        if (lesAddress != null){
            // permet que la liste soit "static"
            NonScrollListView lstHisto = findViewById(R.id.lstAddresse);
            CustomerAddressAdapter adapter = new CustomerAddressAdapter(this, lesAddress);
            lstHisto.setAdapter(adapter);
        }
    }

    /**
     * creer la liste adapteur pour afficher les Commandes d'un client
     */
    public void creerListOrder(){
        ArrayList<Order> lesOrders = controle.getAllOrderCustomer();
        if (lesOrders != null){
            NonScrollListView lstHisto = findViewById(R.id.lstOrderCustomer);
            OrderListAdapter adapter = new OrderListAdapter(this, lesOrders);
            lstHisto.setAdapter(adapter);
        }
    }

    /**
     * Permet d'afficher dans l'activity les information relative a un client
     */
    private void recupCustomer(){
        if (controle.getCustomer() != null){
            Log.d("ViewCustomerActivity", "Customer est pas nul");
            txtViewCustomerLastlogin.setText(String.format("Derniere Connection le : %s", controle.getCustomer().getLast_login()));
            txtViewCustomerDateJointed.setText(String.format("Compte Créer le : %s", controle.getCustomer().getDate_joined()));
            txtViewCustomerUsername.setText(String.format("Username : %s", controle.getCustomer().getUsername()));
            txtViewCustomerFirstName.setText(String.format("Prénom : %s", controle.getCustomer().getFirstName()));
            txtViewCustomerLastName.setText(String.format("Nom : %s", controle.getCustomer().getLastName()));
            txtViewCustomerEmail.setText(String.format("Email : %s", controle.getCustomer().getEmail()));
            txtViewCustomerMobile.setText(String.format("Téléphone : %s", controle.getCustomer().getMobile()));
            txtViewCustomerGender.setText(String.format("Civilité : %s", controle.getCustomer().getGender()));
            // on verifie si le client est inscrit a la newsLetter
            if (controle.getCustomer().getNewsLetter()){
                txtViewCustomerIsNewsLetter.setText(String.format("%s", "Inscrit a la NewsLetter"));
            }else {
                txtViewCustomerIsNewsLetter.setText(String.format("%s", "Pas encore Inscrit a la NewsLetter"));
            }

        }

    }

    /**
     * demande d'afficher la commande dans ViewOrderActivity
     * @param order un object de type Order
     */
    public void afficheOrder(Order order){
        controle.setOrder(order);
        Intent intent = new Intent(ViewCustomerActivity.this, ViewOrderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    /**
     * Permet de réactualiser les information dans l'activity
     */
    private void refresh(){
        btnViewCustomerRefreshImg.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v){
                recupCustomer();
                creerListAddress();
                creerListOrder();
            }
        });
    }

    /**
     * Permet d'afficher un toast avec un message d'erreur
     */
    public void customerDisplayError(){
        Toast.makeText(ViewCustomerActivity.this, "Une erreur c'est produite, impossible d'afficher les informations sur l'utilisateur.", Toast.LENGTH_SHORT).show();
    }

}
