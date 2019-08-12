package io.opensphere.controlpanels.component.image;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

/**
 * Shows an image scaled to the size of the component but scaled so that the
 * aspect ratio is zoomed in.
 */
public class ScaledImageComponent extends Component
{
    /**
     * Serialization id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The model used by the component.
     */
    private final ScaledImageModel myModel = new ScaledImageModel();

    /**
     * Constructs a new scaled image component.
     */
    public ScaledImageComponent()
    {
        setPreferredSize(new Dimension(600, 300));
    }

    @Override
    public void paint(Graphics graphics)
    {
        myModel.setWidthAndHeight(getWidth(), getHeight());
        if (myModel.getImage() != null)
        {
            graphics.drawImage(myModel.getImage(), myModel.getX(), myModel.getY(), myModel.getImageWidth(),
                    myModel.getImageHeight(), null);
        }
    }

    /**
     * Sets the image to display.
     *
     * @param image The image to display.
     */
    public void setImage(Image image)
    {
        myModel.setImage(image);
        repaint();
    }
}
