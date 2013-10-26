/**
 * 
 */
package org.carcv.core.input;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.carcv.core.model.Address;
import org.carcv.core.model.CarData;
import org.carcv.core.model.file.FileCarImage;
import org.carcv.core.model.file.FileEntry;

/**
 * @author oskopek
 *
 */
public class DirectoryLoader {
    
    final public static String[] knownImageFileSuffixes = {".png", ".jpg"};
    final public static String infoFileName = "info.properties";
    
    public static FileEntry load(Path directory) throws IOException {
        DirectoryStream<Path> contents = Files.newDirectoryStream(directory);
        
        List<FileCarImage> images = new ArrayList<>();
        
        for(Path p : contents) {
            if(Files.exists(p) && Files.isRegularFile(p) && isKnownImage(p)) {
                images.add(new FileCarImage(p));
            }
        }
        
        CarData cd = loadCarData(directory);
        
        return new FileEntry(cd, images);
    }
    
    private static CarData loadCarData(Path directory) throws IOException {
        Properties p = new Properties();
        
        Path info = Paths.get(directory.toString(), infoFileName);
        
        p.load(new FileInputStream(info.toFile()));
        
        
        String latitude = p.getProperty("address-lat");
        String longitude = p.getProperty("address-long");
        String city = p.getProperty("address-city");
        String postalcode = p.getProperty("address-postalCode");
        String street = p.getProperty("address-street");
        String country = p.getProperty("address-country");
        String streetNumber = p.getProperty("address-streetNo");
        String referenceNumber = p.getProperty("address-refNo");
        
        String time = p.getProperty("timestamp");
                
        
        Address address = new Address(Double.parseDouble(latitude), Double.parseDouble(longitude), city, postalcode, street, country, Integer.parseInt(streetNumber), Integer.parseInt(referenceNumber)); 
             
        Date timestamp = null;
        try {
            timestamp = SimpleDateFormat.getDateInstance().parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            System.err.println("Failed to parse timestamp from " + info.toString());
        }
        
        CarData cd = new CarData(null, address, null, timestamp);
        
        return cd;
    }
    
    private static boolean isKnownImage(Path p) {
        for(String suffix : knownImageFileSuffixes) {
            if(p.endsWith(suffix)) {
                return true;
            }
        }
        return false;
    }
    
    
    

}
