package curtin.edu.citysim.UI.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import curtin.edu.citysim.Core.Model.Game.GameData;
import curtin.edu.citysim.Core.Model.Structures.Structure;
import curtin.edu.citysim.R;

public class SelectorFragment extends Fragment
{
    private List<? extends Structure> structures;
    private String label;
    private GameData game;
    private SelectorAdapter adapter;

    /**
     * StructureDataHolder that extends the RecyclerView's ViewHolder
     * Holds a structure for each list
     */
    private class StructureDataHolder extends RecyclerView.ViewHolder
    {
        private Structure data;
        private ImageView image;

        public StructureDataHolder(LayoutInflater inflater, ViewGroup parent)
        {
            super(inflater.inflate(R.layout.list_selection, parent, false));

            // Find and set the image in the holder
            image = (ImageView)itemView.findViewById(R.id.selector_image);

            image.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Log.d("SELECTOR", "Selected: " + data.toString());

                    // If we select the same struct that is recorded in the game
                    if (getAdapterPosition() == adapter.getSelected())
                    {
                        // Reset the selected struct to null
                        adapter.setSelected(RecyclerView.NO_POSITION);
                        game.setSelectedStruct(null);
                    }
                    else
                    {
                        // Set the selected struct in the game object
                        adapter.setSelected(getAdapterPosition());
                        game.setSelectedStruct(data);
                    }

                    adapter.notifyItemChanged(getAdapterPosition());
                }
            });
        }

        public void bind(Structure data)
        {
            this.data = data;
            image.setImageResource(data.getImageID());
        }
    }

    /**
     * SelectorAdapter class that extends RecyclerView's adapter
     */
    private class SelectorAdapter extends RecyclerView.Adapter<StructureDataHolder>
    {
        private List<? extends Structure> structures;
        private int selected = RecyclerView.NO_POSITION;

        public SelectorAdapter(List<? extends Structure> data) { this.structures = data; }

        @Override public int getItemCount() { return structures.size(); }

        @NonNull
        @Override
        public StructureDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            return new StructureDataHolder(LayoutInflater.from(getActivity()), parent);
        }

        @Override
        public void onBindViewHolder(@NonNull StructureDataHolder holder, int position)
        {
            holder.bind(structures.get(position));
        }

        public void setSelected(int selected) { this.selected = selected; }
        public int getSelected() { return selected; }
    }

    /**
     * Selector fragment constructor
     *
     * @param structures List of structures to be displayed in the list
     * @param label The text set to the label in the selector fragment
     * @param game A GameData object to record changes to
     */
    public SelectorFragment(List<? extends Structure> structures, String label, GameData game)
    {
        this.structures = structures;
        this.label = label;
        this.game = game;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_selector, container, false);
        RecyclerView rv = view.findViewById(R.id.selector_list);

        // Find and set label
        TextView txtLabel = view.findViewById(R.id.selector_label);
        txtLabel.setText(label);

        // Setup recycler view
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rv.setAdapter((adapter = new SelectorAdapter(structures)));

        return view;
    }
}
