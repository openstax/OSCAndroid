/**
 * Copyright (c) 2013 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.activity

import android.Manifest
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Html
import org.openstaxcollege.android.R
import org.openstaxcollege.android.beans.Content
import org.openstaxcollege.android.utils.MenuUtil
import org.openstaxcollege.android.utils.OSCUtil

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import org.openstaxcollege.android.fragment.WebviewFragment

import java.io.File


/**
 * Activity to view selected content in a web browser.
 *
 * @author Ed Woodward
 */
class WebViewActivity : AppCompatActivity()
{
    private var content: Content? = null
    private val REQUEST = 1336

    private var saveLocation = true

    private val DEVELOPER_MODE = true




    public override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_view)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        if(Build.VERSION.SDK_INT >= 24)
        {
            supportActionBar!!.title = Html.fromHtml(getString(R.string.app_name_html), Html.FROM_HTML_MODE_LEGACY)
        }
        else
        {
            supportActionBar!!.title = Html.fromHtml(getString(R.string.app_name_html))
        }
        content = intent.getSerializableExtra(getString(R.string.webcontent)) as Content

        if (savedInstanceState == null)
        {
            val transaction = supportFragmentManager.beginTransaction()
            val fragment = WebviewFragment()
            transaction.replace(R.id.sample_content_fragment, fragment)
            transaction.commit()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)
    {

        if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {

            displayDownloadAlert(content)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration)
    {
        super.onConfigurationChanged(newConfig)
    }


    override fun onResume()
    {
        super.onResume()
        saveLocation = true

    }

    /**
     * Displays alert telling user where the downloaded files are located, the size of the files to download and confirms download.
     * If download is confirmed, DownloadHandler is called.
     * @param currentContent - Content - current content object
     */
    //@TODO Move download logic to logic class
    private fun displayDownloadAlert(currentContent: Content?)
    {
        val context = this

        val message = getString(R.string.pdf_download_message)

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(R.string.download))
        alertDialog.setMessage(message)
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.ok)) { dialog, which ->
            if(OSCUtil.isConnected(context))
            {

                val cnxDir = File(Environment.getExternalStorageDirectory(), "OpenStax/")
                if(!cnxDir.exists())
                {
                    cnxDir.mkdir()
                }

                val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                //Log.d("WeviewLogic","title: "+currentContent.getBookTitle());
                val pdfUrl = content!!.pdfUrl

                if(pdfUrl == null || pdfUrl == "")
                {
                    makePDFToast()
                }
                else
                {
                    //Log.d("Webview","PDF URL: " + pdfUrl);
                    val uri = Uri.parse(pdfUrl)

                    val request = DownloadManager.Request(uri)
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/" + MenuUtil.getTitle(currentContent!!.bookTitle) + ".pdf")
                    request.setTitle(currentContent.bookTitle + ".pdf")
                    request.setDescription("Downloading " + currentContent.bookTitle + ".pdf")
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    try
                    {
                        dm.enqueue(request)
                    } catch(iae: IllegalArgumentException)
                    {
                        createDialog(context)
                    }

                }

            }
            else
            {
                OSCUtil.makeNoDataToast(context)
            }
        }
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.cancel)) { dialog, which ->
            //do nothing
        }
        alertDialog.show()
    }

    private fun enableDownloadManager(context: Context)
    {
        try
        {
            val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:com.android.providers.downloads")
            context.startActivity(intent)
        } catch(e: ActivityNotFoundException)
        {
            val intent = Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS)
            context.startActivity(intent)
        }

    }

    private fun createDialog(context: Context)
    {
        AlertDialog.Builder(context)
                .setTitle(getString(R.string.download_manager))
                .setMessage(getString(R.string.download_mgr_missing))
                .setPositiveButton(getString(R.string.ok)) { dialog, which -> enableDownloadManager(context) }
                .setNegativeButton(getString(R.string.cancel)) { dialog, which ->
                    //do nothing
                }
                .setCancelable(true)
                .create()
                .show()
    }

    private fun makePDFToast()
    {
        Toast.makeText(this, getString(R.string.pdf_url_missing), Toast.LENGTH_LONG).show()
    }

    companion object
    {

        val STORAGE_PERMS = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

}
