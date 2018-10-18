package ImageHandle;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class Zooming {

	private JPanel contentPane;
	private JTextField iw;
	private JTextField ih;
	
	/**
	 * Create the frame.
	 */
	public Zooming() {
		JFrame childFrame=new JFrame();
		childFrame.setTitle("图像缩放");
		childFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		childFrame.setBounds(400, 140, 450, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		childFrame.setContentPane(contentPane);
		contentPane.setLayout(null);
		childFrame.setVisible(true);
		
		JLabel label_1 = new JLabel("\u76EE\u6807\u56FE\u50CF\u5BBD\u5EA6");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("黑体", Font.PLAIN, 12));
		label_1.setBounds(31, 13, 90, 15);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("\u76EE\u6807\u56FE\u50CF\u9AD8\u5EA6");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("黑体", Font.PLAIN, 12));
		label_2.setBounds(116, 13, 90, 15);
		contentPane.add(label_2);
		
		iw = new JTextField();
		iw.setBounds(41, 39, 66, 21);
		contentPane.add(iw);
		iw.setColumns(10);
		
		ih = new JTextField();
		ih.setBounds(126, 39, 66, 21);
		contentPane.add(ih);
		ih.setColumns(10);
		
		JButton nearest = new JButton("\u6700\u8FD1\u90BB\u63D2\u503C");
		nearest.setFont(new Font("黑体", Font.PLAIN, 12));
		nearest.setBounds(208, 38, 100, 23);
		nearest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int newIw=Integer.parseInt(iw.getText());//目标图像宽度
				int newIh=Integer.parseInt(ih.getText());//目标图像高度
				if(newIw<=0||newIh<=0) {
					if(newIw<=0)
						iw.setText("错误的大小");
					if(newIh<=0)
						ih.setText("错误的大小");
				}
				else{
					UI.temp=BitMap.zooming(UI.image,0,newIw,newIh);
					UI.picture2.setIcon(new ImageIcon(UI.temp));
					UI.picture2.setText(null);
					int hist[]=new int[256];
					hist=BitMap.getHist(UI.temp);
					int sum=UI.temp.getWidth()*UI.temp.getHeight();
					BitMap.histLabel(sum, hist);
					UI.histogram2.setIcon( new ImageIcon(BitMap.drawHist(hist,sum)));
					childFrame.dispose();
				}
			}
		});
		contentPane.add(nearest);
		
		JButton biLinear = new JButton("\u53CC\u7EBF\u6027\u63D2\u503C");
		biLinear.setFont(new Font("黑体", Font.PLAIN, 12));
		biLinear.setBounds(310, 38, 100, 23);
		biLinear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int newIw=Integer.parseInt(iw.getText());//目标图像宽度
				int newIh=Integer.parseInt(ih.getText());//目标图像高度
				if(newIw<=0||newIh<=0) {
					if(newIw<=0)
						iw.setText("错误的大小");
					if(newIh<=0)
						ih.setText("错误的大小");
				}
				else {
					UI.temp=BitMap.zooming(UI.image,1,newIw,newIh);
					UI.picture2.setIcon(new ImageIcon(UI.temp));
					UI.picture2.setText(null);
					int hist[]=new int[256];
					hist=BitMap.getHist(UI.temp);
					int sum=UI.temp.getWidth()*UI.temp.getHeight();
					BitMap.histLabel(sum, hist);
					UI.histogram2.setIcon( new ImageIcon(BitMap.drawHist(hist,sum)));
					childFrame.dispose();	
				}
				
			}
		});
		contentPane.add(biLinear);
	}
}
