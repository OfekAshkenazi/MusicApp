package YoutubeDownloadTasks.Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.search.InfoItemSearchCollector;
import org.schabi.newpipe.extractor.search.SearchEngine;
import org.schabi.newpipe.extractor.search.SearchResult;
import org.schabi.newpipe.extractor.services.youtube.YoutubeSearchEngine;

import java.io.IOException;

/**
 * Created by Ofek on 05-Dec-17.
 */

public class YoutubeSearchTask extends AsyncTask<String,Void,SearchResult> {
    private static final int YS_ID = 1111;
    private OnSearchResultLoaded loadedListener;


    public YoutubeSearchTask(OnSearchResultLoaded loadedListener) {
        this.loadedListener = loadedListener;
    }

    @Override
    protected SearchResult doInBackground(String... strings) {
        SearchResult result=searchVideos(strings[0]);
        return result;
    }


    @Override
    protected void onPostExecute(SearchResult searchResult) {
        loadedListener.onSearchResultLoaded(searchResult);
    }
    public SearchResult searchVideos(String query) {
        YoutubeSearchEngine engine=new YoutubeSearchEngine(YS_ID);
        try {
            InfoItemSearchCollector collector=engine.search(query,0,"en", SearchEngine.Filter.STREAM);
            for (InfoItem item:collector.getSearchResult().resultList){
                Log.v("Search Result Name:  ",item.name);
                Log.v("Search Result Url:  ",item.url);
            }
            return collector.getSearchResult();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExtractionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface OnSearchResultLoaded{
        void onSearchResultLoaded(SearchResult result);
    }
}
