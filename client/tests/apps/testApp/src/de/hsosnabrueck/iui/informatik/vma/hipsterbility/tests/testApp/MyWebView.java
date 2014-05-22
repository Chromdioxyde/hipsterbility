package de.hsosnabrueck.iui.informatik.vma.hipsterbility.tests.testApp;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.Hipsterbility;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.HipsterbilityActivity;

/**
 * Created by Albert on 25.03.2014.
 */
public class MyWebView extends HipsterbilityActivity {
    WebView webView;
    public void onCreate(Bundle savedInstanceState) {
        Hipsterbility.getInstance().enableTesting(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web_view);
        webView = (WebView) findViewById(R.id.webView);
//        handle URL clicks in web view directly
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });
        Button reloadButton = (Button) findViewById(R.id.button_reload_web);
        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadWebPage();
            }
        });
        loadWebPage();
    }

    private void loadWebPage() {
        webView.loadUrl("http://www.hs-osnabrueck.de");
    }
}