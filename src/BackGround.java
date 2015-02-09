import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;




public class BackGround  extends Canvas implements Runnable,KeyListener
{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String restart = "";
	
	private LinkedList<Point> snake;

	private Point fruit;
	private Image i;
	private Thread Run;
	Image city;

	private final int BOX_Height=20;
	private final int BOX_Width=20;
	private final int Grid_Width=30;
	private final int Grid_Height=30;
	private int direct=direction.No_direction;

	private int score=0;
	private boolean start=false;
	private boolean flag=true;
	URL url;
	
	
	public void init()
	{
		
		
	}
	
	
	public void paint(Graphics g)
	{
			g.drawImage(city, 100, 0, null);
		
		//System.out.print("fuck\n");
		

		if (snake==null){
			snake = new LinkedList<Point>();
			snake.add(new Point(1,3));
			snake.add(new Point(1,2));
			snake.add(new Point(1,1));
			this.fruit = new Point(10,10);
		}

		
				//capture the graphics that we can use
		if(this.flag==true)
		{
			this.addKeyListener(this);
		
			city = new ImageIcon("background.jpg").getImage();
			
			this.flag=false;
	
		}
		
		g.setFont(new Font("TimesRoman", Font.PLAIN,18)); 
		String s="Score:";
		g.drawString(s, 800, 300);
		//this.PlaceFruit();
		
		if(this.Run==null)
		{
			this.Run=new Thread(this,"draw");
			Run.start();
		}
		this.DrawGrid(g);
		this.DrawScore(g);
		
		if (this.start==false)
		{
			g.setFont(new Font("TimesRoman", Font.BOLD,30)); 
			String s2="Press Space Button to start ";
			//String s3=1+"2";
			//System.out.println(s3.charAt(1));
			g.drawString(this.restart, 205, 250);
			g.drawString(s2, 205, 300);	
		}
		else
		{
			this.DrawSanke(g);
			this.DrawFruit(g);	
			
		}
	}
	

	public void restart()
	{
		
		snake = new LinkedList<Point>();
		snake.add(new Point(1,3));
		snake.add(new Point(1,2));
		snake.add(new Point(1,1));
		
		this.score=0;
		this.direct=direction.No_direction;
		this.start=false;
		this.restart=new String("Your Snake failed.");
		
	}

	public void update(Graphics g) {
		Graphics offScreenGraphics;
		Dimension d = this.getSize();
		//System.out.println(d.width+"..."+d.height);
		BufferedImage offscreen = new BufferedImage(d.width,d.height, BufferedImage.TYPE_INT_ARGB);
		offScreenGraphics = offscreen.getGraphics();
		offScreenGraphics.setColor(this.getBackground());
		offScreenGraphics.fillRect(100, 0, d.width, d.height);
		offScreenGraphics.setColor(this.getForeground());
		paint(offScreenGraphics);
		
		
		g.drawImage(offscreen, 0, 0, this);
		

	
	}

	public void DrawGrid(Graphics g)
	{
		g.setColor(Color.GRAY);
		g.drawRect(100, 0, this.Grid_Width*this.BOX_Width, this.Grid_Height*this.BOX_Height);
		
//		for(int x=100+this.BOX_Width;x<=100+this.Grid_Width*this.BOX_Width;x=x+this.BOX_Width)
//		{
//			g.drawLine(x, 0, x, this.Grid_Height*this.BOX_Height);
//		}
//		
//		for(int y=this.BOX_Height;y<=this.Grid_Height*this.BOX_Width;y=y+this.BOX_Height)
//		{
//			
//			g.drawLine(100, y, 100+this.Grid_Width*this.BOX_Height, y);
//		}
	}
	
	public void DrawScore(Graphics g)
	{
		g.setColor(Color.BLACK);
		//g.clearRect(850, 250, 100, 100);
		 
		g.setFont(new Font("TimesRoman", Font.PLAIN,18)); 
		g.drawString(""+this.score, 860, 300);
	}
	
	public void move()
	{

		boolean flag=true;
		Point head = snake.peekFirst();
		Point tail = snake.peekLast();
		Point newPoint = head;
		
		switch (this.direct)
		{
		
		case direction.east:
			newPoint =new Point(head.x+1,head.y);
			break;
		case direction.west:
			newPoint =new Point(head.x-1,head.y);
			break;
		case direction.south:
			newPoint =new Point(head.x,head.y+1);
			break;
		case direction.north:	
			newPoint =new Point(head.x,head.y-1);
			break;
		default:
			newPoint =new Point(head.x,head.y+1);
			break;
		}
		
		if(this.direct!=direction.No_direction)
			snake.remove(snake.peekLast());
		
		//check if the point is fruit
		if (newPoint.equals(fruit)){
			// snake add head action 
			snake.add(tail);
			PlaceFruit();
			this.score=this.score+10;		
		}	
		
		else if (newPoint.x<0||newPoint.x>=this.Grid_Width||newPoint.y>=this.Grid_Height||newPoint.y<0)
		{
			restart();
			flag=false;
		}
		
		else if (snake.contains(newPoint))
		{
			restart();
			flag=false;

		}
		
		
		if(this.direct!=direction.No_direction&&flag)	
			snake.push(newPoint);
		
		

	}
	
	
	public void DrawSanke(Graphics g)
	{
		System.out.println(snake.size());
		for(int i=0;i<snake.size();i++)
		{
			if(i==0)
			{
				g.setColor(Color.yellow);
			}
			else{
				g.setColor(Color.green);
			}
			g.fillRect(100+snake.get(i).x * BOX_Width, snake.get(i).y * BOX_Height, BOX_Width, BOX_Height);
			
		}

	}
	
	

	public void DrawFruit(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval(this.fruit.x*BOX_Width+100,this.fruit.y*BOX_Height,BOX_Width,BOX_Height);
		g.setColor(Color.white);
		g.setColor(Color.GRAY);
		
	}
	
	public void PlaceFruit(){
		Random rand = new Random();
		int randomX = rand.nextInt(this.Grid_Width);
		int randomY = rand.nextInt(this.Grid_Height);
		Point randomPoint = new Point(randomX,randomY);
		while (snake.contains(randomPoint)){
			randomX= rand.nextInt(this.Grid_Width);
			randomY = rand.nextInt(this.Grid_Height);
			randomPoint = new Point(randomX,randomY);
		}
		
		fruit = randomPoint;
	}

	
	
	public void run() {
		while(true)
		{		
			
			this.move();
			//this.Draw(global);
			this.repaint();

			try
			{
				
				Thread.currentThread();
				Thread.sleep(100);
				
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
	}


	@Override
	public void keyPressed(KeyEvent arg0) {
		
		
		if (arg0.getKeyCode()==KeyEvent.VK_UP){

			if (this.direct!=direction.south&&this.direct!=direction.No_direction)

			this.direct=direction.north;
	
		}
		else if (arg0.getKeyCode()==KeyEvent.VK_DOWN)
		{

			if (this.direct!=direction.north&&this.direct!=direction.No_direction)

			this.direct=direction.south;

		}
		else if (arg0.getKeyCode()==KeyEvent.VK_RIGHT)
		{

			if (this.direct!=direction.west&&this.direct!=direction.No_direction)
				this.direct=direction.east;

				//System.out.println(this.direct);
		}
		else if (arg0.getKeyCode()==KeyEvent.VK_LEFT)
		{

			if (this.direct!=direction.east&&this.direct!=direction.No_direction)

			this.direct =direction.west;

		}
		else if (arg0.getKeyCode()==KeyEvent.VK_SPACE)
		{
			if(this.direct==direction.No_direction)
			this.direct =direction.south;
			this.start=true;

		}
		
					
	}


	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}	
}
