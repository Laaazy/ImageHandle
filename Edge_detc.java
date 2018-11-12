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

public class Edge_detc{

	private JPanel contentPane;

	public Edge_detc() {
		JFrame childFrame=new JFrame();
		childFrame.setTitle("边缘检测");
		childFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		childFrame.setBounds(400, 140, 320, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		childFrame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton Laplacian = new JButton("Laplacian\u68C0\u6D4B");
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
		Laplacian.setBounds(28, 43, 120, 23);
		contentPane.add(Laplacian);
		
		JButton Krsch = new JButton("Krsch\u65B9\u5411\u68C0\u6D4B");
		Krsch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UI.temp=BitMap.Sharpen(UI.image,4);
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
		Krsch.setFont(new Font("黑体", Font.PLAIN, 12));
		Krsch.setBounds(155, 43, 120, 23);
		contentPane.add(Krsch);
		childFrame.setVisible(true);
	}

}
