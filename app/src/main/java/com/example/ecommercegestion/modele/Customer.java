package com.example.ecommercegestion.modele;

public class Customer {


    private Integer id;
    private String mobile;
    private String gender;
    private Boolean isNewsLetter;
    private Integer userId;
    private String lastName;
    private String firstName;
    private String username;
    private String email;
    private String date_joined;
    private String last_login;

    /***
     * Constructeur de la classe
     * @param id l'id du client
     * @param mobile le numero de tel du client
     * @param gender le type de client (Monsieur ou Madame)
     * @param isNewsLetter inscrit a la newsLetter
     * @param userId l'id de l'utilisateur
     * @param lastName le nom de famille du client
     * @param firstName le prénom du client
     * @param username l'username du client
     * @param email l'email du client
     * @param date_joined la date a laquel le client a creer sont compte
     * @param last_login la date de derniere connection au compte du client
     */
    public Customer(Integer id, String mobile, String gender, Boolean isNewsLetter, Integer userId,
                    String lastName, String firstName, String username, String email,
                    String date_joined, String last_login) {
        this.id = id;
        this.mobile = mobile;
        this.gender = gender;
        this.isNewsLetter = isNewsLetter;
        this.userId = userId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.username = username;
        this.email = email;
        this.date_joined = date_joined;
        this.last_login = last_login;
    }

    /**
     * Getter
     * @return l'id du client
     */
    public Integer getId() {
        return id;
    }

    /***
     * Getter
     * @return le numero du client
     */
    public String getMobile() {
        return mobile;
    }

    /***
     * Getter
     * @return le type de client
     */
    public String getGender() {
        return gender;
    }

    /***
     * Getter
     * @return si le client est inscrit a la newsLetter
     */
    public Boolean getNewsLetter() {
        return isNewsLetter;
    }

    /***
     * Getter
     * @return le nom de famille du client
     */
    public String getLastName() {
        return lastName;
    }

    /***
     * Getter
     * @return le prénom du client
     */
    public String getFirstName() {
        return firstName;
    }

    /***
     * Getter
     * @return l'username du client
     */
    public String getUsername() {
        return username;
    }

    /***
     * Getter
     * @return l'email du client
     */
    public String getEmail() {
        return email;
    }

    /***
     * Getter
     * @return la date de création du compte
     */
    public String getDate_joined() {
        return date_joined;
    }

    /***
     * Getter
     * @return la date de derniere connection du compte
     */
    public String getLast_login() {
        return last_login;
    }
}
