package com.wy.report.business.web;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.wy.report.base.fragment.ToolbarFragment;

/**
 * @author cantalou
 * @date 2017年12月29日 16:37
 */
public class WebChromeClient extends android.webkit.WebChromeClient implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {

    public class JavascriptInterface {
        @android.webkit.JavascriptInterface
        public void notifyVideoEnd() {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    onHideCustomView();
                }
            });
        }
    }

    private Fragment fragment;

    private WebView mWebView;

    private View customView;

    private android.webkit.WebChromeClient.CustomViewCallback customViewCallback;

    /**
     * 忽略视频全屏请求
     */
    private static final boolean IGNORE_VIDEO_FULL_SCREEN = Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP;

    public WebChromeClient(Fragment fragment, WebView webView) {
        this.fragment = fragment;
        mWebView = webView;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            mWebView.addJavascriptInterface(new JavascriptInterface(), "_VideoEnabledWebView");
        }
    }

    public void onDestroy() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            mWebView.removeJavascriptInterface("_VideoEnabledWebView");
        }
        mWebView = null;
        fragment = null;
    }

    private boolean isDestroyed() {
        return fragment == null;
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
        if (isDestroyed()) {
            return false;
        }
        if (result != null) {
            result.confirm();
        }
        return false;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (isDestroyed()) {
            return;
        }
        super.onProgressChanged(view, newProgress);

        // 加载进度超过50
        if (newProgress >= 50) {
            view.getSettings()
                .setBlockNetworkImage(false);
        }

        view.requestFocus();
    }

    @Override
    public void onShowCustomView(View view, android.webkit.WebChromeClient.CustomViewCallback callback) {
        if (IGNORE_VIDEO_FULL_SCREEN) {
            return;
        }

        if (isDestroyed()) {
            return;
        }
        fragment.getActivity()
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        //如果一个视图已经存在，那么立刻终止并新建一个
        if (customView != null) {
            callback.onCustomViewHidden();
            return;
        }
        ViewGroup parent = (ViewGroup) mWebView.getParent();
        if (parent instanceof FrameLayout || parent instanceof RelativeLayout) {
            mWebView.setVisibility(View.INVISIBLE);
        } else {
            mWebView.setVisibility(View.GONE);
        }
        parent.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        customView = view;
        customViewCallback = callback;

        if (!(view instanceof ViewGroup)) {
            return;
        }

        View focusedChild = ((ViewGroup) view).getFocusedChild();
        if (focusedChild instanceof VideoView) {
            // android.widget.VideoView (typically API level <11)
            VideoView videoView = (VideoView) focusedChild;

            // Handle all the required events
            videoView.setOnPreparedListener(this);
            videoView.setOnCompletionListener(this);
            videoView.setOnErrorListener(this);
        } else {
            // Other classes, including:
            // - android.webkit.HTML5VideoFullScreen$VideoSurfaceView, which inherits from android.view.SurfaceView (typically API level 11-18)
            // - android.webkit.HTML5VideoFullScreen$VideoTextureView, which inherits from android.view.TextureView (typically API level 11-18)
            // - com.android.org.chromium.content.browser.ContentVideoView$VideoSurfaceView, which inherits from android.view.SurfaceView (typically API level 19+)

            // Handle HTML5 video ended event only if the class is a SurfaceView
            // Test case: TextureView of Sony Xperia T API level 16 doesn't work fullscreen when loading the javascript below
            if (mWebView != null && mWebView.getSettings()
                                            .getJavaScriptEnabled() && focusedChild instanceof SurfaceView) {
                // Run javascript code that detects the video end and notifies the Javascript interface
                String js = "javascript:";
                js += "var _ytrp_html5_video_last;";
                js += "var _ytrp_html5_video = document.getElementsByTagName('video')[0];";
                js += "if (_ytrp_html5_video != undefined && _ytrp_html5_video != _ytrp_html5_video_last) {";
                {
                    js += "_ytrp_html5_video_last = _ytrp_html5_video;";
                    js += "function _ytrp_html5_video_ended() {";
                    {
                        js += "_VideoEnabledWebView.notifyVideoEnd();"; // Must match Javascript interface name and method of VideoEnableWebView
                    }
                    js += "}";
                    js += "_ytrp_html5_video.addEventListener('ended', _ytrp_html5_video_ended);";
                }
                js += "}";
                mWebView.loadUrl(js);
            }
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {
        onShowCustomView(view, callback);
    }

    @Override
    public void onHideCustomView() {

        if (IGNORE_VIDEO_FULL_SCREEN) {
            return;
        }

        if (isDestroyed()) {
            return;
        }

        // This method should be manually called on video end in all cases because it's not always called automatically.
        // This method must be manually called on back key press (from this class' onBackPressed() method).

        // Hide the custom view.
        fragment.getActivity()
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (customView == null) {
            return;
        }

        customView.setVisibility(View.GONE);

        ViewGroup parent = (ViewGroup) mWebView.getParent();
        // Remove the custom view from its container.
        parent.removeView(customView);
        customView = null;
        if (customViewCallback != null && !customViewCallback.getClass()
                                                             .getName()
                                                             .contains(".chromium.")) {
            customViewCallback.onCustomViewHidden();
        }

        mWebView.setVisibility(View.VISIBLE);
    }

    public boolean onBackPressed() {
        if (IGNORE_VIDEO_FULL_SCREEN) {
            return false;
        }

        if (isDestroyed() || customView == null) {
            return false;
        }
        onHideCustomView();
        return true;
    }

    @Override
    public Bitmap getDefaultVideoPoster() {
        return super.getDefaultVideoPoster();
    }

    @Override
    public View getVideoLoadingProgressView() {
        return super.getVideoLoadingProgressView();
    }


    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        onHideCustomView();
    }


    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        if (TextUtils.isEmpty(title)) {
            return;
        }
        if (!(fragment instanceof ToolbarFragment)) {
            return;
        }
        if (title.contains(".com")) {
            return;
        }
        ((ToolbarFragment) fragment).setTitle(title);
    }
}
