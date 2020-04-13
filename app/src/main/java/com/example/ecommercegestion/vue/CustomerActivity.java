package com.example.ecommercegestion.vue;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ecommercegestion.R;
import com.example.ecommercegestion.controleur.Control;
import com.example.ecommercegestion.modele.Customer;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;


public class CustomerActivity extends AppCompatActivity {

    private Control controle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        init();
    }

    /**
     * Permet d'initialiser l'activity
     */
    private void init() {
        controle = Control.getInstance(this);
        controle.setCustomerActivity(this);
        controle.getCustomerData();
        ecouteMenu((ImageButton)findViewById(R.id.btnBackCustomerImg), DashBoardActivity.class);
    }


    /**
     * Permet d'ecouter le click sur un object de type ImageButton
     * @param imageButton l'object sur lequel il faut ecouté le click
     * @param classe l"activity a lancé si le button est clické
     */
    private void ecouteMenu(ImageButton imageButton, final Class classe){
        imageButton.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v){
                Log.d("Customer", "recuperation des clients");
                Intent intent = new Intent(CustomerActivity.this, classe);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    /**
     * creer la liste adapteur pour afficher la liste de tous les utilisateur de type Customer
     */
    public void creerList(){
        ArrayList<Customer> lesCustomer = controle.getAllCustomer();
        if (lesCustomer != null){
            ListView lstHisto = findViewById(R.id.lstCustomer);
            CustomerListAdapter adapter = new CustomerListAdapter(this, lesCustomer);
            lstHisto.setAdapter(adapter);
        }
    }

    /**
     * demande d'afficher le client dans ViewCustomerActivity
     * @param customer Un object de type Customer
     */
    public void afficheCustomer(Customer customer){
        controle.setCustomer(customer);
        Intent intent = new Intent(CustomerActivity.this, ViewCustomerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    /**
     * Permet d'afficher un toast contenant un message d'erreur
     */
    public void customerDisplayError(){
        Toast.makeText(CustomerActivity.this, "Une erreur c'est produite, impossible d'afficher la liste des produit.", Toast.LENGTH_SHORT).show();
    }
}
