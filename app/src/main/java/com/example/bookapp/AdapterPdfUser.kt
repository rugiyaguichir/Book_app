package com.example.bookapp

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


import com.example.bookapp.databinding.RowPdfUserBinding

class AdapterPdfUser:RecyclerView.Adapter<AdapterPdfUser.HolderPdfUser>, Filterable {
    private var context: Context

    public var pdfArrayList: ArrayList<ModelPdf>

    private val filterList: ArrayList<ModelPdf>

    private lateinit var binding: RowPdfUserBinding

    private var filter: FilterPdfUser? = null

    constructor(context: Context, pdfArrayList: ArrayList<ModelPdf>) : super() {
        this.pdfArrayList = pdfArrayList
        this.context = context
        this.filterList = pdfArrayList
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterPdfUser.HolderPdfUser {
        binding = RowPdfUserBinding.inflate(LayoutInflater.from(context), parent, false)

        return HolderPdfUser(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterPdfUser.HolderPdfUser, position: Int) {
        val model = pdfArrayList[position]
        val bookId = model.id
        val categoryId = model.categoryId
        val title = model.title
        val description = model.description
        val uid = model.uid
        val url = model.url
        val timestamp = model.timestamp

        val date = MyApplication.formatTimestamp(timestamp)

        holder.titleTv.text = title
        holder.descriptionTv.text = description
        holder.dateTv.text = date

        MyApplication.loadCategory(categoryId, holder.categoryTv)

        MyApplication.loadPdfFromUrlSinglePage(url, title, holder.pdfView, holder.progressBar, null)

        MyApplication.loadPdfSize(url, title, holder.sizeTv)


        holder.itemView.setOnClickListener{
            val intent = Intent(context, PdfDetailActivity::class.java)
            intent.putExtra("bookId", bookId)
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return pdfArrayList.size
    }

    override fun getFilter(): Filter {
        if(filter == null){
            filter = FilterPdfUser(filterList, this)
        }
        return filter as FilterPdfUser
    }


    inner class HolderPdfUser(itemView: View) : RecyclerView.ViewHolder(itemView){

        val pdfView = binding.pdfView
        val progressBar = binding.progressBar
        val titleTv = binding.titleTv
        val descriptionTv = binding.descriptionTv
        val categoryTv: TextView = binding.categoryTv
        val sizeTv = binding.sizeTv
        val dateTv = binding.dateTv
    }
}