package com.example.ozinshe.Main

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.ozinshe.NavigationHostProvider
import com.example.ozinshe.R
import com.gyf.immersionbar.ImmersionBar
import com.example.ozinshe.databinding.ActivityMainBinding


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), NavigationHostProvider {
    private lateinit var binding: ActivityMainBinding

    override fun hideBottomNavigationBar(visible: Boolean) {
        // Логика для скрытия/показа нижней панели навигации
        if (visible) {
            // Показать нижнюю панель навигации
            binding.bottomNavigation.visibility = View.VISIBLE
        } else {
            // Скрыть нижнюю панель навигации
            binding.bottomNavigation.visibility = View.GONE
        }
    }
    override fun fullScreenMode(visible: Boolean) {
        // Логика для установки/снятия полноэкранного режима
        if (visible) {
            // Включить полноэкранный режим
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        } else {
            // Отключить полноэкранный режим
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //код для скрытие StatusBar
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)


        ImmersionBar.with(this)
            .statusBarColor(R.color.white) // Замените на ваш цвет
            .statusBarDarkFont(true) // Если true, текст на статус-баре будет темным
            .init()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        val navController = navHostFragment?.navController


        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.homeFragment -> {
                    navController?.navigate(R.id.homeFragment2)
                    true
                }
                R.id.searchFragment -> {
                    navController?.navigate(R.id.searchFragment2)
                    true
                }
                R.id.favoriteFragment -> {
                    navController?.navigate(R.id.favoriteFragment2)
                    true
                }
                R.id.profileFragment -> {
                    navController?.navigate(R.id.profileFragment2)
                    true
                }
                else -> false
            }
        }
    }
}
