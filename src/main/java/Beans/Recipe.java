/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.util.ArrayList;

/**
 *
 * @author soup
 */
public class Recipe {
    private String name;
    private String desc;
    private NutritionInfo nutVal; //Nutritional value per serving
    private ArrayList<Ingredient> ingredients;
    private ArrayList<String> instructions;
    private int totalServings;
    private double timeRequired;
    private ArrayList<String> flavorTags;
    
    public Recipe() {
        this("", "", new NutritionInfo(), new ArrayList<Ingredient>(), new ArrayList<String>(), 0, 0, new ArrayList<String>());
    }
    
    public Recipe(String name, String desc, NutritionInfo nutVal, ArrayList<Ingredient> ingredients, ArrayList<String> instructions,
            int totalServings, double timeRequired, ArrayList<String> flavorTags) {
        this.name = name;
        this.desc = desc;
        this.nutVal = nutVal;
        this.ingredients = ingredients;
        this.totalServings = totalServings;
        this.timeRequired = timeRequired;
        this.flavorTags = flavorTags;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        if (name == null) name = "";
        this.name = name;
    }
    
    public String getDesc() {
        return desc;
    }
    
    public void setDesc(String desc) {
        if (desc == null) desc = "";
        this.desc = desc;
    }
    
    public NutritionInfo getNutVal() {
        return nutVal;
    }
    
    public void setNutVal(NutritionInfo nutVal) {
        if (nutVal == null) nutVal = new NutritionInfo();
        this.nutVal = nutVal;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        if (ingredients == null) ingredients = new ArrayList<>();
        this.ingredients = ingredients;
    }

    public ArrayList<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(ArrayList<String> instructions) {
        if (instructions == null) instructions = new ArrayList<>();
        this.instructions = instructions;
    }

    public int getTotalServings() {
        return totalServings;
    }

    public void setTotalServings(int totalServings) {
        this.totalServings = totalServings;
    }
    
    public double getTimeRequired() {
        return timeRequired;
    }
    
    public void setTimeRequired(double timeRequired) {
        this.timeRequired = timeRequired;
    }
    
    public ArrayList<String> getFlavorTags() {
        return flavorTags;
    }
    
    public void setFlavorTags(ArrayList<String> flavorTags) {
        this.flavorTags = flavorTags;
    }
}