package com.raju.mvvm.ui.adapters.delegates

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.bumptech.glide.Glide
import com.raju.mvvm.R
import com.raju.mvvm.data.model.User
import com.raju.mvvm.ui.adapters.delegates.base.ListAdapterDelegate
import com.raju.mvvm.ui.adapters.delegates.base.ViewAdapterHolder

/**
 * Created by Rajashekhar Vanahalli on 23/07/18.
 */
class UserDelegate : ListAdapterDelegate<User>(R.layout.layout_user_item, User::class.java) {

    override fun formViewHolder(view: View): RecyclerView.ViewHolder {
        return MatchViewHolder(view)
    }

    protected inner class MatchViewHolder constructor(private var view: View): AdapterViewHolder(view), ViewAdapterHolder<User> {

        override fun setData(position: Int, data: User) {
            tvName.text = data.name!!
            Glide.with(ivAvatar.getContext())
                    .load(data.avatarUrl)
                    .override(40, 40)
                    .error(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(ivAvatar);
        }

        @BindView(R.id.iv_avatar)
        lateinit var ivAvatar: ImageView

        @BindView(R.id.tv_name)
        lateinit var tvName: TextView
    }
}