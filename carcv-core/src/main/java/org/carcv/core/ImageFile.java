/**
 * 
 */
package org.carcv.core;

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

/**
 * @author oskopek
 * 
 */
public class ImageFile implements AutoCloseable { //TODO finish

    private BufferedImage bufImage;

    private Path filepath;

    public ImageFile(Path filepath) {
        this.filepath = filepath;
    }

    public void loadImage() throws IOException {
        InputStream inStream = Files.newInputStream(filepath, StandardOpenOption.READ);

        ImageInputStream imageStream = ImageIO.createImageInputStream(inStream);
        ImageReader reader = ImageIO.getImageReaders(imageStream).next();
        ImageReadParam param = reader.getDefaultReadParam();

        reader.setInput(imageStream, true, true);

        this.bufImage = reader.read(0, param);

        reader.dispose();
        imageStream.close();
        inStream.close();
    }

    public void loadFragment(InputStream stream, Rectangle rect) throws IOException {
        ImageInputStream imageStream = ImageIO.createImageInputStream(stream);
        ImageReader reader = ImageIO.getImageReaders(imageStream).next();
        ImageReadParam param = reader.getDefaultReadParam();

        param.setSourceRegion(rect);
        reader.setInput(imageStream, true, true);

        this.bufImage = reader.read(0, param);

        reader.dispose();
        imageStream.close();
    }

    @Override
    public void close() throws Exception {
        bufImage.flush();
    }

}
