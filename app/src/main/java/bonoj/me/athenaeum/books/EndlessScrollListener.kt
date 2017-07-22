package bonoj.me.athenaeum.books

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView

class EndlessScrollListener(
        val func: () -> Unit,
        val layoutManager: GridLayoutManager) : RecyclerView.OnScrollListener() {

    private var previousTotal = 0
    private var loading = true
    private var desiredThreshold = 10
    private var firstVisibleItem = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy > 0) {
            visibleItemCount = recyclerView.childCount;
            totalItemCount = layoutManager.itemCount;
            firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }
            if (!loading && (totalItemCount - visibleItemCount)
                    <= (firstVisibleItem + desiredThreshold)) {

                // Reached desired threshold
                func()
                loading = true;
            }
        }
    }
}