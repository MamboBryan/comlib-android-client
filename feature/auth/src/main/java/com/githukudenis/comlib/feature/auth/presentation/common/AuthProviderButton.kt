package com.githukudenis.comlib.feature.auth.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.githukudenis.comlib.core.designsystem.ui.theme.LocalDimens
import com.githukudenis.comlib.feature.auth.R

@Composable
fun AuthProviderButton(
    modifier: Modifier = Modifier,
    icon: Int,
    onClick: () -> Unit) {
    Box(
        modifier = modifier
            .size(48.dp)
            .clip(
                RoundedCornerShape(LocalDimens.current.large)
            )
            .background(
                color = MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.06f
                ),
            )
            .clickable(onClick = {
                onClick()
            })
            .padding(LocalDimens.current.medium),
        contentAlignment = Alignment.Center

    ) {
        Image(
            modifier = Modifier.size(40.dp),
            painter = painterResource(id = icon),
            contentDescription = null
        )

    }
}

@Preview
@Composable
private fun AuthProviderButtonPrev() {
    AuthProviderButton(icon = R.drawable.comliblogo) {
        
    }
}