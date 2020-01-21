package io.github.memfis19.annca.internal.ui.view;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.FileObserver;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.github.memfis19.annca.GalleryImageAdapter;
import io.github.memfis19.annca.R;
import io.github.memfis19.annca.internal.configuration.AnncaConfiguration;
import io.github.memfis19.annca.internal.utils.DateTimeUtils;

/**
 * Created by memfis on 7/6/16.
 */
public class CameraControlPanel extends RelativeLayout
        implements RecordButton.RecordButtonListener,
        MediaActionSwitchView.OnMediaActionStateChangeListener {

    private Context context;

    private CameraSwitchView cameraSwitchView;
    private RecordButton recordButton;
    private MediaActionSwitchView mediaActionSwitchView;
    private FlashSwitchView flashSwitchView;
    private TextView recordDurationText;
    private TextView recordSizeText;
    private CameraSettingsView settingsButton;
    private RecordButton.RecordButtonListener recordButtonListener;
    private MediaActionSwitchView.OnMediaActionStateChangeListener onMediaActionStateChangeListener;
    private CameraSwitchView.OnCameraTypeChangeListener onCameraTypeChangeListener;
    private FlashSwitchView.FlashModeSwitchListener flashModeSwitchListener;
    private SettingsClickListener settingsClickListener;
    private TimerTaskBase countDownTimer;
    private long maxVideoFileSize = 0;
    private String mediaFilePath;
    RecyclerView  from_gallery;
    GalleryImageAdapter galleryImageAdapter;
    ////1///
    public static final String CAMERA_IMAGE_BUCKET_NAME1 =
            Environment.getExternalStorageDirectory().toString()
                    + "/Pictures/net.phpsm.simsim.simsiminstantorder";
    public static final String CAMERA_IMAGE_BUCKET_ID1 =
            getBucketId(CAMERA_IMAGE_BUCKET_NAME1);
    /////2////
    public static final String CAMERA_IMAGE_BUCKET_NAME2 =
            Environment.getExternalStorageDirectory().toString()
                    + "/Pictures/Messenger";
    public static final String CAMERA_IMAGE_BUCKET_ID2 =
            getBucketId(CAMERA_IMAGE_BUCKET_NAME2);
    ////3////
    public static final String CAMERA_IMAGE_BUCKET_NAME3 =
            Environment.getExternalStorageDirectory().toString()
                    + "/DCIM/Secreenshots";
    public static final String CAMERA_IMAGE_BUCKET_ID3 =
            getBucketId(CAMERA_IMAGE_BUCKET_NAME3);

    ////4////
    public static final String CAMERA_IMAGE_BUCKET_NAME4 =
            Environment.getExternalStorageDirectory().toString()
                    + "/DCIM/Facebook";
    public static final String CAMERA_IMAGE_BUCKET_ID4 =
            getBucketId(CAMERA_IMAGE_BUCKET_NAME4);



    ////5////
    public static final String CAMERA_IMAGE_BUCKET_NAME5 =
            Environment.getExternalStorageDirectory().toString()
                    + "/Pictures/Instagram";
    public static final String CAMERA_IMAGE_BUCKET_ID5 =
            getBucketId(CAMERA_IMAGE_BUCKET_NAME5);



    public static String getBucketId(String path) {
        return String.valueOf(path.toLowerCase().hashCode());
    }
    public interface SettingsClickListener {
        void onSettingsClick();
    }

    private boolean hasFlash = false;

    private
    @MediaActionSwitchView.MediaActionState
    int mediaActionState;

    private int mediaAction;

    private FileObserver fileObserver;

    public CameraControlPanel(Context context) {
        this(context, null);
    }

    public CameraControlPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        hasFlash = context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
//        hasFlash = false;

        LayoutInflater.from(context).inflate(R.layout.camera_control_panel_layout, this);
        Log.d("safaa","safaaaa");
        setBackgroundColor(Color.TRANSPARENT);

        settingsButton = (CameraSettingsView) findViewById(R.id.settings_view);
        cameraSwitchView = (CameraSwitchView) findViewById(R.id.front_back_camera_switcher);
        mediaActionSwitchView = (MediaActionSwitchView) findViewById(R.id.photo_video_camera_switcher);
        recordButton = (RecordButton) findViewById(R.id.record_button);
       List images=getAllShownImagesPath();
        from_gallery=(RecyclerView) findViewById(R.id.from_gallery);
        galleryImageAdapter=new GalleryImageAdapter(images,getContext());
        from_gallery.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        from_gallery.setAdapter(galleryImageAdapter);
        galleryImageAdapter.setiClickListener(new GalleryImageAdapter.IClickListener() {
            @Override
            public void onItemClick(int position, String imgUrl) {
                Toast.makeText(
                        getContext(),
                        "position " + position + " " + imgUrl,
                        Toast.LENGTH_SHORT).show();



            }
        });
        flashSwitchView = (FlashSwitchView) findViewById(R.id.flash_switch_view);
        recordDurationText = (TextView) findViewById(R.id.record_duration_text);
        recordSizeText = (TextView) findViewById(R.id.record_size_mb_text);

        cameraSwitchView.setOnCameraTypeChangeListener(onCameraTypeChangeListener);
        mediaActionSwitchView.setOnMediaActionStateChangeListener(this);

        setOnCameraTypeChangeListener(onCameraTypeChangeListener);
        setOnMediaActionStateChangeListener(onMediaActionStateChangeListener);
        setFlashModeSwitchListener(flashModeSwitchListener);
        setRecordButtonListener(recordButtonListener);
        settingsButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (settingsClickListener != null) settingsClickListener.onSettingsClick();
            }
        });

        if (hasFlash)
            flashSwitchView.setVisibility(VISIBLE);
        else flashSwitchView.setVisibility(GONE);

        countDownTimer = new TimerTask(recordDurationText);
    }
    private ArrayList<String> getAllShownImagesPath() {

/*
        final String[] projection = { MediaStore.Images.Media.DATA };
        final String selection = MediaStore.Images.Media.BUCKET_ID + " = ?";
        final String[] selectionArgs = { CAMERA_IMAGE_BUCKET_ID1,CAMERA_IMAGE_BUCKET_ID2,CAMERA_IMAGE_BUCKET_ID3,CAMERA_IMAGE_BUCKET_ID4 };
        final Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null);
*/
        String[] projection = {
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.MEDIA_TYPE,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.TITLE
        };

// Return only video and image metadata.
        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
               // + " OR "
               // + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
               // + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO
                ;

        Uri queryUri = MediaStore.Files.getContentUri("external");

        CursorLoader cursorLoader = new CursorLoader(
                context,
                queryUri,
                projection,
                selection,
                null, // Selection args (none).
                MediaStore.Files.FileColumns.DATE_ADDED + " DESC" // Sort order.
        );

        Cursor cursor = cursorLoader.loadInBackground();


        ArrayList<String> result = new ArrayList<String>(cursor.getCount());
        if (cursor.moveToFirst()) {
            final int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            do {
                final String data = cursor.getString(dataColumn);
                result.add(data);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    public void lockControls() {
        cameraSwitchView.setEnabled(false);
        recordButton.setEnabled(false);
        settingsButton.setEnabled(false);
        flashSwitchView.setEnabled(false);
    }

    public void unLockControls() {
        cameraSwitchView.setEnabled(true);
        recordButton.setEnabled(true);
        settingsButton.setEnabled(true);
        flashSwitchView.setEnabled(true);
    }

    public void setup(int mediaAction) {
        this.mediaAction = mediaAction;
        if (AnncaConfiguration.MEDIA_ACTION_VIDEO == mediaAction) {
            recordButton.setup(mediaAction, this);
            flashSwitchView.setVisibility(VISIBLE);
        } else {
            recordButton.setup(AnncaConfiguration.MEDIA_ACTION_PHOTO, this);
        }

        if (AnncaConfiguration.MEDIA_ACTION_UNSPECIFIED != mediaAction) {
            mediaActionSwitchView.setVisibility(GONE);
        } else mediaActionSwitchView.setVisibility(GONE);
    }

    public void setFlasMode(@FlashSwitchView.FlashMode int flashMode) {
        flashSwitchView.setFlashMode(flashMode);
    }

    public void setMediaFilePath(final File mediaFile) {
        this.mediaFilePath = mediaFile.toString();
    }

    public void setMaxVideoFileSize(long maxVideoFileSize) {
        this.maxVideoFileSize = maxVideoFileSize;
    }

    public void setMaxVideoDuration(int maxVideoDurationInMillis) {
        if (maxVideoDurationInMillis > 0)
            countDownTimer = new CountdownTask(recordDurationText, maxVideoDurationInMillis);
        else countDownTimer = new TimerTask(recordDurationText);
    }

    public void setMediaActionState(@MediaActionSwitchView.MediaActionState int actionState) {
        if (mediaActionState == actionState) return;
        if (MediaActionSwitchView.ACTION_PHOTO == actionState) {
            recordButton.setMediaAction(AnncaConfiguration.MEDIA_ACTION_PHOTO);
            if (hasFlash)
                flashSwitchView.setVisibility(VISIBLE);
        } else {
            recordButton.setMediaAction(AnncaConfiguration.MEDIA_ACTION_VIDEO);
            flashSwitchView.setVisibility(GONE);
        }
        mediaActionState = actionState;
        mediaActionSwitchView.setMediaActionState(actionState);
    }

    public void setRecordButtonListener(RecordButton.RecordButtonListener recordButtonListener) {
        this.recordButtonListener = recordButtonListener;
    }

    public void rotateControls(int rotation) {
        if (Build.VERSION.SDK_INT > 10) {
            cameraSwitchView.setRotation(rotation);
            mediaActionSwitchView.setRotation(rotation);
            flashSwitchView.setRotation(rotation);
            recordDurationText.setRotation(rotation);
            recordSizeText.setRotation(rotation);
        }
    }

    public void setOnMediaActionStateChangeListener(MediaActionSwitchView.OnMediaActionStateChangeListener onMediaActionStateChangeListener) {
        this.onMediaActionStateChangeListener = onMediaActionStateChangeListener;
    }

    public void setOnCameraTypeChangeListener(CameraSwitchView.OnCameraTypeChangeListener onCameraTypeChangeListener) {
        this.onCameraTypeChangeListener = onCameraTypeChangeListener;
        if (cameraSwitchView != null)
            cameraSwitchView.setOnCameraTypeChangeListener(this.onCameraTypeChangeListener);
    }

    public void setFlashModeSwitchListener(FlashSwitchView.FlashModeSwitchListener flashModeSwitchListener) {
        this.flashModeSwitchListener = flashModeSwitchListener;
        if (flashSwitchView != null)
            flashSwitchView.setFlashSwitchListener(this.flashModeSwitchListener);
    }

    public void setSettingsClickListener(SettingsClickListener settingsClickListener) {
        this.settingsClickListener = settingsClickListener;
    }

    @Override
    public void onTakePhotoButtonPressed() {
        if (recordButtonListener != null)
            recordButtonListener.onTakePhotoButtonPressed();
    }

    public void onStartVideoRecord(final File mediaFile) {
        setMediaFilePath(mediaFile);
        if (maxVideoFileSize > 0) {
            recordSizeText.setText("1Mb" + " / " + maxVideoFileSize / (1024 * 1024) + "Mb");
            recordSizeText.setVisibility(VISIBLE);
            try {
                fileObserver = new FileObserver(this.mediaFilePath) {
                    private long lastUpdateSize = 0;

                    @Override
                    public void onEvent(int event, String path) {
                        final long fileSize = mediaFile.length() / (1024 * 1024);
                        if ((fileSize - lastUpdateSize) >= 1) {
                            lastUpdateSize = fileSize;
                            recordSizeText.post(new Runnable() {
                                @Override
                                public void run() {
                                    recordSizeText.setText(fileSize + "Mb" + " / " + maxVideoFileSize / (1024 * 1024) + "Mb");
                                }
                            });
                        }
                    }
                };
                fileObserver.startWatching();
            } catch (Exception e) {
                Log.e("FileObserver", "setMediaFilePath: ", e);
            }
        }
        countDownTimer.start();

    }

    public void allowRecord(boolean isAllowed) {
        recordButton.setEnabled(isAllowed);
    }

    public void allowCameraSwitching(boolean isAllowed) {
        cameraSwitchView.setVisibility(isAllowed ? VISIBLE : GONE);
    }

    public void onStopVideoRecord() {
        if (fileObserver != null)
            fileObserver.stopWatching();
        countDownTimer.stop();

        recordSizeText.setVisibility(GONE);
        cameraSwitchView.setVisibility(View.VISIBLE);
        settingsButton.setVisibility(VISIBLE);

        if (AnncaConfiguration.MEDIA_ACTION_UNSPECIFIED != mediaAction) {
            mediaActionSwitchView.setVisibility(GONE);
        } else mediaActionSwitchView.setVisibility(GONE);
        recordButton.setRecordState(RecordButton.READY_FOR_RECORD_STATE);
    }

    @Override
    public void onStartRecordingButtonPressed() {

        cameraSwitchView.setVisibility(View.GONE);
        mediaActionSwitchView.setVisibility(GONE);
        settingsButton.setVisibility(GONE);

        if (recordButtonListener != null)
            recordButtonListener.onStartRecordingButtonPressed();
    }

    @Override
    public void onStopRecordingButtonPressed() {
        onStopVideoRecord();
        if (recordButtonListener != null)
            recordButtonListener.onStopRecordingButtonPressed();
    }

    @Override
    public void onMediaActionChanged(int mediaActionState) {
        setMediaActionState(mediaActionState);
        if (onMediaActionStateChangeListener != null)
            onMediaActionStateChangeListener.onMediaActionChanged(this.mediaActionState);
    }

    abstract class TimerTaskBase {
        Handler handler = new Handler(Looper.getMainLooper());
        TextView timerView;
        boolean alive = false;
        long recordingTimeSeconds = 0;
        long recordingTimeMinutes = 0;

        TimerTaskBase(TextView timerView) {
            this.timerView = timerView;
        }

        abstract void stop();

        abstract void start();
    }

    private class CountdownTask extends TimerTaskBase implements Runnable {

        private int maxDurationMilliseconds = 0;

        public CountdownTask(TextView timerView, int maxDurationMilliseconds) {
            super(timerView);
            this.maxDurationMilliseconds = maxDurationMilliseconds;
        }

        @Override
        public void run() {

            recordingTimeSeconds--;

            int millis = (int) recordingTimeSeconds * 1000;

            timerView.setText(
                    String.format("%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(millis),
                            TimeUnit.MILLISECONDS.toSeconds(millis) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
                    ));

            if (recordingTimeSeconds < 10) {
                timerView.setTextColor(Color.RED);
            }

            if (alive && recordingTimeSeconds > 0) handler.postDelayed(this, DateTimeUtils.SECOND);
        }

        @Override
        void stop() {
            timerView.setVisibility(View.INVISIBLE);
            alive = false;
        }

        @Override
        void start() {
            alive = true;
            recordingTimeSeconds = maxDurationMilliseconds / 1000;
            timerView.setTextColor(Color.WHITE);
            timerView.setText(
                    String.format("%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(maxDurationMilliseconds),
                            TimeUnit.MILLISECONDS.toSeconds(maxDurationMilliseconds) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(maxDurationMilliseconds))
                    ));
            timerView.setVisibility(View.VISIBLE);
            handler.postDelayed(this, DateTimeUtils.SECOND);
        }
    }

    private class TimerTask extends TimerTaskBase implements Runnable {

        public TimerTask(TextView timerView) {
            super(timerView);
        }

        @Override
        public void run() {
            recordingTimeSeconds++;

            if (recordingTimeSeconds == 60) {
                recordingTimeSeconds = 0;
                recordingTimeMinutes++;
            }
            timerView.setText(
                    String.format("%02d:%02d", recordingTimeMinutes, recordingTimeSeconds));
            if (alive) handler.postDelayed(this, DateTimeUtils.SECOND);
        }

        public void start() {
            alive = true;
            recordingTimeMinutes = 0;
            recordingTimeSeconds = 0;
            timerView.setText(
                    String.format("%02d:%02d", recordingTimeMinutes, recordingTimeSeconds));
            timerView.setVisibility(View.VISIBLE);
            handler.postDelayed(this, DateTimeUtils.SECOND);
        }

        public void stop() {
            timerView.setVisibility(View.INVISIBLE);
            alive = false;
        }
    }

}
