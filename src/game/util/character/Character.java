package game.util.character;

import java.util.ArrayList;



import android.content.Context;
import android.graphics.Canvas;

import game.util.*;
import game.util.scene.Scene;

public class Character extends GameElement {
	
	enum ActionState { IDLE, WALKING, RUNNING, ATTACK1, ATTACK2, ATTACK3, ATTACK4, ATTACK5,
	ATTACK1_ON_JUMP, ATTACK2_ON_JUMP, ATTACK3_ON_JUMP, ATTACK4_ON_JUMP, ATTACK5_ON_JUMP,	
	DAMAGED, DEAD }
	
	enum DirectionState { RIGHT, LEFT }
	
	enum JumpState { JUMPING, FALLING, IS_GROUND }
	
	enum DamageState { NO_DAMAGE, DAMAGED }
	
	enum LiveState { LIVE, DYING, DEAD }
	
	ActionState action;
	
	DirectionState direction;
	
	JumpState jump;
	
	DamageState damageState;
	
	LiveState liveState;
	
	ArrayList<AnimationSprites> animationList;
	
	AnimationSprites aIdle, aWalking, aRunning, aAttack1, aAttack2, aAttack3, 
	aAttack4, aAttack5, aAttack1OnJump, aAttack2OnJump, aAttack3OnJump, aAttack4OnJump, aAttack5OnJump, aJumping, aDamaged, aDie;
	
	ArrayList<String> aCollisionElementBySide_Tag;

	ArrayList<String> aCollisionElementBySide_Type;
	
	ArrayList<String> aCollisionElementByFall_Tag;

	ArrayList<String> aCollisionElementByFall_Type;
	
	
	int INITIAL_VELOCITY_JUMP = -18;
	
	int MAX_VELOCITY_FALL = 12;
	
	int JumpShift;
	
	int MAX_FRAME_DAMAGE = 15;
	
	int countFrameDamage;
	
	boolean enableFall = true;
	
	
	
	public Character(Context context, int x, int y, int w, int h)
	{
		aIdle = new AnimationSprites(context, x, y, w, h);
		
		aWalking = new AnimationSprites(context, x, y, w, h);
		
		aRunning = new AnimationSprites(context, x, y, w, h);
		
		aAttack1 = new AnimationSprites(context, x, y, w, h);
		
		aAttack2 = new AnimationSprites(context, x, y, w, h);
		
		aAttack3 = new AnimationSprites(context, x, y, w, h);
		
		aAttack4 = new AnimationSprites(context, x, y, w, h);
		
		aAttack5 = new AnimationSprites(context, x, y, w, h);
		
        aAttack1OnJump = new AnimationSprites(context, x, y, w, h);
		
		aAttack2OnJump = new AnimationSprites(context, x, y, w, h);
		
		aAttack3OnJump = new AnimationSprites(context, x, y, w, h);
		
		aAttack4OnJump = new AnimationSprites(context, x, y, w, h);
		
		aAttack5OnJump = new AnimationSprites(context, x, y, w, h);
						
		aJumping = new AnimationSprites(context, x, y, w, h);
		
		aDamaged = new AnimationSprites(context, x, y, w, h);
		
		aDie = new AnimationSprites(context, x, y, w, h);
		
		SetBounds(x, y, w, h);
		
		animationList = new ArrayList<AnimationSprites>();
		
		animationList.add(aAttack1);
		animationList.add(aAttack2);
		animationList.add(aAttack3);
		animationList.add(aAttack4);
		animationList.add(aAttack5);
		animationList.add(aAttack1OnJump);
        animationList.add(aAttack2OnJump);
        animationList.add(aAttack3OnJump);
        animationList.add(aAttack4OnJump);
        animationList.add(aAttack5OnJump);
		animationList.add(aIdle);
		animationList.add(aWalking);
		animationList.add(aRunning);
		animationList.add(aJumping);
		animationList.add(aDamaged);
		animationList.add(aDie);
		
		
		aCollisionElementBySide_Tag =  new ArrayList<String>();
		aCollisionElementBySide_Type = new ArrayList<String>();
		
		aCollisionElementByFall_Tag =  new ArrayList<String>();
		aCollisionElementByFall_Type = new ArrayList<String>();
		
		direction = DirectionState.RIGHT;
		jump = JumpState.IS_GROUND;
		damageState = DamageState.NO_DAMAGE;
		
		
	}
	
	
	public void AddNewSpriteIdle(int Id)
	{
		aIdle.Add(Id);
	}
	
	public void AddNewSpriteWalking(int Id)
	{
		aWalking.Add(Id);
	}
	
	public void AddNewSpriteRunning(int Id)
	{
		aRunning.Add(Id);
	}
	
	public void AddNewSpriteAttack1(int Id)
	{
		aAttack1.Add(Id);
	}
	
	public void AddNewSpriteAttack1OnJump(int Id)
	{
		aAttack1OnJump.Add(Id);
	}
	
	public void AddNewSpriteAttack2(int Id)
	{
		aAttack2.Add(Id);
	}
	
	public void AddNewSpriteAttack2OnJump(int Id)
	{
		aAttack2OnJump.Add(Id);
	}
	
	public void AddNewSpriteAttack3(int Id)
	{
		aAttack3.Add(Id);
	}
	
	public void AddNewSpriteAttack3OnJump(int Id)
	{
		aAttack3OnJump.Add(Id);
	}
	
	
	public void AddNewSpriteAttack4(int Id)
	{
		aAttack4.Add(Id);
	}
	
	public void AddNewSpriteAttack4OnJump(int Id)
	{
		aAttack4OnJump.Add(Id);
	}
	
	public void AddNewSpriteAttack5(int Id)
	{
		aAttack5.Add(Id);
	}
	
	public void AddNewSpriteAttack5OnJump(int Id)
	{
		aAttack5OnJump.Add(Id);
	}
	
	public void AddNewSpriteJumping(int Id)
	{
		aJumping.Add(Id);
	}
	
	public void AddNewSpriteDamage(int Id)
	{
		aDamaged.Add(Id);
	}
	
	public void AddNewSpriteDie(int Id)
    {
        aDie.Add(Id);
    }
	
	
	public void Idle(int frames, boolean loop  )
	{
		if(action != ActionState.IDLE) {
	     action = ActionState.IDLE;	
		 aIdle.Start(frames, loop);		
		}
	}
	
	
	public void WalkingToRight(int frames, boolean loop )
	{
		if((direction != DirectionState.RIGHT) || (action != ActionState.WALKING))
		{
		  direction = DirectionState.RIGHT;
 		  action = ActionState.WALKING;
		  aWalking.Start(frames, loop);
		  
		}
		
	}
	

	public void WalkingToLeft(int frames, boolean loop )
	{
		if((direction != DirectionState.LEFT) || (action != ActionState.WALKING))
		{
		  direction = DirectionState.LEFT;
 		  action = ActionState.WALKING;
		  aWalking.Start(frames, loop);
		  
		}
		
	}
	
	public void RunningToRight(int frames, boolean loop )
	{
		if((direction != DirectionState.RIGHT) || (action != ActionState.RUNNING))
		{
		  direction = DirectionState.RIGHT;
 		  action = ActionState.RUNNING;
		  aRunning.Start(frames, loop);
		  
		}
		
	}
	
	public void RunningToLeft(int frames, boolean loop )
	{
		if((direction != DirectionState.LEFT) || (action != ActionState.RUNNING))
		{
		  direction = DirectionState.LEFT;
 		  action = ActionState.RUNNING;
		  aRunning.Start(frames, loop);
		  
		}
		
	}
	
	public void Attack1(int frames) {
		
		action = ActionState.ATTACK1;
		aAttack1.Start(frames, false);
		
	}
	
	
    public void Attack2(int frames) {
		
		action = ActionState.ATTACK2;
		aAttack2.Start(frames, false);
		
	}
    
    public void Attack3(int frames) {
		
		action = ActionState.ATTACK3;
		aAttack3.Start(frames, false);
		
	}
    
    
    public void Attack4(int frames) {
		
		action = ActionState.ATTACK4;
		aAttack4.Start(frames, false);
		
	}
    
    
    public void Attack5(int frames) {
		
		action = ActionState.ATTACK5;
		aAttack5.Start(frames, false);
		
	}
    
    public void Jump(int frames, boolean loop)
    {
    	if(jump == JumpState.IS_GROUND)
    	{
    		jump = JumpState.JUMPING;
    		JumpShift = INITIAL_VELOCITY_JUMP;    		
    		aJumping.Start(frames, loop);
    	}
    }
    
    public void Jump(int frames, int jump_shift, boolean loop)
    {
    	if(jump == JumpState.IS_GROUND)
    	{
    		jump = JumpState.JUMPING;
    		
    		if(jump_shift > 0)
    		  JumpShift = -jump_shift;
    		else
    		  JumpShift = jump_shift;	
    		
    		aJumping.Start(frames, loop);
    	}
    }
    
	

	@Override
	public void Draw(Canvas canvas) {
		
		if(damageState == DamageState.DAMAGED) {
			  
			
			countFrameDamage++;
			
			if(countFrameDamage != MAX_FRAME_DAMAGE)
			{
			   if((countFrameDamage % 2) == 0)
				  return;
			}
			else 
		    {
			  countFrameDamage = 0;
			  damageState = DamageState.NO_DAMAGE;
		    }
		}
		
		

		if(jump == JumpState.IS_GROUND)
		{
			if(action == ActionState.IDLE)
			{
				if(direction == DirectionState.RIGHT)
					aIdle.Draw(canvas);
				else
					aIdle.Draw(canvas,FlipEffect.HORIZONTAL);
			}
			else if(action == ActionState.WALKING)
			{
				if(direction == DirectionState.RIGHT)
					aWalking.Draw(canvas);
				else
					aWalking.Draw(canvas,FlipEffect.HORIZONTAL);
			}
			else if(action == ActionState.RUNNING)
			{
				if(direction == DirectionState.RIGHT)
					aRunning.Draw(canvas);
				else
					aRunning.Draw(canvas,FlipEffect.HORIZONTAL);
			}
			else if(action == ActionState.ATTACK1)
			{
				if(direction == DirectionState.RIGHT)
					aAttack1.Draw(canvas);
				else
					aAttack1.Draw(canvas,FlipEffect.HORIZONTAL);
			}
			
			else if(action == ActionState.ATTACK2)
			{
				if(direction == DirectionState.RIGHT)
					aAttack2.Draw(canvas);
				else
					aAttack2.Draw(canvas,FlipEffect.HORIZONTAL);
			}
			
			else if(action == ActionState.ATTACK3)
			{
				if(direction == DirectionState.RIGHT)
					aAttack3.Draw(canvas);
				else
					aAttack3.Draw(canvas,FlipEffect.HORIZONTAL);
			}
			
			else if(action == ActionState.ATTACK4)
			{
				if(direction == DirectionState.RIGHT)
					aAttack4.Draw(canvas);
				else
					aAttack4.Draw(canvas,FlipEffect.HORIZONTAL);
			}
			
			else if(action == ActionState.ATTACK5)
			{
				if(direction == DirectionState.RIGHT)
					aAttack5.Draw(canvas);
				else
					aAttack5.Draw(canvas,FlipEffect.HORIZONTAL);
			}
			else if(action == ActionState.DAMAGED)
			{
				if(direction == DirectionState.RIGHT)
					aDamaged.Draw(canvas);
				else
					aDamaged.Draw(canvas,FlipEffect.HORIZONTAL);
			}
			else if(action == ActionState.DEAD)
			{
				if(direction == DirectionState.RIGHT)
					aDie.Draw(canvas);
				else
					aDie.Draw(canvas,FlipEffect.HORIZONTAL);
			}
			
		} else {
			
			
			if(action == ActionState.ATTACK1) {
				
				if(direction == DirectionState.RIGHT)
					aAttack1OnJump.Draw(canvas);
				else
					aAttack1OnJump.Draw(canvas,FlipEffect.HORIZONTAL);
				
			} else if(action == ActionState.ATTACK2) {
				
				if(direction == DirectionState.RIGHT)
					aAttack2OnJump.Draw(canvas);
				else
					aAttack2OnJump.Draw(canvas,FlipEffect.HORIZONTAL);
				
			} else if(action == ActionState.ATTACK3) {
				
				if(direction == DirectionState.RIGHT)
					aAttack3OnJump.Draw(canvas);
				else
					aAttack3OnJump.Draw(canvas,FlipEffect.HORIZONTAL);
				
			} else if(action == ActionState.ATTACK4) {
				
				if(direction == DirectionState.RIGHT)
					aAttack4OnJump.Draw(canvas);
				else
					aAttack4OnJump.Draw(canvas,FlipEffect.HORIZONTAL);
				
			} else if(action == ActionState.ATTACK5) {
				
				if(direction == DirectionState.RIGHT)
					aAttack5OnJump.Draw(canvas);
				else
					aAttack5OnJump.Draw(canvas,FlipEffect.HORIZONTAL);
				
			} else   {
			
			
			if(direction == DirectionState.RIGHT)
				aJumping.Draw(canvas);
			else
				aJumping.Draw(canvas,FlipEffect.HORIZONTAL);
			}
		}

	}
	
	
	public void Update(Scene scene) {
		
		
		boolean isGround = false;
		
		
		
		if(action == ActionState.ATTACK1)
		{
			if(jump == JumpState.IS_GROUND) {
				
			  if(!aAttack1.IsPlaying())
			  {
				action = ActionState.IDLE;
			  }
			  
			}
			else {
			  if(!aAttack1OnJump.IsPlaying())
			   {
					action = ActionState.IDLE;
			   }
			}
		} 
		else if(action == ActionState.ATTACK2)
		{
			if(jump == JumpState.IS_GROUND)
			{
			  if(!aAttack2.IsPlaying())
		  	   {
				 action = ActionState.IDLE;
			   }
			}
			else {
				
				if(!aAttack2OnJump.IsPlaying())
			  	   {
					 action = ActionState.IDLE;
				   }
			}
		} 
		
		else if(action == ActionState.ATTACK3)
		{
			
			if(jump == JumpState.IS_GROUND) {
			  if(!aAttack3.IsPlaying())
		  	  {
				action = ActionState.IDLE;
			  }
			}
			else {
				
				if(!aAttack3OnJump.IsPlaying())
			  	  {
					action = ActionState.IDLE;
				  }
				
			}
		}
		
		else if(action == ActionState.ATTACK4)
		{
			
			if(jump == JumpState.IS_GROUND){
			  if(!aAttack4.IsPlaying())
			  {
				action = ActionState.IDLE;
			  }
			}
			else {
				
			  if(!aAttack4OnJump.IsPlaying())
			   {
				action = ActionState.IDLE;
			   }
				
			}
		}
		
		else if(action == ActionState.ATTACK5)
		{
			if(jump == JumpState.IS_GROUND) {
			 if(!aAttack5.IsPlaying())
			  {
				action = ActionState.IDLE;
			  }
			} else {
				
				if(!aAttack5OnJump.IsPlaying())
				  {
					action = ActionState.IDLE;
				  }
				
			}
		}
		
		else if(action == ActionState.DAMAGED)
		{
			if(!aDamaged.IsPlaying())
			{
				action = ActionState.IDLE;
			}
		}
		else if(action == ActionState.DEAD) {
			
			if(aDie.IsPlaying())
			{
				liveState = LiveState.DYING;
			} else {
				liveState = LiveState.DEAD;
			}
			
		}
		   
		if(!enableFall)
			return; //Sai, não será processado nenhuma queda
		   
		   if(jump == JumpState.JUMPING) {
			   
			   
			   this.MoveByY(JumpShift);
			   
			 
			   JumpShift++;
			   
			   if(JumpShift == 0) {
				  jump = JumpState.FALLING;
				   
			   }
		   } else if(jump == JumpState.FALLING) {
			   
	           this.MoveByY(JumpShift);
	           
	           JumpShift++;
	           
	           if(JumpShift > MAX_VELOCITY_FALL)
	        	   JumpShift--;
			   
			  
			   
			   //Processa todos os elementos da tela para ver se colidiu com o chao
			   for(GameElement element : scene.Elements()) {
				   
				   boolean colidiu = false;
				   
				   if( (IsCollisionElementByFall(element)) || (IsCollisionElementBySide(element)))  {
				      
					   
					   //Checa a colisao entre os objetos
					   if(Collision.Check(this, element)) {
						   
						  
						   
						   if ( (this.GetY() + this.GetHeight()) <= (element.GetY() + 15)) {
							   
							   jump = JumpState.IS_GROUND;
							   
							   
							   
							   this.SetY(element.GetY() - (this.GetHeight()));
							   
							   colidiu = true;
							   
						   }
						   
					   }
				   
				   }
				   
				   if(colidiu)
				     break;
				   
			   }
			   
			   
		   } else if(jump == JumpState.IS_GROUND) {
			   
			   for(GameElement element : scene.Elements()) {
				   
				   if( (IsCollisionElementByFall(element)) || (IsCollisionElementBySide(element)))  {
					   
					  
					   if(   
							   
							   
							   ((this.GetY() + (this.GetHeight())) == (element.GetY())) &&
								 
							    ((this.GetX() + (this.GetWidth())) >= element.GetX() ) && 
								
								((this.GetX()) <= (element.GetX() + element.GetWidth() ) ) ) {
						   
						   isGround = true;
						   
						   break;
					   }
					   
					   
				   }
				   
				   
			   }
			   
			   if(!isGround)
			   {
				   jump = JumpState.FALLING;
				   JumpShift = 0;
				  
			   }
			   
		   }

		
		
	}
	
	public boolean IsAttacking() {
		return ((action == ActionState.ATTACK1) || (action == ActionState.ATTACK2) ||
				(action == ActionState.ATTACK3) || (action == ActionState.ATTACK4) ||
				(action == ActionState.ATTACK5));
	}
	
	public boolean IsDamaged()
	{
		return (damageState == DamageState.DAMAGED);
	}
	
	public void SufferDamage(int frames) {
		countFrameDamage = 0;
		damageState = DamageState.DAMAGED;
		if(aDamaged.GetCount() > 0) {
			
		   action = ActionState.DAMAGED;	
		   aDamaged.Start(frames, false);
		
		}
	}
	
	public void SetMaxFramesDamage(int max_frames)
	{
		MAX_FRAME_DAMAGE = max_frames;
	}
	
	
	public void AddCollisionElementOfFallByTag(String tag) {
		aCollisionElementByFall_Tag.add(tag);
	}
	
	public void AddCollisionElementOfSideByTag(String tag) {
		aCollisionElementBySide_Tag.add(tag);
	}
	
	
	public void AddCollisionElementOfFallByType(String type) {
		aCollisionElementByFall_Type.add(type);
	}
	
	public void AddCollisionElementOfSideByType(String type) {
		aCollisionElementBySide_Type.add(type);
	}
	
	
	private boolean IsCollisionElementByFall(GameElement element) {
		
		boolean isElement = false;
		
		for(String type : aCollisionElementByFall_Type)
		{
			
			if(element.getClass().getSimpleName().trim().equals(type.trim())) {
				isElement = true;
			    break;
			}
		}
			
		if(isElement)
			return true;
		else
		
		{
			
			for(String tag : aCollisionElementByFall_Tag)
			{
				if(element.GetTag() == tag) {
					isElement = true;
					
				    break;	
				}
			}
			
			if(isElement)
				return true;
							
		}
		
		return isElement;
		
	}
	
	
  private boolean IsCollisionElementBySide(GameElement element) {
		
		boolean isElement = false;
		
		for(String type : aCollisionElementBySide_Type)
		{
			
			if(element.getClass().getSimpleName().trim().equals(type.trim()))
				isElement = true;
		}
			
		if(isElement)
			return true;
		else
		
		{
			
			for(String tag : aCollisionElementBySide_Tag)
			{
				if(element.GetTag() == tag)
					isElement = true;
			}
			
			if(isElement)
				return true;
			
				
		}
		
		return isElement;
		
	}
	
    
  public void MoveByX(int value) {
	 
	 
	 super.MoveByX(value);
	 
	 for(AnimationSprites a : animationList)
		 a.MoveByX(value);
	 
  }
 
  public void MoveByY(int value) {
	 
	 
	 super.MoveByY(value);
	 
	 for(AnimationSprites a : animationList)
		 a.MoveByY(value);
	 
  }
  
   public void SetX(int value) {
		 
		 
		 super.SetX(value);
		 
		 for(AnimationSprites a : animationList)
			 a.SetX(value);
		 
   }
   
   public void SetY(int value) {
		 
		 
		 super.SetY(value);
		 
		 for(AnimationSprites a : animationList)
			 a.SetY(value);
		 
   }
   
   public void SetWidth(int value) {
		 
		 
		 super.SetWidth(value);
		 
		 for(AnimationSprites a : animationList)
			 a.SetWidth(value);
		 
  }
   
   public void SetHeight(int value) {
		 
		 
		 super.SetHeight(value);
		 
		 for(AnimationSprites a : animationList)
			 a.SetHeight(value);
		 
  }
   
   
   public boolean CollisionBySide(Scene scene)
   {
	   boolean anyCollision = false;
	   
	   for(GameElement e : scene.Elements())
	   {
		   if(IsCollisionElementBySide(e))
		   {
			 if(Collision.Check(this, e))
			 {
			   anyCollision = true;
			   break;
			 }
		   }
	   }
	   
	   return anyCollision;
   }

   public void SetEnableFall(boolean fall)
   {
	   enableFall = fall;
   }
   
   public void TurnToLeft()
   {
	   direction = DirectionState.LEFT;
   }
   
   public void TurnToRight()
   {
	   direction = DirectionState.RIGHT;
   }

}
