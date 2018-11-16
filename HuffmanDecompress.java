package ImageHandle;

import java.io.FileInputStream;
import java.io.FileOutputStream;


public class HuffmDecompress {
	public int [] codeLengths=new int[256];
	public String[] codeMap=new String[256];
	
	public String changeIntToString(int value) {
		String string="";
		for(int i=0;i<8;i++) {
			string=value%2+string;
			value/=2;
		}
		return string;
	}
	
	public void decompress(String srcPath,String destPath) {
		try {
			FileInputStream fis=new FileInputStream(srcPath);
			FileOutputStream fos=new FileOutputStream(destPath);
			int value;
			int codeLength=0;
			String code="";
			
			//读取每个哈夫曼编码的长度以及计算哈夫曼编码的总长度
			for(int i=0;i<256;i++) {
				value=fis.read();
				codeLengths[i]=value;
				codeLength+=value;
			}
			
			int len=codeLength/8;
			if(codeLength%8!=0)
				len++;
			
			//读取哈夫曼编码
			for(int i=0;i<len;i++) 
				code+=changeIntToString(fis.read());
			
			//构造哈夫曼编码与字符的映射表
			for(int i=0;i<256;i++) {
				if(codeLengths[i]!=0){
					String ss=code.substring(0, codeLengths[i]);
					codeMap[i]=ss;
					code=code.substring(codeLengths[i]);
				}
				else 
					codeMap[i]="";
			}
			
			//读取压缩文件内容
			String codeContent="";
			while(fis.available()>1)
				codeContent+=changeIntToString(fis.read());
			value=fis.read();
			codeContent=codeContent.substring(0, codeContent.length()-value);
			
			for(int i=0;i<codeContent.length();i++) {
				String codecontent=codeContent.substring(0,i+1);
				for(int j=0;j<codeMap.length;j++) {
					if(codecontent.equals(codeMap[j])) {
						fos.write(j);
						fos.flush();
						codeContent=codeContent.substring(i+1);
						i=-1;
						break;
					}
				}
				//此处i++变为0，开始解码下一个哈夫曼编码
			}
			fos.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
