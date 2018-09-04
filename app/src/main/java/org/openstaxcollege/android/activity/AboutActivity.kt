package org.openstaxcollege.android.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View

import org.openstaxcollege.android.R
import org.openstaxcollege.android.fragment.AboutFragment

class AboutActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.about)


        if (savedInstanceState == null)
        {
            val transaction = supportFragmentManager.beginTransaction()
            val fragment = AboutFragment()
            transaction.replace(R.id.sample_content_fragment, fragment)
            transaction.commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        if (item.itemId == android.R.id.home)
        {
            val mainIntent = Intent(applicationContext, BookshelfActivity::class.java)
            mainIntent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(mainIntent)

        }
        return true
    }
}
