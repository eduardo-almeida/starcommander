package game.util;

public class Collision {
	
	public static boolean Check(GameElement obj1, GameElement obj2) {
		
		if( ((obj1.GetX() + obj1.GetWidth()) >= obj2.GetX()) &&
    			
	    		((obj1.GetX() <= (obj2.GetX() + obj2.GetWidth() )) &&
	    		
	    		((obj1.GetY() + obj1.GetHeight()) >= obj2.GetY()) &&
	        			
	    	    ((obj1.GetY() <= (obj2.GetY() + obj2.GetHeight() )))))		
		
		  return true;
		else
		  return false;	
				
		
	}

}
