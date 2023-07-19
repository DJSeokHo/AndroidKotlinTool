package com.swein.androidkotlintool.main.examples.logutilitywithextension

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.swein.androidkotlintool.R
import java.lang.StringBuilder
import kotlin.reflect.KProperty

class LogUtilityWithExtensionActivity : AppCompatActivity() {

    private val tutorialList = listOf(
        Tutorial("MobileA", "Android"),
        Tutorial("MobileI", "iOS"),
        Tutorial("Cross platform", "Flutter"),
        Tutorial("God", "Python")
    )

    private val tutorialMap = mapOf(
        "MobileA" to "Android",
        "MobileI" to "iOS",
        "Cross platform" to "Flutter",
        "God" to "Python",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_utility_with_extension)

        tutorialList.log("tutorialList") {
            "${it.type} - ${it.name}"
        }.let {
            Log.d("???", it)
        }

        tutorialMap.log("tutorialMap") {
            "$it"
        }.let {
            Log.d("???", it)
        }

        tutorialList[0].byMap()?.log("single tutorial") {
            "$it"
        }.let { mapString ->

            mapString?.let {
                Log.d("???", it)
            }
        }

        Channel(
            "Coding with cat",
            Tutorial("MobileA", "Android"),
            tutorialList.slice(1..3)
        ).byMap()?.log("single channel") {
            "$it"
        }.let { mapString ->

            mapString?.let {
                Log.d("???", it)
            }
        }
    }
}

fun <T>Collection<T>.log(tag: String, item: (T) -> String): String {

    return StringBuilder("$tag \n[").also { stringBuilder ->

        this.forEach { element ->
            stringBuilder.append("\n\t${item(element)},")
        }

        stringBuilder.append("\n]")

    }.toString()
}

fun <K, V>Map<K, V?>.log(tag: String, item: (V?) -> String): String {

    return StringBuilder("$tag \n{").also { stringBuilder ->

        this.forEach { element ->
            stringBuilder.append("\n\t${element.key}: ${item(element.value)}")
        }

        stringBuilder.append("\n}")

    }.toString()
}

fun Any.byMap(): Map<String, Any>? {
    return this::class.takeIf {
        it.isData
    }?.members?.filterIsInstance<KProperty<Any>>()?.associate {
        it.name to it.call(this)
    }
}

data class Tutorial(val type: String, val name: String)

data class Channel(val name: String, val tutorial: Tutorial, val otherTutorials: List<Tutorial>)