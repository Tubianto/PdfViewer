package com.tubianto.pdfviewer

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mindev.mindev_pdfviewer.MindevPDFViewer
import com.mindev.mindev_pdfviewer.PdfScope
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url = "https://tubianto.com/sertifikat_android_pemula.pdf"
        pdf.initializePDFDownloader(url, statusListener)
        lifecycle.addObserver(PdfScope())
    }

    private val statusListener = object : MindevPDFViewer.MindevViewerStatusListener {
        override fun onStartDownload() {
            pb_loading.visibility = View.VISIBLE
        }

        override fun onPageChanged(position: Int, total: Int) {
            tv_pages.text = "${position + 1}/$total"
        }

        override fun onProgressDownload(currentStatus: Int) {
            pb_loading.visibility = View.VISIBLE
        }

        override fun onSuccessDownLoad(path: String) {
            pb_loading.visibility = View.GONE
            pdf.fileInit(path)
        }

        override fun onFail(error: Throwable) {
            Toast.makeText(this@MainActivity, error.toString(), Toast.LENGTH_SHORT).show()
        }

        override fun unsupportedDevice() {
            Toast.makeText(this@MainActivity, "Device not supported", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroy() {
        pdf.pdfRendererCore?.clear()
        super.onDestroy()
    }
}