package springcachewithredisdemo.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import springcachewithredisdemo.service.FriendService
import springcachewithredisdemo.model.Friend

@RestController
class FriendController(private val friendService: FriendService) {

    @GetMapping("/friends")
    fun get(): List<Friend> {
        return this.friendService.get()
    }

    @GetMapping("/friends/{id}")
    fun get(@PathVariable id: String): Friend {
        return this.friendService.getById(id)
    }
}