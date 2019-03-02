/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

/**
 *
 * @author soup
 */
public class Recipe {
    //Should contain a list of key-value pairs detailing the ingredients required and how many of each necessary
    //(both saved as Strings to maintain flexibility) Should also have a list of the instructions for the recipe;
    //each step is a separate object in the list Nutritional value ("nutVal") is represented as a String for now,
    //but may want to consider creating class to represent it as its own object, as it will have a number of different
    //values within (calories, sugars, vitamins, etc.).
    
    String name;
    String desc;
    String nutVal; //Nutritional value per serving
    SimpleEntry<Ingredient, String> ingredients;
    ArrayList<String> instructions;
    String servingSize;
    float totalServings;
    
    public Recipe() { }
    
    public Recipe(String name, String desc, String nutVal, SimpleEntry<Ingredient, String> ingredients,
            ArrayList<String> instructions, String servingSize, float totalServings) {
        this.name = name;
        this.desc = desc;
        this.nutVal = nutVal;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.servingSize = servingSize;
        this.totalServings = totalServings;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDesc() {
        return desc;
    }
    
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    public String getNutVal() {
        return nutVal;
    }
    
    public void setNutVal(String nutVal) {
        this.nutVal = nutVal;
    }

    public SimpleEntry<Ingredient, String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(SimpleEntry<Ingredient, String> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(ArrayList<String> instructions) {
        this.instructions = instructions;
    }

    public String getServingSize() {
        return servingSize;
    }

    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }

    public float getTotalServings() {
        return totalServings;
    }

    public void setTotalServings(float totalServings) {
        this.totalServings = totalServings;
    }
}