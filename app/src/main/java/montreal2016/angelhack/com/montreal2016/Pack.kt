package montreal2016.angelhack.com.montreal2016

/**
 * Created by daniel on 2016-06-04.
 */

data class Pack(
    var packName: String,
    var packDescription: String,
    var packUsers: MutableList<String>,
    var locationLat: Double,
    var locationLong: Double
)
