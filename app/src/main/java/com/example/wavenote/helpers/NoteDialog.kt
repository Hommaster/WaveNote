package com.example.wavenote.helpers

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wavenote.R
import com.example.wavenote.model.NoteType
import com.example.wavenote.routes.Routes

@Preview
@Composable
fun PreviewNoteDialog(){
    val context = LocalContext.current
    NoteDialog(
        navController = rememberNavController(),
        onDismissRequest = {
            Toast.makeText(context, "Denied", Toast.LENGTH_SHORT).show()
        }
    )
}

@Composable
fun NoteDialog(
    onDismissRequest: () -> Unit,
    navController: NavHostController
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .clip(RoundedCornerShape(26.dp))
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 4.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.type_note_question),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(24.dp)
                )
                NoteType.values().forEach { noteType ->
                    Text(
                        modifier = Modifier
                            .clickable {
                                navController.navigate(Routes.TextNote.route)
                            },
                        text = stringResource(id = noteType.nameNoteType),
                    )
                }
            }
        }
    }
}