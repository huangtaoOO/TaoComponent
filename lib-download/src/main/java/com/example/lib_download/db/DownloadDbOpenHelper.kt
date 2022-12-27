package com.example.lib_download.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.lib_download.DownloadConfig

/**
 * Author: huangtao
 * Date: 2022/12/27
 * Desc: 下载的SQLiteOpenHelper
 */
class DownloadDbOpenHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "downloadDB.db"
        private const val DATABASE_VERSION = 1


        const val TABLE_NAME = "downloadTask"
        const val FIELDS_URL = "url"
        const val FIELDS_TASK_SIZE = "task_size"
        const val FIELDS_START_POS = "start_pos"
        const val FIELDS_END_POS = "end_pos"
        const val FIELDS_CURRENT_POS = "current_pos"
        const val FIELDS_SAVE_FILE = "save_file"
        const val FIELDS_STATUS = "status"
        const val FIELDS_COMPLETE_SIZE = "complete_size"
        const val SQL_CREATE =
            "CREATE TABLE IF NOT EXISTS `${TABLE_NAME}` " +
                    "(`${FIELDS_URL}` TEXT NOT NULL, " +
                    "`${FIELDS_TASK_SIZE}` INTEGER NOT NULL, " +
                    "`${FIELDS_START_POS}` INTEGER NOT NULL, " +
                    "`${FIELDS_END_POS}` INTEGER NOT NULL, " +
                    "`${FIELDS_CURRENT_POS}` INTEGER NOT NULL, " +
                    "`${FIELDS_SAVE_FILE}` TEXT NOT NULL, " +
                    "`${FIELDS_STATUS}` TEXT NOT NULL, " +
                    "`${FIELDS_COMPLETE_SIZE}` INTEGER NOT NULL," +
                    " PRIMARY KEY(`${FIELDS_URL}`, `${FIELDS_START_POS}`))"

        private const val SQL_DELETE = "DROP TABLE `${TABLE_NAME}`"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        Log.i(DownloadConfig.TAG,"sql=${SQL_CREATE}")
        db?.execSQL(SQL_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE)
        onCreate(db)
    }
}