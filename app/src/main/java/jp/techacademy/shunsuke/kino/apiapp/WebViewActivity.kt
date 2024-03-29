package jp.techacademy.shunsuke.kino.apiapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import jp.techacademy.shunsuke.kino.apiapp.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding

    private var isFavorite = false // お気に入りの状態を管理

    private lateinit var starImageView: ImageView // starImageView の宣言

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(KEY_ID)
        val imageUrl = intent.getStringExtra(KEY_IMAGEURL)
        val name = intent.getStringExtra(KEY_NAME)
        val address = intent.getStringExtra(KEY_ADDRESS)
        val url = intent.getStringExtra(KEY_URL)
        if (id != null && imageUrl != null && name != null && address != null && url != null) {

            binding.webView.loadUrl(url)


            // ★マークの初期表示を設定
            isFavorite = FavoriteShop.findBy(id) != null
            starImageView = findViewById(R.id.couponurlimageView)
            updateStarIcon()


            starImageView.setOnClickListener {
                isFavorite = !isFavorite
                if (isFavorite) {
                    // お気に入りに追加
                    val favoriteShop = FavoriteShop().apply {
                        this.id = id
                        this.imageUrl = imageUrl
                        this.name = name
                        this.address = address
                        this.url = url
                    }
                    FavoriteShop.insert(favoriteShop)


                } else {
                    // お気に入りから削除
                    // お気に入りから削除
                    AlertDialog.Builder(this)
                        .setTitle(R.string.delete_favorite_dialog_title)
                        .setMessage(R.string.delete_favorite_dialog_message)
                        .setPositiveButton(android.R.string.ok) { _, _ ->
                            FavoriteShop.delete(id)
                            // お気に入りから削除した後の処理を記述する場合はここに追加
                            isFavorite = false // ★ マークを更新するために isFavorite を更新
                            updateStarIcon()
                        }
                        .setNegativeButton(android.R.string.cancel) { _, _ -> }
                        .create()
                        .show()

                    }

                }

                updateStarIcon()
            }

    }

    private fun updateStarIcon() {
        // ★マークの表示を更新
        starImageView.setImageResource(if (isFavorite) R.drawable.ic_star else R.drawable.ic_star_border)
    }



    companion object {
        private const val KEY_ID = "key_id"
        private const val KEY_IMAGEURL = "key_imageUrl"
        private const val KEY_NAME = "key_name"
        private const val KEY_ADDRESS = "key_address"
        private const val KEY_URL = "key_url"
        fun start(activity: Activity, id: String, name: String, imageUrl: String, address: String, url: String) {
            activity.startActivity(
                Intent(activity, WebViewActivity::class.java).apply {
                    putExtra(KEY_ID, id)
                    putExtra(KEY_IMAGEURL, name)
                    putExtra(KEY_NAME, imageUrl)
                    putExtra(KEY_ADDRESS, address)
                    putExtra(KEY_URL, url)
                }
            )
        }
    }

}

    /*
        private fun updateStarIcon() {
            val drawableRes = if (isFavorite) R.drawable.ic_star else R.drawable.ic_star_border
            starImageView.setImageResource(drawableRes)
        }
    }
*/

/*
        //お気に入りボタンを押すために追記
       // setContentView(R.layout.activity_web_view)
        // 星の処理
        val isFavorite = FavoriteShop.findBy(shop.id) != null

        // 星のアイコンを設定
        binding.couponurlimageView.setImageResource(if (isFavorite) R.drawable.ic_star else R.drawable.ic_star_border)

        binding.couponurlimageView.setOnClickListener {
            if (isFavorite) {
                // お気に入りから削除
                FavoriteShop.delete(shop.id)
            } else {
                // お気に入りに追加
                FavoriteShop.insert(FavoriteShop().apply {
                    id = shop.id
                    name = shop.name
                    imageUrl = shop.logoImage
                    url = shop.couponUrls.sp.ifEmpty { shop.couponUrls.pc }
                })
            }

            // ★マークの表示を更新
            binding.couponurlimageView.setImageResource(if (isFavorite) R.drawable.ic_star_border else R.drawable.ic_star)
        }
    }

 */
/*
    companion object {
        private const val KEY_ID = "key_id"
        private const val KEY_URL = "key_url"
        fun start(activity: Activity, id: String, url: String) {
            activity.startActivity(
                Intent(activity, WebViewActivity::class.java).apply {
                    putExtra(KEY_ID, id)
                    putExtra(KEY_URL, url)
                }
            )
        }
    }


    //ここからは要検証
        /**
     * Favoriteに追加するときのメソッド(Fragment -> Activity へ通知する
     * お気に入り追加
  */
    fun insert(favoriteShop: FavoriteShop) {
        // Realmデータベースとの接続を開く
        val config = RealmConfiguration.create(schema = setOf(FavoriteShop::class))
        val realm = Realm.open(config)

        // 登録処理
        realm.writeBlocking {
            copyToRealm(favoriteShop)
        }

        // Realmデータベースとの接続を閉じる
        realm.close()
    }

    /**
     * idでお気に入りから削除する
     */
    fun delete(id: String) {
        // Realmデータベースとの接続を開く
        val config = RealmConfiguration.create(schema = setOf(FavoriteShop::class))
        val realm = Realm.open(config)

        // 削除処理
        realm.writeBlocking {
            val favoriteShops = query<FavoriteShop>("id=='$id'").find()
            favoriteShops.forEach {
                delete(it)
            }
        }

        // Realmデータベースとの接続を閉じる
        realm.close()
    }
   }
  }
 }
}

 */