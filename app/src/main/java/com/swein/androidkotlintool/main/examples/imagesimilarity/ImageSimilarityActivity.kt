package com.swein.androidkotlintool.main.examples.imagesimilarity

import android.graphics.*
import android.media.ThumbnailUtils
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog
import kotlin.math.pow


/**
最简单的实现：
第一步，缩小尺寸。
将图片缩小到8x8的尺寸，总共64个像素。
这一步的作用是去除图片的细节，只保留结构、明暗等基本信息，
摒弃不同尺寸、比例带来的图片差异。

第二步，简化色彩。

将缩小后的图片，转为64级灰度。
也就是说，所有像素点总共只有64种颜色。

第三步，计算平均值。

计算所有64个像素的灰度平均值。

第四步，比较像素的灰度。

将每个像素的灰度，与平均值进行比较。
大于或等于平均值，记为1；小于平均值，记为0。

第五步，计算哈希值。

将上一步的比较结果，组合在一起，就构成了一个64位的整数，
这就是这张图片的指纹。
组合的次序并不重要，只要保证所有图片都采用同样次序就行了。

得到指纹以后，就可以对比不同的图片，看看64位中有多少位是不一样的。
 */
class ImageSimilarityActivity : AppCompatActivity() {

    val button: Button by lazy {
        findViewById(R.id.button)
    }

    val buttonR: Button by lazy {
        findViewById(R.id.buttonR)
    }

    val imageView: ImageView by lazy {
        findViewById(R.id.imageView)
    }

    val imageView1: ImageView by lazy {
        findViewById(R.id.imageView1)
    }

    val imageView2: ImageView by lazy {
        findViewById(R.id.imageView2)
    }

    val imageView3: ImageView by lazy {
        findViewById(R.id.imageView3)
    }

    val imageView4: ImageView by lazy {
        findViewById(R.id.imageView4)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_similarity)

        var bitmap = BitmapFactory.decodeResource(resources, R.mipmap.is_original)
        var bitmap1 = BitmapFactory.decodeResource(resources, R.mipmap.is_1)
        var bitmap2 = BitmapFactory.decodeResource(resources, R.mipmap.is_2)
        var bitmap3 = BitmapFactory.decodeResource(resources, R.mipmap.is_3)
        var bitmap4 = BitmapFactory.decodeResource(resources, R.mipmap.is_4)

        imageView.setImageBitmap(bitmap)
        imageView1.setImageBitmap(bitmap1)
        imageView2.setImageBitmap(bitmap2)
        imageView3.setImageBitmap(bitmap3)
        imageView4.setImageBitmap(bitmap4)

        buttonR.setOnClickListener {

            bitmap = BitmapFactory.decodeResource(resources, R.mipmap.is_original)
            bitmap1 = BitmapFactory.decodeResource(resources, R.mipmap.is_1)
            bitmap2 = BitmapFactory.decodeResource(resources, R.mipmap.is_2)
            bitmap3 = BitmapFactory.decodeResource(resources, R.mipmap.is_3)
            bitmap4 = BitmapFactory.decodeResource(resources, R.mipmap.is_4)

            imageView.setImageBitmap(bitmap)
            imageView1.setImageBitmap(bitmap1)
            imageView2.setImageBitmap(bitmap2)
            imageView3.setImageBitmap(bitmap3)
            imageView4.setImageBitmap(bitmap4)
        }

        button.setOnClickListener {

            // 缩小尺寸
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, 16, 16)
            bitmap1 = ThumbnailUtils.extractThumbnail(bitmap1, 16, 16)
            bitmap2 = ThumbnailUtils.extractThumbnail(bitmap2, 16, 16)
            bitmap3 = ThumbnailUtils.extractThumbnail(bitmap3, 16, 16)
            bitmap4 = ThumbnailUtils.extractThumbnail(bitmap4, 16, 16)

            bitmap = convertGreyImg(bitmap)
            bitmap1 = convertGreyImg(bitmap1)
            bitmap2 = convertGreyImg(bitmap2)
            bitmap3 = convertGreyImg(bitmap3)
            bitmap4 = convertGreyImg(bitmap4)

            val avg = getAvg(bitmap)
            val avg1 = getAvg(bitmap1)
            val avg2 = getAvg(bitmap2)
            val avg3 = getAvg(bitmap3)
            val avg4 = getAvg(bitmap4)

            ILog.debug("avg", "$avg $avg1 $avg2 $avg3 $avg4")

            val binary = getBinary(bitmap, avg)
            val binary1 = getBinary(bitmap1, avg1)
            val binary2 = getBinary(bitmap2, avg2)
            val binary3 = getBinary(bitmap3, avg3)
            val binary4 = getBinary(bitmap4, avg4)

            ILog.debug("binary", "$binary $binary1 $binary2 $binary3 $binary4")

            val hex = binaryString2hexString(binary)
            val hex1 = binaryString2hexString(binary1)
            val hex2 = binaryString2hexString(binary2)
            val hex3 = binaryString2hexString(binary3)
            val hex4 = binaryString2hexString(binary4)
            ILog.debug("hex", "$hex $hex1 $hex2 $hex3 $hex4")

            val similarityO1 = (similarity(hex, hex1))
            val similarityO2 = (similarity(hex, hex2))
            val similarityO3 = (similarity(hex, hex3))
            val similarityO4 = (similarity(hex, hex4))

            ILog.debug("???", "similarityO1 $similarityO1")
            ILog.debug("???", "similarityO2 $similarityO2")
            ILog.debug("???", "similarityO3 $similarityO3")
            ILog.debug("???", "similarityO4 $similarityO4")

            imageView.setImageBitmap(bitmap)
            imageView1.setImageBitmap(bitmap1)
            imageView2.setImageBitmap(bitmap2)
            imageView3.setImageBitmap(bitmap3)
            imageView4.setImageBitmap(bitmap4)
        }
    }

    /**
     * 比较相似度 > 60 就行
     */
    fun similarity(s1: String, s2: String): Int {
        val s1s = s1.toCharArray()
        val s2s = s2.toCharArray()
        var diffNum = 100
        for (i in s1s.indices) {
            if (s1s[i] != s2s[i]) {
                diffNum--
            }
        }

        if (diffNum < 0) {
            diffNum = 0
        }

        return diffNum
    }

    fun difference(s1: String, s2: String): Int {
        val s1s = s1.toCharArray()
        val s2s = s2.toCharArray()
        var diffNum = 100
        for (i in s1s.indices) {
            if (s1s[i] != s2s[i]) {
                diffNum++
            }
        }

        return diffNum
    }

    /**
     * 简化色彩
     */
    fun convertGreyImg(img: Bitmap): Bitmap {
        val width = img.width //获取位图的宽
        val height = img.height //获取位图的高
        val pixels = IntArray(width * height) //通过位图的大小创建像素点数组
        img.getPixels(pixels, 0, width, 0, 0, width, height)
        val alpha = 0xFF shl 24
        for (i in 0 until height) {
            for (j in 0 until width) {
                val original = pixels[width * i + j]
                val red = (original and 0x00FF0000) shr 16
                val green = (original and 0x0000FF00) shr 8
                val blue = original and 0x000000FF
                var grey = (red.toFloat() * 0.3 + green.toFloat() * 0.59 + blue.toFloat() * 0.11).toInt()
                grey = alpha or (grey shl 16) or (grey shl 8) or grey
                pixels[width * i + j] = grey
            }
        }
        val result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        result.setPixels(pixels, 0, width, 0, 0, width, height)
        return result
    }

    /**
     * 计算平均值
     */
    fun getAvg(img: Bitmap): Int {
        val width = img.width
        val height = img.height
        val pixels = IntArray(width * height)
        img.getPixels(pixels, 0, width, 0, 0, width, height)
        var avgPixel = 0
        for (pixel in pixels) {
            avgPixel += pixel
        }
        return avgPixel / pixels.size
    }

    /**
     * 比较像素的灰度
     */
    fun getBinary(img: Bitmap, average: Int): String {
        val sb = StringBuilder()
        val width = img.width
        val height = img.height
        val pixels = IntArray(width * height)
        img.getPixels(pixels, 0, width, 0, 0, width, height)
        for (i in 0 until height) {
            for (j in 0 until width) {
                val original = pixels[width * i + j]
                if (original >= average) {
                    pixels[width * i + j] = 1
                } else {
                    pixels[width * i + j] = 0
                }
                sb.append(pixels[width * i + j])
            }
        }
        return sb.toString()
    }

    /**
     * 计算哈希值
     */
    fun binaryString2hexString(bString: String): String {
        if (bString == "" || bString.length % 8 != 0) {
            return ""
        }

        val stringBuild = StringBuilder()

        var iTmp: Int
        for (i in bString.indices step 4) {
            iTmp = 0
            for (j in 0 until 4) {
                iTmp += (bString.substring(i + j, i + j + 1).toInt()) shl (4 - j - 1)
            }
            stringBuild.append(Integer.toHexString(iTmp))
        }
        return stringBuild.toString()
    }
}