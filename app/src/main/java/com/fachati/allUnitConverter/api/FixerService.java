
package com.fachati.allUnitConverter.api;

import com.fachati.allUnitConverter.api.models.CurrencyResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FixerService {
    @GET("latest")
    Call<CurrencyResponse> getLatestRates();
}
