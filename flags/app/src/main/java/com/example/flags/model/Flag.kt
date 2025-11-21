package com.example.flags.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Flag (
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int
)