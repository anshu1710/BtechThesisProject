//© A+ Computer Science  -  www.apluscompsci.com
//Name -
//Date -
//Class -
//Lab  -

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OuterSpace extends Canvas implements KeyListener, Runnable
{
	private Ship ship;
	private Alien alienOne;
	private Alien alienTwo;
	private boolean addclan; //
	private boolean canshoot; //
	private int ammosupply; //
	private double Threadnum; //
	private boolean start; //
	private Menu m; //
	private int hitnum = 0; //
	private int xlimit = 510; //
	private String screen;



	/* uncomment once you are ready for this part
	 */
	private ArrayList<Alien> aliens;
	private ArrayList<Ammo> shots;
	private ArrayList<MovingThing> everything;
	/**/

	private boolean[] keys;
	private BufferedImage back;

	public OuterSpace()
	{
		m = new Menu(this);
		screen = "gamepage";
		setupgamepage();
		setVisible(true);
	}

   public void update(Graphics window)
   {
   	   if (screen.equals("menu"))
   	   {

   	   }

   	   else if (screen.equals("gamepage"))
   	   {
	   	   if (start)
	   	   {
	   	   	   if (aliens.size()>0 && false)
	   	   	   {

		   	   	 	   	int highy = 0;
						int xcord = 0;
						for (Alien a : aliens)
						{
							if (a.getY()>highy && a.getY()<500)
							{
								highy = a.getY();
								xcord = a.getX();
							}
						}

						ship.setX(xcord);
						keys[4] = true;
	   	   	   }


	   	     paint(window);

	   	   }
   	   }


   }

	public void paint( Graphics window )
	{
			if (screen.equals("menu"))
			{

			}

				else if (screen.equals("gamepage"))
				{
				//set up the double buffering to make the game animation nice and smooth
				Graphics2D twoDGraph = (Graphics2D)window;

				//take a snap shop of the current screen and same it as an image
				//that is the exact same width and height as the current screen
				if(back==null)
				   back = (BufferedImage)(createImage(getWidth(),getHeight()));

				//create a graphics reference to the back ground image
				//we will draw all changes on the background image
				Graphics graphToBack = back.createGraphics();


				graphToBack.setColor(Color.BLUE);
				graphToBack.drawString("StarFighter ", 25, 50 );
				graphToBack.setColor(Color.BLACK);
				graphToBack.fillRect(0,0,800,600);
				m.drawgamescreen(graphToBack);

				if(keys[0] == true && ship.getX()>0-20)
				{
					ship.move("LEFT");
				}
				if(keys[1] == true && ship.getX()<800-80)
				{
					ship.move("RIGHT");
				}

				if(keys[2] == true && ship.getY()>0-10)
				{
					ship.move("UP");
				}

				if(keys[3] == true && ship.getY()<600-100)
				{
					ship.move("DOWN");
				}

				if(keys[4] == true)
				{
					shots.add(new Ammo(ship.getX()+40,ship.getY()-80,1)); //orig speed 25
					keys[4] = false;
				}

				//add code to move stuff

				// update ship movement
				ship.draw(graphToBack);

				//update alien movement
				for (Alien a : aliens)
				{
					a.draw(graphToBack);
				}

				//update Ammo movement
				for (Ammo ammo : shots)
				{
					if (ammo.isalive())
					{
					  ammo.draw(graphToBack);
					}

				}

				if (false) //
				{
					int highy = 0;
					int xcord = 0;
					for (Alien a : aliens)
					{
						if (a.getY()>highy && a.getY()<500)
						{
							highy = a.getY();
							xcord = a.getX();
						}
					}

					ship.setX(xcord);
					keys[4] = true;
				}



				if (Threadnum%100==0)
				{
					//if (canshoot)
					keys[4] = true;
					//canshoot = !canshoot;

				}


				if (Threadnum%1==0)
				{
					//if (canshoot)
					{
						for (Ammo ammo : shots)
						{
							if (ammo.isalive())
							ammo.setY(ammo.getY()-10);
						}
					}

					//canshoot = !canshoot;

				}

				if (Threadnum%((random(1,6)*300))==0) //every 2-5 second add a new wave of aliens
				{
					addmorealiens();
				}

				if (Threadnum%10==0)    //if (Threadnum%100==0)
				{
					for (Alien alien : aliens)
					{

						if (alien.getresistance()!=0)
						{
							alien.scootback(2);
							alien.setY(alien.getY()-2);
						}
						else
						{
							alien.setY(alien.getY() + (random(0,2)*1));

							int randx = (random(0,3));
							int dir = random (1,2);

							if (dir==1)
							{
								if (alien.getX()-randx>0)
								{
									alien.setX(alien.getX() - randx);
								}
							}

							if (dir==2)
							{
								if (alien.getX()+randx<xlimit)
								{
									alien.setX(alien.getX() + randx);
								}
							}


					//		alien.setY(alien.getY() + 2);
						}

					}
				}
				if (aliens.size()==0)
				{
				//	addmorealiens();
				}

				//add in collision detection
				for (Ammo am : shots) //remove dead aliens
				{
					if (am.isdead())
					{
						shots.remove(am);
					}
				}

				for (Alien a : aliens) //remove dead aliens
				{
					if (a.isdead())
					{
						aliens.remove(a);
					}
				}


				for (Alien a : aliens) //remove past aliens (collsion detection between aliens and wall)
				{
					if (a.getY()>600)
					{

						a.setY(100);
					//	aliens.remove(a);
					}
				}
				for (Alien alien : aliens) //remove dead aliens and hit ammo(collision detection between aliens and ammo)
				{
					for (Ammo ammo : shots)
					{


		               if (Math.abs(ammo.getX()-alien.getX())<80 && Math.abs(ammo.getY()-alien.getY())<80 && ammo.isalive())          //&&alien.getY()==ammo.getY())
		               {


		               	ammo.kill();
		               	hitnum++;
		               	ammosupply--;
		               	alien.movebackmore();
		               	alien.kill();
		               	System.out.println("alien hit! " + gethitnum());
						//alien.setY(alien.getY()-2);



		               }
		               //removedead();
					}
				}

				if (aliens.size()==0)
				{
				//	addmorealiens();
				}

				//m.drawgamescreen (graphToBack); //
				twoDGraph.drawImage(back, null, 0, 0);
				}
	}


	public void keyPressed(KeyEvent e)
	{
		if (screen.equals("menu"))
		{

		}

		else if (screen.equals("gamepage"))
		{

			if (!start)
			{
				if (e.getKeyCode() == KeyEvent.VK_S)
				{
					start = true;
				}


			}
			else if (start)
			{
				if (e.getKeyCode() == KeyEvent.VK_LEFT)
				{keys[0] = true;}

				if (e.getKeyCode() == KeyEvent.VK_RIGHT)
				{keys[1] = true;}

				if (e.getKeyCode() == KeyEvent.VK_UP)
				{keys[2] = true;}

				if (e.getKeyCode() == KeyEvent.VK_DOWN)
				{keys[3] = true;}

				if (e.getKeyCode() == KeyEvent.VK_SPACE)
				{

				//	keys[4] = true;

				}

				if (e.getKeyCode() == KeyEvent.VK_S) //S = "Stats" -ship info, alien info, ammo info
				{PrintStats();}

				if (e.getKeyCode() == KeyEvent.VK_P)
				{
					start = false;
					Graphics g = getGraphics();
					m.drawMenu(g);
				}

				repaint();
			}
		}

	}

	public void keyReleased(KeyEvent e)
	{
		if (screen.equals("menu"))
		{

		}

		else if (screen.equals("gamepage"))
		{
			if (e.getKeyCode() == KeyEvent.VK_LEFT)
			{
				keys[0] = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			{
				keys[1] = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_UP)
			{
				keys[2] = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN)
			{
				keys[3] = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE)
			{
				keys[4] = true; // key[4]  = false;
			}
		}

		repaint();
	}

	public void keyTyped(KeyEvent e)
	{
		if (screen.equals("menu"))
		{

		}

		else if (screen.equals("gamepage"))
		{
			if (e.getKeyCode() == KeyEvent.VK_SPACE)
			{
				keys[4] = true;
			}
			repaint();
		}


	}

   public void run()
   {
   	try
   	{
   		while(true)
   		{
   		   Thread.currentThread().sleep(5);
   		   Threadnum += 5; //
            repaint();
         }
      }catch(Exception e)
      {
      }
   }

   public void setupgamepage ()
   {
		Graphics g = getGraphics();
		Threadnum = 0;
		setBackground(Color.black);

		keys = new boolean[5];

		//instantiate other stuff
		ship = new Ship(250,400,10);   //10 //20
		aliens = new ArrayList<Alien>();
		shots = new ArrayList<Ammo>();
		everything = new ArrayList<MovingThing>();
		ammosupply = 1000;
		canshoot = true;
		addclan = false;
		start = false;
		addmorealiens();
		this.addKeyListener(this);
		new Thread(this).start();
   }


   public void PrintStats ()
   {
   	 System.out.println("ship: " + ship);
   	 System.out.println("aliens: " + aliens.size());
   	 System.out.println("ammo: " + ammosupply);

   	 System.out.println();
   }


   public void reload(int newammo)//
   {
	   ammosupply = newammo;
   }

   public void addmorealiens() //
   {
   	//	aliens = new ArrayList<Alien>();
		for (int x = 0; x<=5;  x++)
		{
			aliens.add(new Alien((100*x),0,350));
		}
   }


      public int gethitnum ()
   {
		return hitnum;
   }


   public double random(double x, double y) //
   {
   	return (double)(Math.random()*(y-x+1))+1;
   }

   public int random(int x, int y) //
   {
   	return (int)(Math.random()*(y-x+1))+1;
   }




}



/*  old version stuff!!!
 *
				if (Threadnum%20==0)    //if (Threadnum%100==0)
		{
			for (Alien alien : aliens)
			{
				alien.setY(alien.getY()+ (random(0,2)*1));
			}
		}


				if (Threadnum%1==0)
		{
			//if (canshoot)
			{
				for (Ammo ammo : shots)
				{
					ammo.setY(ammo.getY()-4);
				}
			}

			//canshoot = !canshoot;

		}

*/