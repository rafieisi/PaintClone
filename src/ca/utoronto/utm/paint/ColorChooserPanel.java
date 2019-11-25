package ca.utoronto.utm.paint;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Creates a ColorChooserPanel
 * IS-A:JPanel
 * HAS-A:-view:View
 * RESPONDS-TO:+actionPerformed():void
 */
class ColorChooserPanel extends JPanel implements ActionListener, ChangeListener {
private View view; // So we can talk to our parent or other components of the view
private ColorChooserPanel2 colorChooserPanel2;
	
	public ColorChooserPanel(View view) {	
		this.view=view;
		ButtonGroup group = new ButtonGroup();
		
		Color[] buttonLabels = {new Color(0, 0, 0), new Color(255, 0, 0), 
				new Color(255,255,40), new Color(0, 0, 255), new Color(255, 255, 255), new Color(255,135,40),
				new Color(0, 255, 0), new Color(180, 0, 255)}; // Some base colors
		String[] buttonLabels2 = {"black", "red", "yellow", "blue", "white", "orange", "green", "purple"};
		this.setLayout(new GridLayout(3, 6));
		for (int i=0; i<buttonLabels.length; i++) {
			Color label = buttonLabels[i];
			String label2 = buttonLabels2[i];
			JToggleButton button = new JToggleButton();
			ImageIcon imageIcon = new ImageIcon(new ImageIcon("Images/"+label2+"Icon.png").
					getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT)); // Retrieves and scales icons
			button.setIcon(imageIcon); // Adds icon to button
			button.setName(label2); // Identifier for the button
			button.setForeground(label);
			button.setOpaque(true);
			group.add(button);
			this.add(button);
			button.addActionListener(this);
			if (label == buttonLabels[0]){
				button.setSelected(true);//Black is the default color, so the button should be selected when the program starts
			}
		}
		JToggleButton aselector = new JToggleButton("More Colors");
		aselector.setIcon(new ImageIcon(new ImageIcon("Images/colorWheeIcon.png").
					getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT)));
		this.view=view;
	    aselector.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        	view.setCCPVisibility(!view.getCCPVisibility());
	        }
	    });
	    this.add(aselector);
	    
		//Making fill button
		JToggleButton fillButton = new JToggleButton();
	    ImageIcon imageIcon1 = new ImageIcon(new ImageIcon("Images/FilledCircleIcon.png").
				getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT)); // Retrieves and scales icons
		ImageIcon imageIcon2 = new ImageIcon(new ImageIcon("Images/CircleIcon.png").
				getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT)); // Retrieves and scales icons
		fillButton.addActionListener(this);
		fillButton.setText("Fill");
		fillButton.setIcon(imageIcon1);
		fillButton.setDisabledIcon(imageIcon1);
		fillButton.setPressedIcon(imageIcon2);
		fillButton.setSelectedIcon(imageIcon2);
		fillButton.setDisabledSelectedIcon(imageIcon1);
		this.add(fillButton);

		JLabel label = new JLabel("Thickness:", JLabel.CENTER);
		this.add(label);
		
		//Making thickness slider
		JSlider thickness = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
		thickness.setMajorTickSpacing(9);
		thickness.setMinorTickSpacing(1);
		thickness.setPaintTicks(true);
		thickness.setPaintLabels(true);
		thickness.addChangeListener(this);
		this.add(thickness);
	}
	
	/**
	 * Controller aspect of this
	 * Sets this.view's paintPanel mode to the specified userInput
	 * @param e component initiated
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "Fill"){
			this.view.getPaintPanel().setFill(!this.view.getPaintPanel().getFill());
		}
		else if((((JComponent) e.getSource()).getForeground()) != null){
			this.view.getPaintPanel().setColorMode((((JComponent) e.getSource()).getForeground()));
		}
	}

	/**
	 * Controller for thickness slider
	 * Sets this.view's paintPanel mode to the specified userInput
	 * @param e component initiated
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider thickness = (JSlider) e.getSource();
        if (!thickness.getValueIsAdjusting()) {
          int thick = thickness.getValue();
          this.view.getPaintPanel().setThickness(thick);
        }
		
	}
}