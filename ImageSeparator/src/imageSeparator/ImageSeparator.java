package imageSeparator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class ImageSeparator {

	private String filePath;
	private BufferedImage image;
	private ArrayList<BufferedImage> outputImage;
	
	private ArrayList<Integer> guidePointX;
	private ArrayList<Integer> guidePointY;
	
	private int leftMargin = 8;
	private int rightMargin = 8;
	private int upMargin = 24;
	private int downMargin = 0;
	
	/*
	 * A guide pixel should be in the upper right corner of the picture
	 * The pixel for each character should be below them. And in the middle on the horizontal axis
	 */
	public ImageSeparator() {
		
		guidePointX = new ArrayList<Integer>();
		guidePointY = new ArrayList<Integer>();
		outputImage = new ArrayList<BufferedImage>();
		
		leftMargin = Integer.parseInt(JOptionPane.showInputDialog("LeftMargin, remeber that there is a pixel more for the actual guide pixel"));
		rightMargin = Integer.parseInt(JOptionPane.showInputDialog("RightMargin"));
		upMargin = Integer.parseInt(JOptionPane.showInputDialog("UpMargin"));
		downMargin = Integer.parseInt(JOptionPane.showInputDialog("DownMargin"));
		
		filePath = JOptionPane.showInputDialog("Please enter filePath");
		//JFileChooser chooser = new JFileChooser();
		//chooser.showOpenDialog(null);
		
		try {
			image = ImageIO.read(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int pointColor = image.getRGB(0, 0);
		
		for(int y = 0; y < image.getHeight(); y++) {
			for(int x = 0; x < image.getWidth(); x++) {
				if(x != 0 && y != 0) {
					if( image.getRGB(x, y) == pointColor ) {
						guidePointX.add(x);
						guidePointY.add(y);
					}
				}
			}
		}
		
		for(int i = 0; i < guidePointX.size(); i++) {
			try {
				outputImage.add(image.getSubimage(guidePointX.get(i) - leftMargin, guidePointY.get(i) - upMargin, leftMargin + 1 + rightMargin, upMargin + 1 + downMargin));
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Part of image out of picture");
			}
		}
		
		//File f = new File(JOptionPane.showInputDialog("Enter name for file to save") + ".png");
		
		for(int i = 0; i < outputImage.size(); i++) {
			try {
				ImageIO.write(outputImage.get(i), "PNG", new File(String.format("%02d", i) + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("pictures ripped");
		
	}
	
}
