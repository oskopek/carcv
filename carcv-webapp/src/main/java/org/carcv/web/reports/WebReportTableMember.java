/*
 * Copyright 2013-2014 CarCV Development Team
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
package org.carcv.web.reports;

import java.text.DateFormat;

/**
 * A class used for passing data to <code>cartable.jsp</code>.
 */
public class WebReportTableMember {

    private final String previewPath, entryId, time, date, location, licensePlate, timeZone;

    /**
     * Basic setter constructor.
     *
     * @param previewPath the path to the preview image
     * @param entryId id of the entry displayed
     * @param time the time of the entry, formatted
     * @param date the date of the entry, formatted
     * @param location the location of the entry, formatted with &lt;BR&gt; tags
     * @param licensePlate the recognized license plate text
     * @param timeZone TimeZone in any format {@link DateFormat#setTimeZone(java.util.TimeZone)} can handle
     */
    public WebReportTableMember(String previewPath, String entryId, String time, String date, String location,
            String licensePlate, String timeZone) {
        this.previewPath = previewPath;
        this.entryId = entryId;
        this.time = time;
        this.date = date;
        this.location = location;
        this.licensePlate = licensePlate;
        this.timeZone = timeZone;
    }

    public String getPreviewPath() {
        return previewPath;
    }

    public String getEntryId() {
        return entryId;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getTimeZone() {
        return timeZone;
    }
}
