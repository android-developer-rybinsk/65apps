package mobile.apps.ru

import android.util.Log
import android.view.View
import android.widget.ListView
import kotlinx.android.synthetic.main.grid_staff.view.*
import java.text.SimpleDateFormat
import java.util.*

class Utils {


    companion object {

        var strSeparator = ","
        fun convertArrayToString(array: Array<String?>): String? {
            var str = ""
            for (i in array.indices) {
                str += array[i]
                // Do not append comma at the end of last element
                if (i < array.size - 1) {
                    str += strSeparator
                }
            }
            return str
        }

        fun convertStringToArray(str: String): Array<String?>? {
            return str.split(strSeparator).toTypedArray()
        }

        fun dateFormat(date: String): String {
            try {
                var df = SimpleDateFormat("yyyy-MM-dd")
                var output = SimpleDateFormat("dd.MM.yyyy")

                var formattedTime = date

                if(date.substring(2,3) != "-"){
                    val res = df.parse(date)
                    formattedTime = output.format(res) // Это результат
                }

                return formattedTime

            } catch (e: Exception) {
                return "-"
            }
        }

        fun getAge(day: Int, month: Int, year: Int): String? {
            val dob: Calendar = Calendar.getInstance()
            val today: Calendar = Calendar.getInstance()
            dob.set(year, month, day)
            var age: Int = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
            if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
                age--
            }else{
                if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)) {
                    if (today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
                        age--
                    }
                }
            }
            return age.toString()
        }

        fun getViewByPosition(pos: Int, listView: ListView): View? {
            val firstListItemPosition = listView.firstVisiblePosition
            val lastListItemPosition = firstListItemPosition + listView.childCount - 1
            return if (pos < firstListItemPosition || pos > lastListItemPosition) {
                listView.adapter.getView(pos, null, listView)
            } else {
                val childIndex = pos - firstListItemPosition
                listView.getChildAt(childIndex)
            }
        }
    }
}