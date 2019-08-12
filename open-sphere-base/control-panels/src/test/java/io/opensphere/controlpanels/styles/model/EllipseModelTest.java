package io.opensphere.controlpanels.styles.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Observer;

import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.junit.Test;

/**
 * Unit test for {@link EllipseModel}.
 */
public class EllipseModelTest
{
    /**
     * Tests serializing the {@link EllipseModel}.
     *
     * @throws IOException Bad IO.
     * @throws ClassNotFoundException Bad Class.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException
    {
        EllipseModel model = new EllipseModel();

        model.setOrientation(10);
        model.setSemiMajor(11);
        model.setSemiMajorUnits("nm");
        model.setSemiMinor(12);
        model.setSemiMinorUnits("nm");
        model.getAvailableUnits().addAll("nm", "mi");
        model.getEllipseEnabled().set(true);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream objectOut = new ObjectOutputStream(out);
        objectOut.writeObject(model);

        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        ObjectInputStream objectIn = new ObjectInputStream(in);
        EllipseModel actual = (EllipseModel)objectIn.readObject();

        assertEquals(10, actual.getOrientation(), 0d);
        assertEquals(11, actual.getSemiMajor(), 0d);
        assertEquals("nm", actual.getSemiMajorUnits());
        assertEquals(12, actual.getSemiMinor(), 0d);
        assertEquals("nm", actual.getSemiMinorUnits());
        assertTrue(actual.getAvailableUnits().isEmpty());
        assertFalse(actual.getEllipseEnabled().get());
    }

    /**
     * Tests updating and events.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testUpdate()
    {
        EasyMockSupport support = new EasyMockSupport();

        ChangeListener<? super Boolean> enabledListener = support.createMock(ChangeListener.class);
        enabledListener.changed(EasyMock.isA(BooleanProperty.class), EasyMock.eq(Boolean.FALSE), EasyMock.eq(Boolean.TRUE));

        ListChangeListener<? super String> listListener = support.createMock(ListChangeListener.class);
        listListener.onChanged((javafx.collections.ListChangeListener.Change<String>)EasyMock.notNull());

        support.replayAll();

        EllipseModel model = new EllipseModel();
        model.getEllipseEnabled().addListener(enabledListener);
        model.getEllipseEnabled().set(true);

        model.getAvailableUnits().addListener(listListener);
        model.getAvailableUnits().addAll("nmi", "mi");

        model.setOrientation(10);

        model.setSemiMajor(11);

        model.setSemiMinor(10);

        model.setSemiMajorUnits("mi");

        model.setSemiMinorUnits("mi");

        support.verifyAll();
    }
}
