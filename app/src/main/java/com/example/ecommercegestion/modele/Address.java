package com.example.ecommercegestion.modele;

public class Address {
    private Integer id;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String cp;
    private String country;
    private String mobile;
    private Integer customerId;

    /***
     * Constructeur de la classe
     * @param id l'id de l'addresse
     * @param firstName le prénom
     * @param lastName le nom de famille
     * @param address l'adresse
     * @param city la ville
     * @param cp le code postal
     * @param country le pays
     * @param mobile le numero de telephone
     * @param customerId l'id du client
     */
    public Address(Integer id, String firstName, String lastName, String address, String city, String cp, String country, String mobile, Integer customerId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.cp = cp;
        this.country = country;
        this.mobile = mobile;
        this.customerId = customerId;
    }

    /***
     * Getter
     * @return l'id de l'addresse
     */
    public Integer getId() {
        return id;
    }

    /**
     * Getter
     * @return le prénom relatif a cette addresse
     */
    public String getFirstName() {
        return firstName;
    }

    /***
     * Getter
     * @return le nom de famille relatif a cette addresse
     */
    public String getLastName() {
        return lastName;
    }

    /***
     * Getter
     * @return l'addresse
     */
    public String getAddress() {
        return address;
    }

    /***
     * Getter
     * @return la ville
     */
    public String getCity() {
        return city;
    }

    /***
     * Getter
     * @return le code postal
     */
    public String getCp() {
        return cp;
    }

    /***
     * Getter
     * @return le pays
     */
    public String getCountry() {
        return country;
    }

    /***
     * Getter
     * @return le numero de telephone
     */
    public String getMobile() {
        return mobile;
    }

    /***
     * Getter
     * @return l'id du client
     */
    public Integer getCustomerId() {
        return customerId;
    }
}
