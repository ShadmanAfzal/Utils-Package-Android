package com.example.info

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import java.io.File

class info {
        fun getFileExtension(uri: Uri, context: Context): String? = when (uri.scheme) {
            ContentResolver.SCHEME_FILE -> File(uri.path!!).extension
            ContentResolver.SCHEME_CONTENT -> getCursorContent(uri,context);
            else -> null
        }

        private fun getCursorContent(uri: Uri, context: Context): String? = try {
            context.contentResolver.query(uri, null, null, null, null)?.let {cursor ->
                cursor.run {
                    val mimeTypeMap: MimeTypeMap = MimeTypeMap.getSingleton()
                    if (moveToFirst()) mimeTypeMap.getExtensionFromMimeType(context.contentResolver.getType(uri))
                    else null
                }.also { cursor.close() }
            }
        } catch (e: Exception) {
            null
        }

        fun getName(uri: Uri): String
        {
            return File(uri?.path!!).name;
        }

        fun isImage(type: String) : Boolean
        {
            return (type == "jpg" || type=="png" || type == "jpeg" || type=="bmp" || type=="tiff" || type=="webp" || type=="heif");
        }

        fun isFile(type: String) : Boolean
        {
            return (type == "doc" || type == "pdf" || type=="xlsx" || type == "pptx" || type=="docx" || type=="ppt" || type=="json");
        }
}