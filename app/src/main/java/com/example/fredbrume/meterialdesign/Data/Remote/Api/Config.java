package com.example.fredbrume.meterialdesign.Data.Remote.Api;

import android.net.Uri;
import android.util.Log;

import com.example.fredbrume.meterialdesign.BuildConfig;

import java.net.URL;

/**
 * Created by fredbrume on 11/14/17.
 */

public class Config {

    private static final String BASE_URL = "http://10.0.2.2:80/isi"; ///192.168.43.222      10.0.2.2:80
    private static final String PLACE_BASE_URL = "https://maps.googleapis.com/maps/api/place";
    private static final String JSON_PATH = "json";
    private static final String LOACTION_QUERY = "location";
    private static final String RADIUS_QUERY = "radius";
    private static final String RADIUS_QUERY_PARAMETER = "500";
    private static final String KEY_QUERY = "key";
    private static final String NEARBYSEARCH_PATH = "nearbysearch";
    private static final String IMAGES_PATH = "img";
    private static final String BANNER_PATH = "banner";
    private static final String GALLERY_PATH = "gallery";
    private static final String DIRECTORY_DOCUMENT = "document.php";
    private static final String DOCUMENT_PATH = "documents";
    private static final String DOCUMENT_ITEM_PATH = "items";
    private static final String DOCUMENT_ICON_PATH = "icons";
    private static final String DIRECTORY_DOCUMENT_DETAILS = "documentdetails.php";
    private static final String DIRECTORY_GALLERY = "gallery.php";
    private static final String DIRECTORY_LOCATION = "location.php";
    private static final String DIRECTORY_NEWS = "notification.php";
    private static final String DIRECTORY_BANNER = "banner.php";
    private static final String DAILY_WEEKLY_DIRECTORY = "DailyAndWeeklyFeeds.php";
    private static final String DIRECTORY_TEACHERS = "teachers.php";
    private static final String TAG = Config.class.getSimpleName();

    public final static String DOCUMENT_TYPE_QUERY = "Type";
    public final static String EVENT_ALERT_DAILY = "Event Alerts - Daily";
    public final static String EVENT_ALERT_WEEKLY = "Event Alerts - Weekly";
    public final static String EVENT_ALERT_DAILY_VALUE = "Here's what's happening today";
    public final static String EVENT_ALERT_WEEKLY_VALUE = "Here's what's happening this week";
    public final static String EVENT_GENERAL = "General";
    public final static String EVENT_NOT_PINNED = "Not Pinned";
    public final static String EVENT_PINNED = "Pinned";


    public static String buildDailyWeeklyFeedsUrl() {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(DAILY_WEEKLY_DIRECTORY).build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return String.valueOf(url);
    }

    public static String buildTeachersUrl() {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(DIRECTORY_TEACHERS).build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return String.valueOf(url);
    }

    public static String buildDocumentDetailsUrl() {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(DIRECTORY_DOCUMENT_DETAILS).build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return String.valueOf(url);
    }

    public static String buildDocumentUrl() {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(DIRECTORY_DOCUMENT).build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return String.valueOf(url);
    }

    public static String buildDocumentItemUrl(String document) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(DIRECTORY_DOCUMENT)
                .appendPath(DOCUMENT_ITEM_PATH)
                .appendPath(document)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return String.valueOf(url);
    }

    public static String buildBannerUrl() {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(DIRECTORY_BANNER).build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return String.valueOf(url);
    }

    public static String buildPicturesUrl() {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(DIRECTORY_GALLERY).build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return String.valueOf(url);
    }

    public static String buildNewsAttachmentUrl(String attachment) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(DIRECTORY_GALLERY).build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return String.valueOf(url);
    }

    public static String buildDocumentIconUrl(String icon) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(DOCUMENT_PATH)
                .appendPath(DOCUMENT_ICON_PATH)
                .appendPath(icon)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return String.valueOf(url);
    }

    public static String buildLocationUrl() {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(DIRECTORY_LOCATION).build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return String.valueOf(url);
    }

    public static String buildGalleryUrl(String picture) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(IMAGES_PATH)
                .appendPath(GALLERY_PATH)
                .appendPath(picture)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return String.valueOf(url);
    }


    public static String buildBanneImagerUrl(String bannerImg) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(IMAGES_PATH)
                .appendPath(BANNER_PATH)
                .appendPath(bannerImg)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return String.valueOf(url);
    }

    public static String buildNewsUrl() {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(DIRECTORY_NEWS).build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return String.valueOf(url);
    }

    public static String buildUserImageUrl(String imageName) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(IMAGES_PATH)
                .appendPath(imageName).build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return String.valueOf(url);
    }

    public static String buildPlaceUrl(String longLat) {
        Uri builtUri = Uri.parse(PLACE_BASE_URL)
                .buildUpon()
                .appendPath(NEARBYSEARCH_PATH)
                .appendPath(JSON_PATH)
                .appendQueryParameter(LOACTION_QUERY, longLat)
                .appendQueryParameter(RADIUS_QUERY, RADIUS_QUERY_PARAMETER)
                .appendQueryParameter(KEY_QUERY, BuildConfig.API_KEY)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return String.valueOf(url);
    }


}
