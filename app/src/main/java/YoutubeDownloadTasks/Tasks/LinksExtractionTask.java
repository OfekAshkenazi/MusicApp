package YoutubeDownloadTasks.Tasks;

import android.content.Context;
import android.util.SparseArray;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;

/**
 * Created by Ofek on 17-Dec-17.
 */

public class LinksExtractionTask extends YouTubeExtractor {
    private OnExtractionCompleteListener completeListener;
    public LinksExtractionTask(Context con,OnExtractionCompleteListener completeListener) {
        super(con);
        this.completeListener=completeListener;
    }

    @Override
    protected void onExtractionComplete(SparseArray<YtFile> sparseArray, VideoMeta videoMeta) {

    }

    public interface OnExtractionCompleteListener{
        void onExtractionComplete(SparseArray<YtFile> sparseArray, VideoMeta videoMeta);
    }
}
