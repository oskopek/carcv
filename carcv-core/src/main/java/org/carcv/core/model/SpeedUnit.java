/*
 * Copyright 2012 CarCV Development Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.carcv.core.model;

/**
 * The default unit to use is {@link #KPH}.
 * <p/>
 * All values of <code>SpeedUnit</code>:
 * <ul>
 * <li>MPH - miles per hour
 * <li>KPH - kilometers per hour
 * <li>MS - meters per second
 * <li>KNOTS - nautical knots
 * </ul>
 */
public enum SpeedUnit {
    MPH(1.60934),
    KPH(1),
    MS(3.6),
    KNOTS(1.852),
    FPS(1.09728);

    private double conversionCoef;

    private SpeedUnit(double conversionCoef) {
        this.conversionCoef = conversionCoef;
    }

    /**
     * @return the conversion coefficient to the default unit ({@link #KPH})
     */
    public double getConversionCoef() {
        return conversionCoef;
    }

    /**
     * Converts the speed value to the default unit {@link #KPH}.
     *
     * @param speed the speed to convert
     * @return the converted speed in KPH
     */
    public double convertToDefault(double speed) {
        return convert(this, speed, KPH);
    }

    /**
     * Converts the speed value from the <code>from</code> unit to the <code>to</code> unit.
     *
     * @param from  the unit form which to convert
     * @param speed the value to convert
     * @param to    the unit to convert to
     * @return the converted value in <code>to</code> unit
     */
    public static double convert(SpeedUnit from, double speed, SpeedUnit to) {
        return (from.getConversionCoef() * speed) / to.getConversionCoef();
    }

    /**
     * Converts the value from this unit to the <code>to</code> unit.
     *
     * @param speed the value to convert
     * @param to    the unit to convert to
     * @return the converted value in <code>to</code> unit
     */
    public double convertTo(double speed, SpeedUnit to) {
        return convert(this, speed, to);
    }
}