package com.example.quizapp

object setData {

    const val name:String="name"
    const val score:String="score"

    fun getQuestion():ArrayList<QuestionData>{
        var que:ArrayList<QuestionData> = arrayListOf()

        var question1 = QuestionData(
            "Cual es mi segundo apellido?",
            1,
            "badbunny_pfknr",
            "Contreras",
            "ChupaPoto",
            "MasterChupaPoto",
            "Barrera",
            4

        )
        var question2 = QuestionData(
            "Como se llama Rauw Alejandro?",
            2,
            "agua",
            "Benito",
            "Rauw Alejandro po",
            "Papasito",
            "Gucci",
            2

        )
        var question3 = QuestionData(
            "Nirvana por siempre?",
            3,
            "careless_whisper",
            "Nelson",
            "ChupaPoto",
            "Obvio",
            "Por siempre y para siempre e.e",
            4

        )
        var question4 = QuestionData(
            "Sale sus traguitos este viernes?",
            4,
            "ella_y_yo",
            "De una compita, yo compro las birras",
            "Pakepo",
            "Me enfermaré ese dia",
            "Nop",
            1

        )
        var question5 = QuestionData(
            "En que me gasto la plata?",
            4,
            "maluma_borrocasseste",
            "En weas valiosas",
            "En puras weas pa tomar, borracho",
            "Lo dono",
            "Compro weed",
            2

        )

        que.add(question1)
        que.add(question2)
        que.add(question3)
        que.add(question4)
        que.add(question5)

        return que
    }
}