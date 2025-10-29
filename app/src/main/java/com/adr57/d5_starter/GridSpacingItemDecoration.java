package com.adr57.d5_starter;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int horizontalSpacing, verticalSpacing;
    private boolean includeEdge;

    public GridSpacingItemDecoration(int spanCount, int horizontalSpacing, int verticalSpacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.horizontalSpacing = horizontalSpacing;
        this.verticalSpacing = verticalSpacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        if (position >= 0) {
            int column = position % spanCount;

            if (includeEdge) {
                outRect.left = horizontalSpacing - column * horizontalSpacing / spanCount;
                outRect.right = (column + 1) * horizontalSpacing / spanCount;

                if (position < spanCount) {
                    outRect.top = verticalSpacing;
                }
                outRect.bottom = verticalSpacing;
            } else {
                outRect.left = column * horizontalSpacing / spanCount;
                outRect.right = horizontalSpacing - (column + 1) * horizontalSpacing / spanCount;
                if (position >= spanCount) {
                    outRect.top = verticalSpacing;
                }
            }
        }
    }
}
