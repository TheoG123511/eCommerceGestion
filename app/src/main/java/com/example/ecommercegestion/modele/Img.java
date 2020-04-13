package com.example.ecommercegestion.modele;
import com.example.ecommercegestion.outils.AccesHttpUpload;


public class Img {

    private Integer id;
    private String image;
    private String date;
    private Integer productId;
    private String title;

    /***
     * Constructeur de la classe
     * @param id l'id de l'image
     * @param image le chemin d'acces a l'image
     * @param date la date d'ajout de l'image
     * @param productId l'id du produit concernet par cette image
     * @param title le titre de l'image
     */
    public Img(Integer id, String image, String date, Integer productId, String title) {
        this.id = id;
        this.image = image;
        this.date = date;
        this.productId = productId;
        this.title = title;
    }

    /**
     * Ajout de toutes les attribut d'une image a la requete
     * @param accesDonnes Un object de type AccesHttpUpload pour pouvoir y inseré les parametre
     */
    public void createNewImg(AccesHttpUpload accesDonnes) {
        accesDonnes.addParam("id", id.toString());
        accesDonnes.addParam("product", productId.toString());
        accesDonnes.addParam("title", title);
        accesDonnes.addParam("date", date);
        try {
            accesDonnes.addFile("image", image, "image/jpeg");
        } catch (Exception e) {}
    }
}
