import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import static java.lang.Character.*;

public class Menu extends Canvas implements MouseListener, MouseMotionListener
{
	Canvas canvas;
	public Menu (Canvas c)
	{
		canvas = c;
	}

	public void openMenu()
	{
		canvas.addMouseListener(this);
	}
	public void closeMenu()
	{
		canvas.removeMouseListener(this);
	}

	public void drawMenu (Graphics g)
	{
		g.setColor(Color.red);
		g.drawRect(0,0,100,100);
	}

	public void drawgamescreen (Graphics g)
	{
		g.setColor(Color.red);
		g.drawLine(600,0,600,600);
	}

	public void mouseExited (MouseEvent evt)
	{

	}

	public void mouseEntered (MouseEvent evt)
	{

	}

	public void mousePressed (MouseEvent evt)
	{

	}

	public void mouseClicked (MouseEvent evt)
	{

	}

	public void mouseReleased (MouseEvent evt)
	{

	}

	public void mouseMoved (MouseEvent evt)
	{

	}

	public void mouseDragged (MouseEvent evt)
	{

	}
}