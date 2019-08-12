package io.opensphere.controlpanels.component.image;

import java.awt.Image;
import java.beans.PropertyChangeSupport;

/**
 * The model used by the ScaledImagePanel.
 */
public class ScaledImageModel
{
    /**
     * The image property.
     */
    public static final String IMAGE_PROP = "image";

    /**
     * The bean for change notifications.
     */
    private PropertyChangeSupport mySupport = new PropertyChangeSupport(this);

    public PropertyChangeSupport getChangeSupport()
    {
        return mySupport;
    }

    /**
     * The width property.
     */
    public static final String WIDTH_PROP = "width";

    /**
     * The height property.
     */
    public static final String HEIGHT_PROP = "height";

    /**
     * The height of the component.
     */
    private int myHeight;

    /**
     * The original image being displayed.
     */
    private Image myImage;

    /**
     * The height to draw the image.
     */
    private int myImageHeight;

    /**
     * The width to draw the image.
     */
    private int myImageWidth;

    /**
     * The width of the component.
     */
    private int myWidth;

    /**
     * The x coordinate to draw the upper left corner of the image.
     */
    private int myX;

    /**
     * The y coordinate to draw the upper left corner of the image.
     */
    private int myY;

    /**
     * Gets the height of the component.
     *
     * @return The height of the component.
     */
    public int getHeight()
    {
        return myHeight;
    }

    /**
     * Gets the original image being displayed.
     *
     * @return The original image being displayed.
     */
    public Image getImage()
    {
        return myImage;
    }

    /**
     * Gets the height to draw the image.
     *
     * @return The height to draw the image.
     */
    public int getImageHeight()
    {
        return myImageHeight;
    }

    /**
     * Gets the width to draw the image.
     *
     * @return The width to draw the image.
     */
    public int getImageWidth()
    {
        return myImageWidth;
    }

    /**
     * Gets the width of the component.
     *
     * @return The width of the component.
     */
    public int getWidth()
    {
        return myWidth;
    }

    /**
     * Gets the x coordinate to draw the upper left corner of the image.
     *
     * @return The x coordinate to draw the upper left corner of the image.
     */
    public int getX()
    {
        return myX;
    }

    /**
     * Gets the y coordinate to draw the upper left corner of the image.
     *
     * @return The y coordinate to draw the upper left corner of the image.
     */
    public int getY()
    {
        return myY;
    }

    /**
     * Sets the original image being displayed.
     *
     * @param image The original image being displayed.
     */
    public void setImage(Image image)
    {
        mySupport.firePropertyChange(IMAGE_PROP, myImage, image);
        myImage = image;
    }

    /**
     * Sets the height to draw the image.
     *
     * @param imageHeight The height to draw the image.
     */
    public void setImageHeight(int imageHeight)
    {
        myImageHeight = imageHeight;
    }

    /**
     * Sets the width to draw the image.
     *
     * @param imageWidth The width to draw the image.
     */
    public void setImageWidth(int imageWidth)
    {
        myImageWidth = imageWidth;
    }

    /**
     * Sets the width and height of the component.
     *
     * @param width The width of the component.
     * @param height The height of the component.
     */
    public void setWidthAndHeight(int width, int height)
    {
        if (width != myWidth || height != myHeight)
        {
            mySupport.firePropertyChange(WIDTH_PROP, myWidth, width);
            mySupport.firePropertyChange(HEIGHT_PROP, myHeight, height);
            myWidth = width;
            myHeight = height;
        }
    }

    /**
     * Sets the x coordinate to draw the upper left corner of the image.
     *
     * @param x The x coordinate to draw the upper left corner of the image.
     */
    public void setX(int x)
    {
        myX = x;
    }

    /**
     * Sets the y coordinate to draw the upper left corner of the image.
     *
     * @param y The y coordinate to draw the upper left corner of the image.
     */
    public void setY(int y)
    {
        myY = y;
    }
}
