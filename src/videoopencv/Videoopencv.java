package videoopencv;

import java.awt.image.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Videoopencv{	
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    static Mat imag = null;
    
    
    public static BufferedImage Mat2BufferedImage(Mat m){
    	

    	    int type = BufferedImage.TYPE_BYTE_GRAY;
    	    if ( m.channels() > 1 ) {
    	        type = BufferedImage.TYPE_3BYTE_BGR;
    	    }
    	    int bufferSize = m.channels()*m.cols()*m.rows();
    	    byte [] b = new byte[bufferSize];
    	    m.get(0,0,b); // get all the pixels
    	    BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
    	    final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
    	    System.arraycopy(b, 0, targetPixels, 0, b.length);  
    	    return image;

    	}
    
    public static void main(String[] args) throws IOException {
    	JFrame jframe = new JFrame("Teclado Virtual");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel vidpanel = new JLabel();
        jframe.setContentPane(vidpanel);
        jframe.setLocationByPlatform(true);
        jframe.setSize(680, 560);
        jframe.setVisible(true);
        											//Caminho da img
        BufferedImage img=ImageIO.read(new File("C:/Users/Miguel/Documents/PSW/Teclado Itens/Teclado2.png"));
        
        ImageIcon icon=new ImageIcon(img);
       
   
        jframe.setLayout(new FlowLayout());         
        JLabel lbl=new JLabel();
        
        lbl.setIcon(icon);
        lbl.setBorder( new EmptyBorder(-150, 90, 410, 90) );//(int top, int left, int bottom, int right
        //jframe.add(lbl, BorderLayout.CENTER);
        jframe.add(lbl);
        
        
        
        
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

     
        
        Mat frame = new Mat();
        Mat outerBox = new Mat();
        Mat diff_frame = null;
        Mat tempon_frame = null;
        ArrayList<Rect> array = new ArrayList<Rect>();
        VideoCapture camera = new VideoCapture(0);
        
        Size sz = new Size(680, 560);
        
        
        
        int i = 0;
        boolean s=false;
        AudioStream as=null;
        
       while (true) {
            InputStream in = new FileInputStream("0.wav");
            InputStream in1 = new FileInputStream("1.wav");
            InputStream in2 = new FileInputStream("2.wav");
            InputStream in3 = new FileInputStream("3.wav");
            InputStream in4 = new FileInputStream("4.wav");
            InputStream in5 = new FileInputStream("5.wav");
            InputStream in6 = new FileInputStream("6.wav");
            InputStream in7 = new FileInputStream("7.wav");


            if (camera.read(frame)) {
                Imgproc.resize(frame, frame, sz);
                imag = frame.clone();
                outerBox = new Mat(frame.size(), CvType.CV_8UC1);
                Imgproc.cvtColor(frame, outerBox, Imgproc.COLOR_BGR2GRAY);
                Imgproc.GaussianBlur(outerBox, outerBox, new Size(3, 3), 0);
          
                if (i == 0) {
                    jframe.setSize(frame.width(), frame.height());
                    diff_frame = new Mat(outerBox.size(), CvType.CV_8UC1);
                    tempon_frame = new Mat(outerBox.size(), CvType.CV_8UC1);
                    diff_frame = outerBox.clone();
                }
 
                if (i == 1) {
                    Core.subtract(outerBox, tempon_frame, diff_frame);
                    Imgproc.adaptiveThreshold(diff_frame, diff_frame, 255,
                            Imgproc.ADAPTIVE_THRESH_MEAN_C,
                            Imgproc.THRESH_BINARY_INV, 5, 2);
                    array = detection_contours(diff_frame);
                    if (array.size() > 0) {

                        Iterator<Rect> it2 = array.iterator();
                        while (it2.hasNext()) {
                            Rect obj = it2.next();
                            if (obj.x > 90 && obj.x<=152.5 && obj.y>0 && obj.y<150){
                              if(s==false){
                            	  as = new AudioStream(in);  
                                  AudioPlayer.player.start(as);
                                  BufferedImage img2=ImageIO.read(new File("C:/Users/Miguel/Documents/PSW/Teclado Itens/0_c.png"));
                                  
                                  ImageIcon icon2=new ImageIcon(img2);
                                 
                             
                                  jframe.setLayout(new FlowLayout());         
                                  
                                  lbl.setIcon(icon2);
                                  lbl.setBorder( new EmptyBorder(-150, 90, 410, 90) );//(int top, int left, int bottom, int right
                                  //jframe.add(lbl, BorderLayout.CENTER);
                                  jframe.add(lbl);
                                  jframe.setVisible(true);
                                  jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                  System.out.println("Played C");
                                  s=true;
                                  
                              }
                            }
                            else if (obj.x > 152.5 && obj.x<=215 && obj.y>0 && obj.y<150){
                                if(s==false){
                              	  as = new AudioStream(in1);  
                                    AudioPlayer.player.start(as);
                                    
                                    BufferedImage img3=ImageIO.read(new File("C:/Users/Miguel/Documents/PSW/Teclado Itens/1_d.png"));
                                    
                                    ImageIcon icon3=new ImageIcon(img3);
                                   
                               
                                    jframe.setLayout(new FlowLayout());         
                                    
                                    lbl.setIcon(icon3);
                                    lbl.setBorder( new EmptyBorder(-150, 90, 410, 90) );//(int top, int left, int bottom, int right
                                    //jframe.add(lbl, BorderLayout.CENTER);
                                    jframe.add(lbl);
                                    jframe.setVisible(true);
                                    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                    System.out.println("Played D");
                                    s=true;
                                }
                              }
                            else if (obj.x > 215 && obj.x<=277.5 && obj.y>0 && obj.y<150){
                                if(s==false){
                              	  as = new AudioStream(in2);  
                                    AudioPlayer.player.start(as);

                                    BufferedImage img4=ImageIO.read(new File("C:/Users/Miguel/Documents/PSW/Teclado Itens/2_e.png"));
                                    
                                    ImageIcon icon4=new ImageIcon(img4);
                                   
                               
                                    jframe.setLayout(new FlowLayout());         
                                    
                                    lbl.setIcon(icon4);
                                    lbl.setBorder( new EmptyBorder(-150, 90, 410, 90) );//(int top, int left, int bottom, int right
                                    //jframe.add(lbl, BorderLayout.CENTER);
                                    jframe.add(lbl);
                                    jframe.setVisible(true);
                                    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                    System.out.println("Played E");
                                    s=true;
                                }
                              }
                            else if (obj.x > 277.5 && obj.x<=340 && obj.y>0 && obj.y<150){
                                if(s==false){
                              	  as = new AudioStream(in3);  
                                    AudioPlayer.player.start(as);
                                    
                                    BufferedImage img5=ImageIO.read(new File("C:/Users/Miguel/Documents/PSW/Teclado Itens/3_f.png"));
                                    
                                    ImageIcon icon5=new ImageIcon(img5);
                                   
                               
                                    jframe.setLayout(new FlowLayout());         
                                    
                                    lbl.setIcon(icon5);
                                    lbl.setBorder( new EmptyBorder(-150, 90, 410, 90) );//(int top, int left, int bottom, int right
                                    //jframe.add(lbl, BorderLayout.CENTER);
                                    jframe.add(lbl);
                                    jframe.setVisible(true);
                                    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                    System.out.println("Played F");
                                    s=true;
                                }
                              }
                            else if (obj.x > 340 && obj.x<=402.5 && obj.y>0 && obj.y<150){
                                if(s==false){
                              	  as = new AudioStream(in4);  
                                    AudioPlayer.player.start(as);
                                    
                                    BufferedImage img6=ImageIO.read(new File("C:/Users/Miguel/Documents/PSW/Teclado Itens/4_g.png"));
                                    
                                    ImageIcon icon6=new ImageIcon(img6);
                                   
                               
                                    jframe.setLayout(new FlowLayout());         
                                    
                                    lbl.setIcon(icon6);
                                    lbl.setBorder( new EmptyBorder(-150, 90, 410, 90) );//(int top, int left, int bottom, int right
                                    //jframe.add(lbl, BorderLayout.CENTER);
                                    jframe.add(lbl);
                                    jframe.setVisible(true);
                                    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                    System.out.println("Played G");
                                    s=true;
                                }
                              }
                            else if (obj.x > 402.5 && obj.x<=465 && obj.y>0 && obj.y<150){
                                if(s==false){
                              	  as = new AudioStream(in5);  
                                    AudioPlayer.player.start(as);
                                    
                                    BufferedImage img7=ImageIO.read(new File("C:/Users/Miguel/Documents/PSW/Teclado Itens/5_a.png"));
                                    
                                    ImageIcon icon7=new ImageIcon(img7);
                                   
                               
                                    jframe.setLayout(new FlowLayout());         
                                    
                                    lbl.setIcon(icon7);
                                    lbl.setBorder( new EmptyBorder(-150, 90, 410, 90) );//(int top, int left, int bottom, int right
                                    //jframe.add(lbl, BorderLayout.CENTER);
                                    jframe.add(lbl);
                                    jframe.setVisible(true);
                                    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                    System.out.println("Played A");
                                    s=true;
                                }
                              }
                            else if (obj.x > 465 && obj.x<=527.5 && obj.y>0 && obj.y<150){
                                if(s==false){
                              	  as = new AudioStream(in6);  
                                    AudioPlayer.player.start(as);
                                    
                                    BufferedImage img8=ImageIO.read(new File("C:/Users/Miguel/Documents/PSW/Teclado Itens/6_b.png"));
                                    
                                    ImageIcon icon8=new ImageIcon(img8);
                                   
                               
                                    jframe.setLayout(new FlowLayout());         
                                    
                                    lbl.setIcon(icon8);
                                    lbl.setBorder( new EmptyBorder(-150, 90, 410, 90) );//(int top, int left, int bottom, int right
                                    //jframe.add(lbl, BorderLayout.CENTER);
                                    jframe.add(lbl);
                                    jframe.setVisible(true);
                                    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                    System.out.println("Played B");
                                    s=true;
                                }
                              }
                            else if (obj.x > 527.5 && obj.x<=590 && obj.y>0 && obj.y<150){
                                if(s==false){
                              	  as = new AudioStream(in7);  
                                    AudioPlayer.player.start(as);
                                    
                                    BufferedImage img9=ImageIO.read(new File("C:/Users/Miguel/Documents/PSW/Teclado Itens/7_c2.png"));
                                    
                                    ImageIcon icon9=new ImageIcon(img9);
                                   
                               
                                    jframe.setLayout(new FlowLayout());         
                                    
                                    lbl.setIcon(icon9);
                                    lbl.setBorder( new EmptyBorder(-150, 90, 410, 90) );//(int top, int left, int bottom, int right
                                    //jframe.add(lbl, BorderLayout.CENTER);
                                    jframe.add(lbl);
                                    jframe.setVisible(true);
                                    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                    System.out.println("Played C2");
                                    s=true;
                                }
                              }
                        }
                    }
                    else
                    {
                        if(s==true)
                        {
                        System.out.println("Stopped");
	                    AudioPlayer.player.stop(as);
	                    lbl.setIcon(icon);
	                    lbl.setBorder( new EmptyBorder(-150, 90, 410, 90) );//(int top, int left, int bottom, int right
                        //jframe.add(lbl, BorderLayout.CENTER);
                        jframe.add(lbl);
                        jframe.setVisible(true);
                        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	                    as=null;
	                    s=false;
                        }
                    }
                }
 
                i = 1;
 
               ImageIcon image = new ImageIcon(Mat2BufferedImage(imag));
                vidpanel.setIcon(image);
                vidpanel.repaint();
                tempon_frame = outerBox.clone();
 
            }
        }
    }



	
    public static ArrayList<Rect> detection_contours(Mat outmat) {
        Mat v = new Mat();
        Mat vv = outmat.clone();
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Imgproc.findContours(vv, contours, v, Imgproc.RETR_LIST,
                Imgproc.CHAIN_APPROX_SIMPLE);
 
        double maxArea = 100;
        int maxAreaIdx = -1;
        Rect r = null;
        ArrayList<Rect> rect_array = new ArrayList<Rect>();
 
        for (int idx = 0; idx < contours.size(); idx++) { Mat contour = contours.get(idx); double contourarea = Imgproc.contourArea(contour); if (contourarea > maxArea) {
                // maxArea = contourarea;
                maxAreaIdx = idx;
                r = Imgproc.boundingRect(contours.get(maxAreaIdx));
                rect_array.add(r);
            }
 
        }
 
        v.release();
 
        return rect_array;
 
    }
    
}