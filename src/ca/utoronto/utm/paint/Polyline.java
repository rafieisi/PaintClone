package ca.utoronto.utm.paint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
/**
 * IS-A: Shape
 * HAS-A: -pf Point, -position Point, -color:Color ,-thickness:int
 * RESPONDS-TO: +getLastPoint():Point, all the methods in the Shape class
 * 
 * Polyline is really a Polyline segment, and the factory (which will later use Strategy design
 * pattern constructs each segment into a single polyline.
 */
public class Polyline extends Shape{
	private Point pf;//the final point of the polyline segment
	
	public Polyline(Point p0, Point pf, Color color, int thickness) {
		super(p0, color, thickness);
		this.pf = pf;
	}
	/**
	 * Draws a segment of a polyline with g2d
	 * Due to how the Polyline is constructed by the factory, right click starts a new polyline
	 * (won't be joined to the previous segments)
	 * @param: g2d a Graphics2D object
	 */
	public void draw(Graphics2D g2d){
		g2d.setColor(this.color);
		g2d.setStroke(new BasicStroke(this.thickness));

		g2d.drawLine(position.getX(), position.getY(), pf.getX(), pf.getY());
		
	}

	public Point getLastPoint(){
		return this.pf;
	}

}
