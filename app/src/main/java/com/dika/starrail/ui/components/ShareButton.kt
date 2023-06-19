package com.dika.starrail.ui.components
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dika.starrail.ui.theme.StarRailTheme

@Composable
fun ShareButton(text: String, icon: ImageVector, shareContent: String) {
    val context = LocalContext.current

    Button(
        onClick = {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, shareContent)
            context.startActivity(Intent.createChooser(intent, "Share via"))
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.primary),    // Ubah warna button sesuai kebutuhan
        modifier = Modifier.padding(16.dp)
    ) {
        Icon(imageVector = icon, contentDescription = null)
        Text(text = text)
    }
}

@Composable
@Preview(showBackground = true)
fun OrderButtonPreview() {
    StarRailTheme() {
        ShareButton(
            text = "Share",
            icon = androidx.compose.material.icons.Icons.Default.Share,
            shareContent = "StarRail Guide"
        )
    }
}