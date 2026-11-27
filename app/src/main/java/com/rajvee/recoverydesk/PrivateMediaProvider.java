package com.rajvee.recoverydesk;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import java.io.File;
import java.io.FileNotFoundException;

/** Grants a camera app temporary access to a private output file for full-size capture. */
public class PrivateMediaProvider extends ContentProvider {
  @Override public boolean onCreate(){return true;}
  private File file(Uri uri)throws FileNotFoundException {
    java.util.List<String> parts=uri.getPathSegments();String folder=parts.size()>1?parts.get(0):"camera";if(!folder.equals("camera")&&!folder.equals("exports"))throw new FileNotFoundException();
    File dir=new File(getContext().getFilesDir(),folder);
    File target=new File(dir,uri.getLastPathSegment());
    try { if(!target.getCanonicalPath().startsWith(dir.getCanonicalPath()+File.separator))throw new FileNotFoundException(); }
    catch(java.io.IOException e){throw new FileNotFoundException();}
    return target;
  }
  @Override public String getType(Uri uri){String n=uri.getLastPathSegment();return n!=null&&n.endsWith(".pdf")?"application/pdf":n!=null&&n.endsWith(".xlsx")?"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":"image/jpeg";}
  @Override public ParcelFileDescriptor openFile(Uri uri,String mode)throws FileNotFoundException {
    File f=file(uri);f.getParentFile().mkdirs();int flags=mode.contains("w")?(ParcelFileDescriptor.MODE_CREATE|ParcelFileDescriptor.MODE_TRUNCATE|(mode.contains("r")?ParcelFileDescriptor.MODE_READ_WRITE:ParcelFileDescriptor.MODE_WRITE_ONLY)):ParcelFileDescriptor.MODE_READ_ONLY;return ParcelFileDescriptor.open(f,flags);
  }
  @Override public Cursor query(Uri uri,String[] projection,String selection,String[] args,String sort){
    try{File f=file(uri);String[] cols=projection==null?new String[]{OpenableColumns.DISPLAY_NAME,OpenableColumns.SIZE}:projection;MatrixCursor c=new MatrixCursor(cols);Object[] row=new Object[cols.length];for(int i=0;i<cols.length;i++)row[i]=OpenableColumns.DISPLAY_NAME.equals(cols[i])?f.getName():OpenableColumns.SIZE.equals(cols[i])?f.length():null;c.addRow(row);return c;}catch(Exception e){return null;}
  }
  @Override public Uri insert(Uri uri,ContentValues v){throw new UnsupportedOperationException();}
  @Override public int delete(Uri uri,String s,String[] a){throw new UnsupportedOperationException();}
  @Override public int update(Uri uri,ContentValues v,String s,String[] a){throw new UnsupportedOperationException();}
}
