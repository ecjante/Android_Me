package com.example.android.android_me.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.android_me.R;

public class MainActivity extends AppCompatActivity implements MasterListFragment.OnImageClickListener {

    private static final int HEAD_PART_NUMBER = 0;
    private static final int BODY_PART_NUMBER = 1;
    private static final int LEG_PART_NUMBER = 2;

    public static final String HEAD_INDEX_EXTRA = "headIndex";
    public static final String BODY_INDEX_EXTRA = "bodyIndex";
    public static final String LEG_INDEX_EXTRA = "legIndex";

    private int headIndex;
    private int bodyIndex;
    private int legIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onImageSelected(int position) {
        Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_SHORT).show();

        setBodyPartIndexes(position);

        final Intent intent = getAndroidMeActivityIntent();

        Button nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

    private void setBodyPartIndexes(int position) {
        int bodyPartNumber = position / 12;

        int listIndex = position - 12 * bodyPartNumber;

        switch (bodyPartNumber) {
            case HEAD_PART_NUMBER: headIndex = listIndex;
                break;
            case BODY_PART_NUMBER: bodyIndex = listIndex;
                break;
            case LEG_PART_NUMBER: legIndex = listIndex;
                break;
            default:
                break;
        }
    }

    private Intent getAndroidMeActivityIntent() {
        Bundle b = new Bundle();
        b.putInt(HEAD_INDEX_EXTRA, headIndex);
        b.putInt(BODY_INDEX_EXTRA, bodyIndex);
        b.putInt(LEG_INDEX_EXTRA, legIndex);

        Intent intent = new Intent(this, AndroidMeActivity.class);
        intent.putExtras(b);

        return intent;
    }

}
