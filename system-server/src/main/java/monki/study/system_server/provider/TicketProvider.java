package monki.study.system_server.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import monki.study.system_server.database.MyDBHelper;

public class TicketProvider extends ContentProvider {
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI("monki.study.system_server.provider.ticketprovider", "/ticketInfo", 1);
    }

    private MyDBHelper myDBHelper;
    public TicketProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        myDBHelper= MyDBHelper.getInstance(getContext());
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        long rowId;

        int match = uriMatcher.match(uri);
        switch (match) {
            case 1: // match for passengerInfo
                rowId = db.insert("ticketInfo", null, values);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        return uri;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        myDBHelper= MyDBHelper.getInstance(getContext());
        SQLiteDatabase db = myDBHelper.getReadableDatabase();
        int match = uriMatcher.match(uri);
        Cursor cursor=null;
        switch (match) {
            case 1:
                //String sql="select shiftId,ticketPrice from ticketInfo where isSold=0 and lineId=1";
                cursor=db.query("ticketInfo",projection,selection,selectionArgs,null,null,null);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}