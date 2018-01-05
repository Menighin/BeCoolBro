package com.onsoftwares.zensource.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast

import com.onsoftwares.zensource.R
import com.onsoftwares.zensource.adapters.IconListRecyclerAdapter

import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class NavigationDrawerFragment : Fragment() {

    var drawerToggle: ActionBarDrawerToggle? = null
        private set
    private var mDrawerLayout: DrawerLayout? = null
    private var mToolbar: Toolbar? = null
    private var mRecyclerView: RecyclerView? = null
    private var navigationItems: ArrayList<IconListRecyclerAdapter.ItemModel>? = null
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

        mRecyclerView = v.findViewById(R.id.navigation_drawer_list) as RecyclerView
        val layoutManager = LinearLayoutManager(context);
        mRecyclerView!!.layoutManager = layoutManager


        navigationItems = ArrayList()
        navigationItems!!.add(IconListRecyclerAdapter.ItemModel(1, resources.getString(R.string.menu_home), R.mipmap.ic_home_white_24dp, true))
        navigationItems!!.add(IconListRecyclerAdapter.ItemModel(2, resources.getString(R.string.menu_liked), R.drawable.ic_thumb_up_white_24dp))
        navigationItems!!.add(IconListRecyclerAdapter.ItemModel(3, resources.getString(R.string.menu_configurations), R.mipmap.ic_tune_white_24dp))

        val adapter = IconListRecyclerAdapter(context, navigationItems, IconListRecyclerAdapter.OnItemClickListener {
            item ->
            run {

                val f: Fragment = when {
                    item.id == 1 -> HomeFragment()
                    item.id == 2 -> LikedQuotesFragment()
                    item.id == 3 -> ConfigurationFragment()
                    else -> HomeFragment()
                }

                // Setting the titles
                when {
                    item.id == 1 -> mToolbar!!.title = activity.resources.getString(R.string.menu_home)
                    item.id == 2 -> mToolbar!!.title = activity.resources.getString(R.string.menu_liked)
                    item.id == 3 -> mToolbar!!.title = activity.resources.getString(R.string.menu_configurations)
                }

                val ft = activity.supportFragmentManager.beginTransaction()
                ft.replace(R.id.frame_content, f)
                ft.commit()
                mDrawerLayout!!.closeDrawer(Gravity.START)

                item.isSelected = true
            }
        })

        mRecyclerView!!.adapter = adapter

        return v
    }

    fun setUp(drawerLayout: DrawerLayout, toolbar: Toolbar) {

        mDrawerLayout = drawerLayout
        mToolbar = toolbar

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
