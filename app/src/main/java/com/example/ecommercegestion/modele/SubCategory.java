package com.example.ecommercegestion.modele;


public class SubCategory {
    private Integer id;
    private String name;
    private String categoryName;
    private Integer categoryId;

    /***
     * Constructeur de la classe
     * @param id l'id
     * @param name le nom de la sous catégorie
     * @param categoryName le nom de la catégorie
     * @param categoryId l'id de la catégorie
     */
    public SubCategory(Integer id, String name, String categoryName, Integer categoryId) {
        this.id = id;
        this.name = name;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
    }

    /**
     * Getter sur id
     * @return l'id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Getter sur le nom de la sous catégorie
     * @return le nom de la sous catégorie
     */
    public String getName() {
        return name;
    }
}
