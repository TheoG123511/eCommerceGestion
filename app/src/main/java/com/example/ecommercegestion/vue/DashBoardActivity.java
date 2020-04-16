package com.example.ecommercegestion.vue;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.example.ecommercegestion.R;
import com.example.ecommercegestion.controleur.Control;


public class DashBoardActivity extends AppCompatActivity {

    private Control controle;
    private ImageButton btnLogoutImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        init();
    }

    /**
     * Permet d'initialiser l'activity
     */
    private void init(){
        controle = Control.getInstance(this);
        controle.setComeToListProduct(false);
        controle.resetArraysAndContext();
        btnLogoutImg = findViewById(R.id.btnLogoutImg);
        ecouteMenu((ImageButton)findViewById(R.id.btnAddProductImg), AddProductActivity.class);
        ecouteMenu((ImageButton)findViewById(R.id.btnAddImageImg), AddImageProductActivity.class);
        ecouteMenu((ImageButton)findViewById(R.id.btnContactImg), ContactActivity.class);
        ecouteMenu((ImageButton)findViewById(R.id.btnUserImg), CustomerActivity.class);
        ecouteMenu((ImageButton)findViewById(R.id.btnOrderImg), OrderActivity.class);
        ecouteMenu((ImageButton)findViewById(R.id.btnProductImg), ProductActivity.class);
        retourLogin();
    }

    /**
     * Permet d'ecouter le click sur un object de type ImageButton
     * @param imageButton l'object sur lequel il faut ecouté le click
     * @param classe l"activity a lancé si le button est clické
     */
    private void ecouteMenu(ImageButton imageButton, final Class classe){
        imageButton.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(DashBoardActivity.this, classe);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    /**
     * Ecouteur sur l'ImageButton btnLogoutImg, permet de retourner a l'activity de connection
     */
    public void retourLogin() {
        btnLogoutImg.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v){
                // on reinitialise le controleur vu que l'on quitte la session
                controle.setInstance(null);
                // on relance la gui de connection
                Intent intent = new Intent(DashBoardActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}
