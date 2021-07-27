package mobile.apps.ru

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_list_specialization.*
import kotlinx.android.synthetic.main.home_main.*
import mobile.apps.ru.Adapters.Specialization

class ListSpecialization : Fragment() {

    var listId = ArrayList<String>()
    var listName = ArrayList<String>()


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var rootView = inflater.inflate(R.layout.fragment_list_specialization, container, false)

        /** Считывание id для списка специальностей */
        val cursor: Cursor = Home.mDb!!.rawQuery("SELECT * FROM specialization", null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            listId.add(cursor.getString(0).toString())
            listName.add(cursor.getString(1).toString())
            cursor.moveToNext()
        }
        cursor.close()

        /** Адаптер заполнения списка специальностей */
        var adapter = Specialization(context, listId, listName)
        var list = rootView.findViewById<ListView>(R.id.listSpecial)
        list.adapter = adapter

        return rootView
    }
}