package com.raywenderlich.android.creaturemon.view.allcreatures

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.android.creaturemon.R
import com.raywenderlich.android.creaturemon.model.Creature
import com.raywenderlich.android.creaturemon.presenter.AllCreaturesContract
import com.raywenderlich.android.creaturemon.presenter.AllCreaturesPresenter
import com.raywenderlich.android.creaturemon.view.creature.CreatureActivity
import com.raywenderlich.android.creaturemon.view.creature.CreatureDetailActivity
import kotlinx.android.synthetic.main.activity_all_creatures.*
import kotlinx.android.synthetic.main.content_all_creatures.*

class AllCreaturesActivity : AppCompatActivity(), AllCreaturesContract.View {

  private val adapter = CreatureAdapter(mutableListOf(), { creature : Creature -> creatureItemClicked(creature) })

  private val presenter = AllCreaturesPresenter()

  private fun creatureItemClicked(creature : Creature) {
    val intent = Intent(this, CreatureDetailActivity::class.java)
    intent.putExtra("creature", creature)
    Toast.makeText(this, "Creature Clicked: ${creature.name}", Toast.LENGTH_LONG).show()
    startActivity(intent)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_all_creatures)
    setSupportActionBar(toolbar)

    val mAdapterObserver = object : RecyclerView.AdapterDataObserver(){
      override fun onChanged() {
        super.onChanged()
      }

      override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
        super.onItemRangeRemoved(positionStart, itemCount)
      }

      override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
        super.onItemRangeMoved(fromPosition, toPosition, itemCount)
      }

      override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        super.onItemRangeInserted(positionStart, itemCount)
        val creature = adapter.getItem(positionStart)
        presenter.saveCreature(creature)
      }

      override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
        super.onItemRangeChanged(positionStart, itemCount)
      }

      override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
        super.onItemRangeChanged(positionStart, itemCount, payload)
      }
    }


    adapter.registerAdapterDataObserver(mAdapterObserver)



    presenter.setView(this)

    presenter.getAllCreatures().observe(this, Observer { creatures ->
      creatures?.let{
        adapter.updateCreatures(creatures)
      }
    })

    creaturesRecyclerView.layoutManager = LinearLayoutManager(this)
    creaturesRecyclerView.adapter = adapter

    val swipeBackground: ColorDrawable = ColorDrawable(Color.parseColor("#FF0000"))
    val deleteIcon = ContextCompat.getDrawable(this, R.drawable.ic_delete)!!

    fab.setOnClickListener {
      startActivity(Intent(this, CreatureActivity::class.java))
    }

    val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
      override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
      }

      override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val creature = adapter.getItem(viewHolder.adapterPosition)
        adapter.removeItem(viewHolder)
        presenter.deleteCreature(creature)
      }

      override fun onChildDraw(
              c: Canvas,
              recyclerView: RecyclerView,
              viewHolder: RecyclerView.ViewHolder,
              dX: Float,
              dY: Float,
              actionState: Int,
              isCurrentlyActive: Boolean) {
        val itemView = viewHolder.itemView


        val iconMargin = (itemView.height - deleteIcon.intrinsicHeight) / 2

        if(dX > 0){
            swipeBackground.setBounds(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
            deleteIcon.setBounds(itemView.left + iconMargin,
                    itemView.top + iconMargin,
                    itemView.left + iconMargin + deleteIcon.intrinsicWidth,
                    itemView.bottom - iconMargin)
        }else{
            swipeBackground.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
            deleteIcon.setBounds(itemView.right - iconMargin - deleteIcon.intrinsicWidth,
                  itemView.top + iconMargin,
                  itemView.right - iconMargin,
                  itemView.bottom - iconMargin)
        }

        swipeBackground.draw(c)

        c.save()
        if(dX > 0){
          c.clipRect(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
        }else{
          c.clipRect(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
        }

        deleteIcon.draw(c)
        c.restore()

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
      }
    }

    val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
    itemTouchHelper.attachToRecyclerView(creaturesRecyclerView)

  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_clear_all -> {
        presenter.clearAllCreatures()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  override fun showCreaturesCleared() {
    Toast.makeText(this, getString(R.string.creatures_cleared), Toast.LENGTH_SHORT).show()
  }
  override fun showCreaturesSaved() {
    Toast.makeText(this, getString(R.string.creatures_saved), Toast.LENGTH_SHORT).show()
  }
  override fun showCreaturesDeleted() {
    Toast.makeText(this, getString(R.string.creatures_deleted), Toast.LENGTH_SHORT).show()
  }
}
