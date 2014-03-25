package de.hsosnabrueck.iui.informatik.vma.hipsterbility.tests.testApp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

/**
 * Created by Albert on 25.03.2014.
 */
public class MyWebView extends Activity {
    WebView webView;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web_view);
        webView = (WebView) findViewById(R.id.webView);
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