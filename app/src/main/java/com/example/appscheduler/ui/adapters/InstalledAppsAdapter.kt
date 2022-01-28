package com.example.appscheduler.ui.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appscheduler.data.AppInfo
import com.example.appscheduler.databinding.InstalledAppsModelBinding
import java.text.ParseException
import javax.inject.Inject

class InstalledAppsAdapter @Inject constructor(
    private val actionClickListener: ActionClickListenerForInstalledApps
) : ListAdapter<AppInfo, InstalledAppsAdapter.InstalledAppsAdapterViewHolder>(Diff) {

    private lateinit var context: Context

    inner class InstalledAppsAdapterViewHolder(
        private val binding: InstalledAppsModelBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: AppInfo) {
            settingValueOfUI(data, binding, bindingAdapterPosition)
        }
    }

    private fun settingValueOfUI(
        data: AppInfo,
        binding: InstalledAppsModelBinding,
        position: Int
    ) {

        try {
            binding.apply {
                root.setOnClickListener {
                    actionClickListener.actionClickListener(position)
                }
                ivAppIcon.setImageDrawable(data.icon)
                tvAppName.text = data.appName
            }
        } catch (e: ParseException) {
            Log.e(
                "Exception", "InstalledApps -${e}"
            )
        }
    }


    object Diff : DiffUtil.ItemCallback<AppInfo>() {
        override fun areItemsTheSame(
            oldItem: AppInfo,
            newItem: AppInfo
        ): Boolean {
            return oldItem.packageName == newItem.packageName
        }

        override fun areContentsTheSame(
            oldItem: AppInfo,
            newItem: AppInfo
        ): Boolean {
            return oldItem == newItem
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InstalledAppsAdapterViewHolder {
        context = parent.context
        return InstalledAppsAdapterViewHolder(
            InstalledAppsModelBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: InstalledAppsAdapterViewHolder,
        position: Int
    ) {
        val getData = getItem(position)
        if (getData != null) {
            holder.bind(getData)
        }
    }
}