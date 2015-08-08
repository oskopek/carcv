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

package org.carcv.core.model.file;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.carcv.core.model.AbstractCarImage;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * An implementation of AbstractCarImage using images from the file system.
 * <p/>
 * Default behavior is to not load an image from path along the construction of the object. To load it, use
 * {@link FileCarImage#loadImage()} or any other load method in FileCarImage.
 *
 * @see AbstractCarImage
 */
@Entity
public class FileCarImage extends AbstractCarImage {

    private static final long serialVersionUID = 7110565734692075416L;

    private BufferedImage image;
    private PersistablePath persistablePath;

    /**
     * A default constructor used for persistence.
     */
    @SuppressWarnings("unused")
    private FileCarImage() {
        // intentionally empty
    }

    /**
     * Creates a new instance of FileCarImage of an image on the file system with the given Path. Doesn't load the
     * image, you are responsible for calling {@link #loadImage()} and {@link #close()} on demand.
     *
     * @param filepath the Path of the image in the file system
     */
    public FileCarImage(Path filepath) {
        this.persistablePath = new PersistablePath(filepath);
    }

    /**
     * Loads the image from {@link #getFilepath() filepath} into memory. Calls {@link #loadImage(InputStream)}
     * internally.
     *
     * @throws IOException if an error during loading occurs
     */
    public void loadImage() throws IOException {
        if (Files.exists(getFilepath()) && Files.isRegularFile(getFilepath())) {
            InputStream inStream = Files.newInputStream(persistablePath.getPath());
            loadImage(inStream);
            return;
        }
        throw new IOException("Image at " + getFilepath().toString() + " doesn't exist or is invalid.");
    }

    /**
     * Reads the image in inStream into a BufferedImage. Uses {@link ImageIO}.
     *
     * @param inStream the InputStream from which to load the image
     * @throws IOException if an error during loading occurs
     */
    public void loadImage(InputStream inStream) throws IOException {
        // TODO 3 Fix loading of images
        /*
         * ImageInputStream imageStream = ImageIO.createImageInputStream(inStream); ImageReader reader =
         * ImageIO.getImageReaders(imageStream).next(); ImageReadParam param = reader.getDefaultReadParam();
         *
         * reader.setInput(imageStream, true, true);
         *
         * this.image = reader.read(0, param);
         *
         * reader.dispose(); imageStream.close();
         */

        // temp:
        if (inStream == null) {
            throw new IOException("InputStream to load image is null");
        }

        BufferedImage image = ImageIO.read(inStream);

        if (image == null) {
            throw new IOException("Failed to load image " + persistablePath);
        }

        BufferedImage outimage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics2D g = outimage.createGraphics(); // TODO 3 Is this needed?
        g.drawImage(image, 0, 0, null);
        g.dispose();

        this.image = outimage;
        inStream.close();
    }

    /**
     * Loads a part corresponding to the rectangular region from the image into memory. Calls
     * {@link #loadFragment(InputStream, Rectangle)} internally.
     *
     * @param rect specifies the rectangular region to load as the image
     * @throws IOException if an error during loading occurs
     */
    public void loadFragment(Rectangle rect) throws IOException {
        InputStream inStream = Files.newInputStream(persistablePath.getPath(), StandardOpenOption.READ);
        loadFragment(inStream, rect);
    }

    /**
     * Reads a rectangular region from an image in the inStream.
     *
     * @param inStream the InputStream from which to load the image fraction
     * @param rect specifies the rectangular region to load as the image
     * @throws IOException if an error during loading occurs
     */
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
     * Gets the image instance. Note, this field is transient (not persisted).
     *
     * @return the image
     */
    @Override
    @Transient
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Returns the Path of the FileCarImage. This isn't a traditional getter, it is handled by an internal object, but
     * works exactly like one.
     *
     * @return the Path of the FileCarImage on the current file system
     * @see PersistablePath#getPath()
     */
    @Transient
    public Path getFilepath() {
        return persistablePath.getPath();
    }

    /**
     * Sets the Path of the FileCarImage. This isn't a traditional setter, an internal object handles it, but works
     * exactly like one.
     *
     * @param filepath the Path of the FileCarImage on the current file system
     * @see PersistablePath#setPath(Path)
     */
    public void setFilepath(Path filepath) {
        this.persistablePath = new PersistablePath(filepath);
    }

    /**
     * Is private because the PersistablePath class is used just as a protected internal implementation. Only used for
     * persistence.
     *
     * @return the persistablePath
     */
    @NotNull
    @Embedded
    private PersistablePath getPersistablePath() {
        return persistablePath;
    }

    /**
     * Is private because the PersistablePath class is used just as a protected internal implementation. Only used for
     * persistence.
     *
     * @param persistablePath the persistablePath to set
     */
    @SuppressWarnings("unused")
    private void setPersistablePath(PersistablePath persistablePath) {
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
