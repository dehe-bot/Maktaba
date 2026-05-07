package com.ElOuedUniv.maktaba.presentation.book

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ElOuedUniv.maktaba.data.model.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListView(
    onCategoriesClick: () -> Unit = {},
    onAddBookClick: () -> Unit = {},
    onBookClick: (String) -> Unit = {},
    viewModel: BookViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadBooks() }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "MY LIBRARY",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 2.sp
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.onAction(BookUiAction.ToggleColumns) }) {
                        Icon(
                            imageVector = if (uiState.isTwoColumns) Icons.Default.GridView else Icons.Default.GridOn,
                            contentDescription = "Toggle columns"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onCategoriesClick) {
                        Icon(Icons.Default.List, contentDescription = "Categories")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddBookClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = androidx.compose.foundation.shape.CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Book")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (uiState.errorMessage != null) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = uiState.errorMessage ?: "Error",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.loadBooks() }) { Text("Retry") }
                }
            } else {
                if (uiState.books.isEmpty()) {
                    EmptyBooksMessage(modifier = Modifier.align(Alignment.Center))
                } else {
                    BookGrid(
                        books = uiState.books,
                        onBookClick = onBookClick,
                        columns = if (uiState.isTwoColumns) 2 else 1,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun BookGrid(
    books: List<Book>,
    onBookClick: (String) -> Unit,
    columns: Int = 2,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(books) { book ->
            BookCard(book = book, onClick = { onBookClick(book.isbn) })
        }
    }
}

@Composable
fun BookCard(book: Book, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().height(320.dp),
        onClick = onClick,
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier.weight(1f).fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            ) {
                if (book.image_url != null) {
                    AsyncImage(
                        model = book.image_url,
                        contentDescription = book.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Bookmark,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp).align(Alignment.Center),
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
            Column(modifier = Modifier.padding(12.dp).fillMaxWidth()) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    minLines = 2
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text("ISBN", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(book.isbn.take(5) + "...", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
                    }
                    val statusColor = if (book.is_finished) Color(0xFF4CAF50) else MaterialTheme.colorScheme.primary
                    val statusIcon = if (book.is_finished) Icons.Default.CheckCircle else Icons.Default.Bookmark
                    val statusText = if (book.is_finished) "Finished" else "Reading"
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Status", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = statusIcon, contentDescription = null, modifier = Modifier.size(12.dp), tint = statusColor)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(statusText, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = statusColor)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyBooksMessage(modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "📚", style = MaterialTheme.typography.displayLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text("No books in your library", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Click the + button to add a new book", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}