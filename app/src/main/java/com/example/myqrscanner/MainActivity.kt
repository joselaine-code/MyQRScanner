package com.example.myqrscanner

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.URLUtil
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var code by remember {
                mutableStateOf("")
            }
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Header(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                QrCodeReader(
                    { result ->
                        code = result
                    },
                    modifier = Modifier.weight(1f)
                )
                if (URLUtil.isValidUrl(code)) {
                    ResultLink(code)
                } else {
                    ResultText(code)
                }
            }
        }
    }

    @Composable
    private fun Header(modifier: Modifier = Modifier) {
        Image(
            painter = painterResource(id = R.drawable.ic_qr_code),
            contentDescription = "QR Code",
            modifier = modifier.size(50.dp),
            colorFilter = ColorFilter.tint(Color.DarkGray)
        )
    }

    @Composable
    private fun ResultText(code: String, modifier: Modifier = Modifier) {
        Text(
            text = code,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .fillMaxWidth()
                .padding(32.dp)
        )
    }

    @Composable
    fun ResultLink(code: String, modifier: Modifier = Modifier) {
        val annotatedLinkString = buildAnnotatedString {
            append(code)
            addStyle(
                style = SpanStyle(
                    color = Color(0xff64B5F6),
                    textDecoration = TextDecoration.Underline,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                ), start = 0, end = code.length
            )
        }
        ClickableText(
            text = annotatedLinkString,
            onClick = { openUrl(code) },
            modifier = modifier
                .fillMaxWidth()
                .padding(32.dp)
        )
    }

    private fun openUrl(url: String) {
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(webIntent)
    }
}