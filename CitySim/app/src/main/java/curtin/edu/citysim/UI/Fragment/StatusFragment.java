package curtin.edu.citysim.UI.Fragment;

import android.content.Intent;
import android.os.Bundle;
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

import curtin.edu.citysim.Core.Model.GameData;
import curtin.edu.citysim.Core.Model.Settings;
import curtin.edu.citysim.MainActivity;
import curtin.edu.citysim.R;
import curtin.edu.citysim.UI.Activity.DetailsActivity;

public class StatusFragment extends Fragment
{
    private GameData game;

    private Map<String,TextView> txts = new HashMap<>();
    private Button btnDetails;

    public StatusFragment(GameData game) { this.game = game; }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_status, container, false);

        txts.put("txtGameTime", (TextView)view.findViewById(R.id.txtGameTime));
        txts.put("txtMoney", (TextView)view.findViewById(R.id.txtMoney));
        txts.put("txtSalary", (TextView)view.findViewById(R.id.txtSalary));
        txts.put("txtPopulation", (TextView)view.findViewById(R.id.txtPopulation));
        txts.put("txtEmployment", (TextView)view.findViewById(R.id.txtEmployment));

        btnDetails = (Button)view.findViewById(R.id.btnDetails);
        btnDetails.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(MainActivity.GAME, game);
                startActivity(intent);
            }
        });

        updateStatus();

        return view;
    }

    public void updateStatus()
    {
        txts.get("txtGameTime").setText("Game Time: " + game.getGameTime());
        txts.get("txtMoney").setText("Money: " + game.getMoney());
        txts.get("txtSalary").setText("Salary: " + game.getSalary());
        txts.get("txtPopulation").setText("Population: " + game.getPopulation());
        txts.get("txtEmployment").setText("Employment rate: " + game.getEmploymentRate());
    }
}
