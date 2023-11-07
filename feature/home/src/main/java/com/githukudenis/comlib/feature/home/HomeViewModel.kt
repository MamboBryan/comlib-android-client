package com.githukudenis.comlib.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.domain.usecases.ComlibUseCases
import com.githukudenis.comlib.core.model.DataResult
import com.githukudenis.comlib.core.model.book.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val comlibUseCases: ComlibUseCases
) : ViewModel() {

    private var userProfileState: MutableStateFlow<UserProfileState> =
        MutableStateFlow(UserProfileState.Loading)

    private var booksState: MutableStateFlow<BooksState> = MutableStateFlow(BooksState.Loading)

    val state: StateFlow<HomeUiState>
        get() = combine(
            userProfileState, booksState
        ) { profile, books ->
            Log.d("bookState", books.toString())
            HomeUiState.Success(
                booksState = books,
                userProfileState = profile,
                timePeriod = comlibUseCases.getTimePeriodUseCase()
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState.Loading
        )


    init {
        setupData()
    }

    private fun setupData() {
        viewModelScope.launch {
            comlibUseCases.getUserPrefsUseCase.invoke().distinctUntilChanged()
                .collectLatest { prefs ->
                    Log.d("prefs", prefs.toString())
                    prefs.userId?.let { userId ->
                        getUserProfile(userId)
                    }

                    getBooks(
                        readBooks = prefs.readBooks, bookmarkedBooks = prefs.bookmarkedBooks
                    )
                }
        }
    }

    private suspend fun getBooks(readBooks: Set<String>, bookmarkedBooks: Set<String>) {
            comlibUseCases.getAllBooksUseCase().collectLatest { result ->
                when (result) {
                    is DataResult.Error -> {
                        booksState.update {
                            BooksState.Error(message = result.message)
                        }
                    }

                    is DataResult.Empty -> {
                        booksState.update { BooksState.Empty }
                    }

                    is DataResult.Loading -> {
                        booksState.update { BooksState.Loading }
                    }

                    is DataResult.Success -> {
                        booksState.update {
                            BooksState.Success(available = result.data,
                                readBooks = result.data.filter { book ->
                                    book.id in readBooks
                                },
                                bookmarkedBooks = result.data.filter { book -> book.id in bookmarkedBooks })
                        }
                    }
                }
            }
    }

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            HomeUiEvent.Refresh -> {
                setupData()
            }
        }
    }

    private suspend fun getUserProfile(userId: String) {
        userProfileState.update { UserProfileState.Loading }
        val profile = comlibUseCases.getUserProfileUseCase(userId)
        if (profile == null) {
            userProfileState.update { UserProfileState.Error(message = "Could not fetch profile") }
        } else {
            userProfileState.update { UserProfileState.Success(user = profile) }
        }
    }

}