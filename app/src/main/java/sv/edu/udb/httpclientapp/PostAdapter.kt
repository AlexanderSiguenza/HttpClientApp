package sv.edu.udb.httpclientapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class PostAdapter(private var data: List<Post>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    fun setData(data: List<Post>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = data[position]
        holder.titleTextView.text = post.title
        holder.bodyTextView.text = post.body
    }

    override fun getItemCount() = data.size

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val bodyTextView: TextView = itemView.findViewById(R.id.bodyTextView)
    }

}