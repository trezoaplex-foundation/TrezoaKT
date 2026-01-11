package com.trezoa.vendor.borshj

@Target(
    AnnotationTarget.FIELD
)
@Retention(AnnotationRetention.RUNTIME)
annotation class FieldOrder(
    val order: Int
)
