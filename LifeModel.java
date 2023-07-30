import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.Timer;

public class LifeModel implements ActionListener
{

	/*
	 *  This is the Model component.
	 */

	private static int SIZE = 60;
	private LifeCell[][] grid;
	private String fileName = "blinker.lif";
	private LifeCell[][] resetGrid;
        private boolean isReset;
	
	LifeView myView;
	Timer timer;

	/** Construct a new model using a particular file */
	public LifeModel(LifeView view, String fileName) throws IOException
	{       
		int r, c;
		grid = new LifeCell[SIZE][SIZE];
		for ( r = 0; r < SIZE; r++ )
			for ( c = 0; c < SIZE; c++ )
				grid[r][c] = new LifeCell();

		if ( fileName == null ) //use random population
		{                                           
			for ( r = 0; r < SIZE; r++ )
			{
				for ( c = 0; c < SIZE; c++ )
				{
					if ( Math.random() > 0.85) //15% chance of a cell starting alive
						grid[r][c].setAliveNow(true);
				}
			}
		}


		myView = view;
		myView.updateView(grid);

	}

	/** Constructor a randomized model */
	public LifeModel(LifeView view) throws IOException
	{
		this(view, null);
	}

	/** pause the simulation (the pause button in the GUI */
	public void pause()
	{
		timer.stop();
	}
	
	/** resume the simulation (the pause button in the GUI */
	public void resume()
	{
		timer.restart();
	}
	
	/** run the simulation (the pause button in the GUI */
	public void run()
	{
		timer = new Timer(50, this);
		timer.setCoalesce(true);
		timer.start();
	}
	
	
	
	public void reset()
        {
            timer.stop();
    
            if(isReset)
            {
                grid=resetGrid;
            }
            else
            {
                for (int r = 0; r < SIZE; r++ )
                {
                    for (int c = 0; c < SIZE; c++ )
                    {
                        grid[r][c].setAliveNow(false);
                        if ( Math.random() > 0.85) //15% chance of a cell starting alive
                            grid[r][c].setAliveNow(true);
                    }
                }
            }
            myView.updateView(grid);


        }
	
	public void color()
        {
            myView.color= !myView.color;
        }
	
	

	/** called each time timer fires */
	public void actionPerformed(ActionEvent e)
	{
		oneGeneration();
		myView.updateView(grid);
	}

    	 private void oneGeneration()
    {
        for (int r=0;r<SIZE;r++)
        {
            for(int c=0;c<SIZE;c++)
            {
                int n=countNeighbors(r,c);
                boolean alive=grid[r][c].isAliveNow();

                if(n==3 && !alive)
                {
                    grid[r][c].setAliveNext(true);
                }
                else if (alive && (n==3 || n==2)){
                    grid[r][c].setAliveNext(true);
                }
                else{

                    grid[r][c].setAliveNext(false);
                }
            }
        }

        for(int r = 0; r < SIZE; r++){
            for(int c = 0; c < SIZE; c++){
                grid[r][c].setAliveNow(grid[r][c].isAliveNext());
            }
        }
        myView.updateView(grid);
    }
    private boolean isInGrid(int r,int c)
    {
        if (r<0 || r>grid.length-1)
        {
            return false;
        }
        
        if (c<0 || c>grid[r].length-1)
        {
            return false;
        }
        return true;
    }
    private int countNeighbors(int r,int c) {
        int cnt=0;

        for(int i=r-1;i<=r+1;i++)
        {
            for (int j=c-1;j<=c+1;j++)
            {
                if(isInGrid(i,j))
                {
                    if (grid[i][j].isAliveNow())
                    {
                        cnt++;
                    }
                }

            }
        }
        
        if(grid[r][c].isAliveNow())
        {
            cnt--;
        }
        
        return cnt;

    }
}


