import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Student extends JFrame {
    // ส่วนของ GUI 
    private JTextArea chatBox;
    private JTextField inputBox;
    private JButton sendButton;
    // ส่วนของการเชื่อมต่อกับ Server
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public Student() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("R JARN PAO GAME ✍️");
        setSize(1000, 600);

        JPanel screenContent = new JPanel();
        screenContent.setLayout(new BorderLayout());
        screenContent.setBackground(Color.BLACK);
        setContentPane(screenContent);

        chatBox = new JTextArea();
        chatBox.setEditable(false);
        chatBox.setBackground(Color.BLACK);
        chatBox.setForeground(Color.GREEN); 
        chatBox.setFont(new Font("Tahoma", Font.PLAIN, 20));

        JScrollPane scrollPane = new JScrollPane(chatBox);
        screenContent.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        screenContent.add(inputPanel, BorderLayout.SOUTH);

        inputBox = new JTextField();
        inputBox.setBackground(Color.BLACK);
        inputBox.setForeground(Color.GREEN);
        inputBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
        inputBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        inputPanel.add(inputBox, BorderLayout.CENTER);

        sendButton = new JButton("Answer arjarn Pao ");
        sendButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        sendButton.setBackground(Color.BLUE);
        sendButton.setForeground(Color.PINK); 
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        inputPanel.add(sendButton, BorderLayout.EAST);

        setVisible(true);
    }

    private void sendMessage() {
        String message = inputBox.getText();
        output.println(message);
        inputBox.setText("");
    }

    public void connect() throws IOException {
        socket = new Socket("localhost", 7777);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(), true);

        while (true) {
                String message;
                try {
                    while ((message = input.readLine()) != null) {
                        chatBox.append(message + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }}

    public static void main(String[] args) throws IOException {
        Student Student = new Student();
        Student.connect();
    }
}