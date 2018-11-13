package ImageHandle;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class RLEEncode {
	
	//读取文件内容存储为byte数组
	public byte[] readFileToByteArray(String srcPath) {
		try {
			FileInputStream fis=new FileInputStream(srcPath);
			ByteArrayOutputStream out=new ByteArrayOutputStream(1024*10);//最大缓冲10MB的文件
			System.out.println("待压缩文件的字节数："+fis.available());
			
			byte temp[]=new byte[1024];
			int size=0;
			//从输入流中获得字节数组存入字节输出流
			while((size=fis.read(temp))!=-1) {
				out.write(temp,0,size);
			}
			fis.close();
			byte[] result=out.toByteArray();
			System.out.println("读取到的字节数："+result.length);
			return result;
		} catch (Exception e) {
			System.err.println("读取文件为字节数组失败");
			return null;
		}
	}
	
	//将byte数组写入文件
	public void writeByteArrayToFile(String destPath,byte[] content) {
		try {
			FileOutputStream fos=new FileOutputStream(destPath);
			fos.write(content);
			fos.close();
		} catch (Exception e) {
			System.err.println("将字节数组写入文件失败");
		}
	}
	
	//游程编码压缩文件
	public void RLECompress(String srcPath,String destPath) {
		byte[] imageByte=readFileToByteArray(srcPath);
		ByteArrayOutputStream runLengthEncoding=new ByteArrayOutputStream();
		//当前读取得到的的字节
		byte thisByte;
		//相同字节连续出现的长度
		int runLength=1;
		//当前连续出现的字节
		byte lastByte=imageByte[0];
		
		//由字节数组得到游程压缩后的字节流
		for(int i=1;i<imageByte.length;i++) {
			thisByte=imageByte[i];
			if(thisByte==lastByte)
				runLength++;
			else {
				runLengthEncoding.write((byte) lastByte);
				runLengthEncoding.write((byte) runLength);
				runLength=1;
				lastByte=thisByte;
			}
		}
		
		//将最后一次连续的字节写入字节流
		runLengthEncoding.write((byte)lastByte);
		runLengthEncoding.write((byte)runLength);
		
		//游程压缩后的字节数组
		byte[] RLEbyte=runLengthEncoding.toByteArray();
		//将字节数组写入到文件
		writeByteArrayToFile(destPath, RLEbyte);
		return;
	}
	
	//游程编码解压缩
	public void RLEDecompress(String srcPath,String destPath) {
		byte[] RLEbyte=readFileToByteArray(srcPath);
		ByteArrayOutputStream decoding=new ByteArrayOutputStream();
		
		for(int i=0;i<RLEbyte.length;i+=2) 
			for(int j=0;j<RLEbyte[i+1];j++)
				decoding.write(RLEbyte[i]);
		
		writeByteArrayToFile(destPath, decoding.toByteArray());
		return;
	}
}
