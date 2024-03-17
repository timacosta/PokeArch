package com.architects.pokearch.testing.paging

import androidx.paging.CombinedLoadStates
import androidx.paging.DifferCallback
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.NullPaddedList
import androidx.paging.PagingData
import androidx.paging.PagingDataDiffer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.firstOrNull

@Suppress("EmptyFunctionBlock")
class LazyPagingItemsTest<T : Any> (
    private val flow: Flow<PagingData<T>>,
    private val dispatcher: CoroutineDispatcher,
) {

    private val differCallback: DifferCallback = object : DifferCallback {
        override fun onChanged(position: Int, count: Int) {}

        override fun onInserted(position: Int, count: Int) {}

        override fun onRemoved(position: Int, count: Int) {}
    }

    private val pagingDataDiffer = object : PagingDataDiffer<T>(
        differCallback = differCallback,
        mainContext = dispatcher,
        cachedPagingData =
        if (flow is SharedFlow<PagingData<T>>) flow.replayCache.firstOrNull() else null,
    ) {
        override suspend fun presentNewList(
            previousList: NullPaddedList<T>,
            newList: NullPaddedList<T>,
            lastAccessedIndex: Int,
            onListPresentable: () -> Unit,
        ): Int? {
            onListPresentable()
            updateItemSnapshotList()
            return null
        }
    }

    suspend fun initPagingDiffer() {
        val pagingData = flow.firstOrNull() ?: PagingData.empty()
        pagingDataDiffer.collectFrom(pagingData)
        loadState = pagingDataDiffer.loadStateFlow.value ?: loadState
    }

    var itemSnapshotList = pagingDataDiffer.snapshot()

    val itemCount: Int get() = itemSnapshotList.size

    fun updateItemSnapshotList() {
        itemSnapshotList = pagingDataDiffer.snapshot()
    }

    operator fun get(index: Int): T? {
        pagingDataDiffer[index]
        return itemSnapshotList[index]
    }

    fun peek(index: Int): T? {
        return itemSnapshotList[index]
    }

    fun retry() {
        pagingDataDiffer.retry()
    }

    fun refresh() {
        pagingDataDiffer.refresh()
    }

    var loadState: CombinedLoadStates =
        pagingDataDiffer.loadStateFlow.value
            ?: CombinedLoadStates(
                refresh = InitialLoadStates.refresh,
                prepend = InitialLoadStates.prepend,
                append = InitialLoadStates.append,
                source = InitialLoadStates,
            )
}

private val IncompleteLoadState = LoadState.NotLoading(false)
private val InitialLoadStates = LoadStates(
    LoadState.Loading,
    IncompleteLoadState,
    IncompleteLoadState,
)
