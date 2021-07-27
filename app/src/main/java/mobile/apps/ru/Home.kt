package mobile.apps.ru

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.home_main.*
import mobile.apps.ru.API.Activity65apps
import mobile.apps.ru.API.AnswerData
import mobile.apps.ru.DB.DatabaseHelper
import java.io.IOException
import java.sql.SQLException


class Home : Activity65apps() {

    companion object {
        var mDBHelper: DatabaseHelper? = null
        var mDb: SQLiteDatabase? = null
        var idCategorySelect: Int? = null
        var idPersonal: Int? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_main)

        /** Запрос получения данных */
        loadData()
    }

    fun loadData(){
        doRequest(api.getUid().subscribe(
            {
                /** Подключение к БД и загрузка данных */
                connectToSQL(it)
            },
            {
                onErrorRequest(it.message)
            }
        ))
    }

    fun connectToSQL(answerData: AnswerData) {
        mDBHelper = DatabaseHelper(this)

        try {
            mDBHelper!!.updateDataBase()
        } catch (mIOException: IOException) {
            throw Error("UnableToUpdateDatabase")
        }

        mDb = try {
            mDBHelper!!.writableDatabase
        } catch (mSQLException: SQLException) {
            throw mSQLException
        }

        mDb!!.delete("sqlite_sequence", null, null)
        mDb!!.delete("specialization", null, null)
        mDb!!.delete("person", null, null)

        for(i in 0 until answerData.response.size) {
            val person = ContentValues()
            person.put("f_name", answerData.response[i].f_name)
            person.put("l_name", answerData.response[i].l_name)
            person.put("icon", answerData.response[i].avatr_url)

            val arr = arrayOfNulls<String>(answerData.response[i].specialty.size)
            for(j in 0 until answerData.response[i].specialty.size){
                arr[j] = answerData.response[i].specialty[j].specialty_id.toString()
                val specialization = ContentValues()
                specialization.put("id", answerData.response[i].specialty[j].specialty_id)
                specialization.put("name", answerData.response[i].specialty[j].name)

                var id = answerData.response[i].specialty[j].specialty_id

                val cursor: Cursor = mDb!!.rawQuery(
                    "SELECT * FROM specialization WHERE id=$id",
                    null
                )
                if(cursor.count == 0) {
                    mDb!!.insert("specialization", null, specialization)
                }
            }

            var specialty = Utils.convertArrayToString(arr)
            person.put("specialty", specialty)
            person.put("birthday", answerData.response[i].birthday)
            mDb!!.insert("person", null, person)
        }


        var frSpecialization = ListSpecialization()
        var ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.container, frSpecialization)
        ft.commit()

        loading.visibility = View.GONE
    }
}