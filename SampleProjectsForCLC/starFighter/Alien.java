//© A+ Computer Science  -  www.apluscompsci.com
//Name -
//Date -
//Class -
//Lab  -

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class Alien extends MovingThing
{
	private int speed;
	private Image image;
	private boolean isalive; //
	private int moveback; //
	private int xlimit; //

	public Alien()
	{
		this(0,0,0);
	}

	public Alien(int x, int y)
	{
		this(x,y,0);
	}

	public Alien(int x, int y, int s)
	{
		super(x, y);
		speed=s;
		isalive = true;
		moveback = 0;
		try
		{
			image = ImageIO.read(new File("alien.jpg"));
			image = ImageIO.read(new File("alien.png"));
		}
		catch(Exception e)
		{
			//feel free to do something here
		}

		//super.setThread("Alien");  //
		//super.getThread().start(); //

	}

	public void setSpeed(int s)
	{
	   //add code
	   speed = s;
	}

	public int getSpeed()
	{
	   return speed;
	}

	public int getY()
	{
		return super.getY();
	}

	public void setY(int y)
	{
		super.setY(y);
	}

	public void draw( Graphics window )
	{
   	   window.drawImage(image,getX(),getY(),80,80,null);
	   //System.out.println(super.getThread()); //
   	   try //
   	   {
   	   	 //super.getThread().sleep(100);
   	   }

   	   catch (Exception e)
   	   {

   	   }

	}

	public boolean isdead()//
	{
		return !isalive;
	}

	public void kill() //
	{
		isalive = false;
	}

	public void movebackmore () //
	{
		moveback++;
	}

	public void scootback (int x) //
	{
		moveback -= x;
	}

	public int getresistance() //
	{
		if (moveback<0)
			moveback = 0;

		return moveback;
	}



	public String toString()
	{
		return " speed: " + speed;
	}
}
