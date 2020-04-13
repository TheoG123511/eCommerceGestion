package com.example.ecommercegestion.vue;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import com.example.ecommercegestion.R;
import com.example.ecommercegestion.controleur.Control;
import com.example.ecommercegestion.modele.Img;
import com.example.ecommercegestion.outils.MesOutils;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AddImageProductActivity extends AppCompatActivity {

    // constante
    private static final int BACK_TAKE_SCREEN = 1;

    // propriétés
    private Control control;
    private Button btnAddImgProductTakePictureImg;
    private ImageButton btnAddImgProductClearImg;
    private ImageButton btnAddImgProductCreateImg;
    private ImageView imgAddImageProduct;
    private EditText InputAddImgProductTitle;
    private Spinner spAddImageProductCategory;
    private String photoPath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image_product);
        init();
    }

    /**
     * Permet d'initialiser l'activity
     */
    private void init(){
        control = Control.getInstance(this);
        control.setAddImgProductActivity(this);
        control.setAddImgProduct(true);
        control.getProductData();
        btnAddImgProductTakePictureImg = findViewById(R.id.btnAddImgProductTakePictureImg);
        imgAddImageProduct = findViewById(R.id.imgAddImageProduct);
        spAddImageProductCategory = findViewById(R.id.spAddImageProductCategory);
        btnAddImgProductClearImg = findViewById(R.id.btnAddImgProductClearImg);
        btnAddImgProductCreateImg = findViewById(R.id.btnAddImgProductCreateImg);
        InputAddImgProductTitle = findViewById(R.id.InputAddImgProductTitle);
        ecouteMenu((ImageButton)findViewById(R.id.imgBackAddImgProductImg), DashBoardActivity.class);
        createOnClickBtnTakeScreen();
        createOnClickBtnClear();
        createOnClickBtnCreate();
    }

    /**
     * Permet d'ecouter le click sur un object de type ImageButton
     * @param imageButton l'object sur lequel il faut ecouté le click
     * @param classe l"activity a lancé si le button est clické
     */
    private void ecouteMenu(ImageButton imageButton, final Class classe){
        imageButton.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(AddImageProductActivity.this, classe);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    /**
     * Permet d'ecouter le click sur le button btnAddImgProductTakePictureImg
     */
    private void createOnClickBtnTakeScreen(){
        btnAddImgProductTakePictureImg.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v){
               takeScreen();
            }
        });

    }

    /**
     * Permet de prendre une photo grace a l'appareil photo du téléphone
     */
    private void takeScreen() {
        // creer un intent pour ouvrir une fênetre pour prendre la photo
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // on verifie que l'intent peut être gérer par le telephone
        if (intent.resolveActivity(getPackageManager()) != null){
            // on creer un nom de fichier unique
            String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File photoDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            try {
                File photoFile = File.createTempFile("photo"+time, ".jpg", photoDir);
                // enregistrement du chemin
                photoPath = photoFile.getAbsolutePath();
                // creer l'Uri
                Uri photoUri = FileProvider.getUriForFile(AddImageProductActivity.this,
                        AddImageProductActivity.this.getApplicationContext().getPackageName()+".provider",
                        photoFile);
                // transfert uri vers l'intent pour enregistrement photo dans fichier temporaire
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                // ouvrir l'activity par rapport a l'intent
                startActivityForResult(intent, BACK_TAKE_SCREEN);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  Verifie que l'image a bien ete recupérer, puis l'affiche dans l'interface
     * @param requestCode le code de la requete
     * @param resultCode le code de retour de la requete
     * @param data L'intent concernet
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        // on verifie que tous est ok
        if (requestCode==BACK_TAKE_SCREEN && resultCode==RESULT_OK){
            // recuperer l'image
            Bitmap image = BitmapFactory.decodeFile(photoPath);
            // afficher l'image
            imgAddImageProduct.setImageBitmap(image);
        }

    }

    /**
     * Permet d'afficher un toast dans l'interface
     * @param message le message a affiché
     */
    public void displayToast(String message){
        Toast.makeText(AddImageProductActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Permet de vidé tous les champs de l'activity
     */
    public void clearAllFields(){
        photoPath = null;
        InputAddImgProductTitle.setText("");
        imgAddImageProduct.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.take_screen));
    }

    /**
     * Permet d'ecouter le click sur l'imageButton btnAddImgProductClearImg
     */
    private void createOnClickBtnClear(){
        btnAddImgProductClearImg.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v){
                clearAllFields();
                displayProduct();
            }
        });
    }

    /**
     * Permet d'ecouté le click sur l'imageButton btnAddImgProductCreateImg
     */
    private void createOnClickBtnCreate(){
        btnAddImgProductCreateImg.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v){
                try{
                    String[] currencies = (spAddImageProductCategory.getSelectedItem().toString()).split(" ");
                    String title = InputAddImgProductTitle.getText().toString();
                    Integer productId = Integer.parseInt(currencies[0]);
                    // on verifie que l'image existe
                    Date date = new Date();
                    if (photoPath != null){
                        Img image = new Img(666, photoPath, MesOutils.convertDateToString(date), productId, title);
                        control.createNewImage(image);
                    }else {
                        displayToast("Erreur, Merci de prendre une photo.");
                    }
                }catch (Exception e){
                    displayToast("Erreur, Une erreur est survenu, merci de remplir tous les champs.");
                }
            }
        });
    }

    /**
     * Permet d'afficher la liste des produit disponible dans le spinner
     */
    public void displayProduct(){
        if (control.getAllProduct() != null){
            //create a list of items for the spinner.
            String[] items = control.generateNameProduct();
            //create an adapter to describe how the items are displayed, adapters are used in several places in android.
            //There are multiple variations of this, but this is the basic variant.
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
            //set the spinners adapter to the previously created one.
            spAddImageProductCategory.setAdapter(adapter);
        }
    }


}
