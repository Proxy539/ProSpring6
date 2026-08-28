package com.apress.prospring6.sixteen.kotlin.boot

class NotFoundException : RuntimeException {
    constructor(entityName: String) : super("table for $entityName is empty")
    constructor(entityName: String, id: Long) : super("$entityName with id: $id does not exist")
}
