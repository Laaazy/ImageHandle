package ImageHandle;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Smooth {

	private JPanel contentPane;

	public Smooth() {
		JFrame childFrame=new JFrame();
		childFrame.setTitle("图像平滑");
		childFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		childFrame.setBounds(400, 140, 450, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		childFrame.setContentPane(contentPane);
		childFrame.setVisible(true);
		
		JButton average = new JButton("3x3\u5747\u503C\u5E73\u6ED1");
		average.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UI.temp=BitMap.Smooth(UI.image,1);
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
		average.setFont(new Font("黑体", Font.PLAIN, 12));
		average.setBounds(20, 40, 112, 23);
		contentPane.add(average);
		
		JButton mid = new JButton("3x3\u4E2D\u503C\u5E73\u6ED1");
		mid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UI.temp=BitMap.Smooth(UI.image,2);
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
		mid.setFont(new Font("黑体", Font.PLAIN, 12));
		mid.setBounds(135, 40, 112, 23);
		contentPane.add(mid);
		
		JButton knn = new JButton("3x3 2\u8FD1\u90BB\u5747\u503C\u5E73\u6ED1");
		knn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UI.temp=BitMap.Smooth(UI.image,3);
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
		knn.setFont(new Font("黑体", Font.PLAIN, 12));
		knn.setBounds(250, 40, 167, 23);
		contentPane.add(knn);
		
		JLabel label = new JLabel("\u5E73\u6ED1\u6548\u679C\u4F9D\u6B21\u9012\u51CF");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("黑体", Font.PLAIN, 12));
		label.setBounds(135, 73, 116, 15);
		contentPane.add(label);
	}
}
