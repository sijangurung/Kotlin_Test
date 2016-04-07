package shortcut.no.kotlintest.ui.activities

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.async
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import shortcut.no.kotlintest.R
import shortcut.no.kotlintest.data.ForecastResult
import shortcut.no.kotlintest.domain.ForecastDataMapper
import shortcut.no.kotlintest.domain.commands.Command
import shortcut.no.kotlintest.domain.model.ForecastList
import shortcut.no.kotlintest.ui.adapters.ForecastListAdapter
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        setContentView(R.layout.activity_main)

        forecast_list.layoutManager = LinearLayoutManager(this)
        async() {
            val result = RequestForecastCommand("0589").execute()
            val adapter = ForecastListAdapter(result) { toast(it.description+" on "+it.date) }
            uiThread {
                forecast_list.adapter = adapter
            }
        }

        /*  Alert Dialog box with responses..
      alert("Hi, I'm alert! Do you want to exit ?") {
            positiveButton("Yes") { toast("Bye Bye") }
            negativeButton("No") {
                val countries = listOf("Russia", "USA", "Japan", "Australia")
                selector("Where are you from?", countries) { i ->
                    toast("So you're living in ${countries[i]}, right?")
                }

            }
        }.show()*/
        /* thread related examples...
        async() {
            uiThread {
                toast("try me")
            }
        }

        val execeutor = Executors.newScheduledThreadPool(4)
        async(execeutor) {
            //sometask...
            uiThread {
                toast("Another try")
            }
        }*/
        /* layout code...
        verticalLayout {
            val name = editText()
            button("Say Hello") {
                onClick { toast("Hello, ${name.text}!") }
            }
        }

        verticalLayout {
            padding = dip(30)
            var txtName = editText() {
                hint = "Name"
                textSize = 24f
            }
            var txtPassword = editText() {
                hint = "Password"
                textSize = 24f
                inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            var btnLogin = button("Login") {
                textSize = 26f
            }
            var txtMessage = textView(){
                text ="message"
                textSize = 16f
                gravity = Gravity.CENTER

            }

        btnLogin.onClick {
            toast("Hello: ${txtName.text} has * ${txtPassword.text}")
            txtMessage.text = "Hello: ${txtName.text} has * ${txtPassword.text}"
            }
        }
        */
    }

    //operators in extension function.
    operator fun ViewGroup.get(position: Int): View = getChildAt(position)

    class ForecastRequest(val zipCode: String) {
        companion object {
            private val APP_ID = "767188f12363d7564714397967b9d275"
            private val URL = "http://api.openweathermap.org/data/2.5/" + "forecast/daily?mode=json&units=metric&cnt=17"
            private val COMPLETE_URL = "${URL}&APPID=${APP_ID}&q="
        }

        fun execute(): ForecastResult {
            val forecastJsonStr = URL(COMPLETE_URL + zipCode).readText()
            return Gson().fromJson(forecastJsonStr, ForecastResult::class.java)
        }
    }

    class RequestForecastCommand(val zipCode: String) : Command<ForecastList> {
        override fun execute(): ForecastList {
            val forecastRequest = ForecastRequest(zipCode)
            return ForecastDataMapper().convertFromDataModel(forecastRequest.execute())
        }
    }

}

