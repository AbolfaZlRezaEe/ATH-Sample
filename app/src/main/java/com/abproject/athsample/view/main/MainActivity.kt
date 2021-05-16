package com.abproject.athsample.view.main

import android.content.DialogInterface
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
            setupRecyclerView(response, true)
        }

        binding.logOutButtonMain.setOnClickListener {
            setupLogoutSection()
        }
    }

    private fun setupRecyclerView(users: List<User>, implement: Boolean = false) {
        mainAdapter = MainAdapter()
        if (implement) {
            binding.recyclerViewMain.layoutManager =
                LinearLayoutManager(
                    this,
                    RecyclerView.VERTICAL,
                    false
                )
            mainAdapter.userDataChange(users)
            binding.recyclerViewMain.adapter = mainAdapter
        } else
            mainAdapter.userDataChange(users)
    }

    private fun setupLogoutSection() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.logoutDialogTitle))
            .setMessage(getString(R.string.logoutDialogMessage))
            .setPositiveButton(getString(R.string.yes)) { dialog, which ->
                startActivity(Intent(this, AuthActivity::class.java))
                dialog.dismiss()
                UserInformation.clearInformation()
                mainViewModel.clearDataFromStorage()
                finish()
            }
            .setNegativeButton(
                getString(R.string.no),
                DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
            .show()
    }

    private fun setupFullName() {
        val fullName = "${UserInformation.firstName} ${UserInformation.lastName}"
        binding.userFullNameTextViewMain.text = fullName
    }
}