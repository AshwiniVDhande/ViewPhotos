/*
 * Created by Onkar Nene on 07-08-2017.
 */
package widgets;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Listen vertical scroll events for {@link RecyclerView} as follows:
 * <p>
 * 1. Scroll Up <p>
 * 2. Scroll Down <p>
 * 3. Scrolled to Top <p>
 * 4. Scrolled to Bottom
 * </p>
 */
public abstract class CustomVerticalScrollListener extends RecyclerView.OnScrollListener
{
    private static final int TOP = -1;
    private static final int BOTTOM = 1;
    private final LinearLayoutManager m_layoutManager;

    public CustomVerticalScrollListener(LinearLayoutManager manager)
    {
        m_layoutManager = manager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy)
    {
        final int firstVisibleItem = m_layoutManager.findFirstVisibleItemPosition();
        final int visibleItemCount = m_layoutManager.getChildCount();
        final int totalItemCount = m_layoutManager.getItemCount();

        if (!recyclerView.canScrollVertically(TOP))
        {
            onScrolledToTop();
        }
        else if (!recyclerView.canScrollVertically(BOTTOM))
        {
            onScrolledToBottom(firstVisibleItem, visibleItemCount, totalItemCount);
        }
        else if (dy < 0)
        {
            onScrolledUp(firstVisibleItem, visibleItemCount, totalItemCount);
        }
        else if (dy > 0)
        {
            onScrolledDown(firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    /**
     * Triggers when user scroll up the list.
     */
    public void onScrolledUp(int firstVisibleItem, int visibleItemCount, int totalItemCount) {}

    /**
     * Triggers when user scroll down the list.
     */
    public void onScrolledDown(int firstVisibleItem, int visibleItemCount, int totalItemCount) {}

    /**
     * Triggers when user scrolls to top (first item) of the list.
     */
    public void onScrolledToTop() {}

    /**
     * Triggers when user scrolls to bottom (last item) of the list.
     */
    public abstract void onScrolledToBottom(int firstVisibleItem, int visibleItemCount, int totalItemCount);
}