package ca.utoronto.utm.paint;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.*;
import java.util.ArrayList;
/**
 * This is the main view of the paint programs and it is responsible to gather 
 * all other view.This is the top level View+Controller, it contains other aspects of the View+Controller.
 *
 * IS-A:JFrame
 * HAS-A:-model:PaintModel ,-UndoneShapes:ArrayList<Shape>,-paintPanel:PaintPanel,
 * 		-shapeChooserPanel:ShapeChooserPanel, -colorChooserPanel:ColorChooserPanel
 * 		-colorChooserPanel2:ColorChooserPanel
 * RESPONDS-TO:+getUndoneShape():ArrayList<Shape> UndoneShapes,+actionPerformed(actionEvent e):void
 *             -createMenuBar():JMenuBar menuBar,+getPaintPanel():PaintPanel paintPanel
 *             +getShapeChooserPanel():ShapeChooserPanel shapeChooserPanel
 *             +getColorChooserPanel():ColorChooserPanel colorChooserPanel
 *             +getColorChooserPanel2():ColorChooserPanel2 colorChoosePanel2
 * 
 * @author arnold
 *
 */
public class View extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private PaintModel model;//model is an instance of the PaintMod and it will keep track of shapes
	
	private ArrayList<Shape> UndoneShapes=new ArrayList<Shape>();//keeps track of undone shapes when the undo is clicked
	// The components that make this up
	private PaintPanel paintPanel;
	private ShapeChooserPanel shapeChooserPanel;//components that make up shape
	private ColorChooserPanel colorChooserPanel;//component that make primary colors and thickness
	private ColorChooserPanel2 colorChooserPanel2;//component that make more colors
	
	/**
	 * constructs the component view
	 * @param model
	 */
	public View(PaintModel model) {
		super("Paint"); // set the title and do other JFrame init
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setJMenuBar(createMenuBar());
		
		Container c=this.getContentPane();
		// c.add(new JButton("North"),BorderLayout.NORTH);
		// c.add(new JButton("South"),BorderLayout.SOUTH);
		// c.add(new JButton("East"),BorderLayout.EAST);
		this.shapeChooserPanel = new ShapeChooserPanel(this);
		c.add(this.shapeChooserPanel,BorderLayout.WEST);
	
		this.model=model;
		//adding the components to the view
		this.paintPanel = new PaintPanel(model, this);
		c.add(this.paintPanel, BorderLayout.CENTER);
		
		this.colorChooserPanel = new ColorChooserPanel(this);
		c.add(this.colorChooserPanel,BorderLayout.SOUTH);
		
		this.colorChooserPanel2 = new ColorChooserPanel2(this);
		this.colorChooserPanel2.setVisible(false);
		c.add(this.colorChooserPanel2,BorderLayout.EAST);
		
		//this.pack();
		this.setSize(750, 500);
		this.setVisible(true);
	}

	public PaintPanel getPaintPanel(){
		return paintPanel;
	}
	
	public ShapeChooserPanel getShapeChooserPanel() {
		return shapeChooserPanel;
	}
	
	public ColorChooserPanel getColorChooserPanel() {
		return colorChooserPanel;
	}
	
	public ColorChooserPanel2 getColorChooserPanel2() {
		return colorChooserPanel2;
	}
	public boolean getCCPVisibility(){
		return colorChooserPanel2.isVisible();
	}
	public void setCCPVisibility(boolean i){
		colorChooserPanel2.setVisible(i);
	}
	/**
	 * creates a menu bar with tabs File and Edit
	 * @return menuBar
	 */
	private JMenuBar createMenuBar() { // Don't worry about it now 
		JMenuBar menuBar = new JMenuBar();
		JMenu menu;
		JMenuItem menuItem;
		//adds the the tabFile
		menu = new JMenu("File");

		// a group of JMenuItems
		menuItem = new JMenuItem("New");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Open");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Save");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menu.addSeparator();// -------------

		menuItem = new JMenuItem("Exit");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuBar.add(menu);

		menu = new JMenu("Edit");

		// a group of JMenuItems
		menuItem = new JMenuItem("Cut");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Copy");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Paste");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Clear");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menu.addSeparator();// -------------

		menuItem = new JMenuItem("Undo");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Redo");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuBar.add(menu);

		return menuBar;
	}
	/**
	 * controller aspect of view
	 * Add action to Undo, Redo, New and clear
	 * @param ActionEvent e
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="Undo"){
				//if shape list is not empty, it removes the last shape and 
				//adds it to undone shapes list
				if(model.getShapes().size()!=0){
					UndoneShapes.add(model.getShapes().get(model.getShapes().size()-1));
					model.removeShape(model.getShapes().size()-1);}}
		
		if(e.getActionCommand()=="Redo"){
				//it does the reversal of undo
				if(UndoneShapes.size()!=0){
					model.addShape(UndoneShapes.get(UndoneShapes.size()-1));
					UndoneShapes.remove(UndoneShapes.size()-1);
				}}
		if(e.getActionCommand()=="New"){
			//makes new paint
			Paint paint2=new Paint();
		}
		if(e.getActionCommand()=="Clear"){
			    //clear everything inside shape list
				model.SetShape(new ArrayList<Shape>());
		}
		if(e.getActionCommand()=="Exit"){
		    // Exit the paint panel
			System.exit(0);
	}

	}
	public ArrayList<Shape> getUndoneShapes(){
		return UndoneShapes;
	}
}
