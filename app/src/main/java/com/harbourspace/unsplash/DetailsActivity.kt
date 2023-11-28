package com.harbourspace.unsplash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.Coil
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.harbourspace.unsplash.data.model.UnsplashPhoto
import com.harbourspace.unsplash.ui.theme.UnsplashTheme
import com.harbourspace.unsplash.utils.EXTRA_IMAGE


class DetailsActivity : ComponentActivity() {

  private val unsplashViewModel2: UnsplashViewModel2 by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val url = if (intent.hasExtra(EXTRA_IMAGE)) {
      intent.extras?.get(EXTRA_IMAGE)
    } else {
      ""
    }

    val id = if (intent.hasExtra("image_id")) {
      intent.extras?.get("image_id")
    } else {
      ""
    }

    unsplashViewModel2.fetchImagesDetails(id.toString())

    setContent {
      UnsplashTheme {
        val unsplashDetails = unsplashViewModel2.items.observeAsState()
        val details = unsplashDetails.value

        LazyColumn {
          item {
            val painter = rememberAsyncImagePainter(
              model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .build()
            )

            Image(
              modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
              painter = painter,
              contentScale = ContentScale.Crop,
              contentDescription = stringResource(id = R.string.description_image_preview)
            )
          }

          item {
            Row(
              modifier = Modifier.padding(16.dp)
            ) {
              Column(
                modifier = Modifier.weight(1.0f)
              ) {
                AddImageInformation(
                  title = stringResource(id = R.string.details_camera_title),
                  subtitle = details?.exif?.model ?: "",
                )
              }

              Column(
                modifier = Modifier.weight(1.0f)
              ) {
                AddImageInformation(
                  title = stringResource(id = R.string.details_aperture_title),
                  subtitle = details?.exif?.aperture ?: "",
                )
              }
            }
          }

          item {
            Row(
              modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            ) {
              Column(
                modifier = Modifier.weight(1.0f)
              ) {
                AddImageInformation(
                  title = stringResource(id = R.string.details_focal_title),
                  subtitle = details?.exif?.focal_length ?: "",
                )
              }

              Column(
                modifier = Modifier.weight(1.0f)
              ) {
                AddImageInformation(
                  title = stringResource(id = R.string.details_shutter_title),
                  subtitle = details?.exif?.exposure_time ?: "",
                )
              }
            }
          }

          item {
            Row(
              modifier = Modifier.padding(16.dp)
            ) {
              Column(
                modifier = Modifier.weight(1.0f)
              ) {
                AddImageInformation(
                  title = stringResource(id = R.string.details_iso_title),
                  subtitle = details?.exif?.iso.toString() ?: "",
                )
              }

              Column(
                modifier = Modifier.weight(1.0f)
              ) {
                if (details != null) {
                  AddImageInformation(
                    title = stringResource(id = R.string.details_dimensions_title),
                    subtitle = "${details.width?.toString()} x ${details.height?.toString()}}",
                  )
                }
              }
            }
          }

          item {
            Divider(
              modifier = Modifier.padding(start = 16.dp, end = 16.dp),
              thickness = 1.dp,
              color = Color.LightGray
            )
          }

          item {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
              horizontalArrangement = Arrangement.SpaceEvenly
            ) {

              Column(
                horizontalAlignment = Alignment.CenterHorizontally
              ) {
                AddImageInformation(
                  title = stringResource(id = R.string.details_views_title),
                  subtitle = "-",
                )
              }

              Column(
                horizontalAlignment = Alignment.CenterHorizontally
              ) {
                if (details != null) {
                  AddImageInformation(
                    title = stringResource(id = R.string.details_downloads_title),
                    subtitle = details.downloads.toString() ?: ""
                  )
                }
              }

              Column(
                horizontalAlignment = Alignment.CenterHorizontally
              ) {
                if (details != null) {
                  AddImageInformation(
                    title = stringResource(id = R.string.details_likes_title),
                    subtitle = details.likes.toString() ?: ""
                  )
                }
              }
            }
          }
        }
      }
    }
  }
}

@Composable
fun AddImageInformation(
  title: String,
  subtitle: String
) {

  Text(
    text = title,
    fontSize = 17.sp,
    fontWeight = FontWeight.Bold
  )

  Text(
    text = subtitle,
    fontSize = 15.sp
  )
}


