package com.example.proyecto

data class ApiResponse(
    val pagination: Pagination,
    val data: List<Anime>
)

data class Pagination(
    val last_visible_page: Int,
    val has_next_page: Boolean,
    val current_page: Int,
    val items: Items
)

data class Items(
    val count: Int,
    val total: Int,
    val per_page: Int
)

data class Anime(
    val mal_id: Int,
    val title: String,
    val images: Images
    // Add other fields as needed
)

data class Images(
    val jpg: JPG
    // Add other fields as needed
)

data class JPG(
    val image_url: String,
)