package com.example.ecommercegestion.vue;
import com.example.ecommercegestion.R;
import com.example.ecommercegestion.controleur.Control;
import com.example.ecommercegestion.modele.Product;
import com.example.ecommercegestion.outils.DownloadImageTask;
import com.example.ecommercegestion.outils.MesOutils;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
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


public class AddProductActivity extends AppCompatActivity {

    // constante
    private static final int BACK_TAKE_SCREEN = 1;

    // propriété
    private Control control;
    private String photoPath = "null";
    private ImageView imgAddProduct;
    private EditText InputAddProduct;
    private EditText MultitxtAddProduct;
    private Button btnAddProductTakePicture;
    private EditText InputAddProductPrice;
    private EditText InputAddProductQuantity;
    private Spinner spAddProductCategory;
    private ImageButton btnAddProductCreateImg;
    private ImageButton btnAddProductDelete;
    private ImageButton btnAddProductRefreshImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        init();
        recupProduct();
    }

    /**
     * Permet d'initialiser l'activity
     */
    public void init(){
        imgAddProduct = findViewById(R.id.imgAddProduct);
        InputAddProduct = findViewById(R.id.InputAddProduct);
        MultitxtAddProduct = findViewById(R.id.MultitxtAddProduct);
        btnAddProductTakePicture = findViewById(R.id.btnAddProductTakePicture);
        InputAddProductPrice = findViewById(R.id.InputAddProductPrice);
        InputAddProductQuantity = findViewById(R.id.InputAddProductQuantity);
        spAddProductCategory = findViewById(R.id.spAddProductCategory);
        btnAddProductCreateImg = findViewById(R.id.btnAddProductCreateImg);
        btnAddProductDelete = findViewById(R.id.btnAddProductDelete);
        btnAddProductRefreshImg = findViewById(R.id.btnAddProductRefreshImg);
        control = Control.getInstance(this);
        control.setAddproductActivity(this);
        control.getSubCategoryData();
        // on verifie si on affiche le button de retour ou pas
        if (control.getComeToListProduct()){
            findViewById(R.id.btnAddProductBackListImg).setVisibility(View.VISIBLE);
            btnAddProductRefreshImg.setVisibility(View.VISIBLE);
        }else {
            findViewById(R.id.btnAddProductBackListImg).setVisibility(View.GONE);
            btnAddProductRefreshImg.setVisibility(View.GONE);
            control.setProduct(null);
        }
        control.setComeToListProduct(false);
        ecouteMenu((ImageButton)findViewById(R.id.btnBackAddProductImg), DashBoardActivity.class);
        ecouteMenu((ImageButton)findViewById(R.id.btnAddProductBackListImg), ProductActivity.class);
        refresh();
        createOnClickBtnTakeScreen();
        updateOrCreateProduct();
        reinitialiseFields();
    }

    /**
     * Permet d'ecouter le click sur un object de type ImageButton
     * @param imageButton l'object sur lequel il faut ecouté le click
     * @param classe l"activity a lancé si le button est clické
     */
    private void ecouteMenu(ImageButton imageButton, final Class classe){
        imageButton.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(AddProductActivity.this, classe);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    /**
     * Permet d'afficher dans l'activity les information relative a un produit
     */
    public void recupProduct() {
        // on verifie que modifyProduct est pas nul
        if (control.getProduct() != null) {
            InputAddProduct.setText(control.getProduct().getName());
            MultitxtAddProduct.setText(control.getProduct().getDescription());
            InputAddProductPrice.setText(String.format("%s", Double.toString(control.getProduct().getPrice())));
            InputAddProductQuantity.setText(String.format("%s", control.getProduct().getQuantity().toString()));
            DownloadImageTask downloadImageTask = new DownloadImageTask(imgAddProduct, getApplicationContext());
            Log.d("AddProduct", "Img url = " + control.getProduct().getImg());
            downloadImageTask.execute(control.getProduct().getImg());
            photoPath = control.getProduct().getImg();
        }else {
            imgAddProduct.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.take_screen));
        }
    }

    /**
     * Permet d'afficher toutes les catégorie disponible dans le spinner
     */
    public void displayCategory() {

        if (control.getAllSubCategories() != null && control.getProduct() != null){
            //create a list of items for the spinner.
            String[] items = control.generateCategoryProduct(control.getProduct());
            //create an adapter to describe how the items are displayed, adapters are used in several places in android.
            //There are multiple variations of this, but this is the basic variant.
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
            //set the spinners adapter to the previously created one.
            spAddProductCategory.setAdapter(adapter);

        }else if (control.getAllSubCategories() != null){
            //create a list of items for the spinner.
            String[] items = control.generateCategoryProduct();
            //create an adapter to describe how the items are displayed, adapters are used in several places in android.
            //There are multiple variations of this, but this is the basic variant.
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
            //set the spinners adapter to the previously created one.
            spAddProductCategory.setAdapter(adapter);
        }

    }

    /**
     * Permet de réactualisé l'activity
     */
    private void refresh(){
        btnAddProductRefreshImg.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v){
                recupProduct();
            }
        });
    }

    /**
     * Permet d'ecouter le click sur le button btnAddProductTakePicture
     */
    private void createOnClickBtnTakeScreen(){
        btnAddProductTakePicture.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v){
                takeScreen();
            }
        });

    }

    /**
     * Permet d'ecouter le click sur l' imageButton btnAddProductDelete
     */
    private void reinitialiseFields() {
        btnAddProductDelete.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v){
                clearAllFields();
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
                Uri photoUri = FileProvider.getUriForFile(AddProductActivity.this,
                        AddProductActivity.this.getApplicationContext().getPackageName()+".provider",
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
            imgAddProduct.setImageBitmap(image);
            if (control.getProduct() != null) {
                // modification de l'image du modifyProduct
                control.getProduct().setImg(photoPath);
            }
        }

    }

    /**
     * Permet d'ecouter le click sur l' imageButton btnAddProductCreateImg (mes a jour ou insert un nouveau produit)
     */
    private void updateOrCreateProduct(){
        btnAddProductCreateImg.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v){
                // on récupere les données
                try{
                    Integer id = 666;
                    String[] currencies = (spAddProductCategory.getSelectedItem().toString()).split(" ");
                    String name = InputAddProduct.getText().toString();
                    String description = MultitxtAddProduct.getText().toString();
                    double price = Double.parseDouble(InputAddProductPrice.getText().toString());
                    Integer quantity = Integer.parseInt(InputAddProductQuantity.getText().toString());
                    Integer categoryId = Integer.parseInt(currencies[0]);
                    String catgeroy = currencies[1];
                    // on verifie que l'image existe
                    Date date = new Date();
                    if ((!photoPath.equals("null"))){
                        Product product = new Product(id, name, description, price, quantity, MesOutils.convertDateToString(date), catgeroy, photoPath, categoryId);
                        if (control.getProduct() != null) {
                            Log.d("AddProduct", "On l ajoute");
                            // on modifie le produit existant
                            control.modifyProduct(product);
                        }else {
                            // la sa veut dire que le produit n'existe pas on le creer
                            control.addNewProduct(product);
                        }
                    }else {
                        displayToast("Erreur, Merci de prendre une photo du produit que vous voulez ajouté.");
                    }
                }catch (Exception e){
                    Log.d("AddProductActivity", "Erreur recuperation des donnees utilisateur " + e.getMessage());
                    displayToast("Erreur, Une erreur est survenu, merci de remplir tous les champs.");
                }
            }
        });
    }

    /**
     * Permet d'afficher un toast dans l'interface
     * @param message le message a affiché
     */
    public void displayToast(String message){
        Toast.makeText(AddProductActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Permet de vidé tous les champs de l'activity
     */
    public void clearAllFields(){
        InputAddProduct.setText("");
        MultitxtAddProduct.setText("");
        InputAddProductPrice.setText("");
        InputAddProductQuantity.setText("");
        recupProduct();
        displayCategory();
    }

}
