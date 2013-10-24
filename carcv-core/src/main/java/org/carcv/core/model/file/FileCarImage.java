/**
 * 
 */
package org.carcv.core.model.file;

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
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.carcv.core.model.AbstractCarImage;
import org.carcv.core.model.PersistablePath;

/**
 * Default behavior is to not load an image from path. To load it, use
 * {@link FileCarImage#loadImage()}
 * 
 * @author oskopek
 * 
 */
@Embeddable
public class FileCarImage extends AbstractCarImage { //TODO 2 Add test of PersistablePath in FileCarImage

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private BufferedImage image;

    private PersistablePath persistablePath;

    @SuppressWarnings("unused")
    private FileCarImage() {
        //intentionally empty
    }

    public FileCarImage(Path filepath) {
        this.persistablePath = new PersistablePath(filepath);
    }

    public void loadImage() throws IOException {
        InputStream inStream = Files.newInputStream(persistablePath.getPath(), StandardOpenOption.READ);
        loadImage(inStream);
    }

    public void loadImage(InputStream inStream) throws IOException {
        //TODO 3 Fix loading of image
        /*
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

        if (image == null) {
            throw new NullPointerException("Failed to load image " + persistablePath);
        }

        BufferedImage outimage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics2D g = outimage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        this.image = outimage;

        inStream.close();
    }

    public void loadFragment(Rectangle rect) throws IOException {
        InputStream inStream = Files.newInputStream(persistablePath.getPath(), StandardOpenOption.READ);
        loadFragment(inStream, rect);
    }

    public void loadFragment(InputStream inStream, Rectangle rect) throws IOException {

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
        if (image != null) {
            image.flush();
        }
    }

    /**
     * @return the image
     */
    @Override
    @Transient
    public BufferedImage getImage() {
        return image;
    }

    /**
     * @return the persistablePath
     */
    @Transient
    public Path getFilepath() {
        return persistablePath.getPath();
    }

    public void setFilepath(Path filepath) {
        this.persistablePath = new PersistablePath(filepath);
    }

    /**
     * @return the persistablePath
     */
    @NotNull
    @Embedded
    public PersistablePath getPersistablePath() {
        return persistablePath;
    }

    /**
     * @param persistablePath
     *            the persistablePath to set
     */
    public void setPersistablePath(PersistablePath persistablePath) {
        this.persistablePath = persistablePath;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof FileCarImage) {
            FileCarImage f = (FileCarImage) o;

            return new EqualsBuilder().append(image, f.image).append(persistablePath, f.persistablePath).isEquals();
        }
        return false;

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(image).append(persistablePath).toHashCode();
    }

}
