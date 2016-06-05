package montreal2016.angelhack.com.montreal2016

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by daniel on 2016-06-04.
 */

data class Pack(
    var packName: String,
    var packDescription: String,
    var packUsers: MutableList<String>,
    var locationLat: Double,
    var locationLong: Double
) : Parcelable {
    constructor(source: Parcel): this(source.readString(), source.readString(), source.createStringArrayList(), source.readDouble(), source.readDouble())

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(packName)
        dest?.writeString(packDescription)
        dest?.writeStringList(packUsers)
        dest?.writeDouble(locationLat)
        dest?.writeDouble(locationLong)
    }

    companion object {
        @JvmField final val CREATOR: Parcelable.Creator<Pack> = object : Parcelable.Creator<Pack> {
            override fun createFromParcel(source: Parcel): Pack {
                return Pack(source)
            }

            override fun newArray(size: Int): Array<Pack?> {
                return arrayOfNulls(size)
            }
        }
    }
}
