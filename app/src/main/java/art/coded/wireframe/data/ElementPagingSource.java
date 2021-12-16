package art.coded.wireframe.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingSource;
import androidx.paging.PagingState;

import java.util.concurrent.Executor;

import kotlin.coroutines.Continuation;

public class ElementPagingSource extends PagingSource<Integer, Element> {

    public ElementPagingSource(String query, Executor executor) {
        super();
    }

    @Nullable @Override public Integer getRefreshKey(@NonNull PagingState<Integer, Element> pagingState) {

        Integer anchorPosition = pagingState.getAnchorPosition();
        if (anchorPosition == null) return null;

        LoadResult.Page<Integer, Element> anchorPage = pagingState.closestPageToPosition(anchorPosition);
        if (anchorPage == null) return null;

        Integer prevKey = anchorPage.getPrevKey();
        if (prevKey != null) return prevKey + 1;

        Integer nextKey = anchorPage.getNextKey();
        if (nextKey != null) return nextKey - 1;

        return null;
    }

    @Nullable @Override public Object load(@NonNull LoadParams<Integer> loadParams, @NonNull Continuation<? super LoadResult<Integer, Element>> continuation) {

        return null;
    }
}
