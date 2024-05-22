package com.example.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class SecActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sec)
        //variables and components binding

        val btn = findViewById<Button>(R.id.btn2)
        val gridView = findViewById<GridView>(R.id.gridView)
        val img1 = findViewById<ImageView>(R.id.img_player1)
        val img2 = findViewById<ImageView>(R.id.img_player2)
        val tv1 = findViewById<TextView>(R.id.tv_player1)
        val tv2 = findViewById<TextView>(R.id.tv_player2)
        var player = 0
        val names = resources.getStringArray(R.array.name_list)
        val agent = ArrayList<Agent>()
        val array_img = resources.obtainTypedArray(R.array.image_list)
        var player1: Agent? = null
        var player2: Agent? = null
        for (i in 0 until array_img.length()) {
            val photo = array_img.getResourceId(i, 0) //fruit image Id
            val name = names[i] //fruit name
            agent.add(Agent(photo, name)) //add fruit info.
        }
        array_img.recycle()
        gridView.numColumns = 3
        gridView.adapter = MyAdapter(this, agent, R.layout.adapter_vertical)

        gridView.setOnItemClickListener { parent, view, position, id ->
            if(player==0){
                if(tv2.text != null && tv2.text == agent[position].name){
                    Toast.makeText(this,"Please select different agent",Toast.LENGTH_LONG).show()
                }
                else{
                    player1 = agent[position]
                    img1.setImageResource(agent[position].photo)
                    tv1.text = agent[position].name
                    player=1
                }

            }
            else{
                if(tv1.text != null && tv1.text == agent[position].name){
                    Toast.makeText(this,"Please pick different agent",Toast.LENGTH_LONG).show()
                }
                else{
                    player2 = agent[position]
                    img2.setImageResource(agent[position].photo)
                    tv2.text = agent[position].name
                    player=0
                }

            }

        }


        btn.setOnClickListener {
            if (tv1.length() < 1 || tv2.length() < 1)
                Toast.makeText(this, "Need to pick two agents", Toast.LENGTH_SHORT).show()
            else {
                SelectedAgent.player1 = player1
                SelectedAgent.player2 = player2
                val intent = Intent(this, ThirdActivity::class.java)
                startActivity(intent)
            }
        }
    }
}

data class Agent(
    val photo: Int,
    val name: String
)

object SelectedAgent{
    var player1: Agent? = null
    var player2: Agent? = null
}