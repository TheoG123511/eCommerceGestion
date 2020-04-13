package com.example.ecommercegestion.vue;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ecommercegestion.R;
import com.example.ecommercegestion.controleur.Control;
import com.example.ecommercegestion.modele.Contact;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;


public class ContactActivity extends AppCompatActivity {

    private Control controle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        init();
    }

    /**
     * Permet d'initialiser l'activity
     */
    private void init() {
        controle = Control.getInstance(this);
        controle.setContactActivity(this);
        controle.getContactData();
        ecouteMenu((ImageButton)findViewById(R.id.btnBackContactImg), DashBoardActivity.class);
    }

    /**
     * Permet d'ecouter le click sur un object de type ImageButton
     * @param imageButton l'object sur lequel il faut ecouté le click
     * @param classe l"activity a lancé si le button est clické
     */
    private void ecouteMenu(ImageButton imageButton, final Class classe){
        imageButton.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(ContactActivity.this, classe);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    /**
     * Creer la liste adapteur qui permet d'afficher la liste de toutes les demandes de contact
     */
    public void creerList(){
        ArrayList<Contact> lesContacts = controle.getAllContact();
        if (lesContacts != null){
            ListView lstHisto = findViewById(R.id.lstContact);
            ContactListAdapter adapter = new ContactListAdapter(this, lesContacts);
            lstHisto.setAdapter(adapter);
        }
    }

    /**
     * demande d'afficher le contact dans ViewContactActivity
     * @param contact Un object de type Contact
     */
    public void afficheContact(Contact contact){
        controle.setContact(contact);
        Intent intent = new Intent(ContactActivity.this, ViewContactActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    /**
     * Permet d'afficher un toast dans l'interface
     * @param message le message a affiché
     */
    public void displayToast(String message){
        Toast.makeText(ContactActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
