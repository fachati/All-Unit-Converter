

package com.fachati.allUnitConverter.presenters;

import com.fachati.allUnitConverter.activity.Preferences;
import com.fachati.allUnitConverter.R;
import com.fachati.allUnitConverter.api.FixerApi;
import com.fachati.allUnitConverter.api.models.CurrencyResponse;
import com.fachati.allUnitConverter.models.Conversion;
import com.fachati.allUnitConverter.models.Unit;
import com.fachati.allUnitConverter.util.Conversions;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConversionPresenter {

    private Set<Call<CurrencyResponse>> mCalls;
    private ConversionView mView;

    /**
     * Create a new ConversionPresenter
     *
     * @param mView ConversionView callback
     */
    public ConversionPresenter(ConversionView mView) {
        this.mView = mView;
        this.mCalls = new HashSet<>();
    }

    public void onDestroy() {
        // Cancel any running currency updates
        for (Call<CurrencyResponse> call : mCalls) {
            if (call != null && call.isExecuted() && !call.isCanceled()) {
                call.cancel();
            }
        }

        mCalls.clear();
    }

    public void onUpdateCurrencyConversions() {
        final Call<CurrencyResponse> call = FixerApi.getInstance()
                .getService()
                .getLatestRates();

        mCalls.add(call);
        call.enqueue(new Callback<CurrencyResponse>() {
            @Override
            public void onResponse(Call<CurrencyResponse> call, Response<CurrencyResponse> response) {
                boolean hadCurrency = Conversions.getInstance().hasCurrency();
                Preferences.getInstance(mView.getContext()).saveLatestCurrency(response.body());
                Conversions.getInstance().updateCurrencyConversions(mView.getContext());
                Conversions.getInstance().setCurrencyUpdated(true);
                mView.showToast(R.string.toast_currency_updated);
                if (hadCurrency) {
                    mView.updateCurrencyConversion();
                }
                else {
                    mView.showUnitsList(Conversions.getInstance().getById(Conversion.CURRENCY));
                }
            }

            @Override
            public void onFailure(Call<CurrencyResponse> call, Throwable t) {
                if (!Conversions.getInstance().hasCurrency()) {
                    mView.showLoadingError(R.string.error_loading_currency);
                }
                else {
                    mView.showToastError(R.string.toast_error_updating_currency);
                }
            }
        });
    }

    public void onGetUnitsToDisplay(@Conversion.id int conversionId) {
        switch (conversionId) {
            case Conversion.CURRENCY:
                if (Conversions.getInstance().hasCurrency()) {
                    mView.showUnitsList(Conversions.getInstance().getById(conversionId));
                }
                else {
                    mView.showProgressCircle();
                }

                // Only update the conversions the first time the user views them per session
                if (!Conversions.getInstance().isCurrencyUpdated()) {
                    onUpdateCurrencyConversions();
                }
                break;

            default:
                mView.showUnitsList(Conversions.getInstance().getById(conversionId));
                break;
        }
    }

    /**
     * Convert a temperature value from one unit to another
     *
     * @param value the value to convert
     * @param from  the unit to be converted from
     * @param to    the unit to be converted to
     */
    public void convertTemperatureValue(double value, Unit from, Unit to) {
        double result = value;
        if (from.getId() != to.getId()) {
            switch (to.getId()) {
                case (Unit.CELSIUS):
                    result = toCelsius(from.getId(), value);
                    break;

                case (Unit.FAHRENHEIT):
                    result = toFahrenheit(from.getId(), value);
                    break;

                case (Unit.KELVIN):
                    result = toKelvin(from.getId(), value);
                    break;

                case (Unit.RANKINE):
                    result = toRankine(from.getId(), value);
                    break;

                case (Unit.DELISLE):
                    result = toDelisle(from.getId(), value);
                    break;

                case (Unit.NEWTON):
                    result = toNewton(from.getId(), value);
                    break;

                case (Unit.REAUMUR):
                    result = toReaumur(from.getId(), value);
                    break;

                case (Unit.ROMER):
                    result = toRomer(from.getId(), value);
                    break;

                case (Unit.GAS_MARK):
                    result = toGasMark(from.getId(), value);
                    break;
            }
        }

        mView.showResult(result);
    }

    /**
     * Convert a Fuel Consumption value from one unit to another
     *
     * @param value the value to convert
     * @param from  the unit to be converted from
     * @param to    the unit to be converted to
     */
    public void convertFuelValue(double value, Unit from, Unit to) {
        double result = value;
        if (from.getId() != to.getId() && value != 0) {
            if (from.getId() == Unit.L_100K)   // Litres/100km
            {
                BigDecimal toBase = new BigDecimal(from.getConversionToBaseUnit());
                BigDecimal fromBase = new BigDecimal(to.getConversionFromBaseUnit());
                BigDecimal resultBd = toBase.divide(new BigDecimal(value), BigDecimal.ROUND_UP).multiply(fromBase);
                result = resultBd.doubleValue();
            }
            else if (to.getId() == Unit.L_100K)   // Litres/100km
            {
                BigDecimal fromBase = new BigDecimal(to.getConversionFromBaseUnit());
                BigDecimal toBase = new BigDecimal(from.getConversionToBaseUnit());
                BigDecimal resultBd = fromBase.divide(new BigDecimal(value).multiply(toBase), BigDecimal.ROUND_UP);
                result = resultBd.doubleValue();
            }
            else {
                BigDecimal multiplier = new BigDecimal(from.getConversionToBaseUnit()).multiply(new BigDecimal(to.getConversionFromBaseUnit()));
                BigDecimal bdResult = new BigDecimal(value).multiply(multiplier);
                result = bdResult.doubleValue();
            }
        }

        mView.showResult(result);
    }

    /**
     * Convert a value from one unit to another
     *
     * @param value the value to convert
     * @param from  the unit to be converted from
     * @param to    the unit to be converted to
     */
    public void convert(double value, Unit from, Unit to) {
        double result = value;
        if (from.getId() != to.getId()) {
            // use BigDecimal to eliminate multiplication rounding errors
            BigDecimal multiplier = new BigDecimal(from.getConversionToBaseUnit()).multiply(new BigDecimal(to.getConversionFromBaseUnit()));
            BigDecimal bdResult = new BigDecimal(value).multiply(multiplier);
            result = bdResult.doubleValue();
        }
        mView.showResult(result);
    }

    private double toCelsius(int fromId, double value) {
        double result = value;

        switch (fromId) {
            case (Unit.FAHRENHEIT):    // F to C
                result = (value - 32) * 5 / 9;
                break;

            case (Unit.KELVIN):    // K to C
                result = value - 273.15;
                break;

            case (Unit.RANKINE):    // R to C
                result = (value - 491.67) * 5 / 9;
                break;

            case (Unit.DELISLE):    // D to C
                result = 100 - value * 2 / 3;
                break;

            case (Unit.NEWTON):    //N to C
                result = value * 100 / 33;
                break;

            case (Unit.REAUMUR):    //Re to C
                result = value * 5 / 4;
                break;

            case (Unit.ROMER):    //Ro to C
                result = (value - 7.5) * 40 / 21;
                break;

            case (Unit.GAS_MARK): //GM to C
                //Convert from GM to F, then from F to C
                result = (fromGasMark(value) - 32) * 5 / 9;
                break;
        }

        return result;
    }

    private double toFahrenheit(int fromId, double value) {
        double result = value;

        switch (fromId) {
            case (Unit.CELSIUS):    // C to F
                result = value * 9 / 5 + 32;
                break;

            case (Unit.KELVIN):    // K to F
                result = value * 9 / 5 - 459.67;
                break;

            case (Unit.RANKINE):    // R to F
                result = value - 459.67;
                break;

            case (Unit.DELISLE):    //D to F
                result = 212 - value * 6 / 5;
                break;

            case (Unit.NEWTON):    //N to F
                result = value * 60 / 11 + 32;
                break;

            case (Unit.REAUMUR):    //Re to F
                result = value * 9 / 4 + 32;
                break;

            case (Unit.ROMER):    //Ro to F
                result = (value - 7.5) * 24 / 7 + 32;
                break;

            case (Unit.GAS_MARK):
                result = fromGasMark(value);
                break;
        }

        return result;
    }

    private double toKelvin(int fromId, double value) {
        double result = value;

        switch (fromId) {
            case (Unit.CELSIUS):    // C to K
                result = value + 273.15;
                break;

            case (Unit.FAHRENHEIT):    // F to K
                result = (value + 459.67) * 5 / 9;
                break;

            case (Unit.RANKINE):    // R to K
                result = value * 5 / 9;
                break;

            case (Unit.DELISLE):    //D to K
                result = 373.15 - value * 2 / 3;
                break;

            case (Unit.NEWTON):    //N to K
                result = value * 100 / 33 + 273.15;
                break;

            case (Unit.REAUMUR):    //Re to K
                result = value * 5 / 4 + 273.15;
                break;

            case (Unit.ROMER):    //Ro to K
                result = (value - 7.5) * 40 / 21 + 273.15;
                break;

            case (Unit.GAS_MARK):
                result = (fromGasMark(value) + 459.67) * 5 / 9;
                break;
        }

        return result;
    }

    private double toRankine(int fromId, double value) {
        double result = value;

        switch (fromId) {
            case (Unit.CELSIUS):    // C to R
                result = (value + 273.15) * 9 / 5;
                break;

            case (Unit.FAHRENHEIT):    // F to R
                result = value + 459.67;
                break;

            case (Unit.KELVIN):    // K to R
                result = value * 9 / 5;
                break;

            case (Unit.DELISLE):    //D to R
                result = 671.67 - value * 6 / 5;
                break;

            case (Unit.NEWTON):    //N to R
                result = value * 60 / 11 + 491.67;
                break;

            case (Unit.REAUMUR):    //Re to R
                result = value * 9 / 4 + 491.67;
                break;

            case (Unit.ROMER):    //Ro to R
                result = (value - 7.5) * 24 / 7 + 491.67;
                break;

            case (Unit.GAS_MARK):
                result = fromGasMark(value) + 459.67;
                break;
        }

        return result;
    }

    private double toDelisle(int fromId, double value) {
        double result = value;

        switch (fromId) {
            case (Unit.CELSIUS):    // C to D
                result = (100 - value) * 1.5;
                break;

            case (Unit.FAHRENHEIT):    // F to D
                result = (212 - value) * 5 / 6;
                break;

            case (Unit.KELVIN):    // K to D
                result = (373.15 - value) * 1.5;
                break;

            case (Unit.RANKINE):    // R to D
                result = (671.67 - value) * 5 / 6;
                break;

            case (Unit.NEWTON):    //N to D
                result = (33 - value) * 50 / 11;
                break;

            case (Unit.REAUMUR):    //Re to D
                result = (80 - value) * 1.875;
                break;

            case (Unit.ROMER):    //Ro to D
                result = (60 - value) * 20 / 7;
                break;

            case (Unit.GAS_MARK):
                result = (212 - fromGasMark(value)) * 5 / 6;
                break;
        }

        return result;
    }

    private double toNewton(int fromId, double value) {
        double result = value;

        switch (fromId) {
            case (Unit.CELSIUS):    // C to N
                result = value * 33 / 100;
                break;

            case (Unit.FAHRENHEIT):    // F to N
                result = (value - 32) * 11 / 60;
                break;

            case (Unit.KELVIN):    // K to N
                result = (value - 273.15) * 33 / 100;
                break;

            case (Unit.RANKINE):    // R to N
                result = (value - 491.67) * 11 / 60;
                break;

            case (Unit.DELISLE):    //D to N
                result = 33 - value * 11 / 50;
                break;

            case (Unit.REAUMUR):    //Re to N
                result = value * 33 / 80;
                break;

            case (Unit.ROMER):    //Ro to N
                result = (value - 7.5) * 22 / 35;
                break;

            case (Unit.GAS_MARK):
                result = (fromGasMark(value) - 32) * 11 / 60;
                break;
        }

        return result;
    }

    private double toReaumur(int fromId, double value) {
        double result = value;

        switch (fromId) {
            case (Unit.CELSIUS):    // C to Re
                result = value * 4 / 5;
                break;

            case (Unit.FAHRENHEIT):    // F to Re
                result = (value - 32) * 4 / 9;
                break;

            case (Unit.KELVIN):    // K to Re
                result = (value - 273.15) * 4 / 5;
                break;

            case (Unit.RANKINE):    // R to Re
                result = (value - 491.67) * 4 / 9;
                break;

            case (Unit.DELISLE):    //D to Re
                result = 80 - value * 8 / 15;
                break;

            case (Unit.NEWTON):    //N to Re
                result = value * 80 / 33;
                break;

            case (Unit.ROMER):    //Ro to Re
                result = (value - 7.5) * 32 / 21;
                break;

            case (Unit.GAS_MARK):
                result = (fromGasMark(value) - 32) * 4 / 9;
                break;
        }

        return result;
    }

    private double toRomer(int fromId, double value) {
        double result = value;

        switch (fromId) {
            case (Unit.CELSIUS):    // C to Ro
                result = value * 21 / 40 + 7.5;
                break;

            case (Unit.FAHRENHEIT):    // F to Ro
                result = (value - 32) * 7 / 24 + 7.5;
                break;

            case (Unit.KELVIN):    // K to Ro
                result = (value - 273.15) * 21 / 40 + 7.5;
                break;

            case (Unit.RANKINE):    // R to Ro
                result = (value - 491.67) * 7 / 24 + 7.5;
                break;

            case (Unit.DELISLE):    //D to Ro
                result = 60 - value * 7 / 20;
                break;

            case (Unit.NEWTON):    //N to Ro
                result = value * 35 / 22 + 7.5;
                break;

            case (Unit.REAUMUR):    //Re to Ro
                result = value * 21 / 32 + 7.5;
                break;

            case (Unit.GAS_MARK):
                result = (fromGasMark(value) - 32) * 7 / 24 + 7.5;
                break;
        }

        return result;
    }

    private double toGasMark(int fromId, double value) {
        //Convert incoming temperature to Fahrenheit, then convert from F to Gas Mark
        double resultF = toFahrenheit(fromId, value);
        double resultGM = 0;

        if (resultF >= 275) {
            resultGM = 0.04 * resultF - 10;
        }
        else if (resultF < 275) {
            resultGM = 0.01 * resultF - 2;
        }

        if (resultGM < 0) resultGM = 0;

        return resultGM;
    }

    private double fromGasMark(double value) {
        double resultF = 0;

        //Convert incoming Gas Mark to Fahrenheit, which will then be subequently converted to desired unit
        if (value >= 1) {
            resultF = 25 * value + 250;
        }
        else if (value < 1) {
            resultF = 100 * value + 200;
        }

        return resultF;
    }
}
