package org.openstaxcollege.android.tasks

import android.os.AsyncTask
import org.openstaxcollege.android.logic.WebviewLogic

class FetchPdfUrlTask(val bookTitle: String, val pdfListener:PdfTaskCallback): AsyncTask<String, Void, String>()
{

    override fun doInBackground(vararg p0: String?): String
    {
        val wl = WebviewLogic()
        val pdfUrl = wl.getPDFUrl(bookTitle)
        return pdfUrl
    }

    override fun onPostExecute(result: String)
    {
        super.onPostExecute(result)

        pdfListener.onResultReceived(result)

    }

    interface PdfTaskCallback
    {
        fun onResultReceived(result: String)
    }
}