package ImageHandle;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedList;

public class HuffmCompress {
	public int times[]=new int[256];
	public String[] huffmCodes=new String[256];
	public LinkedList<HuffmNode> list=new LinkedList<HuffmNode>();
	
	//构造函数
	public HuffmCompress() {
		for(int i=0;i<256;i++)
			huffmCodes[i]="";
	}
	
	//在容器中的正确位置插入节点，维护容器中节点频率升序
	public int getIndex(HuffmNode node) {
		for(int i=0;i<list.size();i++) {
			if(node.getWeight()<=list.get(i).getWeight())
				return i;
		}
		return list.size();
	}
	
	public int changeStringToInt(String str) {
		int v1=(str.charAt(0)-48)*128;
		int v2=(str.charAt(1)-48)*64;
		int v3=(str.charAt(2)-48)*32;
		int v4=(str.charAt(3)-48)*16;
		int v5=(str.charAt(4)-48)*8;
		int v6=(str.charAt(5)-48)*4;
		int v7=(str.charAt(6)-48)*2;
		int v8=(str.charAt(7)-48)*1;
		return v1+v2+v3+v4+v5+v6+v7+v8;
	}
	
	//统计权值
	public void countTimes(String path) {
		try {
			FileInputStream fis=new FileInputStream(path);
			int value=fis.read();
			while(value!=-1) {
				times[value]++;
				value=fis.read();
			}
			fis.close();
		} catch (Exception e) {
			System.err.println("统计权值失败!");
		}
		
	}
	
	
	//构造哈夫曼树
	public HuffmNode createHuffmTree() {
		for(int i=0;i<times.length;i++) {
			if(times[i]!=0) {
				HuffmNode huffmNode=new HuffmNode(times[i], i);
				list.add(getIndex(huffmNode),huffmNode);
			}
		}
		
		//将森林：容器中的各节点构造为哈夫曼树
		while(list.size()>1) {
			HuffmNode firstNode=list.removeFirst();
			HuffmNode secondNode=list.removeFirst();
			HuffmNode fatherNode=new HuffmNode(firstNode.getWeight()+secondNode.getWeight(), -1);
			fatherNode.setLeft(firstNode);
			fatherNode.setRight(secondNode);
			list.add(getIndex(fatherNode),fatherNode);
		}
		
		//返回最终哈夫曼树的根节点
		return list.getFirst();
	}
	
	//利用前序遍历获取哈夫曼编码表
	public void getHuffmCodes(HuffmNode root,String code) {
		if(root.getLeft()!=null) {
			getHuffmCodes(root.getLeft(), code+"0");
		}
		if(root.getRight()!=null) {
			getHuffmCodes(root.getRight(),code+"1");
		}
		//到达叶节点
		if (root.getLeft()==null&&root.getRight()==null) {
			huffmCodes[root.getIndex()]=code;
		} 
	}
	
	//压缩文件
	public void compress(String path,String destPath) {
		try {
			FileInputStream fis=new FileInputStream(path);
			FileOutputStream fos=new FileOutputStream(destPath);
			
			//将每个哈夫曼编码的长度表写入压缩后的文件
			String code="";
			for(int i=0;i<256;i++) {
				fos.write(huffmCodes[i].length());
				code+=huffmCodes[i];
				fos.flush();
			}
			
			//将哈夫曼编码表写入压缩后的文件
			String str1="";
			while(code.length()>=8) {
				str1=code.substring(0, 8);
				int c=changeStringToInt(str1);
				fos.write(c);
				fos.flush();
				code=code.substring(8);
			}
			//处理最后一个长度不为8的数
			int last=8-code.length();
			for(int i=0;i<last;i++)
				code+="0";
			str1=code.substring(0,8);
			int c=changeStringToInt(str1);
			fos.write(c);
			fos.flush();
			
			//将数据写入压缩后的文件中（写入数据对应的哈夫曼编码）
			int value=fis.read();
			String str="";
			while(value!=-1) {
				str+=huffmCodes[value];
				value=fis.read();
			}
			fis.close();
			String s="";
			while(str.length()>=8) {
				s=str.substring(0, 8);
				int b=changeStringToInt(s);
				fos.write(b);
				fos.flush();
				str=str.substring(8);
			}
			int last1=8-str.length();
			for(int i=0;i<last1;i++) 
				str+="0";
			s=str.substring(0,8);
			int b=changeStringToInt(str);
			fos.write(b);
			//写入最后一个数补了几个零
			fos.write(last1);
			fos.flush();
			fos.close();
			fis.close();
		} catch (Exception e) {
			System.err.println("压缩失败");
		}
	}
}
