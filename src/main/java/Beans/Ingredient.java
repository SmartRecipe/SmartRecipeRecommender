/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.measure.Unit;
import javax.measure.quantity.Volume;

import tec.units.indriya.quantity.Quantities;

/**
 *
 * @author soup
 */
public class Ingredient {
    private static final Logger logger = Logger.getLogger(Ingredient.class.getName());
    
    private String name;
    private double quantity; //Will likely change later when we figure out more efficient way of quantifying ingredients.
    private String unit;
    
    public Ingredient() { 
    	this("", 0, "");
    }
    
    public Ingredient(String name, double quantity, String unit) {
        setName(name);
        this.quantity = quantity;
        this.unit = unit;
    }
    
    /**
     * Adds given quantity to the amount owned.
     * @param quantity How much of the ingredient is being added. (must be positive)
     */
    public boolean replenish(double quantity) {
    	if (quantity < 0) return false;
        this.quantity += quantity;
        return true;
    }
    
    /**
     * Subtracts given quantity from the amount owned.
     * @param amt How much of the ingredient is being used up (must be positive)
     * @return True if there is enough of the ingredient for the operation, false if the quantity is insufficient.
     */
    public boolean use(double amt) {
        if (amt > 0 && quantity - amt >= 0) {
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
        if (name == null) name = "";
        this.name = name;
    }
    
    public double getQuantity() {
        return quantity;
    }
    
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
    
    public String getUnit() {
        return unit;
    }
    
    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    public boolean hasEnough(Ingredient needed) {
        if (needed == null) {
            return true;
        }
        Unit<Volume> thisUnit = VolumeUnits.fromString(this.getUnit());
        Unit<Volume> otherUnit = VolumeUnits.fromString(needed.getUnit());
        if (thisUnit == null || otherUnit == null) {
            if (thisUnit==null) logger.log(Level.WARNING, "Missing unit type for: "+this.getUnit());
            if (otherUnit==null) logger.log(Level.WARNING, "Missing unit type for needed: "+needed.getUnit());
            logger.log(Level.WARNING, "Defaulting to quantity only comparison");
            return this.getQuantity() >= needed.getQuantity();
        }
        return Quantities.getQuantity(this.getQuantity(), thisUnit).isGreaterThanOrEqualTo(
                Quantities.getQuantity(needed.getQuantity(), otherUnit));
    }
  
    @Override
    public String toString() {
    	return this.getName()+": "+this.getQuantity() +" "+this.getUnit();
    }
}
