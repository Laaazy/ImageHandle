package ImageHandle;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Convolution extends JFrame {

	private JPanel contentPane;
	private JTextField weight;
	private JTextField Weight;
	public Convolution() {
		JFrame childFrame=new JFrame();
		childFrame.setTitle("自定义卷积");
		childFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		childFrame.setBounds(400, 140, 450, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		childFrame.setContentPane(contentPane);
		childFrame.setVisible(true);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u5377\u79EF\u6838\u65B9\u9635\u8FB9\u957F\u6307\u5B9A\u4E3A3");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("黑体", Font.PLAIN, 13));
		label.setBounds(39, 27, 160, 15);
		contentPane.add(label);
		
		weight = new JTextField();
		weight.setBounds(49, 47, 66, 21);
		getContentPane().add(weight);
		weight.setColumns(10);
		
		JLabel lblX = new JLabel("\u5377\u79EF\u6838\u65B9\u9635\u6743\u503C\uFF0C9\u4E2A\u503C\u7528\u9017\u53F7\u5206\u9694\uFF0C\u548C\u4E3A1");
		lblX.setHorizontalAlignment(SwingConstants.CENTER);
		lblX.setFont(new Font("黑体", Font.PLAIN, 12));
		lblX.setBounds(125, 50, 253, 15);
		contentPane.add(lblX);
		
		Weight = new JTextField();
		Weight.setBounds(49, 47, 66, 21);
		contentPane.add(Weight);
		Weight.setColumns(10);
		
		JButton convolution = new JButton("\u786E\u5B9A\u5377\u79EF");
		convolution.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				float[] weight=new float[9];//卷积核权值矩阵
				String[] strArray=Weight.getText().split(",");
				for(int i=0;i<9;i++) {
					weight[i]=Float.parseFloat(strArray[i]);
				}
				UI.temp=BitMap.Convolution(UI.image,weight);
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
		convolution.setFont(new Font("黑体", Font.PLAIN, 12));
		convolution.setBounds(49, 75, 93, 23);
		contentPane.add(convolution);
		
		
	}
}
