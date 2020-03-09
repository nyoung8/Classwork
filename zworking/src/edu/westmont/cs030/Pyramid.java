/**
 * Nathan Young
 * Episteme 6
 * Pyramid.java
 * 2/28/2019
 */

package edu.westmont.cs030;
import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;

public class Pyramid extends Drawable{
	
	/**
	 * This is a helper method that will construct a box
	 * @param texture the path name to the asset that wraps the box
	 * @param length the length of the box
	 * @param height the height of the box
	 * @param width the width of the box
	 * @param x the x position of the box
	 * @param y the y position of the box
	 * @param z the z position of the box
	 * @return a Node object representing the box that has been constructed containing the box
	 */
	private Node makeBox(String texture,
			float length, float height, float width,
			float x, float y, float z) {	
		
		// The variable to return
		Node returnNode = new Node();
		
		/* Create a new material for the side of the box*/
		Material boxMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		TextureKey key = new TextureKey(texture);
		key.setGenerateMips(true);
		Texture tex = assetManager.loadTexture(key);
		tex.setWrap(WrapMode.EdgeClamp);
		boxMaterial.setTexture("ColorMap", tex);
		
		/* Create box  */
		Box box = new Box(length/2.0f,height/2.0f,width/2.0f); /* Give it a size */	
		box.scaleTextureCoordinates(new Vector2f(1, 1)); /*Scale the texture */
		Geometry boxGeometry = new Geometry("Box", box);
		boxGeometry.setMaterial(boxMaterial);
		boxGeometry.setShadowMode(ShadowMode.CastAndReceive); /* Set up shadows */
		returnNode.attachChild(boxGeometry);
		returnNode.setLocalTranslation(x, y, z); /* Move it to the right place */
		return returnNode;
	}

	@Override
	/**
	 * Creates a pyramid constructed of boxes, made from an 12x12 base
	 */
	public Spatial draw() {
		
		float spaceBetween=1.5f; //the space between boxes

		//Creates a Node containing all of the boxes to be returned
		//and a starting box so that it has a global scope
		Node node = new Node();
		Node currentbox = this.makeBox("assets/box.jpg",0.0f,0.0f,0.0f,0.0f,0.0f,0.0f);
		
		//Sets the number of levels in the pyramid.
		float level=11f;
		float totalLevels=level;
		
		//Loop that sets the current level of the pyramid being constructed, with the top of the pyramid being level 1
		while (level>0) {
			float columnsDone=0f;
			float height = 0.5f+(totalLevels-level);
			
			//Nested loop that sets the current column of the pyramid being constructed on the current level
			while (columnsDone<level) {
				columnsDone++;
				float row=0f;
				
				//Nested loop that constructs the block on the current row of the current column of the current level
				while (row<level) {
					row++;
					float startPosition=-spaceBetween/2*(level-1);
					currentbox = this.makeBox("assets/box.jpg",1.0f,1.0f,1.0f,startPosition+(row-1)*(spaceBetween),height,startPosition+(columnsDone-1)*(spaceBetween));
					node.attachChild(currentbox);
				}
			}
			level--;

		}
		float yStart=1.0f;
		float yEnd=10.0f;
		for (yStart=yStart;yStart<yEnd;yStart++) {
			System.out.println("stuff");
		}
		


		

		return node;
	}

}
