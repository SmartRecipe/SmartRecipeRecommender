package Beans;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ryan D Smith
 */
public class NutritionInfo {

    private double servingsPerContainer;
    // we're doing it in grams for the sake of consistency
    private double servingSize;

    // the following values are in g / serving
    private double calories, totalFat, saturatedFat, transFat;
    private double carbs, fiber, sugar, addedSugar, protein;

    // the following values are in mg / serving
    private double cholesterol, sodium;

    // for vitamins and all that useless nonsense
    private Map<String, Number> extras;

    public NutritionInfo(double servingsPerContainer,
                         double servingSize,
                         double calories,
                         double totalFat,
                         double saturatedFat,
                         double transFat,
                         double carbs,
                         double fiber,
                         double sugar,
                         double addedSugar,
                         double protein,
                         double cholesterol,
                         double sodium) {

        this.servingsPerContainer = servingsPerContainer;
        this.servingSize = servingSize;
        this.calories = calories;
        this.totalFat = totalFat;
        this.saturatedFat = saturatedFat;
        this.transFat = transFat;
        this.carbs = carbs;
        this.fiber = fiber;
        this.sugar = sugar;
        this.addedSugar = addedSugar;
        this.protein = protein;
        this.cholesterol = cholesterol;
        this.sodium = sodium;
        this.extras = new HashMap<>();
    }

    /**
     * @return The servings per container
     */
    public double getServingsPerContainer() {
        return servingsPerContainer;
    }

    /**
     * @param servingsPerContainer The servings per container
     */
    public void setServingsPerContainer(double servingsPerContainer) {
        this.servingsPerContainer = servingsPerContainer;
    }

    /**
     * @return The serving size in grams
     */
    public double getServingSize() {
        return servingSize;
    }

    /**
     * @param servingSize The serving size in grams
     */
    public void setServingSize(double servingSize) {
        this.servingSize = servingSize;
    }

    /**
     * @return The calorie information in grams per serving
     */
    public double getCalories() {
        return calories;
    }

    /**
     * @param calories The calorie information in grams per serving
     */
    public void setCalories(double calories) {
        this.calories = calories;
    }

    /**
     * @return The total fat content in grams per serving
     */
    public double getTotalFat() {
        return totalFat;
    }

    /**
     * @param totalFat The total fat content in grams per serving
     */
    public void setTotalFat(double totalFat) {
        this.totalFat = totalFat;
    }

    /**
     * @return The saturated fat content in grams per serving
     */
    public double getSaturatedFat() {
        return saturatedFat;
    }

    /**
     * @param saturatedFat The saturated fat content in grams per serving
     */
    public void setSaturatedFat(double saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    /**
     * @return The trans fat content in grams per serving
     */
    public double getTransFat() {
        return transFat;
    }

    /**
     * @param transFat The trans fat content in grams per serving
     */
    public void setTransFat(double transFat) {
        this.transFat = transFat;
    }

    /**
     * @return The total carbohydrates in grams per serving
     */
    public double getCarbs() {
        return carbs;
    }

    /**
     * @param carbs The total carbohydrates in grams per serving
     */
    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    /**
     * @return The <emph>total</emph> fiber content in grams per serving
     */
    public double getFiber() {
        return fiber;
    }

    /**
     * @param fiber The <emph>total</emph> fiber content in grams per serving
     */
    public void setFiber(double fiber) {
        this.fiber = fiber;
    }

    /**
     * @return The sugar content in grams per serving
     */
    public double getSugar() {
        return sugar;
    }

    /**
     * @param sugar The sugar content in grams per serving
     */
    public void setSugar(double sugar) {
        this.sugar = sugar;
    }

    /**
     * @return The added sugars in grams per serving
     */
    public double getAddedSugar() {
        return addedSugar;
    }

    /**
     * @param addedSugar The added sugars in grams per serving
     */
    public void setAddedSugar(double addedSugar) {
        this.addedSugar = addedSugar;
    }

    /**
     * @return The protein content in grams per serving
     */
    public double getProtein() {
        return protein;
    }

    /**
     * @param protein The protein content in grams per serving
     */
    public void setProtein(double protein) {
        this.protein = protein;
    }

    /**
     * @return The cholesterol content in milligrams per serving
     */
    public double getCholesterol() {
        return cholesterol;
    }

    /**
     * @param cholesterol The cholesterol content in milligrams per serving
     */
    public void setCholesterol(double cholesterol) {
        this.cholesterol = cholesterol;
    }

    /**
     * @return The sodium content in milligrams per serving
     */
    public double getSodium() {
        return sodium;
    }

    /**
     * @param sodium The sodium content in milligrams per serving
     */
    public void setSodium(double sodium) {
        this.sodium = sodium;
    }

    /******************************************* EXTRAS *******************************************/
    /**
     * Extra <code>java.lang.Number</code> nutrition information in grams per serving.
     */
    public Map<String, Number> getExtras() {
        return this.extras;
    }

    public Byte getByteExtra(String key, Byte defaultValue) {
        return (key != null && extras.containsKey(key))
                ? (Byte) extras.get(key)
                : defaultValue;
    }

    public void putByteExtra(String key, Byte value) {
        if (key != null && key.length() > 0) {
            extras.put(key, value);
        }
    }

    public Integer getIntegerExtra(String key, Integer defaultValue) {
        return (key != null && extras.containsKey(key))
                ? (Integer) extras.get(key)
                : defaultValue;
    }

    public void putIntegerExtra(String key, Integer value) {
        if (key != null && key.length() > 0) {
            extras.put(key, value);
        }
    }

    public Long getLongExtra(String key, Long defaultValue) {
        return (key != null && extras.containsKey(key))
                ? (Long) extras.get(key)
                : defaultValue;
    }

    public void putLongExtra(String key, Long value) {
        if (key != null && key.length() > 0) {
            extras.put(key, value);
        }
    }

    public Double getDoubleExtra(String key, Double defaultValue) {
        return (key != null && extras.containsKey(key))
                ? (Double) extras.get(key)
                : defaultValue;
    }

    public void putDoubleExtra(String key, Double value) {
        if (key != null && key.length() > 0) {
            extras.put(key, value);
        }
    }
}
