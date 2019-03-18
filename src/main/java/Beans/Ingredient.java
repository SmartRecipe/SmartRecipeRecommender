/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

/**
 *
 * @author soup
 */
public class Ingredient {
    private String name;
    private float quantity; //Will likely change later when we figure out more efficient way of quantifying ingredients.
                            //Note: In the context of a recipe, this is how much is needed; in the context of the virtual refrigerator, this is how much is owned.
    private NutritionInfo nutVal; //Nutritional value per gram (many ways to measure ingredients, so bare mass might be most consistent)
    
    public Ingredient() { }
    
    public Ingredient(String name, int numOwned, NutritionInfo nutVal) {
        this.name = name;
        this.quantity = numOwned;
        this.nutVal = nutVal;
    }
    
    /**
     * Adds given quantity to the amount owned.
     * @param quantity How much of the ingredient is being added.
     */
    public void replenish(float quantity) {
        this.quantity += quantity;
    }
    
    /**
     * Subtracts given quantity from the amount owned.
     * @param amt How much of the ingredient is being used up. (If value is -1, just uses all remaining.)
     * @return True if there is enough of the ingredient for the operation, false if the quantity is insufficient.
     */
    public boolean use(float amt) {
        if (amt == -1) {
            quantity = 0;
            return true;
        }
        else if (quantity - amt >= 0) {
            quantity -= amt;
            return true;
        }
        else
            return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public float getQuantity() {
        return quantity;
    }
    
    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public NutritionInfo getNutVal() {
        return nutVal;
    }

    public void setNutVal(NutritionInfo nutVal) {
        this.nutVal = nutVal;
    }   
}
