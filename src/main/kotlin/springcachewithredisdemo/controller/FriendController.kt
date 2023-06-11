package springcachewithredisdemo.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import springcachewithredisdemo.service.FriendService
import springcachewithredisdemo.model.Friend

@RestController
class FriendController(private val friendService: FriendService) {

    @GetMapping("/friends")
    fun get(): ResponseEntity<List<Friend>> {
        val friends = this.friendService.get()
        return ResponseEntity.ok(friends)
    }

    @GetMapping("/friends/{id}")
    fun get(@PathVariable id: String): Friend {
        return this.friendService.getById(id)
    }
}