package com.abproject.athsample.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abproject.athsample.R
import com.abproject.athsample.data.dataclass.User
import com.abproject.athsample.data.dataclass.UserInformation
import com.abproject.athsample.databinding.ActivityMainBinding
import com.abproject.athsample.view.auth.AuthActivity
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by Abolfazl on 5/16/21
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupFullName()

        mainViewModel.users.observe(this) { response ->
            setupRecyclerView(response)
        }

        binding.logOutButtonMain.setOnClickListener {
            setupLogoutSection()
        }
    }

    private fun setupRecyclerView(users: List<User>) {
        mainAdapter = MainAdapter()
        binding.recyclerViewMain.layoutManager =
            LinearLayoutManager(
                this,
                RecyclerView.VERTICAL,
                false
            )
        mainAdapter.userDataChange(users)
        binding.recyclerViewMain.adapter = mainAdapter
    }

    private fun setupLogoutSection() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.logoutDialogTitle))
            .setMessage(getString(R.string.logoutDialogMessage))
            .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                startActivity(Intent(this, AuthActivity::class.java))
                dialog.dismiss()
                UserInformation.clearInformation()
                mainViewModel.clearDataFromStorage()
                finish()
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun setupFullName() {
        val fullName = "${UserInformation.firstName} ${UserInformation.lastName}"
        binding.userFullNameTextViewMain.text = fullName
    }
}