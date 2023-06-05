package springcachewithredisdemo.service

import org.springframework.stereotype.Service
import springcachewithredisdemo.repository.FriendRepository
import springcachewithredisdemo.model.Friend


@Service
class FriendService(private val friendRepository: FriendRepository) {

    fun get(): List<Friend> {
        return this.friendRepository.findAll()
    }

    fun getById(id: String): Friend {
        return this.friendRepository.getById(id)
    }
}