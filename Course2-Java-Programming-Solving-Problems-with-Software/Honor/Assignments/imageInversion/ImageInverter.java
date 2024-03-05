/**
 * @author Benny Sun
 */
import edu.duke.*;
import java.io.*;

public class ImageInverter
{
    public void selectAndConvert ()
    {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            ImageResource inImage = new ImageResource(f);
            ImageResource outImage = invert(inImage);
            outImage.setFileName("invert-" + inImage.getFileName());
            outImage.save();
        }
    }

    public ImageResource invert(ImageResource inImage)
    {
        ImageResource outImage = new ImageResource(inImage.getWidth(), inImage.getHeight());
        for (Pixel pixel: outImage.pixels()) {
            Pixel inPixel = inImage.getPixel(pixel.getX(), pixel.getY());
            pixel.setRed(255 - inPixel.getRed());
            pixel.setGreen(255 - inPixel.getBlue());
            pixel.setBlue(255 - inPixel.getGreen());
        }

        return outImage;
    }

    public void testInvert()
    {
        ImageResource ir = new ImageResource();
        ImageResource image = invert(ir);
        image.draw();
    }
}
