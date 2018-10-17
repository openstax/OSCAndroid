package org.openstaxcollege.android.tasks

import android.os.AsyncTask
import org.openstaxcollege.android.logic.WebviewLogic

class FetchPdfUrlTask(val bookTitle: String?, val pdfListener:PdfTaskCallback): AsyncTask<String, Void, String>()
{

    override fun doInBackground(vararg p0: String?): String
    {
        val wl = WebviewLogic()
        var pdfUrl = ""
        var title:String? = bookTitle
        if(title != null)
        {
            if(title == "Biology For AP® Courses")
            {
                title = "Biology for AP® Courses"
            }
            pdfUrl = wl.getPDFUrl(title)

            if(pdfUrl == "" && title == "Fizyka dla szkół wyższych. Tom 3")
            {
                pdfUrl = wl.getPDFUrl("Fizyka dla szkół wyższych Tom 3")
            }
            else if(pdfUrl == "" && title == "Fizyka dla szkół wyższych. Tom 2")
            {
                pdfUrl = wl.getPDFUrl("Fizyka dla szkół wyższych Tom 2")
            }
            else if(pdfUrl == "" && title == "Fizyka dla szkół wyższych. Tom 1")
            {
                pdfUrl = wl.getPDFUrl("Fizyka dla szkół wyższych Tom 1")
            }
        }

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