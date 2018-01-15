import net.aksingh.owmjapis.api.APIException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import static java.awt.Component.CENTER_ALIGNMENT;
import static java.awt.Component.RIGHT_ALIGNMENT;
import static java.awt.font.GraphicAttribute.BOTTOM_ALIGNMENT;

public class FindWeatherGUI  {

    private static String path;
    private static String resultInfString="";
    private static WeatherInformation weatherInformation;
    private static CreateFile createFile;

    private static JFrame jFrame;
    static JPanel jPanel;
    static JButton findPath;
    static JButton start;
    static JLabel textField;

    public FindWeatherGUI() throws APIException {
        weatherInformation = new WeatherInformation();
        createFile = new CreateFile();

        jFrame = new JFrame("Weather situation in Kiev");
        jPanel = new JPanel();
        findPath = new JButton("Find path");
        start = new JButton("Start");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        textField = new JLabel(path);
    }

    public static void findWeatherGUI(){

        jFrame.add(jPanel);
        jPanel.add(findPath);
        jPanel.add(Box.createVerticalGlue());
        jPanel.add(start);
        jPanel.add(Box.createVerticalGlue());

        jPanel.add(Box.createVerticalGlue());

        textField.setAlignmentX(CENTER_ALIGNMENT);
        jPanel.add(textField);
        jPanel.add(Box.createRigidArea(new Dimension(10,10)));

        final JLabel resultInf = new JLabel(resultInfString);
        resultInf.setAlignmentX(BOTTOM_ALIGNMENT);
        jPanel.add(resultInf);
        jPanel.add(Box.createVerticalBox());

        findPath.setAlignmentX(RIGHT_ALIGNMENT);
        findPath.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JFileChooser openFile = new JFileChooser();
                openFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int file = openFile.showDialog(null, "Choose directory");

                if(file == JFileChooser.APPROVE_OPTION){
                    File file1 = openFile.getSelectedFile();
                    textField.setText(file1.getAbsolutePath());
                    path = file1.getAbsolutePath();
                }
            }
        });

        start.setAlignmentX(BOTTOM_ALIGNMENT);
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {

                    createFile.createFile(weatherInformation, path);



                    if(!path.isEmpty() && createFile.exists()){
                        resultInfString="Success";
                        resultInf.setText(resultInfString);
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        jFrame.setPreferredSize(new Dimension(420, 100));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }


    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                JDialog.setDefaultLookAndFeelDecorated(true);
                try {
                    new FindWeatherGUI();
                    WeatherInformation.serializable(weatherInformation);
                } catch (APIException | IOException e) {
                    e.printStackTrace();
                }
                findWeatherGUI();

            }
        });

    }
}
