package curtin.edu.citysim.Activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.nio.channels.Selector;

import curtin.edu.citysim.Core.Model.Structure;
import curtin.edu.citysim.Core.Model.StructureData;
import curtin.edu.citysim.R;

public class SelectorFragment extends Fragment
{
    private StructureData structures;
    private Structure selectedStruct;

    private class StructureDataHolder extends RecyclerView.ViewHolder
    {
        private SelectorFragment fragSelect;

        private ConstraintLayout selector;
        private ImageView image;
        private TextView text;

        private Structure currentStruct;

        public StructureDataHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.list_selection, parent, false));

            this.fragSelect = fragSelect;

            selector = (ConstraintLayout)itemView.findViewById(R.id.selector);
            image = (ImageView)itemView.findViewById(R.id.selectorImage);
            text = (TextView)itemView.findViewById(R.id.selectorDescription);

            image.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    fragSelect.setSelectedStructure(currentStruct);
                }
            });
        }

        public void bind(Structure struct)
        {
            currentStruct = struct;
            image.setImageResource(currentStruct.getImageID());
        }

        public void setFragment(SelectorFragment fragSelect) { this.fragSelect = fragSelect; }
    }

    private class SelectorAdapter extends RecyclerView.Adapter<StructureDataHolder>
    {
        private StructureData structures;
        private SelectorFragment fragSelect;

        public SelectorAdapter(StructureData structures) { this.structures = structures; }
        @Override public int getItemCount() { return structures.getSize(); }

        @NonNull
        @Override
        public StructureDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            LayoutInflater li = LayoutInflater.from(getActivity());
            StructureDataHolder holder = new StructureDataHolder(li, parent);
            holder.setFragment(fragSelect);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull StructureDataHolder holder, int position)
        {
            holder.bind(structures.get(position));
        }
    }

    public SelectorFragment(StructureData structures) { this.structures = structures; }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_selector, container, false);
        RecyclerView rv = (RecyclerView)view.findViewById(R.id.selectorRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rv.setAdapter(new SelectorAdapter(structures));

        return view;
    }

    public Structure getSelectedStructure()
    {
        return selectedStruct;
    }

    public void setSelectedStructure(Structure selectedStructure)
    {
        this.selectedStruct = selectedStructure;
    }
}
