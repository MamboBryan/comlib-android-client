package com.githukudenis.comlib.data.repository

import com.githukudenis.comlib.core.database.dao.BookMilestoneDao
import com.githukudenis.comlib.core.database.models.BookMilestoneEntity
import com.githukudenis.comlib.core.model.book.BookMilestone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class BookMilestoneRepositoryImpl @Inject constructor(
    private val bookMilestoneDao: BookMilestoneDao
) : BookMilestoneRepository {
    override val bookMilestone: Flow<BookMilestone?>
        get() = flow {
            val book = bookMilestoneDao.getMilestone()
            if (book != null) {
                emit(
                    BookMilestone(
                        book.bookId, book.bookName, book.startDate, book.endDate
                    )
                )
            } else {
                emit(null)
            }
        }.flowOn(Dispatchers.IO)


    override suspend fun deleteBookMilestone(bookMilestone: BookMilestone) =
        bookMilestoneDao.deleteMilestone(bookMilestone.asBookMilestoneEntity())


    override suspend fun updateBookMilestone(bookMilestone: BookMilestone) =
        bookMilestoneDao.updateMilestone(bookMilestone.asBookMilestoneEntity())


    override suspend fun insertBookMilestone(bookMilestone: BookMilestone) =
        bookMilestoneDao.setMilestone(bookMilestone.asBookMilestoneEntity())
}

fun BookMilestone.asBookMilestoneEntity(): BookMilestoneEntity {
    return BookMilestoneEntity(
        bookId = bookId, bookName = bookName, startDate = startDate, endDate = endDate
    )
}