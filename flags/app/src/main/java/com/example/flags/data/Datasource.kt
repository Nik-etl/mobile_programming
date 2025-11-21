package com.example.flags.data

import com.example.flags.R
import com.example.flags.model.Flag

class Datasource {
    fun loadflags(): List<Flag> {
        return listOf<Flag>(
            Flag(R.string.america, R.drawable.americanflag),
            Flag(R.string.finland, R.drawable.finnishflag),
            Flag(R.string.switzerland, R.drawable.swissflag),
            Flag(R.string.nepal, R.drawable.nepaliflag)
        )
    }
}