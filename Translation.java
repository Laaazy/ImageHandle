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

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Translation{

	private JPanel contentPane;
	private JTextField direction;
	private JTextField distance;

	/**
	 * Create the frame.
	 */
	public Translation() {
		JFrame childFrame=new JFrame();
		childFrame.setTitle("图像平移");
		childFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		childFrame.setBounds(400, 140, 280, 230);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		childFrame.setContentPane(contentPane);
		childFrame.setVisible(true);
		
		direction = new JTextField();
		direction.setBounds(22, 60, 66, 20);
		contentPane.add(direction);
		direction.setColumns(10);
		
		JLabel Label_1 = new JLabel("\u5E73\u79FB\u65B9\u5411\uFF08L,R,U,D\uFF09");
		Label_1.setFont(new Font("黑体", Font.PLAIN, 12));
		Label_1.setBounds(22, 32, 119, 20);
		contentPane.add(Label_1);
		
		JLabel Label_2 = new JLabel("\u5E73\u79FB\u8DDD\u79BB(\u5E94\u5C0F\u4E8E\u56FE\u7247\u5BF9\u5E94\u65B9\u5411\u5C3A\u5BF8)");
		Label_2.setFont(new Font("黑体", Font.PLAIN, 12));
		Label_2.setBounds(22, 85, 200, 20);
		contentPane.add(Label_2);
		
		distance = new JTextField();
		distance.setBounds(22, 110, 66, 20);
		contentPane.add(distance);
		distance.setColumns(10);
		
		JButton nearest = new JButton("\u6700\u8FD1\u90BB\u63D2\u503C\u5E73\u79FB");//最近邻插值
		nearest.setFont(new Font("黑体", Font.PLAIN, 12));
		nearest.setBounds(132, 110, 120, 25);
		nearest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String direct=direction.getText();
				int dist=Integer.parseInt(distance.getText());
				UI.temp=BitMap.translation(UI.image,direct,dist);
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
		contentPane.add(nearest);
		
		JButton biLinear = new JButton("\u53CC\u7EBF\u6027\u63D2\u503C\u5E73\u79FB");//双线性插值
		biLinear.setFont(new Font("黑体", Font.PLAIN, 12));
		biLinear.setBounds(132, 138, 120, 25);
		biLinear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String direct=direction.getText();
				int dist=Integer.parseInt(distance.getText());
				UI.temp=BitMap.translation(UI.image,direct,dist);
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
		contentPane.add(biLinear);
	}

}
