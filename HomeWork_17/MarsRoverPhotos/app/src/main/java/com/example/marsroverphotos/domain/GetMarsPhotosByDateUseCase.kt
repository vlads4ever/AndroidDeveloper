package com.example.marsroverphotos.domain

import com.example.marsroverphotos.data.Repository
import com.example.marsroverphotos.data.Results
import retrofit2.Response
import javax.inject.Inject

class GetMarsPhotosByDateUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun execute(date: String): Response<Results>? {
        return repository.getMarsPhotos.getPicturesByDate(date)
    }
}