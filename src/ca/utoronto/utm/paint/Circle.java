package ca.utoronto.utm.paint;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.*;
/**
 * creates a new circle
 * IS-A:Shape
 * HAS-A:-radius:int
 * RESPONDS-TO:+getCentre():Point position,+setCentre(Point centre):void
 * 			   +getRadius():int Radius, +setRadius(int radius):void
 * 			   +draw(Graphics2d g2d):void
 */

public class Circle extends Shape{
	//keeps track of the radius of the circle 
	private int radius;
	
	
	/**
	 * constructs a new circle 
	 * @param centre
	 * @param radius
	 * @param color
	 * @param fill
	 * @param thickness
	 */
	public Circle(Point centre, int radius, Color color, boolean fill, int thickness){
		super(centre, color, thickness);
		this.radius = radius;
		this.fill = fill;
	}
	
	
	public Point getCentre() {
		return position;
	}
	
	public void setCentre(Point centre) {
		this.position = centre;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	
	/**
	 * draws a circle
	 * @param Graphics2D g2d: uses the graphics2d to draw
	 */
	public void draw(Graphics2D g2d){
		//stores the initial x and initial y 
		int x0=this.position.getX();
		int y0=this.position.getY();
		int radius=this.radius;
		//Let's tell graphics to apply those attributes of the circle 
		g2d.setColor(this.color);
		g2d.setStroke(new BasicStroke(this.thickness));
		if(fill){
			g2d.fillOval(x0, y0, radius, radius);
		}
		else{
			g2d.drawOval(x0, y0, radius, radius);
		}
	}

}
