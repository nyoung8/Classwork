/**
 * @author Nathan Young
 * Ch 02 Episteme
 * E2.16
 */
package edu.westmont.cs030;
public class HalfSizePicture {
	/**
	 * The method takes a picture from online of a twisting spiral and scales the picture down by one half and moves it to the center.
	 */
	public static void main(String[] args) {
		Picture pic = new Picture();
	      pic.load("http://www.op-art.co.uk/op-art-gallery/var/albums/your-op-art/twisting-spirals.jpg?m=1342043651");
	      System.out.println(pic.getWidth());
	      pic.scale(pic.getWidth()/2, pic.getHeight()/2);
	      System.out.println(pic.getWidth());

	      pic.move(pic.getWidth()/4, pic.getHeight()/4);
	      System.out.println(pic.getWidth());

	}

}
