import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class Notepad {
    private static int size = 20;
    private static JTextArea ta;

    public static void main(String... rs) {
        JFrame f = new JFrame();
        f.setSize(600, 800);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.getContentPane().setBackground(Color.WHITE);
        
        JLabel msg = new JLabel("Copyright Reserved @ Rajveer Singh");
        f.add(msg, BorderLayout.SOUTH);

        ta = new JTextArea();
        JScrollPane jsp = new JScrollPane(ta);
        f.add(jsp);

        JMenuBar jmb = new JMenuBar();
        JMenu m1 = new JMenu("File");
        JMenuItem m11 = new JMenuItem("Open");
        m1.add(m11);
        JMenuItem m12 = new JMenuItem("All Clear");
        m1.add(m12);
        JMenuItem m13 = new JMenuItem("Save");
        m1.add(m13);
        jmb.add(m1);

        JMenu m2 = new JMenu("Edit", false);
        JMenuItem m21 = new JMenuItem("Copy");
        m2.add(m21);
        JMenuItem m23 = new JMenuItem("Cut");
        m2.add(m23);
        m2.addSeparator();
        JMenuItem m22 = new JMenuItem("Paste");
        m2.add(m22);
        jmb.add(m2);

        JMenu m3 = new JMenu("View");
        JMenuItem m31 = new JCheckBoxMenuItem("Status Bar", true);
        m3.add(m31);

        // Sub-Menu
        JMenu sub = new JMenu("Zoom");
        JMenuItem s1 = new JMenuItem("Zoom In");
        sub.add(s1);
        JMenuItem s2 = new JMenuItem("Zoom Out");
        sub.add(s2);
        m3.add(sub);
        jmb.add(m3);

        Font font = new Font("Serif", 0, size);
        ta.setFont(font);

        class MyListener implements ActionListener {
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == s1) {
                    size += 10;
                    ta.setFont(new Font("Serif", 0, size));
                } else if (ae.getSource() == s2) {
                    size -= 10;
                    ta.setFont(new Font("Serif", 0, size));
                } else if (ae.getSource() == m21) {
                    ta.copy();
                } else if (ae.getSource() == m23) {
                    ta.cut();
                } else if (ae.getSource() == m22) {
                    ta.paste();
                } else if (ae.getSource() == m11) {
                    openFile();
                } else if (ae.getSource() == m12) {
                    clearText();
                } else if (ae.getSource() == m13) {
                    saveFile();
                }
            }

            private void openFile() {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
                fileChooser.setFileFilter(filter);

                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    try {
                        FileReader fileReader = new FileReader(fileChooser.getSelectedFile());
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        ta.read(bufferedReader, null);
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error reading the file", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

            private void clearText() {
                ta.setText("");
            }

            private void saveFile() {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
                fileChooser.setFileFilter(filter);

                int returnValue = fileChooser.showSaveDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    try {
                        FileWriter fileWriter = new FileWriter(fileChooser.getSelectedFile());
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        ta.write(bufferedWriter);
                        bufferedWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error saving the file", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }

        s1.addActionListener(new MyListener());
        s2.addActionListener(new MyListener());
        m21.addActionListener(new MyListener());
        m23.addActionListener(new MyListener());
        m22.addActionListener(new MyListener());
        m11.addActionListener(new MyListener());
        m12.addActionListener(new MyListener());
        m13.addActionListener(new MyListener());

        f.setJMenuBar(jmb);
        f.setVisible(true);
    }
}
