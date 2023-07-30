import java.awt.*;
import javax.swing.*;
import java.util.*;

/** The view (graphical) component */
public class LifeView extends JPanel
{
    Random rand = new Random();
    private boolean change = false;
        private static final long serialVersionUID = 1L;
    private static int SIZE = 60;
    public static boolean color = false;

    /** store a reference to the current state of the grid */
    private LifeCell[][] grid;

    public LifeView()
    {
        grid = new LifeCell[SIZE][SIZE];
    }

    /** entry point from the model, requests grid be redisplayed */
    public void updateView( LifeCell[][] mg )
    {
        grid = mg;
        repaint();
    }
    
    public Color changeColor(boolean c)
    {
        float red = rand.nextFloat();
        float green = rand.nextFloat();
        float blue = rand.nextFloat();
        Color randomColor = new Color(red, green, blue);
        return randomColor;
    }
    
    

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        int testWidth = getWidth() / (SIZE+1);
        int testHeight = getHeight() / (SIZE+1);
        // keep each life cell square
        int boxSize = Math.min(testHeight, testWidth);
        
        Color randomColor = changeColor(true);
        for ( int r = 0; r < SIZE; r++ )
        {
            for (int c = 0; c < SIZE; c++ )
            {
                if (grid[r][c] != null)
                {
                    if ( grid[r][c].isAliveNow() ){
                        
                        if(color){
                            
                            g.setColor(randomColor);
                            
                        }
                        else{g.setColor( Color.BLUE );}
                    }
                    else
                        g.setColor( new Color(235,235,255) );

                    g.fillRect( (c+1)*boxSize, (r+1)* boxSize, boxSize-2, boxSize-2);
                }
            }
        }
        
    }
    
    
}
