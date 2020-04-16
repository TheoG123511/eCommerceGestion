package com.example.ecommercegestion.vue;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ecommercegestion.R;
import com.example.ecommercegestion.controleur.Control;
import com.example.ecommercegestion.modele.Order;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;


public class OrderActivity extends AppCompatActivity {

    private Control controle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        init();
    }

    /**
     * Permet d'initialiser l'activity
     */
    private void init() {
        controle = Control.getInstance(this);
        controle.setOrderActivity(this);
        controle.getOrderData();
        ecouteMenu((ImageButton)findViewById(R.id.btnBackOrderImg), DashBoardActivity.class);
    }

    /**
     * Permet d'ecouter le click sur un object de type ImageButton
     * @param imageButton l'object sur lequel il faut ecouté le click
     * @param classe l"activity a lancé si le button est clické
     */
    private void ecouteMenu(ImageButton imageButton, final Class classe){
        imageButton.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(OrderActivity.this, classe);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    /**
     * creer la liste adapteur, qui permet d'afficher la liste de toutes les commandes
     */
    public void creerList(){
        ArrayList<Order> lesOrders = controle.getAllOrder();
        if (lesOrders != null){
            ListView lstHisto = findViewById(R.id.lstOrder);
            OrderListAdapter adapter = new OrderListAdapter(this, lesOrders);
            lstHisto.setAdapter(adapter);
        }
    }

    /**
     * demande d'afficher la commande dans ViewOrderActivity
     * @param order un object de type Order
     */
    public void afficheOrder(Order order){
        controle.setOrder(order);
        Intent intent = new Intent(OrderActivity.this, ViewOrderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    /**
     * Permet d'afficher un toast avec un message d'erreur
     */
    public void orderDisplayError(){
        Toast.makeText(OrderActivity.this, "Une erreur c'est produite, impossible d'afficher la liste des produit.", Toast.LENGTH_SHORT).show();
    }
}

