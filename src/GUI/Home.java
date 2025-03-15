package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class Home extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Home(){
		Color sidebarColor = new Color(34, 177, 76); // Xanh lá cây đậm
        Color hoverColor = new Color(50, 205, 85);   // Xanh sáng hơn khi hover
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1400, 705);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		JPanel main_Content = new JPanel();
		main_Content.setBackground(new Color(255, 255, 255));
		main_Content.setBounds(225, 0, 1189, 668);
		contentPane.add(main_Content);
		main_Content.setLayout(null);
		JPanel sideBar = new JPanel();
		sideBar.setBackground(sidebarColor);
		sideBar.setBounds(0, 0, 215, 668);
		contentPane.add(sideBar);
		sideBar.setLayout(null);
		
		JButton btnExam = new JButton("ĐỀ THI");
		btnExam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnExam.setHorizontalAlignment(SwingConstants.LEFT);
		
		btnExam.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnExam.setBounds(0, 197, 215, 49);
		btnExam.setIcon(new ImageIcon(getClass().getClassLoader().getResource("ICON/exam.png")));
		btnExam.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0)); // (top, left, bottom, right)
		btnExam.setBorderPainted(false);
		btnExam.setBackground(sidebarColor);
		btnExam.setForeground(Color.WHITE);
		btnExam.setFocusPainted(false);
		btnExam.setIconTextGap(5); // Thêm hiệu ứng hover
		btnExam.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnExam.setBackground(hoverColor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnExam.setBackground(sidebarColor);
			}
		});
		sideBar.add(btnExam);
		
		JButton btnUser = new JButton("NGƯỜI DÙNG");
		btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				UserForm userForm = new UserForm();
				userForm.setVisible(true);
				main_Content.removeAll();
				main_Content.add(userForm);
				main_Content.revalidate(); // Cập nhật layout
				main_Content.repaint(); // Vẽ lại giao diện
			}
		});
	
		btnUser.setHorizontalAlignment(SwingConstants.LEFT);
		btnUser.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnUser.setBounds(0, 245, 215, 49);
		btnUser.setIcon(new ImageIcon(getClass().getClassLoader().getResource("ICON/user.png")));
		btnUser.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0)); // (top, left, bottom, right)
		btnUser.setBorderPainted(false);
		btnUser.setBackground(sidebarColor);
		btnUser.setForeground(Color.WHITE);
		btnUser.setFocusPainted(false);
		btnUser.setIconTextGap(5); // Thêm hiệu ứng hover
		btnUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnUser.setBackground(hoverColor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnUser.setBackground(sidebarColor);
			}
		});
		sideBar.add(btnUser);
		
		JButton btnTopic = new JButton("CHỦ ĐỀ");
		btnTopic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TopicForm userForm = new TopicForm();
				userForm.setVisible(true);
				main_Content.removeAll();
				main_Content.add(userForm);
				main_Content.revalidate(); // Cập nhật layout
				main_Content.repaint(); // Vẽ lại giao diện
			}
		});
		btnTopic.setHorizontalAlignment(SwingConstants.LEFT);
		btnTopic.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnTopic.setBounds(0, 293, 215, 49);
		btnTopic.setIcon(new ImageIcon(getClass().getClassLoader().getResource("ICON/topic.png")));
		btnTopic.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0)); // (top, left, bottom, right)
		btnTopic.setBorderPainted(false);
		btnTopic.setBackground(sidebarColor);
		btnTopic.setForeground(Color.WHITE);
		btnTopic.setFocusPainted(false);
		btnTopic.setIconTextGap(5); // Thêm hiệu ứng hover
		btnTopic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnTopic.setBackground(hoverColor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnTopic.setBackground(sidebarColor);
			}
		});
		sideBar.add(btnTopic);
		
		JButton btnQuestion = new JButton("CÂU HỎI");
		btnQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				QuestionForm userForm = new QuestionForm();
				userForm.setVisible(true);
				main_Content.removeAll();
				main_Content.add(userForm);
				main_Content.revalidate(); // Cập nhật layout
				main_Content.repaint(); // Vẽ lại giao diện
			}
		});
		btnQuestion.setHorizontalAlignment(SwingConstants.LEFT);
		btnQuestion.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnQuestion.setBounds(0, 341, 215, 49);

		btnQuestion.setIcon(new ImageIcon(getClass().getClassLoader().getResource("ICON/question.png")));
		btnQuestion.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0)); // (top, left, bottom, right)
		btnQuestion.setBorderPainted(false);
		btnQuestion.setBackground(sidebarColor);
	    btnQuestion.setForeground(Color.WHITE);
        btnQuestion.setFocusPainted(false);
		btnQuestion.setIconTextGap(5); // Thêm hiệu ứng hover
        btnQuestion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnQuestion.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnQuestion.setBackground(sidebarColor);
            }
        });

		sideBar.add(btnQuestion);
		
		JButton btnAnswer = new JButton("CÂU TRẢ LỜI");
		btnAnswer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAnswer.setHorizontalAlignment(SwingConstants.LEFT);

		btnAnswer.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnAnswer.setBounds(0, 389, 215, 49);
		btnAnswer.setIcon(new ImageIcon(getClass().getClassLoader().getResource("ICON/choose.png")));
		btnAnswer.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0)); // (top, left, bottom, right)
		btnAnswer.setBorderPainted(false);
		btnAnswer.setBackground(sidebarColor);
		btnAnswer.setForeground(Color.WHITE);
		btnAnswer.setFocusPainted(false);
		btnAnswer.setIconTextGap(5); // Thêm hiệu ứng hover
		btnAnswer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAnswer.setBackground(hoverColor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnAnswer.setBackground(sidebarColor);
			}
		});
		sideBar.add(btnAnswer);
		
		JButton btnStatic = new JButton("THỐNG KÊ");
		btnStatic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExamCandidatesPanel userForm = new ExamCandidatesPanel("123");
				userForm.setVisible(true);
				main_Content.removeAll();
				main_Content.add(userForm);
				main_Content.revalidate(); // Cập nhật layout
				main_Content.repaint(); // Vẽ lại giao diện
			}
		});
		btnStatic.setHorizontalAlignment(SwingConstants.LEFT);
		btnStatic.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnStatic.setBounds(0, 435, 215, 49);
		btnStatic.setIcon(new ImageIcon(getClass().getClassLoader().getResource("ICON/online-data.png")));
		btnStatic.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0)); // (top, left, bottom, right)
		btnStatic.setBorderPainted(false);
		btnStatic.setBackground(sidebarColor);
		btnStatic.setForeground(Color.WHITE);
		btnStatic.setFocusPainted(false);
		btnStatic.setIconTextGap(5); // Thêm hiệu ứng hover
		btnStatic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnStatic.setBackground(hoverColor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnStatic.setBackground(sidebarColor);
			}
		});
		sideBar.add(btnStatic);
		
		JButton btnLogout = new JButton("Đăng xuất");
		btnLogout.setHorizontalAlignment(SwingConstants.LEFT);
		btnLogout.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnLogout.setBounds(0, 598, 215, 49);
		btnLogout.setIcon(new ImageIcon(getClass().getClassLoader().getResource("ICON/switch.png")));
		btnLogout.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0)); // (top, left, bottom, right)
		btnLogout.setBorderPainted(false);
		btnLogout.setBackground(sidebarColor);
		btnLogout.setForeground(Color.WHITE);
		btnLogout.setFocusPainted(false);
		btnLogout.setIconTextGap(5); // Thêm hiệu ứng hover
		btnLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnLogout.setBackground(hoverColor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnLogout.setBackground(sidebarColor);
			}
		});

		sideBar.add(btnLogout);
		
		JLabel lblNewLabel = new JLabel("UserName");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel.setBounds(60, 70, 106, 38);
		sideBar.add(lblNewLabel);
		
		JButton btnTest = new JButton("BÀI THI");
		btnTest.setHorizontalAlignment(SwingConstants.LEFT);
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnTest.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnTest.setBounds(0, 153, 215, 49);
		btnTest.setIcon(new ImageIcon(getClass().getClassLoader().getResource("ICON/test.png")));
		btnTest.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0)); // (top, left, bottom, right)
		btnTest.setBorderPainted(false);
		btnTest.setBackground(sidebarColor);
		btnTest.setForeground(Color.WHITE);
		btnTest.setFocusPainted(false);
		btnTest.setIconTextGap(5); // Thêm hiệu ứng hover
		btnTest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnTest.setBackground(hoverColor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnTest.setBackground(sidebarColor);
			}
		});

		sideBar.add(btnTest);
		
		JLabel lblHello = new JLabel("Hello");
		lblHello.setForeground(Color.WHITE);
		lblHello.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblHello.setBounds(82, 50, 106, 28);
		sideBar.add(lblHello);
		

	}
}
