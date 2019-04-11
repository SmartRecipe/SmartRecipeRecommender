/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;

/**
 *
 * @author soup
 */
public class Recipe {
    @SerializedName("_id")
    private ObjectId id;

    private String name;
    private String desc;
    private NutritionInfo nutVal; //Nutritional value per serving
    private ArrayList<Ingredient> ingredients;
    private ArrayList<String> instructions;
    private int totalServings;
    private double timeRequired;
    
    public Recipe() { 
        this("", "", new NutritionInfo(), new ArrayList<Ingredient>(), new ArrayList<String>(), 0, 0);
    }
    
    public Recipe(String name, String desc, NutritionInfo nutVal, ArrayList<Ingredient> ingredients, ArrayList<String> instructions,
            int totalServings, double timeRequired) {
        setName(name);
        setDesc(desc);
        setNutVal(nutVal);
        setIngredients(ingredients);
        setInstructions(instructions);
        setTotalServings(totalServings);
        setTimeRequired(timeRequired);
    }

    public ObjectId getId() {
        return this.id;
    }

    public void setId(ObjectId id) {
        if (id == null) id = new ObjectId();
        this.id = id;
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

    /**
     * Set the time (in minutes) needed to create this recipe
     * @param timeRequired
     */
    public void setTimeRequired(double timeRequired) {
        this.timeRequired = timeRequired;
    }
}