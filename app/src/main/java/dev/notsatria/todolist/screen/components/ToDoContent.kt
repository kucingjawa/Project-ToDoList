package dev.notsatria.todolist.screen.components

import android.R.attr.type
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.notsatria.todolist.model.Priority
import dev.notsatria.todolist.theme.ToDoListTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoContent(
    modifier: Modifier = Modifier,
    title: String = "",
    setTitle: (String) -> Unit = {},
    desc: String = "",
    setDesc: (String) -> Unit = {},
    navigateBack: () -> Unit = {},
    onButtonClick: () -> Unit = {},
    type: ToDoScreenType = ToDoScreenType.ADD,
    selectedPriority: Priority? = null,
    priorities: List<Priority> = emptyList(),
    onSelectPriority: (Priority) -> Unit = {},
) {
    val titleText = if (type == ToDoScreenType.ADD) "Add Todo" else "Edit Todo"
    val buttonText = if (type == ToDoScreenType.ADD) "Add Task" else "Edit Task"
    Scaffold(modifier = modifier, topBar = {
        TopAppBar(title = {
            Text(titleText)
        }, navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
            }
        })
    }) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = setTitle,
                label = {
                    Text("Title")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = desc, onValueChange = setDesc,
                label = {
                    Text("Description")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                minLines = 4
            )
            Spacer(Modifier.height(16.dp))
            TodoTypeCard(priorities = priorities, selectedPriority = selectedPriority, onSelect = onSelectPriority)
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = onButtonClick, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                enabled = title.isNotEmpty() && desc.isNotEmpty() && selectedPriority != null
            ) {
                Text(buttonText)
            }
        }
    }
}

@Composable
fun TodoTypeCard(
    modifier: Modifier = Modifier,
    priorities: List<Priority>,
    selectedPriority: Priority? = null,
    onSelect: (Priority) -> Unit = {},
) {
    var priorityExpanded by remember { mutableStateOf(false) }
    Card(
        onClick = {
            priorityExpanded = true
        }, modifier = modifier.padding(start = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selectedPriority?.color != null)
                Color(selectedPriority.color)
            else MaterialTheme.colorScheme.surfaceContainerHigh
        )
    ) {
        Box(Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
            Text(
                selectedPriority?.name ?: "Select priority",
                style = MaterialTheme.typography.bodyLarge
            )
            DropdownMenu(
                expanded = priorityExpanded,
                onDismissRequest = { priorityExpanded = false }
            ) {
                priorities.forEach {
                    DropdownMenuItem(
                        text = { Text(it.name) },
                        onClick = {
                            priorityExpanded = false
                            onSelect(it)
                        },
                    )
                }
            }
        }
    }
}

enum class ToDoScreenType {
    ADD, EDIT
}

@Preview
@Composable
private fun Preview() {
    ToDoListTheme {
        ToDoContent()
    }
}