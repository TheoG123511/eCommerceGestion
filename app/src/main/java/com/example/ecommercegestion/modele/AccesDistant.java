package com.example.ecommercegestion.modele;
import android.util.Log;
import com.example.ecommercegestion.controleur.Control;
import com.example.ecommercegestion.outils.AccesHTTP;
import com.example.ecommercegestion.outils.AccesHttpUpload;
import com.example.ecommercegestion.outils.AsyncResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;



public class AccesDistant implements AsyncResponse {
    // constante
    private static final String SERVEURTOKEN = "https://gestion.brocanticke.com/api/auth/token/login";
    // propriété
    private Control control;

    /***
     * Constructeur de la classe
     */
    public AccesDistant(){
        super();
        control = Control.getInstance(null);
    }
    /**
     * Fonction qui doit traité le retour du serveur distant
     * @param output: le retour du serveur au format String
     * @param url: l'url qui a permit d'avoir le output
     * @param method la method qui a ete utiliser
     */
    @Override
    public void processFinish(String output, String url, String method) {
        Log.d("serveur", "****************" + output);
        Log.d("serveur", "->" + url);
        Log.d("serveur", "method ->" + method);
        if (url.contains("/api/auth/token/login")){
            try {
                JSONObject info = new JSONObject(output);
                if (info.has("auth_token")){
                    String token = info.getString("auth_token");
                    control.setToken(token);
                    control.runDashBoard(true, "");
                }
            } catch (NullPointerException e){
                Log.d("AccesDistant", "Erreur dans la fonction processFinish() -> message : " + e.getMessage());
                control.runDashBoard(false, "Impossible de se connecter, les information de connection ne sont pas valide ou le serveur n'est pas accecible");
            } catch (Exception e) {
                control.runDashBoard(false, "Impossible de se connecter, Erreur Inconnu !");
            }
        }
        else if (url.contains("/api/update/order/")){
            try {
                JSONObject info = new JSONObject(output);
                if (!info.has("detail")){
                    Integer id = info.getInt("id");
                    String status = info.getString("status");
                    String startDate = info.getString("date");
                    String methodPayment = info.getString("methodpayment");
                    String delivery = info.getString("delivery");
                    Integer addressId = info.getInt("address");
                    Integer customerId = info.getInt("customer");
                    control.setOrder(new Order(id, status, startDate, methodPayment, addressId, customerId, delivery));
                    control.updateOrderState(true);
                }else {
                    control.updateOrderState(false);
                }

            } catch (JSONException e) {
                control.updateOrderState(false);
                Log.d("Erreur", "Conversion jSON impossible : "+ e.toString());
            }
        }
        else if (url.contains("/api/contact/")){
            try {
                JSONArray data = new JSONArray(output);
                ArrayList<Contact> lesContact = new ArrayList<>();
                for(int i = 0; i<data.length(); i++){
                    JSONObject info = new JSONObject(data.get(i).toString());
                    Integer id = info.getInt("id");
                    String name = info.getString("nom");
                    String email = info.getString("email");
                    String message = info.getString("message");
                    String date = info.getString("date");
                    String subject = info.getString("subject");
                    Contact contact = new Contact(id, name, email, message, date, subject);
                    lesContact.add(contact);
                }
                control.setAllContact(lesContact);
                control.displayContact(true);
            }
            catch (JSONException e) {
                Log.d("Erreur", "Conversion jSON impossible : " + e.toString() + "\nUrl = " + url);
            }
        }
        else if (url.contains("/api/product/") && method.equals("GET")){
            try {
                JSONArray data = new JSONArray(output);
                ArrayList<Product> lesProduits = new ArrayList<>();
                for(int i = 0; i<data.length(); i++){
                    JSONObject info = new JSONObject(data.get(i).toString());

                    Integer id = info.getInt("id");
                    String name = info.getString("name");
                    String description = info.getString("description");
                    double price = info.getDouble("price");
                    Integer quantity = info.getInt("quantity");
                    String date = info.getString("date");
                    String catgeroy = info.getString("category_name");
                    Integer catgeroyId = info.getInt("category");
                    String img = info.getString("image");
                    Product product = new Product(id, name, description, price, quantity, date, catgeroy, img, catgeroyId);
                    lesProduits.add(product);
                }
                control.setAllProduct(lesProduits);
                if (control.getAddImgProduct()){
                    control.displayProductAddImg(true);
                }else {
                    control.displayProduct(true);
                }

            }
            catch (JSONException e) {
                if (control.getAddImgProduct()){
                    control.displayProductAddImg(false);
                }else {
                    control.displayProduct(false);
                }
            } finally {
                control.setAddImgProduct(false);
            }
        }
        else if (url.contains("/api/product/") && method.equals("POST")) {
            try {
                JSONObject info = new JSONObject(output);
                Integer id = info.getInt("id");
                String name = info.getString("name");
                String description = info.getString("description");
                double price = info.getDouble("price");
                Integer quantity = info.getInt("quantity");
                String date = info.getString("date");
                String catgeroy = info.getString("category_name");
                Integer catgeroyId = info.getInt("category");
                String img = info.getString("image");
                new Product(id, name, description, price, quantity, date, catgeroy, img, catgeroyId);
                control.updateOrCreateProductState(true);
            } catch (Exception e) { control.updateOrCreateProductState(false); }
        }
        else if (url.contains("/api/customer/")){
            try {
                JSONArray data = new JSONArray(output);
                ArrayList<Customer> lesCustomer = new ArrayList<>();
                for(int i = 0; i<data.length(); i++){
                    JSONObject info = new JSONObject(data.get(i).toString());

                    Integer id = info.getInt("id");
                    String mobile = info.getString("mobile");
                    String gender = info.getString("gender");
                    Boolean isNewsLetter = info.getBoolean("isnewsletter");
                    Integer userId = info.getInt("user");
                    String lastName = info.getString("last_name");
                    String firstName = info.getString("first_name");
                    String username = info.getString("username");
                    String email = info.getString("email");
                    String date_joined = info.getString("date_joined");
                    String last_login = info.getString("last_login");


                    Customer customer = new Customer(id, mobile, gender, isNewsLetter, userId, lastName, firstName, username, email, date_joined, last_login);
                    lesCustomer.add(customer);
                }
                control.setAllCustomer(lesCustomer);
                control.displayCustomer(true);
            }
            catch (JSONException e) {
                Log.d("Erreur", "Conversion jSON impossible : " + e.toString() + "\nUrl = " + url);
                control.displayCustomer(false);
            }
        }
        else if (url.contains("/api/order/?customer_id=")) {
            try {
                JSONArray data = new JSONArray(output);
                ArrayList<Order> lesOrder = new ArrayList<>();
                for (int i = 0; i < data.length(); i++) {
                    JSONObject info = new JSONObject(data.get(i).toString());

                    Integer id = info.getInt("id");
                    String status = info.getString("status");
                    String startDate = info.getString("date");
                    String methodPayment = info.getString("methodpayment");
                    Integer addressId = info.getInt("address");
                    Integer customerId = info.getInt("customer");
                    String delivery = info.getString("delivery");
                    Order order = new Order(id, status, startDate, methodPayment, addressId, customerId, delivery);
                    lesOrder.add(order);
                }
                control.setAllOrderCustomer(lesOrder);
                control.setCustomerDetails(true, 1);
                if (checkOutputServer(control.getCustomerDetails())) {
                    control.displayCustomerDetails(true);
                }

            } catch (JSONException e) {
                Log.d("Erreur", "Conversion jSON impossible : " + e.toString() + "\nUrl = " + url);
            }
        }
        else if (url.contains("/api/order/details/")){
            try {
                JSONArray data = new JSONArray(output);
                ArrayList<OrderProduct> lesOrderProduct = new ArrayList<>();
                for(int i = 0; i<data.length(); i++){
                    JSONObject info = new JSONObject(data.get(i).toString());
                    Integer id = info.getInt("id");
                    Integer quantity = info.getInt("quantity");
                    Integer orderId = info.getInt("order");
                    Integer productId = info.getInt("product");
                    String productName = info.getString("product_name");
                    double productPrice = info.getDouble("product_price");
                    OrderProduct product = new OrderProduct(id, quantity, orderId, productId, productName, productPrice);
                    lesOrderProduct.add(product);
                }
                control.setAllOrderProduct(lesOrderProduct);
                control.setOrderDetails(true, 0);
                if (checkOutputServer(control.getOrderDetails())) {
                    control.displayOrderDetails(true);
                }
            }
            catch (JSONException e) {
                Log.d("Erreur", "Conversion jSON impossible : " + e.toString() + "\nUrl = " + url);
                control.displayOrder(false);
            }
        }
        else if (url.contains("/api/order/")){
            try {
                JSONArray data = new JSONArray(output);
                ArrayList<Order> lesOrder = new ArrayList<>();
                for(int i = 0; i<data.length(); i++){
                    JSONObject info = new JSONObject(data.get(i).toString());

                    Integer id = info.getInt("id");
                    String status = info.getString("status");
                    String startDate = info.getString("date");
                    String delivery = info.getString("delivery");
                    String methodPayment = info.getString("methodpayment");
                    Integer addressId = info.getInt("address");
                    Integer customerId = info.getInt("customer");
                    Order order = new Order(id, status, startDate, methodPayment, addressId, customerId, delivery);
                    lesOrder.add(order);
                }
                control.setAllOrder(lesOrder);
                control.displayOrder(true);
            }
            catch (JSONException e) {
                Log.d("Erreur", "Conversion jSON impossible : " + e.toString() + "\nUrl = " + url);
                control.displayOrder(false);
            }
        }
        else if (url.contains("/api/address/order/")) {
            try {
                JSONArray data = new JSONArray(output);
                for(int i = 0; i<data.length(); i++){
                    JSONObject info = new JSONObject(data.get(i).toString());
                    Integer id = info.getInt("id");
                    String firstName = info.getString("firstname");
                    String lastName = info.getString("lastname");
                    String address = info.getString("address");
                    String city = info.getString("city");
                    String cp = info.getString("cp");
                    String country = info.getString("country");
                    String mobile = info.getString("mobile");
                    Integer customerId = info.getInt("customer");
                    Address Address = new Address(id, firstName, lastName, address, city, cp, country, mobile, customerId);
                    control.setAddress(Address);
                }
                control.setOrderDetails(true, 1);
                if (checkOutputServer(control.getOrderDetails())) {
                    control.displayOrderDetails(true);
                }
            }
            catch (JSONException e) {
                Log.d("Erreur", "Conversion jSON impossible : " + e.toString() + "\nUrl = " + url);
            }
        }
        else if (url.contains("/api/address/")) {
            try {
                JSONArray data = new JSONArray(output);
                ArrayList<Address> lesAddress = new ArrayList<>();
                for(int i = 0; i<data.length(); i++){
                    JSONObject info = new JSONObject(data.get(i).toString());

                    Integer id = info.getInt("id");
                    String firstName = info.getString("firstname");
                    String lastName = info.getString("lastname");
                    String address = info.getString("address");
                    String city = info.getString("city");
                    String cp = info.getString("cp");
                    String country = info.getString("country");
                    String mobile = info.getString("mobile");
                    Integer customerId = info.getInt("customer");
                    Address Address = new Address(id, firstName, lastName, address, city, cp, country, mobile, customerId);
                    lesAddress.add(Address);
                }
                control.setAllAddress(lesAddress);
                control.setCustomerDetails(true, 0);
                if (checkOutputServer(control.getCustomerDetails())) {
                    control.displayCustomerDetails(true);
                }
            }
            catch (JSONException e) {
                Log.d("Erreur", "Conversion jSON impossible : " + e.toString() + "\nUrl = " + url);
            }
        }
        else if (url.contains("/api/subcategory/")) {
            try {
                JSONArray data = new JSONArray(output);
                ArrayList<SubCategory> allSubCategory = new ArrayList<>();
                for(int i = 0; i<data.length(); i++){
                    JSONObject info = new JSONObject(data.get(i).toString());

                    Integer id = info.getInt("id");
                    String name = info.getString("name");
                    String category_name = info.getString("category_name");
                    Integer category = info.getInt("category");
                    SubCategory Address = new SubCategory(id, name, category_name, category);
                    allSubCategory.add(Address);
                }
                control.setAllSubCategories(allSubCategory);
                control.displaySubCategory(true);
            }
            catch (JSONException e) {
                Log.d("Erreur", "Conversion jSON impossible : " + e.toString() + "\nUrl = " + url);
            }
        }
        else if (url.contains("/api/update/product/") && method.equals("PUT")) {
            try {
                JSONObject info = new JSONObject(output);
                Integer id = info.getInt("id");
                String name = info.getString("name");
                String description = info.getString("description");
                double price = info.getDouble("price");
                Integer quantity = info.getInt("quantity");
                String date = info.getString("date");
                String catgeroy = info.getString("category_name");
                Integer catgeroyId = info.getInt("category");
                String img = info.getString("image");
                Log.d("AccesDistant", "Image est une string donc");
                Integer index = control.getProductById(id);
                if (index != null){
                    control.getAllProduct().get(index).updateProduct(new Product(id, name, description, price, quantity, date, catgeroy, img, catgeroyId));
                    control.updateOrCreateProductState(true);
                }else { control.updateOrCreateProductState(false); }
            } catch (Exception e) { control.updateOrCreateProductState(false); }

        }
        else if (url.contains("/api/add/img/") && method.equals("POST")){
            try {
                JSONObject info = new JSONObject(output);
                Integer id = info.getInt("id");
                String image = info.getString("image");
                String date = info.getString("date");
                String title = info.getString("title");
                Integer product = info.getInt("product");
                new Img(id, image, date, product, title);
                control.createImageState(true);
            } catch (Exception e) { control.createImageState(false); }
        }
    }

    /***
     * Permet de se connecter a l'api
     * @param username l'username a utilisé
     * @param password le mot de passe a utilisé
     */
    public void connection(String username, String password){
        AccesHTTP accesDonnes = new AccesHTTP(SERVEURTOKEN, "POST", "");
        // lien de delegation
        accesDonnes.delegate = this;
        // ajout parametre
        accesDonnes.addParam("username", username);
        accesDonnes.addParam("password", password);
        accesDonnes.execute();
    }

    /***
     * Permet d'envoyer une requete
     * @param method la methode a utilisé dans la requete
     * @param url l'url a utilisé
     * @param token le token d'authentification a utilisé
     */
    public void sendRequest(String method, String url, String token){
        AccesHTTP accesDonnes = new AccesHTTP(url, method, token);
        // lien de delegation
        accesDonnes.delegate = this;
        // on recupere le token
        accesDonnes.execute();
    }

    /***
     * Permet de mettre a jour une commande
     * @param token le token d'authentification a utilisé
     * @param order un object de type Order a mettre a jours
     */
    public void updateOrder(String token, Order order){
        AccesHTTP accesDonnes = new AccesHTTP(String.format("https://gestion.brocanticke.com/api/update/order/%s", order.getId().toString()) , "PUT", token);
        // lien de delegation
        accesDonnes.delegate = this;
        order.updateOrder(accesDonnes);
        accesDonnes.execute();
    }

    /***
     * Permet de mettre a jour un produit
     * @param token le token d'authentification a utilisé
     * @param product le produit a mettre a jour
     */
    public void updateProduct(String token, Product product) {
        try {
            String url = String.format("https://gestion.brocanticke.com/api/update/product/%s", product.getId().toString());
            AccesHttpUpload accesDonnes = new AccesHttpUpload(url, "PUT", token, "UTF-8");
            accesDonnes.delegate = this;
            product.updateOrCreateNewProduct(accesDonnes, "PUT");
            accesDonnes.execute();
        }catch (Exception e) {
            Log.d("AccesDistant", "erreur = ", e);
        }

    }

    /***
     * Permet de creer un nouveau produit
     * @param token le token d'authentification a utilisé
     * @param product le produit a creer
     */
    public void createNewProduct(String token, Product product){
        try {
            String url = "https://gestion.brocanticke.com/api/product/";
            AccesHttpUpload accesDonnes = new AccesHttpUpload(url, "POST", token, "UTF-8");
            accesDonnes.delegate = this;
            product.updateOrCreateNewProduct(accesDonnes, "POST");
            accesDonnes.execute();
        }catch (Exception e) {
            Log.d("AccesDistant", "erreur = ", e);
        }
    }

    /***
     * Permet de creer une nouvelle image
     * @param token le token d'authentification a utilisé
     * @param img l'image a creer
     */
    public void createNewImage(String token, Img img){
        try {
            String url = "https://gestion.brocanticke.com/api/add/img/";
            AccesHttpUpload accesDonnes = new AccesHttpUpload(url, "POST", token, "UTF-8");
            accesDonnes.delegate = this;
            img.createNewImg(accesDonnes);
            accesDonnes.execute();
        }catch (Exception e) {
            Log.d("AccesDistant", "erreur = ", e);
        }
    }

    /***
     * Permet de verifié si toutes les valeur d'un Boolean[] sont vrai
     * @param toCheckArray l'array a vérifié
     * @return True si toutes les valeurs sont vrai sinon false
     */
    private boolean checkOutputServer(boolean[] toCheckArray){
        for (int i = 0; i< toCheckArray.length; i++) {
            Log.d("checkOutputServer",  String.format("%b", toCheckArray[i]));
            if (!toCheckArray[i]){
                return false;
            }
        }
        return true;
    }

}