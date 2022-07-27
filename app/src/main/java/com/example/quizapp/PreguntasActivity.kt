package com.example.quizapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_preguntas.*


class PreguntasActivity : AppCompatActivity() {

    private var Name:String?=null
    private var score:Int=0

    private var currentPosition:Int=1
    private var questionList:ArrayList<QuestionData> ? = null
    private var selectedOption:Int=0

    val fd by lazy {
        assets.openFd(cancionActual)
    }

    val mp by lazy {
        val m = MediaPlayer()
        m.setDataSource(
            fd.fileDescriptor,
            fd.startOffset,
            fd.length
        )
        fd.close()
        m.prepare()
        m
    }

    val nombreCancion by lazy {
        findViewById<TextView>(R.id.nombreCanción)
    }

    val canciones by lazy {
        val nombreFicheros = assets.list("")?.toList() ?: listOf()
        nombreFicheros.filter { it.contains(".mp3") }
    }

    var cancionActualIndex = 0
        set(value){
            var v = if(value==-1){
                canciones.size-1
            }
            else{
                value%canciones.size
            }
            field = v
            cancionActual = canciones[v]
        }

    val controllers by lazy{
        listOf(R.id.submit).map{
            findViewById<MaterialButton>(it)
        }
    }

    object ci{
        val submit = 3
    }



    lateinit var cancionActual:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preguntas)

        controllers[ci.submit].setOnClickListener(this::nextSong)

        Name=intent.getStringExtra(setData.name)

        questionList=setData.getQuestion()

        cancionActual= canciones[cancionActualIndex]
        nombreCancion.text= cancionActual

        setQuestion()
        mp.start()


        opt_1.setOnClickListener{
            selectedOptionStyle(opt_1, 1)
        }
        opt_2.setOnClickListener{
            selectedOptionStyle(opt_2, 2)
        }
        opt_3.setOnClickListener{
            selectedOptionStyle(opt_3, 3)
        }
        opt_4.setOnClickListener{
            selectedOptionStyle(opt_4, 4)
        }

        submit.setOnClickListener {
            if(selectedOption!=0)
            {
                val question=questionList!![currentPosition-1]
                if(selectedOption!=question.correct_asn)
                {
                    setColor(selectedOption,R.drawable.incorrect_question_option)

                }else{
                    score++;
                }
                setColor(question.correct_asn, R.drawable.correct_question_option)
                if(currentPosition==questionList!!.size)
                submit.text="FINISH"
                else
                    submit.text="Vamo a la siguiente compita"

            }else
            {
                currentPosition++
                when{
                    currentPosition<=questionList!!.size->{
                        setQuestion()
                    }
                    else->{
                        var intent= Intent(this,Result::class.java)
                        intent.putExtra(setData.name,Name.toString())
                        intent.putExtra(setData.score,score.toString())
                        intent.putExtra("total size",questionList!!.size.toString())

                        startActivity(intent)
                        finish()
                    }

                }
            }
            selectedOption=0
        }

    }

    fun setColor(opt:Int, color:Int){
        when(opt){
            1->{
                opt_1.background=ContextCompat.getDrawable(this,color)
            }
            2->{
                opt_2.background=ContextCompat.getDrawable(this,color)
            }
            3->{
                opt_3.background=ContextCompat.getDrawable(this,color)
            }
            4->{
                opt_4.background=ContextCompat.getDrawable(this,color)
            }
        }
    }

    fun setQuestion(){
        val question = questionList!![currentPosition-1]
        setOptionStyle()


        progress_bar.progress=currentPosition
        progress_bar.max=questionList!!.size
        progress_text.text="${currentPosition}"+"/"+"${questionList!!.size}"

        question_text.text=question.question
        nombreCancion.visibility = View.VISIBLE
        opt_1.text=question.option_one
        opt_2.text=question.option_two
        opt_3.text=question.option_three
        opt_4.text=question.option_four
    }

    fun setOptionStyle(){
        var optionList:ArrayList<TextView> = arrayListOf()
        optionList.add(0,opt_1)
        optionList.add(1,opt_2)
        optionList.add(2,opt_3)
        optionList.add(3,opt_4)

        for(op in optionList)
        {
            op.setTextColor(Color.parseColor("#736F6F"))
            op.background=ContextCompat.getDrawable(this, R.drawable.question_option)
            op.typeface= Typeface.DEFAULT
        }
    }

    fun selectedOptionStyle(view: TextView, opt:Int){

        setOptionStyle()
        selectedOption=opt
        view.background=ContextCompat.getDrawable(this,R.drawable.selected_question_option)
        view.typeface= Typeface.DEFAULT_BOLD
        view.setTextColor(Color.parseColor("#000000"))

    }


    fun nextSong(v:View){
        cancionActualIndex++
        refreshSong()
    }

    fun refreshSong(){
        mp.reset()
        val fd = assets.openFd(cancionActual)
        mp.setDataSource(
            fd.fileDescriptor,
            fd.startOffset,
            fd.length
        )
        mp.prepare()
        mp.start()
        nombreCancion.text = cancionActual
    }
}