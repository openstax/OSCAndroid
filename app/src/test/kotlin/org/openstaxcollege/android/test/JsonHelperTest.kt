package org.openstaxcollege.android.test

import android.content.Context
import org.junit.Test
import org.junit.runner.RunWith
import org.openstaxcollege.android.beans.AboutList
import org.openstaxcollege.android.handlers.JsonHelper
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.runners.MockitoJUnitRunner

private const val FAKE_STRING = "HELLO WORLD"

@RunWith(MockitoJUnitRunner::class)
class JsonHelperTest
{
    @Mock
    private lateinit var mockContext: Context

    @Test
    fun testGetAboutData()
    {
        val jsonHelper = JsonHelper()
        `when`(mockContext.getString(R.string.abc_action_mode_done))
                .thenReturn(FAKE_STRING)
        //val context = ApplicationProvider.getApplicationContext<Context>()
        val aboutList = jsonHelper.getAboutData(mockContext, AboutList::class.java, "aboutList.json")
        for(item in aboutList!!.aboutList)
        {
            println(item.title)
            println(item.blurb)

        }

    }
}