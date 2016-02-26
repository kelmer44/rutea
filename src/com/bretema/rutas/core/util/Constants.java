
package com.bretema.rutas.core.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;

import org.mapsforge.core.GeoPoint;

import java.util.Collection;
import java.util.zip.CRC32;

public class Constants {

    public static final float PI = 3.14159f;
    public static final String APP_PATH = "/sdcard/maps/";
    public static final String POIS_PATH = APP_PATH + "pois/";
    public static final String MEDIA_PATH = APP_PATH + "media/";
    public static final String IMAGE_MAP_ASSETS_PATH = MEDIA_PATH + "maps/";
    public static final String CONFIG_FILE = APP_PATH + "config.properties";

    public static final Integer GEO_INTENT_NAVIGATE = 0;
    public static final Integer GEO_INTENT_GEO = 1;
    public static final Integer GEO_INTENT_SYGIC = 2;
    public static final Integer CURRENT_INTENT_TYPE = GEO_INTENT_SYGIC;

    private static Intent launchGeoIntent(Integer tipo, String name, double lat, double lon) {
        Intent intent = new Intent();
        if (tipo == GEO_INTENT_GEO) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + lat + "," + lon));
        }
        else if (tipo == GEO_INTENT_NAVIGATE)
        {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + lat + "," + lon));
        }
        else if (tipo == GEO_INTENT_SYGIC)
        {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("com.sygic.aura://coordinate|" + lon + "|" + lat + "|drive"));
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public static Intent launchGeoIntent(String name, double lat, double lon) {
        return launchGeoIntent(CURRENT_INTENT_TYPE, name, lat, lon);
    }

    public static double geoLatDegToDouble(int deg, int min, float sec, boolean north) {

        double val = deg + min / 60 + sec / 3600;
        if (!north)
            val = -val;
        return val;
    }

    public static double geoLonDegToDouble(int deg, int min, float sec, boolean east) {

        double val = deg + min / 60 + sec / 3600;
        if (!east)
            val = -val;
        return val;
    }

    /**
     * Transforms a collection of GeoPoints to an array.
     * 
     * @param collection the geo points
     * @return array of geo points
     */
    public static GeoPoint[][] toGeoPointArray(Collection<GeoPoint> collection) {

        if (collection == null || collection.size() == 0) {
            return new GeoPoint[][] {};
        }

        GeoPoint[][] result = new GeoPoint[1][collection.size()];

        int i = 0;
        for (GeoPoint geoPoint : collection) {
            result[0][i++] = geoPoint;
        }
        return result;
    }

    public static Typeface getTitleFont(AssetManager mgr) {
        return Typeface.createFromAsset(mgr, "YanoneKaffeesatz-Light.ttf");
    }

    public static Typeface getTitleFontRegular(AssetManager mgr) {
        return Typeface.createFromAsset(mgr, "YanoneKaffeesatz-Regular.ttf");
    }

    public static Typeface getTextFont(AssetManager mgr) {
        return Typeface.createFromAsset(mgr, "ColabReg.ttf");
    }

    public static Typeface getSubtitleFont(AssetManager mgr) {
        return Typeface.createFromAsset(mgr, "ColabMed.ttf");
    }

    public static long calculateCrc32(String data) {

        return calculateCrc32(data.getBytes());
    }

    public static long calculateCrc32(byte[] data) {
        CRC32 crc = new CRC32();
        crc.update(data);
        return crc.getValue();
    }

    public static Bitmap drawTextToBitmap(Context gContext, int gResId, String gText) {
        Resources resources = gContext.getResources();
        float scale = resources.getDisplayMetrics().density;
        Bitmap bitmap =
                BitmapFactory.decodeResource(resources, gResId);

        android.graphics.Bitmap.Config bitmapConfig =
                bitmap.getConfig();
        // set default bitmap config if none
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);
        // new antialised Paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint.setColor(Color.WHITE);
        // text size in pixels
        paint.setTextSize((int) (14 * scale));
        // text shadow
        paint.setShadowLayer(1f, 0f, 1f, Color.rgb(61, 61, 61));

        // draw text to the Canvas center
        Rect bounds = new Rect();
        paint.getTextBounds(gText, 0, gText.length(), bounds);
        int x = (bitmap.getWidth() - bounds.width()) / 2;
        int y = (bitmap.getHeight() + bounds.height()) / 2 - (bitmap.getHeight() + bounds.height()) / 6;

        canvas.drawText(gText, x, y, paint);

        return bitmap;
    }
}
