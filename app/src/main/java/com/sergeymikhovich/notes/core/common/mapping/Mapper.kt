package com.sergeymikhovich.notes.core.common.mapping

fun interface Mapper<From, To> : (From) -> To