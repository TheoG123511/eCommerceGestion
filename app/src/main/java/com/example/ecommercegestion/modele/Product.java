package com.example.ecommercegestion.modele;
import android.util.Log;
import com.example.ecommercegestion.outils.AccesHttpUpload;


public class Product {

    private Integer id;
    private String name;
    private String description;
    private double price;
    private Integer quantity;
    private String date;
    private String catgeroy;
    private String img;
    private Integer categoryId;

    /**
     * Constructeur de la classe
     * @param id l'id du produit
     * @param name le nom du produit
     * @param description la description du produit
     * @param price le prix du produit
     * @param quantity la quantité disponnible du produit
     * @param date la date d'ajout du produit
     * @param catgeroy la catégorie du produit
     * @param img le chemin d'acces vers l'image du produit
     * @param categoryId l'id de la catégorie du produit
     */
    public Product(Integer id, String name, String description, double price, Integer quantity, String date, String catgeroy, String img, Integer categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.date = date;
        this.catgeroy = catgeroy;
        this.img = img;
        this.categoryId = categoryId;
    }

    /**
     * Getter
     * @return l'id de la catégorie
     */
    public Integer getCategoryId() { return categoryId; }

    /***
     * Getter
     * @return l'id du produuit
     */
    public Integer getId() {
        return id;
    }

    /**
     * Getter
     * @return le nom du produit
     */
    public String getName() {
        return name;
    }

    /***
     * Setter
     * @param name le nouveau nom du produit
     */
    public void setName(String name) {
        this.name = name;
    }

    /***
     * Getter
     * @return la description du produit
     */
    public String getDescription() {
        return description;
    }

    /***
     * Getter
     * @return le prix du produit
     */
    public double getPrice() {
        return price;
    }

    /***
     * Getter sur la quantité
     * @return la quantité disponible du produit
     */
    public Integer getQuantity() {
        return quantity;
    }

    /***
     * Getter
     * @return la date d'ajout du produit
     */
    public String getDate() {
        return date;
    }

    /***
     * getter
     * @return Le nom de la sous catégorie associé a se produit
     */
    private String getCatgeroy() {
        return catgeroy;
    }

    /***
     * Getter
     * @return le chemin d'acces vers l'image du produit
     */
    public String getImg() {
        return img;
    }

    /***
     * Setter
     * @param filepath le chemin vers le fichier image
     */
    public void setImg(String filepath) { img = filepath; }

    /**
     * Ajout de toutes les attribut d'un produit a la requete
     * @param accesDonnes Un object de type AccesHttpUpload pour pouvoir y inseré les parametre
     * @param method la methode a utilisé pour l'envoie des donnees
     */
    public void updateOrCreateNewProduct(AccesHttpUpload accesDonnes, String method) {
        if (method.equals("PUT")){
            accesDonnes.addParam("id", id.toString());
            accesDonnes.addParam("name", name);
            accesDonnes.addParam("description", description);
            accesDonnes.addParam("price", Double.toString(price));
            accesDonnes.addParam("quantity", quantity.toString());
            accesDonnes.addParam("category", categoryId.toString());
            accesDonnes.addParam("date", date);
            // on verifie que l'image n'est pas une url
            if (!(img.contains("http://") || img.contains("https://"))){
                try {
                    accesDonnes.addFile("image", img, "image/jpeg");
                }catch (Exception e) {
                    Log.d("Product", "fonction updateOrCreateNewProduct() -> l'image est null");
                }
            }
        }else {
            accesDonnes.addParam("name", name);
            accesDonnes.addParam("description", description);
            accesDonnes.addParam("price", Double.toString(price));
            accesDonnes.addParam("quantity", quantity.toString());
            accesDonnes.addParam("category", categoryId.toString());
            accesDonnes.addParam("date", date);
            try {
                accesDonnes.addFile("image", img, "image/jpeg");
            }catch (Exception e) {
                Log.d("Product", "fonction updateOrCreateNewProduct() -> l'image est null");
            }
        }

    }

    /**
     * Mes a jour un object de type Product grace a un nouvelle object de type Produit
     *
     */
    public void updateProduct(Product product){
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.catgeroy = product.getCatgeroy();
        this.img = product.getImg();
        this.categoryId = product.getCategoryId();
    }


}
