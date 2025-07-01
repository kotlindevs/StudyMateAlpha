package com.m3.rajat.piyush.studymatealpha.student
import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.m3.rajat.piyush.studymatealpha.R
import com.m3.rajat.piyush.studymatealpha.database.AdminModel

class StudentAdapter : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private var admList : ArrayList<AdminModel> = ArrayList()
    private var onClickItem : ((AdminModel) -> Unit) ?= null

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(items: ArrayList<AdminModel>){
        this.admList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: ((AdminModel) -> Unit) ?= null){
        this.onClickItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= StudentViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.card_student_data,parent,false)
    )

    override fun getItemCount(): Int {
        return admList.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val adm = admList[position]
        holder.bindView(adm)
        holder.itemView.setOnClickListener{
            onClickItem?.invoke(adm)
        }
    }

    class StudentViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        private var name = view.findViewById<TextView>(R.id.StudentName)
        private var email = view.findViewById<TextView>(R.id.StudentEmailId)
        private var image = view.findViewById<ImageView>(R.id.UserAccountProfile)
        private var sub = view.findViewById<TextView>(R.id.StudentClass)

        fun bindView(adm : AdminModel){
            name.text = adm.student_name
            email.text = adm.student_email
            sub.text = adm.student_class
            if (adm.student_image!=null){
                image.setImageBitmap(BitmapFactory.decodeByteArray(adm.student_image,0,adm.student_image!!.size))
            }
        }
    }
}