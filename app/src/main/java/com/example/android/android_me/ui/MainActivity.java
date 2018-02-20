package com.example.android.android_me.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

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

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.android_me_linear_layout) != null) {
            mTwoPane = true;

            GridView gridView = (GridView) findViewById(R.id.gv_master_list);
            gridView.setNumColumns(2);

            Button nextButton = (Button) findViewById(R.id.next_button);
            nextButton.setVisibility(View.GONE);

            if (savedInstanceState == null) {

                BodyPartFragment headFragment = new BodyPartFragment();
                headFragment.setImageIds(AndroidImageAssets.getHeads());
                headFragment.setListIndex(headIndex);

                BodyPartFragment bodyFragment = new BodyPartFragment();
                bodyFragment.setImageIds(AndroidImageAssets.getBodies());
                bodyFragment.setListIndex(bodyIndex);

                BodyPartFragment legFragment = new BodyPartFragment();
                legFragment.setImageIds(AndroidImageAssets.getLegs());
                legFragment.setListIndex(legIndex);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.head_container, headFragment)
                        .add(R.id.body_container, bodyFragment)
                        .add(R.id.leg_container, legFragment)
                        .commit();

            }
        } else {
            mTwoPane = false;
        }
    }

    @Override
    public void onImageSelected(int position) {
        Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_SHORT).show();

        if (mTwoPane) {
            BodyPartFragment bodyPartFragment = new BodyPartFragment();

            int bodyPartNumber = position / 12;
            int listIndex = position - 12 * bodyPartNumber;

            switch (bodyPartNumber) {
                case HEAD_PART_NUMBER:
                    bodyPartFragment.setImageIds(AndroidImageAssets.getHeads());
                    bodyPartFragment.setListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.head_container, bodyPartFragment)
                            .commit();
                    break;
                case BODY_PART_NUMBER:
                    bodyPartFragment.setImageIds(AndroidImageAssets.getBodies());
                    bodyPartFragment.setListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.body_container, bodyPartFragment)
                            .commit();
                    break;
                case LEG_PART_NUMBER:
                    bodyPartFragment.setImageIds(AndroidImageAssets.getLegs());
                    bodyPartFragment.setListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.leg_container, bodyPartFragment)
                            .commit();
                    break;
                default:
                    break;
            }

        } else {
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
