package internshala.com.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import internshala.com.R
import internshala.com.MainActivity
import internshala.com.fragments.AboutUsFragment
import internshala.com.fragments.FavouriteFragment
import internshala.com.fragments.MainScreenFragment
import internshala.com.fragments.SettingsFragment

class NavigationDrawerAdapter(_contentList:ArrayList<String>,_getImages: IntArray,_context: Context): RecyclerView.Adapter<NavigationDrawerAdapter.NavViewHolder>() {

    var contentList: ArrayList<String>? = null
    var getImages: IntArray? = null
    var mContext: Context? = null

    init {
        this.contentList = _contentList
        this.getImages = _getImages
        this.mContext = _context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavViewHolder {
        var itemView = LayoutInflater.from(parent?.context)
            .inflate(R.layout.row_custom_navigationdrawer, parent, false)
        val returnThis = NavViewHolder(itemView)
        return returnThis

    }

    override fun getItemCount(): Int {
        return(contentList as ArrayList).size
    }

    override fun onBindViewHolder(holder: NavViewHolder, position: Int) {
        holder?.iconGET?.setBackgroundResource(getImages?.get(position) as Int)
        holder?.textGET?.setText(contentList?.get(position))
        holder?.contentHolder?.setOnClickListener {
            if (position == 0) {
                val mainScreenFragment = MainScreenFragment()
                (mContext as MainActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.details_fragmant, mainScreenFragment)
                    .commit()
            } else if (position == 1) {
                val favouriteFragment = FavouriteFragment()
                (mContext as MainActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.details_fragmant, favouriteFragment)
                    .commit()
            } else if (position == 2) {
                val settingsFragment = SettingsFragment()
                (mContext as MainActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.details_fragmant, settingsFragment)
                    .commit()
            } else {
                val aboutUsFragment = AboutUsFragment()
                (mContext as MainActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.details_fragmant, aboutUsFragment)
                    .commit()
            }
            MainActivity.Statified.drawerLayout?.closeDrawers()
        }
    }


    class NavViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iconGET: ImageView? = null
        var textGET: TextView? = null
        var contentHolder: RelativeLayout? = null

        init {
            iconGET = itemView?.findViewById(R.id.icon_navdrawer)
            textGET = itemView?.findViewById(R.id.text_navdrawer)
            contentHolder = itemView?.findViewById(R.id.navdrawer_item_content_holder)


        }

    }
}

