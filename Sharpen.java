package ImageHandle;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Sharpen {

	private JPanel contentPane;

	public Sharpen() {
		JFrame childFrame=new JFrame();
		childFrame.setTitle("图像锐化");
		childFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		childFrame.setBounds(400, 140, 400, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		childFrame.setContentPane(contentPane);
		contentPane.setLayout(null);
		childFrame.setVisible(true);
		
		JButton Roberts = new JButton("Roberts\u9510\u5316");
		Roberts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UI.temp=BitMap.Sharpen(UI.image,1);
				UI.picture2.setIcon(new ImageIcon(UI.temp.getScaledInstance(UI.WIDTH, UI.HEIGHT, java.awt.Image.SCALE_DEFAULT)));
				UI.picture2.setText(null);
				int hist[]=new int[256];
				hist=BitMap.getHist(UI.temp);
				int sum=UI.temp.getWidth()*UI.temp.getHeight();
				BitMap.histLabel(sum, hist);
				UI.histogram2.setIcon( new ImageIcon(BitMap.drawHist(hist,sum)));
				childFrame.dispose();	
			}
		});
		Roberts.setFont(new Font("黑体", Font.PLAIN, 12));
		Roberts.setBounds(136, 35, 110, 23);
		contentPane.add(Roberts);
		
		JButton Sobel = new JButton("Sobel\u9510\u5316");
		Sobel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UI.temp=BitMap.Sharpen(UI.image,2);
				UI.picture2.setIcon(new ImageIcon(UI.temp.getScaledInstance(UI.WIDTH, UI.HEIGHT, java.awt.Image.SCALE_DEFAULT)));
				UI.picture2.setText(null);
				int hist[]=new int[256];
				hist=BitMap.getHist(UI.temp);
				int sum=UI.temp.getWidth()*UI.temp.getHeight();
				BitMap.histLabel(sum, hist);
				UI.histogram2.setIcon( new ImageIcon(BitMap.drawHist(hist,sum)));
				childFrame.dispose();	
			}
		});
		Sobel.setFont(new Font("黑体", Font.PLAIN, 12));
		Sobel.setBounds(250, 35, 116, 23);
		contentPane.add(Sobel);
		
		JButton Laplacian = new JButton("Laplacian\u9510\u5316");
		Laplacian.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UI.temp=BitMap.Sharpen(UI.image,3);
				UI.picture2.setIcon(new ImageIcon(UI.temp.getScaledInstance(UI.WIDTH, UI.HEIGHT, java.awt.Image.SCALE_DEFAULT)));
				UI.picture2.setText(null);
				int hist[]=new int[256];
				hist=BitMap.getHist(UI.temp);
				int sum=UI.temp.getWidth()*UI.temp.getHeight();
				BitMap.histLabel(sum, hist);
				UI.histogram2.setIcon( new ImageIcon(BitMap.drawHist(hist,sum)));
				childFrame.dispose();	
			}
		});
		Laplacian.setFont(new Font("黑体", Font.PLAIN, 12));
		Laplacian.setBounds(10, 35, 123, 23);
		contentPane.add(Laplacian);
		
		JLabel label = new JLabel("\u9510\u5316\u7A0B\u5EA6\u4F9D\u6B21\u589E\u5F3A");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("黑体", Font.PLAIN, 12));
		label.setBounds(130, 68, 116, 15);
		contentPane.add(label);
	}
}
