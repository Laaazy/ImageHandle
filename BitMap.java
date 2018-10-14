package ImageHandle;

import java.awt.Color;
import java.awt.Graphics;
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
}
