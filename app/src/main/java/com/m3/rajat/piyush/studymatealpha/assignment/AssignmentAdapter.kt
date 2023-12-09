package com.m3.rajat.piyush.studymatealpha
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AssignmentAdapter : RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder>() {

    private var admList : ArrayList<AdminModel> = ArrayList()
    private var onClickItem : ((AdminModel) -> Unit) ?= null

    fun addItems(items: ArrayList<AdminModel>){
        this.admList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: ((AdminModel) -> Unit) ?= null){
        this.onClickItem = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= AssignmentViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.card_assignment_data,parent,false)
    )

    override fun getItemCount(): Int {
        return admList.size
    }

    override fun onBindViewHolder(holder: AssignmentViewHolder, position: Int) {
        val adm = admList[position]
        holder.bindView(adm)
        holder.itemView.setOnClickListener{
            onClickItem?.invoke(adm)
        }
    }

    class AssignmentViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        private var name = view.findViewById<TextView>(R.id.view_assignment_name)
        private var sdate = view.findViewById<TextView>(R.id.view_assignment_date)
        private var stype = view.findViewById<TextView>(R.id.view_assignment_type)

        fun bindView(adm : AdminModel){
            name.text = adm.assignment_name
            sdate.text = adm.assignment_sdate
            stype.text = adm.assignment_type
        }
    }
}

