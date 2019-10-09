package curtin.edu.citysim.UI.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import java.util.Timer;
import java.util.TimerTask;

import curtin.edu.citysim.Core.Model.Game.GameData;
import curtin.edu.citysim.Core.Model.Game.MapData;
import curtin.edu.citysim.Core.Model.Game.MapElement;
import curtin.edu.citysim.R;
import curtin.edu.citysim.UI.Activity.DetailsActivity;

public class MapFragment extends Fragment
{
    public static final String COLUMN_POSITION = "column";
    public static final String ROW_POSITION = "row";
    public static final String MAP_ELEMENT = "map_element";

    private static final int REQUEST_DETAILS = 1;

    private GameData game;
    private MapAdapter adapter;

    private Handler handler;
    private Timer gameTimer;

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

            setEvent();
        }

        public void bind(MapElement data)
        {
            this.data = data;
            setCell();
        }

        private void setEvent()
        {
            cellStruct.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int i = getAdapterPosition() % map.getHeight();
                    int j = getAdapterPosition() / map.getHeight();

                    if (game.getMode() == GameData.DEMOLISH)
                    {
                        Log.d("MAP", String.format("Demolishing (%d, %d): %s", i, j, data.toString()));
                        map.demolish(i, j);
                    }
                    else if (game.getMode() == GameData.DETAILS && data.getStruct() != null)
                    {
                        Log.d("MAP", String.format("Details (%d, %d): %s", i, j, data.toString()));

                        Intent intent = new Intent(getActivity(), DetailsActivity.class);
                        intent.putExtra(ROW_POSITION, i);
                        intent.putExtra(COLUMN_POSITION, j);
                        intent.putExtra(MAP_ELEMENT, data);

                        startActivityForResult(intent, REQUEST_DETAILS);
                        game.setMode(GameData.DEFAULT);
                    }
                    else if (data.getStruct() == null && game.getSelectedStruct() != null)
                    {
                        Log.d("MAP", String.format("Building (%d, %d): %s", i, j, data.toString()));
                        game.build(i, j);
                    }

                    setCell();
                    adapter.notifyDataSetChanged();
                }
            });
        }

        private void setCell()
        {
            int i = getAdapterPosition() % map.getHeight();
            int j = getAdapterPosition() / map.getHeight();

            cellGround.setImageResource(data.getDrawId());
            Log.d("MAP", String.format("(%d, %d): %s", i, j, data.toString()));

            if (data.getImg() != null)
                cellStruct.setImageBitmap(data.getImg().getImg());
            else if (data.getStruct() != null)
                cellStruct.setImageResource(data.getStruct().getImageID());
            else
                cellStruct.setImageDrawable(null);
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
            holder.bind(map.getElement(position % map.getHeight(), position / map.getHeight()));
        }

        // From https://stackoverflow.com/a/43730205
        // Accessed on 3/10/19
        // Needed because the grid would draw duplicate structures, when there's no such structures set
        @Override public long getItemId(int position) { return position; }
        @Override public int getItemViewType(int position) { return position; }
    }

    public MapFragment(GameData game)
    {
        this.game = game;
        setHandler();
    }

    public void setHandler()
    {
        handler = new Handler()
        {
            public void handleMessage(Message m)
            {
                if (m.what == 101)
                {
                    game.step();
                    if (game.isGameOver())
                    {
                        getActivity().onBackPressed();
                        gameTimer.cancel();
                    }
                }
            }
        };

        gameTimer = new Timer();
        gameTimer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                Message m = new Message();
                m.what = 101;
                handler.sendMessage(m);
            }
        }, 5000, 5000);
    }

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK)
            return;

        int i, j;
        MapElement newData;

        if (requestCode == REQUEST_DETAILS)
        {
            i = data.getIntExtra(ROW_POSITION, -1);
            j = data.getIntExtra(COLUMN_POSITION, -1);
            newData = (MapElement)data.getSerializableExtra(MAP_ELEMENT);

            if (i == -1 || j == -1)
                return;

            game.getMap().setElement(i, j, newData);
            adapter.notifyDataSetChanged();
        }
    }
}
