package com.example.sony.interactivestory.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sony.interactivestory.R;
import com.example.sony.interactivestory.model.Page;
import com.example.sony.interactivestory.model.Story;

public class Main2Activity extends AppCompatActivity {

    public static final String TAG = Main2Activity.class.getSimpleName();

    private Story mStory = new Story();
    private ImageView mImageView;
    private TextView mTextView;
    private Button mChoice1;
    private Button mChoice2;
    private String mName;
    private Page mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        mName = intent.getStringExtra(getString(R.string.key_name));

        if (mName==null){
            mName="Friend";
        }
        Log.d(TAG, mName);

        mImageView = (ImageView)findViewById(R.id.storyImageView);
        mTextView = (TextView)findViewById(R.id.storyTextView);
        mChoice1 = (Button)findViewById(R.id.choiceButton1);
        mChoice2 = (Button)findViewById(R.id.choiceButton2);

        loadPage(0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void loadPage(int choice){
        mCurrentPage = mStory.getPage(choice);
        Drawable mipmap = getResources().getDrawable(mCurrentPage.getImageId());
        mImageView.setImageDrawable(mipmap);

        String pageText = mCurrentPage.getText();
        //Add name if paceholder include, no add if no placeholder
        pageText = String.format(pageText, mName);

        mTextView.setText(pageText);

        if (mCurrentPage.isFinal()){
            mChoice1.setVisibility(View.INVISIBLE);
            mChoice2.setText("PLAY AGAIN");
            mChoice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        else{


        mChoice1.setText(mCurrentPage.getChoice1().getText());
        mChoice2.setText(mCurrentPage.getChoice2().getText());

        mChoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextPage = mCurrentPage.getChoice1().getNextPage();
                loadPage(nextPage);
            }
        });
        mChoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextPage = mCurrentPage.getChoice2().getNextPage();
                loadPage(nextPage);
            }
        });
        }

    }

}



