package Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ofek.musicapp.MainActivity;
import com.example.ofek.musicapp.R;
import Adapters.YTSearchAdapter;
import YoutubeDownloadTasks.Tasks.YoutubeSearchTask;


import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.search.SearchResult;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnYtListFragmentInteractionListener}
 * interface.
 */
public class YTSearchFragment extends Fragment {

    // TODO: Customize parameters

    private OnYtListFragmentInteractionListener mListener;
    private ArrayList<InfoItem> searchItems;
    private String query;
    YTSearchAdapter adapter;
    private RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public YTSearchFragment() {


    }
    public static YTSearchFragment getInstance(String query){
        Bundle bundle=new Bundle();
        YTSearchFragment fragment=new YTSearchFragment();
        bundle.putString("query",query);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        query=getArguments().getString("query","query not found");
        if (query.equals("query not found")){
            throw new RuntimeException("query not found exception");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.yt_search_frag_layout, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }

        YoutubeSearchTask searchTask=new YoutubeSearchTask(new YoutubeSearchTask.OnSearchResultLoaded() {
            @Override
            public void onSearchResultLoaded(SearchResult result) {
                searchItems=new ArrayList<>();
                searchItems.addAll(result.resultList);
                adapter=new YTSearchAdapter(searchItems,mListener);
                recyclerView.setAdapter(adapter);
                if (MainActivity.dialog!=null) {
                    MainActivity.dialog.dismiss();
                }
            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnYtListFragmentInteractionListener) {
            mListener = (OnYtListFragmentInteractionListener) context;
        } else {
            new Exception(context.toString()
                    + " must implement OnYtListFragmentInteractionListener").printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnYtListFragmentInteractionListener {
        // TODO: Update argument type and name
        public static final int DOWNLOAD_ACTION=1;
        public static final int PLAY_ACTION=2;
        void onListFragmentInteraction(InfoItem item,int action);
    }
}
