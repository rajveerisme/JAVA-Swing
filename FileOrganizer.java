import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public class FileOrganizer extends JFrame {

    private JTextField directoryTextField;
    private JButton organizeButton;

    public FileOrganizer() {
        super("File Organizer");
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 150);

        directoryTextField = new JTextField();
        organizeButton = new JButton("Organize");
        organizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                organizeFiles();
            }
        });

        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(new JLabel("Enter directory path:"));
        panel.add(directoryTextField);

        add(panel, BorderLayout.NORTH);
        add(organizeButton, BorderLayout.CENTER);
    }

    private void organizeFiles() {
        String directoryPath = directoryTextField.getText();

        if (directoryPath.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a directory path.");
            return;
        }

        File directory = new File(directoryPath);

        if (!directory.exists() || !directory.isDirectory()) {
            JOptionPane.showMessageDialog(this, "Invalid directory path.");
            return;
        }

        try {
            Files.walkFileTree(Paths.get(directoryPath), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    organizeFile(file.toFile());
                    return FileVisitResult.CONTINUE;
                }
            });

            JOptionPane.showMessageDialog(this, "Organizing complete!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error organizing files.");
        }
    }

    private void organizeFile(File file) {
        String extension = getFileExtension(file);

        if (extension != null) {
            File destinationDir = new File(directoryTextField.getText(), extension);
            destinationDir.mkdirs();

            File destinationFile = new File(destinationDir, file.getName());

            try {
                Files.move(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getFileExtension(File file) {
        String name = file.getName();
        int lastDotIndex = name.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < name.length() - 1) {
            return name.substring(lastDotIndex + 1);
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FileOrganizer().setVisible(true);
            }
        });
    }
}
