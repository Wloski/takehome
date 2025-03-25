package com.createfuture.takehome.compose.character

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.createfuture.takehome.compose.common.text.MyAppBodyText
import com.createfuture.takehome.compose.common.text.MyAppBodyTextOffset
import com.createfuture.takehome.compose.common.text.MyAppTitleText
import com.createfuture.takehome.model.GotCharacter
import com.createfuture.takehome.ui.theme.AppTheme

@Composable
fun CharacterCard(character: GotCharacter) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MyAppTitleText(text = character.name)
                Column {
                    MyAppBodyText("Seasons: ")
                    MyAppBodyText(character.tvSeries)
                }
            }

            CardTextStyle("Culture", character.culture)
            CardTextStyle("Born", character.born)
            CardTextStyle("Died", character.died)

            HorizontalDivider(modifier = Modifier.padding(top = 5.dp))
        }
    }
}

@Composable
private fun CardTextStyle(title: String, value: String) {
    Row {
        MyAppBodyText(text = "$title: ")
        MyAppBodyTextOffset(text = value)
    }
}

@Composable
@Preview(
    showBackground = true,
    backgroundColor = 0xFF000000
)
fun CharacterCardPreview() {
    AppTheme {
        CharacterCard(
            GotCharacter(
                name = "Character One",
                gender = "Male",
                culture = "English",
                born = "UK",
                died = "N/A",
                aliases = listOf("Test1", "Test2"),
                tvSeries = "I, II, IV, VI",
                playedBy = listOf("Person1", "Person2")
            )
        )
    }
}