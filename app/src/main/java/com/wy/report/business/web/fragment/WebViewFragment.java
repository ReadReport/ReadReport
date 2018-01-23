package com.wy.report.business.web.fragment;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.fragment.NetworkFragment;
import com.wy.report.business.web.WebChromeClient;
import com.wy.report.business.web.WebViewClient;

import butterknife.BindView;

/**
 * @author cantalou
 * @date 2017年12月29日 16:30
 * <p>
 */
public class WebViewFragment extends NetworkFragment {

    @BindView(R.id.webview)
    WebView webView;

    private WebChromeClient webChromeClient;

    private String url;

    private String title;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            url = (String) arguments.get(BundleKey.BUNDLE_KEY_WEB_VIEW_URL);
            title = (String) arguments.get(BundleKey.BUNDLE_KEY_WEB_VIEW_TITLE);
        } else {
            getActivity().finish();
        }
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setNeedInitialFocus(false);
        settings.setSaveFormData(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setAllowFileAccess(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setUseWideViewPort(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        if (android.os.Build.VERSION.SDK_INT > 16) {
            settings.setMediaPlaybackRequiresUserGesture(false);
        }

        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);
        webView.setScrollbarFadingEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebChromeClient(webChromeClient = new WebChromeClient(this, webView));
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        if (title != null) {
            setTitle(title);
        }
    }

    /**
     * Called when the fragment is no longer in use. Destroys the internal state of the WebView.
     */
    @Override
    public void onDestroy() {
        webChromeClient.onDestroy();
        if (webView != null) {
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_webview;
    }


    /**
     * Called when the fragment is visible to the user and actively running. Resumes the WebView.
     */
    @Override
    public void onPause() {
        super.onPause();
        webView.onPause();
    }

    /**
     * Called when the fragment is no longer resumed. Pauses the WebView.
     */
    @Override
    public void onResume() {
        webView.onResume();
        super.onResume();
    }

    /**
     * Called when the WebView has been detached from the fragment.
     * The WebView is no longer available after this time.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
