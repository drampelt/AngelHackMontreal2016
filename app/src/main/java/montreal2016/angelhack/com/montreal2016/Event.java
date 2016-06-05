package montreal2016.angelhack.com.montreal2016;


import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("categories")
    @Expose
    private List<String> categories = new ArrayList<String>();
    @SerializedName("latlng")
    @Expose
    private String latlng;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The categories
     */
    public List<String> getCategories() {
        return categories;
    }

    /**
     *
     * @param categories
     * The categories
     */
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    /**
     *
     * @return
     * The latlng
     */
    public String getLatlng() {
        return latlng;
    }

    /**
     *
     * @param latlng
     * The latlng
     */
    public void setLatlng(String latlng) {
        this.latlng = latlng;
    }

    /**
     *
     * @return
     * The imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     *
     * @param imageUrl
     * The imageUrl
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}