

package com.fachati.allUnitConverter.models;


public final class TemperatureUnit extends Unit {


    public TemperatureUnit(int id, int labelResource) {
        // Don't require conversion info, handled in code
        super(id, labelResource, 0.0, 0.0);
    }
}
