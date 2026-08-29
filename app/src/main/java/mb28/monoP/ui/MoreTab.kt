package mb28.monoP.ui

import android.graphics.BitmapFactory
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mb28.monoP.R
import mb28.monoP.core.PhotosListVM
import mb28.monoP.core.Settings
import mb28.monoP.core.createOrGetThumbnail
import mb28.monoP.core.openPhoto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreTab(modifier: Modifier = Modifier, photosVM: PhotosListVM) {
    val context = LocalActivity.current!!
    Column(
        modifier.padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 75.dp)
    ) {
        val cst = rememberCarouselState(0) { photosVM.photosList.count()
            .coerceAtMost(Settings.specialSectionsCount) }
        HorizontalMultiBrowseCarousel(
            state = cst,
            preferredItemWidth = 200.dp,
            itemSpacing = 10.dp,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        ) { i ->
            Image(
                BitmapFactory.decodeFile(createOrGetThumbnail(photosVM.photosList[i].path)).asImageBitmap(),
                null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(140.dp)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(25.dp))
                    .combinedClickable(
                        onClick = {
                            openPhoto(photosVM.photosList[i].path, context)
                        }
                    )
            )
        }

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

        Text(
            stringResource(R.string.favorites),
            fontSize = 38.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        )

        MoreTabFavorites()
    }
}