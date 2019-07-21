package ua.profarma.medbook.recyclerviews.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import ua.profarma.medbook.R;

//*****************************************************************************
// SimpleDividerItemDecoration
//-----------------------------------------------------------------------------
public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration
{
	//=============================================================================
	//SimpleDividerItemDecoration
	//-----------------------------------------------------------------------------
	public SimpleDividerItemDecoration(Context context)
	{
		divider = ContextCompat.getDrawable(context, R.drawable.line_divider_recycler_view);
	}
	//=============================================================================
	//onDrawOver
	//-----------------------------------------------------------------------------
	@Override
	public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state)
	{
		int left = parent.getPaddingLeft();
		int right = parent.getWidth() - parent.getPaddingRight();

		int childCount = parent.getChildCount();
		for(int i = 0; i < childCount - 1; i++)
		{
			View child = parent.getChildAt(i);

			RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

			int top = child.getBottom() + params.bottomMargin;
			int bottom = top + divider.getIntrinsicHeight();

			divider.setBounds(left, top, right, bottom);
			divider.draw(c);
		}
	}

	private Drawable divider;
}
