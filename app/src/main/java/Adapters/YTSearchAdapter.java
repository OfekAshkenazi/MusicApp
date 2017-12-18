package Adapters;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import Fragments.YTSearchFragment.OnYtListFragmentInteractionListener;

import com.example.ofek.musicapp.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.schabi.newpipe.extractor.InfoItem;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link InfoItem} and makes a call to the
 * specified {@link OnYtListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class YTSearchAdapter extends RecyclerView.Adapter<YTSearchAdapter.ViewHolder> {

    private ArrayList<InfoItem> items;
    private OnYtListFragmentInteractionListener iListener;
    private HashMap<Integer,File> bitmapHashMap=new HashMap<>();
    public YTSearchAdapter(ArrayList<InfoItem> items, OnYtListFragmentInteractionListener listener) {
        this.items = items;
        iListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.yt_search_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        InfoItem item=items.get(position);
        holder.nameTV.setText(item.name);
        if (bitmapHashMap.containsKey(position)){
            Picasso.with(holder.itemView.getContext()).load(bitmapHashMap.get(position)).fit().into(holder.previewIV);
        }
        else {
            RequestCreator image=Picasso.with(holder.itemView.getContext()).load(item.thumbnail_url);
            image.fit().into(holder.previewIV);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private static final int ACTION_TAG_KEY = 4 ;
        public TextView nameTV;
        Button playBtn,downloadBtn;
        ImageView previewIV;
        public ViewHolder(View view) {
            super(view);
            nameTV=itemView.findViewById(R.id.nameTV_ytList);
            playBtn=itemView.findViewById(R.id.playBtn_ytList);
            playBtn.setTag(ACTION_TAG_KEY,OnYtListFragmentInteractionListener.PLAY_ACTION);
            downloadBtn=itemView.findViewById(R.id.downloadBtn_ytList);
            downloadBtn.setTag(ACTION_TAG_KEY,OnYtListFragmentInteractionListener.DOWNLOAD_ACTION);
            previewIV=itemView.findViewById(R.id.previewIV_ytList);
            View.OnClickListener listener=new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iListener.onListFragmentInteraction(items.get(getAdapterPosition()), (Integer) view.getTag(ACTION_TAG_KEY));
                }
            };
            playBtn.setOnClickListener(listener);
            downloadBtn.setOnClickListener(listener);
        }
    }
}
