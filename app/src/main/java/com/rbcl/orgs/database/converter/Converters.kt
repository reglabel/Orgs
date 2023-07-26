package com.rbcl.orgs.database.converter

import androidx.room.TypeConverter
import java.math.BigDecimal

class Converters {
    @TypeConverter
    fun fromDoubleToBigDecimal(value: Double?): BigDecimal{
        return value?.let { BigDecimal(value.toString())} ?: BigDecimal.ZERO
    }

    @TypeConverter
    fun fromBigDecimaltoDouble(value: BigDecimal?): Double? {
        return value?.let { value.toDouble() }
    }
}