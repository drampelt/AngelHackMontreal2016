package montreal2016.angelhack.com.montreal2016

/**
 * Created by daniel on 2016-06-05.
 */

data class WhichPackResponse(
    var hasPack: Boolean,
    var packName: String,
    var packDescription: String,
    var packLat: Double,
    var packLong: Double,
    var packMembers: MutableList<String>
)
