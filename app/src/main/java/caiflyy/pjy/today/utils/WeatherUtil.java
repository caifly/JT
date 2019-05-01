package caiflyy.pjy.today.utils;


import android.content.Context;
import caiflyy.pjy.today.data.weather.WeatherBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.schedulers.Schedulers;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 项目名称：Today
 * 包名：caiflyy.pjy.today.utils
 * 日期：2018/6/1
 * 描述：天气工具类
 *
 * @author 蔡葳
 */
public class WeatherUtil {

    private static WeatherUtil instance;

    private Map<String, WeatherBean> weatherBeanMap;

    private WeatherUtil(Context context) {
        weatherBeanMap = new HashMap<>();
        List<WeatherBean> list = new Gson().fromJson(readFromAssets(context),
                new TypeToken<List<WeatherBean>>() {
                }.getType());
        for (WeatherBean bean : list) {
            weatherBeanMap.put(bean.getCode(), bean);
        }
    }

    public static WeatherUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (WeatherUtil.class) {
                instance = new WeatherUtil(context);
            }
        }
        return instance;
    }

    public Observable<WeatherBean> getWeatherDict(final String code) {
        return Observable.unsafeCreate(new ObservableSource<WeatherBean>() {
            @Override
            public void subscribe(Observer<? super WeatherBean> observer) {
                observer.onNext(weatherBeanMap.get(code));
            }
        }).subscribeOn(Schedulers.io());
    }

    private String readFromAssets(Context context) {
        try {
            InputStream is = context.getAssets().open("weather_map.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
