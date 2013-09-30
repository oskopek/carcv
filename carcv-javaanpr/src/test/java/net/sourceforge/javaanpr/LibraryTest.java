/**
 * 
 */
package net.sourceforge.javaanpr;

import javaanpr.imageanalysis.CarSnapshot;
import javaanpr.intelligence.Intelligence;

/**
 * @author oskopek
 *
 */
public class LibraryTest {

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        Intelligence intel = new Intelligence(false);
        System.out.println("Loaded");
        String spz = intel.recognize(new CarSnapshot("/home/oskopek/dev/javaanpr/car_random/skoda_oct.jpg"));
        System.out.println(spz);
    }

}
