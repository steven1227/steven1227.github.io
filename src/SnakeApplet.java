import java.applet.Applet;
import java.awt.Dimension;
import java.awt.Graphics;


public class SnakeApplet extends Applet
{

	public void init()
	{
		
		c=new BackGround();
		c.setPreferredSize(new Dimension(1000,640));
		c.setVisible(true);
		c.setFocusable(true);
		this.add(c);
		this.setVisible(true);
		this.setSize(new Dimension(1000,640));
	
	}
	
	
	private BackGround c;
}
