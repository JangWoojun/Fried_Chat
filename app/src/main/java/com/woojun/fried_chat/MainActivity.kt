package com.woojun.fried_chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.woojun.fried_chat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            val navController = findNavController(R.id.nav_host_fragment)
            bottomNavigation.run {
                setOnItemSelectedListener { item ->
                    when (item.itemId) {
                        R.id.public_community -> {
                            item.setIcon(R.drawable.select_public_community_icon)
                            bottomNavigation.menu.findItem(R.id.private_community)?.setIcon(R.drawable.private_community_icon)
                            bottomNavigation.menu.findItem(R.id.chat)?.setIcon(R.drawable.chat_icon)
                            bottomNavigation.menu.findItem(R.id.account)?.setIcon(R.drawable.account_icon)
                            navController.navigate(R.id.public_community)
                            true
                        }
                        R.id.private_community -> {
                            item.setIcon(R.drawable.select_private_community_icon)
                            bottomNavigation.menu.findItem(R.id.public_community)?.setIcon(R.drawable.public_community_icon)
                            bottomNavigation.menu.findItem(R.id.chat)?.setIcon(R.drawable.chat_icon)
                            bottomNavigation.menu.findItem(R.id.account)?.setIcon(R.drawable.account_icon)
                            navController.navigate(R.id.private_community)
                            true
                        }
                        R.id.chat -> {
                            item.setIcon(R.drawable.select_chat_icon)
                            bottomNavigation.menu.findItem(R.id.public_community)?.setIcon(R.drawable.public_community_icon)
                            bottomNavigation.menu.findItem(R.id.private_community)?.setIcon(R.drawable.private_community_icon)
                            bottomNavigation.menu.findItem(R.id.account)?.setIcon(R.drawable.account_icon)
                            navController.navigate(R.id.chat)
                            true
                        }
                        R.id.account -> {
                            item.setIcon(R.drawable.select_account_icon)
                            bottomNavigation.menu.findItem(R.id.public_community)?.setIcon(R.drawable.public_community_icon)
                            bottomNavigation.menu.findItem(R.id.private_community)?.setIcon(R.drawable.private_community_icon)
                            bottomNavigation.menu.findItem(R.id.chat)?.setIcon(R.drawable.chat_icon)
                            navController.navigate(R.id.account)
                            true
                        }
                        else -> false
                    }
                }
                selectedItemId = R.id.public_community
            }
        }

    }
}