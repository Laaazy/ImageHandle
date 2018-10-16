package ImageHandle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;



public class BitMap {
	
	public static int hist[]=new int[256];
	
	//将彩色位图转换为灰度图
	public static void toGray(BufferedImage image) throws IOException {
		File file=new File("D:\\imageHandle\\Gray.bmp");
		int imgWidth=image.getWidth();
		int imgHeight=image.getHeight();
		BufferedImage gray=new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_BYTE_GRAY);
		ColorModel cm = ColorModel.getRGBdefault();
		
		for(int i=0;i<imgHeight;i++) {
			for(int j=0;j<imgWidth;j++) {
				int rgb=image.getRGB(i, j);
				int r=cm.getRed(rgb);
				int g=cm.getGreen(rgb);
				int b=cm.getBlue(rgb);
				int h=(int)(0.299 * r + 0.587 * g + 0.114 * b);
				gray.setRGB(i, j, new Color(h,h,h).getRGB());
			}
		}
		String format="bmp";
		try {
			ImageIO.write(gray, format, file);//将image对象保存至文件
			System.out.println("保存至灰度图成功.");
		}catch (IOException e) {
			System.out.println("保存至灰度图失败.");
		}
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
					int rgb=gray.getRGB(j, k);
					int red=cModel.getRed(rgb);
					red=red&(1<<i);
					img[i].setRGB(j, k,new Color(red,red,red).getRGB());
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
	
	//画灰度直方图
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
	
	//线性点处理函数1，整体变亮50个灰度级
	public static BufferedImage dot_change_1(BufferedImage image) {
		int iw=image.getWidth();
		int ih=image.getHeight();
		BufferedImage result=new BufferedImage(iw, ih, BufferedImage.TYPE_BYTE_GRAY);
		ColorModel cm=ColorModel.getRGBdefault();
		for(int i=0;i<ih;i++) {
			for(int j=0;j<iw;j++) {
				int rgb=image.getRGB(i, j);
				int red=cm.getRed(rgb)+50;
				if(red>255)
					red=255;
				result.setRGB(i, j, new Color(red,red,red).getRGB());
			}
		}
		return result;
	} 
	
	//线性点处理函数2，拉伸2倍
	public static BufferedImage dot_change_2(BufferedImage image) {
		int iw=image.getWidth();
		int ih=image.getHeight();
		BufferedImage result=new BufferedImage(iw, ih, BufferedImage.TYPE_BYTE_GRAY);
		ColorModel cm=ColorModel.getRGBdefault();
		for(int i=0;i<ih;i++) {
			for(int j=0;j<iw;j++) {
				int rgb=image.getRGB(i, j);
				int red=cm.getRed(rgb)*2;
				if(red>255)
					red=255;
				result.setRGB(i, j, new Color(red,red,red).getRGB());
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
				int rgb=image.getRGB(i, j);
				int red=(int)Math.log(cm.getRed(rgb));
				if(red>255)
					red=255;
				if(red<0)
					red=0;
				result.setRGB(i, j, new Color(red,red,red).getRGB());
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
				int rgb=image.getRGB(i, j);
				int red=(int)Math.sin(cm.getRed(rgb));
				if(red>255)
					red=255;
				if(red<0)
					red=0;
				result.setRGB(i, j, new Color(red,red,red).getRGB());
			}
		}
		return result;
	}
	
	//直方图均衡化函数
		public static BufferedImage histEqualization(BufferedImage image,int[]hist) {
			int iw=image.getWidth();
			int ih=image.getHeight();
			BufferedImage result=new BufferedImage(iw, ih, BufferedImage.TYPE_BYTE_GRAY);
			ColorModel cm=ColorModel.getRGBdefault();
			for(int i=0;i<ih;i++) {
				for(int j=0;j<iw;j++) {
					int rgb=image.getRGB(i, j);
					int red=(int)Math.sin(cm.getRed(rgb));
					if(red>255)
						red=255;
					if(red<0)
						red=0;
					result.setRGB(i, j, new Color(red,red,red).getRGB());
				}
			}
			return result;
		}			
}
