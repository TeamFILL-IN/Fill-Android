package com.teamfillin.fillin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil.setContentView
import com.teamfillin.fillin.databinding.ActivityHomeBinding
import kotlinx.coroutines.newFixedThreadPoolContext

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var newPhotosAdapter: NewPhotosAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        initAdapter()
        popup()
        setContentView(binding.root)

    }


    private fun initAdapter() {
        newPhotosAdapter = NewPhotosAdapter()

        binding.rcvNewPhotos.adapter = newPhotosAdapter

        val exphoto = R.drawable.and_photo_rectangle

        newPhotosAdapter.photolist.addAll(
            listOf(
                NewPhotosData(exphoto),
                NewPhotosData(exphoto),
                NewPhotosData(exphoto),
                NewPhotosData(exphoto),
                NewPhotosData(exphoto),
                NewPhotosData(exphoto),
                NewPhotosData(exphoto),
                NewPhotosData(exphoto)
            )
        )
        newPhotosAdapter.notifyDataSetChanged()
    }

    fun popup() {
        binding.apply{
            btnClose.setOnClickListener {
                if (clPopup.getVisibility() == View.VISIBLE)
                    clPopup.setVisibility(View.GONE);

            }
            ///
            tvNotice.setOnClickListener{
                var dialog = PhotoDialogFragment()
                dialog.show(supportFragmentManager, "dialogfragmnet")
            }
        }
    }
}


//<원래코드>
//tvNotice.setOnClickListener{
//    Toast.makeText(this@HomeActivity, "현상소 제보 Page이동", Toast.LENGTH_SHORT).show()
//}