package ca.utoronto.utm.paint;

import javax.swing.*;  
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
/**
 *Creates a PaintPanel 
 *IS-A: JPanel
 *HAS-A: -PaintModel model,-View view,-String mode,-Color color,-boolean fill
 *       -int thickness, -ArrayList<Point> points, -Factory factory
 *RESPONDS-TO:+PaintComponent(Graphics g):void,+mouseReleased(MouseEvent e):void
 *			  +mousePressed(MouseEvent e):void,+mouseDragged(MouseEvent e):void
 *			  +mouseEntered(MouseEvent e):void,+mouseExited(MouseEvent e):void
 *            +mouseClicked(MouseEvent e):void,+mouseMoved(MouseEvent e):void
 *			  +setThickness(int thick):void,+getFill():boolean fill
 *			  +setFill(boolean i):void,+setColorMode(Color color):void
 *			  +setMode(String mode):void,update(Observable o, Object arg):void
 *			  +paintComponent(Graphics g):void
 */

// https://docs.oracle.com/javase/8/docs/api/java/awt/Graphics2D.html
// https://docs.oracle.com/javase/tutorial/2d/

class PaintPanel extends JPanel implements Observer, MouseMotionListener, MouseListener  {
	
	private PaintModel model; // slight departure from MVC, because of the way painting works
	private View view; // So we can talk to our parent or other components of the view

	private String mode; // modifies how we interpret input (could be better?)
	private Color color; //keeps track of color 
	private boolean fill; //keeps track of fill
	private int thickness; //keeps track of thickness

	private ArrayList<Point> points=new ArrayList<Point>();//keeps track of points during a mouse drag, used to build Squiggle shapes
	private Factory factory = new Factory();//creates Shapes
	
	private int x0, y0, xf, yf;//keeps track of initial and final position
	
	/**
	 * constructs a new paintPanel
	 * @param model
	 * @param view
	 */
	public PaintPanel(PaintModel model, View view){
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(300,300));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		this.mode="Circle"; // default shape
		this.color= new Color(0, 0, 0); // default color
		this.fill=false; // default fill
		this.thickness=1; // default thickness
		
		this.model = model;
		this.model.addObserver(this);
		
		this.view=view;
	}

	/**
	 *  shows the actual painting
	 *  @param Graphics g: graphics component
	 */
	public void paintComponent(Graphics g) {
		// Use g to draw on the JPanel, lookup java.awt.Graphics in
		// the javadoc to see more of what this can do for you!!
		
        super.paintComponent(g); //paint background
        Graphics2D g2d = (Graphics2D) g; // lets use the advanced api


		// Draw Shapes
		ArrayList<Shape> shapes = this.model.getShapes();
		for(Shape s: this.model.getShapes()){
			s.draw(g2d);
		}
		
		g2d.dispose();
	}

	/**
	 * repaints everything every time we add a shape to the paintModel's list
	 */
	public void update(Observable o, Object arg) {
		// Not exactly how MVC works, but similar.
		this.repaint(); // Schedule a call to paintComponent
	}
	
	public void setMode(String mode){
		this.mode=mode;
	}
	
	public void setColorMode(Color color){
		this.color=color;
	}
	
	public void setFill(boolean i){
		this.fill=i;
	}
	
	public boolean getFill(){
		return this.fill;
	}
	
	public void setThickness(int thick) {
		this.thickness=thick;
	}
	

	public void mouseMoved(MouseEvent e) {
		//""
	}


	/**
	 * 	-provides the points for the squiggle
	 *  -cause the feedback of the shapes 
	 */
	public void mouseDragged(MouseEvent e) {
		this.xf=e.getX();
		this.yf=e.getY();
		String lr = "left";
		if (e.getButton()==3){
			lr = "right";
		}
		//gets the x and y coordinates of the mouse position during a mouse drag
		points.add(new Point(e.getX(), e.getY()));
		//adds these points to this.points (which will only be used if the shape is a Squiggle)
		Shape shape = this.factory.construct(this.mode, this.x0, this.y0, this.xf, this.yf,this.points, this.color, this.fill, this.thickness, this.model, "drag", lr);
		//deletes last shape in model.Shapes, builds the new shape and then adds it to model.Shapes
		this.model.getShapes().remove(this.model.getShapes().size()-1);
		this.model.addShape(shape);
		}

	
	public void mouseClicked(MouseEvent e) {
		//
	}

	/**
	 * Makes a new shape
	 */
	public void mousePressed(MouseEvent e) {
		//stores the x, y coords for the start of a mouse drag
		this.x0 = e.getX();
		this.y0 = e.getY();
		String lr = "left";
		if (e.getButton()==3){
			lr = "right";
		}
		Shape shape = this.factory.construct(this.mode, this.x0, this.y0, this.x0, this.y0,this.points, this.color, this.fill, this.thickness, this.model, "press", lr);
		//builds shape and then adds to model
		this.model.addShape(shape);
		
	}

	/**
	 * clears all the lists which are keeping track of shapes
	 */
	public void mouseReleased(MouseEvent e) {
		this.points = new ArrayList<Point>();
		view.getUndoneShapes().clear();
		//resets this.points so that next Squiggle shape is completely unrelated to other points of past Squiggle shapes
	}


	public void mouseEntered(MouseEvent e) {
		//
	}


	public void mouseExited(MouseEvent e) {
		//
	}

}


