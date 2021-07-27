package mobile.apps.ru

import android.database.Cursor
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import kotlinx.android.synthetic.main.fragment_info_personal.*

class InfoPersonal : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_info_personal, container, false)

        /** Считывание данных по id */
        var cursor: Cursor = Home.mDb!!.rawQuery(
            "SELECT * FROM person WHERE id=${Home.idPersonal}",
            null
        )
        cursor.moveToFirst()
        try{
            Glide
                .with(this)
                .load(cursor.getString(4))
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(p0: GlideException?, p1: Any?, p2: com.bumptech.glide.request.target.Target<Drawable>?, p3: Boolean): Boolean {
                        rootView.findViewById<ImageView>(R.id.icon).visibility = View.GONE
                        return false
                    }
                    override fun onResourceReady(p0: Drawable?, p1: Any?, p2: com.bumptech.glide.request.target.Target<Drawable>?, p3: DataSource?, p4: Boolean): Boolean {
                        return false
                    }
                })
                .into(rootView.findViewById<ImageView>(R.id.icon))

        }catch (e: Exception){
            rootView.findViewById<ImageView>(R.id.icon).visibility = View.GONE
        }

        var f = cursor.getString(1)
        f = f.substring(0, 1).toUpperCase() + f.substring(1, f.length).toLowerCase()

        var i = cursor.getString(2)
        i = i.substring(0, 1).toUpperCase() + i.substring(1, i.length).toLowerCase()

        var name = rootView.findViewById<TextView>(R.id.name)
        name.text = "Имя: ${f}"

        var secondName = rootView.findViewById<TextView>(R.id.secondName)
        secondName.text = "Фамилия: ${i}"

        var birthday = rootView.findViewById<TextView>(R.id.birthday)
        var age = rootView.findViewById<TextView>(R.id.age)

        try {
            var formattedTime = Utils.dateFormat(cursor.getString(3))
            if (formattedTime == "-") {
                birthday.text = "Дата рождения: -"
                age.text = "Возраст: -"
            } else {
                birthday.text = "Дата рождения: " + Utils.dateFormat(cursor.getString(3))
                age.text = "Возраст: " + Utils.getAge(
                    formattedTime.substring(0, 2).toInt(),
                    formattedTime.substring(3, 5).toInt(),
                    formattedTime.substring(6, 10).toInt()
                )
            }
        }catch (e: Exception){
            birthday.text = "Дата рождения: -"
            age.text = "Возраст: -"
        }

        val cursor1: Cursor = Home.mDb!!.rawQuery("SELECT * FROM specialization WHERE id=${cursor.getInt(5)}", null)
        cursor1.moveToFirst()
        var specialization = rootView.findViewById<TextView>(R.id.specialization)
        specialization.text = "Специальность: " + cursor1.getString(1).toString()
        cursor1.moveToNext()
        cursor1.close()


        cursor.close()


        return rootView
    }
}