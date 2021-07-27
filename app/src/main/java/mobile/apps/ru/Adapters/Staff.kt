package mobile.apps.ru.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import kotlinx.android.synthetic.main.grid_staff.view.*
import mobile.apps.ru.Home
import mobile.apps.ru.InfoPersonal
import mobile.apps.ru.R
import mobile.apps.ru.Utils
import java.util.*

class Staff(
    var context: Context?,
    var list: ArrayList<Int>,
    var listView: ListView,
) : BaseAdapter(){

    private val inflater: LayoutInflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    var oldPosition = -1

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder", "SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var grid = inflater.inflate(R.layout.grid_staff, parent, false)

        if(position == oldPosition){
            grid.parentView.setBackgroundColor(context!!.resources.getColor(R.color.red))
            grid.fio.setTextColor(Color.WHITE)
            grid.birthday.setTextColor(Color.WHITE)
        }

        /** Считывание данных по id */
        val cursor: Cursor = Home.mDb!!.rawQuery(
            "SELECT * FROM person WHERE id=${list[position]}",
            null
        )
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            var f = cursor.getString(1)
            f = f.substring(0, 1).toUpperCase() + f.substring(1, f.length).toLowerCase()

            var i = cursor.getString(2)
            i = i.substring(0, 1).toUpperCase() + i.substring(1, i.length).toLowerCase()

            grid.fio.text = "ФИ: ${f}  ${i}"

            try {
                var formattedTime = Utils.dateFormat(cursor.getString(3))
                if (formattedTime == "-") {
                    grid.birthday.text = "Возраст: -"
                } else {
                    grid.birthday.text = "Возраст: " + Utils.getAge(
                        formattedTime.substring(0, 2).toInt(),
                        formattedTime.substring(3, 5).toInt(),
                        formattedTime.substring(6, 10).toInt()
                    )
                }
            }catch (e: Exception){
                grid.birthday.text = "Возраст: -"
            }

            cursor.moveToNext()
        }
        cursor.close()


        grid.setOnClickListener {

            try {
                Log.e("oldPosition", oldPosition.toString())
                var view = Utils.getViewByPosition(oldPosition, listView)
                view!!.parentView.setBackgroundColor(Color.WHITE)
                view!!.fio.setTextColor(Color.BLACK)
                view!!.birthday.setTextColor(Color.BLACK)
            }catch (e: Exception){

            }

            grid.parentView.setBackgroundColor(context!!.resources.getColor(R.color.red))
            grid.fio.setTextColor(Color.WHITE)
            grid.birthday.setTextColor(Color.WHITE)

            Home.idPersonal = list[position]

            var frInfoPersonal= InfoPersonal()
            var ft = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
            ft.replace(R.id.container3, frInfoPersonal)
            ft.commit()

            oldPosition = position
        }

        return grid
    }
}