
package com.Android3;

import android.app.Activity;
import android.widget.TextView;
import android.os.Bundle;

public class Android3 extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_color_activity_example);
        
        relativeLayout = findViewById(R.id.relativeLayout);
        relativeLayout.setBackgroundColor(color.RED);
        /* Create a TextView and set its text to "Hello world" */
        TextView  tv = new TextView(this);
        tv.setText("test");
        setContentView(tv);
    }
}
