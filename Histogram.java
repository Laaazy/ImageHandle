package ImageHandle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JTextField;
import javax.swing.JSlider;

public class Histogram {

	public static JFrame frame;
	public static JSlider slider;
	public static ChangeListener listener=null;
	public static JLabel histLabel,average,mid,deviation,pixSum,div,binaryPic;
	private JPanel contentPane;
	public static int size=300;//直方图尺寸
	private JLabel label;
	private JLabel lebel1;
	
	public static BufferedImage drawHist(int []hist,int pixSum) {
		int size = 300;
		BufferedImage pic = new BufferedImage(size,size, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2d = pic.createGraphics();  
	    g2d.setPaint(Color.BLACK);  
	    g2d.fillRect(0, 0, size, size);  
	    g2d.setPaint(Color.WHITE);  
        g2d.drawLine(30, 280, 290, 280); //横轴，长度为250
        g2d.drawLine(290, 280, 285, 277);
        g2d.drawLine(290, 280, 285, 283);
        g2d.drawString("灰度级  255", 220, 295);//横轴最右
        g2d.drawString("0", 27, 280+13);//原点
	    g2d.drawLine(30, 280, 30, 30);//纵轴，长度为250 	
	    g2d.drawLine(30, 30, 27, 35);
	    g2d.drawLine(30, 30, 33, 35);
	    g2d.drawString("出现频率", 27, 28);//纵轴最高点
	    //g2d.drawString("出现频率", 27, 15);//纵轴最高点
	           
	    g2d.setPaint(Color.GREEN);  
	    int max = 0;//记录最大灰度级
	    for ( int i = 0; i<256; i++ )
        {
        	if( hist[i] > max ) max = hist[i];
        }
          
        int offset = 1;  
        for(int i=0; i<hist.length; i++) {  
        	int lineLength = (int)(250*hist[i]/(double)max);//灰度直方图中的线长度  
            g2d.drawLine(30 + offset + i, 280, 30 + offset + i, 280-lineLength);  
        } 
        //g2d.drawString("最大灰度级:"+String.valueOf(max), 130, 295); 
           
      //  g2d.setPaint(Color.RED);  
       // g2d.drawString("", 100, 270); 
        return pic;
	}
	
	public Histogram() {
		frame=new JFrame();
		frame.setBounds(532, 60, 600, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		histLabel = new JLabel("\u7070\u5EA6\u76F4\u65B9\u56FE\u5C06\u5728\u8FD9\u91CC\u663E\u793A");
		histLabel.setBackground(Color.WHITE);
		histLabel.setForeground(Color.BLACK);
		histLabel.setFont(new Font("黑体", Font.PLAIN, 13));
		histLabel.setHorizontalAlignment(SwingConstants.CENTER);
		histLabel.setBounds(30, 30, 300, 300);
		contentPane.add(histLabel);
		
		average = new JLabel("\u5E73\u5747\u7070\u5EA6\uFF1A");
		average.setBounds(361, 30, 100, 25);
		contentPane.add(average);
		
		mid = new JLabel("\u4E2D\u503C\u7070\u5EA6\uFF1A");
		mid.setBounds(361, 65, 100, 25);
		contentPane.add(mid);
		
		deviation = new JLabel("\u7070\u5EA6\u6807\u51C6\u5DEE\uFF1A");
		deviation.setBounds(361, 100, 130, 25);
		contentPane.add(deviation);
		
		pixSum = new JLabel("\u50CF\u7D20\u603B\u6570\uFF1A");
		pixSum.setBounds(361, 135, 120, 25);
		contentPane.add(pixSum);
		
		div = new JLabel("\u9608\u503C\u5927\u5C0F\uFF1A");
		div.setBounds(361, 172, 100, 25);
		contentPane.add(div);
		
		slider = new JSlider(1,255);
		slider.setBounds(361, 242, 150, 26);
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(20);
		slider.setMinorTickSpacing(10);
		slider.setValue(128);
		contentPane.add(slider);
		
		binaryPic = new JLabel("\u9608\u503C\u5316\u7684\u56FE\u50CF\u5C06\u5728\u8FD9\u91CC\u663E\u793A");
		binaryPic.setHorizontalAlignment(SwingConstants.CENTER);
		binaryPic.setBounds(30, 375, 300, 160);
		contentPane.add(binaryPic);
		
		label = new JLabel("\u8C03\u8282\u9608\u503C\uFF1A");
		label.setBounds(361, 207, 100, 25);
		contentPane.add(label);
		
		lebel1 = new JLabel("\u7070\u5EA6\u76F4\u65B9\u56FE");
		lebel1.setHorizontalAlignment(SwingConstants.CENTER);
		lebel1.setBounds(127, 345, 100, 25);
		contentPane.add(lebel1);
		
		frame.setVisible(true);
		
		
		
		
	}
}
