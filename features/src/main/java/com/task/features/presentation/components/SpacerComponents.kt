package com.task.features.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.task.features.R

@Composable
fun TinyVerticalSpacer(){
    Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.element_spacing_tiny)))
}

@Composable
fun SmallVerticalSpacer(){
    Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.element_spacing_small)))

}

@Composable
fun MediumVerticalSpacer(){
    Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.element_spacing_medium)))

}

@Composable
fun LargeVerticalSpacer(){
    Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.element_spacing_large)))

}

@Composable
fun XLargeVerticalSpacer(){
    Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.element_spacing_xlarge)))

}

@Composable
fun TinyHorizontalSpacer(){
    Spacer(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.element_spacing_tiny)))
}

@Composable
fun SmallHorizontalSpacer(){
    Spacer(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.element_spacing_small)))

}

@Composable
fun MediumHorizontalSpacer(){
    Spacer(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.element_spacing_medium)))

}

@Composable
fun LargeHorizontalSpacer(){
    Spacer(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.element_spacing_large)))

}

@Composable
fun XLargeHorizontalSpacer(){
    Spacer(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.element_spacing_xlarge)))

}