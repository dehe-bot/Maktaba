package com.ElOuedUniv.maktaba.presentation.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.ElOuedUniv.maktaba.data.model.Category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryListView(
    onBackClick: () -> Unit,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val categories by viewModel.categories.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "CATEGORIES",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 2.sp
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                if (categories.isEmpty()) {
                    EmptyCategoriesMessage(modifier = Modifier.align(Alignment.Center))
                } else {
                    CategoryList(
                        categories = categories,
                        onExpandClick = { selectedCategory = it },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }

    // Dialog for expanded category detail
    selectedCategory?.let { category ->
        Dialog(onDismissRequest = { selectedCategory = null }) {
            Card(
                shape = MaterialTheme.shapes.extraLarge,
                elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        modifier = Modifier.size(80.dp),
                        shape = MaterialTheme.shapes.large,
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Icon(
                            imageVector = getCategoryIcon(category.name),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize(),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = category.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = category.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 22.sp
                    )
                    if (category.id.isNotBlank()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Tag,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "ID: ${category.id}",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = { selectedCategory = null },
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Text("Close")
                    }
                }
            }
        }
    }
}

fun getCategoryIcon(name: String): ImageVector {
    return when {
        name.contains("program", ignoreCase = true) ||
        name.contains("code", ignoreCase = true) ||
        name.contains("dev", ignoreCase = true) -> Icons.Default.Code

        name.contains("algo", ignoreCase = true) ||
        name.contains("data struct", ignoreCase = true) -> Icons.Default.AccountTree

        name.contains("database", ignoreCase = true) ||
        name.contains("sql", ignoreCase = true) -> Icons.Default.Storage

        name.contains("network", ignoreCase = true) ||
        name.contains("web", ignoreCase = true) -> Icons.Default.Wifi

        name.contains("security", ignoreCase = true) ||
        name.contains("crypto", ignoreCase = true) -> Icons.Default.Security

        name.contains("mobile", ignoreCase = true) ||
        name.contains("android", ignoreCase = true) -> Icons.Default.PhoneAndroid

        name.contains("ai", ignoreCase = true) ||
        name.contains("machine", ignoreCase = true) ||
        name.contains("learning", ignoreCase = true) -> Icons.Default.Psychology

        name.contains("math", ignoreCase = true) -> Icons.Default.Calculate

        name.contains("design", ignoreCase = true) ||
        name.contains("ui", ignoreCase = true) -> Icons.Default.DesignServices

        name.contains("science", ignoreCase = true) -> Icons.Default.Science

        else -> Icons.Default.MenuBook
    }
}

@Composable
fun CategoryList(
    categories: List<Category>,
    onExpandClick: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(categories) { category ->
            CategoryItem(category = category, onExpandClick = { onExpandClick(category) })
        }
    }
}

@Composable
fun CategoryItem(category: Category, onExpandClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        shape = MaterialTheme.shapes.extraLarge,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Category Icon
            Surface(
                modifier = Modifier.size(80.dp),
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
            ) {
                Icon(
                    imageVector = getCategoryIcon(category.name),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Text Details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = category.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    lineHeight = 20.sp
                )
            }

            // Expand / Zoom button
            IconButton(onClick = onExpandClick) {
                Icon(
                    imageVector = Icons.Default.ZoomIn,
                    contentDescription = "Expand",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun EmptyCategoriesMessage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.FolderOpen,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No categories available",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Complete the TODO exercises in TP2",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
