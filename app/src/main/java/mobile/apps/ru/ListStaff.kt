package mobile.apps.ru

import android.database.Cursor
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ListView
import mobile.apps.ru.Adapters.Specialization
import mobile.apps.ru.Adapters.Staff

class ListStaff : Fragment() {

    var listId = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        var con = activity?.findViewById<FrameLayout>(R.id.container3)
//        con?.removeAllViewsInLayout()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_list_staff, container, false)

        /** Считывание id для списка специальностей */
        val cursor: Cursor = Home.mDb!!.rawQuery("SELECT * FROM person WHERE specialty=${Home.idCategorySelect}", null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            listId.add(cursor.getString(0).toInt())
            cursor.moveToNext()
        }
        cursor.close()

        /** Адаптер заполнения списка сотрудников */
        var adapter = Staff(context, listId)
        var list = rootView.findViewById<ListView>(R.id.listStaff)
        list.adapter = adapter

        return rootView
    }
}