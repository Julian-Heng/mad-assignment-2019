package curtin.edu.citysim.UI.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import curtin.edu.citysim.Core.Model.Game.GameData;
import curtin.edu.citysim.R;

public class StatusFragment extends Fragment
{
    private GameData game;
    private Handler handler;
    private Timer statusUpdater;

    private Map<String,TextView> txts = new HashMap<>();
    private Button btnDetails;
    private Button btnDemolish;

    /**
     * StatusFragment constructor
     *
     * @param game A GameData object to fetch info from
     */
    public StatusFragment(GameData game)
    {
        this.game = game;

        // Setup status updater as a task
        handler = new Handler()
        {
            public void handleMessage(Message m)
            {
                if (m.what == 101)
                    updateStatus();
            }
        };

        statusUpdater = new Timer();

        // Update every 0.5 seconds
        statusUpdater.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                Message m = new Message();
                m.what = 101;
                handler.sendMessage(m);
            }
        }, 500, 500);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_status, container, false);

        // Find views
        txts.put("txtGameTime", (TextView)view.findViewById(R.id.txtGameTime));
        txts.put("txtMoney", (TextView)view.findViewById(R.id.txtMoney));
        txts.put("txtSalary", (TextView)view.findViewById(R.id.txtSalary));
        txts.put("txtPopulation", (TextView)view.findViewById(R.id.txtPopulation));
        txts.put("txtEmployment", (TextView)view.findViewById(R.id.txtEmployment));

        btnDetails = (Button)view.findViewById(R.id.btnDetails);
        btnDemolish = (Button)view.findViewById(R.id.btnDemolish);

        // Set event handlers
        btnDetails.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v) { game.setMode(GameData.DETAILS); }
        });

        btnDemolish.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v) { game.setMode(GameData.DEMOLISH); }
        });

        updateStatus();

        return view;
    }

    /**
     * Update text views using information from GameData
     */
    public void updateStatus()
    {
        txts.get("txtGameTime").setText("Game Time: " + game.getGameTime());
        txts.get("txtMoney").setText("Money: $" + game.getMoney());
        txts.get("txtSalary").setText("Salary: $" + game.getSalary());
        txts.get("txtPopulation").setText("Population: " + game.getPopulation());
        txts.get("txtEmployment").setText(String.format("Employment rate:\n%.3f%%", game.getEmploymentRate() * 100));
        btnDemolish.setText(game.getMode() == GameData.DEMOLISH ? "Quit" : "Demolish");
        btnDetails.setText(game.getMode() == GameData.DETAILS ? "Quit" : "Details");
    }
}
