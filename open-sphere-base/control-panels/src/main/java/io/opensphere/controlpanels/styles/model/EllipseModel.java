package io.opensphere.controlpanels.styles.model;

import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The model to describe the ellipse style bullseye.
 */
public class EllipseModel implements Serializable
{

    /**
     * Defaults serialization id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The available units for semi major and minor lengths.
     */
    private transient ObservableList<String> myAvailableUnits;

    /**
     * Indicates if the ellipse style is enabled or not.
     */
    private transient BooleanProperty myEllipseEnabled;

    /**
     * The ellipse orientation.
     */
    public transient DoubleProperty myOrientation;

    /**
     * The bean for change notifications.
     */
    private PropertyChangeSupport mySupport = new PropertyChangeSupport(this);

    /**
     * The semi major length.
     */
    public transient DoubleProperty mySemiMajor;

    /**
     * The semi major length units.
     */
    public transient StringProperty mySemiMajorUnits;

    /**
     * The semi minor length.
     */
    public transient DoubleProperty mySemiMinor;

    /**
     * The semi minor length units.
     */
    public transient StringProperty mySemiMinorUnits;

    /**
     * Creates the model.
     */
    public EllipseModel()
    {
        initInstance();
    }

    /**
     * Gets the available units for semi major and minor lengths and initializes
     * it if it has not been done already.
     *
     * @return the availableUnits
     */
    public ObservableList<String> getAvailableUnits()
    {
        if (myAvailableUnits == null)
        {
            myAvailableUnits = FXCollections.observableArrayList();
        }
        return myAvailableUnits;
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
        s.writeDouble(myOrientation.get());
        s.writeDouble(mySemiMajor.get());
        s.writeDouble(mySemiMinor.get());
        s.writeUTF(mySemiMajorUnits.get());
        s.writeUTF(mySemiMinorUnits.get());
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
        myOrientation = new SimpleDoubleProperty(s.readDouble());
        mySemiMajor = new SimpleDoubleProperty(s.readDouble());
        mySemiMinor = new SimpleDoubleProperty(s.readDouble());
        mySemiMajorUnits = new SimpleStringProperty(s.readUTF());
        mySemiMinorUnits = new SimpleStringProperty(s.readUTF());
    }

    /**
     * Sets up default values for our fields.
     */
    private void initInstance()
    {
        myOrientation = new SimpleDoubleProperty(1);
        mySemiMajor = new SimpleDoubleProperty(1);
        mySemiMajorUnits = new SimpleStringProperty("kilometers");
        mySemiMinor = new SimpleDoubleProperty(1);
        mySemiMinorUnits = new SimpleStringProperty("kilometers");
    }

    /**
     * Indicates if the ellipse is enabled and initializes the property if it
     * has not been initialize yet.
     *
     * @return the ellipseEnabled
     */
    public BooleanProperty getEllipseEnabled()
    {
        if (myEllipseEnabled == null)
        {
            myEllipseEnabled = new SimpleBooleanProperty();
        }
        return myEllipseEnabled;
    }

    /**
     * Gets the ellipse orientation.
     *
     * @return the orientation
     */
    public double getOrientation()
    {
        return myOrientation.getValue().doubleValue();
    }

    /**
     * Gets the semi major length.
     *
     * @return the semiMajor
     */
    public double getSemiMajor()
    {
        return mySemiMajor.getValue().doubleValue();
    }

    /**
     * Gets the semi major length units.
     *
     * @return the semiMajorUnits
     */
    public String getSemiMajorUnits()
    {
        return mySemiMajorUnits.getValue().toString();
    }

    /**
     * Gets the semi minor length.
     *
     * @return the semiMinor
     */
    public double getSemiMinor()
    {
        return mySemiMinor.getValue().doubleValue();
    }

    /**
     * Gets the semi minor length units.
     *
     * @return the semiMinorUnits
     */
    public String getSemiMinorUnits()
    {
        return mySemiMinorUnits.getValue().toString();
    }

    /**
     * Sets the ellipse orientation.
     *
     * @param orientation the orientation to set
     */
    public void setOrientation(double orientation)
    {
        mySupport.firePropertyChange(myOrientation.getName(), this.myOrientation, orientation);
        myOrientation.set(orientation);
    }

    /**
     * Sets the semi major length.
     *
     * @param semiMajor the semiMajor to set
     */
    public void setSemiMajor(double semiMajor)
    {
        mySupport.firePropertyChange(mySemiMajor.getName(), this.mySemiMajor.getValue(), semiMajor);
        mySemiMajor.set(semiMajor);
    }

    /**
     * Sets the semi major length units.
     *
     * @param semiMajorUnits the semiMajorUnits to set
     */
    public void setSemiMajorUnits(String semiMajorUnits)
    {
        mySupport.firePropertyChange(mySemiMajorUnits.getName(), this.mySemiMajorUnits.getValue(), semiMajorUnits);
        mySemiMajorUnits.set(semiMajorUnits);
    }

    /**
     * Sets the semi minor length.
     *
     * @param semiMinor the semiMinor to set
     */
    public void setSemiMinor(double semiMinor)
    {
        mySupport.firePropertyChange(mySemiMinor.getName(), this.mySemiMinor, semiMinor);
        mySemiMinor.set(semiMinor);
    }

    /**
     * Sets the semi minor length units.
     *
     * @param semiMinorUnits the semiMinorUnits to set
     */
    public void setSemiMinorUnits(String semiMinorUnits)
    {
        mySupport.firePropertyChange(mySemiMinorUnits.getName(), this.mySemiMinorUnits, semiMinorUnits);
        mySemiMinorUnits.set(semiMinorUnits);
    }
}
