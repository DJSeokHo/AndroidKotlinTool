package com.swein.androidkotlintool.framework.util.dbcrypt

import android.content.Context
import com.swein.androidkotlintool.framework.util.log.ILog
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SQLiteOpenHelper
import java.io.File
import java.io.IOException

class SQLCipherHelper {

    companion object {

        fun autoEncryptDB(
            dbHelper: SQLiteOpenHelper,
            context: Context,
            databaseName: String,
            dbPassphase: String
        ): SQLiteDatabase {
            var db: SQLiteDatabase? = null

            try {
                db = dbHelper.getWritableDatabase(dbPassphase)
            } catch (e: Exception) {

                e.printStackTrace()
                db = dbHelper.getWritableDatabase("")
                try {

                    db!!.close()

                    encryptDB(context, databaseName, dbPassphase)

                    db = dbHelper.getWritableDatabase(dbPassphase)
                } catch (e1: Exception) {

                    e1.printStackTrace()
                    db!!.close()
                } finally {
                    db!!.close()
                }
            } finally {
                db!!.close()
            }

            return db
        }


        @Throws(IOException::class)
        private fun encryptDB(ctxt: Context, dbName: String, passphrase: String) {

            val originalFile = ctxt.getDatabasePath(dbName)

            if (originalFile.exists()) {
                val newFile = File.createTempFile(
                    "sqlcipherutils", "tmp",
                    ctxt.cacheDir
                )
                var db = SQLiteDatabase.openDatabase(
                    originalFile.absolutePath, "", null,
                    SQLiteDatabase.OPEN_READWRITE
                )

                db.rawExecSQL(
                    String.format(
                        "ATTACH DATABASE '%s' AS encrypted KEY '%s';",
                        newFile.absolutePath, passphrase
                    )
                )
                db.rawExecSQL("SELECT sqlcipher_export('encrypted')")
                db.rawExecSQL("DETACH DATABASE encrypted;")

                val version = db.getVersion()

                db.close()

                db = SQLiteDatabase.openDatabase(newFile.absolutePath, passphrase, null, SQLiteDatabase.OPEN_READWRITE)
                db.setVersion(version)
                db.close()

                originalFile.delete()
                newFile.renameTo(originalFile)
            }
        }


        fun logDBSQLCipherVersion(db: SQLiteDatabase) {
            val cursor = db.rawQuery("PRAGMA cipher_version;", null)
            cursor.moveToFirst()
            val version = cursor.getString(0)
            ILog.debug("version", "SQLCipher version $version")
            cursor.close()
        }

    }

}