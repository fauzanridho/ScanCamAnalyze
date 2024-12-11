package com.capstone.scancamanalyze.data.api

import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("files")
	val files: List<FilesItem?>? = null
)

data class FilesItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)
