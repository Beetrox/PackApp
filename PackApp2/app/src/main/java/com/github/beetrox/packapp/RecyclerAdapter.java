import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.beetrox.packapp.R;

import java.util.zip.Inflater;

/**
 * Created by Owner on 09/03/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private View inflater;

    public RecyclerAdapter() {
        //make constructor
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater = inflater.inflate(R.layout.packing_list_card, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
