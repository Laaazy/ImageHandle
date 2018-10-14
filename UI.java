package ImageHandle;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;


import jdk.dynalink.beans.StaticClass;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Canvas;
import java.awt.Color;

public class UI extends JFrame {
	
	public static final int H_WIDTH=350;
	public static final int H_HEIGHT=300;
	public static final int size=300;//直方图区域大小
	
	static JFileChooser chooser=new JFileChooser();
	static File file=null;
	static BufferedImage image=null;
	static BufferedImage temp=null;
	static ChangeListener sListener=null,qListener=null;
	static int sValue,qValue;
	private JPanel contentPane;
	//static int lastSlide=0;//滑动采用率为0，滑动量化等级为1
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI frame = new UI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1300, 1000);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel s = new JLabel("\u91C7\u6837\u95F4\u9694");
		s.setBounds(30, 48, 54, 15);
		contentPane.add(s);
		
		JLabel q = new JLabel("\u91CF\u5316\u7B49\u7EA7");
		q.setBounds(30, 75, 54, 15);
		contentPane.add(q);
		
		
		JLabel picture = new JLabel("\u56FE\u7247\u5C06\u4F1A\u5728\u8FD9\u91CC\u663E\u793A");
		picture.setFont(new Font("黑体", Font.PLAIN, 14));
		picture.setHorizontalAlignment(SwingConstants.CENTER);
		picture.setBounds(10, 110, 512, 512);
		contentPane.add(picture);
		
		JLabel sample = new JLabel("\u91C7\u6837\u95F4\u9694\uFF1A");
		sample.setFont(new Font("黑体", Font.PLAIN, 14));
		sample.setBounds(10, 640, 110, 25);
		contentPane.add(sample);
		
		JLabel quantization = new JLabel("\u91CF\u5316\u7B49\u7EA7\uFF1A\r\n");
		quantization.setFont(new Font("黑体", Font.PLAIN, 14));
		quantization.setBounds(10, 670, 110, 25);
		contentPane.add(quantization);
		
		//打开图片按钮
		JButton open = new JButton("\u6253\u5F00\u56FE\u7247");
		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				chooser.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPEG","jpg", "gif","bmp");//只打开bmp文件
				chooser.setFileFilter(filter);
				chooser.showOpenDialog(null);
				file=chooser.getSelectedFile();
				try {
					image=ImageIO.read(file);
					temp=ImageIO.read(file);
					picture.setIcon(new ImageIcon(image));
					System.out.println("打开文件成功");
				}catch (IOException e) {
					System.err.println("打开文件失败");
				}
			}
		});
		open.setFont(new Font("黑体", Font.PLAIN, 13));
		open.setBounds(10, 10, 90, 25);
		contentPane.add(open);
		
		JButton save = new JButton("\u4FDD\u5B58\u66F4\u6539");
		save.setFont(new Font("黑体", Font.PLAIN, 13));
		save.setBounds(103, 10, 90, 25);
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					chooser.showSaveDialog(null);
					String path=file.toString();
					String fileType=path.substring(path.lastIndexOf("."));
					File file2=new File(chooser.getSelectedFile().getPath());
					ImageIO.write(temp, "jpg", file2);
					System.out.println("保存文件成功");
				}catch (IOException e) {
					System.err.println("保存文件失败");
				}
			}
		});
		contentPane.add(save);
		
		JButton bitPlane = new JButton("\u5206\u5272\u4F4D\u9762\u56FE");
		bitPlane.setFont(new Font("黑体", Font.PLAIN, 13));
		bitPlane.setBounds(196, 10, 102, 25);
		bitPlane.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					BitMap.bitPlane(temp);
					System.out.println("分割位面图成功");
				}catch (IOException e) {
					System.err.println("分割位面图失败");
				}
			}
		});
		contentPane.add(bitPlane);
		
		JSlider sampleSlider = new JSlider(1,512);
		JSlider quantizationSlider = new JSlider(1,256);
		
		//采样率滑动条的监听器
				sListener=new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent e) {
						JSlider slider=(JSlider)e.getSource();
						sValue=slider.getValue();//采样率滑动条的值
						qValue=quantizationSlider.getValue();
						sample.setText("采样间隔："+sValue);
						temp=BitMap.quantization(image, qValue);//图片亮度遵从量化等级
						temp=BitMap.sample(temp, sValue);//图片内容遵从采样率
						picture.setIcon(new ImageIcon(temp));
					}
				};
				
				
		//量化等级滑动条的监听器
				qListener=new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent e) {
						JSlider slider=(JSlider)e.getSource();
						sValue=sampleSlider.getValue();
						qValue=slider.getValue();//采样率滑动条的值
						quantization.setText("量化等级："+qValue);
						temp=BitMap.sample(image, sValue);//图片内容遵从采样率
						temp=BitMap.quantization(temp, qValue);//图片亮度遵从量化等级
						picture.setIcon(new ImageIcon(temp));
					}
				};
		
		//采样率滑动条
		sampleSlider.setBounds(83, 45, 200, 26);
		sampleSlider.setMajorTickSpacing(10);
		//sampleSlider.setMinorTickSpacing(5);
		sampleSlider.setPaintTicks(true);
		sampleSlider.setValue(1);
		sampleSlider.setSnapToTicks(true);
		sampleSlider.addChangeListener(sListener);
		contentPane.add(sampleSlider);
		
		//量化等级滑动条
		
		quantizationSlider.setBounds(83, 70, 200, 26);
		quantizationSlider.setMajorTickSpacing(10);
		//quantizationSlider.setMinorTickSpacing(5);
		quantizationSlider.setPaintTicks(true);
		quantizationSlider.setValue(256);
		quantizationSlider.setSnapToTicks(true);
		quantizationSlider.addChangeListener(qListener);
		contentPane.add(quantizationSlider);
		
		JButton restart = new JButton("\u91CD\u65B0\u5F00\u59CB");
		restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame resrt=new UI();
				resrt.setVisible(true);
			}
		});
		restart.setBounds(302, 10, 93, 23);
		contentPane.add(restart);
		
		
		JButton showHist = new JButton("\u663E\u793A\u76F4\u65B9\u56FE");
		showHist.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(image==null)
					System.out.println("请先打开图片");
				else{
					Histogram his=new Histogram();
					int iw=image.getWidth();
					int ih=image.getHeight();
					int hist[]=new int[iw*ih];
					hist=BitMap.getHist(temp);
				
				
					int average=0;
					int mid=-1;
					int sum=iw*ih;
					double deviation=0;
					int n=0;//n用于寻找中位数
				
					for(int i=0;i<hist.length;i++){
						average+=(double)i*hist[i];
						if(n<sum/2)
							n+=hist[i];
						else {
							if(mid==-1)
								mid=i;
						}
					}
					average/=(double)sum;
					his.average.setText("平均灰度："+String.valueOf(average));
					his.mid.setText("中值灰度："+String.valueOf(mid));
					his.pixSum.setText("像素总数："+String.valueOf(sum));
					
					for ( int i=0; i<hist.length; i++ )
					{
						deviation += (double) (i-average)*(i-average)*hist[i]/(double)sum;
					}
					deviation=Math.sqrt(deviation);
					his.deviation.setText("灰度标准差："+String.valueOf(deviation));
					
					ColorModel cm = ColorModel.getRGBdefault();
					 
					int pix[] = new int[iw*ih];
					image.getRGB( 0, 0, iw, ih, pix, 0, iw );
					BufferedImage binary = new BufferedImage( iw,ih,BufferedImage.TYPE_BYTE_GRAY );
					
					//图像二值化
					his.listener = new ChangeListener() {
						@Override
						public void stateChanged(ChangeEvent e) {
							JSlider slider = (JSlider) e.getSource();
							his.div.setText( "阈值大小："+ String.valueOf( slider.getValue() ));
							int[] temp = new int[iw*ih];
							
							for ( int i=0; i< iw*ih; i++ )
							{
								int h = cm.getBlue(pix[i]);
								if (h >  slider.getValue() )
								{
									temp[i] = new Color(255,255,255).getRGB();
								}
								else temp[i] = new Color(0,0,0).getRGB();
							}
							binary.setRGB( 0, 0, iw, ih, temp, 0, iw );
							his.binaryPic.setIcon(new ImageIcon(binary) );
							//System.out.println(N);
						}	
					};
					his.slider.addChangeListener(his.listener);
					
					his.histLabel.setIcon( new ImageIcon(Histogram.drawHist(hist,sum)) );
				}
			}
		});
		showHist.setBounds(399, 10, 100, 23);
		contentPane.add(showHist);
		
		
		/*BufferedImage pic=new BufferedImage(size, size, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2d=pic.createGraphics();
		g2d.setPaint(Color.BLACK);  
        g2d.fillRect(0, 0, size, size);  
        g2d.setPaint(Color.RED);  
        g2d.drawLine(525, 400, 625, 400); 
        g2d.drawString("0", 0, 250+10);
        g2d.drawLine(5, 250, 5, 5); 	 	
        g2d.drawString("255", 265, 260);
        histLabel.setIcon(new ImageIcon());*/
	}
}
