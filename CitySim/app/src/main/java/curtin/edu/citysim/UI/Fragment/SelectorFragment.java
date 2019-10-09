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

    private class StructureDataHolder extends RecyclerView.ViewHolder
    {
        private Structure data;
        private ImageView image;

        public StructureDataHolder(LayoutInflater inflater, ViewGroup parent)
        {
            super(inflater.inflate(R.layout.list_selection, parent, false));

            image = (ImageView)itemView.findViewById(R.id.selector_image);

            image.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Log.d("SELECTOR", "Selected: " + data.toString());

                    if (getAdapterPosition() == adapter.getSelected())
                    {
                        adapter.setSelected(RecyclerView.NO_POSITION);
                        game.setSelectedStruct(null);
                    }
                    else
                    {
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
        TextView txtLabel = view.findViewById(R.id.selector_label);
        txtLabel.setText(label);
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rv.setAdapter((adapter = new SelectorAdapter(structures)));

        return view;
    }
}
