package util

const val BASE_URL = "https://api.openweathermap.org/"
const val API_KEY = "68a39f5322e1e6f73d05d56cad1c1dd5"
fun getIconLink(iconCode: String): String {
    return "https://openweathermap.org/img/wn/$iconCode@4x.png"
}


