package springcachewithredisdemo.repository

import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import springcachewithredisdemo.model.Friend
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


@Component
class FriendRepository {

    companion object {
        private const val SALT = "[READ_IT_FROM_APP_CONFIGURATION]"
    }

    private val friends = listOf(
        Friend("11122233344", "Joao", 20),
        Friend("22233344455", "Mara", 50),
        Friend("33344455566", "Ana", 35)
    )

    @Cacheable(value = ["friendsCache"], sync = true)
    fun findAll(): List<Friend> {
        Thread.sleep(1000)
        return friends.map { Friend(computeFrontEndIdentifier(it.id), it.name, it.age) }
    }

    @Cacheable(value = ["friendsCache"], key = "#id", sync = true)
    fun getById(id: String): Friend {
        Thread.sleep(1000)
        return friends.first { computeFrontEndIdentifier(it.id) == id }
    }

    @Throws(NoSuchAlgorithmException::class, UnsupportedEncodingException::class)
    fun computeFrontEndIdentifier(realItemBackendIdentifier: String): String {
        val tmp: String = SALT + realItemBackendIdentifier
        val digester: MessageDigest = MessageDigest.getInstance("sha1")
        val hash: ByteArray = digester.digest(tmp.toByteArray(charset("utf-8")))
        return hash.joinToString("") {
            java.lang.Byte.toUnsignedInt(it).toString(radix = 16).padStart(2, '0')
        }
    }
}