package com.swein.androidkotlintool.main.examples.newsystemphotopicker

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog

class NewSystemPhotoPickerActivity : AppCompatActivity() {

    // why here ???
    // because "LifecycleOwners must call register before they are STARTED", said by android studio, not me!!
    private val wrapper = NewPhotoPickWrapper(this, 2)

    private val button: Button by lazy {
        findViewById(R.id.button)
    }

    private val imageView1: ImageView by lazy {
        findViewById(R.id.imageView1)
    }

    private val imageView2: ImageView by lazy {
        findViewById(R.id.imageView2)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_system_photo_picker)

        actionBar?.hide()

        button.setOnClickListener {

            PhotoPickerSelectorFragment(

                onSingle = { sheet ->

                    sheet.dismiss()

                    wrapper.singleImage(
                        onSingle = { uri ->
                            ILog.debug("???", "$uri")

                            imageView1.setImageURI(null)
                            imageView2.setImageURI(null)

                            imageView1.setImageURI(uri)
                        },
                        onEmpty = {
                            ILog.debug("???", "not selected")
                        }
                    )
                },

                onMultiple = { sheet ->

                    sheet.dismiss()

                    wrapper.multipleImages(
                        onMultiple = { uris ->

                            for (uri in uris) {
                                ILog.debug("???", "$uri")
                            }

                            imageView1.setImageURI(null)
                            imageView2.setImageURI(null)

                            if (uris.size == 1) {
                                imageView1.setImageURI(uris[0])
                            }

                            if (uris.size > 1) {
                                imageView1.setImageURI(uris[0])
                                imageView2.setImageURI(uris[1])
                            }
                        },
                        onEmpty = {
                            ILog.debug("???", "not selected")
                        }
                    )

                }

            ).show(supportFragmentManager, "PhotoPickerSelectorFragment")
        }
    }
}