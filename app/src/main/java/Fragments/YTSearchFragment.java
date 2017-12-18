package Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ofek.musicapp.R;
import Adapters.YTSearchAdapter;


import org.schabi.newpipe.extractor.InfoItem;

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

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public YTSearchFragment() {


    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.yt_search_frag_layout, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (searchItems.size() <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(new YTSearchAdapter(searchItems, mListener));
            }
        }
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
