package com.hfad.bitsandpizzas

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout

@Suppress("DEPRECATION")
class MainActivity : Activity() {

    var shareActionProvider: ShareActionProvider? = null

    lateinit var titles: Array<String>
    lateinit var drawerList: ListView
    lateinit var drawerLayout: DrawerLayout
    private lateinit var drawerToggle: ActionBarDrawerToggle
    var currentPosition: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.drawer_layout)
        drawerToggle = object : ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        ) {
            //Called when a drawer has settled in a completely closed state
            override fun onDrawerClosed(view: View) {
                super.onDrawerClosed(view)
                invalidateOptionsMenu()
                var toast = Toast.makeText(applicationContext, "Drawer closed", Toast.LENGTH_LONG)
                toast.show()
            }

            //Called when a drawer has settled in a completely open state
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                invalidateOptionsMenu()
                var toast = Toast.makeText(applicationContext, "Drawer opened", Toast.LENGTH_LONG)
                toast.show()
            }

        }
        drawerLayout.setDrawerListener(drawerToggle)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeButtonEnabled(true)
        titles = resources.getStringArray(R.array.titles)
        drawerList = findViewById(R.id.drawer)
        var arrayAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, titles!!)
        drawerList.adapter = arrayAdapter
        drawerList.onItemClickListener = DrawerItemClickListener()

        fragmentManager.addOnBackStackChangedListener {


            val fragMan = fragmentManager
            val fragment = fragMan.findFragmentByTag("visible_fragment")
            if (fragment is TopFragment) {
                currentPosition = 0
            }
            if (fragment is PizzaFragment) {
                currentPosition = 1
            }
            if (fragment is PastaFragment) {
                currentPosition = 2
            }
            if (fragment is StoresFragment) {
                currentPosition = 3
            }
            setActionBarTitle(currentPosition)
            drawerList.setItemChecked(currentPosition, true)

        }




        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("position")
            setActionBarTitle(currentPosition)
        } else {
            selectItem(0)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("position", currentPosition)
    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        drawerToggle.onConfigurationChanged(newConfig)
    }

    //Called whenever we call invalidateOptionsMenu()
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        //If the drawer is open, hide action items related to the content view
        val drawerOpen: Boolean = drawerLayout.isDrawerOpen(drawerList)
        menu!!.findItem(R.id.action_share).setVisible(!drawerOpen)
        return super.onPrepareOptionsMenu(menu)

    }


    inner class DrawerItemClickListener : OnItemClickListener {
        override fun onItemClick(
            adapterView: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            selectItem(position)
        }
    }


    fun selectItem(position: Int) {
        currentPosition = position

        var fragment = android.app.Fragment()
        when (position) {
            1 -> {
                fragment = PizzaFragment()
            }
            2 -> {
                fragment = PastaFragment()
            }
            3 -> {
                fragment = StoresFragment()
            }

            else -> {
                fragment = TopFragment()
            }

        }
        val ft = fragmentManager.beginTransaction()
        ft.replace(R.id.content_frame, fragment, "visible_fragment")
        ft.addToBackStack(null)
        ft.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        ft.commit()
        setActionBarTitle(position)
        drawerLayout.closeDrawer(drawerList)

    }

    fun fragmentBackStackSetup() {

    }


    fun setActionBarTitle(position: Int) {
        var title: String
        title = if (position == 0) {
            resources.getString(R.string.app_name)
        } else {
            titles!![position]
        }
        actionBar!!.title = title
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_main, menu)
        var menuItem = menu!!.findItem(R.id.action_share)
        shareActionProvider = menuItem.actionProvider as ShareActionProvider?
        setIntent("This is example text")
        return super.onCreateOptionsMenu(menu)

    }

    fun setIntent(text: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, text)
        shareActionProvider!!.setShareIntent(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }

        intent = Intent(applicationContext, OrderActivity::class.java)

        when (item.itemId) {
            R.id.action_create_order -> startActivity(intent)
            R.id.action_settings -> return true
        }
        return super.onOptionsItemSelected(item)
    }
}
