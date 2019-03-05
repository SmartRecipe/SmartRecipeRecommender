package Beans;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ryan D Smith
 */
public class NutritionInfo {

    private float servingsPerContainer;
    // we're doing it in grams for the sake of consistency
    private float servingSize;

    // the following values are in g / serving
    private float calories, totalFat, saturatedFat, transFat;
    private float carbs, fiber, sugar, addedSugar, protein;

    // the following values are in mg / serving
    private float cholesterol, sodium;

    // for vitamins and all that useless nonsense
    private Map<String, Number> extras;

    public NutritionInfo(float servingsPerContainer,
                         float servingSize,
                         float calories,
                         float totalFat,
                         float saturatedFat,
                         float transFat,
                         float carbs,
                         float fiber,
                         float sugar,
                         float addedSugar,
                         float protein,
                         float cholesterol,
                         float sodium) {

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

    public float getServingsPerContainer() {
        return servingsPerContainer;
    }

    public void setServingsPerContainer(float servingsPerContainer) {
        this.servingsPerContainer = servingsPerContainer;
    }

    /**
     * @return The serving size in grams
     */
    public float getServingSize() {
        return servingSize;
    }

    /**
     * @param servingSize The serving size in grams
     */
    public void setServingSize(float servingSize) {
        this.servingSize = servingSize;
    }

    /**
     * @return The calorie information in grams per serving
     */
    public float getCalories() {
        return calories;
    }

    /**
     * @param calories The calorie information in grams per serving
     */
    public void setCalories(float calories) {
        this.calories = calories;
    }

    /**
     * @return The total fat content in grams per serving
     */
    public float getTotalFat() {
        return totalFat;
    }

    /**
     * @param totalFat The total fat content in grams per serving
     */
    public void setTotalFat(float totalFat) {
        this.totalFat = totalFat;
    }

    /**
     * @return The saturated fat content in grams per serving
     */
    public float getSaturatedFat() {
        return saturatedFat;
    }

    /**
     * @param saturatedFat The saturated fat content in grams per serving
     */
    public void setSaturatedFat(float saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    /**
     * @return The trans fat content in grams per serving
     */
    public float getTransFat() {
        return transFat;
    }

    /**
     * @param transFat The trans fat content in grams per serving
     */
    public void setTransFat(float transFat) {
        this.transFat = transFat;
    }

    /**
     * @return The total carbohydrates in grams per serving
     */
    public float getCarbs() {
        return carbs;
    }

    /**
     * @param carbs The total carbohydrates in grams per serving
     */
    public void setCarbs(float carbs) {
        this.carbs = carbs;
    }

    /**
     * @return The <emph>total</emph> fiber content in grams per serving
     */
    public float getFiber() {
        return fiber;
    }

    /**
     * @param fiber The <emph>total</emph> fiber content in grams per serving
     */
    public void setFiber(float fiber) {
        this.fiber = fiber;
    }

    /**
     * @return The sugar content in grams per serving
     */
    public float getSugar() {
        return sugar;
    }

    /**
     * @param sugar The sugar content in grams per serving
     */
    public void setSugar(float sugar) {
        this.sugar = sugar;
    }

    /**
     * @return The added sugars in grams per serving
     */
    public float getAddedSugar() {
        return addedSugar;
    }

    /**
     * @param addedSugar The added sugars in grams per serving
     */
    public void setAddedSugar(float addedSugar) {
        this.addedSugar = addedSugar;
    }

    /**
     * @return The protein content in grams per serving
     */
    public float getProtein() {
        return protein;
    }

    /**
     * @param protein The protein content in grams per serving
     */
    public void setProtein(float protein) {
        this.protein = protein;
    }

    /**
     * @return The cholesterol content in milligrams per serving
     */
    public float getCholesterol() {
        return cholesterol;
    }

    /**
     * @param cholesterol The cholesterol content in milligrams per serving
     */
    public void setCholesterol(float cholesterol) {
        this.cholesterol = cholesterol;
    }

    /**
     * @return The sodium content in milligrams per serving
     */
    public float getSodium() {
        return sodium;
    }

    /**
     * @param sodium The sodium content in milligrams per serving
     */
    public void setSodium(float sodium) {
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

    public Float getFloatExtra(String key, Float defaultValue) {
        return (key != null && extras.containsKey(key))
                ? (Float) extras.get(key)
                : defaultValue;
    }

    public void putFloatExtra(String key, Float value) {
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
