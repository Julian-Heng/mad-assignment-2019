package curtin.edu.citysim.UI.Fragment;

import android.os.Bundle;
import android.util.Log;
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
import curtin.edu.citysim.Core.Model.MapData;
import curtin.edu.citysim.Core.Model.MapElement;
import curtin.edu.citysim.R;

public class MapFragment extends Fragment
{
    private GameData game;
    private MapAdapter adapter;

    private class MapDataHolder extends RecyclerView.ViewHolder
    {
        private MapElement data;

        private MapData map;

        private ImageView cellGround;
        private ImageView cellStruct;

        public MapDataHolder(LayoutInflater inflater, ViewGroup parent, MapData map)
        {
            super(inflater.inflate(R.layout.map_cell, parent, false));

            this.map = map;

            int size = parent.getMeasuredHeight() / map.getHeight() + 1;
            ViewGroup.LayoutParams params = itemView.getLayoutParams();
            params.width = size;
            params.height = size;

            cellGround = (ImageView)itemView.findViewById(R.id.cellGround);
            cellStruct = (ImageView)itemView.findViewById(R.id.cellStruct);
        }

        public void bind(MapElement data)
        {
            this.data = data;
            cellGround.setImageResource(data.getDrawId());

            if (data.getStruct() != null)
            {
                cellStruct.setImageResource(data.getStruct().getImageID());
            }
        }
    }

    private class MapAdapter extends RecyclerView.Adapter<MapDataHolder>
    {
        private MapData map;

        public MapAdapter(MapData map) { this.map = map; }

        @Override
        public int getItemCount() { return map.getWidth() * map.getHeight(); }

        @NonNull
        @Override
        public MapDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            return new MapDataHolder(LayoutInflater.from(getActivity()), parent, map);
        }

        @Override
        public void onBindViewHolder(@NonNull MapDataHolder holder, int position)
        {
            holder.bind(map.getElement(position / map.getHeight(), position % map.getHeight()));
        }
    }

    public MapFragment(GameData game) { this.game = game; }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        RecyclerView rv = view.findViewById(R.id.map);
        rv.setLayoutManager(
            new GridLayoutManager(
                getActivity(),
                game.getMap().getHeight(),
                GridLayoutManager.HORIZONTAL,
                false
            )
        );

        rv.setAdapter((adapter = new MapAdapter(game.getMap())));

        return view;
    }
}
