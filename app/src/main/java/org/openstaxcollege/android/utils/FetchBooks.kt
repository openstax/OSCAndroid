/**
 * Copyright (c) 2018 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.utils

import android.util.Log
import com.google.gson.Gson
import org.openstaxcollege.android.beans.Response
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class FetchBooks
{

    fun getBooks(): Response?
    {
        var bis: BufferedInputStream? = null
        var reader: BufferedReader? = null

        try
        {
            val URL = URL("https://openstax.org/apps/cms/api/v2/pages/30/?format=json")
            val connect = URL.openConnection() as HttpURLConnection

            connect.connect()
            val responseCode: Int = connect.responseCode
            //            Log.d(Tag, "ResponseCode" + ResponseCode)
            val gson = Gson()

            if (responseCode == 200)
            {
                bis = BufferedInputStream(connect.inputStream)
                reader = BufferedReader(InputStreamReader(bis))
                return gson.fromJson(reader, Response::class.java)
            }
        }
        catch (Ex: Exception)
        {
            //Log.d("", "Error in doInBackground " + Ex.message);
            println("exception: " + Ex.toString())
            println("msg: " + Ex.message)
            println("stacktrace: " + Ex.stackTrace.contentToString())
        }
        finally
        {
            if(bis != null)
            {
                bis.close()
            }
            if(reader != null)
            {
                reader.close()
            }
        }
        return null

    }


}