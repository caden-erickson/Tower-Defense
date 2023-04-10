package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A <code>Path</code> object represents a path across the view windows,
 * specified by a set of coordinates.
 * 
 * Objects of this class will store each coordinate the path follows, and will
 * have functionality to draw the path, find its length or the length of any
 * segment, or to find the a coordinate position given a percentage that has
 * been traversed.
 * 
 * @author Caden Erickson
 * @version December 06, 2021
 */
public class Path
{
	// Fields
	private List<Point> coords;

	/**
	 * The Path constructor does the following:
	 * - It reads a number of coordinates, n, from the scanner.
	 * - It creates new array(s) (or an ArrayList) to hold the
	 * 		path coordinates, and stores it in the field in 'this' object.
	 * - It loops n times, each time scanning a coordinate x,y pair,
	 * 		creating an object to represent the coordinate (if needed),
	 * 		and stores the coordinate in the array(s) or ArrayList.
	 * 
	 * @param s a Scanner set up by the caller to provide a list of coordinates
	 */
	public Path(Scanner readIn)
	{
		coords = new ArrayList<Point>();

		// Get the number of coordinates
		int numCoords = readIn.nextInt();
		
		// Read each coordinate pair and create a new Point object in the array for each
		for (int i = 0; i < numCoords; i++)
		{
			coords.add(new Point(readIn.nextInt(), readIn.nextInt()));
		}
	}

	/**
	 * Draws the current path to the screen (to wherever the graphics object points)
	 * using a highly-visible particleColor.
	 * 
	 * @param g the <code>Graphics</code> context in which to paint
	 */
	public void draw(Graphics g)
	{
		Graphics2D gr = (Graphics2D)g;
		gr.setColor(Color.RED);
		gr.setStroke(new BasicStroke(3));
		for (int i = 0; i < coords.size() - 1; i++)
		{
			gr.drawLine(coords.get(i).x, coords.get(i).y, coords.get(i+1).x, coords.get(i+1).y);
		}
	}

	/**
	 * Returns the total length of the path. Since the path is specified using
	 * screen coordinates, the length is in pixel units (by default).
	 * 
	 * @return the length of the path
	 */
	public double getPathLength()
	{
		double pathLength = 0.0;

		//For each pair of Points in the array,
		// use the Pythagorean theorem to calculate the
		// distance between them, in pixels
		for (int i = 0; i < coords.size() - 1; i++)
		{
			pathLength += coords.get(i).distance(coords.get(i+1));
		}

		return pathLength;
	}

	/**
	 * Given a percentage between 0% and 100%, this method calculates the location
	 * along the path that is exactly this percentage along the path. The location
	 * is returned in a Point object (integer x and y), and the location is a screen
	 * coordinate.
	 * 
	 * If the percentage is less than 0%, the starting position is returned. If the
	 * percentage is greater than 100%, the final position is returned.
	 * 
	 * If students don't want to use Point objects, they may write or use another
	 * object to represent coordinates.
	 *
	 * Caution: Students should never directly return a Point object from a path
	 * list. It could be changed by the outside caller. Instead, always create and
	 * return new point objects as the result from this method.
	 * 
	 * @param percentTraveled a distance along the path
	 * @return the screen coordinate of this position along the path
	 */
	public Point getPathPosition(double percentTraveled)
	{
		Point position; 											// the coordinate we'll end up returning
		double lengthTraveled = percentTraveled * getPathLength(); 	// the length we've been given as a percentage
		double lengthChecked = 0.0; 								// how far along the path we've checked
		double segmentLength = 0.0; 								// length of the segment we're working with
		double segmentPortionLength, segmentPortionPercent; 		// length and percentage for the traveled portion of that segment
		int posBefore = 0; 											// index for the coordinate before the point we're finding
		double tempX, tempY; 										// variables for calculating the x and y of our point

		// If the percentage isn't between 0.0 and 1.0, return the beginning or end
		if (percentTraveled < 0.0)
		{
			percentTraveled = 0.0;
		}
		else if (percentTraveled > 1.0)
		{
			percentTraveled = 1.0;
		}

		// Find which segment we're in
		//  In other words- between which two indices of the
		//  coords arraylist are we at the moment?
		for (int pos = 0; pos < coords.size() - 1; pos++)
		{
			// Add on the next segment
			segmentLength = coords.get(pos).distance(coords.get(pos+1));
			lengthChecked += segmentLength;

			// If we've passed the point we're looking for
			if (lengthChecked > lengthTraveled)
			{
				posBefore = pos;
				break;
			}
		}

		// Find how far we are along that segment
		segmentPortionLength = lengthTraveled - (lengthChecked - segmentLength);
		segmentPortionPercent = segmentPortionLength / segmentLength;

		// Find the x and y positions
		tempX = (1 - segmentPortionPercent) * (coords.get(posBefore).x)
				+ (segmentPortionPercent)   * (coords.get(posBefore + 1).x);
		tempY = (1 - segmentPortionPercent) * (coords.get(posBefore).y)
				+ (segmentPortionPercent)   * (coords.get(posBefore + 1).y);

		// Create our found point with those coordinate values
		position = new Point((int) tempX, (int) tempY);

		return position;
	}
	
	/**
	 * Returns the distance between a specified point,
	 * and the closest coordinate node to it on the path.
	 * 
	 * @param p the point to which to compare
	 * @return the distance to the closest node
	 */
	public double nearestNodeDistance(Point p)
	{
		double shortestDistance = coords.get(0).distance(p);
		
		for (Point node : coords)
		{
			double distance = p.distance(node);
			if (distance < shortestDistance)
			{
				shortestDistance = distance;
			}
		}
		
		return shortestDistance;
	}
}
