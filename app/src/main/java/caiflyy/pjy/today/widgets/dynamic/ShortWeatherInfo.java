package caiflyy.pjy.today.widgets.dynamic;

/**
 * 项目名称：Today
 * 包名：caiflyy.pjy.today.widgets.dynamic
 * 日期：2019/4/23
 * 描述：天气动画功能短时天气类型类
 *
 * @author 蔡葳
 */
public class ShortWeatherInfo {

    private String code;
    private String windSpeed;
    private String sunrise;
    private String sunset;
    private String moonrise;
    private String moonset;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getMoonrise() {
        return moonrise;
    }

    public void setMoonrise(String moonrise) {
        this.moonrise = moonrise;
    }

    public String getMoonset() {
        return moonset;
    }

    public void setMoonset(String moonset) {
        this.moonset = moonset;
    }
}
