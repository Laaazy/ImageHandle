package ImageHandle;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class Rotating {

	private JPanel contentPane;
	private JLabel angle;
	private JTextField angleValue;
	private JButton biLinearRotate;

	public Rotating() {
		JFrame childFrame=new JFrame();
		childFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		childFrame.setBounds(400, 140, 350, 180);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		childFrame.setContentPane(contentPane);
		childFrame.setTitle("图片旋转");
		childFrame.setVisible(true);
		
		JLabel Label = new JLabel("\u987A\u65F6\u9488\u65CB\u8F6C");
		Label.setFont(new Font("黑体", Font.PLAIN, 12));
		Label.setBounds(45, 26, 77, 20);
		contentPane.add(Label);
		
		angle = new JLabel("\u65CB\u8F6C\u89D2\u5EA6\uFF080-360\uFF09");
		angle.setFont(new Font("黑体", Font.PLAIN, 12));
		angle.setBounds(45, 47, 102, 20);
		contentPane.add(angle);
		
		angleValue = new JTextField();
		angleValue.setBounds(45, 72, 130, 20);
		contentPane.add(angleValue);
		angleValue.setColumns(10);
		
		JButton nearestRotate = new JButton("\u6700\u8FD1\u90BB\u63D2\u503C\u65CB\u8F6C");//最近邻插值
		nearestRotate.setFont(new Font("黑体", Font.PLAIN, 12));
		nearestRotate.setBounds(185, 60, 120, 25);
		nearestRotate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int ang=Integer.parseInt(angleValue.getText());
				if(ang<0||ang>360)
					angleValue.setText("角度范围应在[0°,360°]");
				else {
					UI.temp=BitMap.rotating(UI.image, 0,  ang);
					UI.picture2.setIcon(new ImageIcon(UI.temp.getScaledInstance(UI.WIDTH, UI.HEIGHT, java.awt.Image.SCALE_DEFAULT)));
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
		contentPane.add(nearestRotate);
		
		biLinearRotate = new JButton("\u53CC\u7EBF\u6027\u63D2\u503C\u65CB\u8F6C");//双线性插值
		biLinearRotate.setFont(new Font("黑体", Font.PLAIN, 12));
		biLinearRotate.setBounds(185, 95, 120, 25);
		biLinearRotate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int ang=Integer.parseInt(angleValue.getText());
				if(ang<0||ang>360)
					;
				else {
					UI.temp=BitMap.rotating(UI.image, 1,  ang);
					UI.picture2.setIcon(new ImageIcon(UI.temp.getScaledInstance(UI.WIDTH, UI.HEIGHT, java.awt.Image.SCALE_DEFAULT)));
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
		contentPane.add(biLinearRotate);
		
		
		
	}
}
