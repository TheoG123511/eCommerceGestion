package com.example.ecommercegestion.vue;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ecommercegestion.R;
import com.example.ecommercegestion.controleur.Control;
import com.example.ecommercegestion.modele.Product;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;


public class ProductActivity extends AppCompatActivity {

    private Control controle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        init();
    }
    /**
     * Permet d'initialiser l'activity
     */
    public void init(){
        controle = Control.getInstance(this);
        controle.setProductActivity(this);
        controle.getProductData();
        ecouteMenu((ImageButton)findViewById(R.id.btnBackProductImg), DashBoardActivity.class);
    }

    /**
     * Permet d'ecouter le click sur un object de type ImageButton
     * @param imageButton l'object sur lequel il faut ecouté le click
     * @param classe l"activity a lancé si le button est clické
     */
    private void ecouteMenu(ImageButton imageButton, final Class classe){
        imageButton.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(ProductActivity.this, classe);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    /**
     * creer la liste adapteur qui permet d'afficher la liste de tous les produits
     */
    public void creerList(){
        ArrayList<Product> lesProducts = controle.getAllProduct();
        if (lesProducts != null){
            ListView lstHisto = findViewById(R.id.lstProduct);
            ProductListAdapter adapter = new ProductListAdapter(this, lesProducts);
            lstHisto.setAdapter(adapter);
        }
    }
    /**
     * demande d'afficher le produit dans AddProductActivity
     * @param product Un object de type Product
     */
    public void afficheProduct(Product product){
        controle.setProduct(product);
        Intent intent = new Intent(ProductActivity.this, AddProductActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /**
     * Permet d'afficher un toast avec un message de succes
     */
    public void productDelDone(){
        Toast.makeText(ProductActivity.this, "Produit Supprimé avec Succes !", Toast.LENGTH_SHORT).show();
    }

    /**
     * Permet d'afficher un toast avec un message d'erreur
     */
    public void productDisplayEroor(){
        Toast.makeText(ProductActivity.this, "Une erreur c'est produite, impossible d'afficher la liste des produit.", Toast.LENGTH_SHORT).show();
    }
}
