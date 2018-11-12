package ImageHandle;

public class HuffmNode {
	//数据权重
	private int weight;
	//索引值
	private int index;
	//左子节点
	private HuffmNode left;
	//右子节点
	private HuffmNode right;
	
	//构造函数
	public HuffmNode(int weight,int index) {
		this.weight=weight;
		this.index=index;
	}
	
	//私有属性的封装
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public HuffmNode getLeft() {
		return left;
	}
	public void setLeft(HuffmNode left) {
		this.left = left;
	}
	public HuffmNode getRight() {
		return right;
	}
	public void setRight(HuffmNode right) {
		this.right = right;
	}	
}
