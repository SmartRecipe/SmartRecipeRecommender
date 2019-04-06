package Beans;

import java.lang.reflect.Method;

import javax.measure.Unit;
import javax.measure.quantity.Volume;

import systems.uom.common.USCustomary;

public enum VolumeUnits {
    LITER(USCustomary.LITER),
    GALLON(USCustomary.GALLON_LIQUID),
    PINT(USCustomary.PINT),
    FL_OZ(USCustomary.FLUID_OUNCE),
    CUP(USCustomary.CUP),
    TABLESPOON(USCustomary.TABLESPOON),
    TEASPOON(USCustomary.TEASPOON);

    Unit<Volume> unitRepresentation;

    VolumeUnits(Unit<Volume> unitRepresentation) {
        this.unitRepresentation = unitRepresentation;
    }

    public Unit<Volume> getUnitRepresentation() {
        return unitRepresentation;
    }
    
    public static Unit<Volume> fromString(String unit) {
        if (unit == null) {
            return null;
        }
        unit = unit.toLowerCase();
        //Strip plural off the end
        if (unit.endsWith("s")) unit = unit.substring(0, unit.length()-1);
        for (VolumeUnits testUnit : VolumeUnits.values()) {
            String name = testUnit.getUnitRepresentation().getName();
            String symbol = testUnit.getUnitRepresentation().getSymbol();
            if (name != null && name.toLowerCase().contains(unit)) {
                return testUnit.getUnitRepresentation();
            } else if (symbol != null && symbol.toLowerCase().contains(unit)) {
                return testUnit.getUnitRepresentation();
            }
        }
        // Other general catches that the above may miss
        if (unit.contains("cup")) {
            return CUP.getUnitRepresentation();
        } else if (unit.contains("oz") || unit.contains("ounce")) {
            return FL_OZ.getUnitRepresentation();
        } else if (unit.contains("gallon")) {
            return GALLON.getUnitRepresentation();
        }
        return null;
    }
    
    public static String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"VolumeUnits\":[");
        for (VolumeUnits unit : VolumeUnits.values()) {
            sb.append("\"");
            sb.append(unit.getUnitRepresentation().getName());
            sb.append("\", ");
        }
        //Remove last comma and space
        sb.setLength(sb.length() - 2);
        sb.append("]}");
        return sb.toString();
    }
}