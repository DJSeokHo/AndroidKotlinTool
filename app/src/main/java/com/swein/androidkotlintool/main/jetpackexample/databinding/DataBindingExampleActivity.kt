package com.swein.androidkotlintool.main.jetpackexample.databinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.databinding.ObservableArrayMap
import com.swein.androidkotlintool.databinding.ActivityDataBindingExampleBinding
import com.swein.androidkotlintool.framework.util.log.ILog
import java.util.ArrayList
import java.util.HashMap

class DataBindingExampleActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "DataBindingExampleActivity"
    }

    private lateinit var binding: ActivityDataBindingExampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataBindingExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.activity_data_binding_example)

//        binding.testHandlers = this
//
//        binding.exampleData = ExampleDataBean(11, "haha", "hehe")
//        binding.index = 1
//        binding.testStringList = mutableListOf<String>() as ArrayList<String>?
//        binding.testStringList!!.add("111")
//        binding.testStringList!!.add("222")
//        binding.testStringList!!.add("333")
//
//        binding.testExampleDataList = mutableListOf<ExampleDataBean>() as ArrayList<ExampleDataBean>?
//        binding.testExampleDataList!!.add(ExampleDataBean(0, "0t", "0c"))
//        binding.testExampleDataList!!.add(ExampleDataBean(1, "1t", "1c"))
//        binding.testExampleDataList!!.add(ExampleDataBean(2, "2t", "2c"))
//
//        binding.testMap = mutableMapOf<String, Any>() as HashMap<String, Any>?
//        binding.testMap!!["hahaha"] = "hehehe"
//
//        ILog.debug(TAG, "${binding.testStringList == null}, ${binding.index}")
//        ILog.debug(TAG, "${binding.testExampleDataList == null}, ${binding.testExampleDataList!![1]}")
//        ILog.debug(TAG, "${binding.testMap == null} ${binding.testMap!!["hahaha"]}")
//
//        binding.button.setOnClickListener {
//            ILog.debug(TAG, "click")
//        }
//
//        observableTest()
    }

    private fun observableTest() {
        ILog.debug(TAG, "${binding.testObservableBean == null}")
        binding.testObservableBean = ObservableFieldBean()
        binding.testObservableBean!!.name.set("observable test")
        binding.testObservableBean!!.age.set(555)
        ILog.debug(TAG, "${binding.testObservableBean == null}")
//        binding.testObservableBean.name = "observableTest"
//        binding.testObservableBean.age = 10

        binding.testObservableMap = ObservableArrayMap<String, Any>().apply {
            put("keyOne", "haha1")
            put("keyTwo", "haha2")
            put("keyThree", "haha3")
        }

        binding.testUser = TestUser()
        binding.testUser!!.userName = "sss"
        binding.testUser!!.userAge = 10
    }

    fun onClickTest(view: View) {
        ILog.debug("???", "onClickTest ${view is Button}")
        binding.testObservableBean!!.name.set("from click")
        binding.testObservableBean!!.age.set(binding.testObservableBean!!.age.get() + 1)

        binding.testUser!!.userName = "ddd"
        binding.testUser!!.userAge = 12

    }

}