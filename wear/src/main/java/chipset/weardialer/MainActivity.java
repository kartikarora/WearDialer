package chipset.weardialer;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import io.fabric.sdk.android.Fabric;
import java.util.List;

/**
 * Developer: chipset
 * Package : chipset.weardialer
 * Project : Wear Dialer
 * Date : 15/5/15
 */

public class MainActivity extends Activity {

    private TextView mDialTextView;
    private Button zeroButton, oneButton, twoButton, threeButton, fourButton, fiveButton,
            sixButton, sevenButton, eightButton, nineButton, starButton, hashButton;
    private ImageButton callImageButton, backspaceImageButton;
    private GoogleApiClient mApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mDialTextView = (TextView) stub.findViewById(R.id.dialed_no_textview);
                zeroButton = (Button) stub.findViewById(R.id.zero_button);
                oneButton = (Button) stub.findViewById(R.id.one_button);
                twoButton = (Button) stub.findViewById(R.id.two_button);
                threeButton = (Button) stub.findViewById(R.id.three_button);
                fourButton = (Button) stub.findViewById(R.id.four_button);
                fiveButton = (Button) stub.findViewById(R.id.five_button);
                sixButton = (Button) stub.findViewById(R.id.six_button);
                sevenButton = (Button) stub.findViewById(R.id.seven_button);
                eightButton = (Button) stub.findViewById(R.id.eight_button);
                nineButton = (Button) stub.findViewById(R.id.nine_button);
                starButton = (Button) stub.findViewById(R.id.star_button);
                hashButton = (Button) stub.findViewById(R.id.hash_button);
                callImageButton = (ImageButton) stub.findViewById(R.id.call_image_button);
                backspaceImageButton = (ImageButton) stub.findViewById(R.id.backspace_image_button);

                mDialTextView.setText("");

                zeroButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialTextView.setText(mDialTextView.getText() + "0");
                    }
                });

                zeroButton.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mDialTextView.setText(mDialTextView.getText() + "+");
                        return true;
                    }
                });

                oneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialTextView.setText(mDialTextView.getText() + "1");
                    }
                });

                twoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialTextView.setText(mDialTextView.getText() + "2");
                    }
                });

                threeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialTextView.setText(mDialTextView.getText() + "3");
                    }
                });

                fourButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialTextView.setText(mDialTextView.getText() + "4");
                    }
                });

                fiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialTextView.setText(mDialTextView.getText() + "5");
                    }
                });

                sixButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialTextView.setText(mDialTextView.getText() + "6");
                    }
                });

                sevenButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialTextView.setText(mDialTextView.getText() + "7");
                    }
                });

                eightButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialTextView.setText(mDialTextView.getText() + "8");
                    }
                });

                nineButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialTextView.setText(mDialTextView.getText() + "9");
                    }
                });

                starButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialTextView.setText(mDialTextView.getText() + "*");
                    }
                });

                hashButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialTextView.setText(mDialTextView.getText() + "#");
                    }
                });

                backspaceImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mDialTextView.getText().length() != 0)
                            mDialTextView.setText(mDialTextView.getText().subSequence(0, mDialTextView.getText().length() - 1));
                    }
                });
                backspaceImageButton.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mDialTextView.setText(null);
                        return true;
                    }
                });

                callImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!mDialTextView.getText().toString().isEmpty()) {
                            sendMessage(mDialTextView.getText().toString(), null);
                            Toast.makeText(getApplicationContext(), "Calling " + mDialTextView.getText(), Toast.LENGTH_SHORT).show();
                            mDialTextView.setText(null);
                        }
                    }
                });
            }
        });
    }

    private void sendMessage(final String message, final byte[] payload) {
        Log.i(MainActivity.class.getSimpleName(), message);
        Wearable.NodeApi.getConnectedNodes(mApiClient).setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
            @Override
            public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
                List<Node> nodes = getConnectedNodesResult.getNodes();
                for (Node node : nodes) {
                    Log.i(MainActivity.class.getSimpleName(), "WEAR sending " + message + " to " + node);
                    Wearable.MessageApi.sendMessage(mApiClient, node.getId(), message, payload).setResultCallback(new ResultCallback<MessageApi.SendMessageResult>() {
                        @Override
                        public void onResult(MessageApi.SendMessageResult sendMessageResult) {
                            Log.i(MainActivity.class.getSimpleName(), "WEAR Result " + sendMessageResult.getStatus());
                        }
                    });
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        mApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {

                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
                        Log.i(MainActivity.class.getSimpleName(), "Connection failed");
                    }
                })
                .addApi(Wearable.API)
                .build();
        mApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Wearable.MessageApi.removeListener(mApiClient, new MessageApi.MessageListener() {
            @Override
            public void onMessageReceived(MessageEvent messageEvent) {

            }
        });
        mApiClient.disconnect();
    }
}
