package com.github.kitakkun.mirrorcomment.model

import org.openqa.selenium.By
import org.openqa.selenium.WebElement

sealed interface MirrativComment {
    val id: Int
    val username: String
    val comment: String
    val avatarUrl: String

    companion object {
        fun convert(comment: WebElement): MirrativComment? {
            try {
                val commentId = comment.getAttribute("id").replace("comment-", "").toInt()

                // ギフトコメントは名前取得が特殊なのでこのタイミングで取得しておく
                val isGiftComment = comment.findElements(By.cssSelector("._commentGift_1m6ac_3")).isNotEmpty()

                // ユーザーネームを取得
                val username = if(isGiftComment) {
                    comment.findElement(By.cssSelector("._commentGift_1m6ac_3 a span:first-child")).text.dropLast(1)
                }else{
                    comment.findElement(By.cssSelector("._commentUserName_6t3be_32")).text
                }

                // コメントテキストを取得
                val commentText = comment.findElement(By.cssSelector("._commentText_6t3be_37")).text

                // アバター画像のURLを取得
                val userAvatarElement = comment.findElement(By.cssSelector("._commentAvatar_6t3be_23"))
                val style = userAvatarElement.getAttribute("style")
                val matchResult = Regex("""url\("(.*)"\)""").find(style)
                val avatarUrl = matchResult?.groups?.get(1)?.value ?: ""

                // 入室コメントを判定
                val isEnterComment = commentText.endsWith("が入室しました") || commentText.endsWith("joined")

                // ボットのコメントを判定
                val isBotComment = username == "Mirrativ bot"

                return if (isBotComment) {
                    println("Bot comment: $commentText")
                    Bot(commentId, username, commentText, avatarUrl)
                } else if (isGiftComment) {
                    Gift(
                        id = commentId,
                        username = username,
                        comment = commentText,
                        avatarUrl = avatarUrl,
                    )
                } else if (isEnterComment) {
                    println("Enter comment: $commentText")
                    Enter(
                        id = commentId,
                        username = username,
                        comment = commentText,
                        avatarUrl = avatarUrl
                    )
                } else {
                    Normal(
                        id = commentId,
                        username = username,
                        comment = commentText,
                        avatarUrl = avatarUrl
                    )
                }
            } catch (e: Throwable) {
                println("Failed to convert comment: $e")
                return null
            }
        }
    }

    data class Normal(
        override val id: Int,
        override val username: String,
        override val comment: String,
        override val avatarUrl: String,
    ) : MirrativComment

    data class Gift(
        override val id: Int,
        override val username: String,
        override val comment: String,
        override val avatarUrl: String,
    ) : MirrativComment

    data class Enter(
        override val id: Int,
        override val username: String,
        override val comment: String,
        override val avatarUrl: String,
    ) : MirrativComment

    data class Bot(
        override val id: Int,
        override val username: String,
        override val comment: String,
        override val avatarUrl: String,
    ) : MirrativComment
}
