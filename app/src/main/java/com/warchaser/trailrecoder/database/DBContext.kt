package com.warchaser.trailrecoder.database

import android.content.Context
import android.content.ContextWrapper
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import com.warchaser.baselib.tools.FileUtils
import com.warchaser.trailrecoder.tools.Constant
import java.io.File

class DBContext(base : Context) : ContextWrapper(base){

    override fun getDatabasePath(name: String?): File {
        val rootDirFile = getExternalFilesDir(android.os.Environment.DIRECTORY_DOCUMENTS)
        rootDirFile?.run {
            Constant.run {
                SANDBOX_PATH = absolutePath
                DB_PATH = SANDBOX_PATH + "DB/"
                FileUtils.makeDirsOnNotExist(DB_PATH)
            }
        }

        return FileUtils.createFileOnNotExist(Constant.DB_PATH + Constant.DB_NAME)
    }

    override fun openOrCreateDatabase(
        name: String?,
        mode: Int,
        factory: SQLiteDatabase.CursorFactory?
    ): SQLiteDatabase {
        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null)
    }

    override fun openOrCreateDatabase(
        name: String?,
        mode: Int,
        factory: SQLiteDatabase.CursorFactory?,
        errorHandler: DatabaseErrorHandler?
    ): SQLiteDatabase {
        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null)
    }

}