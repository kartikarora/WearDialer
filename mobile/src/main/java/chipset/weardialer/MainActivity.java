package chipset.weardialer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import chipset.potato.Potato;

/**
 * Developer: chipset
 * Package : chipset.weardialer
 * Project : Wear Dialer
 * Date : 15/5/15
 */

public class MainActivity extends AppCompatActivity {

    private static final String URL_GITHUB = "http://www.github.com/kartikarora/WearDialer";
    private static final String URL_GOOGLE_PLUS = "https://plus.google.com/communities/102020203714396804737";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.github_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Potato.potate(getApplicationContext()).Intents().browserIntent(URL_GITHUB);
            }
        });

        findViewById(R.id.google_plus_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Potato.potate(getApplicationContext()).Intents().browserIntent(URL_GOOGLE_PLUS);
            }
        });
    }
}

