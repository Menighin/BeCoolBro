package com.onsoftwares.zensource.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.FrameLayout
import android.widget.ListView
import android.widget.Toast

import com.onsoftwares.zensource.R
import com.onsoftwares.zensource.adapters.IconListAdapter

import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class NavigationDrawerFragment : Fragment() {

    var drawerToggle: ActionBarDrawerToggle? = null
        private set
    private var mDrawerLayout: DrawerLayout? = null
    private var mListView: ListView? = null
    private var navigationItems: ArrayList<IconListAdapter.ItemModel>? = null
    private var mFrameContent: FrameLayout? = null

    private var mUserLearnDrawer: Boolean = false
    private var mFromSavedInstanceState: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mUserLearnDrawer = java.lang.Boolean.valueOf(readFromPreferences(activity, KEY_USER_LEARN_DRAWER, "false"))!!
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater!!.inflate(R.layout.fragment_navigation_drawer, container, false)

        mListView = v.findViewById(R.id.navigation_drawer_list) as ListView

        navigationItems = ArrayList<IconListAdapter.ItemModel>()
        navigationItems!!.add(IconListAdapter.ItemModel(1, "Home", R.mipmap.ic_home_white_24dp))
        navigationItems!!.add(IconListAdapter.ItemModel(2, "Configuration", R.mipmap.ic_tune_white_24dp, true))
        navigationItems!!.add(IconListAdapter.ItemModel(3, "Label 3", R.mipmap.ic_home_white_24dp))

        val adapter = IconListAdapter(navigationItems, context)

        mListView!!.adapter = adapter

        return v
    }

    fun setUp(drawerLayout: DrawerLayout) {
        mDrawerLayout = drawerLayout

        mFrameContent = drawerLayout.findViewById(R.id.frame_content) as FrameLayout

        drawerToggle = object : ActionBarDrawerToggle(activity, drawerLayout, null, R.string.drawer_open, R.string.drawer_close) {
            override fun onDrawerClosed(view: View?) {
                super.onDrawerClosed(view)
                activity.invalidateOptionsMenu()
            }

            override fun onDrawerOpened(drawerView: View?) {
                super.onDrawerOpened(drawerView)
                activity.invalidateOptionsMenu()
            }
        }

        mDrawerLayout!!.addDrawerListener(drawerToggle!!)
        mDrawerLayout!!.post { drawerToggle!!.syncState() }

        mListView!!.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            val m = adapterView.getItemAtPosition(i) as IconListAdapter.ItemModel
            Toast.makeText(context, "Clicked on: " + m.label, Toast.LENGTH_LONG).show()

            val f: Fragment
            f = when {
                m.id == 1 -> HomeFragment()
                m.id == 2 -> ConfigurationFragment()
                else -> HomeFragment()
            }

            val ft = activity.supportFragmentManager.beginTransaction()
            ft.replace(R.id.frame_content, f)
            ft.commit()
            mDrawerLayout!!.closeDrawer(Gravity.START)

            m.isSelected = true

        }

    }

    companion object {

        private val PREF_FILE_NAME = "zenPrefFile"
        private val KEY_USER_LEARN_DRAWER = "USER_LEARN_DRAWER"

        fun saveToPreferences(context: Context, preferenceName: String, preferenceValue: String) {
            val sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(preferenceName, preferenceValue)
            editor.apply()
        }

        fun readFromPreferences(context: Context, preferenceName: String, defaultValue: String): String? {
            val sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getString(preferenceName, defaultValue)
        }
    }
}// Required empty public constructor