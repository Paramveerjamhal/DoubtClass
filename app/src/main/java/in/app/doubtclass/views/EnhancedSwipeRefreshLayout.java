//package in.app.doubtclass.views;
//
//import android.content.Context;
//import android.support.v4.view.ViewCompat;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.StaggeredGridLayoutManager;
//import android.util.AttributeSet;
//import android.view.View;
//import android.widget.AbsListView;
//
//public class EnhancedSwipeRefreshLayout extends SwipeRefreshLayout {
//
//    private View mTarget;
//
//    public EnhancedSwipeRefreshLayout(Context context) {
//        super(context);
//    }
//    public EnhancedSwipeRefreshLayout(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    @Override
//    public boolean canChildScrollUp(){
//        if (mTarget instanceof RecyclerView) {
//            final RecyclerView recyclerView = (RecyclerView) mTarget;
//            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//            if (layoutManager instanceof LinearLayoutManager) {
//                int position = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
//                return position != 0;
//            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
//                int[] positions = ((StaggeredGridLayoutManager) layoutManager).findFirstCompletelyVisibleItemPositions(null);
//                for (int i = 0; i < positions.length; i++) {
//                    if (positions[i] == 0) {
//                        return false;
//                    }
//                }
//            }
//            return true;
//        } else if (android.os.Build.VERSION.SDK_INT < 14) {
//            if (mTarget instanceof AbsListView) {
//                final AbsListView absListView = (AbsListView) mTarget;
//                return absListView.getChildCount() > 0
//                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
//                        .getTop() < absListView.getPaddingTop());
//            } else {
//                return mTarget.getScrollY() > 0;
//            }
//        } else {
//            return ViewCompat.canScrollVertically(mTarget, -1);
//        }
//    }
//
//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        final int width = getMeasuredWidth();
//        final int height = getMeasuredHeight();
//        if (getChildCount() == 0) {
//            return;
//        }
//        if (mTarget == null) {
//            ensureTarget();
//        }
//        if (mTarget == null) {
//            return;
//        }
//    }
//}