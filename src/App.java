import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    JTextArea outputArea;
    JLabel statusLabel;

    public App() {
        setTitle("Voice File Manager");
        setSize(650, 520);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 🔷 HEADER
        JLabel title = new JLabel("Voice File Manager", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        title.setBackground(new Color(25, 25, 25));
        title.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(title, BorderLayout.NORTH);

        // 🔷 OUTPUT AREA
        outputArea = new JTextArea();
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        outputArea.setEditable(false);
        outputArea.setBackground(new Color(18, 18, 18));
        outputArea.setForeground(new Color(0, 255, 150));
        outputArea.setCaretColor(Color.WHITE);
        outputArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        // 🔷 STATUS
        statusLabel = new JLabel("● Ready");
        statusLabel.setForeground(Color.GREEN);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // 🔷 LOAD Icons
        ImageIcon micIcon = new ImageIcon("src/Icons/mic.png");
        ImageIcon clearIcon = new ImageIcon("src/Icons/clear.png");

        // Resize Icons
        micIcon = new ImageIcon(micIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH));
        clearIcon = new ImageIcon(clearIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH));

        // 🔷 BUTTONS
        JButton speakBtn = new JButton(" Speak", micIcon);
        JButton clearBtn = new JButton(" Clear", clearIcon);

        styleButton(speakBtn, new Color(0, 120, 215));
        styleButton(clearBtn, new Color(200, 50, 50));

        // 🔷 ACTIONS
        speakBtn.addActionListener(e -> {
            statusLabel.setText("● Listening...");
            statusLabel.setForeground(Color.RED);

            outputArea.append("\nListening...\n");

            String command = VoiceInput.getCommand();

            statusLabel.setText("● Ready");
            statusLabel.setForeground(Color.GREEN);

            outputArea.append("🎤 You said: " + command + "\n");
            CommandProcessor.process(command, outputArea);
        });

        clearBtn.addActionListener(e -> outputArea.setText(""));

        // 🔷 BUTTON PANEL
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(30, 30, 30));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(speakBtn);
        buttonPanel.add(clearBtn);

        // 🔷 FOOTER
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(25, 25, 25));
        footer.add(statusLabel, BorderLayout.WEST);
        footer.add(buttonPanel, BorderLayout.CENTER);

        add(footer, BorderLayout.SOUTH);

        // 🔷 WINDOW ICON
        setIconImage(new ImageIcon("src/Icons/mic.png").getImage());

        setVisible(true);
    }

    // 🎨 BUTTON STYLE
    private void styleButton(JButton btn, Color color) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(140, 40));
        btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    public static void main(String[] args) {
        new App();
    }
}