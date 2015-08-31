package imageStitcher;

import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class ImageStitcher {

	private String filePath;
	private int noPictures;
	private ArrayList<BufferedImage> images;
	
	private BufferedImage outputImage;
	
	public ImageStitcher() {
		
		filePath = JOptionPane.showInputDialog("Enter the filepath of the directory");
		
		String[] command = {"powershell.exe", "(get-childitem -filter *.png -path " + filePath + ").count"};
		
		ArrayList<String> psOutput = PowershellJava.powershell(command);
		System.out.println("This is a test");
		
		System.out.println("psOutput.get(0): " + psOutput.get(0));
		System.out.println("test2: " + Integer.parseInt("5"));
		
		//Integer.parseInt(s)
		
		try {
			noPictures = Integer.parseInt(psOutput.get(0));
			//noPictures = psOutput.get(0);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("noPictures: " + noPictures);
		
		
		String[] getNames = {"powershell.exe", "get-childitem -name -filter *.png -path " + filePath };
		
		
		ArrayList<String> names = PowershellJava.powershell(getNames);
		
		System.out.println("test1");
		
		images = new ArrayList<BufferedImage>();
		
		for(int i = 0; i < noPictures; i++) {
			try {
				String fileName = filePath + "\\" + names.get(i);
				System.out.println(fileName);
				BufferedImage tempImage = ImageIO.read(new File(fileName));
				images.add(tempImage);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Powershell got wrong names of files");
			}
		}
		
		outputImage = new BufferedImage(images.get(0).getWidth() * noPictures, images.get(0).getHeight(), BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g2dImage = outputImage.createGraphics();
		for(int i = 0; i < noPictures; i++) {
			g2dImage.drawImage(images.get(i), i * images.get(0).getWidth(), 0, null);
		}
		
		try {
			ImageIO.write(outputImage, "png", new File(filePath + "\\" + JOptionPane.showInputDialog("Please enter a file name") + ".png"));
		} catch (HeadlessException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
}

