/**
 * 
 */
package org.carcv.core.recognize;

import java.io.IOException;

/**
 * @author oskopek
 * 
 */
public abstract class CarRecognizer {

    /**
     * This method is supposed to read new <code>Entry</code>-s from one or more data source,
     * detect individual cars, detect their speed and number plate,
     * afterwards writing <code>CarData</code> (possibly the <code>List of CarImage-s</code> too)to one or more data sources.
     * <BR>
     * Getting the data afterwards is the responsibility of your client externally from the input/output datasources.
     * 
     * @throws IOException
     */
    public abstract void recognize() throws IOException;

}
