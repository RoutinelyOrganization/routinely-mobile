package com.routinely.routinely.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.routinely.routinely.R
import com.routinely.routinely.ui.theme.PurpleRoutinely
import com.routinely.routinely.ui.theme.SecondaryYellowRoutinely
import com.routinely.routinely.util.ActionItem
import com.routinely.routinely.util.TaskCategory
import com.routinely.routinely.util.TaskItems

@Composable
fun TasksViewerRoutinely(
    listOfTaskItems: List<TaskItems>,
    listOfConcludedTaskItems: List<TaskItems>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 24.dp, start = 12.dp, end = 12.dp)
            .border(
                width = 1.dp,
                color = PurpleRoutinely,
                shape = RoundedCornerShape(10.dp)
            ),
    ) {
        Text(
            text = stringResource(R.string.label_tasks_viewer),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = PurpleRoutinely,
            modifier = Modifier
                .padding(top = 16.dp, bottom = 6.dp, end = 16.dp, start = 16.dp)
        )
        Column(
            Modifier
                .fillMaxWidth(),
        ) {
            for(item in listOfTaskItems) {
                TaskItem(item)
            }
        }


        Spacer(modifier = Modifier
            .border(1.dp, PurpleRoutinely)
            .fillMaxWidth()
            .height(1.dp)
        )

        Text(
            text = stringResource(R.string.label_completed_tasks),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = PurpleRoutinely,
            modifier = Modifier
                .padding(top = 12.dp, bottom = 6.dp, end = 16.dp, start = 16.dp)
        )
        Column(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
        ) {
            for(item in listOfConcludedTaskItems) {
                ConcludedTaskItem(item)
            }
        }
    }
}


/**
 * If someone tries to fix the focus and I can't update the counter below with how many more hours you spent here
 * Counter: 3 hours
 */
@Composable
private fun TaskItem(
    taskItem: TaskItems
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val isFocused = remember { mutableStateOf(false) }
    val checkedState = remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
            ) {
                if (!isFocused.value) {
                    focusRequester.requestFocus()
                } else {
                    focusManager.clearFocus()
                }
                isFocused.value = !isFocused.value
            },

        ) {
        CheckButtonTasks(checkedState, focusManager, focusRequester)

        TextWithMarquee(taskItem.nameOfTask, focusRequester)

        Row{
            CategoryItem(taskItem.category)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = { },
                    enabled = false,
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = taskItem.taskPriorities.icon),
                        contentDescription = stringResource(id = taskItem.taskPriorities.contentDescription),
                        tint = Color.Unspecified,
                        modifier = Modifier.size(20.dp)
                    )
                }

                ActionsForTask(listOfActions = taskItem.listOfActions!!,)
            }
        }
    }
}

@Composable
private fun ConcludedTaskItem(
    taskItem: TaskItems
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val isFocused = remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
            ) {
                if (!isFocused.value) {
                    focusRequester.requestFocus()
                } else {
                    focusManager.clearFocus()
                }
                isFocused.value = !isFocused.value
            },

        ) {
        CheckButtonConcludedTask(focusManager, focusRequester)

        TextConcludedWithMarquee(taskItem.nameOfTask, focusRequester)

        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            CategoryItem(category = taskItem.category)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = { },
                    enabled = false,
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = taskItem.taskPriorities.icon),
                        contentDescription = stringResource(id = taskItem.taskPriorities.contentDescription),
                        tint = Color.Unspecified,
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryItem(category: TaskCategory) {
    Column(
        modifier = Modifier
            .padding(start = 12.dp, end = 6.dp)
            .background(SecondaryYellowRoutinely, shape = RoundedCornerShape(20))
            .width(70.dp)
            ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(category.stringId),
            fontSize = 12.sp,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ActionsForTask(listOfActions: List<ActionItem>) {
    for(action in listOfActions) {
        IconButton(
            onClick = action.onClick
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = action.imageVectorId),
                contentDescription = stringResource(id = action.contentDescriptionId),
                tint = Color.Unspecified,
                modifier = Modifier.size(18.dp)
            )
        }
    }

}

@Composable
private fun RowScope.CheckButtonTasks(
    checkedState: MutableState<Boolean>,
    focusManager: FocusManager,
    focusRequester: FocusRequester
) {
    Checkbox(
        checked = checkedState.value,
        /**
         * Is this correctly? I don't know,
         * but at the moment I'm clearing the focus here to stop basicMarquee from text...
         * when user click on CheckBox as well
         */
        onCheckedChange = {
            focusManager.clearFocus()
            checkedState.value = it
        },
        modifier = Modifier
            .align(Alignment.CenterVertically)
            .focusable()
            .focusRequester(focusRequester),
    )
}

@Composable
private fun RowScope.CheckButtonConcludedTask(
    focusManager: FocusManager,
    focusRequester: FocusRequester
) {
    Checkbox(
        checked = true,
        onCheckedChange = {
            focusManager.clearFocus()
        },
        modifier = Modifier
            .align(Alignment.CenterVertically)
            .focusable()
            .focusRequester(focusRequester),
        enabled = false
    )
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun RowScope.TextWithMarquee(text: String, focusRequester: FocusRequester) {
    Text(
        text = text,
        /**
         * basicMarquee will start once the user click on the text
         * It will also stop if the user click again.
         * If the user clicks on any component other than this line, it will not interrupt basicMarquee...
         * I can't make the background takes the focus
         */
        modifier = Modifier
            .fillMaxWidth(0.33f)
            .align(Alignment.CenterVertically)
            .basicMarquee(
                animationMode = MarqueeAnimationMode.WhileFocused,
                velocity = 40.dp,
                delayMillis = 1500
            )
            .focusRequester(focusRequester = focusRequester)
            .focusable(),
        fontSize = 14.sp,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
    )
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun RowScope.TextConcludedWithMarquee(text: String, focusRequester: FocusRequester) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth(0.33f)
            .align(Alignment.CenterVertically)
            .basicMarquee(
                animationMode = MarqueeAnimationMode.WhileFocused,
                velocity = 40.dp,
                delayMillis = 1500
            )
            .focusRequester(focusRequester = focusRequester)
            .focusable(),
        fontSize = 14.sp,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        textDecoration = TextDecoration.LineThrough,
        color = Color.Gray
    )
}