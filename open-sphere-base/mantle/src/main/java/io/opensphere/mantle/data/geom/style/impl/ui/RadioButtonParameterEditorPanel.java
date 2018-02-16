package io.opensphere.mantle.data.geom.style.impl.ui;

import java.awt.BorderLayout;
import java.util.Collection;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import io.opensphere.core.util.collections.New;
import io.opensphere.core.util.swing.GridBagPanel;
import io.opensphere.mantle.data.geom.style.MutableVisualizationStyle;

/**
 * A radio button AbstractStyleParameterEditorPanel.
 */
public class RadioButtonParameterEditorPanel extends AbstractStyleParameterEditorPanel
{
    /** The serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The radio buttons. */
    private List<JRadioButton> myRadioButtons = New.list();

    /**
     * Constructor.
     *
     * @param label the {@link PanelBuilder}
     * @param style the style
     * @param paramKey the param key
     * @param options the options
     */
    public RadioButtonParameterEditorPanel(PanelBuilder label, MutableVisualizationStyle style, String paramKey,
            Collection<? extends String> options)
    {
        super(label, style, paramKey);

        GridBagPanel panel = new GridBagPanel();
        ButtonGroup buttonGroup = new ButtonGroup();
        for (String option : options)
        {
            JRadioButton button = new JRadioButton(option);
            myRadioButtons.add(button);
            buttonGroup.add(button);
            panel.add(button);
        }
        panel.fillHorizontalSpace();

        myControlPanel.setLayout(new BorderLayout());
        myControlPanel.add(panel, BorderLayout.CENTER);

        update();
    }

    @Override
    public final void update()
    {
        Object value = getParamValue();
        if (value != null)
        {
            String stringValue = value.toString();
            myRadioButtons.stream().filter(b -> b.getText().equals(stringValue)).forEach(b -> b.setSelected(true));
        }
    }
}
