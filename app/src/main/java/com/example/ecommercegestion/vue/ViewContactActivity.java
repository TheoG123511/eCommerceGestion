package com.example.ecommercegestion.vue;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ecommercegestion.R;
import com.example.ecommercegestion.controleur.Control;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class ViewContactActivity extends AppCompatActivity {

    private Control controle;
    private TextView txtViewContactDate;
    private TextView txtViewContactName;
    private TextView txtViewContactEmail;
    private TextView txtViewContactSubject;
    private TextView txtViewContactMessage;
    private ImageButton btnViewContactDeleteImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);
        init();
    }

    /**
     * Permet d'initialiser l'activity
     */
    private void init(){
        txtViewContactDate = findViewById(R.id.txtViewContactDate);
        txtViewContactName = findViewById(R.id.txtViewContactName);
        txtViewContactEmail = findViewById(R.id.txtViewContactEmail);
        txtViewContactSubject = findViewById(R.id.txtViewContactSubject);
        txtViewContactMessage = findViewById(R.id.txtViewContactMessage);
        btnViewContactDeleteImg = findViewById(R.id.btnViewContactDeleteImg);
        controle = Control.getInstance(this);
        ecouteMenu((ImageButton)findViewById(R.id.btnViewContactBackHomeImg), DashBoardActivity.class);
        ecouteMenu((ImageButton)findViewById(R.id.btnViewContactBackToListImg), ContactActivity.class);
        recupContact();
        supprimerContact();
    }

    /**
     * Ecoute le click sur le button btnViewContactDeleteImg qui permet de supprimer une demande de contact
     */
    private void supprimerContact(){
        btnViewContactDeleteImg.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
                if (controle.getContact() != null) {
                    controle.delContact(controle.getContact());
                    ecouteMenu((ImageButton) findViewById(R.id.btnViewContactDeleteImg), ContactActivity.class);
                }else {
                    Toast.makeText(ViewContactActivity.this, "Une erreur c'est produite, merci de réessayer ultérieurement", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Permet d'ecouter le click sur un object de type ImageButton
     * @param imageButton l'object sur lequel il faut ecouté le click
     * @param classe l"activity a lancé si le button est clické
     */
    private void ecouteMenu(ImageButton imageButton, final Class classe){
        imageButton.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(ViewContactActivity.this, classe);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    /**
     * Permet d'afficher dans l'activity les information relative a une demande de contact
     */
    public void recupContact(){
        if (controle.getContact() != null){
            txtViewContactDate.setText(String.format("Recu le : %s", controle.getContact().getDate()));
            txtViewContactName.setText(String.format("Nom : %s", controle.getContact().getName()));
            txtViewContactEmail.setText(String.format("Addresse email : %s", controle.getContact().getEmail()));
            txtViewContactSubject.setText(String.format("Sujet : %s", controle.getContact().getSubject()));
            txtViewContactMessage.setText(String.format("%s", controle.getContact().getMessage()));
        }

    }

}
