package com.example.artspaceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.artspaceapp.ui.theme.ArtSpaceAppTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    ArtSpaceLayout()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceLayout() {
    var collectionIndex by remember { mutableIntStateOf(0) }
    var artworkIndex by remember { mutableIntStateOf(0) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    Column(
        modifier = Modifier
            .padding(horizontal = 40.dp)
            .offset { IntOffset(0, offsetY.roundToInt()) }
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    offsetY += delta
                },
                onDragStopped = {
                    collectionIndex = if (offsetY > 0f) {
                        nextCollection(collectionIndex)
                    } else {
                        previousCollection(collectionIndex)
                    }
                    offsetY = 0f
                    artworkIndex = 0
                }
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TitleField(
            artworkIndex = artworkIndex,
            collectionIndex = collectionIndex
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        ImageField(
            artworkIndex = artworkIndex,
            collectionIndex = collectionIndex
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavButton(
                text = "<",
                color = R.color.teal_700,
                clickAction = {
                    val newIndex = previousArtwork(collectionIndex, artworkIndex)
                    artworkIndex = newIndex
                },
                modifier = Modifier.padding(end = 8.dp)
            )
            DetailsField(
                artworkIndex = artworkIndex,
                collectionIndex = collectionIndex,
                color = R.color.teal_700,
                modifier = Modifier.weight(1F)
            )
            NavButton(
                text = ">",
                color = R.color.teal_700,
                clickAction = {
                    artworkIndex = nextArtwork(collectionIndex, artworkIndex)
                },
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

fun previousArtwork(collectionIndex: Int, artworkIndex: Int): Int {
    val collection = artworks(collectionIndex)
    return (artworkIndex + collection.size - 1) % collection.size
}

fun nextArtwork(collectionIndex: Int, artworkIndex: Int): Int {
    val collection = artworks(collectionIndex)
    return (artworkIndex + 1) % collection.size
}

fun previousCollection(index: Int): Int {
    return (index + collections().size - 1) % collections().size
}

fun nextCollection(index: Int): Int {
    return (index + 1) % collections().size
}

class Artwork(
    public val title: Int,
    public val artist: Int,
    public val year: Int,
    public val image: Int
) {}

private fun collections(): List<List<Artwork>> {
    return listOf(
        listOf(
            Artwork(
                R.string.cafe_terrace,
                R.string.artist_van_gogh,
                1888,
                R.drawable.cafe_terrace_at_night
            ),
            Artwork(
                R.string.bedroom_in_arles,
                R.string.artist_van_gogh,
                1888,
                R.drawable.bedroom_in_arles
            ),
            Artwork(
                R.string.the_old_mill,
                R.string.artist_van_gogh,
                1888,
                R.drawable.the_old_mill
            ),
            Artwork(
                R.string.the_yellow_house,
                R.string.artist_van_gogh,
                1888,
                R.drawable.the_yellow_house
            ),
            Artwork(
                R.string.the_olive_trees,
                R.string.artist_van_gogh,
                1889,
                R.drawable.the_olive_trees
            )
        ),
        listOf(
            Artwork(
                R.string.i_and_the_village,
                R.string.artist_chagall,
                1911,
                R.drawable.i_and_the_village
            ),
            Artwork(
                R.string.reclining_nude,
                R.string.artist_chagall,
                1911,
                R.drawable.reclining_nude
            ),
            Artwork(
                R.string.self_portrait_seven_fingers,
                R.string.artist_chagall,
                1913,
                R.drawable.self_portrait_with_seven_fingers
            ),
            Artwork(
                R.string.the_marketplace,
                R.string.artist_chagall,
                1917,
                R.drawable.the_marketplace
            ),
        )
    )
}

private fun artworks(collectionIndex: Int) = collections()[collectionIndex]

@Composable
fun TitleField(
    artworkIndex: Int,
    collectionIndex: Int,
    modifier: Modifier = Modifier
) {
    val artwork = artworks(collectionIndex)[artworkIndex]
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(artwork.title),
            color = colorResource(R.color.teal_700),
        )
    }
}

@Composable
fun DetailsField(
    artworkIndex: Int,
    collectionIndex: Int,
    @ColorRes color: Int,
    modifier: Modifier = Modifier
) {
    val artwork = artworks(collectionIndex)[artworkIndex]
    Text(
        text = "${stringResource(artwork.artist)}\n(${artwork.year})",
        color = colorResource(color),
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@Composable
fun ImageField(
    artworkIndex: Int,
    collectionIndex: Int,
) {
    val artwork = artworks(collectionIndex)[artworkIndex]
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val maxHeight = screenHeight * 0.7f
    Image(
        painter = painterResource(artwork.image),
        contentDescription = stringResource(artwork.title),
        modifier = Modifier
            .border(
                width = 4.dp,
                brush = SolidColor(colorResource(R.color.teal_700)),
                shape = RectangleShape
            )
            .heightIn(max = maxHeight)
            .shadow(8.dp),
        contentScale = ContentScale.Fit
    )
}

@Composable
fun NavButton(
    text: String,
    @ColorRes color: Int,
    clickAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = clickAction,
        colors = ButtonDefaults.buttonColors(
            colorResource(color)
        ),
        contentPadding = PaddingValues(
            4.dp, 0.dp
        ),
        modifier = modifier
    ) {
        Text(
            text = text,
            fontSize = 6.em,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ArtSpaceAppTheme {
        ArtSpaceLayout()
    }
}