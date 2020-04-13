package com.example.ecommercegestion.modele;
import com.example.ecommercegestion.outils.AccesHTTP;



public class Order {
    private Integer id;
    private String status;
    private String delivery;
    private String startDate;
    private String methodPayment;
    private Integer addressId;
    private Integer customerId;
    private String[] statueOrder = new String[]{"En cours de Traitement", "En cours de Préparation",
            "En cours de depot chez le transporteur", "En cours de Livraison",
            "Livraison Recu", "Commande Terminé"};

    /***
     * Constructeur de la classe
     * @param id l'id de la commande
     * @param status le statue de la commande
     * @param startDate la date de debut de la commande
     * @param methodPayment la methode de payement de la commande
     * @param addressId l'id de l'addresse concernant cette commande
     * @param customerId l'id du client a qui appartient la commande
     * @param delivery le lien de suivi du colis
     */
    public Order(Integer id, String status, String startDate, String methodPayment, Integer addressId, Integer customerId, String delivery) {
        this.id = id;
        this.status = status;
        this.startDate = startDate;
        this.methodPayment = methodPayment;
        this.addressId = addressId;
        this.customerId = customerId;
        this.delivery = delivery;
    }

    /***
     * getter
     * @return l'id de la commande
     */
    public Integer getId() {
        return id;
    }

    /***
     * Getter
     * @return le statue de la commande
     */
    public String getStatus() {
        return status;
    }

    /***
     * Setter
     * @param status le nouveau statue de la commande
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /***
     * getter
     * @return le lien de suivit du colis
     */
    public String getDelivery() {
        return delivery;
    }

    /***
     * Setter
     * @param delivery le nouveau lien de suivi du colis
     */
    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    /***
     * Getter
     * @return la date de debut de la commande
     */
    public String getStartDate() {
        return startDate;
    }

    /***
     * Getter
     * @return la methode de payement
     */
    public String getMethodPayment() {
        return methodPayment;
    }

    /***
     * Getter
     * @return l'id du client concernet par cette commande
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * Ajout de toutes les attribut d'un produit aux parametre d'une requete
     * @param accesDonnes Un object de type AccesHttp pour pouvoir y inseré les parametre
     */
    public void updateOrder(AccesHTTP accesDonnes){
        accesDonnes.addParam("customer", customerId.toString());
        accesDonnes.addParam("address", addressId.toString());
        accesDonnes.addParam("status", status);
        accesDonnes.addParam("delivery", delivery);
        accesDonnes.addParam("date", startDate);
        accesDonnes.addParam("methodpayment", methodPayment);
    }

    /***
     * Permet de générer la liste de tous les statues disponnible pour une commande
     * @return Une liste de String contenant tous les statue disponible pour une commande
     */
    public String[] availableMethod(){
        String[] state = statueOrder;
        String buff;
        int pos = 0;
        for (int i=0; i<statueOrder.length; i++)
        {
            if (statueOrder[i].contains(status)){
                pos = i;
            }
        }
        buff = state[0];
        state[0] = status;
        state[pos] = buff;
        return state;
    }

}
