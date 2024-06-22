import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class OptionWindow extends JFrame {
    private JButton clearWindowButton = new JButton("Clear");
    private JButton grayScaleButton = new JButton("Black&White");
    private JButton negativeButton = new JButton("Negative");
    private JButton recoverButton = new JButton("Recover");
    private JButton storeImageButton = new JButton("Store Image");
    private JButton logTransformationButton = new JButton("LogT");
    private JButton powerLawTransformationButton = new JButton("PowT");
    private JPanel optionPanel = new JPanel();

    public OptionWindow() {

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        clearWindowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TransformationFunctions.clearTheWindow(EditorFrame.image);
            }
        });


        grayScaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TransformationFunctions.grayScaleFilter(EditorFrame.image);
            }
        });

        negativeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TransformationFunctions.negativeFilter(EditorFrame.image);
            }
        });

        
        recoverButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                TransformationFunctions.recoverToPreviousCurrentState(EditorFrame.image);
            }
        });


        storeImageButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                TransformationFunctions.storeCurrentImageState(EditorFrame.image);
            }
        });


        logTransformationButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                TransformationFunctions.logTransformation(EditorFrame.image, 1f, 20f);
            }
        });


        powerLawTransformationButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                TransformationFunctions.powerLawTransformation(EditorFrame.image, 1f, 1.2f);
            }
        });

        optionPanel.add(clearWindowButton);
        optionPanel.add(grayScaleButton);
        optionPanel.add(negativeButton);
        optionPanel.add(recoverButton);
        optionPanel.add(storeImageButton);
        optionPanel.add(logTransformationButton);
        optionPanel.add(powerLawTransformationButton);

        this.add(optionPanel);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }
}

public class EditorFrame {
    private static JPanel panel;
    private JButton openImageButton;
    private JButton optionButton;
    private JButton saveButton;
    private static JFrame frame;
    private static JLabel label;
    static BufferedImage image;

    public static void displayImage(BufferedImage img) {
        if(img == null) return;

        int panelWidth = panel.getWidth();
        int panelHeight = panel.getHeight();
        int imageWidth = img.getWidth();
        int imageHeight = img.getHeight();

        double scaleFactor = Math.min((double) panelWidth / imageWidth, (double) panelHeight / imageHeight);

        int scaledWidth = (int) (imageWidth * scaleFactor);
        int scaledHeight = (int) (imageHeight * scaleFactor);

        ImageIcon scaledIcon = new ImageIcon(img.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH));

        label = new JLabel(scaledIcon);

        label.setPreferredSize(new Dimension(scaledWidth, scaledHeight));

        panel.add(label);
        // frame.pack();
        frame.revalidate();
    }

    public static void removePreviousImageFromPanel(){
        if(EditorFrame.label == null) return;
        JLabel component = EditorFrame.label;
        panel.remove(component);
        panel.revalidate();
        panel.repaint();
    }

    public EditorFrame() {
        panel = new JPanel();
        openImageButton = new JButton("Open");
        optionButton = new JButton("Options");
        saveButton = new JButton("Save");
        panel.add(openImageButton);
        panel.add(optionButton);
        panel.add(saveButton);

        openImageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Open Image");
                int userSelection = fileChooser.showOpenDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    try {
                        File fileToOpen = fileChooser.getSelectedFile();
                        image = ImageIO.read(fileToOpen);
                        removePreviousImageFromPanel();
                        displayImage(image);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        optionButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new OptionWindow();
            }

        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save Image");
                int userSelection = fileChooser.showSaveDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    try {
                        File fileToSave = fileChooser.getSelectedFile();
                        ImageIO.write(image, "png", fileToSave);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        frame = new JFrame("suggest a good name for the title!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new EditorFrame();
    }
}
