/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.util.ArrayList;
import java.util.List;

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
    private String instructions;
    private int totalServings;
    private double timeRequired;
    private List<String> flavorTags;
    
    public Recipe() {
        this("", "", new NutritionInfo(), new ArrayList<Ingredient>(), "", 0, 0, new ArrayList<String>());
    }
    
    public Recipe(String name, String desc, NutritionInfo nutVal, ArrayList<Ingredient> ingredients, String instructions,
            int totalServings, double timeRequired, List<String> flavorTags) {
        this.name = name;
        this.desc = desc;
        this.nutVal = nutVal;
        this.ingredients = ingredients;
        this.totalServings = totalServings;
        this.timeRequired = timeRequired;
        this.flavorTags = flavorTags;
        this.instructions = instructions;
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

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        if (instructions == null) instructions = "";
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
    
    public List<String> getFlavorTags() {
        return flavorTags;
    }
    
    public void setFlavorTags(List<String> flavorTags) {
        this.flavorTags = flavorTags;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String tag : flavorTags) {
            sb.append(tag);
            sb.append(", ");
        }
        return "Recipe [\n"
                + "id=" + id +",\n"
                + "name=" + name + ",\n"
                + "desc=" + desc.substring(0, Math.min(desc.length(), 20)) + ",\n"
                + "totalServings="+ totalServings + ",\n"
                + "timeRequired=" + timeRequired + ",\n"
                + "flavorTags=" + sb.toString() +
                "\n]";
    }
    
}