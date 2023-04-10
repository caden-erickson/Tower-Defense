package game;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A GameView object represents the graphical user interface to our game. It
 * contains the code for building the JFrame (the game window) and setting up
 * the JPanels and menus. It also listens for events and sends them to the
 * GameControl object when they arrive.
 * 
 * There is exactly one GameView object for the entire game. (It's purpose is to
 * handle drawing and events.)
 * 
 * In checkpoint #1, functionality that belonged in GameState and GameControl
 * was placed in GameView for convenience. We'll separate most of this out in
 * checkpoint #2.
 * 
 * @author Caden Erickson
 * @version December 01, 2021
 */
public class GameView extends JPanel implements MouseListener, MouseMotionListener
{
	// This constant is needed to get rid of a warning. It won't matter to us.
	private static final long serialVersionUID = 1L;

	// Fields -- These variables will be part of the GameView object (that we make
	// in GameControl).
	private GameState state;

	/**
	 * Our GameView constructor. The 'view' is the GUI (Graphical User Interface)
	 * and this constructor builds a JFrame (window) so the user can see our
	 * 'drawing'.
	 */
	public GameView(GameState state)
	{
		this.state = state;

		// Build the frame. The frame object represents the application 'window'.
		JFrame frame = new JFrame("Tower Defense 2021");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add a drawing area to the frame (a panel). Note that 'this' object IS the
		// panel that we need, so we add it.
		frame.setContentPane(this);

		// Set the size of 'this' panel to match the size of the backdrop.
		Dimension d = new Dimension(855, 600);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setMaximumSize(d);

		// Allow the JFrame to layout the window (by 'packing' it) and make it visible.
		frame.pack();
		frame.setVisible(true);
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	/**
	 * Draws our game. This method will be called automatically when Java needs to
	 * repaint our window. Use the repaint() method call (on this object) to cause
	 * this method to be executed.
	 * 
	 * @param g the <code>Graphics</code> context in which to paint
	 */
	public void paint(Graphics g)
	{
		// Draw everything
		state.drawAll(g, this);
	}
	
	/**
	 * Draws a specified image, with the specified dimensions, centered at the
	 * specified Point.
	 * 
	 * @param g        the <code>Graphics</code> context in which to paint
	 * @param startButtonFile the image file to draw
	 * @param p        the <code>Point</code> at which to center the image
	 * @param width    the width at which to draw the image
	 * @param height   the height at which to draw the image
	 */
	public void drawCenteredImage(Graphics g, String filename, Point p, int width, int height)
	{
		g.drawImage(ResourceLoader.getLoader().getImage(filename), p.x-width/2, p.y-height/2, width, height, null);
	}	
	
	/**
	 * Draws a specified image, with the specified dimensions, centered at the
	 * specified Point. This is an overload of drawCenteredImage, used for drawing
	 * splat effects which need manipulatable opacity.
	 * 
	 * @param g        the <code>Graphics</code> context in which to paint
	 * @param startButtonFile the image file to draw
	 * @param p        the <code>Point</code> at which to center the image
	 * @param width    the width at which to draw the image
	 * @param height   the height at which to draw the image
	 * @param alpha    the opacity at which to draw the image
	 */
	public void drawCenteredImage(Graphics g, String filename, Point p, int width, int height, float alpha)
	{
		// Configure graphics panel to draw image with manipulatable opacity
		Graphics2D g2d = (Graphics2D)g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        
		g2d.drawImage(ResourceLoader.getLoader().getImage(filename), p.x-width/2, p.y-height/2, width, height, null);
		
		// Set opacity back to 1.0 so everything else gets drawn normally
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
	}	
	
	// ------------------- EVENT HANDLERS ------------------- //
	
	public void mousePressed(MouseEvent e)
	{
		state.setMouseClicked();		
		
		// For finding coordinates
//		System.out.println(e.getX() + " " + e.getY());
	}
	public void mouseClicked(MouseEvent e) { }
	public void mouseReleased(MouseEvent e)	{ }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) {	}

	public void mouseDragged(MouseEvent e)
	{
		state.setMousePosition(e.getX(), e.getY());
	}
	public void mouseMoved(MouseEvent e)
	{
		state.setMousePosition(e.getX(), e.getY());
	}
}
