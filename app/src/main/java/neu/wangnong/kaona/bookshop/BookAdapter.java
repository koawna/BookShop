package neu.wangnong.kaona.bookshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by iMac_24 on 6/21/2016 AD.
 */
public class BookAdapter extends BaseAdapter{

    //Explicit
    private Context context;
    private String[] iconStrings,nameStrings, priceStrings;

    public BookAdapter(Context context,
                       String[] iconStrings,
                       String[] nameStrings,
                       String[] priceStrings) {
        this.context = context;
        this.iconStrings = iconStrings;
        this.nameStrings = nameStrings;
        this.priceStrings = priceStrings;
    }   // Constructor

    @Override
    public int getCount() {
        return nameStrings.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.book_listviwe, viewGroup, false);

        ImageView imageView = (ImageView) view1.findViewById(R.id.imageView2);
        Picasso.with(context).load(iconStrings[i]).resize(150,180).into(imageView);

        TextView nameTextView = (TextView) view1.findViewById(R.id.textView7);
        nameTextView.setText(nameStrings[i]);

        TextView priceTextView = (TextView) view1.findViewById(R.id.textView8);
        priceTextView.setText(priceStrings[i]);

        return view1;
    }
}   //Main Adapter
