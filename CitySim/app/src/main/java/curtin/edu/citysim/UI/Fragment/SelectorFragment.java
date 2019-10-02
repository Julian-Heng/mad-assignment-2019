package curtin.edu.citysim.UI.Fragment;

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


import java.util.List;

import curtin.edu.citysim.Core.Model.Structure;
import curtin.edu.citysim.Core.Model.StructureData;
import curtin.edu.citysim.R;

public abstract class SelectorFragment extends Fragment
{
    private List<? extends Structure> structures;

    private class StructureDataHolder extends RecyclerView.ViewHolder
    {
        private SelectorFragment frag;
        private Structure data;

        private ImageView image;

        public StructureDataHolder(LayoutInflater inflater, ViewGroup parent, SelectorFragment frag)
        {
            super(inflater.inflate(R.layout.list_selection, parent, false));
            this.frag = frag;

            image = (ImageView)itemView.findViewById(R.id.selector_image);
        }

        public void bind(Structure data)
        {
            this.data = data;
            image.setImageResource(data.getImageID());
        }
    }

    private class SelectorAdapter extends RecyclerView.Adapter<StructureDataHolder>
    {
        private List<? extends Structure> data;
        private SelectorFragment frag;

        public SelectorAdapter(List<? extends Structure> data, SelectorFragment frag)
        {
            this.data = data;
            this.frag = frag;
        }

        @Override public int getItemCount() { return data.size(); }

        @NonNull
        @Override
        public StructureDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            return new StructureDataHolder(LayoutInflater.from(getActivity()), parent, frag);
        }

        @Override
        public void onBindViewHolder(@NonNull StructureDataHolder holder, int position)
        {
            holder.bind(data.get(position));
        }
    }

    public SelectorFragment(List<? extends Structure> structures) { this.structures = structures; }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_selector, container, false);
        RecyclerView rv = view.findViewById(R.id.selector_list);
        TextView label = view.findViewById(R.id.selector_label);
        label.setText(getLabel());
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rv.setAdapter(new SelectorAdapter(structures, this));

        return view;
    }

    public abstract String getLabel();
}
