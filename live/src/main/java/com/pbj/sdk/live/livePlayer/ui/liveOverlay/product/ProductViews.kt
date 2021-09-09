package com.pbj.sdk.live.livePlayer.ui.liveOverlay.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.pbj.sdk.R
import com.pbj.sdk.domain.product.model.Product

@Composable
fun ProductListView(
    modifier: Modifier = Modifier,
    productList: List<Product>,
    onClickProduct: (Product) -> Unit
) {
    LazyRow(modifier = modifier) {
        itemsIndexed(productList) { i, product ->
            ProductCard(index = i, product, onClickProduct)
        }
    }
}

@Composable
fun ProductCard(index: Int, product: Product, onClick: (Product) -> Unit) {
    val paddingStart = if (index == 0) 16 else 0
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        modifier = Modifier
            .clickable { onClick(product) }
            .width(310.dp)
            .height(132.dp)
            .padding(start = paddingStart.dp, end = 16.dp)
    ) {
        product.apply {
            Row(modifier = Modifier.padding(16.dp)) {
                Column(Modifier.weight(1f, fill = true)) {
                    title?.let {
                        Text(
                            text = it,
                            fontSize = 16.sp,
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    price?.let {
                        Text(
                            text = "$$it",
                            fontSize = 14.sp,
                            color = Color.Black,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                    Divider(
                        modifier = Modifier.padding(top = 4.dp),
                        color = colorResource(R.color.product_divider)
                    )
                    detail?.let {
                        Text(
                            text = it.trim(),
                            fontSize = 10.sp,
                            color = Color.Black.copy(0.5f),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .fillMaxHeight(),
                    painter = rememberImagePainter(
                        image,
                        builder = {
                            crossfade(true)
                        }),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
fun ProductItemPreview() {
    ProductCard(
        index = 0,
        product = Product(
            "",
            "Handy gloves",
            "999",
            "A very handy pair of gloves for any kind of work",
            null,
            null,
            null
        )
    ) {}
}

@Preview
@Composable
fun ProductItemLongTitlePreview() {
    ProductCard(
        index = 0,
        product = Product(
            "",
            "Handy gloves for any kind of work related to gardening and paper work",
            "999",
            "A very handy pair of gloves for any kind of work",
            null,
            null,
            null
        )
    ) {}
}


@Composable
fun ProductButton(modifier: Modifier = Modifier, productCount: String, onClick: () -> Unit) {
    Row(
        modifier
            .clip(RoundedCornerShape(4.dp))
            .clickable {
                onClick()
            }
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_product),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = productCount,
            color = Color.White,
            fontSize = 14.sp
        )
    }
}

@Preview
@Composable
private fun ChatButtonPreview() {
    ProductButton(productCount = "148") {}
}