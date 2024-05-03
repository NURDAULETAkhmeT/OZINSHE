package com.example.ozinshe

interface NavigationHostProvider {
    fun hideBottomNavigationBar(visible: Boolean)
    fun fullScreenMode(visible: Boolean)
}

//override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is NavigationHostProvider) {
//            navigationHostProvider = context
//        } else {
//            throw ClassCastException("$context must implement NavigationHostProvider")
//        }
//    }



//navigationHostProvider?.hideBottomNavigationBar(false)
//        navigationHostProvider?.fullScreenMode(true)