package ImageHandle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;




public class BitMap {
	
	//public static int hist[]=new int[256];
	
	//将24位彩色位图转换为灰度图
	public static BufferedImage toGray(BufferedImage image) throws IOException {
		File file=new File("D:\\imageHandle\\Gray.bmp");
		int imgWidth=image.getWidth();
		int imgHeight=image.getHeight();
		BufferedImage gray=new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_BYTE_GRAY);
		ColorModel cm = ColorModel.getRGBdefault();
		
		for(int i=0;i<imgHeight;i++) {
			for(int j=0;j<imgWidth;j++) {
				int rgb=image.getRGB(j, i);//注意这里i和j表达的含义，不能弄反位置
				int r=cm.getRed(rgb);
				int g=cm.getGreen(rgb);
				int b=cm.getBlue(rgb);
				int h=(int)(0.299 * r + 0.587 * g + 0.114 * b);
				gray.setRGB(j, i, new Color(h,h,h).getRGB());
			}
		}
		return gray;
		/*String format="bmp";
		try {
			ImageIO.write(gray, format, file);//将image对象保存至文件
			System.out.println("保存至灰度图成功.");
		}catch (IOException e) {
			System.out.println("保存至灰度图失败.");
		}*/
	}
	
	//将8位灰度图分解为8幅位平面
	public static void bitPlane(BufferedImage gray)throws IOException {
		BufferedImage img[]=new BufferedImage[8];
		int imgWidth=gray.getWidth();
		int imgHeight=gray.getHeight();
		ColorModel cModel=ColorModel.getRGBdefault();
		for(int i=0;i<8;i++) {
			img[i] = new BufferedImage( imgWidth,imgHeight, BufferedImage.TYPE_BYTE_GRAY);
			for(int j=0;j<imgHeight;j++) {
				for(int k=0;k<imgWidth;k++) {
					int rgb=gray.getRGB(k, j);
					int red=cModel.getRed(rgb);
					red=red&(1<<i);
					img[i].setRGB(k, j,new Color(red,red,red).getRGB());
				}
			}
			File file=new File("D:\\imageHandle\\bitPlane"+(i+1)+".bmp");
			try{
			String format="bmp";
				ImageIO.write(img[i], format, file);
				System.out.println("获取位面图"+(i+1)+"成功");
			}catch(IOException e) {
				System.out.println("获取位面图"+(i+1)+"失败");
			}
		}
	}
	
	//图像的采样
	public static BufferedImage sample(BufferedImage image,int d) {
		ColorModel cModel=ColorModel.getRGBdefault();
		int iw=image.getWidth();
		int ih=image.getHeight();
		int pix[]=new int[iw*ih];
		image.getRGB(0, 0, iw, ih, pix, 0, iw);//保存图片的像素至pix数组
		int dd=d*d;
		for(int i=0;i<ih;i+=d) {
			for(int j=0;j<iw;j+=d) {
				int r=0,g=0,b=0;
				for(int k=0;k<d&&i+k<ih;k++) {
					for(int l=0;l<d&&j+l<iw;l++) {
						try {
							r+=cModel.getRed(pix[(i+k)*iw+j+l]);
							g+=cModel.getGreen(pix[(i+k)*iw+j+l]);
							b+=cModel.getBlue(pix[(i+k)*iw+j+l]);
						} catch (Exception e) {
							System.out.println(i+" "+j+" "+k+" "+l+" "+(i+k)*iw+j+l);
						}
					}
				}
				r=(int)(r/dd);
				g=(int)(g/dd);
				b=(int)(b/dd);
				for(int k=0;k<d&&i+k<ih;k++)
					for(int l=0;l<d&&j+l<iw;l++)
						try {
							pix[(i+k)*iw+j+l]=new Color(r,g,b).getRGB();
						} catch (Exception e) {
							System.out.println(d+" "+i+" "+j+" "+k+" "+l+" "+(i+k)*iw+j+l);
						}
			}
		}
		BufferedImage temp=new BufferedImage(iw, ih, image.getType());
		temp.setRGB(0, 0, iw, ih, pix, 0, iw);
		return temp;
	}
	
	//图像的量化,grey是量化组数
	public static BufferedImage quantization(BufferedImage image,int grey) {
		int greyScope=256/grey;//greyScope是每组跨度
		int iw=image.getWidth();
		int ih=image.getHeight();
		int pix[]=new int[iw*ih];
		ColorModel cModel=ColorModel.getRGBdefault();
		int r,g,b,temp;
		image.getRGB(0, 0, iw, ih, pix, 0, iw);
		r=g=b=temp=0;
		for(int i=0;i<iw*ih;i++) {
			r=cModel.getRed(pix[i]);
			temp=r/greyScope;
			r=temp*greyScope;//除之后再乘，抹去了余数，r一定是greyScope的整数倍,因此出现跨度
			
			g=cModel.getGreen(pix[i]);
			temp=g/greyScope;
			g=temp*greyScope;
			
			b=cModel.getBlue(pix[i]);
			temp=b/greyScope;
			b=temp*greyScope;
			
			pix[i]=new Color(r,g,b).getRGB();
		}
		BufferedImage temp1=new BufferedImage(iw, ih, image.getType());
		temp1.setRGB(0, 0, iw, ih, pix, 0, iw);
		return temp1;
	}
	
	//得到图像的灰度直方图统计数组
	public static int[] getHist(BufferedImage image) {
		int iw=image.getWidth();
		int ih=image.getHeight();
		int pix[]=new int[iw*ih];
		int hist[]=new int[256];
		for(int i=0;i<hist.length;i++)
			hist[i]=0;
		int temp;
		ColorModel colorModel=ColorModel.getRGBdefault();
		image.getRGB(0, 0, iw, ih, pix, 0, iw);
		for(int i=0;i<pix.length;i++) {
			temp=colorModel.getRed(pix[i]);
			hist[temp]++;
		}
		return hist;
	}
	
	//得到直方图各种统计信息
	public static void histLabel(int sum,int []hist) {
		int average=0;
		int mid=-1;
		double deviation=0;
		int n=0;//n用于寻找中位数,特别注意全黑全白的情况下mid的取值
	
		for(int i=0;i<hist.length;i++){
			average+=(double)i*hist[i];
			if(n<sum/2)
				n+=hist[i];
			else {
				if(mid==-1)
					mid=i;
			}
		}
		if(hist[0]==sum)//全黑的情况
			mid=0;
		if(hist[255]==sum)//全白的情况
			mid=255;
		average/=(double)sum;
		UI.average2.setText("平均灰度："+String.valueOf(average));
		UI.mid2.setText("中值灰度："+String.valueOf(mid));
		UI.pixSum2.setText("像素总数："+String.valueOf(sum));
		
		for ( int i=0; i<hist.length; i++ )
		{
			deviation += (double) (i-average)*(i-average)*hist[i]/(double)sum;
		}
		deviation=Math.sqrt(deviation);
		UI.deviation2.setText("灰度标准差："+String.valueOf(new java.text.DecimalFormat("#.00").format(deviation)));
	}
	
	//画灰度直方图
		public static BufferedImage drawHist(int []hist,int pixSum) {
			BufferedImage pic=new BufferedImage(370, 300, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g2d=pic.createGraphics();
			g2d.setPaint(Color.BLACK);
			g2d.fillRect(0, 0, 370, 300);
			g2d.setPaint(Color.WHITE);  
	        g2d.drawLine(55, 255, 320, 255); //横轴，长度为200
	        g2d.drawLine(320, 255, 315, 252);
	        g2d.drawLine(320,255 , 315, 258);
	        g2d.drawString("灰度级  255", 250, 268);//横轴最右
	        g2d.drawString("0", 55-5, 255+12);//原点
	        g2d.drawLine(55, 255, 55, 55);//纵轴，长度200
	        g2d.drawLine(55, 55, 52, 60);
	        g2d.drawLine(55, 55 ,58, 60);
	        g2d.drawString("出现频率", 55-5, 55-5);//纵轴最高点
	        
	        g2d.setPaint(Color.GREEN);  
		    int max = 0;//记录最大灰度级
		    for ( int i = 0; i<256; i++ )
	        {
	        	if( hist[i] > max ) max = hist[i];
	        }
	          
	        int offset = 1;//从x=1开始画
	        for(int i=0; i<hist.length; i++) {  
	        	int lineLength = (int)(200*hist[i]/(double)max);//灰度直方图中的线长度，需要注意的是这里的线长度只反应不同灰度级之间的比例关系，相加并不为全集  
	            g2d.drawLine(55 + offset + i, 255, 55 + offset + i, 255-lineLength);  
	        } 
			return pic;
		}
		
	
	//线性点处理函数1，整体变亮50个灰度级
	public static BufferedImage dot_change_1(BufferedImage image) {
		int iw=image.getWidth();
		int ih=image.getHeight();
		BufferedImage result=new BufferedImage(iw, ih, BufferedImage.TYPE_BYTE_GRAY);
		ColorModel cm=ColorModel.getRGBdefault();
		for(int i=0;i<ih;i++) {
			for(int j=0;j<iw;j++) {
				int rgb=image.getRGB(j, i);
				int red=cm.getRed(rgb)+50;
				if(red>255)
					red=255;
				if(red<0)
					red=0;
				result.setRGB(j, i, new Color(red,red,red).getRGB());
			}
		}
		return result;
	} 
	
	//线性点处理函数2，实现亮暗部分对调
	public static BufferedImage dot_change_2(BufferedImage image) {
		int iw=image.getWidth();
		int ih=image.getHeight();
		BufferedImage result=new BufferedImage(iw, ih, BufferedImage.TYPE_BYTE_GRAY);
		ColorModel cm=ColorModel.getRGBdefault();
		for(int i=0;i<ih;i++) {
			for(int j=0;j<iw;j++) {
				int rgb=image.getRGB(j, i);
				int red=cm.getRed(rgb)*-1+255;
				if(red>255)
					red=255;
				if(red<0)
					red=0;
				result.setRGB(j, i, new Color(red,red,red).getRGB());
			}
		}
		return result;
	} 
	
	//非线性点处理函数3
	public static BufferedImage dot_change_3(BufferedImage image) {
		int iw=image.getWidth();
		int ih=image.getHeight();
		BufferedImage result=new BufferedImage(iw, ih, BufferedImage.TYPE_BYTE_GRAY);
		ColorModel cm=ColorModel.getRGBdefault();
		for(int i=0;i<ih;i++) {
			for(int j=0;j<iw;j++) {
				int rgb=image.getRGB(j, i);
				int red=cm.getRed(rgb);
				red=(int)(red+0.8*red*(255-red)/255);
				if(red>255)
					red=255;
				if(red<0)
					red=0;
				result.setRGB(j, i, new Color(red,red,red).getRGB());
			}
		}
		return result;
	}	
		
	//非线性点处理函数4
	public static BufferedImage dot_change_4(BufferedImage image) {
		int iw=image.getWidth();
		int ih=image.getHeight();
		BufferedImage result=new BufferedImage(iw, ih, BufferedImage.TYPE_BYTE_GRAY);
		ColorModel cm=ColorModel.getRGBdefault();
		for(int i=0;i<ih;i++) {
			for(int j=0;j<iw;j++) {
				int rgb=image.getRGB(j, i);
				int red=cm.getRed(rgb);
				red=(int)(255/(double)2*(1+1/Math.sin(Math.PI/2)*Math.sin(Math.PI*(red/(double)255-0.5))));
				if(red>255)
					red=255;
				if(red<0)
					red=0;
				result.setRGB(j, i, new Color(red,red,red).getRGB());
			}
		}
		return result;
	}
	
	//直方图均衡化函数
		public static void histEqualization(BufferedImage image) {
			int hist[]=new int[256];
			hist=BitMap.getHist(image);
			//显示处理后图像的直方图的标准流程
			int iw=image.getWidth();
			int ih=image.getHeight();
			int sum=iw*ih;
			//BitMap.histLabel(sum, hist);
			for(int i=1;i<hist.length;i++)
				hist[i]+=hist[i-1];
			for(int i=0;i<hist.length;i++)//hist均衡化
				hist[i]=hist[i]/hist[255]*255;
			//for(int i=0;i<hist.length;i++)
				//System.out.print(hist[i]+" ");
			BitMap.histLabel(sum, hist);
			UI.histogram2.setIcon( new ImageIcon(BitMap.drawHist(hist,sum)) );
			if(UI.mid2.getText().equals("中值灰度：-1"))
				UI.mid2.setText("中值灰度："+String.valueOf(0));
			UI.picture2.setIcon(new ImageIcon(image.getScaledInstance(UI.WIDTH, UI.HEIGHT, java.awt.Image.SCALE_DEFAULT)));
		}	
		
	
}
