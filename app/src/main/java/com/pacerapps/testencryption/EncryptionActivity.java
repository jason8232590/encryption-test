package com.pacerapps.testencryption;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pacerapps.EncApp;

import javax.inject.Inject;

public class EncryptionActivity extends AppCompatActivity implements View.OnClickListener, EncryptionActivityView {

    Button encryptButton;
    Button decryptButton;
    Button encryptToDbButton;
    Button decryptFromDbButton;
    Button playOriginalButton;
    Button playEncryptedButton;
    Button playDeryptedButton;
    Button playDecryptedFromDbButton;
    TextView statusTextView;
    int pink;
    int green;

    @Inject
    EncryptionActivityPresenter presenter;

    public static final String TAG = "jwc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encryption);
        defineWidgets();
        setClickListeners();
        EncApp.getInstance().getAppComponent().inject(this);
        pink = getResources().getColor(R.color.colorAccent);
        green = getResources().getColor(R.color.green);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.makeDirectory();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void defineWidgets() {
        encryptButton = (Button) findViewById(R.id.button_encrypt);
        decryptButton = (Button) findViewById(R.id.button_decrypt);
        encryptToDbButton = (Button) findViewById(R.id.button_add_to_db);
        decryptFromDbButton = (Button) findViewById(R.id.button_decrypt_from_db);
        playOriginalButton = (Button) findViewById(R.id.button_play_source);
        playDeryptedButton = (Button) findViewById(R.id.button_play_decrypt);
        playEncryptedButton = (Button) findViewById(R.id.button_play_encrypted);
        playDecryptedFromDbButton = (Button) findViewById(R.id.button_play_decrypted_from_db);
        statusTextView = (TextView) findViewById(R.id.textview_status);
    }

    private void setClickListeners() {
        encryptButton.setOnClickListener(this);
        decryptButton.setOnClickListener(this);
        encryptToDbButton.setOnClickListener(this);
        decryptFromDbButton.setOnClickListener(this);
        playOriginalButton.setOnClickListener(this);
        playDeryptedButton.setOnClickListener(this);
        playEncryptedButton.setOnClickListener(this);
        playDecryptedFromDbButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: RUNNING!!!");

        if (v == encryptButton) {
            presenter.encryptFile();
        } else if (v == decryptButton) {
            presenter.decryptFile();
        } else if (v == playOriginalButton) {
            presenter.playOriginal();
        } else if (v == playDeryptedButton) {
            presenter.playDecrypted();
        } else if (v == playEncryptedButton) {
            presenter.playEncrypted();
        } else if (v == encryptToDbButton) {
            presenter.encryptToDb();
        } else if (v == decryptFromDbButton) {
            presenter.decryptFromDb();
        } else if (v == playDecryptedFromDbButton) {
            presenter.playDecryptedFromDb();
        }
    }

    private void showUser(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /*Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.show();*/

                statusTextView.setText(message);
            }
        });

    }

    @Override
    public void onFileWrittenFromRaw() {
        showUser("Song written to file system");
        enableButton(encryptButton, false);
        enableButton(encryptToDbButton, false);
        enableButton(playOriginalButton, true);
    }

    @Override
    public void onEncrypt() {
        Log.d(TAG, "onEncrypt: running!");
        showUser("Song File Encrypted");
        enableButton(decryptButton, false);
        enableButton(playEncryptedButton, true);
    }

    @Override
    public void onDecrypt() {
        Log.d(TAG, "onDecrypt: running!");
        showUser("Song File Decrypted");
        enableButton(playDeryptedButton, true);
    }

    @Override
    public void onSongEncryptedToDb() {
        showUser("File Encrypted To DB");
        enableButton(decryptFromDbButton, false);
    }

    @Override
    public void onSongDecryptedFromDb() {
        showUser("File Decrypted From DB");
        enableButton(playDecryptedFromDbButton, true);
    }

    @Override
    public void onPlayOriginal() {
        Log.d(TAG, "onPlayOriginal: running!");
    }

    @Override
    public void onPlayDecrypted() {
        Log.d(TAG, "onPlayDecrypted: running!");
    }

    @Override
    public void onPlayEncrypted() {
        Log.d(TAG, "onPlayEncrypted: running!");
    }

    @Override
    public void onPlayDecryptedFromDb() {
        Log.d(TAG, "onPlayDecryptedFromDb: running!");
    }

    @Override
    public void onMusicPlaying(String songPath) {
        String nowPlaying = "PLAYING " + songPath;
        showUser(nowPlaying);
    }

    @Override
    public void onMusicStopped() {
        showUser("");
    }

    @Override
    public void onError(Exception e) {
        showUser(e.getClass().getSimpleName() + " occurred!");
    }

    private void enableButton(final Button button, final boolean play) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (play) {
                    button.setTextColor(green);
                } else {
                    button.setTextColor(pink);
                }
                button.setClickable(true);
            }
        });

    }
}
