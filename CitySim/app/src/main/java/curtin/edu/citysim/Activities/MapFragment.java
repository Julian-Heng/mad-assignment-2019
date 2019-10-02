package curtin.edu.citysim.Activities;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import curtin.edu.citysim.Core.Model.GameData;
import curtin.edu.citysim.Core.Model.MapElement;
import curtin.edu.citysim.Core.Model.Settings;
import curtin.edu.citysim.Core.Model.Structure;
import curtin.edu.citysim.R;

public class MapFragment extends Fragment
{
    private GameData game = null;
    private Settings settings = null;

    private SelectorFragment fragSelect;

    private class MapDataHolder extends RecyclerView.ViewHolder
    {
        private MapElement element;

        private MapAdapter adapter;
        private ImageView topRight;
        private ImageView topLeft;
        private ImageView bottomRight;
        private ImageView bottomLeft;
        private ImageView full;

        public MapDataHolder(LayoutInflater li, ViewGroup parent, int height)
        {
            super(li.inflate(R.layout.grid_cell, parent, false));

            int size = parent.getMeasuredHeight() / height + 1;
            ViewGroup.LayoutParams lp = itemView.getLayoutParams();
            lp.width = size;
            lp.height = size;

            topLeft = (ImageView)itemView.findViewById(R.id.cellTopLeft);
            topRight = (ImageView)itemView.findViewById(R.id.cellTopRight);
            bottomLeft = (ImageView)itemView.findViewById(R.id.cellBottomLeft);
            bottomRight = (ImageView)itemView.findViewById(R.id.cellBottomRight);
            full = (ImageView)itemView.findViewById(R.id.cellFull);

            full.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    element.setStruct(fragSelect.getSelectedStructure());
                    setCell(element);
                    adapter.notifyItemChanged(getAdapterPosition());
                }
            });
        }

        public void bind(MapElement element)
        {
            this.element = element;
            setCell(this.element);
        }

        public void setCell(MapElement element)
        {
            Structure struct = element.getStruct();

            if (struct != null)
            {
                /*
                topLeft.setImageResource(struct.getTopLeft());
                topRight.setImageResource(struct.getTopRight());
                bottomLeft.setImageResource(struct.getBottomLeft());
                bottomRight.setImageResource(struct.getBottomRight());
                 */
                topLeft.setImageResource(struct.getImageID());
                topRight.setImageResource(struct.getImageID());
                bottomLeft.setImageResource(struct.getImageID());
                bottomRight.setImageResource(struct.getImageID());
                full.setImageResource(struct.getImageID());
            }
        }

        public void setAdapter(MapAdapter adapter) { this.adapter = adapter; }
    }

    private class MapAdapter extends RecyclerView.Adapter<MapDataHolder>
    {
        private GameData game;
        private Settings settings;

        public MapAdapter(GameData game, Settings settings)
        {
            this.game = game;
            this.settings = settings;
        }

        @Override
        public int getItemCount()
        {
            return settings.getIntSetting("mapWidth", 50) * settings.getIntSetting("mapHeight", 10);
        }

        @NonNull
        @Override
        public MapDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            LayoutInflater li = LayoutInflater.from(getActivity());
            MapDataHolder holder = new MapDataHolder(li, parent, settings.getIntSetting("mapHeight", 10));
            holder.setAdapter(this);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MapDataHolder holder, int position)
        {

            holder.bind(game.getMap()[position % settings.getIntSetting("mapHeight", 10)][position / settings.getIntSetting("mapHeight", 10)]);
        }
    }

    public MapFragment(GameData game, Settings settings)
    {
        this.game = game;
        this.settings = settings;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        RecyclerView rv = (RecyclerView)view.findViewById(R.id.mapRecyclerView);
        rv.setLayoutManager(
            new GridLayoutManager(
                getActivity(),
                settings.getIntSetting("mapHeight", 10),
                GridLayoutManager.HORIZONTAL,
                false
            )
        );

        rv.setAdapter(new MapAdapter(game, settings));
        return view;
    }

    public void setSelector(SelectorFragment fragSelect) { this.fragSelect = fragSelect; }
    public SelectorFragment getSelector() { return fragSelect; }
}
