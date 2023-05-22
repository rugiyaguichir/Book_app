package com.example.bookapp

import android.widget.Filter
import kotlin.contracts.contract

class FilterPdfAdmin: Filter {
    var filterList: ArrayList<ModelPdf>

    var adapterPdfAdmin: AdapterPdfAdmin

    constructor(filterList: ArrayList<ModelPdf>, adapterPdfAdmin: AdapterPdfAdmin) {
        this.filterList = filterList
        this.adapterPdfAdmin = adapterPdfAdmin
    }

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        var constraint = constraint
        val results = FilterResults()

        if(constraint != null && constraint.isNotEmpty()){
            constraint = constraint.toString().lowercase()
            val filteredModels: ArrayList<ModelPdf> = ArrayList()
            for (i in 0 until  filterList.size){

                if(filterList[i].title.lowercase().contains(constraint)){
                    filteredModels.add(filterList[i])
                }
            }
            results.count = filteredModels.size
            results.values = filteredModels
        }
        else{
            results.count = filterList.size
            results.values = filterList
        }

        return results
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults) {
        adapterPdfAdmin.pdfArrayList = results.values as ArrayList<ModelPdf>

        adapterPdfAdmin.notifyDataSetChanged()
    }
}