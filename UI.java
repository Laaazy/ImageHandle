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
	
	public static final int WIDTH=320;//展示图片区域的宽度
	public static final int HEIGHT=320;//展示图片区域的高度
	
	public static final int H_WIDTH=350;
	public static final int H_HEIGHT=300;
	public static final int size=300;//直方图区域大小
	
	static JFileChooser chooser=new JFileChooser();
	static File file=null;
	static BufferedImage image=null;
	static BufferedImage temp=null;
	static ChangeListener sListener=null,qListener=null,divListener=null;
	
	public static JLabel average2=null;
	public static JLabel histogram2=null;
	public static JLabel mid2=null;
	public static JLabel deviation2=null;
	public static JLabel pixSum2 =null;
	public static JLabel picture2=null;
	
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
					frame.setTitle("主窗口");
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
		setBounds(0, 0, 1370, 730);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel s = new JLabel("\u91C7\u6837\u95F4\u9694");
		s.setFont(new Font("黑体", Font.PLAIN, 12));
		s.setBounds(30, 48, 54, 15);
		contentPane.add(s);
		
		JLabel q = new JLabel("\u91CF\u5316\u7B49\u7EA7");
		q.setFont(new Font("黑体", Font.PLAIN, 12));
		q.setBounds(30, 75, 54, 15);
		contentPane.add(q);
		
		
		JLabel picture1 = new JLabel("\u539F\u59CB\u56FE\u50CF\u5C06\u5728\u8FD9\u91CC\u663E\u793A");
		picture1.setFont(new Font("黑体", Font.PLAIN, 14));
		picture1.setHorizontalAlignment(SwingConstants.CENTER);
		picture1.setBounds(10, 110, WIDTH, HEIGHT);
		contentPane.add(picture1);
		
		JLabel sample = new JLabel("\u91C7\u6837\u95F4\u9694\uFF1A");
		sample.setFont(new Font("黑体", Font.PLAIN, 14));
		sample.setBounds(298, 43, 110, 25);
		contentPane.add(sample);
		
		JLabel quantization = new JLabel("\u91CF\u5316\u7B49\u7EA7\uFF1A\r\n");
		quantization.setFont(new Font("黑体", Font.PLAIN, 14));
		quantization.setBounds(298, 70, 110, 25);
		contentPane.add(quantization);
		
		picture2 = new JLabel("\u53D8\u6362\u540E\u7684\u56FE\u50CF\u5C06\u5728\u8FD9\u91CC\u663E\u793A");
		picture2.setFont(new Font("黑体", Font.PLAIN, 14));
		picture2.setHorizontalAlignment(SwingConstants.CENTER);
		picture2.setBounds(890, 110, 320, 320);
		contentPane.add(picture2);
		
		JLabel histogram1 = new JLabel("\u539F\u56FE\u7684\u76F4\u65B9\u56FE");
		histogram1.setFont(new Font("黑体", Font.PLAIN, 14));
		histogram1.setHorizontalAlignment(SwingConstants.CENTER);
		histogram1.setBounds(10, 440, 310, 240);
		//histogram1.setIcon(new ImageIcon(BitMap.drawHist(hist,sum)));
		contentPane.add(histogram1);
		
		JLabel average1 = new JLabel("\u5E73\u5747\u7070\u5EA6\uFF1A");
		average1.setBounds(330, 440, 100, 25);
		contentPane.add(average1);
		
		JLabel mid1 = new JLabel("\u4E2D\u503C\u7070\u5EA6\uFF1A");
		mid1.setBounds(330, 462, 100, 25);
		contentPane.add(mid1);
		
		JLabel deviation1 = new JLabel("\u7070\u5EA6\u6807\u51C6\u5DEE\uFF1A");
		deviation1.setBounds(330, 485, 130, 25);
		contentPane.add(deviation1);
		
		JLabel pixSum1 = new JLabel("\u50CF\u7D20\u603B\u6570\uFF1A");
		pixSum1.setBounds(330, 509, 120, 25);
		contentPane.add(pixSum1);
		
		JLabel div = new JLabel("\u56FE\u50CF\u9608\u503C\u5316  \u8C03\u8282\u9608\u503C");
		div.setFont(new Font("黑体", Font.PLAIN, 14));
		div.setBounds(426, 10, 157, 25);
		contentPane.add(div);
		
		JLabel div_Value = new JLabel("\u9608\u503C\u5927\u5C0F\uFF1A");
		div_Value.setFont(new Font("黑体", Font.PLAIN, 14));
		div_Value.setBounds(426, 70, 100, 25);
		contentPane.add(div_Value);
		
		
		histogram2 = new JLabel("\u53D8\u6362\u540E\u7684\u7070\u5EA6\u76F4\u65B9\u56FE");
		histogram2.setFont(new Font("黑体", Font.PLAIN, 14));
		histogram2.setHorizontalAlignment(SwingConstants.CENTER);
		histogram2.setBounds(890, 440, 310, 240);
		//histogram2.setIcon(new ImageIcon(BitMap.test()));
		contentPane.add(histogram2);
		
		average2 = new JLabel("\u5E73\u5747\u7070\u5EA6\uFF1A");
		average2.setBounds(1210, 440, 100, 25);
		contentPane.add(average2);
		
		mid2 = new JLabel("\u4E2D\u503C\u7070\u5EA6\uFF1A");
		mid2.setBounds(1210, 462, 100, 25);
		contentPane.add(mid2);
		
		deviation2 = new JLabel("\u7070\u5EA6\u6807\u51C6\u5DEE\uFF1A");
		deviation2.setBounds(1210, 485, 130, 25);
		contentPane.add(deviation2);
		
		pixSum2 = new JLabel("\u50CF\u7D20\u603B\u6570\uFF1A");
		pixSum2.setBounds(1210, 509, 120, 25);
		contentPane.add(pixSum2);
		
		
		//图像二值化
		divListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider) e.getSource();
				div_Value.setText( "阈值大小："+ String.valueOf( slider.getValue() ));
				int iw=image.getWidth();
				int ih=image.getHeight();
				int[] temp = new int[iw*ih];
				ColorModel cm = ColorModel.getRGBdefault();
				 
				int pix[] = new int[iw*ih];
				image.getRGB( 0, 0, iw, ih, pix, 0, iw );
				BufferedImage binary = new BufferedImage( iw,ih,BufferedImage.TYPE_BYTE_GRAY );
				
				for ( int i=0; i< iw*ih; i++ )
				{
					int h = cm.getBlue(pix[i]);
					if (h >  slider.getValue() )
					{
						temp[i] = new Color(255,255,255).getRGB();//大于为白
					}
					else temp[i] = new Color(0,0,0).getRGB();//小于为黑
				}
				binary.setRGB( 0, 0, iw, ih, temp, 0, iw );
				//显示处理后图像的直方图的标准流程
				int sum=iw*ih;
				int hist[]=new int[256];
				hist=BitMap.getHist(binary);
				BitMap.histLabel(sum, hist);
				if(hist[0]>hist[255])
					mid2.setText("中值灰度："+String.valueOf(0));
				else
					mid2.setText("中值灰度:"+String.valueOf(255));
				histogram2.setIcon( new ImageIcon(BitMap.drawHist(hist,sum)) );
				picture2.setIcon(new ImageIcon(binary.getScaledInstance(WIDTH, HEIGHT, java.awt.Image.SCALE_DEFAULT)));
				picture2.setText(null);
			}	
		};		
		
		JSlider divSlider = new JSlider(1, 255);
		divSlider.setValue(128);
		divSlider.setPaintTicks(true);
		divSlider.setMinorTickSpacing(10);
		divSlider.setMajorTickSpacing(20);
		divSlider.setBounds(418, 37, 150, 26);
		divSlider.addChangeListener(divListener);
		contentPane.add(divSlider);
		
		
		//打开图片按钮
		JButton open = new JButton("\u6253\u5F00\u56FE\u7247");
		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				chooser.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPEG","jpg", "gif","bmp");
				chooser.setFileFilter(filter);
				chooser.showOpenDialog(null);
				file=chooser.getSelectedFile();
				try {
					image=ImageIO.read(file);
					temp=ImageIO.read(file);
					temp=BitMap.toGray(image);
					image=BitMap.toGray(image);//转化为灰度图,这里temp和image先后处理的顺讯是有影响的，即灰度图作用之后会发生改变
					System.out.println("转化为灰度图成功");
					picture1.setIcon(new ImageIcon(image.getScaledInstance(WIDTH, HEIGHT, java.awt.Image.SCALE_DEFAULT)));
					picture1.setText(null);
				
					int iw=image.getWidth();
					int ih=image.getHeight();
					int hist[]=new int[256];
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
					average1.setText("平均灰度："+String.valueOf(average));
					mid1.setText("中值灰度："+String.valueOf(mid));
					pixSum1.setText("像素总数："+String.valueOf(sum));
					
					for ( int i=0; i<hist.length; i++ )
					{
						deviation += (double) (i-average)*(i-average)*hist[i]/(double)sum;
					}
					deviation=Math.sqrt(deviation);
					deviation1.setText("灰度标准差："+String.valueOf(new java.text.DecimalFormat("#.00").format(deviation)));
					histogram1.setIcon( new ImageIcon(BitMap.drawHist(hist,sum)) );
					System.out.println("打开文件成功");
				}catch (IOException e) {
					System.err.println("打开文件失败");
				}
			}
		});
		open.setFont(new Font("黑体", Font.PLAIN, 13));
		open.setBounds(10, 10, 90, 25);
		contentPane.add(open);
		
		//保存图片按钮
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
					ImageIO.write(temp, "jpg", file2);//保存至磁盘的是经过变换后的图像temp
					System.out.println("保存文件成功");
				}catch (IOException e) {
					System.err.println("保存文件失败");
				}
			}
		});
		contentPane.add(save);
		
		//分割位平面
		JButton bitPlane = new JButton("\u5206\u5272\u4F4D\u9762\u56FE");
		bitPlane.setFont(new Font("黑体", Font.PLAIN, 13));
		bitPlane.setBounds(196, 10, 102, 25);
		bitPlane.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					BitMap.bitPlane(temp);//分割位面图针对的也是变换之后的图像temp
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
						
						//显示处理后图像的直方图的标准流程
						int iw=temp.getWidth();
						int ih=temp.getHeight();
						int sum=iw*ih;
						int hist[]=new int[256];
						hist=BitMap.getHist(temp);
						BitMap.histLabel(sum, hist);
						histogram2.setIcon( new ImageIcon(BitMap.drawHist(hist,sum)) );
						picture2.setIcon(new ImageIcon(temp.getScaledInstance(WIDTH, HEIGHT, java.awt.Image.SCALE_DEFAULT)));
						picture2.setText(null);
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
						
						//显示处理后图像的直方图的标准流程
						int iw=temp.getWidth();
						int ih=temp.getHeight();
						int sum=iw*ih;
						int hist[]=new int[256];
						hist=BitMap.getHist(temp);
						BitMap.histLabel(sum, hist);
						histogram2.setIcon( new ImageIcon(BitMap.drawHist(hist,sum)) );
						picture2.setIcon(new ImageIcon(temp.getScaledInstance(WIDTH, HEIGHT, java.awt.Image.SCALE_DEFAULT)));
						picture2.setText(null);
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
		
		//线性点处理1
		JButton dot_change_1 = new JButton("\u7EBF\u6027\u70B9\u5904\u74061");
		dot_change_1.setFont(new Font("黑体", Font.PLAIN, 12));
		dot_change_1.setBounds(580, 10, 100, 25);
		dot_change_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				temp=BitMap.dot_change_1(image);
				//显示处理后图像的直方图的标准流程
				int iw=temp.getWidth();
				int ih=temp.getHeight();
				int sum=iw*ih;
				int hist[]=new int[256];
				hist=BitMap.getHist(temp);
				BitMap.histLabel(sum, hist);
				histogram2.setIcon( new ImageIcon(BitMap.drawHist(hist,sum)) );
				picture2.setIcon(new ImageIcon(temp.getScaledInstance(WIDTH, HEIGHT, java.awt.Image.SCALE_DEFAULT)));
				picture2.setText(null);
			}
		});
		contentPane.add(dot_change_1);
		
		//线性点处理2
		JButton dot_change_2 = new JButton("\u7EBF\u6027\u70B9\u5904\u74062");
		dot_change_2.setFont(new Font("黑体", Font.PLAIN, 12));
		dot_change_2.setBounds(683, 10, 100, 25);
		dot_change_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				temp=BitMap.dot_change_2(image);
				//显示处理后图像的直方图的标准流程
				int iw=temp.getWidth();
				int ih=temp.getHeight();
				int sum=iw*ih;
				int hist[]=new int[256];
				hist=BitMap.getHist(temp);
				BitMap.histLabel(sum, hist);
				histogram2.setIcon( new ImageIcon(BitMap.drawHist(hist,sum)) );
				picture2.setIcon(new ImageIcon(temp.getScaledInstance(WIDTH, HEIGHT, java.awt.Image.SCALE_DEFAULT)));
				picture2.setText(null);
			}
		});
		contentPane.add(dot_change_2);
		
		//非线性点处理1
		JButton dot_change_3 = new JButton("\u975E\u7EBF\u6027\u70B9\u5904\u74061");
		dot_change_3.setFont(new Font("黑体", Font.PLAIN, 9));
		dot_change_3.setBounds(580, 38, 100, 25);
		dot_change_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				temp=BitMap.dot_change_3(image);
				//显示处理后图像的直方图的标准流程
				int iw=temp.getWidth();
				int ih=temp.getHeight();
				int sum=iw*ih;
				int hist[]=new int[256];
				hist=BitMap.getHist(temp);
				BitMap.histLabel(sum, hist);
				histogram2.setIcon( new ImageIcon(BitMap.drawHist(hist,sum)) );
				picture2.setIcon(new ImageIcon(temp.getScaledInstance(WIDTH, HEIGHT, java.awt.Image.SCALE_DEFAULT)));
				picture2.setText(null);
			}
		});
		contentPane.add(dot_change_3);
		
		//非线性点处理2
		JButton dot_change_4 = new JButton("\u975E\u7EBF\u6027\u70B9\u5904\u74062");
		dot_change_4.setFont(new Font("黑体", Font.PLAIN, 9));
		dot_change_4.setBounds(683, 37, 100, 25);
		dot_change_4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				temp=BitMap.dot_change_4(image);
				//显示处理后图像的直方图的标准流程
				int iw=temp.getWidth();
				int ih=temp.getHeight();
				int sum=iw*ih;
				int hist[]=new int[256];
				hist=BitMap.getHist(temp);
				BitMap.histLabel(sum, hist);
				histogram2.setIcon( new ImageIcon(BitMap.drawHist(hist,sum)) );
				picture2.setIcon(new ImageIcon(temp.getScaledInstance(WIDTH, HEIGHT, java.awt.Image.SCALE_DEFAULT)));
				picture2.setText(null);
			}
		});
		contentPane.add(dot_change_4);
		
		//直方图均衡化
		JButton GST = new JButton("\u539F\u56FE\u76F4\u65B9\u56FE\u5747\u8861");
		GST.setFont(new Font("黑体", Font.PLAIN, 11));
		GST.setBounds(301, 10, 115, 25);
		GST.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				BitMap.histEqualization(image);
			}
		});
		contentPane.add(GST);
		
		//图像缩放
		JButton zooming = new JButton("图像缩放");
		zooming.setFont(new Font("黑体", Font.PLAIN, 12));
		zooming.setBounds(786, 10, 93, 25);
		zooming.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			new Zooming();
			}
		});
		contentPane.add(zooming);
		
		//图像平移
		JButton translation = new JButton("\u56FE\u50CF\u5E73\u79FB");
		translation.setFont(new Font("黑体", Font.PLAIN, 12));
		translation.setBounds(786, 37, 93, 25);
		translation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Translation();
			}
		});
		contentPane.add(translation);
		
		//图像旋转
		JButton rotating = new JButton("\u56FE\u50CF\u65CB\u8F6C");
		rotating.setFont(new Font("黑体", Font.PLAIN, 12));
		rotating.setBounds(882, 10, 93, 25);
		contentPane.add(rotating);
		

	}
}
