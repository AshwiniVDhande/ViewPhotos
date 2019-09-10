package widgets;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/*
 * Created by Ashwini Dhande on 28-09-2018.
 */

/**
 * Initialize WrapContentLinearLayoutManager class which inheritance from LinearLayoutManager to set the manager to ViewPager
 */
public class WrapContentLinearLayoutManager extends LinearLayoutManager
{
    public WrapContentLinearLayoutManager(Context context)
    {
        super(context);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state)
    {
        try
        {
            super.onLayoutChildren(recycler, state);
        }
        catch (IndexOutOfBoundsException e)
        {
        }
    }
}
