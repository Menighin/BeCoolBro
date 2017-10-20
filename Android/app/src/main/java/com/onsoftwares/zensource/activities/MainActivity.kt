package com.onsoftwares.zensource.activities

import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.onsoftwares.zensource.fragments.NavigationDrawerFragment
import com.onsoftwares.zensource.R

class MainActivity : AppCompatActivity() {

    private var toolbar: Toolbar? = null;
    private var mDrawerFragment: NavigationDrawerFragment? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        toolbar = findViewById(R.id.toolbar_main) as Toolbar;
        setSupportActionBar(toolbar);

        mDrawerFragment  = supportFragmentManager.findFragmentById(R.id.fragment_navigation_drawer) as NavigationDrawerFragment;
        mDrawerFragment!!.setUp(findViewById(R.id.drawer_layout) as DrawerLayout);

        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setHomeButtonEnabled(true);
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mDrawerFragment!!.drawerToggle.onConfigurationChanged(newConfig)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        return if (mDrawerFragment!!.drawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
        // Handle your other action bar items...

    }

}