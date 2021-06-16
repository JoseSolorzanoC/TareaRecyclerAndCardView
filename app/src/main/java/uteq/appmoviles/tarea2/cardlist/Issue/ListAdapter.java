package uteq.appmoviles.tarea2.cardlist.Issue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import uteq.appmoviles.tarea2.R;
import uteq.appmoviles.tarea2.model.Issue;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<Issue> rvData;
    private LayoutInflater rvInflater;
    private Context context;

    public ListAdapter(List<Issue> rvData, Context context) {
        this.rvData = rvData;
        this.rvInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(rvInflater.inflate(R.layout.issue_cardelement, null));
    }

    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, int position) {
        holder.bindData(rvData.get(position));
    }

    @Override
    public int getItemCount() {
        return rvData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView issueCoverImage;
        TextView preInfo, title, publishDate, doi;

        public ViewHolder(View itemView) {
            super(itemView);
            issueCoverImage = itemView.findViewById(R.id.issueImageView);
            preInfo = itemView.findViewById(R.id.preInfoTextView);
            title = itemView.findViewById(R.id.titleTextView);
            publishDate = itemView.findViewById(R.id.publishDateTextView);
            doi = itemView.findViewById(R.id.doiTextView);
        }

        void bindData(final Issue item){
            Glide.with(context).load(item.getCover()).into(issueCoverImage);
            preInfo.setText(String.format("Vol. %s Núm. %s (%s)", item.getVolume(), item.getNumber(), item.getYear()));
            title.setText(String.format("Título: %s", item.getTitle()));
            publishDate.setText(String.format("Fecha de publicación: %s", item.getDate_published()));
            doi.setText(String.format("DOI: %s", item.getDoi()));
        }
    }

}
