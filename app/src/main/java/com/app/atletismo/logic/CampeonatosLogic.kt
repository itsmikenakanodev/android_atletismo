package com.app.atletismo.logic

import com.app.atletismo.logic.data.Campeonatos

class CampeonatosLogic {

    fun getAllCampeonatos(): ArrayList<Campeonatos> {
        var itemList = arrayListOf<Campeonatos>(    Campeonatos(1, "Campeonato Nacional 2024"),
            Campeonatos(2, "Campeonato Regional 2023"),
            Campeonatos(3, "Campeonato Internacional 2024"),
            Campeonatos(4, "Campeonato Sub-20 2023"),
            Campeonatos(5, "Campeonato Juvenil 2024")
        )

        return itemList
    }
    
}