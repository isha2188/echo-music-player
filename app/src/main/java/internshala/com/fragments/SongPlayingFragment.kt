package internshala.com.fragments




import android.app.Activity
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.net.Uri.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import internshala.com.CurrentSongHelper
import internshala.com.R
import internshala.com.Songs
import java.util.Random
/*
 * A simple [Fragment] subclass.
 *
 */
@Suppress("DEPRECATION")
class SongPlayingFragment : Fragment() {

    var myActivity: Activity? = null
    var mediaplayer: MediaPlayer? = null
    var startTimeText: TextView? = null
    var endTimeText: TextView? = null
    var playpauseImageButton: ImageButton? = null
    var previousImageButton: ImageButton? = null
    var nextImageButton: ImageButton? = null
    var loopImageButton: ImageButton? = null
    var seekbar: SeekBar? = null
    var songArtistView: TextView? = null
    var songTitleView: TextView? = null
    var shuffleImageButton: ImageButton? = null
    var currentSongHelper: CurrentSongHelper? = null
    var currentPosition: Int = 0
    var fetchSongs: ArrayList<Songs>? = null
    var mediaPlayer: MediaPlayer? = null
    /*The different variables defined will be used for their respective purposes*/
/*Depending on the task they do we name the variables as such so that it gets easier to
identify the task they perform*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment


        val view = inflater!!.inflate(R.layout.fragment_song_playing, container, false)
        seekbar = view?.findViewById(R.id.seekBar)
        startTimeText = view?.findViewById(R.id.startTime)
        endTimeText = view?.findViewById(R.id.endTime)
        playpauseImageButton = view?.findViewById(R.id.playPauseButton)
        nextImageButton = view?.findViewById(R.id.nextButton)
        previousImageButton = view?.findViewById(R.id.previousButton)
        loopImageButton = view?.findViewById(R.id.loopButton)
        shuffleImageButton = view?.findViewById(R.id.shuffleButton)
        songArtistView = view?.findViewById(R.id.songArtist)
        songTitleView = view?.findViewById(R.id.songTitle)

        return view

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        myActivity = context as Activity

    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        myActivity = activity
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        currentSongHelper = CurrentSongHelper()
        currentSongHelper?.isPlaying = true
        currentSongHelper?.isLoop = false
        currentSongHelper?.isShuffle = false
        var path: String? = null
        var _songTitle: String? = null
        var _songArtist: String? = null
        var songId: Long? = 0

        try {
            path = arguments?.getString("path")
            _songTitle = arguments?.getString("songTitle")
            _songArtist = arguments?.getString("songArtist")
            songId = arguments?.getInt("songId")?.toLong()
            currentPosition = arguments.getInt("position")
            fetchSongs = arguments.getParcelableArrayList("songData")
            currentSongHelper?.songPath = path
            currentSongHelper?.songTitle = _songTitle
            currentSongHelper?.songArtist = _songArtist
            currentSongHelper?.songId = songId
            currentSongHelper?.currentPosition = currentPosition

        } catch (e: Exception) {
            e.printStackTrace()
        }
        mediaplayer = MediaPlayer()
        mediaplayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
        try {
            mediaplayer?.setDataSource(myActivity!!.applicationContext, Uri.parse(path))
            mediaplayer?.prepare()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mediaplayer?.start()
        if (currentSongHelper?.isPlaying as Boolean) {
            playpauseImageButton?.setBackgroundResource(R.drawable.pause_icon)
        } else {
            playpauseImageButton?.setBackgroundResource(R.drawable.play_icon)
        }

    }

    fun clickHandler() {
        shuffleImageButton?.setOnClickListener({
        })
        nextImageButton?.setOnClickListener({
            currentSongHelper?.isPlaying = true
/*First we check if the shuffle button was enabled or not*/
            if (currentSongHelper?.isShuffle as Boolean) {
/*If yes, then we play next song randomly
* The check string is passed as the PlayNextLikeNormalShuffle which plays
the random next song*/
                playNext("PlayNextLikeNormalShuffle")
            } else {
/*If shuffle was not enabled then we normally play the next song
* The check string passed is the PlayNextNormal which serves the purpose*/
                playNext("PlayNextNormal")
            }
        })
        previousImageButton?.setOnClickListener({
            currentSongHelper?.isPlaying = true
/*First we check if the loop is on or not*/
            if (currentSongHelper?.isLoop as Boolean) {
/*If the loop was on we turn it off*/
                loopImageButton?.setBackgroundResource(R.drawable.loop_white_icon)
            }
/*After all of the above is done we then play the previous song using the
playPrevious() function*/
            playPrevious()

        })
        loopImageButton?.setOnClickListener({
        })
        playpauseImageButton?.setOnClickListener({

            if (mediaplayer?.isPlaying as Boolean) {
                mediaplayer?.pause()
                currentSongHelper?.isPlaying = false
                playpauseImageButton?.setBackgroundResource(R.drawable.play_icon)

            } else {
                mediaplayer?.start()
                currentSongHelper?.isPlaying = true
                playpauseImageButton?.setBackgroundResource(R.drawable.pause_icon)
            }
        })
    }

    fun playNext(check: String) {
        if (check.equals("playNextNormal", true)) {
            currentPosition = currentPosition + 1
        } else if (check.equals("PlayNextLikeNormalShuffle", true)) {
            var randomObject = java.util.Random()
            var randomPosition = randomObject.nextInt(fetchSongs?.size?.plus(1) as Int)
            currentPosition = randomPosition
        }
        if(currentPosition == fetchSongs?.size) {
            currentPosition = 0
        }
        var nextSong = fetchSongs?.get(currentPosition)
        currentSongHelper?.songTitle = nextSong?.songTitle
        currentSongHelper?.songPath = nextSong?.songData
        currentSongHelper?.currentPosition = currentPosition
        currentSongHelper?.songId = nextSong?.SongID as Long
        mediaplayer?.reset()
        try {
            mediaplayer?.setDataSource(myActivity!!.applicationContext, parse(currentSongHelper?.songPath))
            mediaplayer?.prepare()
            mediaplayer?.start()
        }
      catch (e: java.lang.Exception) {
          e.printStackTrace()
      }
    }
}




