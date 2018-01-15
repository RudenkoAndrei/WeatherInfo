import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateFile {

    private File file;

    public void createFile(WeatherInformation weatherInformation, String path) throws IOException {

        file = new File(path + "//weather.properties");
        FileWriter weatherFile = new FileWriter(file, false);

        weatherFile.write(weatherInformation.getCity());
        weatherFile.write(weatherInformation.getHumidity());
        weatherFile.write(weatherInformation.getPressure());
        weatherFile.write(weatherInformation.getTemperature());
        weatherFile.write(weatherInformation.getWindSpeed());

        weatherFile.flush();
        weatherFile.close();
    }

    public boolean exists(){
        return file.exists();
    }
}
