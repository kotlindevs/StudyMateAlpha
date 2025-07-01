package com.m3.rajat.piyush.studymatealpha.database
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME,null,
    DATABASE_VERSION
) {

    companion object{
        private const val DATABASE_NAME = "student.db"
        private const val DATABASE_VERSION = 1

        //Admin Table
        private const val TBL_ADMIN = "tbl_admin"
        private const val ADMIN_ID = "admin_id"
        private const val ADMIN_IMAGE = "admin_image"
        private const val ADMIN_NAME = "admin_name"
        private const val ADMIN_EMAIL = "admin_email"
        private const val ADMIN_PASSWORD = "admin_password"

        //Faculty Table
        private const val TBL_FACULTY = "tbl_faculty"
        private const val FACULTY_ID = "faculty_id"
        private const val FACULTY_IMAGE = "faculty_image"
        private const val FACULTY_NAME = "faculty_name"
        private const val FACULTY_EMAIL = "faculty_email"
        private const val FACULTY_PASSWORD = "faculty_password"
        private const val FACULTY_SUB = "faculty_sub"

        //Student Table
        private const val TBL_STUDENT = "tbl_student"
        private const val STUDENT_ID = "student_id"
        private const val STUDENT_IMAGE = "student_image"
        private const val STUDENT_NAME = "student_name"
        private const val STUDENT_EMAIL = "student_email"
        private const val STUDENT_PASSWORD = "student_password"
        private const val STUDENT_CLASS = "student_class"

        //Notice Table
        private const val TBL_NOTICE = "tbl_notice"
        private const val NOTICE_NAME = "notice_name"
        private const val NOTICE_DES = "notice_des"
        private const val NOTICE_DATE = "notice_date"

        //Assignment Table
        private const val TBL_ASSIGNMENT = "tbl_assignment"
        private const val ASSIGNMENT_NAME = "assignment_name"
        private const val ASSIGNMENT_TYPE = "assignment_type"
        private const val ASSIGNMENT_SDATE = "assignment_sdate"

        //ContactUs Table
        private const val TBL_CONTACT = "tbl_contact"
        private const val CNAME = "contact_name"
        private const val CEMAIL = "contact_email"
        private const val CDESC = "contact_desc"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        //Admin Table
        val createTblAdmin = "CREATE TABLE $TBL_ADMIN($ADMIN_ID INT ,$ADMIN_IMAGE BLOB,$ADMIN_NAME TEXT NOT NULL,$ADMIN_EMAIL VARCHAR(128) PRIMARY KEY,$ADMIN_PASSWORD TEXT NOT NULL);"
        p0?.execSQL(createTblAdmin)

        //Faculty Table
        val createTblFaculty = "CREATE TABLE $TBL_FACULTY($FACULTY_ID INT PRIMARY KEY,$FACULTY_IMAGE BLOB,$FACULTY_NAME TEXT,$FACULTY_EMAIL VARCHAR(128) UNIQUE NOT NULL,$FACULTY_PASSWORD TEXT,$FACULTY_SUB VARCHAR(256));"
        p0?.execSQL(createTblFaculty)

        //Student Table
        val createTblStudent = "CREATE TABLE $TBL_STUDENT($STUDENT_ID INT PRIMARY KEY , $STUDENT_IMAGE BLOB,$STUDENT_NAME TEXT,$STUDENT_EMAIL VARCHAR(128) UNIQUE NOT NULL,$STUDENT_PASSWORD TEXT,$STUDENT_CLASS VARCHAR(256));"
        p0?.execSQL(createTblStudent)

        //Notice Table
        val createTblNotice = "CREATE TABLE $TBL_NOTICE($NOTICE_NAME TEXT PRIMARY KEY,$NOTICE_DES VARCHAR(512),$NOTICE_DATE TEXT);"
        p0?.execSQL(createTblNotice)

        //Assignment Table
        val createTblAssignment = "CREATE TABLE $TBL_ASSIGNMENT($ASSIGNMENT_NAME TEXT PRIMARY KEY,$ASSIGNMENT_SDATE TEXT,$ASSIGNMENT_TYPE VARCHAR(256));"
        p0?.execSQL(createTblAssignment)

        //ContactUs Table
        val createTblContact = "CREATE TABLE $TBL_CONTACT($CNAME TEXT,$CEMAIL TEXT PRIMARY KEY ,$CDESC VARCHAR(256));"
        p0?.execSQL(createTblContact)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        //Admin Table
        p0!!.execSQL("DROP TABLE IF EXISTS $TBL_ADMIN")
        onCreate(p0)

        //Faculty Table
        p0.execSQL("DROP TABLE IF EXISTS $TBL_FACULTY")
        onCreate(p0)

        //Student Table
        p0.execSQL("DROP TABLE IF EXISTS $TBL_STUDENT")
        onCreate(p0)

        //Notice Table
        p0  .execSQL("DROP TABLE IF EXISTS $TBL_NOTICE")
        onCreate(p0)

        //Assignment Table
        p0.execSQL("DROP TABLE IF EXISTS $TBL_ASSIGNMENT")
        onCreate(p0)

        //ContactUs Table
        p0.execSQL("DROP TABLE IF EXISTS $TBL_CONTACT")
        onCreate(p0)
    }

    //Inserting Data Of Admin
    fun InsertAdmin(adm : AdminModel):Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ADMIN_ID,adm.admin_id)
        contentValues.put(ADMIN_NAME,adm.admin_name)
        contentValues.put(ADMIN_EMAIL,adm.admin_email)
        contentValues.put(ADMIN_PASSWORD,adm.admin_password)

        val insertQuery = db.insert(TBL_ADMIN,null,contentValues)
        db.close()
        return insertQuery
    }

    fun InsertContactUs(adm : AdminModel):Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(CNAME,adm.cname)
        contentValues.put(CEMAIL,adm.cemail)
        contentValues.put(CDESC,adm.cdesc)

        val insertQuery = db.insert(TBL_CONTACT,null,contentValues)
        db.close()
        return insertQuery
    }


    //Inserting Data Of Faculty
    fun InsertFaculty(adm : AdminModel):Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(FACULTY_ID,adm.faculty_id)
        contentValues.put(FACULTY_IMAGE,adm.faculty_image)
        contentValues.put(FACULTY_NAME,adm.faculty_name)
        contentValues.put(FACULTY_EMAIL,adm.faculty_email)
        contentValues.put(FACULTY_PASSWORD,adm.faculty_password)
        contentValues.put(FACULTY_SUB,adm.faculty_sub)

        val insertQuery = db.insert(TBL_FACULTY,null,contentValues)
        db.close()
        return insertQuery
    }

    @SuppressLint("Range", "Recycle")
    fun getAdmin(id : Int) : ArrayList<AdminModel>{
        val db  = this.readableDatabase
        val adminImageList : ArrayList<AdminModel> = ArrayList()
        val cursor : Cursor?
        try{
            cursor = db.rawQuery("SELECT * FROM $TBL_ADMIN WHERE $ADMIN_ID = $id ",null)
        }catch (e:SQLiteException){
            e.printStackTrace()
            return adminImageList
        }
        if(cursor.moveToFirst()){
            do{
                val admin = AdminModel(
                    admin_id = cursor.getInt(cursor.getColumnIndex(ADMIN_ID)),
                    admin_image = cursor.getBlob(cursor.getColumnIndex("admin_image")),
                    admin_email = cursor.getString(cursor.getColumnIndex("admin_email")),
                    admin_name = cursor.getString(cursor.getColumnIndex("admin_name"))
                )
                adminImageList.add(admin)
            }while (cursor.moveToNext())
        }
        return adminImageList
    }


    @SuppressLint("Range", "Recycle")
    fun getFaculty(id : Int) : ArrayList<AdminModel>{
        val db  = this.readableDatabase
        val faculty : ArrayList<AdminModel> = ArrayList()
        val cursor : Cursor?
        try{
            cursor = db.rawQuery("SELECT * FROM $TBL_FACULTY WHERE $FACULTY_ID = $id ",null)
        }catch (e:SQLiteException){
            e.printStackTrace()
            return faculty
        }
        if(cursor.moveToFirst()){
            do{
                val fac = AdminModel(
                    faculty_id = cursor.getInt(cursor.getColumnIndex(FACULTY_ID)),
                    faculty_image = cursor.getBlob(cursor.getColumnIndex(FACULTY_IMAGE)),
                    faculty_name = cursor.getString(cursor.getColumnIndex(FACULTY_NAME)),
                    faculty_email = cursor.getString(cursor.getColumnIndex(FACULTY_EMAIL)),
                    faculty_sub = cursor.getString(cursor.getColumnIndex(FACULTY_SUB))
                )
                faculty.add(fac)
            }while (cursor.moveToNext())
        }
        return faculty
    }

    @SuppressLint("Range", "Recycle")
    fun getStudent(id : Int) : ArrayList<AdminModel>{
        val db  = this.readableDatabase
        val student : ArrayList<AdminModel> = ArrayList()
        val cursor : Cursor?
        try{
            cursor = db.rawQuery("SELECT * FROM $TBL_STUDENT WHERE $STUDENT_ID = $id ",null)
        }catch (e:SQLiteException){
            e.printStackTrace()
            return student
        }
        if(cursor.moveToFirst()){
            do{
                val std = AdminModel(
                    student_id = cursor.getInt(cursor.getColumnIndex(STUDENT_ID)),
                    student_image = cursor.getBlob(cursor.getColumnIndex(STUDENT_IMAGE)),
                    student_name = cursor.getString(cursor.getColumnIndex(STUDENT_NAME)),
                    student_email = cursor.getString(cursor.getColumnIndex(STUDENT_EMAIL)),
                    student_class = cursor.getString(cursor.getColumnIndex(STUDENT_CLASS))
                )
                student.add(std)
            }while (cursor.moveToNext())
        }
        return student
    }

    fun updateImage(adm: AdminModel): Int {

        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(ADMIN_IMAGE,adm.admin_image)
        val email = adm.admin_email
        val uploadImage = db.update(TBL_ADMIN,contentValues, "$ADMIN_EMAIL = '$email'",null)
        db.close()
        return  uploadImage
    }


    //Displaying Data Of Faculty
    @SuppressLint("Range", "Recycle")
    fun getAllFaculty() : ArrayList<AdminModel>
    {
        val admList : ArrayList<AdminModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_FACULTY"
        val db = this.readableDatabase

        val cursor : Cursor?

        try{
            cursor = db.rawQuery(selectQuery,null)
        } catch (e : Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id : Int
        var image:ByteArray?
        var name : String
        var email : String
        var password : String
        var subject : String

        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex(FACULTY_ID))
                image = cursor.getBlob(cursor.getColumnIndex(FACULTY_IMAGE))
                name = cursor.getString(cursor.getColumnIndex("faculty_name"))
                email = cursor.getString(cursor.getColumnIndex("faculty_email"))
                password = cursor.getString(cursor.getColumnIndex("faculty_password"))
                subject = cursor.getString(cursor.getColumnIndex("faculty_sub"))

                val adm = AdminModel(faculty_id = id,faculty_image = image,faculty_name = name, faculty_email = email, faculty_password = password, faculty_sub = subject)
                admList.add(adm)

            } while (cursor.moveToNext())
        }
        return admList
    }


    //Deleting Data Of Faculty
    fun DeleteFaculty(email: String): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(FACULTY_EMAIL,email)

        val DeleteQuery = db.delete(TBL_FACULTY, "$FACULTY_EMAIL = '$email' " ,null)
        db.close()
        return DeleteQuery
    }

    //Updating Data Of Faculty
    fun updateFacultyById(adm: AdminModel): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(FACULTY_ID,adm.faculty_id)
        contentValues.put(FACULTY_NAME,adm.faculty_name)
        contentValues.put(FACULTY_SUB,adm.faculty_sub)
        contentValues.put(FACULTY_EMAIL,adm.faculty_email)
        contentValues.put(FACULTY_PASSWORD,adm.faculty_password)

        val id = adm.faculty_id
        val UpdateQuery = db.update(TBL_FACULTY,contentValues,"$FACULTY_ID = $id",null)
        db.close()
        return UpdateQuery
    }
    //Update Admin
    fun updateAdminById(adm: AdminModel): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ADMIN_ID,adm.admin_id)
        contentValues.put(ADMIN_NAME,adm.admin_name)
        contentValues.put(ADMIN_EMAIL,adm.admin_email)

        val id = adm.admin_id
        val UpdateQuery = db.update(TBL_ADMIN,contentValues,"$ADMIN_ID = $id",null)
        db.close()
        return UpdateQuery
    }

    //Inserting Data Of Student
    fun InsertStudent(adm : AdminModel):Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(STUDENT_ID,adm.student_id)
        contentValues.put(STUDENT_IMAGE,adm.student_image)
        contentValues.put(STUDENT_NAME,adm.student_name)
        contentValues.put(STUDENT_EMAIL,adm.student_email)
        contentValues.put(STUDENT_PASSWORD,adm.student_password)
        contentValues.put(STUDENT_CLASS,adm.student_class)

        val insertQuery = db.insert(TBL_STUDENT,null,contentValues)
        db.close()
        return insertQuery
    }

    //Displaying Data Of Student
    @SuppressLint("Range", "Recycle")
    fun getAllStudent() : ArrayList<AdminModel>
    {
        val admList : ArrayList<AdminModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_STUDENT ORDER BY $STUDENT_CLASS"
        val db = this.readableDatabase

        val cursor : Cursor?

        try{
            cursor = db.rawQuery(selectQuery,null)
        } catch (e : Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id : Int
        var image : ByteArray?
        var name : String
        var email : String
        var password : String
        var stud_class : String

        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex(STUDENT_ID))
                image = cursor.getBlob(cursor.getColumnIndex(STUDENT_IMAGE))
                name = cursor.getString(cursor.getColumnIndex("student_name"))
                email = cursor.getString(cursor.getColumnIndex("student_email"))
                password = cursor.getString(cursor.getColumnIndex("student_password"))
                stud_class = cursor.getString(cursor.getColumnIndex("student_class"))

                val adm = AdminModel(student_id = id,student_name = name, student_email = email, student_password = password, student_class = stud_class, student_image = image)
                admList.add(adm)

            } while (cursor.moveToNext())
        }
        return admList
    }


    //Deleting Data Of Student
    fun DeleteStudent(email: String): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(STUDENT_EMAIL,email)

        val DeleteQuery = db.delete(TBL_STUDENT,"$STUDENT_EMAIL = '$email' ",null)
        db.close()
        return DeleteQuery
    }

    //Updating Data Of Student
    fun updateStudentById(adm: AdminModel): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(STUDENT_EMAIL,adm.student_email)
        contentValues.put(STUDENT_NAME,adm.student_name)
        contentValues.put(STUDENT_PASSWORD,adm.student_password)
        contentValues.put(STUDENT_CLASS,adm.student_class)

        val id = adm.student_id
        val UpdateQuery = db.update(TBL_STUDENT,contentValues,"$STUDENT_ID = $id",null)
        db.close()
        return UpdateQuery
    }


    //Inserting Notices
    fun InsertNotice(adm : AdminModel):Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(NOTICE_NAME,adm.notice_name)
        contentValues.put(NOTICE_DES,adm.notice_des)
        contentValues.put(NOTICE_DATE,adm.notice_date)

        val insertQuery = db.insert(TBL_NOTICE,null,contentValues)
        db.close()
        return insertQuery
    }

    //Displaying Notices
    @SuppressLint("Range", "Recycle")
    fun getAllNotice(): ArrayList<AdminModel> {
        val admList : ArrayList<AdminModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_NOTICE"
        val db = this.writableDatabase

        val cursor : Cursor?

        try{
            cursor = db.rawQuery(selectQuery,null)
        } catch (e : Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var name : String
        var des : String
        var ndate : String

        if(cursor.moveToFirst()){
            do{

                name = cursor.getString(cursor.getColumnIndex("notice_name"))
                des = cursor.getString(cursor.getColumnIndex("notice_des"))
                ndate = cursor.getString(cursor.getColumnIndex("notice_date"))

                val adm = AdminModel(notice_name = name, notice_des = des, notice_date = ndate)
                admList.add(adm)

            } while (cursor.moveToNext())
        }
        return admList
    }

    //Delete Notices
    fun DeleteNotice(name_notice: String): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(NOTICE_NAME,name_notice)

        val DeleteQuery = db.delete(TBL_NOTICE,"notice_name=$NOTICE_NAME",null)
        db.close()
        return DeleteQuery
    }


    //Inserting Assignments
    fun InsertAssignment(adm: AdminModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ASSIGNMENT_NAME,adm.assignment_name)
        contentValues.put(ASSIGNMENT_SDATE,adm.assignment_sdate)
        contentValues.put(ASSIGNMENT_TYPE,adm.assignment_type)

        val insertQuery = db.insert(TBL_ASSIGNMENT,null,contentValues)
        db.close()
        return insertQuery
    }

    //Displaying Assignments
    @SuppressLint("Range", "Recycle")
    fun getAllAssignment(): ArrayList<AdminModel> {
        val admList : ArrayList<AdminModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_ASSIGNMENT"
        val db = this.writableDatabase

        val cursor : Cursor?

        try{
            cursor = db.rawQuery(selectQuery,null)
        } catch (e : Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var name : String
        var sdate : String
        var stype : String

        if(cursor.moveToFirst()){
            do{

                name = cursor.getString(cursor.getColumnIndex("assignment_name"))
                sdate = cursor.getString(cursor.getColumnIndex("assignment_sdate"))
                stype = cursor.getString(cursor.getColumnIndex("assignment_type"))

                val adm = AdminModel(assignment_name = name, assignment_sdate = sdate, assignment_type = stype)
                admList.add(adm)

            } while (cursor.moveToNext())
        }
        return admList
    }

    //facultyLogin

    @SuppressLint("Range", "Recycle")
    fun isFaculty(email : String) : ArrayList<AdminModel>{
        val db  = this.readableDatabase
        val facultyList : ArrayList<AdminModel> = ArrayList()
        val cursor : Cursor?
        try{
            cursor = db.rawQuery("SELECT * FROM $TBL_FACULTY WHERE $FACULTY_EMAIL = '$email' ",null)
        }catch (e:SQLiteException){
            e.printStackTrace()
            return facultyList
        }
        if(cursor.moveToFirst()){
            do{
                val faculty = AdminModel(
                    faculty_id = cursor.getInt(cursor.getColumnIndex("faculty_id")),
                    faculty_email = cursor.getString(cursor.getColumnIndex("faculty_email")),
                    faculty_password = cursor.getString(cursor.getColumnIndex("faculty_password"))
                )
                facultyList.add(faculty)
            }while (cursor.moveToNext())
        }
        return facultyList
    }

    @SuppressLint("Range", "Recycle")
    fun isStudent(email : String) : ArrayList<AdminModel>{
        val db  = this.readableDatabase
        val studentList : ArrayList<AdminModel> = ArrayList()
        val cursor : Cursor?
        try{
            cursor = db.rawQuery("SELECT * FROM $TBL_STUDENT WHERE $STUDENT_EMAIL = '$email' ",null)
        }catch (e:SQLiteException){
            e.printStackTrace()
            return studentList
        }
        if(cursor.moveToFirst()){
            do{
                val faculty = AdminModel(
                    student_id = cursor.getInt(cursor.getColumnIndex("student_id")),
                    student_email = cursor.getString(cursor.getColumnIndex("student_email")),
                    student_password = cursor.getString(cursor.getColumnIndex("student_password"))
                )
                studentList.add(faculty)
            }while (cursor.moveToNext())
        }
        return studentList
    }

    @SuppressLint("Range", "Recycle")
    fun checkAdmin(email : String) : ArrayList<AdminModel>{
        val db  = this.readableDatabase
        val adminList : ArrayList<AdminModel> = ArrayList()
        val cursor : Cursor?
        try{
            cursor = db.rawQuery("SELECT * FROM $TBL_ADMIN WHERE $ADMIN_EMAIL = '$email' ",null)
        }catch (e:SQLiteException){
            e.printStackTrace()
            return adminList
        }
        if(cursor.moveToFirst()){
            do{
                val admin = AdminModel(
                    admin_id = cursor.getInt(cursor.getColumnIndex(ADMIN_ID)),
                    admin_email = cursor.getString(cursor.getColumnIndex(ADMIN_EMAIL)),
                    admin_name = cursor.getString(cursor.getColumnIndex(ADMIN_NAME)),
                    admin_password = cursor.getString(cursor.getColumnIndex(ADMIN_PASSWORD))
                )
                adminList.add(admin)
            }while (cursor.moveToNext())
        }
        return adminList
    }

    @SuppressLint("Range", "Recycle")
    fun chkPasswdStudent(id : Int) : ArrayList<AdminModel>{
        val db  = this.readableDatabase
        val studentList : ArrayList<AdminModel> = ArrayList()
        val cursor : Cursor?
        try{
            cursor = db.rawQuery("SELECT * FROM $TBL_STUDENT WHERE $STUDENT_ID = $id ",null)
        }catch (e:SQLiteException){
            e.printStackTrace()
            return studentList
        }
        if(cursor.moveToFirst()){
            do{
                val student = AdminModel(
                    student_id = cursor.getInt(cursor.getColumnIndex("student_id")),
                    student_password = cursor.getString(cursor.getColumnIndex("student_password"))
                )
                studentList.add(student)
            }while (cursor.moveToNext())
        }
        return studentList
    }

    @SuppressLint("Range", "Recycle")
    fun chkPasswdFaculty(id : Int) : ArrayList<AdminModel>{
        val db  = this.readableDatabase
        val facultyList : ArrayList<AdminModel> = ArrayList()
        val cursor : Cursor?
        try{
            cursor = db.rawQuery("SELECT * FROM $TBL_FACULTY WHERE $FACULTY_ID = $id ",null)
        }catch (e:SQLiteException){
            e.printStackTrace()
            return facultyList
        }
        if(cursor.moveToFirst()){
            do{
                val faculty = AdminModel(
                    faculty_id = cursor.getInt(cursor.getColumnIndex(FACULTY_ID)),
                    faculty_password = cursor.getString(cursor.getColumnIndex(FACULTY_PASSWORD))
                )
                facultyList.add(faculty)
            }while (cursor.moveToNext())
        }
        return facultyList
    }

}