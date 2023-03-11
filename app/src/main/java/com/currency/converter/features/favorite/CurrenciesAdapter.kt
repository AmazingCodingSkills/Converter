



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.currency.converter.ConverterApplication
import com.currency.converter.base.favoritemodel.CurrencyItem
import com.currency.converter.base.room.Favorite
import com.currency.converter.features.favorite.FavoriteRepository
import com.example.converter.R
import com.example.converter.databinding.ListItemFavoriteBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CurrenciesAdapter constructor(private val onItemClickListener: (CurrencyItem) -> Unit) :
    ListAdapter<CurrencyItem, CurrenciesAdapter.Holder>(Comparator()) {

   private lateinit var favoriteRepository: FavoriteRepository

    inner class Holder(view: View, private val onItemClickListener: (CurrencyItem) -> Unit) :
        RecyclerView.ViewHolder(view) {

        private val binding = ListItemFavoriteBinding.bind(view)
        private lateinit var currency: CurrencyItem


        init {
            binding.favoriteImageButton.setOnClickListener {
                onItemClickListener(currency)
            }
        }

        fun bind(item: CurrencyItem) = with(binding) {
            //favoriteRepository = FavoriteRepository(ConverterApplication.appComponent.providesRoom())
            currency = item
            if (item.isFavorite) {
                binding.favoriteImageButton.setImageResource(R.drawable.ic_baseline_star_border_blue24)
                /*val newFavorite = currency.copy(isFavorite = !currency.isFavorite)
                GlobalScope.launch {
                    favoriteRepository.insert(newFavorite as Favorite)
                }*/
            } else {
                binding.favoriteImageButton.setImageResource(R.drawable.ic_baseline_star_border_24)
               /* val newFavorite = currency.copy(isFavorite = !currency.isFavorite)
                GlobalScope.launch {
                    favoriteRepository.insert(newFavorite as Favorite)
                }*/
            }
            nameCountryForFavorite.text = item.currencyName
        }
    }

    class Comparator : DiffUtil.ItemCallback<CurrencyItem>() {
        override fun areItemsTheSame(oldItem: CurrencyItem, newItem: CurrencyItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CurrencyItem, newItem: CurrencyItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_favorite, parent, false)
        favoriteRepository = FavoriteRepository(ConverterApplication.appComponent.providesRoom())
        return Holder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val favorite = getItem(position)
        holder.bind(favorite)
        holder.itemView.setOnClickListener {
            val newFavorite = favorite.copy(isFavorite = favorite.isFavorite) as Favorite
            GlobalScope.launch {
                favoriteRepository.insert(newFavorite)
            }
        }
    }
}



/*favoriteRepository = FavoriteRepository(ConverterApplication.appComponent.providesRoom())
val favorite =  getItem(position)
holder.bind(favorite)
val newFavorite = favorite.copy(isFavorite = !favorite.isFavorite)
GlobalScope.launch {
    favoriteRepository.insert(newFavorite as Favorite)*/
