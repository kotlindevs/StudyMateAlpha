package com.m3.rajat.piyush.studymatealpha.faculty
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

class FacultyAdapter : RecyclerView.Adapter<FacultyAdapter.FacultyViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= FacultyViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.card_faculty_data,parent,false)
    )

    override fun getItemCount(): Int {
        return admList.size
    }

    override fun onBindViewHolder(holder: FacultyViewHolder, position: Int) {
        val adm = admList[position]
        holder.bindView(adm)
        holder.itemView.setOnClickListener{
            onClickItem?.invoke(adm)
        }
    }

    class FacultyViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        private var name = view.findViewById<TextView>(R.id.FacultyName)
        private var email = view.findViewById<TextView>(R.id.FacultyEmail)
        private var subClass = view.findViewById<TextView>(R.id.FacultySub)
        private var image = view .findViewById<ImageView>(R.id.UserAccountProfile)

        fun bindView(adm : AdminModel){
            subClass.text = adm.faculty_sub
            name.text = adm.faculty_name
            email.text = adm.faculty_email
            if(adm.faculty_image!=null) {
                image.setImageBitmap(
                    BitmapFactory.decodeByteArray(
                        adm.faculty_image,
                        0,
                        adm.faculty_image!!.size
                    )
                )
            }
        }
    }
}