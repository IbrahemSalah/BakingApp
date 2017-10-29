package com.example.android.bakingapp.BakingWidget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.MainActivity;
import com.example.android.bakingapp.Modules.IngredientItem;
import com.example.android.bakingapp.R;

import java.util.ArrayList;

/**
 * Created by Eng_I on 10/12/2017.
 */

public class ListViewWidgetService extends RemoteViewsService {

    int position;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactoryForWidget(this.getApplicationContext(), intent, position);
    }

}


class RemoteViewsFactoryForWidget implements RemoteViewsService.RemoteViewsFactory {

    Context context;
    ArrayList<String> ingredints;
    int position = -1;


    public RemoteViewsFactoryForWidget(Context context, Intent intent, int position) {
        this.context = context;
        this.position = -1;
    }

    @Override
    public void onCreate() {
        ingredints = new ArrayList<>();
    }

    @Override
    public void onDataSetChanged() {
        ingredints = new ArrayList<>();
        position = BakingWidget.position;
        Log.i("I Value  :", String.valueOf(position));
        if (position > -1 && MainActivity.recipes.get(position).getIngredientItems().size() > -1) {
            ArrayList<IngredientItem> ingredientItems = MainActivity.recipes.get(position).getIngredientItems();
            for (int i = 0; i < ingredientItems.size(); i++) {
                ingredints.add(ingredientItems.get(i).ingredient);
            }
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (ingredints != null) {
            return ingredints.size();
        } else {
            return 0;
        }
    }


    @Override
    public RemoteViews getViewAt(int position) {
        if (ingredints == null || ingredints.size() == 0) return null;

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_listview_item);
        views.setTextViewText(R.id.widgetListViewTextView, ingredints.get(position));

        return views;
    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
