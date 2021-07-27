package mobile.apps.ru.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.grid_special.view.*
import mobile.apps.ru.Home
import mobile.apps.ru.ListSpecialization
import mobile.apps.ru.ListStaff
import mobile.apps.ru.R


class Specialization(
    var context: Context?,
    var list: ArrayList<String>,
    var listName: ArrayList<String>,
) : BaseAdapter(){

    private val inflater: LayoutInflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    var oldview: View? = null

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var grid = inflater.inflate(R.layout.grid_special, parent, false)

        grid.info.text = listName[position]

        grid.setOnClickListener {
            if(oldview != null) {
                oldview!!.parentView.setBackgroundColor(Color.WHITE)
                oldview!!.info.setTextColor(Color.BLACK)
            }
            oldview = grid

            Home.idCategorySelect = list[position].toInt()

            var frStaff = ListStaff()
            var ft = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
            ft.replace(R.id.container2, frStaff)
            ft.commit()

            grid.parentView.setBackgroundColor(context!!.resources.getColor(R.color.red))
            grid.info.setTextColor(Color.WHITE)

        }

        return grid
    }
}