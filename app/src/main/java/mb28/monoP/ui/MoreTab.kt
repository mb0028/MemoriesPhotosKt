package mb28.monoP.ui

import android.graphics.BitmapFactory
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.cloudy.cloudy
import com.skydoves.cloudy.rememberSky
import com.skydoves.cloudy.sky
import mb28.monoP.R
import mb28.monoP.core.Settings
import mb28.monoP.core.createOrGetThumbnail
import mb28.monoP.core.getComment
import mb28.monoP.core.openPhoto
import mb28.monoP.core.photosList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreTab(modifier: Modifier = Modifier) {
    val context = LocalActivity.current!!
    LazyColumn(
        modifier,
        contentPadding = PaddingValues(bottom =
            WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 75.dp)
    ) {
        item {
            val cst = rememberCarouselState(0) { photosList.count()
                .coerceAtMost(Settings.specialSectionsCount) }
            HorizontalMultiBrowseCarousel(
                state = cst,
                preferredItemWidth = 200.dp,
                itemSpacing = 10.dp,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            ) { i ->
                Image(
                    BitmapFactory.decodeFile(createOrGetThumbnail(photosList[i].path)).asImageBitmap(),
                    null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(140.dp)
                        .fillMaxSize()
                        .clip(RoundedCornerShape(25.dp))
                        .combinedClickable(
                            onClick = {
                                openPhoto(photosList[i].path, context)
                            }
                        )
                )
            }
        }

        item {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button({}) {
                    Text(stringResource(R.string.archive))
                }
                Button({}) {
                    Text(stringResource(R.string.trash))
                }
            }
        }

        item {
            Text(
                stringResource(R.string.favorites),
                fontSize = 38.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            )
        }

        items(Settings.favorites.count()) { i ->
            val path = Settings.favorites[i]
            val sky = rememberSky()
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(5.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .combinedClickable(
                        onClick = {
                            openPhoto(path, context)
                        }
                    ),
                contentAlignment = Alignment.BottomCenter
            ) {
                Image(
                    BitmapFactory.decodeFile(createOrGetThumbnail(path)).asImageBitmap(),
                    null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .sky(sky)
                )

                Box(
                    Modifier
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                        .cloudy(sky, 20,
                            shape = RoundedCornerShape(25.dp),
                            tint = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
                        ),
                ) {
                    Text(
                        getComment(path),
                        Modifier.padding(8.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 22.sp,
                        maxLines = 2
                    )
                }
            }
        }
    }
}
