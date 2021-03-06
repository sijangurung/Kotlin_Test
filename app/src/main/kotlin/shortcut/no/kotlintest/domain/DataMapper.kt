package shortcut.no.kotlintest.domain

import shortcut.no.kotlintest.data.Forecast
import shortcut.no.kotlintest.data.ForecastResult
import shortcut.no.kotlintest.domain.model.ForecastList
import java.text.DateFormat
import java.util.*

import shortcut.no.kotlintest.domain.model.Forecast as ModelForecast

/**
 * Created by Sijan Gurung on 31/03/16.
 * Shortcut AS
 * sijan.gurung@shortcut.no
 */

public class ForecastDataMapper{
    fun convertFromDataModel(forecast: ForecastResult): ForecastList{
        return ForecastList(forecast.city.name, forecast.city.country, convertForecastListToDomain(forecast.list))
    }

    private fun convertForecastListToDomain(list: List<Forecast>): List<ModelForecast>{
        return list.map { convertForecastItemToDomain(it) }
    }

    private fun convertForecastItemToDomain( forecast: Forecast): ModelForecast{
        return ModelForecast(convertDate(forecast.dt), forecast.weather[0].description,
                forecast.temp.max.toInt(), forecast.temp.min.toInt(), generateIconUrl(forecast.weather[0].icon))
    }
    private fun generateIconUrl(iconCode: String): String = "http://openweathermap.org/img/w/$iconCode.png"

    private fun convertDate(date: Long): String{
        val df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
        return df.format(date*1000)
    }

}