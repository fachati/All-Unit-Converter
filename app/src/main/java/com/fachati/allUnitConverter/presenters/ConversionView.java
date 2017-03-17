
package com.fachati.allUnitConverter.presenters;

import android.content.Context;

import com.fachati.allUnitConverter.models.Conversion;


public interface ConversionView {
    Context getContext();
    void showUnitsList(Conversion conversion);
    void showProgressCircle();
    void showLoadingError(int message);
    void showResult(double result);
    void updateCurrencyConversion();
    void showToast(int message);
    void showToastError(int message);
}
