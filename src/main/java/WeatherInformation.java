import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.model.CurrentWeather;

import java.io.*;

public class WeatherInformation implements Serializable {

    private transient CurrentWeather currentWeather;

    private transient String city;
    private String temperature;
    private String humidity;
    private String pressure;
    private String windSpeed;

    public WeatherInformation() throws APIException {
        OWM owm = new OWM("8af150f353754e1e7c2c43acec628e58");
        currentWeather = owm.currentWeatherByCityName("Kiev");
    }

    public String getCity(){
        if(currentWeather.hasRespCode() && currentWeather.getRespCode() == 200){
            if(currentWeather.hasCityName()){
                city = "city : " + currentWeather.getCityName() + "\n";
            }
        }
        return city;
    }

    public String getTemperature() {
        if(currentWeather.hasRespCode() && currentWeather.getRespCode() == 200){
            if(currentWeather.hasMainData()
                    && currentWeather.getMainData().hasTempMax()
                    && currentWeather.getMainData().hasTempMin()){
                temperature = "temperature : " + currentWeather.getMainData().getTempMin() + "/"
                        + currentWeather.getMainData().getTempMax() + "\n";
            }
        }
        return temperature;
    }

    public String getHumidity() {
        if(currentWeather.hasRespCode() && currentWeather.getRespCode() == 200){
            if(currentWeather.hasMainData() && currentWeather.getMainData().hasHumidity()){
                humidity = "humidity : " + currentWeather.getMainData().getHumidity() + "\n";
            }
        }
        return humidity;
    }

    public String getPressure() {
        if(currentWeather.hasRespCode() && currentWeather.getRespCode() == 200){
            if(currentWeather.hasMainData() && currentWeather.getMainData().hasPressure()){
                pressure = "pressure : " + currentWeather.getMainData().getPressure() + "\n";
            }
        }
        return pressure;
    }

    public String getWindSpeed() {
        if(currentWeather.hasRespCode() && currentWeather.getRespCode() == 200){
            if(currentWeather.hasWindData() && currentWeather.getWindData().hasSpeed()){
                windSpeed = "windSpeed : " + currentWeather.getWindData().getSpeed() + "\n";
            }
        }
        return windSpeed;
    }


   public static void serializable(WeatherInformation weatherInformation) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("serial.dat"));
        out.writeObject(weatherInformation);
        out.flush();

    }
}
