/**
Copyright (c) 2012 Babin Philippe
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.*/

package lazarus;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;


public class MachineGun extends Item {
	
	private int lastBullet = 0;
	
	
	MachineGun( String name){
		super( name);
		icon = RessourceManager.arms.getImage( 0);
		arms = RessourceManager.arms;
	}
	
	/**
	 * Item update function
	 * @param gc GameContainer
	 * @param sb StateBasedGame
	 * @param delta Time between frame
	 */
    public void update( GameContainer gc, StateBasedGame sb, int delta){
    	Input input = gc.getInput();
    	Vector2f pCursor = new Vector2f( input.getMouseX() + playstate.getCam().x , input.getMouseY() +  playstate.getCam().y);
    	
    	Vector2f pPlayer = playstate.getPlayer().getPosition();

    	lastBullet += delta;
		if( input.isMouseButtonDown( Input.MOUSE_LEFT_BUTTON) && lastBullet >= 200){
			Vector2f bG = new Vector2f(  pCursor.x - pPlayer.x - CONST.PLAYER_WIDTH / 2, pCursor.y - pPlayer.y);
			double h = Math.sqrt( bG.x * bG.x + bG.y * bG.y);

			Vector2f bP = new Vector2f( pPlayer.x + CONST.PLAYER_WIDTH / 2 - 2, pPlayer.y);
			Vector2f bV = new Vector2f(
					(float) ( bG.x / h) * CONST.BULLET_VELOCITY,
					(float) ( bG.y / h) * CONST.BULLET_VELOCITY);
			Vector2f bP2 = null;
			

			if( pCursor.x > pPlayer.x + CONST.PLAYER_WIDTH / 2)
				bP2 = new Vector2f( bP.x - 5 * bG.y / bG.length(), bP.y + 5 * bG.x / bG.length());
			else
				bP2 = new Vector2f( bP.x + 8 * bG.y / bG.length(), bP.y - 8 * bG.x / bG.length());
			
			playstate.addEntity( new Bullet( RessourceManager.bullet, bP2, bV));
			
			lastBullet = 0;
		}
    }
}
