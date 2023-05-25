package com.builditcreative.qfonapp.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.builditcreative.qfonapp.R
import com.builditcreative.qfonapp.data.remote.Data
import com.builditcreative.qfonapp.databinding.ItemPassengerBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class PassengerAdapter(private val data: List<Data>) :
    RecyclerView.Adapter<PassengerAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemPassengerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, data: Data) {
            binding.tvPassengerId.text = data.id
            binding.tvPassengerName.text = data.name
            binding.tvPassengerTrip.text = data.trips.toString()

            Glide.with(binding.root.context)
                .load(data.airline[0].logo)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.ivAirlineLogo)

            binding.tvAirlineName.text = data.airline[0].name
            binding.tvAirlineSlogan.text = data.airline[0].slogan
            binding.tvAirlineCountry.text = data.airline[0].country
            binding.tvAirlineEstablish.text = data.airline[0].established
            binding.tvAirlineHeadQuater.text = data.airline[0].headQuarters

            binding.ivLink.setOnClickListener {
                it.context.startActivity(Intent(Intent.ACTION_VIEW,Uri.parse(if (data.airline[0].website.contains("http",true)) data.airline[0].website else "http://${data.airline[0].website}" )))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemPassengerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, data[position])
    }

    override fun getItemCount() = data.size

}
