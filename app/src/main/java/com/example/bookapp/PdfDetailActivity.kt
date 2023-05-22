package com.example.bookapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bookapp.databinding.ActivityPdfDetailBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PdfDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPdfDetailBinding

    private var bookId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bookId = intent.getStringExtra("bookId")!!

        MyApplication.incrementBookViewCount(bookId)

        loadBookDetails()

        binding.backBtn.setOnClickListener{
            onBackPressed()
        }

    }

    private fun loadBookDetails(){

        val ref = FirebaseDatabase.getInstance().getReference("Books")
        ref.child(bookId)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val categoryId = "${snapshot.child(" categoryId ").value}"
                    val description = "${snapshot.child(" description ").value}"
                    val downloadsCount = "${snapshot.child(" downloadsCount ").value}"
                    val timestamp = "${snapshot.child(" timestamp ").value}"
                    val uid = "${snapshot.child(" uid ").value}"
                    val title = "${snapshot.child(" title ").value}"
                    val url = "${snapshot.child(" url ").value}"
                    val viewsCount = "${snapshot.child(" viewsCount ").value}"

                    val date = timestamp.toLongOrNull()?.let { MyApplication.formatTimestamp(it) }



                    MyApplication.loadCategory(categoryId, binding.categoryTv)

                    MyApplication.loadPdfFromUrlSinglePage("$url", "$title", binding.pdfView, binding.progressBar, binding.pagesTv)

                    MyApplication.loadPdfSize("$url", "$title", binding.sizeTv)

                    binding.titleTv.text = title
                    binding.descriptionTv.text = description
                    binding.viewsTv.text = viewsCount
                    binding.downloadsTv.text = downloadsCount
                    binding.dateTv.text = date
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

    }
}