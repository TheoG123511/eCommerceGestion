package com.example.ecommercegestion.controleur;
import android.content.Context;
import com.example.ecommercegestion.modele.AccesDistant;
import com.example.ecommercegestion.modele.Address;
import com.example.ecommercegestion.modele.Contact;
import com.example.ecommercegestion.modele.Customer;
import com.example.ecommercegestion.modele.Img;
import com.example.ecommercegestion.modele.Order;
import com.example.ecommercegestion.modele.OrderProduct;
import com.example.ecommercegestion.modele.Product;
import com.example.ecommercegestion.modele.SubCategory;
import com.example.ecommercegestion.vue.AddImageProductActivity;
import com.example.ecommercegestion.vue.AddProductActivity;
import com.example.ecommercegestion.vue.ContactActivity;
import com.example.ecommercegestion.vue.CustomerActivity;
import com.example.ecommercegestion.vue.MainActivity;
import com.example.ecommercegestion.vue.OrderActivity;
import com.example.ecommercegestion.vue.ProductActivity;
import com.example.ecommercegestion.vue.ViewCustomerActivity;
import com.example.ecommercegestion.vue.ViewOrderActivity;
import java.util.ArrayList;


public final class Control {

    private Boolean isAuth = null;
    private String token = "";

    private static Control instance = null;
    private static AccesDistant accesDistant;
    private static Context context;

    private static Product product;
    private ArrayList<Product> allProduct = new ArrayList<>();

    private ArrayList<SubCategory> allSubCategories = new ArrayList<>();

    private static Contact contact;
    private ArrayList<Contact> allContact = new ArrayList<>();

    private static Customer customer;
    private ArrayList<Customer> allCustomer = new ArrayList<>();

    private static Address address;
    private ArrayList<Address> allAddress = new ArrayList<>();

    private ArrayList<Order> allOrderCustomer = new ArrayList<>();

    private static Order order;
    private ArrayList<Order> allOrder = new ArrayList<>();

    private ArrayList<OrderProduct> allOrderProduct = new ArrayList<>();

    // permet de savoir si l'utilisateur vient du dashboard ou de la ProductActivity
    private static Boolean comeToListProduct = false;
    // context permettant au controleur de lancer des methode dans les vues
    private Context mainActivity = null;

    private Context viewOrderActivity = null;
    private Context orderActivity = null;

    private Context productActivity = null;
    private Context contactActivity = null;

    private Context customerActivity = null;
    private Context viewCustomerActivity = null;

    private Context addproductActivity = null;
    private Context addImgProductActivity = null;

    private Boolean addImgProduct = false;
    private static boolean[] orderDetails = new boolean[2];
    private static boolean[] customerDetails = new boolean[2];


    private Control() {
        super();
    }

    /**
     *
     * @return une instance de la classe control (singleton)
     */
    public static final Control getInstance(Context context){
        if (context != null){
            Control.context = context;
        }
        if (instance == null){
            Control.instance = new Control();
            accesDistant = new AccesDistant();
        }
        return Control.instance;
    }

    /***
     * Getter
     * @return renvoie customerDetails
     */
    public boolean[] getCustomerDetails() {
        return customerDetails;
    }

    /***
     * Setter
     * @param orderDetails la nouvelle valeur a initialiser
     * @param position la position dans la liste
     */
    public void setCustomerDetails(boolean orderDetails, int position) {
        Control.customerDetails[position] = orderDetails;
    }

    /***
     * Getter
     * @return la valeur de viewCustomerActivity
     */
    private Context getViewCustomerActivity() {
        return viewCustomerActivity;
    }

    /***
     * Setter
     * @param viewCustomerActivity la nouvelle valeur a initialiser
     */
    public void setViewCustomerActivity(Context viewCustomerActivity) {
        this.viewCustomerActivity = viewCustomerActivity;
    }

    /***
     * Getter
     * @return la valeur de orderDetails
     */
    public boolean[] getOrderDetails() {
        return orderDetails;
    }

    /***
     * Setter
     * @param orderDetails la nouvelle valeur a initialiser
     */
    public void setOrderDetails(boolean orderDetails, int position) {
        Control.orderDetails[position] = orderDetails;
    }

    /***
     * Getter
     * @return la valeur de customerActivity
     */
    private Context getCustomerActivity() {
        return customerActivity;
    }

    /***
     * Setter
     * @param customerActivity la nouvelle valeur a initialiser
     */
    public void setCustomerActivity(Context customerActivity) {
        this.customerActivity = customerActivity;
    }

    /***
     * Getter
     * @return la valeur de orderActivity
     */
    private Context getOrderActivity() {
        return orderActivity;
    }

    /***
     * Setter
     * @param orderActivity la nouvelle valeur a initialiser
     */
    public void setOrderActivity(Context orderActivity) {
        this.orderActivity = orderActivity;
    }

    /***
     * Getter
     * @return la valeur de contactActivity
     */
    private Context getContactActivity() {
        return contactActivity;
    }

    /***
     * Setter
     * @param contactActivity la nouvelle valeur a initialiser
     */
    public void setContactActivity(Context contactActivity) {
        this.contactActivity = contactActivity;
    }

    /***
     * Setter
     * @param instance la nouvelle valeur a initialiser
     */
    public void setInstance(Control instance) {
        // on remes le token a "" et isauth a false si l'instance est null on remet le controleur a zero
        if (instance == null) {
            setToken("");
            setAuth(false);
        }
        Control.instance = instance;
    }

    /***
     * Getter
     * @return la valeur de viewOrderActivity
     */
    private Context getViewOrderActivity() {
        return viewOrderActivity;
    }

    /***
     * Setter
     * @param viewOrderActivity la nouvelle valeur a initialiser
     */
    public void setViewOrderActivity(Context viewOrderActivity) {
        this.viewOrderActivity = viewOrderActivity;
    }

    /***
     * Getter
     * @return la valeur de productActivity
     */
    private Context getProductActivity() {
        return productActivity;
    }

    /***
     * Setter
     * @param productActivity la nouvelle valeur a initialiser
     */
    public void setProductActivity(Context productActivity) {
        this.productActivity = productActivity;
    }

    /***
     * Getter
     * @return la valeur de mainActivity
     */
    private Context getMainActivity() {
        return mainActivity;
    }

    /***
     * Setter
     * @param mainActivity la nouvelle valeur a initialiser
     */
    public void setMainActivity(Context mainActivity) {
        this.mainActivity = mainActivity;
    }

    /***
     * Permet de mettre a jour l'activity MainActivity apres le retour du serveur
     * @param state l'etat du retour (true succes, false erreur)
     * @param message le message a affiché ( a laisse vide en cas de succes)
     */
    public void runDashBoard(Boolean state, String message){
        if (state){
            this.setAuth(true);
            ((MainActivity) getMainActivity()).isConnected();
        }else {
            this.setAuth(false);
            ((MainActivity) getMainActivity()).displayToast(message);
            ((MainActivity) getMainActivity()).enabledBtnLogin();
        }
    }

    /***
     * Getter
     * @return la valeur de isAuth
     */
    public Boolean getAuth() {
        return isAuth;
    }

    /***
     * Setter
     * @param auth la nouvelle valeur a initialiser
     */
    public void setAuth(Boolean auth) {
        isAuth = auth;
    }

    /***
     * Getter
     * @return la valeur de comeToListProduct
     */
    public Boolean getComeToListProduct() {
        return comeToListProduct;
    }

    /***
     * Setter
     * @param comeToListProduct la nouvelle valeur a initialiser
     */
    public void setComeToListProduct(Boolean comeToListProduct) {
        Control.comeToListProduct = comeToListProduct;
    }

    /***
     * Getter
     * @return la valeur de product
     */
    public Product getProduct() {
        return product;
    }

    /***
     * Setter
     * @param product la nouvelle valeur a initialiser
     */
    public void setProduct(Product product) {
        Control.product = product;
    }

    /***
     * Getter
     * @return la valeur de address
     */
    public Address getAddress() {
        if (address == null){
            return null;
        }else {
            return address;
        }
    }

    /***
     * Setter
     * @param address la nouvelle valeur a initialiser
     */
    public void setAddress(Address address) {
        Control.address = address;
    }

    /***
     * Getter
     * @return la valeur de order
     */
    public Order getOrder() {
        return order;
    }

    /***
     * Setter
     * @param order la nouvelle valeur a initialiser
     */
    public void setOrder(Order order) {
        Control.order = order;
    }

    /***
     * Getter
     * @return la valeur de customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /***
     * Setter
     * @param customer la nouvelle valeur a initialiser
     */
    public void setCustomer(Customer customer) {
        Control.customer = customer;
    }

    /***
     * Getter
     * @return la valeur de contact
     */
    public Contact getContact() {
        if (contact == null){
            return null;
        }else {
            return contact;
        }
    }
    /***
     * Setter
     * @param contact la nouvelle valeur a initialiser
     */
    public void setContact(Contact contact) {
        Control.contact = contact;
    }

    /***
     * Permet de recuperer toutes les demande de contact
     */
    public void getContactData() {
        accesDistant.sendRequest("GET","https://gestion.brocanticke.com/api/contact/", getToken());
    }

    /***
     * Permet de récuperer la liste de toutes les commandes
     */
    public void getOrderData() {
        accesDistant.sendRequest("GET","https://gestion.brocanticke.com/api/order/", getToken());
    }

    /***
     * Permet de recuperer la liste de tous les client
     */
    public void getCustomerData() {
        accesDistant.sendRequest("GET","https://gestion.brocanticke.com/api/customer/", getToken());
    }

    /***
     * Permet de recuperer la liste de tous les produits
     */
    public void getProductData(){
        accesDistant.sendRequest("GET","https://gestion.brocanticke.com/api/product/", getToken());
    }

    /***
     * Permet de recuperer les information relatif a un client
     * @param idCustomer l'id du client
     */
    public void getInfoAboutCustomer(Integer idCustomer){
        accesDistant.sendRequest("GET", String.format("https://gestion.brocanticke.com/api/address/%s", idCustomer.toString()), token);
        accesDistant.sendRequest("GET", String.format("https://gestion.brocanticke.com/api/order/?customer_id=%s", idCustomer.toString()), token);
    }

    /***
     * Permet de recuperer la liste de tous les produit contenu dans une commande
     * @param idCustomer l'id du client
     * @param idOrder l'id de la commande
     */
    public void getProductInOrder(Integer idCustomer, Integer idOrder){
        accesDistant.sendRequest("GET", String.format("https://gestion.brocanticke.com/api/order/details/%s/%s", idOrder.toString(), idCustomer.toString()), token);
    }

    /***
     * Récupere l'addresse de livraison d'une commande
     * @param idCustomer l'id du client
     * @param idOrder l'id de la commande
     */
    public void getAddressForOrder(Integer idCustomer, Integer idOrder){
        accesDistant.sendRequest("GET", String.format("https://gestion.brocanticke.com/api/address/order/%s/%s", idOrder.toString(), idCustomer.toString()), token);
    }

    /***
     * Permet de se connecter a l'api
     * @param username l'username a utilisé
     * @param password le mot de passe a utilisé
     */
    public void connection(String username, String password){
        accesDistant.connection(username, password);
    }

    /***
     * Getter
     * @return la valeur de token. Permet de récupérer le token d'authentification
     */
    private String getToken() {
        return token;
    }

    /***
     * Setter
     * @param token le token d'authentification a utilisé
     */
    public void setToken(String token) {
        this.token = token;
    }

    /***
     * Getter
     * @return la valeur de allProduct
     */
    public ArrayList<Product> getAllProduct() {
        return allProduct;
    }

    /***
     * Setter
     * @param allProduct la nouvelle valeur a initialiser
     */
    public void setAllProduct(ArrayList<Product> allProduct) {
        this.allProduct = allProduct;
    }

    /***
     * Setter
     * @param allSubCategories la nouvelle valeur a initialiser
     */
    public void setAllSubCategories(ArrayList<SubCategory> allSubCategories) {
        this.allSubCategories = allSubCategories;
    }

    /***
     * Getter
     * @return la valeur de allSubCategories
     */
    public ArrayList<SubCategory> getAllSubCategories() { return this.allSubCategories; }

    /***
     * Getter
     * @return la valeur de allContact
     */
    public ArrayList<Contact> getAllContact() {
        return allContact;
    }

    /***
     * Setter
     * @param allContact la nouvelle valeur a initialiser
     */
    public void setAllContact(ArrayList<Contact> allContact) {
        this.allContact = allContact;
    }

    /***
     * Getter
     * @return la valeur de allCustomer
     */
    public ArrayList<Customer> getAllCustomer() {
        return allCustomer;
    }

    /***
     * Setter
     * @param allCustomer la nouvelle valeur a initialiser
     */
    public void setAllCustomer(ArrayList<Customer> allCustomer) {
        this.allCustomer = allCustomer;
    }

    /***
     * Getter
     * @return la valeur de allAddress
     */
    public ArrayList<Address> getAllAddress() {
        return allAddress;
    }

    /***
     * Setter
     * @param allAddress la nouvelle valeur a initialiser
     */
    public void setAllAddress(ArrayList<Address> allAddress) {
        this.allAddress = allAddress;
    }

    /***
     * Getter
     * @return la valeur de allOrder
     */
    public ArrayList<Order> getAllOrder() {
        return allOrder;
    }

    /***
     * Setter
     * @param allOrder la nouvelle valeur a initialiser
     */
    public void setAllOrder(ArrayList<Order> allOrder) {
        this.allOrder = allOrder;
    }

    /***
     * Getter
     * @return la valeur de allOrderProduct
     */
    public ArrayList<OrderProduct> getAllOrderProduct() {
        return allOrderProduct;
    }

    /***
     * Setter
     * @param allOrderProduct la nouvelle valeur a initialiser
     */
    public void setAllOrderProduct(ArrayList<OrderProduct> allOrderProduct) {
        this.allOrderProduct = allOrderProduct;
    }

    /**
     * permet de supprimer un contact dans la base distante et dans la collection
     * @param contact: le contact a supprimer
     */
    public void delContact(Contact contact){
        accesDistant.sendRequest("DELETE", String.format("https://gestion.brocanticke.com/api/contact/%s", contact.getId().toString()), token);
        allContact.remove(contact);
        ((ContactActivity) context).displayToast("Contact Supprimé avec Succes !");
    }

    /**
     * permet de supprimer un produit dans la base distante et dans la collection
     * @param product: le produit a supprimer
     */
    public void delProduct(Product product){
        accesDistant.sendRequest("DELETE", String.format("https://gestion.brocanticke.com/api/update/product/%s", product.getId().toString()), token);
        allProduct.remove(product);
        ((ProductActivity) context).productDelDone();
    }

    /***
     * Permet de mettre a jour une commande
     * @param state l'etat de la commande
     * @param delivery le lien de suivi du colis
     */
    public void updateOrder(String state, String delivery){
        order.setStatus(state);
        order.setDelivery(delivery);
        accesDistant.updateOrder(token, order);
        setViewOrderActivity(context);
    }

    /***
     * Permet de mettre a jour l'activity ViewOrderActivity apres le retour du serveur
     * @param state l'etat du retour (true succes, false erreur)
     */
    public void updateOrderState(Boolean state){
        if (state){
            ((ViewOrderActivity) getViewOrderActivity()).updateSucces();
        }else {
            ((ViewOrderActivity) getViewOrderActivity()).updateError();
        }

    }

    /***
     * Permet de mettre a la liste de details des produit commandé dans l'activity ViewOrderActivity apres le retour du serveur
     * @param state l'etat du retour (true succes, false erreur)
     */
    public void displayOrderDetails(Boolean state) {
        if (state) {
            ((ViewOrderActivity) getViewOrderActivity()).creerListProduct();
            ((ViewOrderActivity) getViewOrderActivity()).recupOrder();
        }else {
            ((ViewOrderActivity) getViewOrderActivity()).orderDisplayError();
        }
    }

    /***
     * Permet de mettre a jour l'activity ProductActivity apres le retour du serveur
     * @param state l'etat du retour (true succes, false erreur)
     */
    public void displayProduct(Boolean state){
        if (state) {
            ((ProductActivity) getProductActivity()).creerList();
        }else {
            ((ProductActivity) getProductActivity()).productDisplayEroor();
        }
    }

    /***
     * Permet de mettre a jour l'activity ContactActivity apres le retour du serveur
     * @param state l'etat du retour (true succes, false erreur)
     */
    public void displayContact(Boolean state){
        if (state) {
            ((ContactActivity) getContactActivity()).creerList();
        }else {
            ((ContactActivity) getContactActivity()).displayToast("Une erreur c'est produite, impossible d'afficher la liste des produit.");
        }
    }

    /***
     * Permet de mettre a jour l'activity OrderActivity apres le retour du serveur
     * @param state l'etat du retour (true succes, false erreur)
     */
    public void displayOrder(Boolean state){
        if (state) {
            ((OrderActivity) getOrderActivity()).creerList();
        }else {
            ((OrderActivity) getOrderActivity()).orderDisplayError();
        }
    }

    /***
     * Permet de mettre a jour l'activity CustomerActivity apres le retour du serveur
     * @param state l'etat du retour (true succes, false erreur)
     */
    public void displayCustomer(Boolean state){
        if (state) {
            ((CustomerActivity) getCustomerActivity()).creerList();
        }else {
           ((CustomerActivity) getCustomerActivity()).customerDisplayError();
        }
    }

    /***
     * Permet de mettre a jour l'activity ViewCustomerActivity apres le retour du serveur
     * @param state l'etat du retour (true succes, false erreur)
     */
    public void displayCustomerDetails(Boolean state) {
        if (state) {
            ((ViewCustomerActivity) getViewCustomerActivity()).creerListAddress();
            ((ViewCustomerActivity) getViewCustomerActivity()).creerListOrder();
            // ((ViewCustomerActivity) getViewCustomerActivity()).recupCustomer();
        }else {
            ((ViewCustomerActivity) getViewCustomerActivity()).customerDisplayError();
        }
    }

    /***
     * Getter
     * @return la valeur de allOrderCustomer
     */
    public ArrayList<Order> getAllOrderCustomer() {
        return allOrderCustomer;
    }

    /***
     * Setter
     * @param allOrderCustomer la nouvelle valeur a initialiser
     */
    public void setAllOrderCustomer(ArrayList<Order> allOrderCustomer) {
        this.allOrderCustomer = allOrderCustomer;
    }

    /***
     * Permet de modifié un produit
     * @param product Un object de type Product
     */
    public void modifyProduct(Product product){
        this.getProduct().updateProduct(product);
        accesDistant.updateProduct(token, this.getProduct());
    }

    /***
     * Permet d'ajouter un nouveau produit dans la base de données
     * @param product Un object de type Product
     */
    public void addNewProduct(Product product){
        accesDistant.createNewProduct(token, product);
    }

    /***
     * Permet de générer une liste contenant toutes les sous catégorie disponible
     * @param product un object de type Product
     * @return Une liste contenant toutes les categorie
     */
    public String[] generateCategoryProduct(Product product){
        int lenCategory = allSubCategories.size();
        String[] category = new String[lenCategory];
        String buff;
        int pos = 0;
        for (int i = 0; i<lenCategory; i++){
            category[i] = String.format("%s %s", allSubCategories.get(i).getId().toString(), allSubCategories.get(i).getName());
            if (allSubCategories.get(i).getId().equals(product.getCategoryId())) { pos = i; }
        }
        buff = category[pos];
        category[pos] = category[0];
        category[0] = buff;
        return category;
    }

    /***
     * Permet de générer une liste contenant toutes les sous catégorie disponible
     * @return Une liste contenant toutes les categorie
     */
    public String[] generateCategoryProduct(){
        int lenCategory = allSubCategories.size();
        String[] category = new String[lenCategory];
        for (int i = 0; i<lenCategory; i++){
            category[i] = String.format("%s %s", allSubCategories.get(i).getId().toString(), allSubCategories.get(i).getName());
        }
        return category;
    }

    /***
     * Permet de générer une liste de tous les produits disponible
     * @return Une liste contenant tous les produit disponible
     */
    public String[] generateNameProduct(){
        int lenAllProduct = allProduct.size();
        String[] product = new String[lenAllProduct];
        for (int i = 0; i<lenAllProduct; i++){
            product[i] = String.format("%s %s", allProduct.get(i).getId().toString(), allProduct.get(i).getName());
        }
        return product;
    }

    /***
     * Getter
     * @return la valeur de addproductActivity
     */
    private Context getAddProductActivity() {
        return addproductActivity;
    }

    /***
     * Setter
     * @param addproductActivity la nouvelle valeur a initialiser
     */
    public void setAddproductActivity(Context addproductActivity) {
        this.addproductActivity = addproductActivity;
    }

    /***
     * Permet de récuperer toutes les sous catégorie
     */
    public void getSubCategoryData() {
        accesDistant.sendRequest("GET", "https://gestion.brocanticke.com/api/subcategory/", token);
    }

    /***
     * Permet de mettre a jour la liste des catégorie dans l'activity AddProductActivity apres le retour du serveur
     * @param state l'etat du retour (true succes, false erreur)
     */
    public void displaySubCategory(Boolean state) {
        if (state) {
            ((AddProductActivity) getAddProductActivity()).displayCategory();
        }else {
            ((AddProductActivity) getAddProductActivity()).displayToast("Une erreur est survenu, impossible d'afficher les categories !");
        }
    }

    /***
     * Permet de mettre a jour l'activity AddProductActivity apres le retour du serveur
     * @param state l'etat du retour (true succes, false erreur)
     */
    public void updateOrCreateProductState(boolean state){
        if (state){
            ((AddProductActivity) getAddProductActivity()).displayToast("Produit Ajouter avec Succes !");
            ((AddProductActivity) getAddProductActivity()).clearAllFields();
        }else {
            ((AddProductActivity) getAddProductActivity()).displayToast("Une erreur c'est produite.");
        }

    }

    /***
     * Permet de retrouvé l'index d'un produit dans la collection en fonction de l'id du produit
     * @param id l'id du produit
     * @return l'index de l'object trouvé
     */
    public Integer getProductById(Integer id){
        for (int i = 0; i< allProduct.size(); i++){
            if (allProduct.get(i).getId().equals(id)){
                return i;
            }
        }
        return null;
    }

    /***
     * Getter
     * @return la valeur de addImgProductActivity
     */
    private Context getAddImgProductActivity() {
        return addImgProductActivity;
    }

    /***
     * Setter
     * @param addImgProductActivity la nouvelle valeur a initialiser
     */
    public void setAddImgProductActivity(Context addImgProductActivity) {
        this.addImgProductActivity = addImgProductActivity;
    }

    /***
     * Getter
     * @return la valeur de addImgProduct
     */
    public Boolean getAddImgProduct() {
        return addImgProduct;
    }

    /***
     * Setter
     * @param addImgProduct la nouvelle valeur a initialiser
     */
    public void setAddImgProduct(Boolean addImgProduct) {
        this.addImgProduct = addImgProduct;
    }

    /***
     * Permet de mettre a jour la liste des produits dans l'activity AddImageProductActivity apres le retour du serveur
     * @param state l'etat du retour (true succes, false erreur)
     */
    public void displayProductAddImg(Boolean state){
        if (state) {
            ((AddImageProductActivity) getAddImgProductActivity()).displayProduct();
        }else {
            ((AddImageProductActivity) getAddImgProductActivity()).displayToast("Une erreur est survenu, impossible d'afficher le(s) produit(s) disponible !");
        }
    }

    /***
     * Permet d'inserer une nouvelle image pour un produit
     * @param image Un object de type Img
     */
    public void createNewImage(Img image){
        accesDistant.createNewImage(token, image);
    }

    /***
     * Permet de mettre a jour l'activity AddImageProductActivity apres le retour du serveur
     * @param state l'etat du retour (true succes, false erreur)
     */
    public void createImageState(Boolean state){
        if (state) {
            ((AddImageProductActivity) getAddImgProductActivity()).clearAllFields();
            ((AddImageProductActivity) getAddImgProductActivity()).displayToast("Image ajouter avec succes !");
        }else {
            ((AddImageProductActivity) getAddImgProductActivity()).displayToast("Une erreur est survenu, impossible d'ajouter l'image !");
        }
    }

    /***
     * Permet de vidé les attribut du controleur, cela permet si il y a beaucoup de donnes de reduire la consomation de ram de l'application
     */
    public void resetArraysAndContext(){
        // on remet tous les context a null
        mainActivity = null;
        viewOrderActivity = null;
        orderActivity = null;
        productActivity = null;
        contactActivity = null;
        customerActivity = null;
        viewCustomerActivity = null;
        addproductActivity = null;
        addImgProductActivity = null;
        // on remet tous les array a null pour economiser de la ram
        product = null;
        allProduct = null;
        allSubCategories = null;
        allContact = null;
        contact = null;
        customer = null;
        allCustomer = null;
        address = null;
        allAddress = null;
        allOrderCustomer = null;
        order = null;
        allOrder = null;
        allOrderProduct = null;
    }
}
