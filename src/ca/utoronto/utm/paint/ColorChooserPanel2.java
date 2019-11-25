package ca.utoronto.utm.paint;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Creates a ShapeChooserPanel
 * IS-A:JPanel
 * HAS-A:-view:View
 * RESPONDS-TO:+actionPerformed():void
 * https://docs.oracle.com/javase/tutorial/uiswing/components/colorchooser.html
 * @author markospajic
 */

public class ColorChooserPanel2 extends JPanel implements ChangeListener{
	private View view;

	private static final long serialVersionUID = 1L;
	JColorChooser colorchooser = new JColorChooser();
	
	/**
	 * Creates a colorchooser
	 * @param view
	 */
	public ColorChooserPanel2(View view) {
		this.view=view;
        this.setLayout(new BorderLayout());
        colorchooser.setPreviewPanel(new JPanel());
        colorchooser.getSelectionModel().addChangeListener(this);
        add(colorchooser, BorderLayout.PAGE_END);
    }
	
	/**
	 * Controller aspect of this
	 * Sets this.view's paintPanel mode to the specified userInput
	 * @param e component initiated
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		Color color = colorchooser.getColor();
		this.view.getPaintPanel().setColorMode(color);
	}
}
