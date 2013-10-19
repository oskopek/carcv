/**
 * 
 */
package org.carcv.impl.core.model.file;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.persistence.Entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.carcv.core.model.AbstractCarImage;

/**
 * Default behavior is to not load an image from path. To load it, use {@link FileCarImage#loadImage()}
 * @author oskopek
 * 
 */
@Entity
public class FileCarImage extends AbstractCarImage  {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private BufferedImage image;

    private Path filepath;

    public FileCarImage(Path filepath) {
        this.filepath = filepath;
    }

    public void loadImage() throws IOException {
        InputStream inStream = Files.newInputStream(filepath, StandardOpenOption.READ);
        
        /*//TODO fix loading of image
        ImageInputStream imageStream = ImageIO.createImageInputStream(inStream);
        ImageReader reader = ImageIO.getImageReaders(imageStream).next();
        ImageReadParam param = reader.getDefaultReadParam();

        reader.setInput(imageStream, true, true);

        this.image = reader.read(0, param);

        reader.dispose();
        imageStream.close();
        */
        
        //temp:
        BufferedImage image = ImageIO.read(inStream);

        if(image == null) {
            throw new NullPointerException("Failed to load image " + filepath);
        }
        
        BufferedImage outimage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics2D g = outimage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        this.image = outimage;
        
        inStream.close();
    }

    public void loadFragment(Rectangle rect) throws IOException {
        InputStream inStream = Files.newInputStream(filepath, StandardOpenOption.READ);
        
        ImageInputStream imageStream = ImageIO.createImageInputStream(inStream);
        ImageReader reader = ImageIO.getImageReaders(imageStream).next();
        ImageReadParam param = reader.getDefaultReadParam();

        param.setSourceRegion(rect);
        reader.setInput(imageStream, true, true);

        this.image = reader.read(0, param);

        reader.dispose();
        imageStream.close();
    }

    @Override
    public void close() {
        if(image!=null) {
            image.flush();
        }
    }

    /**
     * @return the image
     */
    @Override
    public BufferedImage getImage() {
        return image;
    }

    /**
     * @return the filepath
     */
    public Path getFilepath() {
        return filepath;
    }

    /**
     * @param filepath the filepath to set
     */
    public void setFilepath(Path filepath) {
        this.filepath = filepath;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof FileCarImage) {
            FileCarImage f = (FileCarImage) o;
            
            return new EqualsBuilder().append(image, f.image).append(filepath, f.filepath).isEquals();
        }        
        return false;
        
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(image).append(filepath).toHashCode();
    }

}
