package com.sergeymikhovich.notes.common.mapping

fun interface Mapper<From, To> : (From) -> To