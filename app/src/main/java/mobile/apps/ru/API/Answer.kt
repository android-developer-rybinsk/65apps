package mobile.apps.ru.API

class AnswerData(
    var response: ArrayList<Personal>
)

class Personal(
    var f_name: String,
    var l_name: String,
    var birthday: String,
    var avatr_url: String,
    var specialty: ArrayList<Specialty>
)

class Specialty(
    var specialty_id: Int,
    var name: String
)