package internshala.com.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import internshala.com.Songs
import internshala.com.R
import internshala.com.fragments.SongPlayingFragment


class MainScreenAdapter(_songDetails: ArrayList<Songs>,_context: Context) : RecyclerView.Adapter<MainScreenAdapter.MyViewHolder>(){

    var songDetails: ArrayList<Songs>? = null
    var mContext: Context? = null
    init {
        this.songDetails = _songDetails
        this.mContext = _context
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val songObject = songDetails?.get(position)
        holder.trackTitle?.text = songObject?.songTitle
        holder.trackTitle?.text = songObject?.artist
        holder.contentHolder?.setOnClickListener {
            val songPlayingFragment = SongPlayingFragment()
            var args = Bundle()
            args.putString("songArtist",songObject?.artist)
            args.putString("path",songObject?.songData)
            args.putString("songTitle",songObject?.songTitle)
            args.putInt("songId",songObject?.SongID?.toInt() as Int)
            args.putInt("songPosition",position)
            args.putParcelableArrayList("songData",songDetails)
            (mContext as FragmentActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.details_fragmant,songPlayingFragment)
                .commit()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
            .inflate(R.layout.row_custom_mainscreen_adapter,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        if(songDetails == null){
            return 0
        }else
        {
            return (songDetails as ArrayList<Songs>).size
        }

    }
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var trackTitle: TextView? = null
        var trackArtist: TextView?= null
        var contentHolder: RelativeLayout? = null
        init {
            trackTitle = view.findViewById<TextView>(R.id.trackTitle)
            trackArtist = view.findViewById<TextView>(R.id.trackArtist)
            contentHolder = view.findViewById<RelativeLayout>(R.id.contentRow)
        }
    }
}

private fun Bundle.putParcelableArrayList(s: String, songDetails: ArrayList<Songs>?) {

}
