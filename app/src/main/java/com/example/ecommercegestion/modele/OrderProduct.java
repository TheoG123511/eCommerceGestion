package com.example.ecommercegestion.modele;

public class OrderProduct {
    private Integer id;
    private Integer quantity;
    private Integer orderId;
    private Integer productId;
    private String productName;
    private double productPrice;

    /***
     * Constructeur de la classe (classe qui permet de stocker les article présent dans une commandeà
     * @param id l'id
     * @param quantity la quantité voulu
     * @param orderId l'id de la commande
     * @param productId l'id du produit
     * @param productName le nom du produit
     * @param productPrice le prix unitaire du produit
     */
    public OrderProduct(Integer id, Integer quantity, Integer orderId, Integer productId,
                        String productName, double productPrice) {
        this.id = id;
        this.quantity = quantity;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    /***
     * Getter
     * @return l'id
     */
    public Integer getId() {
        return id;
    }

    /***
     * Getter
     * @return la quantité voulu
     */
    public Integer getQuantity() {
        return quantity;
    }

    /***
     * Getter
     * @return le nom du produit voulu
     */
    public String getProductName() {
        return productName;
    }

    /***
     * Getter
     * @return le prix du produit voulu
     */
    public double getProductPrice() {
        return productPrice;
    }
}
