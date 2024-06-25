import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class OptionWindow extends JFrame {
    private JButton clearWindowButton = new JButton("Clear");
    private JButton grayScaleButton = new JButton("Black&White");
    private JButton negativeButton = new JButton("Negative");
    private JButton recoverButton = new JButton("Recover");
    private JButton storeImageButton = new JButton("Store Image");
    private JButton logTransformationButton = new JButton("LogT");
    private JButton powerLawTransformationButton = new JButton("PowT");
    private JButton exponentialTransformationButton = new JButton("ExpT");
    private JButton rotateImageButton = new JButton("Rotate");
    private JButton removeColorButton = new JButton("ChangeColor");
    private JButton scalingButton = new JButton("Scale");
    private JButton shiftButton = new JButton("Shift");
    private JPanel optionPanel = new JPanel();

    private float inputPrompt(String msg) {
        String inputString = JOptionPane.showInputDialog(msg);
        float inputFloat = -1f;
        if (inputString == null)
            return -1f;
        else {
            try {
                inputFloat = Float.parseFloat(inputString);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Not a number!");
            }
        }
        return inputFloat;
    }

    private Color getRGB(String msg) {
        int[] RGB = new int[3];
        int k = 0;
        String inputString = JOptionPane.showInputDialog(msg);
        inputString = inputString + " "; 
        String temp = new String("");
        for (int i = 0; i < inputString.length(); i++) {
            if (inputString.charAt(i) == ' ') {
                if (k == 3) {
                    JOptionPane.showMessageDialog(null, "Invalid RGB!");
                    return null;
                }
                int number = Integer.parseInt(temp);
                if (number > 255 || number < 0)
                    return null;
                RGB[k++] = number;
                temp = "";
            } else {
                temp += inputString.charAt(i);
            }
        }
        return new Color(RGB[0], RGB[1], RGB[2]);
    }

    private static boolean isPositiveShift(){
        String input = JOptionPane.showInputDialog(null, "Enter the +ve or -ve shift(+ or -): ");
        if(input == null || input.equals("")) return true;
        for(char c: input.toCharArray()){
            if(c == '+') return true;
            if(c == '-') return false;
        }
        return true;
    }

    // singleton instance
    private static OptionWindow optionWindowInstance;

    public static OptionWindow getOptionWindow() {
        if (optionWindowInstance == null) {
            optionWindowInstance = new OptionWindow();
        }
        return optionWindowInstance;
    }

    private OptionWindow() {

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

        recoverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TransformationFunctions.recoverToPreviousCurrentState(EditorFrame.image);
            }
        });

        storeImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TransformationFunctions.storeCurrentImageState(EditorFrame.image);
            }
        });

        logTransformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float scalingFactor = inputPrompt("Enter the Scaling Factor: ");
                if (scalingFactor < 0f)
                    return;
                float upscalingFactor = inputPrompt("Input Upscaling Factor: ");
                if (upscalingFactor < 0f)
                    return;
                TransformationFunctions.logTransformation(EditorFrame.image, scalingFactor, upscalingFactor);
            }
        });

        powerLawTransformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float scalingFactor = inputPrompt("Enter the Scaling Factor: ");
                if (scalingFactor < 0f)
                    return;
                float gammaFactor = inputPrompt("Enter the Gamma Factor: ");
                if (gammaFactor < 0f)
                    return;
                TransformationFunctions.powerLawTransformation(EditorFrame.image, scalingFactor, gammaFactor);
            }
        });

        exponentialTransformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float scalingFactor = inputPrompt("Enter the Scaling Factor: ");
                if (scalingFactor < 0f)
                    return;
                TransformationFunctions.exponentialTransformation(EditorFrame.image,
                        scalingFactor);
            }
        });

        rotateImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double angle = inputPrompt("Enter the rotation angle in degree(respect to the original pos): ");
                TransformationFunctions.rotateTheImage(EditorFrame.image,
                        angle);
            }
        });

        removeColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TransformationFunctions.replaceTheColor(EditorFrame.image,
                        getRGB("Enter the color to remove(like: r:56 g:47 b:89): "),
                        getRGB("Enter the color to put in replace: "));
            }
        });

        scalingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float delX = inputPrompt("Enter the scale X: ");
                float delY = inputPrompt("Enter the scale Y: ");
                TransformationFunctions.scalingPos(EditorFrame.image, delX, delY);
            }
        });

        shiftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TransformationFunctions.shiftRGB(EditorFrame.image, getRGB("Enter the shift RGB: "), isPositiveShift());
            }
        });

        optionPanel.add(clearWindowButton);
        optionPanel.add(grayScaleButton);
        optionPanel.add(negativeButton);
        optionPanel.add(recoverButton);
        optionPanel.add(storeImageButton);
        optionPanel.add(logTransformationButton);
        optionPanel.add(powerLawTransformationButton);
        optionPanel.add(exponentialTransformationButton);
        optionPanel.add(rotateImageButton);
        optionPanel.add(removeColorButton);
        optionPanel.add(scalingButton);
        optionPanel.add(shiftButton);

        this.add(optionPanel);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }
}

public class EditorFrame {

    private static JPanel panel;
    private static JFrame frame;
    private static JLabel label;
    static BufferedImage image;

    private JButton openImageButton;
    private JButton optionButton;
    private JButton saveButton;

    public static void displayImage(BufferedImage img) {
        if (img == null)
            return;

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
        panel.revalidate();
        panel.repaint();
        frame.revalidate();
        frame.repaint();
    }

    public static void removePreviousImageFromPanel() {
        if (EditorFrame.label == null) {
            System.out.println("EdittorFrame.JLabel is null!");
            return;
        }
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
                OptionWindow.getOptionWindow();
            }

        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (EditorFrame.image == null || EditorFrame.label == null)
                    return;
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save Image");
                int userSelection = fileChooser.showSaveDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    JLabel label = EditorFrame.label;

                    if (label.getIcon() != null) {
                        try {
                            Image image;
                            if (label.getIcon() instanceof ImageIcon) {
                                image = ((ImageIcon) label.getIcon()).getImage();
                            } else {
                                throw new UnsupportedOperationException("Unsupported image format in label");
                            }

                            BufferedImage bufferedImage;
                            if (!(image instanceof BufferedImage)) {
                                bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
                                        BufferedImage.TYPE_INT_ARGB);
                                Graphics2D g2 = bufferedImage.createGraphics();
                                g2.drawImage(image, 0, 0, null);
                                g2.dispose();
                            } else {
                                bufferedImage = (BufferedImage) image;
                            }

                            File fileToSave = fileChooser.getSelectedFile();
                            ImageIO.write(bufferedImage, "png", fileToSave);

                            label.setText("Image saved successfully!");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Error saving image: " + ex.getMessage(), "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        } catch (UnsupportedOperationException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Image format in label not yet supported",
                                    "Unsupported Image", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No image found in the label to save", "No Image",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        frame = new JFrame("Just an Image Editor you will hate most");
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
