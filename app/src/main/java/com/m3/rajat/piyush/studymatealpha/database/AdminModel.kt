package com.m3.rajat.piyush.studymatealpha.database
data class AdminModel (
    // Admin Content
    var admin_id : Int? = null,
    var admin_image : ByteArray?= null,
    var admin_name : String = "",
    var admin_email : String = "",
    var admin_password : String = "",

    // Faculty Content
    var faculty_id : Int? = null,
    var faculty_image : ByteArray? = null,
    var faculty_name : String = "",
    var faculty_email : String = "",
    var faculty_password : String = "",
    var faculty_sub : String = "",

    // Student Content
    var student_id : Int? = null,
    var student_image : ByteArray? = null,
    var student_name : String = "",
    var student_email : String = "",
    var student_password : String = "",
    var student_class : String = "",

    //Notice Content
    var notice_txt : String = "Notice Date : ",
    var notice_name : String = "",
    var notice_des : String = "",
    var notice_date : String = "",

    //Assignment Content
    var assignment_txt : String = "Submit ON : ",
    var assignment_name : String = "",
    var assignment_sdate : String = "",
    var assignment_type : String = "",

    //ContactUs Content
    var cname : String = "",
    var cemail : String = "",
    var cdesc : String = "",
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AdminModel

        if (admin_id != other.admin_id) return false
        if (faculty_id != other.faculty_id) return false
        if (student_id != other.student_id) return false
        if (admin_image != null) {
            if (other.admin_image == null) return false
            if (!admin_image.contentEquals(other.admin_image)) return false
        } else if (other.admin_image != null) return false
        if (admin_name != other.admin_name) return false
        if (admin_email != other.admin_email) return false
        if (admin_password != other.admin_password) return false
        if (faculty_image != null) {
            if (other.faculty_image == null) return false
            if (!faculty_image.contentEquals(other.faculty_image)) return false
        } else if (other.faculty_image != null) return false
        if (faculty_name != other.faculty_name) return false
        if (faculty_email != other.faculty_email) return false
        if (faculty_password != other.faculty_password) return false
        if (faculty_sub != other.faculty_sub) return false
        if (student_image != null) {
            if (other.student_image == null) return false
            if (!student_image.contentEquals(other.student_image)) return false
        } else if (other.student_image != null) return false
        if (student_name != other.student_name) return false
        if (student_email != other.student_email) return false
        if (student_password != other.student_password) return false
        if (student_class != other.student_class) return false
        if (notice_txt != other.notice_txt) return false
        if (notice_name != other.notice_name) return false
        if (notice_des != other.notice_des) return false
        if (notice_date != other.notice_date) return false
        if (assignment_txt != other.assignment_txt) return false
        if (assignment_name != other.assignment_name) return false
        if (assignment_sdate != other.assignment_sdate) return false
        if (assignment_type != other.assignment_type) return false
        if (cname != other.cname) return false
        if (cemail != other.cemail) return false
        if (cdesc != other.cdesc) return false

        return true
    }

    override fun hashCode(): Int {
        var result = admin_id ?: 0
        result = 31 * result + (faculty_id ?: 0)
        result = 31 * result + (student_id ?: 0)
        result = 31 * result + (admin_image?.contentHashCode() ?: 0)
        result = 31 * result + admin_name.hashCode()
        result = 31 * result + admin_email.hashCode()
        result = 31 * result + admin_password.hashCode()
        result = 31 * result + (faculty_image?.contentHashCode() ?: 0)
        result = 31 * result + faculty_name.hashCode()
        result = 31 * result + faculty_email.hashCode()
        result = 31 * result + faculty_password.hashCode()
        result = 31 * result + faculty_sub.hashCode()
        result = 31 * result + (student_image?.contentHashCode() ?: 0)
        result = 31 * result + student_name.hashCode()
        result = 31 * result + student_email.hashCode()
        result = 31 * result + student_password.hashCode()
        result = 31 * result + student_class.hashCode()
        result = 31 * result + notice_txt.hashCode()
        result = 31 * result + notice_name.hashCode()
        result = 31 * result + notice_des.hashCode()
        result = 31 * result + notice_date.hashCode()
        result = 31 * result + assignment_txt.hashCode()
        result = 31 * result + assignment_name.hashCode()
        result = 31 * result + assignment_sdate.hashCode()
        result = 31 * result + assignment_type.hashCode()
        result = 31 * result + cname.hashCode()
        result = 31 * result + cemail.hashCode()
        result = 31 * result + cdesc.hashCode()
        return result
    }
}