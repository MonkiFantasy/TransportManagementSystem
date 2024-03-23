package monki.study.system_client.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.*;
import android.widget.Spinner;
import android.widget.TextView;
import monki.study.system_client.R;

import java.util.List;
public class SpinnerListAdapter extends ArrayAdapter<String>{
    public SpinnerListAdapter(Context context, List<String> data) {
        super(context, R.layout.list_item_layout, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            itemView = inflater.inflate(R.layout.list_item_layout, parent, false);
        }

        TextView textView = itemView.findViewById(R.id.listItemTextView);
        String itemText = getItem(position);
        textView.setText(itemText);

        return itemView;
    }
}
