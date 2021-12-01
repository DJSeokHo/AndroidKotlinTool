package com.swein.androidkotlintool.framework.module.room.example

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.swein.androidkotlintool.R
//import com.swein.androidkotlintool.framework.module.room.example.controller.db.DBController
//import com.swein.androidkotlintool.framework.module.room.example.database.DatabaseManager
//import com.swein.androidkotlintool.framework.module.room.example.model.IdeaModel
import com.swein.androidkotlintool.template.coroutine.BaseCoroutineActivity

class RoomWithCoroutineAndStartUPExampleActivity : BaseCoroutineActivity() {

    companion object {
        private const val TAG = "RoomWithCoroutineAndStartUPExampleActivity"
    }

    private lateinit var textViewName: TextView
    private lateinit var textViewTitle: TextView
    private lateinit var textViewInfo: TextView
    private lateinit var textViewDate: TextView

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_with_coroutine_and_start_upexample)

        textViewName = findViewById(R.id.textViewName)
        textViewTitle = findViewById(R.id.textViewTitle)
        textViewInfo = findViewById(R.id.textViewInfo)
        textViewDate = findViewById(R.id.textViewDate)

        findViewById<Button>(R.id.buttonInsert).setOnClickListener {
//            val ideaModel = IdeaModel().apply {
//                this.uuId = UUID.randomUUID().toString().replace("-", "")
//                this.name = "name 1"
//                this.title = "title 1"
//                this.info = "info 1"
//                this.date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().time)
//            }
//
//            launch(Dispatchers.Main) {
//
//                val result = async {
//                    DBController.insert(ideaModel)
//                }
//
//                result.await().run {
//                    Toast.makeText(this@RoomWithCoroutineAndStartUPExampleActivity, "Insert success", Toast.LENGTH_SHORT).show()
//                    updateView(ideaModel)
//                }
//            }

        }

        findViewById<Button>(R.id.buttonLoadNew).setOnClickListener {

//            launch(Dispatchers.Main) {
//
//                val result = async {
//                    DBController.loadLatest()
//                }
//
//                result.await().apply {
//
//                    val entity = this
//
//                    entity?.let {
//                        // if not empty
//                        ILog.debug(TAG, "load success and entity is: $entity")
//                        Toast.makeText(this@RoomWithCoroutineAndStartUPExampleActivity, "Load new success", Toast.LENGTH_SHORT).show()
//                        updateView(IdeaModel().apply {
//                            this.parsing(it)
//                        })
//                    } ?: run {
//                        // if empty
//                        Toast.makeText(this@RoomWithCoroutineAndStartUPExampleActivity, "Load new success but empty", Toast.LENGTH_SHORT).show()
//                        clear()
//                    }
//                }
//            }
        }

        findViewById<Button>(R.id.buttonClearAll).setOnClickListener {

//            launch(Dispatchers.Main) {
//
//                val result = async {
////                    DBController.deleteAll()
//                }
//
//                result.await().run {
//                    Toast.makeText(this@RoomWithCoroutineAndStartUPExampleActivity, "Delete all success", Toast.LENGTH_SHORT).show()
//                    clear()
//                }
//            }
        }
    }

//    private fun updateView(ideaModel: IdeaModel) {
//        textViewName.text = ideaModel.name
//        textViewTitle.text = ideaModel.title
//        textViewInfo.text = ideaModel.info
//        textViewDate.text = ideaModel.date
//    }

    private fun clear() {
        textViewName.text = ""
        textViewTitle.text = ""
        textViewInfo.text = ""
        textViewDate.text = ""
    }
}