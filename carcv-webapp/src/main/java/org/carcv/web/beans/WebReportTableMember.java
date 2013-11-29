/*
 * Copyright 2013 Ondrej Skopek
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
package org.carcv.web.beans;

/**
 *
 */
public class WebReportTableMember {

    final private String previewURL, entryId, time, date, location, licensePlate;

    /**
     * @param previewURL
     * @param entryId
     * @param time
     * @param date
     * @param location
     * @param licensePlate
     */
    public WebReportTableMember(String previewURL, String entryId, String time, String date, String location,
        String licensePlate) {
        this.previewURL = previewURL;
        this.entryId = entryId;
        this.time = time;
        this.date = date;
        this.location = location;
        this.licensePlate = licensePlate;
    }

    /**
     * @return the previewURL
     */
    public String getPreviewURL() {
        return previewURL;
    }

    /**
     * @return the entryId
     */
    public String getEntryId() {
        return entryId;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @return the licensePlate
     */
    public String getLicensePlate() {
        return licensePlate;
    }
}