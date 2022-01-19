package com.teamfillin.fillin.presentation.onboarding

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.context.drawableOf
import com.teamfillin.fillin.core.context.stringOf
import com.teamfillin.fillin.databinding.ItemOnboardingBinding

class OnboardingAdapter(
    context: Context
) : RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {
    private val inflater = LayoutInflater.from(context)

    class OnboardingViewHolder(
        private val binding: ItemOnboardingBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(onboarding: Onboarding) {
            binding.onboarding = onboarding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val binding = ItemOnboardingBinding.inflate(inflater, parent, false)
        return OnboardingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.onBind(Onboarding.pages(holder.itemView.context)[position])
    }

    override fun getItemCount() = 3


    data class Onboarding(
        val title: String,
        val content: String,
        val imageResId: Drawable?
    ) {
        companion object {
            fun pages(context: Context) = listOf<Onboarding>(
                Onboarding(
                    context.stringOf(R.string.onboarding_title_1),
                    context.stringOf(R.string.onboarding_content_1),
                    context.drawableOf(R.drawable.ic_onboarding_1)
                ),
                Onboarding(
                    context.stringOf(R.string.onboarding_title_2),
                    context.stringOf(R.string.onboarding_content_2),
                    context.drawableOf(R.drawable.ic_onboarding_2)
                ),
                Onboarding(
                    context.stringOf(R.string.onboarding_title_3),
                    context.stringOf(R.string.onboarding_content_3),
                    context.drawableOf(R.drawable.ic_onboarding_3)
                )
            )
        }
    }
}