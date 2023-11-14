package com.architects.pokearch.ui.components.image

import androidx.compose.foundation.Image
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.core.graphics.drawable.toBitmap
import coil.compose.AsyncImagePainter
import com.architects.pokearch.R
import com.architects.pokearch.ui.components.progressIndicators.ArchLoadingIndicator

@Composable
fun ArchAsyncImage(
    asyncImagePainter: AsyncImagePainter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DrawScope.DefaultFilterQuality,
) {
    when (val state = asyncImagePainter.state) {
        AsyncImagePainter.State.Empty -> {}
        is AsyncImagePainter.State.Error ->
            Icon(
                painter = painterResource(id = R.drawable.broken_image_icon),
                contentDescription = stringResource(id = R.string.error_async_image_painter)
            )
        is AsyncImagePainter.State.Loading -> ArchLoadingIndicator()
        is AsyncImagePainter.State.Success -> {
            Image(
                bitmap = state.result.drawable.toBitmap().asImageBitmap(),
                contentDescription = contentDescription,
                modifier = modifier,
                alignment = alignment,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = colorFilter,
                filterQuality = filterQuality
            )
        }
    }
}
