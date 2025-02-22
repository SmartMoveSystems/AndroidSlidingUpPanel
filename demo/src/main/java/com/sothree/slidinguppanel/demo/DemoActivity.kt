package com.sothree.slidinguppanel.demo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.sothree.slidinguppanel.PanelSlideListener
import com.sothree.slidinguppanel.PanelState
import com.sothree.slidinguppanel.demo.databinding.ActivityDemoBinding
import java.util.*

class DemoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDemoBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setSupportActionBar(findViewById<View>(R.id.main_toolbar) as Toolbar)
        binding.listView.onItemClickListener =
            OnItemClickListener { parent, view, position, id -> Toast.makeText(this@DemoActivity, "onItemClick", Toast.LENGTH_SHORT).show() }
        val yourArrayList = Arrays.asList(
            "This",
            "Is",
            "An",
            "Example",
            "ListView",
            "That",
            "You",
            "Can",
            "Scroll",
            ".",
            "It",
            "Shows",
            "How",
            "Any",
            "Scrollable",
            "View",
            "Can",
            "Be",
            "Included",
            "As",
            "A",
            "Child",
            "Of",
            "SlidingUpPanelLayout"
        )

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        val arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            yourArrayList
        )
        binding.listView.adapter = arrayAdapter
        binding.slidingLayout.addPanelSlideListener(object : PanelSlideListener {
            override fun onPanelSlide(panel: View, slideOffset: Float) {
                Log.i(TAG, "onPanelSlide, offset $slideOffset")
            }

            override fun onPanelStateChanged(panel: View, previousState: PanelState, newState: PanelState) {
                Log.i(TAG, "onPanelStateChanged $newState")
            }
        })
        binding.slidingLayout.setFadeOnClickListener { binding.slidingLayout.setPanelState(PanelState.COLLAPSED) }
        val textName = findViewById<TextView>(R.id.name)
        textName.text = Html.fromHtml(getString(R.string.hello))
        val followButton = findViewById<Button>(R.id.follow)
        followButton.text = Html.fromHtml(getString(R.string.follow))
        followButton.movementMethod = LinkMovementMethod.getInstance()
        followButton.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse("http://www.twitter.com/umanoapp")
            startActivity(i)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.demo, menu)
        val item = menu.findItem(R.id.action_toggle)
        if (binding.slidingLayout.panelState == PanelState.HIDDEN) {
            item.setTitle(R.string.action_show)
        } else {
            item.setTitle(R.string.action_hide)
        }
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_toggle -> {
                if (binding.slidingLayout.panelState != PanelState.HIDDEN) {
                    binding.slidingLayout.panelState = PanelState.HIDDEN
                    item.setTitle(R.string.action_show)
                } else {
                    binding.slidingLayout.panelState = PanelState.COLLAPSED
                    item.setTitle(R.string.action_hide)
                }
                return true
            }
            R.id.action_anchor -> {
                if (binding.slidingLayout.anchorPoint == 1.0f) {
                    binding.slidingLayout.anchorPoint = 0.7f
                    binding.slidingLayout.panelState = PanelState.ANCHORED
                    item.setTitle(R.string.action_anchor_disable)
                } else {
                    binding.slidingLayout.anchorPoint = 1.0f
                    binding.slidingLayout.panelState = PanelState.COLLAPSED
                    item.setTitle(R.string.action_anchor_enable)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if ((binding.slidingLayout.panelState == PanelState.EXPANDED || binding.slidingLayout.panelState == PanelState.ANCHORED)) {
            binding.slidingLayout.panelState = PanelState.COLLAPSED
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        private const val TAG = "DemoActivity"
    }
}