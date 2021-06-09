package com.example.cataractpredict.prediction

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cataractpredict.R
import com.example.cataractpredict.databinding.ActivityPredictionBinding
import com.example.cataractpredict.main.MainActivity
import com.example.cataractpredict.ml.ModelBasic
import org.aviran.cookiebar2.CookieBar
import org.aviran.cookiebar2.OnActionClickListener
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class PredictionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPredictionBinding

    var bitmap: Bitmap? = null
    lateinit var imgView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPredictionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val assetFile ="cataractlabel.txt"
        val inputString = application.assets.open(assetFile).bufferedReader().use { it.readText() }
        val townList =inputString.split("\n")

        val url = "https://github.com/fifprasetya/CapstoneProject-CataractPrediction-"

        imgView = findViewById(R.id.img_poster)

        //Expanded FAB
        binding.fabHome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.fabHelp.setOnClickListener {
            CookieBar.build(this@PredictionActivity)
                .setTitle(R.string.instruction_title)
                .setBackgroundColor(R.color.green_500)
                .setMessage(R.string.instruction)
                .setCookiePosition(CookieBar.BOTTOM)
                .setDuration(6000)
                .show()
        }

        binding.fabAbout.setOnClickListener {
            CookieBar.build(this@PredictionActivity)
                .setBackgroundColor(R.color.green_500)
                .setMessage(R.string.about)
                .setAction("Source Code", OnActionClickListener { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url))) })
                .setCookiePosition(CookieBar.TOP)
                .setDuration(6000)
                .show()
        }

        binding.addBtn.setOnClickListener(View.OnClickListener {
            var intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"

            startActivityForResult(intent, 100)
        })

        binding.predictBtn.setOnClickListener(View.OnClickListener {
            if (bitmap != null){
                val imageProcessor: ImageProcessor = ImageProcessor.Builder()
                    .add(ResizeOp(256, 256, ResizeOp.ResizeMethod.BILINEAR))
                    .build()
                val model = ModelBasic.newInstance(this)


// Creates inputs for reference.
                var tensorImage = TensorImage(DataType.FLOAT32)
                val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 256, 256, 3), DataType.FLOAT32)
                tensorImage.load(bitmap!!)
                tensorImage = imageProcessor.process(tensorImage)
                var byteBuffer = tensorImage.buffer

                inputFeature0.loadBuffer(byteBuffer)

// Runs model inference and gets result.
                val outputs = model.process(inputFeature0)
                val outputFeature0 = outputs.outputFeature0AsTensorBuffer
                var getMax = getMax(outputFeature0.floatArray)

                binding.tvResult.setText(townList[getMax.toInt()])

// Releases model resources if no longer used.
                model.close()
            }
            else{
                Toast.makeText(this, "Please uplod the fundus photograph first!", Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        imgView.setImageURI(data?.data)

        var uri: Uri? = data?.data

        bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
    }

    fun getMax(arr: FloatArray): Float{

        var index = 0.00f

        index = arr[0]

        return index
    }
}