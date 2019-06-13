package io.opensphere.controlpanels.iconpicker.ui;

import java.net.MalformedURLException;

import io.opensphere.controlpanels.iconpicker.controller.IconChooserDisplayer;
import io.opensphere.controlpanels.iconpicker.controller.IconPickerController;
import io.opensphere.controlpanels.iconpicker.model.IconPickerModel;
import io.opensphere.core.Toolbox;
import io.opensphere.core.util.CrashReporter.SendLogControllerImpl;
import javafx.beans.property.LongProperty;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * A javafx button that allows the user to select system icons, and will display
 * the selected icon on the button.
 */
public class IconPickerButton extends Button
{
    /**
     * The controller.
     */
    private final IconPickerController myController;

    /**
     * The image view that displays the selected icon.
     */
    private final ImageView myImageView = new ImageView();

    /**
     * The model.
     */
    private final IconPickerModel myModel;

    private SendLogControllerImpl mySender;

    /**
     * Constructs a new icon picker button.
     *
     * @param toolbox The system toolbox.
     * @param iconIdProperty The property to set with an icon id, when the user
     *            selects one.
     * @throws MalformedURLException
     */
    public IconPickerButton(Toolbox toolbox, LongProperty iconIdProperty)
    {
        this(toolbox, iconIdProperty, new IconChooserDisplayerImpl(toolbox.getUIRegistry().getMainFrameProvider()));
    }

    /**
     * Constructs a new icon picker button.
     *
     * @param toolbox The system toolbox.
     * @param iconIdProperty The property to set with an icon id, when the user
     *            selects one.
     * @param displayer The class that knows how to show the Icon picking
     *            dialog.
     * @throws MalformedURLException
     */
    protected IconPickerButton(Toolbox toolbox, LongProperty iconIdProperty, IconChooserDisplayer displayer)
    {

        myModel = new IconPickerModel(iconIdProperty);
        myController = new IconPickerController(toolbox, displayer, myModel);
        mySender = new SendLogControllerImpl(toolbox);

        myImageView.setFitHeight(16);
        myImageView.setFitWidth(16);
        setGraphic(myImageView);
        myImageView.imageProperty().bindBidirectional(myModel.imageProperty());
        setOnAction((e) ->
        {
            myController.showPicker();
            mySender.ConnectToServer();
            // mySender.postBug();
            // mySender.uploadfiles();
            // mySender.uploadfiles();
        });
    }
}
