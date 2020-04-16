package com.example.ecommercegestion.modele;

public class Contact {

    private String name;
    private String email;
    private String message;
    private String date;
    private String subject;
    private Integer id;

    /***
     * Constructeur de la classe
     * @param id l'id de la demande de contact
     * @param name la nom du demandeur
     * @param email l'email du demandeur
     * @param message le message
     * @param date la date d'envoie du message
     * @param subject le sujet du message
     */
    public Contact(Integer id, String name, String email, String message, String date, String subject) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.message = message;
        this.date = date;
        this.subject = subject;
    }

    /***
     * Getter
     * @return le nom du demandeur
     */
    public String getName() {
        return name;
    }

    /***
     * Getter
     * @return l'email du demandeur
     */
    public String getEmail() {
        return email;
    }

    /***
     * Getter
     * @return le message
     */
    public String getMessage() {
        return message;
    }

    /***
     * Getter
     * @return la date d'envoie du message
     */
    public String getDate() {
        return date;
    }

    /***
     * Getter
     * @return le sujet du message
     */
    public String getSubject() {
        return subject;
    }

    /***
     * Getter
     * @return l'id de la demande de contact
     */
    public Integer getId() {
        return id;
    }
}
