package internshala.com

import android.os.Parcel
import android.os.Parcelable

class Songs(var SongID: Long,var songTitle: String,var artist: String,var songData: String,var dateAdded: Long) : Parcelable
{
    override fun writeToParcel(p0: Parcel?, p1: Int) {

    }

    override fun describeContents(): Int {
      return 0
    }

}
