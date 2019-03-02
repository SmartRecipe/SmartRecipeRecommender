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
    //Should have fields pertaining to nutrition values, dietary implications, etc. Can all be pulled from Edamam API
    //Nutritional value ("nutVal") is represented as a String for now, but may want to consider creating class
    //to represent it as its own object, as it will have a number of different values within (calories, sugars, vitamins,
    //etc.).
    
    String name;
    String nutVal; //Nutritional value per gram (many ways to measure ingredients, so bare mass might be most consistent)
    
    public Ingredient() { }
    
    public Ingredient(String name, String nutVal) {
        this.name = name;
        this.nutVal = nutVal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNutVal() {
        return nutVal;
    }

    public void setNutVal(String nutVal) {
        this.nutVal = nutVal;
    }
    
    
}
