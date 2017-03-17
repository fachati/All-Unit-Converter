/*
 * Copyright 2016 Phil Shadlyn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fachati.allUnitConverter;

import android.app.Application;

import com.fachati.allUnitConverter.util.Conversions;

public class UnitConverterApplication extends Application {

    public static final String BUILD_FLAVOUR_GOOGLE = "google";
    public static final String BUILD_FLAVOUR_BASE = "base";

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize conversions object, and load currency if stored
        Conversions.getInstance().updateCurrencyConversions(this);
    }
}
