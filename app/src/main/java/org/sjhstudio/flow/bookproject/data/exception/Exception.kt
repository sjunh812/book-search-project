package org.sjhstudio.flow.bookproject.data.exception

class NetworkErrorException(message: String? = ""): Exception(message)

class ClientErrorException(message: String? = ""): Exception(message)

class EmptyBodyException(message: String? = ""): Exception(message)