package com.saad.invitation.views.subviews

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.saad.invitation.R
import com.saad.invitation.learning.ImageSaver
import com.saad.invitation.learning.debug_tag
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class MyBottomSheetDialogFragment(private val bitmap: Bitmap, private val context: Context) :
    BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textSaveAsPdf = view.findViewById<TextView>(R.id.textSaveAsPdf)
        val textSaveAsImage = view.findViewById<TextView>(R.id.textSaveAsImage)

        textSaveAsPdf.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                savePDF(bitmap)
            }
            dismiss() // Close the bottom sheet after handling the click
        }

        textSaveAsImage.setOnClickListener {
            saveImage(bitmap)
            dismiss() // Close the bottom sheet after handling the click
        }
    }

    private suspend fun savePDF(bitmap: Bitmap) {
        withContext(Dispatchers.IO) {
            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, 595, 842, true)

            val pdfDocument = PdfDocument()
            val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()

            val page = pdfDocument.startPage(pageInfo)
            page.canvas.drawBitmap(scaledBitmap, 0F, 0F, null)
            pdfDocument.finishPage(page)

            val timestamp = System.currentTimeMillis()
            val fileName = "bitmapPdf_$timestamp.pdf"

            val filePath = File(context.getExternalFilesDir(null), fileName)
            Log.d(debug_tag, "File Path: ${filePath.absolutePath}")
            pdfDocument.writeTo(FileOutputStream(filePath))
            pdfDocument.close()

            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Pdf Saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveImage(bitmap: Bitmap) {
        val imageSaver = ImageSaver(context)
        val savedImagePath = imageSaver.saveImage(bitmap)

        if (savedImagePath != null) {
            Toast.makeText(context, "Image saved: $savedImagePath", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Failed to save image", Toast.LENGTH_SHORT).show()
        }
    }
}
