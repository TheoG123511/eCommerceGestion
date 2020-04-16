package com.example.ecommercegestion.vue;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ecommercegestion.R;
import com.example.ecommercegestion.controleur.Control;
import com.example.ecommercegestion.modele.OrderProduct;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


public class ViewOrderActivity extends AppCompatActivity {

    private Control controle;

    private TextView txtOrderAddressFirstName;
    private TextView txtOrderAddressLastName;
    private TextView txtOrderAddressAddr;
    private TextView txtOrderAddressCity;
    private TextView txtOrderAddressCp;
    private TextView txtOrderAddressCountry;
    private TextView txtOrderAddressMobile;
    private TextView txtViewOrderId;
    private TextView txtViewOrderDate;
    private TextView txtViewOrderPayement;

    private Spinner spViewOrderState;
    private EditText InputViewOrderDelivery;

    private ImageButton btnViewOrderRefreshImg;
    private ImageButton btnViewOrderSaveImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        hideKeyboard2(this);
        init();
    }

    /**
     * Permet d'initialiser l'activity
     */
    public void init(){
        controle = Control.getInstance(this);
        controle.setViewOrderActivity(this);
        controle.getProductInOrder(controle.getOrder().getId(), controle.getOrder().getCustomerId());
        controle.getAddressForOrder(controle.getOrder().getId(), controle.getOrder().getCustomerId());
        txtOrderAddressFirstName = findViewById(R.id.txtOrderAddressFirstName);
        txtOrderAddressLastName = findViewById(R.id.txtOrderAddressLastName);
        txtOrderAddressAddr = findViewById(R.id.txtOrderAddressAddr);
        txtOrderAddressCity = findViewById(R.id.txtOrderAddressCity);
        txtOrderAddressCp = findViewById(R.id.txtOrderAddressCp);
        txtOrderAddressCountry = findViewById(R.id.txtOrderAddressCountry);
        txtOrderAddressMobile = findViewById(R.id.txtOrderAddressMobile);
        txtViewOrderId = findViewById(R.id.txtViewOrderId);
        txtViewOrderDate = findViewById(R.id.txtViewOrderDate);
        txtViewOrderPayement = findViewById(R.id.txtViewOrderPayement);
        InputViewOrderDelivery = findViewById(R.id.InputViewOrderDelivery);
        //get the spinner from the xml.
        spViewOrderState = findViewById(R.id.spViewOrderState);
        btnViewOrderRefreshImg = findViewById(R.id.btnViewOrderRefreshImg);
        btnViewOrderSaveImg = findViewById(R.id.btnViewOrderSaveImg);

        ecouteMenu((ImageButton)findViewById(R.id.btnViewOrderBackHomeImg), DashBoardActivity.class);
        ecouteMenu((ImageButton)findViewById(R.id.btnViewOrderBackToListImg), OrderActivity.class);
        refresh();
        updateOrder();
    }

    /**
     * Permet de ne pas ouvrir le clavier virtuel au lancement de l'activity
     * @param activity L'activity concerner
     */
    public void hideKeyboard2(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * Permet d'ecouter le click sur un object de type ImageButton
     * @param imageButton l'object sur lequel il faut ecouté le click
     * @param classe l"activity a lancé si le button est clické
     */
    private void ecouteMenu(ImageButton imageButton, final Class classe){
        imageButton.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(ViewOrderActivity.this, classe);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    /**
     * creer la liste adapteur pour afficher les articles commander
     */
    public void creerListProduct(){
        Log.d("ViewOrderActivity", "Creation de la list modifyProduct order");
        ArrayList<OrderProduct> lesProduct = controle.getAllOrderProduct();
        if (lesProduct != null){
            Log.d("ViewOrderActivity", "Les modifyProduct sont pas nul");
            NonScrollListView lstHisto = findViewById(R.id.lstViewOrderProduct);
            OrderProductAdapter adapter = new OrderProductAdapter(this, lesProduct);
            lstHisto.setAdapter(adapter);
        }
    }

    /**
     * Permet d'afficher dans l'activity les information relative a une commande
     */
    public void recupOrder() {
        // on verifie que order est pas nul
        // on affiche les info sur la commande
        if (controle.getOrder() != null) {
            Log.d("ViewOrderActivity", "Order est pas nul");
            txtViewOrderId.setText(String.format("Id du Client : %s", controle.getOrder().getCustomerId()));
            txtViewOrderDate.setText(String.format("Date de creation de la commande : %s", controle.getOrder().getStartDate()));
            txtViewOrderPayement.setText(String.format("Methode de Payement : %s", controle.getOrder().getMethodPayment()));
            //create a list of items for the spinner.
            String[] items = controle.getOrder().availableMethod();
            //create an adapter to describe how the items are displayed, adapters are used in several places in android.
            //There are multiple variations of this, but this is the basic variant.

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
            //set the spinners adapter to the previously created one.
            spViewOrderState.setAdapter(adapter);
            InputViewOrderDelivery.setText(controle.getOrder().getDelivery());
        }
        // on affiche les info sur l'addresse de livraison de la commande
        if (controle.getAddress() != null){
            txtOrderAddressFirstName.setText(String.format("Prénom %s", controle.getAddress().getFirstName()));
            txtOrderAddressLastName.setText(String.format("Nom : %s", controle.getAddress().getLastName()));
            txtOrderAddressAddr.setText(String.format("Addresse : %s", controle.getAddress().getAddress()));
            txtOrderAddressCity.setText(String.format("Ville : %s", controle.getAddress().getCity()));
            txtOrderAddressCp.setText(String.format("Code Postal : %s", controle.getAddress().getCp()));
            txtOrderAddressCountry.setText(String.format("Pays : %s", controle.getAddress().getCountry()));
            txtOrderAddressMobile.setText(String.format("Téléphone : %s", controle.getAddress().getMobile()));
        }

    }

    /**
     * Permet de reactualiser les information dans l'activity
     */
    private void refresh(){
        btnViewOrderRefreshImg.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v){
                recupOrder();
            }
        });
    }

    /**
     * Ecoute le click sur l'ImageButton btnViewOrderSaveImg qui permet de mettre a jour les informations d'une commande
     */
    private void updateOrder(){
        btnViewOrderSaveImg.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v){
                Log.d("ViewOrderActivity", "Prompt = " + spViewOrderState.getSelectedItem().toString());
                controle.updateOrder(spViewOrderState.getSelectedItem().toString(), InputViewOrderDelivery.getText().toString());
            }
        });

    }
    /**
     * Permet d'afficher un toast avec un message de succes
     */
    public void updateSucces(){
        Toast.makeText(ViewOrderActivity.this, "Commande Modifié avec succes !", Toast.LENGTH_SHORT).show();
    }

    /**
     * Permet d'afficher un toast avec un message d'erreur si la commande n'a pas pu être modifier
     */
    public void updateError(){
        Toast.makeText(ViewOrderActivity.this, "Une Erreur c'est produite, impossible de modifier la commande !", Toast.LENGTH_SHORT).show();
    }

    /**
     * Permet d'afficher un toast avec un message d'erreur si la liste des produits commander n'a pas pu être afficher
     */
    public void orderDisplayError(){
        Toast.makeText(ViewOrderActivity.this, "Une erreur c'est produite, impossible d'afficher la liste des produit.", Toast.LENGTH_SHORT).show();
    }


}
