package me.kartikarora.weardialer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.List;

public class MainActivity extends Activity {

    private TextView mDialTextView;

    private GoogleApiClient mApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button zeroButton, oneButton, twoButton, threeButton, fourButton, fiveButton,
                sixButton, sevenButton, eightButton, nineButton, starButton, hashButton;
        ImageButton callImageButton, backspaceImageButton;

        mDialTextView = (TextView) findViewById(R.id.dialed_no_textview);
        zeroButton = (Button) findViewById(R.id.zero_button);
        oneButton = (Button) findViewById(R.id.one_button);
        twoButton = (Button) findViewById(R.id.two_button);
        threeButton = (Button) findViewById(R.id.three_button);
        fourButton = (Button) findViewById(R.id.four_button);
        fiveButton = (Button) findViewById(R.id.five_button);
        sixButton = (Button) findViewById(R.id.six_button);
        sevenButton = (Button) findViewById(R.id.seven_button);
        eightButton = (Button) findViewById(R.id.eight_button);
        nineButton = (Button) findViewById(R.id.nine_button);
        starButton = (Button) findViewById(R.id.star_button);
        hashButton = (Button) findViewById(R.id.hash_button);
        callImageButton = (ImageButton) findViewById(R.id.call_image_button);
        backspaceImageButton = (ImageButton) findViewById(R.id.backspace_image_button);

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
                    sendMessage("/number", mDialTextView.getText().toString().getBytes());
                    mDialTextView.setText(null);
                    startActivity(new Intent(MainActivity.this, ConfirmationActivity.class)
                            .putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.SUCCESS_ANIMATION));
                }
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
                    Log.i(MainActivity.class.getSimpleName(), "WEAR sending " + message + " to " + node + "with data " + new String(payload));
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
        mApiClient.disconnect();
    }
}
