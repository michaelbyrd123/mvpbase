/*
 * Copyright (c) 2018 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.creaturemon.view.creature

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.raywenderlich.android.creaturemon.R
import com.raywenderlich.android.creaturemon.model.*
import com.raywenderlich.android.creaturemon.presenter.CreatureDetailContract
import com.raywenderlich.android.creaturemon.presenter.CreatureDetailPresenter
import com.raywenderlich.android.creaturemon.view.avatars.AvatarAdapter
import kotlinx.android.synthetic.main.activity_creature_detail.*


class CreatureDetailActivity : AppCompatActivity(), AvatarAdapter.AvatarListener, CreatureDetailContract.View {

    private val presenter = CreatureDetailPresenter()
    private var creature : Creature = Creature()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creature_detail)

        presenter.setView(this)

        configureClickListeners()
        configureUI()

        creature = getIntent().getExtras().getSerializable("creature") as Creature
        setupCreature(creature)
    }

    private fun configureUI() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.creature_detail)
    }


    override fun avatarClicked(avatar: Avatar) {
        hideTapLabel()
    }

    private fun setupCreature(creature: Creature) {
        presenter.setCreature(creature)
    }

    private fun hideTapLabel() {
        tapLabel.visibility = View.INVISIBLE
    }

    val EDIT_CREATURE_REQUEST = 1
    private fun configureClickListeners() {
        saveButton.setOnClickListener {
            val intent = Intent(this, EditCreatureActivity::class.java)
            intent.putExtra("creature", creature)
            startActivityForResult(intent, EDIT_CREATURE_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == EDIT_CREATURE_REQUEST && data != null){
            creature = data.getExtras().getSerializable("creature") as Creature
            setupCreature(creature)
        }
    }

    override fun setCreature(creature: Creature) {
        this.creature = creature
        nameEditText.text = creature.name
        intelligence.text = creature.attributes.intelligence.toString()
        strength.text = creature.attributes.strength.toString()
        endurance.text= creature.attributes.endurance.toString()
        hideTapLabel()
    }

    override fun showHitPoints(hitPoints: String) {
        this.hitPoints.text = hitPoints
    }

    override fun showAvatarDrawable(resourceId: Int) {
        avatarImageView.setImageResource(resourceId)
    }
}
