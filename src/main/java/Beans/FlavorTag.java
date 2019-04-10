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
public enum FlavorTag {
    BREAKFAST,
    LUNCH,
    DINNER,
    SNACK,
    MEXICAN,
    ITALIAN,
    FRENCH,
    ASIAN,
    INDIAN,
    SPICY,
    VEGETARIAN,
    SOUP,
    SALAD,
    SANDWICH,
    BURGER;
    
    private String flavor;
    
    FlavorTag(String flavor) {
        this.flavor = flavor;
    }
    
    @Override
    public String toString() {
        return flavor;
    }
}
