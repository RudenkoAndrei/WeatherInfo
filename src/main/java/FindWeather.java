import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.model.CurrentWeather;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class FindWeather extends JFrame implements Serializable {


//    JPanel jPanel1;
//    JLabel textField;
//    JButton findPath;
//    JButton start;

    static String path;
    static String city;
    static String temperature;
    static String pressure;
    static String humidity;
    static String windSpeed;

    public FindWeather(){
        super("Weather situation in Kiev");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton findPath = new JButton("Find path");
        JButton start = new JButton("Start");

        JPanel jPanel1 = new JPanel();

        jPanel1.add(Box.createVerticalGlue());

        final JLabel textField = new JLabel(path);

        textField.setAlignmentX(CENTER_ALIGNMENT);

        jPanel1.add(textField);

        jPanel1.add(Box.createRigidArea(new Dimension(10,10)));

        findPath.setAlignmentX(RIGHT_ALIGNMENT);

        findPath.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JFileChooser openFile = new JFileChooser();
                openFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int file = openFile.showDialog(null, "Open package");

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
                    fileCreate();
                } catch (APIException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        jPanel1.add(findPath);
        jPanel1.add(Box.createVerticalGlue());
        jPanel1.add(start);
        jPanel1.add(Box.createVerticalGlue());

        getContentPane().add(jPanel1);

        setPreferredSize(new Dimension(260, 220));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void fileCreate() throws APIException, IOException{
        FileWriter weatherFile = new FileWriter(new File(path + "//weather.properties"), false);

        OWM owm = new OWM("8af150f353754e1e7c2c43acec628e58");

        CurrentWeather currentWeather = owm.currentWeatherByCityName("Kiev");

        if(currentWeather.hasRespCode() && currentWeather.getRespCode() == 200){

            if(currentWeather.hasCityName()){
                city = "city : "+currentWeather.getCityName();
            }

            if(currentWeather.hasMainData()
                    && currentWeather.getMainData().hasTempMax()
                    && currentWeather.getMainData().hasTempMin()){
                temperature ="temperature : " +currentWeather.getMainData().getTempMin()+"/"
                        +currentWeather.getMainData().getTempMax() +"\n";
                weatherFile.write(temperature);
            }

            if(currentWeather.hasMainData() && currentWeather.getMainData().hasHumidity()){
                humidity = "humidity : "+currentWeather.getMainData().getHumidity()+"\n";
                weatherFile.write(humidity);
            }

            if(currentWeather.hasMainData() && currentWeather.getMainData().hasPressure()){
                pressure = "pressure : "+currentWeather.getMainData().getPressure()+"\n";
                weatherFile.write(pressure);
            }

            if(currentWeather.hasWindData() && currentWeather.getWindData().hasSpeed()){
                windSpeed = "windSpeed : " + currentWeather.getWindData().getSpeed()+"\n";
                weatherFile.write(windSpeed);
            }
            weatherFile.flush();
        }
    }

    public static void main(String[] args)  {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                JDialog.setDefaultLookAndFeelDecorated(true);
                new FindWeather();
            }
        });

    }
}
