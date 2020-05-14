
//import GUIClass.*;
import aicapn.bot.Bot;
import aicapn.resources.Items;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Vector;
import aicapn.resources.Resources;
import aicapn.world.*;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class defaultBot1 extends Bot{

    private CrateState item = null;
    private ShipState target = null;
	private ArrayList<ShipState> enemies;
    private ArrayList<CrateState> crates;
	private Boolean bDone = false;
    private String previousMessage;

    public defaultBot1() {
        name="defaultBot1";
        previousMessage = "";		
    }

    /**
     * Action thread of the bot
     */
    @Override
    public void action() {             
            //try{
		
				PrintWriter pw = null;
				try {
					Socket s = new Socket(InetAddress.getLocalHost().getHostAddress(), 2000); //who you want to listen to
					pw = new PrintWriter(s.getOutputStream(), true);
				} catch (UnknownHostException ex) {
					Logger.getLogger(defaultBot1.class.getName()).log(Level.SEVERE, null, ex);
				} catch (IOException ex) {
					Logger.getLogger(defaultBot1.class.getName()).log(Level.SEVERE, null, ex);
				}

                
	Integer state = 0; //IDLE STATE
	Point prev=null;
	Vector<Point> path=null;
	while(ship.isAlive()){
	enemies = world.getShips(ship);
	crates = world.getCrates();
		if(state==0)
		{
			//BEGIN TRANSITION
			if(getEnemyExist_0() ) //HP_MORE_THAN_50&&ENEMY_IS_NEAR
			{
				state=2; //STATE MOVES HERE
				if(!previousMessage.equals("2 0 0"))
				{ 
					pw.println("2 0 0"); 
					previousMessage = "2 0 0";
				}
			}
			else
			if(getEnemyExist_1() ) //HP_LESS_THAN_50&&ENEMY_IS_NEAR
			{
				state=1; //STATE MOVES HERE
				if(!previousMessage.equals("1 0 0"))
				{ 
					pw.println("1 0 0"); 
					previousMessage = "1 0 0";
				}
			}
			else{
				//BEGIN ACTIONS
				if(  ship.isAlive()==true   ) //ALIVE
				{
					 ShipState enemy = getEnemyMatch_1();
goTo(enemy.getX(), enemy.getY());if (enemy.getX() > ship.getX() || enemy.getX() < ship.getX())ship.setDirection(Resources.TOP);else if (enemy.getY() > ship.getY() || enemy.getY() < ship.getY())ship.setDirection(Resources.LEFT);ship.fire(); 
					if(!previousMessage.equals("0 1 0"))
					{ 
						pw.println("0 1 0"); 
						previousMessage = "0 1 0";
					}
				}
			} //for each state
		}else
		if(state==1)
		{
			//BEGIN TRANSITION
			if(getEnemyExist_2() ) //HP_MORE_THAN_50&&ENEMY_IS_NEAR
			{
				state=2; //STATE MOVES HERE
				if(!previousMessage.equals("2 0 0"))
				{ 
					pw.println("2 0 0"); 
					previousMessage = "2 0 0";
				}
			}
			else{
				//BEGIN ACTIONS
				if(getItemExist_3() ) //HEALTH_ITEM_EXISTS&&SHIP_NEAR_DEATH
				{
					 item = getItemMatch_3();
goTo(item.getX(), item.getY()); 
					if(!previousMessage.equals("1 1 0"))
					{ 
						pw.println("1 1 0"); 
						previousMessage = "1 1 0";
					}
				}
				else
				if(getItemExist_4() ) //MINE_EXISTS
				{
					 item = getItemMatch_4();
goTo(item.getX(), item.getY()); 
					if(!previousMessage.equals("1 1 1"))
					{ 
						pw.println("1 1 1"); 
						previousMessage = "1 1 1";
					}
				}
				else
				if(getEnemyExist_5() ) //ENEMY_IS_NEAR
				{
					 ShipState enemy = getEnemyMatch_5();
if( Math.abs(enemy.getX()-ship.getX())<=10 || Math.abs(enemy.getY()-ship.getY()) <=10 ){ try{ goTo(enemy.getX() - 10, enemy.getY() - 10);} catch (Exception Ef){  try{  goTo(enemy.getX() + 10, enemy.getY() + 10);  }catch (Exception Eg) { }}} 
					if(!previousMessage.equals("1 1 2"))
					{ 
						pw.println("1 1 2"); 
						previousMessage = "1 1 2";
					}
				}
			} //for each state
		}else
		if(state==2)
		{
			//BEGIN TRANSITION
			if(getEnemyExist_6() ) //HP_LESS_THAN_50&&ENEMY_IS_NEAR
			{
				state=1; //STATE MOVES HERE
				if(!previousMessage.equals("1 0 0"))
				{ 
					pw.println("1 0 0"); 
					previousMessage = "1 0 0";
				}
			}
			else{
				//BEGIN ACTIONS
				if(getEnemyExist_7() ) //ENEMY_IS_NEAR
				{
					 ShipState enemy = getEnemyMatch_7();
if( Math.abs(enemy.getX()-ship.getX())<=10 || Math.abs(enemy.getY()-ship.getY()) <=10 ){ try{ goTo(enemy.getX() - 10, enemy.getY() - 10);} catch (Exception Ef){  try{  goTo(enemy.getX() + 10, enemy.getY() + 10);  }catch (Exception Eg) { }}} 
					if(!previousMessage.equals("2 1 0"))
					{ 
						pw.println("2 1 0"); 
						previousMessage = "2 1 0";
					}
				}
			} //for each state
		}
	}

            //}catch(OutOfThisWorldException o){ o.printStackTrace(); }

    }

       
	private Boolean getEnemyExist_0(){   
		Boolean result = false;   
		for(int i=0; i<enemies.size(); i++){   
			target = enemies.get(i);   
			if( ship.getHealth()>5  && target.getHealth()>0 ){   
				result = true;   
			}   
		}   
		return result;   
	}    
	private ShipState getEnemyMatch_0(){   
		ShipState result = null;   
		for(int i=0; i<enemies.size(); i++){   
			target = enemies.get(i);   
			if( ship.getHealth()>5  && target.getHealth()>0 ){   
				result = enemies.get(i);   
			}   
		}   
		return result;   
	}   
	private Boolean getEnemyExist_1(){   
		Boolean result = false;   
		for(int i=0; i<enemies.size(); i++){   
			target = enemies.get(i);   
			if( ship.getHealth()<5  && target.getHealth()>0 ){   
				result = true;   
			}   
		}   
		return result;   
	}    
	private ShipState getEnemyMatch_1(){   
		ShipState result = null;   
		for(int i=0; i<enemies.size(); i++){   
			target = enemies.get(i);   
			if( ship.getHealth()<5  && target.getHealth()>0 ){   
				result = enemies.get(i);   
			}   
		}   
		return result;   
	}   
	private Boolean getEnemyExist_2(){   
		Boolean result = false;   
		for(int i=0; i<enemies.size(); i++){   
			target = enemies.get(i);   
			if( ship.getHealth()>5  && target.getHealth()>0 ){   
				result = true;   
			}   
		}   
		return result;   
	}    
	private ShipState getEnemyMatch_2(){   
		ShipState result = null;   
		for(int i=0; i<enemies.size(); i++){   
			target = enemies.get(i);   
			if( ship.getHealth()>5  && target.getHealth()>0 ){   
				result = enemies.get(i);   
			}   
		}   
		return result;   
	}    
	private Boolean getItemExist_3(){   
		Boolean result = false;   
		for(int i=0; i<crates.size(); i++){   
			item = crates.get(i);   
			if(  item.getType()==Items.HEAL  &&  ship.getHealth()==1  ){   
				result = true;   
			}   
		}   
		return result;   
	}    
	private CrateState getItemMatch_3(){   
		CrateState result = null;   
		for(int i=0; i<crates.size(); i++){   
			item = crates.get(i);   
			if(  item.getType()==Items.HEAL  &&  ship.getHealth()==1  ){   
				result = crates.get(i);   
			}   
		}   
		return result;   
	}    
	private Boolean getItemExist_4(){   
		Boolean result = false;   
		for(int i=0; i<crates.size(); i++){   
			item = crates.get(i);   
			if(  item.getType()==Items.MINE  ){   
				result = true;   
			}   
		}   
		return result;   
	}    
	private CrateState getItemMatch_4(){   
		CrateState result = null;   
		for(int i=0; i<crates.size(); i++){   
			item = crates.get(i);   
			if(  item.getType()==Items.MINE  ){   
				result = crates.get(i);   
			}   
		}   
		return result;   
	}   
	private Boolean getEnemyExist_5(){   
		Boolean result = false;   
		for(int i=0; i<enemies.size(); i++){   
			target = enemies.get(i);   
			if( target.getHealth()>0 ){   
				result = true;   
			}   
		}   
		return result;   
	}    
	private ShipState getEnemyMatch_5(){   
		ShipState result = null;   
		for(int i=0; i<enemies.size(); i++){   
			target = enemies.get(i);   
			if( target.getHealth()>0 ){   
				result = enemies.get(i);   
			}   
		}   
		return result;   
	}   
	private Boolean getEnemyExist_6(){   
		Boolean result = false;   
		for(int i=0; i<enemies.size(); i++){   
			target = enemies.get(i);   
			if( ship.getHealth()<5  && target.getHealth()>0 ){   
				result = true;   
			}   
		}   
		return result;   
	}    
	private ShipState getEnemyMatch_6(){   
		ShipState result = null;   
		for(int i=0; i<enemies.size(); i++){   
			target = enemies.get(i);   
			if( ship.getHealth()<5  && target.getHealth()>0 ){   
				result = enemies.get(i);   
			}   
		}   
		return result;   
	}   
	private Boolean getEnemyExist_7(){   
		Boolean result = false;   
		for(int i=0; i<enemies.size(); i++){   
			target = enemies.get(i);   
			if( target.getHealth()>0 ){   
				result = true;   
			}   
		}   
		return result;   
	}    
	private ShipState getEnemyMatch_7(){   
		ShipState result = null;   
		for(int i=0; i<enemies.size(); i++){   
			target = enemies.get(i);   
			if( target.getHealth()>0 ){   
				result = enemies.get(i);   
			}   
		}   
		return result;   
	} //methods

    // =============================== METHODS ================================== //

    Vector<Point> currentPath = new Vector<Point>();
    
    public void goTo(int x, int y)
    {
        try {
            currentPath = findPath(x, y);

            sailTo(currentPath.firstElement().x, currentPath.firstElement().y);
            currentPath.remove(0);

        } catch (Exception ex) {
            Logger.getLogger(defaultBot1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Moves the ship towards an x,y coordinate
     * @param x x coordinate
     * @param y y coordinate
     */
    public void sailTo(int x, int y)
    {
            if(ship.getX()>x)
                ship.moveLeft();
            else
                if(ship.getX()<x)
                    ship.moveRight();
                else
                    if(ship.getY()>y)
                        ship.moveUp();
                    else
                        if(ship.getY()<y)
                            ship.moveDown();
    }

    /**
     * Creates a path from the bot to a goal location through breadth first search
     * @param x x coordinate
     * @param y y coordinate
     * @return Path
     */
    public Vector<Point> findPath(int x, int y) throws Exception
    {
        ArrayList<Node> visited=new ArrayList<Node>();
        ArrayList<Node> pending=new ArrayList<Node>();

        Node top, bottom, left, right, current;
        top=bottom=left=right=null;
        current=new Node(ship.getX(), ship.getY(), null);
        visited.add(new Node(ship.getX(),ship.getY(),current));
        if(ship.getX()-1>0 && world.isPassable(ship.getX()-1,ship.getY()))
            left=new Node(ship.getX()-1, ship.getY(),current);
        if(ship.getX()+1<40 && world.isPassable(ship.getX()+1,ship.getY()))
            right=new Node(ship.getX()+1, ship.getY(),current);
        if(ship.getY()-1>0 && world.isPassable(ship.getX(),ship.getY()-1))
            top=new Node(ship.getX(), ship.getY()-1,current);
        if(ship.getY()+1<30 && world.isPassable(ship.getX(),ship.getY()+1))
            bottom=new Node(ship.getX(), ship.getY()+1,current);
        if(left!=null && !visited.contains(left))
            pending.add(left);
        if(top!=null && !visited.contains(top))
            pending.add(top);
        if(right!=null&&!visited.contains(right))
            pending.add(right);
        if(bottom!=null&&!visited.contains(bottom))
            pending.add(bottom);
        while(pending.size()>0)
        {
            visited.add(pending.get(0));
            current=pending.get(0);
            if(x==current.x && y==current.y)
                break;
            top=bottom=left=right=null;
            if(current.getX()-1>0 && world.isPassable(current.getX()-1,current.getY()))// && world.getMap()[((int)current.getX()-1)/20][((int)current.getY())/20]==Resources.PASSABLE)
                left=new Node((int)current.getX()-1, (int)current.getY(), current);
            if(current.getX()+1<Resources.MAP_WIDTH&&world.isPassable(current.getX()+1,current.getY()))// && world.getMap()[((int)current.getX()+1)/20][((int)current.getY())/20]==Resources.PASSABLE)
                right=new Node((int)current.getX()+1, (int)current.getY(), current);
            if(current.getY()-1>0 && world.isPassable(current.getX(),current.getY()-1))// && world.getMap()[((int)current.getX())/20][((int)current.getY()-1)/20]==Resources.PASSABLE)
                top=new Node((int)current.getX(), (int)current.getY()-1, current);
            if(current.getY()+1<Resources.MAP_HEIGHT && world.isPassable(current.getX(),current.getY()+1))// && world.getMap()[((int)current.getX())/20][((int)current.getY()+1)/20]==Resources.PASSABLE)
                bottom=new Node((int)current.getX(), (int)current.getY()+1, current);
            if(left!=null&&!visited.contains(left)&&!pending.contains(left))
                pending.add(left);
            if(top!=null && !visited.contains(top)&&!pending.contains(top))
                pending.add(top);
            if(right!=null && !visited.contains(right)&&!pending.contains(right))
                pending.add(right);
            if(bottom!=null && !visited.contains(bottom)&&!pending.contains(bottom))
                pending.add(bottom);
            pending.remove(0);
        }
        Vector<Point> path=new Vector<Point>();
        while(current.parent!=null)
        {
            path.insertElementAt(new Point(current.getX(),current.getY()),0);
            current=current.getParent();
        }

        return path;
    }
/**
 * Represents a node in a path
 */
 class Node{
     private int x;
     private int y;
     private Node parent;

        /**
         * Creates a node
         * @param x x coordinate of the node
         * @param y y coordinate of the node
         * @param parent Previous node in a path
         */
        public Node(int x, int y,Node parent) {
            this.x=x;
            this.y=y;
            this.parent=parent;
        }
        /**
         * Retrieves the preceeding node
         * @return
         */
        public defaultBot1.Node getParent() {
            return parent;
        }

        /**
         * Sets the preceeding node
         * @param parent
         */
        public void setParent(defaultBot1.Node parent) {
            this.parent = parent;
        }

        /**
         * Gets the x coordinate of the node
         * @return X coordinate
         */
        public int getX() {
            return x;
        }

        /**
         * Sets the x coordinate of the node
         * @param x X coordinate
         */
        public void setX(int x) {
            this.x = x;
        }

        /**
         * Retrieves the Y coordinate of the node
         * @return Y coordinate
         */
        public int getY() {
            return y;
        }

        /**
         * Sets the Y coordinate of the node
         * @param y Y coordinate
         */
        public void setY(int y) {
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            Node temp=(Node)obj;
            return this.x==temp.getX()&&this.y==temp.getY();
        }
 }
}
