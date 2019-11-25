package ca.utoronto.utm.paint;

import java.awt.Color;
import java.util.ArrayList;

/**
 * class Factory
 * 
 * constructs Shapes
 * IS-A:None
 * HAS-A:None 
 * RESPONDS-TO:+getPosition():Point, +setPosition(Point position):void
 * 			   +getColor():String, +set
 */
public class Factory {
	/**
	 * 	Construct and return an instance of a subclass of Shape (Circle, Square, Rectangle, Squiggle, or Polyline)
	 * @param type the type of shape to be constructed
	 * @param x0 int; the x coordinate of the Point where the mouse was pressed. Must be positive
	 * @param y0 int; the y coordinate of the Point where the mouse was pressed. Must be positive
	 * @param xf int; the x coordinate of the Point where the mouse was dragged to. Must be positive
	 * @param yf int; the y coordinate of the Point where the mouse was dragged to. Must be positive
	 * @param points ArrayList<Point>; an ArrayList of all the Points that were drawn (only used to create Squiggle shapes)
	 * @param color Color; the color the shape being constructed should be
	 * @param fill boolean; true if the shape being constructed should be filled, false if it should be just the outline
	 * @param thickness int; the thickness of the shape (thickness of the border for closed shapes
	 * @param model PaintModel; The model containing all the shapes that have been drawn
	 * @param mouse_event String; "drag" if the factory is called from a mouseDragged method
	 * @param lr String; "left" or "right" depending on which mouse button was clicked. Right click starts new Polyline.
	 * @return a Shape instance
	 */
	public Shape construct(String type, int x0, int y0, int xf, int yf,ArrayList<Point> points, Color color, boolean fill, int thickness, PaintModel model, String mouse_event, String lr){
		if (type == "Circle"){
			
			int radius = (int)Math.sqrt((xf-x0)*(xf-x0) + (yf-y0)*(yf-y0))*2;
			// the way g2d.draw oval works, it uses the beginning and end of a mouse drag as the diameter
			//of the circle. Since we want that to be the radius.

			return new Circle(new Point(x0-radius/2, y0-radius/2),radius, color, fill, thickness);
			//x0 and y0 are the center coordinates of the circle. Due to the way g2d draws the 
			//circle, this point needs to be shifted to the left by radius/2 and up by radius/2
			
		}else if(type=="Squiggle"){
			return new Squiggle("Squiggle",points, color, thickness);
		} 
		else if(type=="Rectangle") {

			int width = Math.abs(xf-x0);
			int length = Math.abs(yf-y0);

			int x = Math.min(x0, xf);
			int y = Math.min(y0, yf);
			
			//g2d draws rectangles using the Point that is passed in as the top left corner. Therefore the x and
			//y coordinates of the point must be the smallest  x and y values (out of x0 and xf, and y0 and yf), since
			//(0,0) is the top left corner of the JPanel (PaintPanel)
			
			return new Rectangle(length, width, new Point(x,y), color, fill, thickness);
		}
	
		else if(type=="Square") {

			int width = Math.abs(xf-x0);
			
			//Like rectangle, square must be drawn using the top left corner of the square
			//since  the size of the square only depends on width (x direction), the x coord
			//of this point is the smallest (furthest left) of x0 and xf
			int x = Math.min(x0, xf);
			
			
			//however, depending on how the user drags the mouse, the height of the square will not depend
			//on y0 (it only depends on the width). Therefore, there are two cases for finding the y coord
			// of the top left point:
			// 1) if y0 is smaller (higher up) than yf, then y0 is the top coordinate.
			// 2) if yf is smaller (higher up) than y0, then the y coordinate of the top left point is
			//    y0 minus the height of the square. Since the height is the same as the width, this is given
			//	  by y = y0 - |xf-x0|
			int y;
			if(y0<yf){
				y=y0;
			}
			else{
				y = Math.min(y0, y0 - Math.abs(xf-x0));
			}
			
			return new Square(width, new Point(x,y), color, fill, thickness);
		}
		
		else if (type=="Polyline"){
			Polyline poly = new Polyline(new Point(x0,y0), new Point(xf,yf), color, thickness);//creates a default polyline to be modified later
			
			ArrayList<Shape> shapes = model.getShapes();//gets the list of shapes from the model
			//this is needed because polyline should only connect to the previous polyline if it is the most recent shape
			//ie. if a polyline is drawn, then a circle is drawn, the next polyline should not connect to the old one
			
			
			if (shapes.size() > 0 && shapes.get(model.getShapes().size()-1).getClass().isInstance(poly)){//if last shape drawn was Polyline
				
				Polyline prev_poly = (Polyline)model.getShapes().get(model.getShapes().size()-1);//gets previous shape if it is a Polyline
				
				if (mouse_event == "drag"){
					poly.setPosition(new Point(prev_poly.getPosition().getX(), prev_poly.getPosition().getY()));
					
				}//If the mouse is doing a drag, then the feedback should show from the point where the mouse was clicked
					//since this is where the polyline segment starts from
				
				
				else{	//basically if mouse_event == "press"
					poly.setPosition(new Point(prev_poly.getLastPoint().getX(), prev_poly.getLastPoint().getY()));
					//on a new mouse press, the next segment of the polyline is being drawn, so the line needs to start from
					//the endpoint of the previous segment
				}
				
				if (lr=="right"){
					poly.setPosition(new Point(x0,y0));
				}
						
			}
			return poly;
		}
		
		else{
			return null;}
	}
}
