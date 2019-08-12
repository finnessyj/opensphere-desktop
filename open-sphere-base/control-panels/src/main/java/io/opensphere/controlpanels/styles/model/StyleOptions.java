package io.opensphere.controlpanels.styles.model;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The bulls eye's style options model.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement
public class StyleOptions implements Serializable
{

    /**
     * Serialization id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The color of the bulls eye.
     */
    @XmlJavaTypeAdapter(ColorAdapter.class)
    @XmlAttribute(name = "color")
    public transient SimpleObjectProperty<Color> myColor;

    /**
     * Indicates if the size has been set by a user.
     */
    @XmlAttribute(name = "hasSizeBeenSet")
    private boolean myHasSizeBeenSet;

    /**
     * The id of the icon to display in this style.
     */
    @XmlAttribute(name = "iconId")
    public transient LongProperty myIconId;

    /**
     * The size of the bulls eye.
     */
    @XmlAttribute(name = "size")
    public transient IntegerProperty mySize;

    /**
     * The style of the bulls eye.
     */
    @XmlAttribute(name = "style")
    public transient SimpleObjectProperty<Styles> myStyle;

    /**
     * The available styles for the bulls eye.
     */
    private transient ObservableList<Styles> myStyles;

    public StyleOptions()
    {
        init();
    }

    private void init()
    {
        mySize = new SimpleIntegerProperty(5);
        myColor = new SimpleObjectProperty<Color>(Color.RED);
        myStyle = new SimpleObjectProperty<Styles>(Styles.POINT);
        myIconId = new SimpleLongProperty(1);
    }

    /**
     * override the serializable methods to write custom objects.
     * 
     * @param s the output stream to write to the cache.
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream s) throws IOException
    {
        s.defaultWriteObject();
        s.writeInt(mySize.get());
        s.writeObject(myColor.getValue());
        s.writeObject(myStyle.getValue());
        s.writeLong(myIconId.get());
    }

    /**
     * override the serializable methods to read custom objects.
     * 
     * @param s the input stream from the cache.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException
    {
        s.defaultReadObject();
        mySize = new SimpleIntegerProperty(s.readInt());
        myColor = new SimpleObjectProperty<Color>((Color)s.readObject());
        myStyle = new SimpleObjectProperty<Styles>((Styles)s.readObject());
        myIconId = new SimpleLongProperty(s.readLong());
    }

    /**
     * Copy the contents from another (which was probably edited).
     *
     * @param other the other style options
     */
    public void copyFrom(StyleOptions other)
    {
        setColor(other.getColor());
        setIconId(other.getIconId());
        setSize(other.getSize());
        setStyle(other.getStyle());
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null || getClass() != obj.getClass())
        {
            return false;
        }
        StyleOptions other = (StyleOptions)obj;
        return Objects.equals(myColor, other.myColor) && myIconId == other.myIconId && mySize == other.mySize
                && myStyle == other.myStyle;
    }

    /**
     * Gets the color of the bulls eye.
     *
     * @return the color
     */
    public Color getColor()
    {
        return myColor.get();
    }

    /**
     * Gets the id of the icon for this style.
     *
     * @return the iconId.
     */
    public long getIconId()
    {
        return myIconId.get();
    }

    /**
     * Gets the size of the bulls eye.
     *
     * @return the size
     */
    public int getSize()
    {
        return mySize.get();
    }

    /**
     * Gets the style of the bulls eye.
     *
     * @return the style
     */
    public Styles getStyle()
    {
        return myStyle.get();
    }

    /**
     * Gets the available styles for the bulls eye and initializes the list if
     * it has not yet been initialized. In the latter case, all styles options
     * are included by default.
     *
     * @return the styles
     */
    public ObservableList<Styles> getStyles()
    {
        if (myStyles == null)
        {
            myStyles = FXCollections.observableArrayList(Styles.values());
        }
        return myStyles;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(myColor, Long.valueOf(myIconId.get()), Integer.valueOf(mySize.get()), myStyle);
    }

    /**
     * Indicates if the size has been set by a user.
     *
     * @return True if the user has set the size, false otherwise.
     */
    public boolean hasSizeBeenSet()
    {
        return myHasSizeBeenSet;
    }

    /**
     * Sets the color of the bulls eye.
     *
     * @param color the color to set
     */
    public void setColor(Color color)
    {
        myColor.set(color);
    }

    /**
     * Sets the id of the icon for this style.
     *
     * @param iconId the iconId to set.
     */
    public void setIconId(long iconId)
    {
        myIconId.set(iconId);
    }

    /**
     * Sets the size of the bulls eye.
     *
     * @param size the size to set
     */
    public void setSize(int size)
    {
        mySize.set(size);
        myHasSizeBeenSet = true;
    }

    /**
     * Sets the style of the bulls eye.
     *
     * @param style the style to set
     */
    public void setStyle(Styles style)
    {
        myStyle.set(style);
    }

    /**
     * Specify a list of Styles options.
     *
     * @param styles the options to be made available
     */
    public void setStyles(List<Styles> styles)
    {
        myStyles = FXCollections.observableList(styles);
    }
}
